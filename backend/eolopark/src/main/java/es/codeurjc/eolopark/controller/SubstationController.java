package es.codeurjc.eolopark.controller;

import es.codeurjc.eolopark.service.SubstationService;
import es.codeurjc.eolopark.model.Aerogenerator;
import es.codeurjc.eolopark.model.Substation;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/substation")
public class SubstationController {

    @Autowired
    private SubstationService substationService;

    public SubstationController(SubstationService substationService) {
        this.substationService = substationService;
    }

    @PostConstruct
	public void init() {
		substationService.addSubstation(new Substation("Modelo Ejemplo", 12.22, 53.11));
		//eoloParkService.save(new EoloPark("Juan", "Hola caracola", "XXXX"));
	}

    @PostMapping("/add")
    public ResponseEntity<Substation> addSubstation(@RequestBody Substation substation) {
        Substation newSubstation = substationService.addSubstation(substation);
        return ResponseEntity.ok(newSubstation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Substation>> getSubstation(@PathVariable Long id) {
        Optional<Substation> substation = substationService.findSubstationById(id);
        return ResponseEntity.ok(substation);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Substation> updateSubstation(@PathVariable Long id, @RequestBody Substation updatedSubstation) {
        Substation substation = substationService.modifySubstation(id, updatedSubstation);
        return ResponseEntity.ok(substation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSubstation(@PathVariable Long id) {
        substationService.deleteSubstation(id);
        return ResponseEntity.ok().build();
    }

    // Otros métodos según sean necesarios...
}