package es.codeurjc.eolopark.service;

import es.codeurjc.eolopark.model.Message;
import es.codeurjc.eolopark.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * This service simulate the report creation
 *
 * To simulate a long computational time, Thread.sleep(...) is used.
 *
 * When a report is updated, reportUpdatesService is used to save the update on database and to notify users
 */
@Service
public class ReportGenerator {

    Logger logger = LoggerFactory.getLogger(ReportGenerator.class);

    @Autowired
    private EoloParkUpdatesService eoloParkUpdatesService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Async
    protected void createReport(Long parkId, String city, double area, User user) {

        logger.info("createReport: "+parkId);
        Message data = new Message(parkId,city,area,user);
        sendData(data);

        simulateProcessTime();
        eoloParkUpdatesService.eoloParkUpdated(parkId, 25, "false");

        simulateProcessTime();
        eoloParkUpdatesService.eoloParkUpdated(parkId, 50, "false");

        simulateProcessTime();
        eoloParkUpdatesService.eoloParkUpdated(parkId, 75, "false");

        simulateProcessTime();
        eoloParkUpdatesService.eoloParkUpdated(parkId, 100, "false");

    }

    //LISTENER VICEN
    @RabbitListener(queues="eoloplantCreationProgressNotifications", ackMode = "AUTO")
    public void receivedPark(Message data){

        System.out.println("New automatic Eolo Park: " + data.getEoloPark().getName() + " "+ data.getEoloPark().getArea());
    }

    //PRODUCER TEST VICEN
    @Scheduled(fixedRate = 1000)
    public void sendData(Message data) {



        //numData++;

        System.out.println("publishToQueue: " + data);

        rabbitTemplate.convertAndSend("eoloplantCreationRequests", data);
    }


    private void simulateProcessTime() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
