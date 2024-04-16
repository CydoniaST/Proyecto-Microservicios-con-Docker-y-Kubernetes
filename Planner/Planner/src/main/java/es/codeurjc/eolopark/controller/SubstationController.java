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

@Controller
public class SubstationController {

    @Autowired
    private SubstationService substationService;

    @Autowired
    private EoloParkService eoloParkService;

    @GetMapping("/NewSubstation/save/{id}")
    public String substationView(@PathVariable Long id, Model model) {

        model.addAttribute("parkId", id);
        return "NewSubstation";
    }

    @PostMapping("/NewSubstation/save/{eolopark_id}")
    public String newSubstation(@ModelAttribute Substation substation, @PathVariable Long eolopark_id,Model model) {

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

    @GetMapping("/EditSubstation/save/{substation_id}")
    public String editSubstationView(@PathVariable Long substation_id, Model model) {

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
            model.addAttribute("substation", substation); 
        }
        return "deletedSubstation";

    }



}