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


    @PostConstruct
    public void init() {
        aerogeneratorService.save(new Aerogenerator("123", 12.1243, 12.43422, 23.2, 323.2, 123.3));
        aerogeneratorService.save(new Aerogenerator("123", 23.233, 65.543, 50.0, 654.1, 192.3));
        aerogeneratorService.save(new Aerogenerator("456", 65.34, 98.12, 543.3, 600.3, 1.1));

    }


    /*@GetMapping("/DetallesPark/{id}")
    public String infoEoloPark(@PathVariable Long id, Model model) {
        // Obtenemos la info del parque por su ID
       
       
        model.addAttribute("DetallesPark", aerogeneratorService.findAerogeneratorById(id));
       
         // Obtenemos la info del usuario que creo el parque
         //User createdByUser = userService.findUserById(eoloPark.getCreatedByUserId());
         //model.addAttribute("createdBy", createdByUser.getUsername());

        return "DetallesPark"; 
    }  */

}