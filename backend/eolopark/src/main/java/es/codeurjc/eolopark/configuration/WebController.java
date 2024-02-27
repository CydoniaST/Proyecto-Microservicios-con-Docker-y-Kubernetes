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

    @Autowired
    private EoloParkService eoloParkService;

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
    public String userDetails(@PathVariable Long id, @RequestParam(required = false) String city,
                              @PageableDefault(size = 3) Pageable pageable,
                              Model model) {
        //String name = userRepository.findById(id).get().getName();
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Page<EoloPark> eoloParkPage = eoloParkService.findEoloParksByOwnerId(user.getId(), pageable);

        model.addAttribute("city", city != null ? city : "");
        model.addAttribute("eoloParks", eoloParkPage.getContent());
        model.addAttribute("isPremium", user.isPremium());
        //model.addAttribute("username", user.getName());

        // Check pagination
        int currentPage = eoloParkPage.getNumber();
        model.addAttribute("currentPage", currentPage + 1); // Actual page


        //Add pagination buttons
        if (currentPage < eoloParkPage.getTotalPages() - 1) {
            int nextPage = currentPage + 1;
            model.addAttribute("hasNextPage", true);
            model.addAttribute("nextPage", nextPage);
        } else {
            model.addAttribute("hasNextPage", false);
        }

        if (currentPage > 0) {
            int previousPage = currentPage - 1;
            model.addAttribute("hasPreviousPage", true);
            model.addAttribute("previousPage", previousPage);
        } else {

            model.addAttribute("hasPreviousPage", false);
        }

        return "InfoUser";
    }

    @GetMapping("/admin/makeUserPremium/{id}")
    public String makeUserPremium(Model model, @PathVariable long id, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPremium(!user.isPremium());
            userRepository.save(user);
            model.addAttribute("user", user);
            redirectAttributes.addFlashAttribute("successMessage", "User has been changed premium status successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
        }
        return "premiumChanged"; //+ userOpt.get().getId();

    }




}
