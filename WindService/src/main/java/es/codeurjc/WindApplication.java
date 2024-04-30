package es.codeurjc;

import es.codeurjc.server.service.WindService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

@SpringBootApplication
public class WindApplication {

    public static void main(String[] args) throws IOException, InterruptedException {

        SpringApplication.run(WindApplication.class, args);

    }

}
