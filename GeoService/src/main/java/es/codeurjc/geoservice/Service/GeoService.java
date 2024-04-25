package es.codeurjc.geoservice.Service;

import es.codeurjc.geoservice.Model.City;

public interface GeoService {
    City getCityInfoByName(String cityName);

}
