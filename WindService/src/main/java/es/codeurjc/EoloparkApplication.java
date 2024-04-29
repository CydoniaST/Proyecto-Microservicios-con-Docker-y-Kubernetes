package es.codeurjc;

import java.io.IOException;

import es.codeurjc.client.WindClientGrpc;
import es.codeurjc.server.GrpcServer;

public class EoloparkApplication {
    public static void main(String[] args) {
        try {
            EoloparkApplication app = new EoloparkApplication();
            Double wind = app.getWind("Madrid");
            System.out.println("Velocidad del viento en Madrid: " + wind);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Double getWind(String city) throws IOException, InterruptedException {
        // Start the gRPC server
        GrpcServer server = new GrpcServer();
        server.startServer();

        // Start the gRPC client
        WindClientGrpc client = new WindClientGrpc();
        Double wind = client.grpcClient(city);

        return wind;
    }
}
