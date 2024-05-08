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
import java.util.List;

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


    //LISTENER VICEN
    @RabbitListener(queues="eoloplantCreationProgressNotifications")
    public void receivedPark(MessagePark data)throws IOException, InterruptedException {

        System.out.println(data.toString());

        if(data != null){
            System.out.println("Progress: " + data.getProgress());
            System.out.println("getId(): " + data.getId());
            System.out.println("getCompleted(): " + data.getCompleted());
            if(data.getEoloPark() == null) eoloParkUpdatesService.eoloParkUpdated(data.getId(), data.getProgress(), data.getCompleted(),null);


            if(data.getEoloPark() != null){

                //AutoEoloPark recivePark = data.getAutoEoloPark();
                EoloPark automaticEolopark = data.getEoloPark();

                System.out.println("JOSELITOOOO "+ automaticEolopark.toString());
                automaticEolopark.setOwner(userDetailsService.findByUsername2("sandra"));

                eoloParkService.save(automaticEolopark);

                List<Aerogenerator> aerogenerators = new ArrayList<>();
                aerogenerators = automaticEolopark.getAerogeneratorList();
                automaticEolopark.setAerogeneratorList(aerogenerators);
                eoloParkService.save(automaticEolopark);

                Substation substation = automaticEolopark.getSubstation();
                automaticEolopark.setSubstation(substation);

                eoloParkService.save(automaticEolopark);

                eoloParkUpdatesService.eoloParkUpdated(data.getId(), data.getProgress(), data.getCompleted(),automaticEolopark);

            }
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
