package es.codeurjc.eolopark.configuration;

import es.codeurjc.eolopark.model.User;
import es.codeurjc.eolopark.repository.UserRepository;
import es.codeurjc.eolopark.service.UserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.desktop.UserSessionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class WebController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/loginerror")
    public String loginerror() {
        return "loginerror";
    }



    @GetMapping("/private")
    public String privatePage(Model model, HttpServletRequest request) {

        String name = request.getUserPrincipal().getName();

        User user = userRepository.findByName(name).orElseThrow();

        model.addAttribute("username", user.getName());
        model.addAttribute("admin", request.isUserInRole("ADMIN"));

        return "private";
    }


    @GetMapping("/register")
    public String register(){return "register";}

    @PostMapping("/register")
    public String nuevoUser(Model model, User user) {

        //model.addAttribute("user", user.getName());
        if (userRepository.findByName(user.getName()).isPresent()) {

            model.addAttribute("error", "El nombre de usuario ya está en uso");
            return "register";


        }
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);
        user.setEncodedPassword(passwordEncoder.encode(user.getEncodedPassword()));


        userRepository.save(user);
        

        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}
