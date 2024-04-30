package es.codeurjc.eolopark.client;

import es.codeurjc.dad.grpc.WindRequest;
import es.codeurjc.dad.grpc.WindResponse;
import es.codeurjc.dad.grpc.WindServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class WindClientGrpc {

    public Double grpcClient(String city){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8084)
            .usePlaintext()
            .build();

        WindServiceGrpc.WindServiceBlockingStub client = WindServiceGrpc.newBlockingStub(channel);

        WindRequest request = WindRequest.newBuilder()
            .setName(city)
            .build();
        
		WindResponse response = client.getWindSpeed(request);

        System.out.println("Response received from server:\n" + response);

        channel.shutdown();

        return response.getWind();
    }
}
