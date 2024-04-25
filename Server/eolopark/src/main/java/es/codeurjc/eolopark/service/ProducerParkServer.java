package es.codeurjc.eolopark.service;

import es.codeurjc.eolopark.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProducerParkServer {

	@Autowired
	RabbitTemplate rabbitTemplate;

	public void sendData(Message data) {

		System.out.println("publishToQueue: " + data);

		rabbitTemplate.convertAndSend("eoloplantCreationRequests", data);
	}



}
