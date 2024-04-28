package es.codeurjc.client;

import es.codeurjc.ProtoService.proto.WindRequest;
import es.codeurjc.ProtoService.proto.WindResponse;
import es.codeurjc.ProtoService.proto.WindServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class WindClientGrpc {

    public Double grpcClient(String city){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
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
