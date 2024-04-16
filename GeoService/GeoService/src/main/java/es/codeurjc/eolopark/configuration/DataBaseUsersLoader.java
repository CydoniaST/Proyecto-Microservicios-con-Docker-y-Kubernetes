package es.codeurjc.eolopark.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.codeurjc.eolopark.model.User;
import es.codeurjc.eolopark.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Component
public class DataBaseUsersLoader {
  
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	    private PasswordEncoder passwordEncoder;

      @PostConstruct    //two user default
    private void initDatabase() {
        // add user in bbdd
        userRepository.save(new User("sandra", passwordEncoder.encode("pass"), "ADMIN"));
        userRepository.save(new User("maria", passwordEncoder.encode("password2"), "USER"));
        }
    

    //save users in bbdd
    public void saveUser(String username, String password, String roles) {
      User user = new User(username, passwordEncoder.encode(password), roles);
      userRepository.save(user);
  }
}
