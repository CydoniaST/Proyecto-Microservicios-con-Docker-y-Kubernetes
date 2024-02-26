package es.codeurjc.eolopark.service;


import es.codeurjc.eolopark.model.EoloPark;
import es.codeurjc.eolopark.repository.EoloParkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EoloParkService {

    @Autowired
    private EoloParkRepository eoloParkRepository;

    public EoloParkService(EoloParkRepository eoloParkRepository) {
        this.eoloParkRepository = eoloParkRepository;
    }
    public Page<EoloPark> getAllEoloParks(Pageable pageable) {
        return eoloParkRepository.findAll(pageable);
    }

    public List<EoloPark> findEoloParks(String city) {
        if (city == null || city.isEmpty()) {

            return eoloParkRepository.findAll();
        } else {
            return eoloParkRepository.findByCity(city);
        }
    }
    public Page<EoloPark> findEoloParks(String city, Pageable pageable) {
        if (city == null || city.isEmpty()) {
            return eoloParkRepository.findAll(pageable);
        } else {
            return eoloParkRepository.findByCity(city, pageable);
        }
    }

    public EoloPark findEoloParkById(Long id) {
        return eoloParkRepository.findById(id).orElseThrow(() -> new RuntimeException("Wind Farm not found with ID: " + id));
    }
    //delete park
    public void deleteEoloPark(Long id) {
        eoloParkRepository.deleteById(id);
    }

    
    public void save(EoloPark eoloPark){
        eoloParkRepository.save(eoloPark);
    }







}



