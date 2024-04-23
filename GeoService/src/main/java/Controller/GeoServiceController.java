package Controller;

import Service.CityInfo;
import Service.GeoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import Model.City;

@RestController
public class GeoServiceController {

    @Autowired
    private GeoService geoservice;

    @PostConstruct
    public void init() {
        /*CityInfo cityInfo = new CityInfo();
        List<City> cities = cityInfo.getCityInfoByName("Madrid");
        for(City city: cities){
            System.out.println(city);
        }*/
    }

    @GetMapping("api/city/{nameCity}")
    public City getInfo(@PathVariable String nameCity){
        CityInfo cityInfo = new CityInfo();
        City city = cityInfo.getCityInfoByName(nameCity);//geoservice.getCityInfoByName(nameCity);
        return city;
        /*if (!city.isEmpty()) {
            return new ResponseEntity<>(city, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }*/
    }
}
