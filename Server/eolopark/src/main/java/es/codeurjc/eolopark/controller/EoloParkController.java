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

        User maria = userRepository.findByName("maria").get();
        EoloPark ep1 = new EoloPark("Miraflores", "Madrid", 0, 0, 0, TerrainType.DESERT, maria);
        EoloPark ep2 = new EoloPark("Ciudad Lineal","Barcelona", 0, 0, 0, TerrainType.MOUNTAIN, maria);
        EoloPark ep3 = new EoloPark();
        EoloPark ep4 = new EoloPark();


        eoloParkRepository.save(ep1);
        eoloParkRepository.save(ep2);
        eoloParkRepository.save(ep3);
        eoloParkRepository.save(ep4);



    }


    @GetMapping("/EoloPark")
    public String EoloPark( Model model) {
        model.addAttribute("eoloParks");
        return "EoloPark";
    }


    @PostMapping("/EditEoloPark/Edit/{id}")
    public String saveEoloPark(@PathVariable Long id, @ModelAttribute("eoloPark") EoloPark updatedEoloPark) {
        EoloPark existingEoloPark = eoloParkService.findEoloParkById(id);

        existingEoloPark.setName(updatedEoloPark.getName());
        existingEoloPark.setCity(updatedEoloPark.getCity());
        existingEoloPark.setLatitude(updatedEoloPark.getLatitude());
        existingEoloPark.setLongitude(updatedEoloPark.getLongitude());
        existingEoloPark.setArea(updatedEoloPark.getArea());
        existingEoloPark.setTerrainType(updatedEoloPark.getTerrainType());

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

        model.addAttribute("success");

        return "Successfully";
    }


    @GetMapping("/saveAutomatic/{parkId}")
    public String newAutomatic(@PathVariable Long parkId, HttpServletRequest request){
        EoloPark park = eoloParkRepository.findById(parkId).orElseThrow();
        String name = request.getUserPrincipal().getName();
        User user = userRepository.findByName(name).orElseThrow();
        park.setOwner(user);
        eoloParkRepository.save(park);

        return "Succesfully";
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
