package es.codeurjc.eolopark.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.eolopark.model.Aerogenerator;
import es.codeurjc.eolopark.model.Cities;
import es.codeurjc.eolopark.model.EoloPark;
import es.codeurjc.eolopark.model.Substation;
import es.codeurjc.eolopark.model.TerrainType;
import es.codeurjc.eolopark.model.User;
import es.codeurjc.eolopark.repository.EoloParkRepository;
import es.codeurjc.eolopark.repository.UserRepository;
import es.codeurjc.eolopark.service.AerogeneratorService;
import es.codeurjc.eolopark.service.CitiesService;
import es.codeurjc.eolopark.service.EoloParkService;
import es.codeurjc.eolopark.service.SubstationService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;



@Controller
public class EoloParkController {

    @Autowired
    EoloParkService eoloParkService;

    @Autowired
    AerogeneratorService aerogeneratorService;

    @Autowired
    SubstationService substationService;

    @Autowired
    CitiesService citiesService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EoloParkRepository eoloParkRepository;

    private List<Aerogenerator> aerogeneratorList = new ArrayList<>();

    @PostConstruct
    public void init() {

        User sandra = userRepository.findByName("sandra").get();
        EoloPark ep1 = new EoloPark("Miraflores", "Madrid", 0, 0, 0, TerrainType.DESERT, sandra);
        EoloPark ep2 = new EoloPark("Ciudad Lineal","Barcelona", 0, 0, 0, TerrainType.MOUNTAIN, sandra);


        eoloParkRepository.save(ep1);
        eoloParkRepository.save(ep2);

        Aerogenerator a1 = new Aerogenerator("1", 12.1243, 12.43422, 23.2, 323.2, 123.3);
        Aerogenerator a2 = new Aerogenerator("1", 23.233, 65.543, 50.0, 654.1, 192.3);

        a1.setEoloPark(ep2);
        a2.setEoloPark(ep1);

        aerogeneratorService.save(a1);
        aerogeneratorService.save(a2);

        User maria = userRepository.findByName("maria").get(); //test a premium user
        maria.setPremium(true);
        userRepository.save(maria);
        // EoloPark ep3 = new EoloPark("Joselu", "Madrid", 0, 0, 0, TerrainType.DESERT, maria);
        // EoloPark ep4 = new EoloPark("Joseluis","Barcelona", 0, 0, 0, TerrainType.MOUNTAIN, maria);
        // EoloPark ep5 = new EoloPark("Pepe", "Madrid", 0, 0, 0, TerrainType.DESERT, maria);
        // EoloPark ep6 = new EoloPark("Antonio","Barcelona", 0, 0, 0, TerrainType.MOUNTAIN, maria);
        // EoloPark ep7 = new EoloPark("Almendra","Barcelona", 0, 0, 0, TerrainType.MOUNTAIN, maria);
        // EoloPark ep8 = new EoloPark("Macaco","Barcelona", 0, 0, 0, TerrainType.MOUNTAIN, maria);
        // eoloParkRepository.save(ep3);
        // eoloParkRepository.save(ep4);
        // eoloParkRepository.save(ep5);
        // eoloParkRepository.save(ep6);
        // eoloParkRepository.save(ep7);
        // eoloParkRepository.save(ep8);

        Cities c1 = new Cities("A Coruña", "A Coruña", 7.2, 43.37, -8.39, 24604, 21);
        Cities c2 = new Cities("Albacete", "Albacete", 6.9, 38.99, -1.85, 17047, 681);
        Cities c3 = new Cities("Alicante", "Alicante", 7.5, 38.34, -0.48, 33441, 5);
        Cities c4 = new Cities("Almeria", "Almeria", 8, 36.83, -2.46, 19001, 16);
        Cities c5 = new Cities("Bilbao", "Vizcaya", 7.7, 43.25, -2.92, 35318, 6);

        citiesService.save(c1);
        citiesService.save(c2);
        citiesService.save(c3);
        citiesService.save(c4);
        citiesService.save(c5);


    }


    @GetMapping("/EoloPark")
    public String EoloPark( Model model) {
        model.addAttribute("eoloParks");
        return "EoloPark";
    }


