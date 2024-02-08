package com.eolopark;

import eolopark.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AerogeneratorController {

    private final EoloParkService eoloParkService;

    public AerogeneratorController(EoloParkService eoloParkService) {
        this.eoloParkService = eoloParkService;
    }

    @GetMapping("/")
    public String listEoloParks(@RequestParam(required = false) String city, Model model) {
        model.addAttribute("eoloParks", eoloParkService.findEoloParks(city));
        return "index";
    }
}