package es.codeurjc.eolopark.controller;


import java.util.ArrayList;
import java.util.List;

import es.codeurjc.eolopark.model.Aerogenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.eolopark.service.AerogeneratorService;
import es.codeurjc.eolopark.service.EoloParkService;
import es.codeurjc.eolopark.service.SubstationService;
import es.codeurjc.eolopark.model.Aerogenerator;
//import es.codeurjc.eolopark.service.UserDetailsService;
import es.codeurjc.eolopark.model.EoloPark;
import es.codeurjc.eolopark.model.TerrainType;

import es.codeurjc.eolopark.repository.EoloParkRepository;
import jakarta.annotation.PostConstruct;


@Controller
public class EoloParkController {

    @Autowired
    EoloParkService eoloParkService;

    @Autowired
    AerogeneratorService aerogeneratorService;

    @Autowired
    SubstationService substationService;

    //@Autowired
    //UserDetailsService userService;
    
    @Autowired
    EoloParkRepository eoloParkRepository;

    private List<Aerogenerator> aerogeneratorList = new ArrayList<>();

    @PostConstruct
	public void init() {
		eoloParkService.save(new EoloPark("Miraflores", "Madrid", 12.1243, 12.43422,23.2, TerrainType.PLAIN));
	}
   
    
    @GetMapping("/")
    public String listEoloParks(@RequestParam(required = false) String city, Model model) {
        model.addAttribute("eoloParks", eoloParkService.findEoloParks(city));
        return "PaginaPrincipal";
    }

   /* @GetMapping("/PaginaPrincipal")
    public String paginaPrincipal(@RequestParam(required = false) String city, Model model) {
        model.addAttribute("eoloParks", eoloParkService.findEoloParks(city));
        return "PaginaPrincipal";
    }*/

    @GetMapping("/EoloPark")
    public String EoloPark( Model model) {
        model.addAttribute("eoloParks");
        return "EoloPark";
    }
    
    /*@GetMapping("/Error")
    public String Error( @RequestParam(required = false) String city, Model model) {
        model.addAttribute("eoloParks", eoloParkService.findEoloParks(city));
        return "Login";
    }*/


/*
 * 
 *  @GetMapping("/EoloPark/{id}")
    public String editEoloPark(@PathVariable Long id, Model model) {
        EoloPark eoloPark = eoloParkService.findEoloParkById(id);
        model.addAttribute("eoloPark", eoloPark);
        return "editEoloPark"; // Suponiendo que tienes una vista llamada "editEoloPark"
    }
 */
    /*@RequestMapping(value = "/EoloPark/{id}")
    public String getEoloParkInfo(@PathVariable Long id, Model model) {
        // Obtener la información del parque Eólico por su ID
        EoloPark eoloPark = eoloParkService.findEoloParkById(id);
        model.addAttribute("eoloPark", eoloPark);

        // Obtener la información del usuario que creó el parque Eólico
        //User createdByUser = userService.findUserById(eoloPark.getCreatedByUserId());
        //model.addAttribute("createdBy", createdByUser.getUsername());

        return "DetallesPark"; // Devuelve el nombre de la vista
    }
    */


    @GetMapping("/EoloPark/edit/{id}")
    public String editEoloPark(@PathVariable Long id, Model model) {
        // Lógica para obtener los datos del parque a editar y pasarlos al formulario de edición
        EoloPark eoloPark = eoloParkService.findEoloParkById(id);
        model.addAttribute("eoloPark", eoloPark);

        return "EditEoloPark"; // Devuelve el nombre de la vista de edición
    }

    @GetMapping("/EoloPark/delete/{id}")
    public String deleteEoloPark(@PathVariable Long id) {
        // Lógica para borrar el parque Eólico por su ID
        eoloParkService.deleteEoloPark(id);

        return "redirect:/"; // Redirecciona a la página principal
    }

    @GetMapping("/DetallesPark/{id}")
    public String infoEoloPark(@PathVariable Long id, Model model) {
        // Obtenemos la info del parque por su ID
       
        model.addAttribute("DetallesPark", eoloParkService.findEoloParkById(id));
        //model.addAttribute("DetallesSubstation", substationService.findSubstationByEoloParkId(id));
        //model.addAttribute("DetallesAerogenerator", aerogeneratorService.findAerogeneratorByEoloParkId(id));
         // Obtenemos la info del usuario que creo el parque
         //User createdByUser = userService.findUserById(eoloPark.getCreatedByUserId());
         //model.addAttribute("createdBy", createdByUser.getUsername());

        return "DetallesPark"; 
    }   



}
