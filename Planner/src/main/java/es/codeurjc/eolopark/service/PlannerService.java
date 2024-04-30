package es.codeurjc.eolopark.service;
import java.io.IOException;

import es.codeurjc.eolopark.client.WindClientGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import es.codeurjc.eolopark.model.City;


@Service
public class PlannerService {

    @Autowired
    private WindClientGrpc windClientGrpc;

    private final RestTemplate restTemplate;
    private final String geoServiceUrl = "http://localhost:8082/api/city/";

    public PlannerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    //Llamar a getCityInfo al recibir el Rabbit
    public City getCityInfo(String cityName) {
        String url = geoServiceUrl + cityName;
        ResponseEntity<City> response = restTemplate.getForEntity(url, City.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            System.out.println("Failed to retrieve city info for: " + cityName);
            return null;
        }
    }

    //getWind (grpc)
    public Double getWind(String city) throws IOException, InterruptedException{

        return windClientGrpc.grpcClient(city);
    }
}
