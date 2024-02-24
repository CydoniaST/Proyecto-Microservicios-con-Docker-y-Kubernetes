package es.codeurjc.eolopark.controller;


import java.util.ArrayList;
import java.util.List;

import es.codeurjc.eolopark.model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import es.codeurjc.eolopark.service.AerogeneratorService;
import es.codeurjc.eolopark.service.EoloParkService;
import es.codeurjc.eolopark.service.SubstationService;
import es.codeurjc.eolopark.service.UserDetailsService;
import es.codeurjc.eolopark.repository.UserRepository;

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

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    EoloParkRepository eoloParkRepository;

    private List<Aerogenerator> aerogeneratorList = new ArrayList<>();

    @PostConstruct
	public void init() {
	
        EoloPark ep1 = new EoloPark("Miraflores", "Madrid", 0, 0, 0, TerrainType.DESERT);
        EoloPark ep2 = new EoloPark("Ciudad Lineal","Barcelona", 0, 0, 0, TerrainType.MOUNTAIN);
    
        eoloParkRepository.save(ep1);
        eoloParkRepository.save(ep2);

        Aerogenerator a1 = new Aerogenerator("1", 12.1243, 12.43422, 23.2, 323.2, 123.3);
        Aerogenerator a2 = new Aerogenerator("1", 23.233, 65.543, 50.0, 654.1, 192.3);
        
        a1.setEoloPark(ep2);
        a2.setEoloPark(ep1);

        aerogeneratorService.save(a1);
        aerogeneratorService.save(a2);

	}
   

//    @RequestMapping("/PaginaPrincipal")
//     public String paginaPrincipal(@RequestParam(required = false) String city, Model model, HttpServletRequest request) {
//        String name = request.getUserPrincipal().getName();

//        User user = userRepository.findByName(name).orElseThrow();

//        model.addAttribute("eoloParks", eoloParkService.findEoloParks(city));
//        model.addAttribute("username", user.getName());
//        model.addAttribute("admin", request.isUserInRole("ADMIN"));
//        return "PaginaPrincipal";
//     }

    @GetMapping("/PaginaPrincipal")
    public String mainPage(Model model, HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();

        User user = userRepository.findByName(name).orElseThrow();

        model.addAttribute("eoloParks", eoloParkService.findEoloParks(null));
        model.addAttribute("username", user.getName());
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        return "PaginaPrincipal";
    }

    @PostMapping("/PaginaPrincipal")
    public String searchMainPage(@RequestParam(required = false) String city, Model model,
            HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();

        User user = userRepository.findByName(name).orElseThrow();

        model.addAttribute("eoloParks", eoloParkService.findEoloParks(city));
        model.addAttribute("username", user.getName());
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        return "PaginaPrincipal";
    }


    @GetMapping("/EoloPark")
    public String EoloPark( Model model) {
        model.addAttribute("eoloParks");
        return "EoloPark";
    }
    
    /*@GetMapping("/loginerror")
    public String Error( @RequestParam(required = false) String city, Model model) {
        model.addAttribute("eoloParks", eoloParkService.findEoloParks(city));
        return "login";
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

    @DeleteMapping("/EoloPark/delete/{id}")
    public ResponseEntity<EoloPark> deleteEoloPark(@PathVariable long id) {
        try {
            eoloParkService.deleteEoloPark(id);
            return new ResponseEntity<>(null, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Successfully")
    public String success(Model model) {

        model.addAttribute("succes");

        return "Successfully";
    }

    @PostMapping("/EoloPark/Manual")
    public String newPark(EoloPark eoloPark) {

        Aerogenerator aerogenerator = new Aerogenerator("null", 0, 0, 0, 0, 0 );
        Substation substation = new Substation("null", 0.0, 0.0,null);

        eoloParkService.save(eoloPark);
        aerogeneratorService.save(aerogenerator);
        substationService.save(substation);

        return "Successfully";
    }

    @GetMapping("/DetailsPark/{id}")
    public String infoEoloPark(@PathVariable Long id, Model model) {
        // Obtenemos la info del parque por su ID
        EoloPark eoloPark = eoloParkService.findEoloParkById(id);
        model.addAttribute("DetailsPark", eoloPark);
        model.addAttribute("hasSubstation", eoloPark.getSubstation() != null);
        model.addAttribute("hasAerogenerator", eoloPark.getAerogeneratorList() != null);

        //model.addAttribute("DetallesSubstation", eoloParkService.findSubstationByEoloParkId(id));
        //model.addAttribute("DetallesAerogenerator", aerogeneratorService.findAerogeneratorByEoloParkId(id));
         // Obtenemos la info del usuario que creo el parque
         //User createdByUser = userService.findUserById(eoloPark.getCreatedByUserId());
         //model.addAttribute("createdBy", createdByUser.getUsername());

        return "DetailsPark"; 
    }   


}
