package es.codeurjc.geoservice.Controller;

import es.codeurjc.geoservice.Service.CityInfo;
import es.codeurjc.geoservice.Service.GeoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import es.codeurjc.geoservice.Model.City;
@RestController
@RequestMapping("/api")
public class GeoServiceController {

    @Autowired
    private GeoService geoService;

    @GetMapping("/city/{name}")
    public ResponseEntity<City> getCityInfo(@PathVariable String name) {
        CityInfo cityInfo = new CityInfo();
        City city = cityInfo.getCityInfoByName(name);
        if (city != null) {
            return ResponseEntity.ok(city);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

