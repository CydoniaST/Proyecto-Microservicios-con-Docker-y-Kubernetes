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

    @Value("${GEOSERVICE_HOST}")
    private String geoserviceHost;

    @Value("${GEOSERVICE_PORT}")
    private String geoservicePort;

    private final RestTemplate restTemplate;

    public PlannerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public City getCityInfo(String cityName) {
        String url = "http://" + geoserviceHost + ":" + geoservicePort + "/api/city/" + city;

        ResponseEntity<City> response = restTemplate.getForEntity(url, City.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            System.out.println("Failed to retrieve city info for: " + cityName);
            return null;
        }
    }

    public Double getWind(String city) throws IOException, InterruptedException{

        return windClientGrpc.grpcClient(city);
    }
}
