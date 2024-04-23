package es.codeurjc.eolopark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EoloparkApplication {

	public static void main(String[] args) {
		SpringApplication.run(EoloparkApplication.class, args);
	}

}
