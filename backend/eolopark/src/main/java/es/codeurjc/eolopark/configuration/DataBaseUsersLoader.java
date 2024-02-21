package es.codeurjc.eolopark.configuration;

import es.codeurjc.eolopark.model.User;
import es.codeurjc.eolopark.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataBaseUsersLoader {
  
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	    private PasswordEncoder passwordEncoder;

      @PostConstruct    //te pide que tenga dos usaurios por defecto
    private void initDatabase() {
        // Aquí puedes añadir la lógica para guardar usuarios en la base de datos
        userRepository.save(new User("sandra", passwordEncoder.encode("password1"), "USER"));
        userRepository.save(new User("maria", passwordEncoder.encode("password2"), "USER"));
        }
    

    //guarda lo usuarios en la base de datos 
    public void saveUser(String username, String password, String roles) {
      User user = new User(username, passwordEncoder.encode(password), roles); //hace el usuario es decir el objeto
      userRepository.save(user); // guarda el usuario en la base de datos
  }
}
