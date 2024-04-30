package es.codeurjc.eolopark;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ServerApplication {

	public static void main(String[] args) {

		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	public Queue creationQueue() {

		System.out.println("Cola creada.");
		return new Queue("eoloplantCreationRequests", false);
	}

	@Bean
	public Queue ResponseQueue() {

		System.out.println("Cola creada.");
		return new Queue("eoloplantCreationProgressNotifications", false);
	}

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
