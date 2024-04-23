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

/*
package es.codeurjc.geoservice.Controller;

import es.codeurjc.geoservice.Service.CityInfo;
import es.codeurjc.geoservice.Service.GeoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import es.codeurjc.geoservice.Model.City;

@RestController
public class GeoServiceController {

    @Autowired
    private GeoService geoservice;

    @PostConstruct
    public void init() {
        */
/*CityInfo cityInfo = new CityInfo();
        List<City> cities = cityInfo.getCityInfoByName("Madrid");
        for(City city: cities){
            System.out.println(city);
        }*//*

    }

    */
/*
    * @GetMapping("/eolopark/{id}")
    public ResponseEntity<EoloPark> getEolopark(@PathVariable long id) {
        Optional<EoloPark> eolopark = eoloParksrepository.findById(id);
        return eolopark.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    * *//*


    @GetMapping("/api/city/{nameCity}")
    public ResponseEntity<City> getInfo(@PathVariable String nameCity){
        CityInfo cityInfo = new CityInfo();
        City city = cityInfo.getCityInfoByName(nameCity);//geoservice.getCityInfoByName(nameCity);
        return ResponseEntity.ok(city);

        */
/*if (!city.isEmpty()) {
            return new ResponseEntity<>(city, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }*//*

    }
}
*/
