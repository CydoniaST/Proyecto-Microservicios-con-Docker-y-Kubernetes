
package es.codeurjc.windservice.service;

import java.util.HashMap;
import java.util.Map;

import es.codeurjc.WindService.proto.WindRequest;
import es.codeurjc.WindService.proto.WindResponse;
import es.codeurjc.WindService.proto.WindServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
public class WindServ extends WindServiceGrpc.WindServiceImplBase implements WindService{
    Map<String, Double> wind = new HashMap<>();
    public WindServ(){
        wind.put("A Coruña", 7.2);
        wind.put("Albacete", 6.9);
        wind.put("Alicante", 7.5);
        wind.put("Almería", 8.0);
        wind.put("Ávila", 6.7);
        wind.put("Badajoz", 7.0);
        wind.put("Barcelona", 7.4);
        wind.put("Bilbao", 7.7);
        wind.put("Burgos", 7.6);
        wind.put("Cáceres", 7.2);
        wind.put("Cádiz", 8.1);
        wind.put("Castellón de la Plana", 7.1);
        wind.put("Ceuta", 7.4);
        wind.put("Ciudad Real", 6.6);
        wind.put("Córdoba", 7.1);
        wind.put("Cuenca", 6.8);
        wind.put("Girona", 7.5);
        wind.put("Granada", 7.5);
        wind.put("Guadalajara", 6.9);
        wind.put("Huelva", 7.9);
        wind.put("Huesca", 8.1);
        wind.put("Jaén", 7.3);
        wind.put("Las Palmas de Gran Canaria", 8.2);
        wind.put("León", 7.4);
        wind.put("Lleida", 7.5);
        wind.put("Logroño", 7.2);
        wind.put("Lugo", 7.3);
        wind.put("Madrid", 7.3);
        wind.put("Málaga", 7.8);
        wind.put("Melilla", 7.3);
        wind.put("Murcia", 7.6);
        wind.put("Ourense", 6.5);
        wind.put("Oviedo", 7.5);
        wind.put("Palencia", 6.8);
        wind.put("Palma", 7.9);
        wind.put("Pamplona", 7.4);
        wind.put("Pontevedra", 6.6);
        wind.put("Salamanca", 7.2);
        wind.put("San Sebastián", 7.6);
        wind.put("Santa Cruz de Tenerife", 8.2);
        wind.put("Santander", 7.4);
        wind.put("Segovia", 6.9);
        wind.put("Sevilla", 7.4);
        wind.put("Soria", 6.7);
        wind.put("Tarragona", 7.2);
        wind.put("Teruel", 7.0);
        wind.put("Toledo", 7.1);
        wind.put("Valencia", 7.3);
        wind.put("Valladolid", 7.0);
        wind.put("Vitoria", 7.2);
        wind.put("Zamora", 6.8);
        wind.put("Zaragoza", 8.1);
    }

    @Override
    public void getWindSpeed(WindRequest request, StreamObserver<WindResponse> response){

        System.out.println("Request received from client:\n" + request);

        String nameCity = request.getName();
        double windSpeed = wind.getOrDefault(nameCity,null);

        WindResponse response2 = WindResponse.newBuilder().setWind(windSpeed).build();

        response.onNext(response2);
        response.onCompleted();
    }
}