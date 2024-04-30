package es.codeurjc.eolopark.service;

import es.codeurjc.eolopark.model.*;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class EoloParkServiceSend {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    PlannerService service;

    private EoloPark newEolopark;

    private Message progress;

    //LISTENER 
    @RabbitListener(queues="eoloplantCreationRequests", ackMode = "AUTO")
    public void received(Message data) throws IOException, InterruptedException {

        if(data != null){
            System.out.println(data.toString());
            newEolopark = testEoloPark(data);
        }


        if (newEolopark != null) {
            System.out.println("New Park recived. "+ data);
        }

    }

    //PRODUCER
    public void sendDataProgress(Message progress) throws IOException, InterruptedException {

        Message progressMessage = new Message(progress.getId(), progress.getProgress(), progress.getCompleted());

        System.out.println("Processing: " + progressMessage);

        rabbitTemplate.convertAndSend("eoloplantCreationProgressNotifications", progressMessage);
    }

    //PRODUCER
    public void sendDataPark(EoloPark park) {

        MessagePark automaticPark = new MessagePark(park.getId().toString(),100.0, true, park);

        System.out.println("Sending a vicen EoloPark: " + automaticPark);

        rabbitTemplate.convertAndSend("eoloplantCreationProgressNotifications", automaticPark);
    }

    public EoloPark testEoloPark(Message data) throws IOException, InterruptedException {

        EoloPark newEolopark = new EoloPark(data.getCity(),data.getArea());

        //Proceso geoservice
        simulateProcessTime();
        newEolopark.setCity(data.getCity());
        newEolopark.setArea(data.getArea());
        progress = new Message(1L, 25.0, false);
        sendDataProgress(progress);


        //Proceso windservice
        simulateProcessTime();
        newEolopark.setId(data.getId());
        progress = new Message(1L, 50.0, false);
        sendDataProgress(progress);

        //Proceso Aerogeneradores
        simulateProcessTime();
        progress = new Message(1L, 75.0, false);
        sendDataProgress(progress);

        //Proceso Subestaciones
        simulateProcessTime();
        Message newPark = new Message(1L, 100.0, true, newEolopark);
        sendDataProgress(newPark);
        sendDataPark(newEolopark);

        return newEolopark;
    }


    //Automatic Creation
    public EoloPark newAutomaticEoloPark(Message data) throws IOException, InterruptedException {
        //GENERIC NAME FOR AUTOMATIC PARK
        String nameAutoPark = "EoloParque Automatico con ID: "+Math.random()*10;
        String wind;

        //create EoloPark based on city and are
        EoloPark automaticEoloPark = new EoloPark(data.getCity(), data.getArea());

        //Geoservice response with info about the city
        City city = service.getCityInfo(data.getCity());
        System.out.println(city);
        simulateProcessTime();
        progress = new Message(1L, 25.0, false);
        sendDataProgress(progress);

        if(city != null){
            //WindService response with city wind info
            Double windSpeed = service.getWind(data.getCity());
            simulateProcessTime();
            progress = new Message(1L, 50.0, false);
            sendDataProgress(progress);

            if(windSpeed <= 7.06){
                wind = "LOW";
            } else if ((windSpeed >= 7.06)&&(windSpeed < 7.63)) {
                wind = "MEDIUM";
            }else{
                wind = "HIGH";
            }
            Aerogenerator.Size size = aerogeneratorSize(wind);

            //Number of Aerogen that fit in the EoloPark
            int aerogeneratorNum= (int)Math.ceil(data.getArea());



            automaticEoloPark.setName(nameAutoPark);
            automaticEoloPark.setLatitude(city.getLatitude());
            automaticEoloPark.setLongitude(city.getLongitude());


             //List of new Aerogens
            List<Aerogenerator> aerogenerators = new ArrayList<>();
            double latitude = city.getLatitude() - 0.5 / 111.0;
            double longitude = city.getLongitude() + 0.5 / 111.0;
             for(int i = 0; i < aerogeneratorNum; i++){
                Aerogenerator aerogenerator = new Aerogenerator(i + "",latitude, longitude, size.getBladeLength(), size.getHeight(), size.getPower());
                aerogenerator.setEoloPark(automaticEoloPark);
                aerogenerators.add(aerogenerator);
                longitude += 1.0/111.0;
            }

             //All Aerogenerator placed
            simulateProcessTime();
            progress = new Message(1L, 75.0, false);
            sendDataProgress(progress);

            automaticEoloPark.setAerogeneratorList(aerogenerators);
            automaticEoloPark.setTerrainType(TerrainType.PLAIN.toString());

            //Substation based on new EoloPark
            Substation newSubstation = new Substation("Model 1",220.0, calculateSubstationPower(aerogeneratorNum),automaticEoloPark);
            automaticEoloPark.setSubstation(newSubstation);

            //Automatic EoloPark Completed
            simulateProcessTime();
            Message newPark = new Message(1L, 100.0, true, automaticEoloPark);
            sendDataProgress(newPark);
            sendDataPark(automaticEoloPark);

            return automaticEoloPark;
        }else{
            throw new IllegalArgumentException("No se encuentra la ciudad");
        }

        //return new EoloPark(name, area);
    }


    
    private Aerogenerator.Size aerogeneratorSize(String windSpeed){
        return switch (windSpeed) {
            case "LOW" -> Aerogenerator.Size.SMALL;
            case "MEDIUM" -> Aerogenerator.Size.MEDIUM;
            case "HIGH" -> Aerogenerator.Size.BIG;
            default -> throw new IllegalArgumentException("Unknown wind speed: " + windSpeed);
        };
    }

    private double calculateSubstationPower(int aerogeneratorNum){

        double maxPower = 0;
        for(int i = 0; i< aerogeneratorNum; i++){
            maxPower += Aerogenerator.Size.MEDIUM.getPower();
        }

        return maxPower*1.2; //margin = +20%
    }

    private void simulateProcessTime() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}



