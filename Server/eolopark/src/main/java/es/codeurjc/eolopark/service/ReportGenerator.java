package es.codeurjc.eolopark.service;

import es.codeurjc.eolopark.model.EoloPark;
import es.codeurjc.eolopark.model.Message;
import es.codeurjc.eolopark.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

    private EoloPark automaticEolopark;


    //LISTENER VICEN
    @RabbitListener(queues="eoloplantCreationProgressNotifications", ackMode = "AUTO")
    public void receivedPark(Message data){

        System.out.println("Progress: " + data. getProgress());
        eoloParkUpdatesService.eoloParkUpdated(data.getId(), data.getProgress(), data.getCompleted());
        if(data.getEoloPark() != null){
            System.out.println("New automatic Eolo Park: " + data.getEoloPark().getName() + " "+ data.getEoloPark().getArea());
            automaticEolopark = data.getEoloPark();
        }

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
