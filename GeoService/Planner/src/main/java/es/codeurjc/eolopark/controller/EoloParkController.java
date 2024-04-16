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

        Cities c1 = new Cities("A Coruña", "A Coruña", 7.2, 43.37012643, -8.39114853, 246047, 21);
        Cities c2 = new Cities("Albacete", "Albacete", 6.9, 38.99588053, -1.85574745, 170475, 681);
        Cities c3 = new Cities("Alicante", "Alicante", 7.5, 38.34548705, -0.4831832, 334418, 5);
        Cities c4 = new Cities("Almería", "Almería", 8, 36.83892362, -2.46413188, 190013, 16);
        Cities c5 = new Cities("Ávila", "Ávila", 6.7, 40.65586958, -4.69771277, 58245, 1131);
        Cities c6 = new Cities("Badajoz", "Badajoz", 7, 38.87874339, -6.97099704, 150376, 182);
        Cities c7 = new Cities("Barcelona", "Barcelona", 7.4, 41.38424664, 2.17634927, 1619337, 13);
        Cities c8 = new Cities("Bilbao", "Vizcaya", 7.7, 43.25721957, -2.92390606, 353187, 6);
        Cities c9 = new Cities("Burgos", "Burgos", 7.6, 42.34113004, -3.70419805, 178574, 859);
        Cities c10 = new Cities("Cáceres", "Cáceres", 7.2, 39.47316762, -6.37121092, 94179, 457);
        Cities c11 = new Cities("Cádiz", "Cádiz", 8.1, 36.52171152, -6.28414575, 125826, 13);
        Cities c12 = new Cities("Castellón de la Plana", "Castellón", 7.1, 39.98640809, -0.03688142, 180690, 27);
        Cities c13 = new Cities("Ceuta", "Ceuta", 7.4, 35.88810209, -5.30675127, 80579, 27);
        Cities c14 = new Cities("Ciudad Real", "Ciudad Real", 6.6, 38.98651781, -3.93131981, 74345, 625);
        Cities c15 = new Cities("Córdoba", "Córdoba", 7.1, 37.87954225, -4.78032455, 328547, 106);
        Cities c16 = new Cities("Cuenca", "Cuenca", 6.8, 40.07653762, -2.13152306, 56189, 997);
        Cities c17 = new Cities("Girona", "Girona", 7.5, 41.98186075, 2.82411899, 96236, 69);
        Cities c18 = new Cities("Granada", "Granada", 7.5, 37.17641932, -3.60001883, 239154, 684);
        Cities c19 = new Cities("Guadalajara", "Guadalajara", 6.9, 40.63435548, -3.16210273, 83789, 685);
        Cities c20 = new Cities("Huelva", "Huelva", 7.9, 37.26004113, -6.95040588, 149310, 24);
        Cities c21 = new Cities("Huesca", "Huesca", 8.1, 42.14062739, -0.40842276, 52347, 483);
        Cities c22 = new Cities("Jaén", "Jaén", 7.3, 37.7651913, -3.7903594, 116790, 570);
        Cities c23 = new Cities("Las Palmas de Gran Canaria", "Las Palmas", 8.2, 28.09937855, -15.41336841, 383308, 6);
        Cities c24 = new Cities("León", "León", 7.4, 42.59912097, -5.56707631, 134012, 837);
        Cities c25 = new Cities("Lleida", "Lleida", 7.5, 41.61527355, 0.62061934, 137387, 167);
        Cities c26 = new Cities("Logroño", "La Rioja", 7.2, 42.46644945, -2.44565538, 152650, 384);
        Cities c27 = new Cities("Lugo", "Lugo", 7.3, 43.0091282, -7.55817392, 97635, 452);
        Cities c28 = new Cities("Madrid", "Madrid", 7.3, 40.40841191, -3.68760088, 3273049, 657);
        Cities c29 = new Cities("Málaga", "Málaga", 7.8, 36.72034267, -4.41997511, 568507, 8);
        Cities c30 = new Cities("Melilla", "Melilla", 7.3, 35.294731, -2.942281, 76034, 30);
        Cities c31 = new Cities("Murcia", "Murcia", 7.6, 37.98436361, -1.1285408, 441345, 42);
        Cities c32 = new Cities("Ourense", "Ourense", 6.5, 42.33654919, -7.86368375, 108673, 138);
        Cities c33 = new Cities("Oviedo", "Asturias", 7.5, 43.36232165, -5.84372206, 225155, 231);
        Cities c34 = new Cities("Palencia", "Palencia", 6.8, 42.0078373, -4.53460106, 82169, 734);
        Cities c35 = new Cities("Palma", "Islas Baleares", 7.9, 39.57114699, 2.65181698, 404681, 24);
        Cities c36 = new Cities("Pamplona", "Navarra", 7.4, 42.814102, -1.6451528, 197488, 450);
        Cities c37 = new Cities("Pontevedra", "Pontevedra", 6.6, 42.43381442, -8.64799018, 81981, 16);
        Cities c38 = new Cities("Salamanca", "Salamanca", 7.2, 40.96736822, -5.66538084, 154462, 798);
        Cities c39 = new Cities("San Sebastián", "Gipuzkoa", 7.6, 43.31717158, -1.98191785, 185506, 7);
        Cities c40 = new Cities("Santa Cruz de Tenerife", "Santa Cruz de Tenerife", 8.2, 28.46285408, -16.24720629, 222643, 1);
        Cities c41 = new Cities("Santander", "Cantabria", 7.4, 43.46297885, -3.80474784, 181589, 8);
        Cities c42 = new Cities("Segovia", "Segovia", 6.9, 40.9498703, -4.12524116, 55748, 1002);
        Cities c43 = new Cities("Sevilla", "Sevilla", 7.4, 37.38620512, -5.99251368, 704198, 11);
        Cities c44 = new Cities("Soria", "Soria", 6.7, 41.76327912, -2.46624798, 39838, 1061);
        Cities c45 = new Cities("Tarragona", "Tarragona", 7.2, 41.11910287, 1.2584219, 140184, 69);
        Cities c46 = new Cities("Teruel", "Teruel", 7, 40.34412951, -1.10927177, 35241, 915);
        Cities c47 = new Cities("Toledo", "Toledo", 7.1, 39.85715187, -4.02431421, 82489, 516);
        Cities c48 = new Cities("Valencia", "Valencia", 7.3, 39.47534441, -0.37565717, 809267, 16);
        Cities c49 = new Cities("Valladolid", "Valladolid", 7, 41.65232777, -4.72334924, 315522, 690);
        Cities c50 = new Cities("Vitoria", "Álava", 7.2, 42.85058789, -2.67275685, 238247, 539);
        Cities c51 = new Cities("Zamora", "Zamora", 6.8, 41.49913956, -5.75494831, 65998, 649);
        Cities c52 = new Cities("Zaragoza", "Zaragoza", 8.1, 41.65645655, -0.87928652, 675121, 208);



        citiesService.save(c1);
        citiesService.save(c2);
        citiesService.save(c3);
        citiesService.save(c4);
        citiesService.save(c5);
        citiesService.save(c6);
        citiesService.save(c7);
        citiesService.save(c8);
        citiesService.save(c9);
        citiesService.save(c10);
        citiesService.save(c11);
        citiesService.save(c12);
        citiesService.save(c13);
        citiesService.save(c14);
        citiesService.save(c15);
        citiesService.save(c16);
        citiesService.save(c17);
        citiesService.save(c18);
        citiesService.save(c19);
        citiesService.save(c20);
        citiesService.save(c21);
        citiesService.save(c22);
        citiesService.save(c23);
        citiesService.save(c24);
        citiesService.save(c25);
        citiesService.save(c26);
        citiesService.save(c27);
        citiesService.save(c28);
        citiesService.save(c29);
        citiesService.save(c30);
        citiesService.save(c31);
        citiesService.save(c32);
        citiesService.save(c33);
        citiesService.save(c34);
        citiesService.save(c35);
        citiesService.save(c36);
        citiesService.save(c37);
        citiesService.save(c38);
        citiesService.save(c39);
        citiesService.save(c40);
        citiesService.save(c41);
        citiesService.save(c42);
        citiesService.save(c43);
        citiesService.save(c44);
        citiesService.save(c45);
        citiesService.save(c46);
        citiesService.save(c47);
        citiesService.save(c48);
        citiesService.save(c49);
        citiesService.save(c50);
        citiesService.save(c51);
        citiesService.save(c52);



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
