package es.codeurjc.eolopark.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.codeurjc.eolopark.model.Substation;
import es.codeurjc.eolopark.repository.SubstationRepository;

@Service
public class SubstationService {

    @Autowired
    private SubstationRepository substationRepository;

    public Page<Substation> getAllSubstations(Pageable pageable) {
        return substationRepository.findAll(pageable);
    }
    public SubstationService(SubstationRepository substationRepository) {
        this.substationRepository = substationRepository;
    }

    public Substation save(Substation substation) {

        return substationRepository.save(substation);
    }

    public Optional<Substation> findSubstationById(Long id) {

        return substationRepository.findById(id);
    }

    public Substation findSubstationByEoloParkId(Long eoloParkId) {

        return substationRepository.findById(eoloParkId).orElseThrow(() -> new RuntimeException("Substación no encontrada con ID: " + eoloParkId));
    }

    public Substation modifySubstation(Long id, Substation updatedSubstation) {
        Optional<Substation> existingSubstation = substationRepository.findById(id);
        if (existingSubstation.isPresent()) {
            Substation substation = existingSubstation.get();

           
            substation.setModel(updatedSubstation.getModel());
           
            return substationRepository.save(substation);
        } else {

            throw new RuntimeException("Substation not found with id " + id);
        }
    }



    public boolean deleteSubstation(Long substation_id) {
        try {
            substationRepository.deleteById(substation_id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    

   
}
