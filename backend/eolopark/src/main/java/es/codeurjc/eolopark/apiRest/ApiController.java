package es.codeurjc.eolopark.apiRest;

import es.codeurjc.eolopark.model.Aerogenerator;
import es.codeurjc.eolopark.model.EoloPark;
import es.codeurjc.eolopark.model.Substation;
import es.codeurjc.eolopark.model.User;
import es.codeurjc.eolopark.repository.AerogeneratorRepository;
import es.codeurjc.eolopark.repository.EoloParkRepository;
import es.codeurjc.eolopark.repository.SubstationRepository;
import es.codeurjc.eolopark.repository.UserRepository;
import es.codeurjc.eolopark.service.AerogeneratorService;
import es.codeurjc.eolopark.service.EoloParkService;
import es.codeurjc.eolopark.service.SubstationService;
import es.codeurjc.eolopark.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;


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
    //para eolopark

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


    //para Subestacion
    @GetMapping("/subestation")
    public ResponseEntity<Page<Substation>> getSubestaciones(@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<Substation> subestacionesPage = substationService.getAllSubstations(PageRequest.of(page, PAGE_SIZE));
        return ResponseEntity.ok(subestacionesPage);
    }


    @GetMapping("/subestation/{id}")
    public ResponseEntity<Substation> getSubestacion(@PathVariable long id) {
        Optional<Substation> subestacion = subestationrepository.findById(id);
        return subestacion.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/subestation")
    public ResponseEntity<Substation> createSubestacion(@RequestBody Substation subestacion) {
        Substation savedSubestacion = subestationrepository.save(subestacion);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedSubestacion.getId()).toUri();
        return ResponseEntity.created(location).body(savedSubestacion);
    }

    @PutMapping("/subestation/{id}")
    public ResponseEntity<Substation> updateSubestacion(@PathVariable long id, @RequestBody Substation newSubestacion) {
        if (subestationrepository.existsById(id)) {
            newSubestacion.setId(id);
            Substation updatedSubestacion = subestationrepository.save(newSubestacion);
            return ResponseEntity.ok(updatedSubestacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/subestation/{id}")
    public ResponseEntity<Substation> deleteSubestacion(@PathVariable long id) {
        Optional<Substation> subestacion = subestationrepository.findById(id);
        if (subestacion.isPresent()) {
            subestationrepository.deleteById(id);
            return ResponseEntity.ok(subestacion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //  para Aerogenerador
    @GetMapping("/aerogerator")
    public ResponseEntity<Page<Aerogenerator>> getAerogenerators(@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<Aerogenerator> aerogeneratorsPage = aerogeneratorService.getAllAerogenerators(PageRequest.of(page, PAGE_SIZE));
        return ResponseEntity.ok(aerogeneratorsPage);
    }

    @GetMapping("/aerogerator/{id}")
    public ResponseEntity<Aerogenerator> getAerogenerador(@PathVariable long id) {
        Optional<Aerogenerator> aerogenerador = aerogeneratorRepository.findById(id);
        return aerogenerador.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/aerogerator")
    public ResponseEntity<Aerogenerator> createAerogenerador(@RequestBody Aerogenerator aerogenerador) {
        Aerogenerator savedAerogenerador = aerogeneratorRepository.save(aerogenerador);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedAerogenerador.getId()).toUri();
        return ResponseEntity.created(location).body(savedAerogenerador);
    }

    @PutMapping("/aerogerator/{id}")
    public ResponseEntity<Aerogenerator> updateAerogenerador(@PathVariable long id, @RequestBody Aerogenerator newAerogenerador) {
        if (aerogeneratorRepository.existsById(id)) {
            newAerogenerador.setId(String.valueOf(id));
            Aerogenerator updatedAerogenerador = aerogeneratorRepository.save(newAerogenerador);
            return ResponseEntity.ok(updatedAerogenerador);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/aerogerator/{id}")
    public ResponseEntity<Aerogenerator> deleteAerogenerador(@PathVariable long id) {
        Optional<Aerogenerator> aerogenerador = aerogeneratorRepository.findById(id);
        if (aerogenerador.isPresent()) {
            aerogeneratorRepository.deleteById(id);
            return ResponseEntity.ok(aerogenerador.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


   // para User
    @GetMapping("/user")
    public ResponseEntity<Page<User>> getUsers(@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<User> usersPage = userService.getAllUsers(PageRequest.of(page, PAGE_SIZE));
        return ResponseEntity.ok(usersPage);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        Optional<User> user = userrepository.findById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userrepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).body(savedUser);
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

