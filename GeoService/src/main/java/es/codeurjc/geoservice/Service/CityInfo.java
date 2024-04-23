package es.codeurjc.geoservice.Service;

import es.codeurjc.geoservice.Model.City;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CityInfo implements GeoService{

    private Map<String,City> cities = new HashMap<>();


    public CityInfo(){
        cities.put("A Coruña", new City("A Coruña", 43.37012643, -8.39114853, 21));
        cities.put("Albacete", new City("Albacete", 38.99588053, -1.85574745, 681));
        cities.put("Alicante", new City("Alicante", 38.34548705, -0.4831832, 5));
        cities.put("Almería", new City("Almería", 36.83892362, -2.46413188, 16));
        cities.put("Ávila", new City("Ávila", 40.65586958, -4.69771277, 1131));
        cities.put("Badajoz", new City("Badajoz", 38.87874339, -6.97099704, 182));
        cities.put("Barcelona", new City("Barcelona", 41.38424664, 2.17634927, 13));
        cities.put("Bilbao", new City("Vizcaya", 43.25721957, -2.92390606, 6));
        cities.put("Burgos", new City("Burgos", 42.34113004, -3.70419805, 859));
        cities.put("Cáceres", new City("Cáceres", 39.47316762, -6.37121092, 457));
        cities.put("Cádiz", new City("Cádiz", 36.52171152, -6.28414575, 13));
        cities.put("Castellón de la Plana", new City("Castellón", 39.98640809, -0.03688142, 27));
        cities.put("Ceuta", new City("Ceuta", 35.88810209, -5.30675127, 27));
        cities.put("Ciudad Real", new City("Ciudad Real", 38.98651781, -3.93131981, 625));
        cities.put("Córdoba", new City("Córdoba", 37.87954225, -4.78032455, 106));
        cities.put("Cuenca", new City("Cuenca", 40.07653762, -2.13152306, 997));
        cities.put("Girona", new City("Girona", 41.98186075, 2.82411899, 69));
        cities.put("Granada", new City("Granada", 37.17641932, -3.60001883, 684));
        cities.put("Guadalajara", new City("Guadalajara", 40.63435548, -3.16210273, 685));
        cities.put("Huelva", new City("Huelva", 37.26004113, -6.95040588, 24));
        cities.put("Huesca", new City("Huesca", 42.14062739, -0.40842276, 483));
        cities.put("Jaén", new City("Jaén", 37.7651913, -3.7903594, 570));
        cities.put("Las Palmas de Gran Canaria", new City("Las Palmas", 28.09937855, -15.41336841, 6));
        cities.put("León", new City("León", 42.59912097, -5.56707631, 837));
        cities.put("Lleida", new City("Lleida", 41.61527355, 0.62061934, 167));
        cities.put("Logroño", new City("La Rioja", 42.46644945, -2.44565538, 384));
        cities.put("Lugo", new City("Lugo", 43.0091282, -7.55817392, 452));
        cities.put("Madrid", new City("Madrid", 40.40841191, -3.68760088, 657));
        cities.put("Málaga", new City("Málaga", 36.72034267, -4.41997511, 8));
        cities.put("Melilla", new City("Melilla", 35.294731, -2.942281, 30));
        cities.put("Murcia", new City("Murcia", 37.98436361, -1.1285408, 42));
        cities.put("Ourense", new City("Ourense", 42.33654919, -7.86368375, 138));
        cities.put("Oviedo", new City("Asturias", 43.36232165, -5.84372206, 231));
        cities.put("Palencia", new City("Palencia", 42.0078373, -4.53460106, 734));
        cities.put("Palma", new City("Islas Baleares", 39.57114699, 2.65181698, 24));
        cities.put("Pamplona", new City("Navarra", 42.814102, -1.6451528, 450));
        cities.put("Pontevedra", new City("Pontevedra", 42.43381442, -8.64799018, 16));
        cities.put("Salamanca", new City("Salamanca", 40.96736822, -5.66538084, 798));
        cities.put("San Sebastián", new City("Gipuzkoa", 43.31717158, -1.98191785, 7));
        cities.put("Santa Cruz de Tenerife", new City("Santa Cruz de Tenerife", 28.46285408, -16.24720629, 1));
        cities.put("Santander", new City("Cantabria", 43.46297885, -3.80474784, 8));
        cities.put("Segovia", new City("Segovia", 40.9498703, -4.12524116, 1002));
        cities.put("Sevilla", new City("Sevilla", 37.38620512, -5.99251368, 11));
        cities.put("Soria", new City("Soria", 41.76327912, -2.46624798, 1061));
        cities.put("Tarragona", new City("Tarragona", 41.11910287, 1.2584219, 69));
        cities.put("Teruel", new City("Teruel", 40.34412951, -1.10927177, 915));
        cities.put("Valencia", new City("Valencia", 39.47534441, -0.37565717, 16));
        cities.put("Valladolid", new City("Valladolid", 41.65232777, -4.72334924, 690));
        cities.put("Vitoria", new City("Álava", 42.85058789, -2.67275685, 539));
        cities.put("Zamora", new City("Zamora", 41.49913956, -5.75494831, 649));
        cities.put("Zaragoza", new City("Zaragoza", 41.65645655, -0.87928652, 208));


    }

    public City getCityInfoByName(String cityName) {
        City city = cities.get(cityName);
        if (city != null) {
            System.out.println("City found: " + cityName); // Debug: Ciudad encontrada
            return city;
        } else {
            System.out.println("City not found: " + cityName); // Debug: Ciudad no encontrada
            return null;
        }
    }
    /*@Override
    public List<City> getCityInfoByName(String cityName) {
        List<City> result = new ArrayList<>();

        // Iterate through cities map and add matching cities to result list
        for (City city : cities.values()) {
            if (city.getName().equalsIgnoreCase(cityName)) {
                result.add(city);
            }
        }

        return result;
    }*/
}
