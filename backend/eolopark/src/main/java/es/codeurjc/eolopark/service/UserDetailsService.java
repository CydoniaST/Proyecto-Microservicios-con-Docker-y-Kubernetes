package es.codeurjc.eolopark.service;

/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.codeurjc.eolopark.model.User;
import es.codeurjc.eolopark.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(String username, String password, String roles) {
        if (userRepository.findByName(username).isPresent()) {
            throw new RuntimeException("User already exists: " + username);
        }

        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, encodedPassword, roles);
        return userRepository.save(newUser);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByName(username);
    }
}

package es.codeurjc.eolopark.Servicios;
*/
import java.util.Optional;

import es.codeurjc.eolopark.model.Aerogenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.codeurjc.eolopark.model.User;
import es.codeurjc.eolopark.repository.UserRepository;

@Service
public class UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }



    public Optional<User> findByUsername(String username) {
        return userRepository.findByName(username);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

}