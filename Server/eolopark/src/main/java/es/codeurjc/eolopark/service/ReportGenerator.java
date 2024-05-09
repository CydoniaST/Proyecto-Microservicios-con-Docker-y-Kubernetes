package es.codeurjc.eolopark.service;

import es.codeurjc.eolopark.model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This service simulate the report creation
 *
 * To simulate a long computational time, Thread.sleep(...) is used.
 *
 * When a report is updated, reportUpdatesService is used to save the update on database and to notify users
 */
@Component
public class ReportGenerator {

    Logger logger = LoggerFactory.getLogger(ReportGenerator.class);

    @Autowired
    private EoloParkUpdatesService eoloParkUpdatesService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private ProducerParkServer producerParkServer;

    @Autowired
    private EoloParkService eoloParkService;

    @Autowired
    private UserDetailsService userDetailsService;

    HttpServletRequest request;
    @Autowired
    private AerogeneratorService aerogeneratorService;

    //LISTENER VICEN
    @RabbitListener(queues="eoloplantCreationProgressNotifications", ackMode = "AUTO")
    public void receivedPark(MessagePark data)throws IOException, InterruptedException {

        System.out.println(data.toString());

            if(data.getEoloPark() == null){
                eoloParkUpdatesService.eoloParkUpdated(data.getId(), data.getProgress(), data.getCompleted(),null);
            }else{

                //AutoEoloPark recivePark = data.getAutoEoloPark();
                es.codeurjc.eolopark.model.DTO.EoloPark eoloPark = data.getEoloPark();
                EoloPark automaticEolopark = new EoloPark();
                //Map<Aerogenerator, Long> aerogenerators = new HashMap<>();
                List<Aerogenerator> aerogeneratorList = new ArrayList<>(eoloPark.getAerogeneratorList());


                automaticEolopark.setOwner(userDetailsService.findByUsername2("sandra"));

                automaticEolopark.setName(eoloPark.getName());
                automaticEolopark.setCity(eoloPark.getCity());
                automaticEolopark.setArea(eoloPark.getArea());
                automaticEolopark.setId(eoloPark.getId());
                automaticEolopark.setLatitude(eoloPark.getLatitude());
                automaticEolopark.setLongitude(eoloPark.getLongitude());
                automaticEolopark.setAerogeneratorList(aerogeneratorList);

                for(Aerogenerator aerogenerator : automaticEolopark.getAerogeneratorList()){
                    aerogenerator.setEoloPark(automaticEolopark);
                }

                String terrain = eoloPark.getTerrainType();
                automaticEolopark.setTerrainType(TerrainType.valueOf(terrain));
                automaticEolopark.setSubstation(eoloPark.getSubstation());

                eoloParkService.save(automaticEolopark);

                eoloParkUpdatesService.eoloParkUpdated(data.getId(), data.getProgress(), data.getCompleted(),automaticEolopark);

            }

        System.out.println("New automatic park recibed: " + data.getEoloPark());
    }

    @Async
    protected void createReport(Long parkId, String city, double area, User user) {

        logger.info("createReport: "+parkId);
        //Message data = new Message(parkId,city,area,user);
        Message data = new Message(parkId ,city,area);

        //PRODUCER MESSAGE TO PLANNER
        producerParkServer.sendData(data);

    }

    private void simulateProcessTime() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
