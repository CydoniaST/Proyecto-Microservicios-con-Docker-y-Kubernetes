package es.codeurjc.eolopark.service;


import es.codeurjc.eolopark.model.EoloPark;
import es.codeurjc.eolopark.repository.EoloParkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EoloParkService {

    @Autowired
    private EoloParkRepository eoloParkRepository;

    public EoloParkService(EoloParkRepository eoloParkRepository) {
        this.eoloParkRepository = eoloParkRepository;
    }


    public List<EoloPark> findEoloParks(String city) {
        if (city == null || city.isEmpty()) {
            return eoloParkRepository.findAll();
        } else {
            return eoloParkRepository.findByCity(city);
        }
    }

    public EoloPark findEoloParkById(Long id) {
        return eoloParkRepository.findById(id).orElseThrow(() -> new RuntimeException("Parque Eólico no encontrado con ID: " + id));
    }
    //Borrar parque
    public void deleteEoloPark(Long id) {
        eoloParkRepository.deleteById(id);
    }

    
    public void save(EoloPark eoloPark){
        eoloParkRepository.save(eoloPark);
    }

    //Modificación de parque

    /*
    public EoloPark modifyEoloPark(String name, EoloPark updatedEoloPark) {
        Optional<EoloPark> existingEoloPark = eoloParkRepository.findByName(name);
        if (existingEoloPark.isPresent()) {
            EoloPark eoloPark = existingEoloPark.get();
            // Aquí actualizas los atributos de eoloPark con los de updatedEoloPark
            // Por ejemplo:
            eoloPark.setName(updatedEoloPark.getName());
            eoloPark.setCity(updatedEoloPark.getCity());
            eoloPark.setLatitude(updatedEoloPark.getLatitude());
            eoloPark.setLongitude(updatedEoloPark.getLongitude());
            eoloPark.setArea(updatedEoloPark.getArea());
            eoloPark.setTerrainType(updatedEoloPark.getTerrainType());
            // Falta el foreach de los aerogeneradores
            //Falta subestacion

            return eoloParkRepository.save(eoloPark);
        } else {
            // Manejar el caso en que el EoloPark no se encuentra
            // Puedes lanzar una excepción o manejarlo de otra manera
            throw new RuntimeException("EoloPark not found with name " + name);
        }
    }

*/


/*
    //Borrado de parque
    public void deletedByName(String name){
        eoloParkRepository.deletedByName(name);
    }

*/

}



