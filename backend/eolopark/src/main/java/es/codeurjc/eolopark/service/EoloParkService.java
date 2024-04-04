package es.codeurjc.eolopark.service;


import es.codeurjc.eolopark.model.*;
import es.codeurjc.eolopark.repository.CitiesRepository;
import es.codeurjc.eolopark.repository.EoloParkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EoloParkService {

    @Autowired
    private EoloParkRepository eoloParkRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private UserDetailsService userDetailsService;


    public EoloParkService(EoloParkRepository eoloParkRepository) {
        this.eoloParkRepository = eoloParkRepository;
    }

    public Page<EoloPark> getAllEoloParks(Pageable pageable) {
        Page<EoloPark> eoloParks = eoloParkRepository.findAll(pageable);

        // Agregar logs para verificar los parques recuperados
        System.out.println("Recuperando todos los parques:");
        for (EoloPark park : eoloParks.getContent()) {
            System.out.println("ID: " + park.getId() + ", Nombre: " + park.getName() + ", Tipo: " + (park instanceof EoloPark ? "Manual" : "Automático"));
        }

        return eoloParks;
    }


    public Page<EoloPark> findEoloParksByOwnerId(Long id, Pageable pageable) {
        if(id == 0){
            return eoloParkRepository.findAll(pageable);
        } else {
            return eoloParkRepository.findByOwnerId(id, pageable);
        }
    }

    public Page<EoloPark> findEoloParksByOwnerIdAndCity(Long id, String city, Pageable pageable) {
        if(id == 0 && (city == null || city.isEmpty())){
            return eoloParkRepository.findAll(pageable);
        } else if (id != 0 && (city == null || city.isEmpty())) {
            return eoloParkRepository.findByOwnerId(id, pageable);
        } else if (id == 0 && (city != null && !city.isEmpty())) {
            return eoloParkRepository.findByCity(city, pageable);
        } else {
            return eoloParkRepository.findByOwnerIdAndCity(id, city, pageable);
        }
    }

    public EoloPark findEoloParkById(Long id) {
        return eoloParkRepository.findById(id).orElseThrow(() -> new RuntimeException("Wind Farm not found with ID: " + id));
    }
    //delete park
    public void deleteEoloPark(Long id) {
        eoloParkRepository.deleteById(id);
    }

    public void setEoloParkOwner(Long id, User user){
        EoloPark eoloPark = eoloParkRepository.findById(id).get();

        eoloPark.setOwner(user);
    }

    public void setOwner(EoloPark eoloPark, User user){
        eoloPark.setOwner(user);
    }

    public EoloPark save(EoloPark eoloPark) {
        EoloPark savedPark = eoloParkRepository.save(eoloPark);
        System.out.println("Parque guardado correctamente: " + savedPark.getName());
        return savedPark;
    }


    //Automatic Creation
    public @ResponseBody EoloPark newAutomaticEoloPark(String name, double area, User owner){
        //GENERIC NAME FOR AUTOMATIC PARK
        String nameCity = "EoloParque Automatico con ID: "+Math.random()*10;

        //method to use latitude and longitude of a city from dataBase


        Optional<Cities> newCity = citiesRepository.findByName(name);

        if(newCity.isPresent()){
            Cities cityAux = newCity.get();

            String windSpeed = getWindSpeed(name);

            Aerogenerator.Size size = aerogeneratorSize(windSpeed);

            //Number of Aerogen that fit in the EoloPark
            int aerogeneratorNum= (int)Math.ceil(area/1.0);

            //create EoloPark based on city and are
            EoloPark automaticEoloPark = new EoloPark(name, area);

            automaticEoloPark.setName(nameCity);
            automaticEoloPark.setLatitude(cityAux.getLatitude());
            automaticEoloPark.setLongitude(cityAux.getLongitude());

            //Substation based on new EoloPark
            Substation newSubstation = new Substation("Model 1",220.0, calculateSubstationPower(aerogeneratorNum),automaticEoloPark);
            automaticEoloPark.setSubstation(newSubstation);

            //List of new Aerogens
            List<Aerogenerator> aerogenerators = new ArrayList<>();
            double latitude = cityAux.getLatitude() - 0.5 / 111.0;
            double longitude = cityAux.getLongitude() + 0.5 / 111.0;
            for(int i = 0; i < aerogeneratorNum; i++){
                Aerogenerator aerogenerator = new Aerogenerator(i + "",latitude, longitude, size.getBladeLength(), size.getHeight(), size.getPower());
                aerogenerator.setEoloPark(automaticEoloPark);
                aerogenerators.add(aerogenerator);
                longitude += 1.0/111.0;
            }

            automaticEoloPark.setAerogeneratorList(aerogenerators);
            automaticEoloPark.setTerrainType(TerrainType.PLAIN);

            return automaticEoloPark;
        }else{
            throw new IllegalArgumentException("No se encuentra la ciudad");
        }

    }

    private String getWindSpeed(String city){

        //acceder a base de datos //LEER ESTO CUANDO CONTINUE: HAY QUE BUSCAR EN LA BD LA cityAux Y
        //QUE EL METODO FINDWINDBYCITY DEVUELVA EL VIENTO DE ESA cityAux
        Optional<Cities> wind = citiesRepository.findByName(city);

        Cities windSpeedCity;
        double windSpeed = 0;
        if(wind.isPresent()){
            windSpeedCity = wind.get();

            windSpeed = windSpeedCity.getWindSpeed();
            if(windSpeed <= 7.06){
                return "LOW";
            }else if(windSpeed > 7.06 && windSpeed <= 7.63){
                return"MEDIUM";
            }else{
                return "HIGH";
            }
        }else{
            throw new IllegalArgumentException("Unknown wind speed: " + windSpeed);
        }

    }

    private Aerogenerator.Size aerogeneratorSize(String windSpeed){
        switch(windSpeed) {
            case "LOW":
                return Aerogenerator.Size.SMALL;
            case "MEDIUM":
                return Aerogenerator.Size.MEDIUM;
            case "HIGH":
                return Aerogenerator.Size.BIG;
            default:
                throw new IllegalArgumentException("Unknown wind speed: " + windSpeed);
        }
    }

    private double calculateSubstationPower(int aerogeneratorNum){

        double maxPower = 0;
        for(int i = 0; i< aerogeneratorNum; i++){
            maxPower += Aerogenerator.Size.MEDIUM.getPower();
        }

        return maxPower*1.2; //margin = +20%
    }

    public EoloPark findBySubstation_Id(Long id){
        return eoloParkRepository.findBySubstation_Id(id);
    }







}



