package es.codeurjc.eolopark.service;

import es.codeurjc.eolopark.model.Substation;
import es.codeurjc.eolopark.repository.SubstationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        return substationRepository.findById(eoloParkId).orElseThrow(() -> new RuntimeException("Substation not found with ID: " + eoloParkId));
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

    public void deleteSubstation(Long id) {

        substationRepository.deleteById(id);
    }


}
