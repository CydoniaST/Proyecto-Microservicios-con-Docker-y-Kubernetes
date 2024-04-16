package es.codeurjc.eolopark.apiRest;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import es.codeurjc.eolopark.configuration.WebController;
import es.codeurjc.eolopark.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.codeurjc.eolopark.repository.AerogeneratorRepository;
import es.codeurjc.eolopark.repository.EoloParkRepository;
import es.codeurjc.eolopark.repository.SubstationRepository;
import es.codeurjc.eolopark.repository.UserRepository;
import es.codeurjc.eolopark.service.AerogeneratorService;
import es.codeurjc.eolopark.service.EoloParkService;
import es.codeurjc.eolopark.service.SubstationService;
import es.codeurjc.eolopark.service.UserDetailsService;


@RestController
@RequestMapping("/api")
public class ApiController {

    private static final int PAGE_SIZE = 5;
    @Autowired
    private EoloParkRepository eoloParksrepository;

    @Autowired
    private SubstationRepository subestationrepository;

    @Autowired
    private AerogeneratorRepository aerogeneratorRepository;

    @Autowired
    private UserRepository userrepository;
    @Autowired
    private EoloParkService eoloParkService;

    @Autowired
    private SubstationService substationService;
    @Autowired
    private AerogeneratorService aerogeneratorService;

    @Autowired
    private UserDetailsService userService;

    @GetMapping("/eolopark")
    public ResponseEntity<Page<EoloPark>> getEoloparks(@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<EoloPark> eoloparksPage = eoloParkService.getAllEoloParks(PageRequest.of(page, PAGE_SIZE));
        return ResponseEntity.ok(eoloparksPage);
    }

    @GetMapping("/eolopark/{id}")
    public ResponseEntity<EoloPark> getEolopark(@PathVariable long id) {
        Optional<EoloPark> eolopark = eoloParksrepository.findById(id);
        return eolopark.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/eolopark")
    public ResponseEntity<EoloPark> createEolopark(@RequestBody EoloPark eolopark) {
        EoloPark savedEolopark = eoloParksrepository.save(eolopark);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedEolopark.getId()).toUri();
    return ResponseEntity.created(location).body(savedEolopark);
    }

