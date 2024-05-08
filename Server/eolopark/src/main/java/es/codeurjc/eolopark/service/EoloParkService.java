package es.codeurjc.eolopark.service;


import es.codeurjc.eolopark.model.*;
import es.codeurjc.eolopark.repository.CitiesRepository;
import es.codeurjc.eolopark.repository.EoloParkRepository;
import es.codeurjc.eolopark.repository.SubstationRepository;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Handler;


@Service
public class EoloParkService {

    @Autowired
    private EoloParkRepository eoloParkRepository;
    
    @Autowired
    private SubstationRepository substationRepository;

    public EoloParkService(EoloParkRepository eoloParkRepository) {
        this.eoloParkRepository = eoloParkRepository;
    }

    public Page<EoloPark> getAllEoloParks(Pageable pageable) {
        Page<EoloPark> eoloParks = eoloParkRepository.findAll(pageable);


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

    public EoloPark findBySubstation_Id(Long id){
        return eoloParkRepository.findBySubstation_Id(id);
    }



    public Page<Substation> getAllSubstations(Pageable pageable) {
        return substationRepository.findAll(pageable);
    }




}



