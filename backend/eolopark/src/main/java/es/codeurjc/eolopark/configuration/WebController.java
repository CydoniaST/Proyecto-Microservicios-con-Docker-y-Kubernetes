package es.codeurjc.eolopark.configuration;

import es.codeurjc.eolopark.model.EoloPark;
import es.codeurjc.eolopark.model.User;
import es.codeurjc.eolopark.repository.UserRepository;
import es.codeurjc.eolopark.service.EoloParkService;
import es.codeurjc.eolopark.service.UserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    public String admin(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/admin/user/{id}")
    public String userDetails(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        // Aquí puedes agregar más detalles si es necesario
        return "InfoUser"; // Nombre de la vista HTML para los detalles del usuario
    }

    @GetMapping("/admin/makeUserPremium/{id}")
    public String makeUserPremium(Model model, @PathVariable long id, RedirectAttributes redirectAttributes, Pageable pageable) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            int sizeParks = user.getEoloParks().size();
            if(user.isPremium() && sizeParks>=5){
                model.addAttribute("errorPremium","Este usuario tiene más de 5 parques. No puedes quitarle el premium aún");
                model.addAttribute("isPremium", user.isPremium());
                model.addAttribute("succesPremiumChange", false);
                model.addAttribute("user", user);
                return "premiumChanged";
            }
            user.setPremium(!user.isPremium());
            userRepository.save(user);
            model.addAttribute("user", user);
            model.addAttribute("succesPremiumChange",true);

            redirectAttributes.addFlashAttribute("successMessage", "User has been changed premium status successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
        }
        return "premiumChanged"; //+ userOpt.get().getId();

    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }





}