    @PostMapping("/eolopark/automatic")
    public ResponseEntity<EoloPark> createEolopark(@RequestBody AutomaticPark requestBody) {
        try {
            String cityName = requestBody.getCity();
            double area = requestBody.getArea();
            Long owner_id =  requestBody.getOwner();
            User owner = userService.findUserById(owner_id);


            EoloPark automaticEoloPark = eoloParkService.newAutomaticEoloPark(cityName, area,owner);

            //eoloParkService.setEoloParkOwner(automaticEoloPark.getId(),owner);

            eoloParkService.setOwner(automaticEoloPark, owner);


            eoloParkService.save(automaticEoloPark);
            eoloParkService.save(automaticEoloPark);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(automaticEoloPark.getId()).toUri();
            return ResponseEntity.created(location).body(automaticEoloPark);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/eolopark/{id}")
    public ResponseEntity<EoloPark> updateEolopark(@PathVariable long id, @RequestBody EoloPark newEolopark) {
        if (eoloParksrepository.existsById(id)) {
            newEolopark.setId(id);
            EoloPark updatedEolopark = eoloParksrepository.save(newEolopark);
            return ResponseEntity.ok(updatedEolopark);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eolopark/{id}")
    public ResponseEntity<EoloPark> deleteEolopark(@PathVariable long id) {
        Optional<EoloPark> eolopark = eoloParksrepository.findById(id);
        if (eolopark.isPresent()) {
            eoloParksrepository.deleteById(id);
            return ResponseEntity.ok(eolopark.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/substation")
    public ResponseEntity<Page<Substation>> getSubstationes(@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<Substation> substationesPage = substationService.getAllSubstations(PageRequest.of(page, PAGE_SIZE));
        return ResponseEntity.ok(substationesPage);
    }

    @GetMapping("/substation/{id}")
    public ResponseEntity<Substation> getSubstation(@PathVariable long id) {
        Optional<Substation> substation = subestationrepository.findById(id);
        return substation.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/substation")
    public ResponseEntity<Substation> createSubstation(@RequestBody Substation substation) {

        EoloPark park = eoloParkService.findEoloParkById(substation.getId());

        substation.setEoloPark(park);

        park.setSubstation(substation);
       

        park = eoloParkService.save(park);

        Substation substation2 = substationService.save(substation);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(substation2.getId()).toUri();

        return ResponseEntity.created(location).body(substation2);
    }

    @PutMapping("/substation/{id}")
    public ResponseEntity<Substation> updateSubstation(@PathVariable long id, @RequestBody Substation newSubstation) {
        if (subestationrepository.existsById(id)) {
            newSubstation.setId(id);
            Substation updatedSubstation = subestationrepository.save(newSubstation);
            return ResponseEntity.ok(updatedSubstation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/substation/{id}")
    public ResponseEntity<Substation> deleteSubstation(@PathVariable long id) {
        Optional<Substation> substationOptional = subestationrepository.findById(id);
        if (substationOptional.isPresent()) {
            Substation substation = substationOptional.get();

            List<EoloPark> eoloParks = eoloParksrepository.findAll();

            for (EoloPark eoloPark : eoloParks) {
                if (eoloPark.getSubstation() != null && eoloPark.getSubstation().getId() == id) {
                    eoloPark.setSubstation(null);
                    eoloParksrepository.save(eoloPark);
                }
            }

            subestationrepository.deleteById(id);

            return ResponseEntity.ok(substation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/aerogenerator")
    public ResponseEntity<Page<Aerogenerator>> getAerogenerators(@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<Aerogenerator> aerogeneratorsPage = aerogeneratorService.getAllAerogenerators(PageRequest.of(page, PAGE_SIZE));
        return ResponseEntity.ok(aerogeneratorsPage);
    }

    @GetMapping("/aerogenerator/{id}")
    public ResponseEntity<Aerogenerator> getaerogenerator(@PathVariable long id) {
        Optional<Aerogenerator> aerogenerator = aerogeneratorRepository.findById(id);
        return aerogenerator.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/aerogenerator")
    public ResponseEntity<Aerogenerator> createAerogenerator(@RequestBody Aerogenerator aerogenerator, @PathVariable long id) {
        
        EoloPark park = eoloParkService.findEoloParkById(id);

        aerogenerator.setEoloPark(park);

        park.setAerogenerator(aerogenerator);

        park = eoloParkService.save(park);

        Aerogenerator savedAerogenerator = aerogeneratorRepository.save(aerogenerator);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedAerogenerator.getTrueId()).toUri();
        return ResponseEntity.created(location).body(savedAerogenerator);
    }

    @PutMapping("/aerogenerator/{id}")
    public ResponseEntity<Aerogenerator> updateAerogenerator(@PathVariable long id, @RequestBody Aerogenerator newaerogenerator) {
        if (aerogeneratorRepository.existsById(id)) {
            newaerogenerator.setId(String.valueOf(id));
            Aerogenerator updatedaerogenerator = aerogeneratorRepository.save(newaerogenerator);
            return ResponseEntity.ok(updatedaerogenerator);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/aerogenerator/{id}")
    public ResponseEntity<Aerogenerator> deleteaerogenerator(@PathVariable long id) {
        Optional<Aerogenerator> aerogenerator = aerogeneratorRepository.findById(id);
        if (aerogenerator.isPresent()) {
            aerogeneratorRepository.deleteById(id);
            return ResponseEntity.ok(aerogenerator.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}/user")
    public ResponseEntity<Page<User>> getUsers(@RequestParam(name = "page", defaultValue = "0") int page,@PathVariable long id ) {
        
        User userNew = userService.findUserById(id);

        List<String> roles = userNew.getRoles();
        if(roles.contains("ADMIN")){

            Page<User> usersPage = userService.getAllUsers(PageRequest.of(page, PAGE_SIZE));
            return ResponseEntity.ok(usersPage);

        }else{
            throw new RuntimeException("No tienes permisos de administrador.");
        }
        
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {

        Optional<User> user = userrepository.findById(id);
        
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/user")
    public ResponseEntity<User> createUser(@RequestBody User user,@PathVariable long id ) {

        User userNew = userService.findUserById(id);

        List<String> roles = userNew.getRoles();

        if(roles.contains("ADMIN")){
            User savedUser = userrepository.save(user);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedUser.getId()).toUri();
            return ResponseEntity.created(location).body(savedUser);
        }else{
            throw new RuntimeException("No tienes permisos de administrador.");
        }
        

        
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User newUser) {
        if (userrepository.existsById(id)) {
            newUser.setId(id);
            User updatedUser = userrepository.save(newUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        Optional<User> user = userrepository.findById(id);
        if (user.isPresent()) {
            userrepository.deleteById(id);
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

