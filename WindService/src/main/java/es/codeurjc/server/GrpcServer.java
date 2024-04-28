package es.codeurjc.server;

import java.io.IOException;

import es.codeurjc.server.service.WindServ;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {

    public void startServer() throws IOException, InterruptedException{
        System.out.println("Starting server...");

        Server server = ServerBuilder.forPort(9090)
          .addService(new WindServ()).build();
        
        server.start();
        
        System.out.println("Server started!");
        
        server.awaitTermination();
    }
}