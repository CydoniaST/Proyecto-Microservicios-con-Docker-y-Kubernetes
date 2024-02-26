package es.codeurjc.eolopark.controller;


import es.codeurjc.eolopark.service.AerogeneratorService;
import es.codeurjc.eolopark.model.Aerogenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AerogeneratorController {


    @Autowired
    AerogeneratorService aerogeneratorService;


    /*@GetMapping("/DetallesPark/{id}")
    public String infoEoloPark(@PathVariable Long id, Model model) {
        // We obtain the park information by its ID
       
       
        model.addAttribute("DetallesPark", aerogeneratorService.findAerogeneratorById(id));
       

         //User createdByUser = userService.findUserById(eoloPark.getCreatedByUserId());
         //model.addAttribute("createdBy", createdByUser.getUsername());

        return "DetallesPark"; 
    }  */

}