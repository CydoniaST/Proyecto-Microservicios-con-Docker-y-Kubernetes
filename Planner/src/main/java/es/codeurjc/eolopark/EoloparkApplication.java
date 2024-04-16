package es.codeurjc.eolopark;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class EoloparkApplication {

	public static void main(String[] args) {
		SpringApplication.run(EoloparkApplication.class, args);
	}

	@Bean
	public Queue myQueue() {
    	return new Queue("eoloplantCreationRequests", false);
	}

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
