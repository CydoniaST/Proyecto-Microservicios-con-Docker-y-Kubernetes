package es.codeurjc.eolopark.controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    public class GreetingController {

        public String greeting(Model model){
            return "index";
        }

    }



