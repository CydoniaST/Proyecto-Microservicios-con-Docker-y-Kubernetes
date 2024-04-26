package es.codeurjc.eolopark.service;

import es.codeurjc.eolopark.model.*;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;


@Component
public class EoloParkServiceSend {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    PlannerService cityInfo;

    private String name; 
    private double area;
    private int numData;

    private EoloPark newEolopark;
    private Message progress;

    //LISTENER 
    @RabbitListener(queues="eoloplantCreationRequests", ackMode = "AUTO")
    public void received(Message data){
        newEolopark = testEoloPark(data);

        if (newEolopark != null) {
            System.out.println("New Park recived. "+ data);
        }

    }

    //PRODUCER
    public void sendDataProgress(Message progress) {

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

    public EoloPark testEoloPark(Message data){

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
    public EoloPark newAutomaticEoloPark(String name, double area){
        //GENERIC NAME FOR AUTOMATIC PARK
        String nameAutoPark = "EoloParque Automatico con ID: "+Math.random()*10;

        //method to use latitude and longitude of a city from dataBase

        // Optional<Cities> newCity = citiesRepository.findByName(name);


        // if(newCity.isPresent()){
        //     Cities cityAux = newCity.get();

        //     String windSpeed = getWindSpeed(name);

        //     Aerogenerator.Size size = aerogeneratorSize(windSpeed);

        //     //Number of Aerogen that fit in the EoloPark
        //     int aerogeneratorNum= (int)Math.ceil(area/1.0);

        //     //create EoloPark based on city and are
        //     EoloPark automaticEoloPark = new EoloPark(name, area);

        //     automaticEoloPark.setName(nameAutoPark);
        //     automaticEoloPark.setLatitude(cityAux.getLatitude());
        //     automaticEoloPark.setLongitude(cityAux.getLongitude());

        //     //Substation based on new EoloPark
        //     Substation newSubstation = new Substation("Model 1",220.0, calculateSubstationPower(aerogeneratorNum),automaticEoloPark);
        //     automaticEoloPark.setSubstation(newSubstation);

        //     //List of new Aerogens
        //     List<Aerogenerator> aerogenerators = new ArrayList<>();
        //     double latitude = cityAux.getLatitude() - 0.5 / 111.0;
        //     double longitude = cityAux.getLongitude() + 0.5 / 111.0;
        //     for(int i = 0; i < aerogeneratorNum; i++){
        //         Aerogenerator aerogenerator = new Aerogenerator(i + "",latitude, longitude, size.getBladeLength(), size.getHeight(), size.getPower());
        //         aerogenerator.setEoloPark(automaticEoloPark);
        //         aerogenerators.add(aerogenerator);
        //         longitude += 1.0/111.0;
        //     }

        //     automaticEoloPark.setAerogeneratorList(aerogenerators);
        //     automaticEoloPark.setTerrainType(TerrainType.PLAIN);

        //     return automaticEoloPark;
        // }else{
        //     throw new IllegalArgumentException("No se encuentra la ciudad");
        // }

        return new EoloPark(name, area);
    }


    
    private Aerogenerator.Size aerogeneratorSize(String windSpeed){
        switch(windSpeed) {
            case "LOW":
                return Aerogenerator.Size.SMALL;
            case "MEDIUM":
                return Aerogenerator.Size.MEDIUM;
            case "HIGH":
                return Aerogenerator.Size.BIG;
            default:
                throw new IllegalArgumentException("Unknown wind speed: " + windSpeed);
        }
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



