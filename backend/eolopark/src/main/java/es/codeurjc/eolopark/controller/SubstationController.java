package es.codeurjc.eolopark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import es.codeurjc.eolopark.model.EoloPark;
import es.codeurjc.eolopark.model.Substation;
import es.codeurjc.eolopark.service.EoloParkService;
import es.codeurjc.eolopark.service.SubstationService;

//@RestController
//@RequestMapping("/substation")
@Controller
public class SubstationController {

    @Autowired
    private SubstationService substationService;

    @Autowired
    private EoloParkService eoloParkService;

  
    // public SubstationController(SubstationService substationService) {
    // this.substationService = substationService;
    // }

    // @PostConstruct
    // public void init() {
    // substationService.save(new Substation("Modelo Ejemplo", 12.22, 53.11,null));
    // //eoloParkService.save(new EoloPark("Juan", "Hola caracola", "XXXX"));
    // }

    // @PostMapping("/add")
    // public ResponseEntity<Substation> addSubstation(@RequestBody Substation
    // substation) {
    // Substation newSubstation = substationService.save(substation);
    // return ResponseEntity.ok(newSubstation);
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<Optional<Substation>> getSubstation(@PathVariable Long
    // id) {
    // Optional<Substation> substation = substationService.findSubstationById(id);
    // return ResponseEntity.ok(substation);
    // }

    // @PutMapping("/update/{id}")
    // public ResponseEntity<Substation> updateSubstation(@PathVariable Long id,
    // @RequestBody Substation updatedSubstation) {
    // Substation substation = substationService.modifySubstation(id,
    // updatedSubstation);
    // return ResponseEntity.ok(substation);
    // }

    // @DeleteMapping("/delete/{id}")
    // public ResponseEntity<?> deleteSubstation(@PathVariable Long id) {
    // substationService.deleteSubstation(id);
    // return ResponseEntity.ok().build();
    // }

    // Método para mostrar el formulario para crear una nueva subestacion
    @GetMapping("/NewSubstation/save/{id}")
    public String substationView(@PathVariable Long id, Model model) {
        // Pasar el ID del parque al formulario
        model.addAttribute("parkId", id);
        return "NewSubstation";
    }

    // Método para guardar el nuevo aerogenerador
    @PostMapping("/NewSubstation/save/{eolopark_id}")
    public String newSubstation(@ModelAttribute Substation substation, @PathVariable Long eolopark_id,Model model) {

        // Obtener el parque correspondiente al ID
        EoloPark eoloPark = eoloParkService.findEoloParkById(eolopark_id);

        if (eoloPark == null) {
            model.addAttribute("error", "No se encuentra este parque");
            return "redirect:/"; 
        }

        substationService.save(substation);
        eoloPark.setSubstation(substation);
        eoloParkService.save(eoloPark);

        return "redirect:/DetailsPark/" + eolopark_id;
    }

    // Método para mostrar el formulario para editar una subestacion
    @GetMapping("/EditSubstation/save/{substation_id}")
    public String editSubstationView(@PathVariable Long substation_id, Model model) {
        // Pasar el ID del parque al formulario
        model.addAttribute("parkId", substation_id);
        return "EditSubstation";
    }

    @PostMapping("/EditSubstation/save/{substation_id}")
    public String editSubstation(@ModelAttribute Substation updatedSubstation,@PathVariable Long substation_id, Model model) {

        EoloPark eoloPark = eoloParkService.findBySubstation_Id(substation_id);
        Substation substation = eoloPark.getSubstation();

        if (substation != null) {
            substation.setModel(updatedSubstation.getModel());
            substation.setPower(updatedSubstation.getPower());
            substation.setVoltage(updatedSubstation.getVoltage());

            substationService.save(substation);


        }

        return "redirect:/DetailsPark/" + eoloPark.getId();
    }

    @GetMapping("/Substation/delete/{id}")
    public String deleteSubstation(Model model, @PathVariable long id) {
        EoloPark eoloPark = eoloParkService.findBySubstation_Id(id);
        Substation substation = eoloPark.getSubstation();

        if (substation != null) {

            eoloPark.setSubstation(null);
            eoloParkService.save(eoloPark);
            substationService.deleteSubstation(id);
            model.addAttribute("eoloPark", eoloPark);
            model.addAttribute("substation", substation); //getClass()??
        }
        return "deletedSubstation";

    }



    // @PostMapping("/DeleteSubstation/delete/{substation_id}")
        // public String deleteSubstation(@PathVariable Long substation_id){
        //     boolean isDelete = substationService.deleteSubstation(substation_id);
        //         if(isDelete){
        //             return "redirect:/DetailsPark/";
        //         } else {
        //             return "redirect:/loginerror";
        //         }
            
        // }

}