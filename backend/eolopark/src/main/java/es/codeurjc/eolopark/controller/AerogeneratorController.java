package es.codeurjc.eolopark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import es.codeurjc.eolopark.model.Aerogenerator;
import es.codeurjc.eolopark.model.EoloPark;
import es.codeurjc.eolopark.repository.AerogeneratorRepository;
import es.codeurjc.eolopark.repository.EoloParkRepository;
import es.codeurjc.eolopark.service.AerogeneratorService;

@Controller
public class AerogeneratorController {

    @Autowired
    AerogeneratorService aerogeneratorService;

    @Autowired
    AerogeneratorRepository aerogeneratorRepository;

    @Autowired
    EoloParkRepository eoloParkRepository;

    /*
     * @GetMapping("/DetallesPark/{id}")
     * public String infoEoloPark(@PathVariable Long id, Model model) {
     * // Obtenemos la info del parque por su ID
     * 
     * 
     * model.addAttribute("DetallesPark",
     * aerogeneratorService.findAerogeneratorById(id));
     * 
     * // Obtenemos la info del usuario que creo el parque
     * //User createdByUser =
     * userService.findUserById(eoloPark.getCreatedByUserId());
     * //model.addAttribute("createdBy", createdByUser.getUsername());
     * 
     * return "DetallesPark";
     * }
     */

    @PostMapping("/NewAerogenerator/saveNewAerogenerator/{id}")
    public String saveNewAerogenerator(@ModelAttribute Aerogenerator aerogenerator, @PathVariable Long id,
            Model model) {

        EoloPark eoloPark = eoloParkRepository.findById(id).orElse(null);

        if (eoloPark == null) {
            model.addAttribute("error", "No se encuentra este parque");
            return "redirect:/"; 
        }

        aerogenerator.setEoloPark(eoloPark);

        aerogeneratorRepository.save(aerogenerator);

        return "redirect:/DetailsPark/" + id;
    }

    @GetMapping("/NewAerogenerator/saveNewAerogenerator/{id}")
    public String showNewAerogeneratorForm(@PathVariable Long id, Model model) {
        model.addAttribute("parkId", id);
        return "NewAerogenerator";
    }

    @GetMapping("/EditAerogenerator/save/{id_aerogenerator}")
    public String editAerogeneratorView(@PathVariable Long id_aerogenerator, Model model) {

        Aerogenerator aerogenerator = aerogeneratorService.findAerogeneratorById(id_aerogenerator).orElse(null);

        model.addAttribute("parkId", aerogenerator.getEoloPark().getId());
        model.addAttribute(aerogenerator);
        return "EditAerogenerator";
    }

    @PostMapping("/EditAerogenerator/save/{id_aerogenerator}")
    public String editAerogenerator(@ModelAttribute Aerogenerator updatedAerogenerator,
            @PathVariable Long id_aerogenerator, Model model) {

        Aerogenerator aerogenerator = aerogeneratorService.findAerogeneratorById(id_aerogenerator).orElse(null);

        if (aerogenerator != null) {
            aerogenerator.setLatitude(updatedAerogenerator.getLatitude());
            aerogenerator.setLongitude(updatedAerogenerator.getLongitude());
            aerogenerator.setBladeLength(updatedAerogenerator.getBladeLength());
            aerogenerator.setHeight(updatedAerogenerator.getHeight());
            aerogenerator.setPower(updatedAerogenerator.getPower());

            aerogeneratorRepository.saveAndFlush(aerogenerator);

            eoloParkRepository.saveAndFlush(aerogenerator.getEoloPark());
        }

        return "redirect:/DetailsPark/" + aerogenerator.getEoloPark().getId();
    }

}