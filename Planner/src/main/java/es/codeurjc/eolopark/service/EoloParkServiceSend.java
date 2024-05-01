package es.codeurjc.eolopark.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.codeurjc.eolopark.model.*;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

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
    MessagePlanner progress;

    //LISTENER 
    @RabbitListener(queues="eoloplantCreationRequests", ackMode = "AUTO")
    public void received(MessagePlanner data) throws IOException, InterruptedException {

        if(data != null){
            System.out.println("El parque es:"+data.toString());
            newAutomaticEoloPark(data);
        }


        if (newEolopark != null) {
            System.out.println("New Park recived. "+ data);
        }

    }

    //PRODUCER
    @JsonIgnore
    public void sendDataProgress(MessagePlanner progress2) throws IOException, InterruptedException {

//        Message progressMessage = new Message(progress.getId(), progress.getProgress(), progress.getCompleted());
          MessagePlanner progressMessagePlanner;
          MessagePark parkCompleted;
//        System.out.println("Processing: " + progressMessage);

//        if(progress2.getProgress() == 100){
//            //Message automaticPark = new Message(progress.getId(), progress.getProgress(), progress.getCompleted(), park);
//            System.out.println("El parque es:" + progress2.toString() );
//            parkCompleted = new MessagePark(progress2.getId(), progress2.getProgress(), progress2.getCompleted(), progress2.getEoloPark());
//            System.out.println("Processing: " + parkCompleted);
//            rabbitTemplate.convertAndSend("eoloplantCreationProgressNotifications", parkCompleted);
//        }else{

            progressMessagePlanner = new MessagePlanner(progress2.getId(), progress2.getProgress(), progress2.getCompleted());

            System.out.println("Eolopark: " + progressMessagePlanner.getEoloPark());

            if(progress2.getProgress() == 100){
                System.out.println("Envio de Parque completo" );

                parkCompleted = new MessagePark(progress2.getId(), 100.0, progress2.getCompleted(), progress2.getEoloPark());
                rabbitTemplate.convertAndSend("eoloplantCreationProgressNotifications", parkCompleted);
                System.out.println("Eolopark: " + parkCompleted.getEoloPark());

            }else{
                rabbitTemplate.convertAndSend("eoloplantCreationProgressNotifications", progressMessagePlanner);
            }


        //}

        //rabbitTemplate.convertAndSend("eoloplantCreationProgressNotifications", progressMessage);
    }

    //PRODUCER
    @JsonIgnore
    public void sendDataPark(MessagePark park) {


        MessagePark parkCompleted = new MessagePark(park.getId(), park.getProgress(), park.getCompleted(), park.getEoloPark());

        rabbitTemplate.convertAndSend("eoloplantCreationProgressNotifications", parkCompleted);
    }

//    public EoloPark testEoloPark(MessagePlanner data) throws IOException, InterruptedException {
//
//        EoloPark newEolopark = new EoloPark(data.getCity(),data.getArea());
//
//        //Proceso geoservice
//        simulateProcessTime();
//        newEolopark.setCity(data.getCity());
//        newEolopark.setArea(data.getArea());
//        progress = new MessagePlanner(1L, 25.0, false);
//        sendDataProgress(progress);
//
//
//        //Proceso windservice
//        simulateProcessTime();
//        newEolopark.setId(data.getId());
//        progress = new MessagePlanner(1L, 50.0, false);
//        sendDataProgress(progress);
//
//        //Proceso Aerogeneradores
//        simulateProcessTime();
//        progress = new MessagePlanner(1L, 75.0, false);
//        sendDataProgress(progress);
//
//        //Proceso Subestaciones
//        simulateProcessTime();
//        MessagePark newPark = new MessagePark(1L, 100.0, true, newEolopark);
//        sendDataProgress(newPark);
//        //sendDataProgress(newEolopark);
//
//        return newEolopark;
//    }


    //Automatic Creation
    public void newAutomaticEoloPark(MessagePlanner data) throws IOException, InterruptedException {
        //GENERIC NAME FOR AUTOMATIC PARK
        String nameAutoPark = "EoloParque Automatico con ID: "+Math.random()*10;
        String wind;


        //create EoloPark based on city and are
        EoloPark automaticEoloPark = new EoloPark(data.getCity(), data.getArea());

        automaticEoloPark.setId(data.getId());

        //Geoservice response with info about the city
        City city = service.getCityInfo(data.getCity());
        System.out.println(data.getCity());
        simulateProcessTime();
        progress = new MessagePlanner(1L, 25.0, false);
        sendDataProgress(progress);
        //sendDataProgress(progress);

        if(city != null){
            //WindService response with city wind info
            Double windSpeed = service.getWind(data.getCity());
            simulateProcessTime();
            progress = new MessagePlanner(1L, 50.0, false);
            sendDataProgress(progress);
            //sendDataProgress(progress);

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
                aerogenerators.add(aerogenerator);
                //aerogenerator.setEoloPark(automaticEoloPark);
                longitude += 1.0/111.0;
            }

             //All Aerogenerator placed
            simulateProcessTime();
            progress = new MessagePlanner(1L, 75.0, false);
            sendDataProgress(progress);
            //sendDataProgress(progress);

            automaticEoloPark.setAerogeneratorList(aerogenerators);
            automaticEoloPark.setTerrainType(TerrainType.PLAIN.toString());

            //Substation based on new EoloPark
            Substation newSubstation = new Substation("Model 1",220.0, calculateSubstationPower(aerogeneratorNum));
            automaticEoloPark.setSubstation(newSubstation);

            //Automatic EoloPark Completed
            simulateProcessTime();
            //Message newPark = new Message(1L, 100.0, true, automaticEoloPark);

            progress = new MessagePlanner(1L, 100.0, true);
            progress.setEoloPark(automaticEoloPark);
            sendDataProgress(progress);
            //sendDataPark(automaticEoloPark);

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