    @PostMapping("/EditEoloPark/Edit/{id}")
    public String saveEoloPark(@PathVariable Long id, @ModelAttribute("eoloPark") EoloPark updatedEoloPark) {
        EoloPark existingEoloPark = eoloParkService.findEoloParkById(id);
        // Actualizamos los atributos del parque existente con los valores del formulario
        existingEoloPark.setName(updatedEoloPark.getName());
        existingEoloPark.setCity(updatedEoloPark.getCity());
        existingEoloPark.setLatitude(updatedEoloPark.getLatitude());
        existingEoloPark.setLongitude(updatedEoloPark.getLongitude());
        existingEoloPark.setArea(updatedEoloPark.getArea());
        existingEoloPark.setTerrainType(updatedEoloPark.getTerrainType());
        // Guardamos los cambios en la bbdd
        eoloParkService.save(existingEoloPark);

        return "editedPark";
    }

    @GetMapping("/EditEoloPark/Edit/{id}")
    public String editEoloPark(Model model,@PathVariable Long id) {
        // Obtenemos el parque existente por su ID
        EoloPark existingEoloPark = eoloParkService.findEoloParkById(id);
        model.addAttribute("existingEoloPark", existingEoloPark);
        return "EditEoloPark";
    }
    

    @GetMapping("/EoloPark/delete/{id}")
    public String deleteEoloPark(Model model, @PathVariable long id) {
        EoloPark eoloPark = eoloParkService.findEoloParkById(id);
        if (eoloPark != null) {
            eoloParkService.deleteEoloPark(id);
            model.addAttribute("eoloPark", eoloPark); //getClass()??
        }
        return "deletedPark";
    }

    @GetMapping("/Successfully")
    public String success(Model model) {

        model.addAttribute("succes");

        return "Successfully";
    }

    @PostMapping("/EoloPark/Automatic")
    public String newAutomatic(EoloPark eoloPark, Model model,HttpServletRequest request) {

        String name = request.getUserPrincipal().getName();
        User user = userRepository.findByName(name).orElseThrow();
        if(!user.isPremium()){
            if(user.getEoloParks().size()>=5) {
                model.addAttribute("errorCreate","Has alcanzado tu máximo de parques (5)");
                return "EoloPark";
            }
        }
        eoloPark = eoloParkService.newAutomaticEoloPark(eoloPark.getName(), eoloPark.getArea(),user);

        eoloParkService.save(eoloPark);
        eoloParkService.setEoloParkOwner(eoloPark.getId(),user);
        eoloParkService.save(eoloPark);
        return "Successfully";
    }


    @PostMapping("/EoloPark/Manual")
    public String newPark(EoloPark eoloPark, Model model, HttpServletRequest request) {
        //int i = 0;
        String name = request.getUserPrincipal().getName();
        User user = userRepository.findByName(name).orElseThrow();

        if(!user.isPremium()){
            if(user.getEoloParks().size()>=5) {
                model.addAttribute("errorCreate","Has alcanzado tu máximo de parques (5)");
                return "EoloPark";
            }
        }

        if (eoloParkRepository.findByName(eoloPark.getName()).isPresent()) {
            model.addAttribute("errorName", "Ya existe un parque con ese nombre");
            return "EoloPark";
        }

        // Si no existe, continuar con la creación del parque
        Aerogenerator aerogenerator = new Aerogenerator("null", 0, 0, 0, 0, 0 );
        Substation substation = new Substation("null", 0.0, 0.0,null);

        eoloParkService.save(eoloPark);
        eoloParkService.setEoloParkOwner(eoloPark.getId(),user);
        eoloParkService.save(eoloPark);
        aerogeneratorService.save(aerogenerator);
        substationService.save(substation);

        return "Successfully";
    }

    @GetMapping("/DetailsPark/{eolo_id}")
    public String infoEoloPark(@PathVariable Long eolo_id, Model model, HttpServletRequest request) {

        EoloPark eoloPark = eoloParkService.findEoloParkById(eolo_id);

        model.addAttribute("DetailsPark", eoloPark);
        model.addAttribute("hasSubstation", eoloPark.getSubstation() != null);
        model.addAttribute("hasAerogenerator", eoloPark.getAerogeneratorList() != null);

        return "DetailsPark";
    }


}
