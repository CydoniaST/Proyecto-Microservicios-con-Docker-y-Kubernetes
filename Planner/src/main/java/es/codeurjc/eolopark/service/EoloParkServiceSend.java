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

    // LISTENER
    @RabbitListener(queues="eoloplantCreationRequests", ackMode = "AUTO")
    public void received(MessagePlanner data) throws IOException, InterruptedException {
        try {
            if(data == null) {
                throw new IllegalArgumentException("El mensaje recibido es nulo");
            }

            System.out.println("Park:" + data.toString());
            newAutomaticEoloPark(data);

            if (newEolopark != null) {
                System.out.println("New Park received. " + data);
            }
        } catch (Exception e) {
            System.err.println("Error al procesar el mensaje: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // PRODUCER
    public void sendDataProgress(MessagePlanner progress2) throws IOException, InterruptedException {
        MessagePlanner progressMessagePlanner;
        MessagePark parkCompleted;

        progressMessagePlanner = new MessagePlanner(progress2.getId(), progress2.getProgress(), progress2.getCompleted());

        System.out.println("Eolopark: " + progressMessagePlanner.getEoloPark());

        if(progress2.getProgress() == 100){
            System.out.println("Complete Park Delivery");
            progressMessagePlanner.setEoloPark(progress2.getEoloPark());
            progressMessagePlanner.getEoloPark().setAerogeneratorList(progress2.getEoloPark().getAerogeneratorList());
            progressMessagePlanner.getEoloPark().setSubstation(progress2.getEoloPark().getSubstation());
            parkCompleted = new MessagePark(progress2.getId(), 100.0, progress2.getCompleted(), progressMessagePlanner.getEoloPark());
            rabbitTemplate.convertAndSend("eoloplantCreationProgressNotifications", parkCompleted);
            System.out.println("Eolopark: " + parkCompleted.getEoloPark());
        } else {
            rabbitTemplate.convertAndSend("eoloplantCreationProgressNotifications", progressMessagePlanner);
        }
    }

    // Automatic creation
    public void newAutomaticEoloPark(MessagePlanner data) throws IOException, InterruptedException {
        String nameAutoPark = "Automatic EoloParque with ID: " + Math.random() * 10;
        String wind;

        EoloPark automaticEoloPark = new EoloPark(data.getCity(), data.getArea());
        Long id = data.getId();
        automaticEoloPark.setId(id);

        City city = service.getCityInfo(data.getCity());
        System.out.println(data.getCity());
        simulateProcessTime();
        progress = new MessagePlanner(id, 25.0, false);
        sendDataProgress(progress);

        if(city != null){
            Double windSpeed = service.getWind(data.getCity());
            simulateProcessTime();
            progress = new MessagePlanner(id, 50.0, false);
            sendDataProgress(progress);

            wind = determineWindSpeed(windSpeed);
            Aerogenerator.Size size = aerogeneratorSize(wind);

            int aerogeneratorNum = (int)Math.ceil(data.getArea());

            automaticEoloPark.setName(nameAutoPark);
            automaticEoloPark.setLatitude(city.getLatitude());
            automaticEoloPark.setLongitude(city.getLongitude());

            List<Aerogenerator> aerogenerators = new ArrayList<>();
            double latitude = city.getLatitude() - 0.5 / 111.0;
            double longitude = city.getLongitude() + 0.5 / 111.0;
            for(int i = 0; i < aerogeneratorNum; i++){
                Aerogenerator aerogenerator = new Aerogenerator(String.valueOf(i), latitude, longitude, size.getBladeLength(), size.getHeight(), size.getPower());
                aerogenerators.add(aerogenerator);
                longitude += 1.0 / 111.0;
            }

            simulateProcessTime();
            progress = new MessagePlanner(id, 75.0, false);
            sendDataProgress(progress);

            automaticEoloPark.setAerogeneratorList(aerogenerators);
            automaticEoloPark.setTerrainType(TerrainType.PLAIN.toString());

            Substation newSubstation = new Substation("Model 1", 220.0, calculateSubstationPower(aerogeneratorNum));
            automaticEoloPark.setSubstation(newSubstation);

            simulateProcessTime();

            progress = new MessagePlanner(id, 100.0, true);
            progress.setEoloPark(automaticEoloPark);
            data.setEoloPark(automaticEoloPark);
            sendDataProgress(progress);

        } else {
            throw new IllegalArgumentException("No se encuentra la ciudad");
        }
    }

    private String determineWindSpeed(Double windSpeed) {
        if (windSpeed <= 7.06) {
            return "LOW";
        } else if (windSpeed >= 7.06 && windSpeed < 7.63) {
            return "MEDIUM";
        } else {
            return "HIGH";
        }
    }

    private Aerogenerator.Size aerogeneratorSize(String windSpeed) {
        return switch (windSpeed) {
            case "LOW" -> Aerogenerator.Size.SMALL;
            case "MEDIUM" -> Aerogenerator.Size.MEDIUM;
            case "HIGH" -> Aerogenerator.Size.BIG;
            default -> throw new IllegalArgumentException("Unknown wind speed: " + windSpeed);
        };
    }

    private double calculateSubstationPower(int aerogeneratorNum) {
        double maxPower = 0;
        for (int i = 0; i < aerogeneratorNum; i++) {
            maxPower += Aerogenerator.Size.MEDIUM.getPower();
        }
        return maxPower * 1.2; // margen = +20%
    }

    private void simulateProcessTime() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}



