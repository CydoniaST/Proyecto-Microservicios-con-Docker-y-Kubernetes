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

      @PostConstruct    //asks two users by default
    private void initDatabase() {
        // logic that saves in the database
        userRepository.save(new User("sandra", passwordEncoder.encode("password1"), "ADMIN"));
        userRepository.save(new User("maria", passwordEncoder.encode("password2"), "USER"));
        }
    

    //saved users in the database
    public void saveUser(String username, String password, String roles) {
      User user = new User(username, passwordEncoder.encode(password), roles);
      userRepository.save(user);
  }
}
