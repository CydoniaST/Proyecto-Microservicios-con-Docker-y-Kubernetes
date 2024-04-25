package es.codeurjc.eolopark.service;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import es.codeurjc.eolopark.model.City;
import org.springframework.http.ResponseEntity;

@Service
public class PlannerService {

    private final RestTemplate restTemplate;
    private final String geoServiceUrl = "http://localhost:8080/api/city/";

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
}
