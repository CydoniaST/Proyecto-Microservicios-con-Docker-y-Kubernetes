package es.codeurjc.eolopark.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.User;

@Service
public class RepositoryUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

    @Value("${security.admin}") //Para que lo lea desde el fichero propierties
    private String adminUsername;

    @Value("${security.adminEncodedPassword}")
    private String adminEncodedPassword;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
            if (username.equals(adminUsername)) {  // se comprubea que el nombre que le pasas es el mismo que el del propities si lo es se crea
                return User.builder() // se crea cogiendolo del propiertir
                        .username(adminUsername) //variables del value
                        .password(adminEncodedPassword)
                        .roles("ADMIN") // le asignas que es el administrador
                        .build();
            }else{	 es.codeurjc.eolopark.model.User user = userRepository.findByName(username) //USER NUESTRO USUARIO
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		List<GrantedAuthority> roles = new ArrayList<>();     // Te devuelve los roles
		for (String role : user.getRoles()) {
			roles.add(new SimpleGrantedAuthority("ROLE_" + role));
		}

        
        

		return new org.springframework.security.core.userdetails.User(user.getName(),   //Guarda el usuario del else
				user.getEncodedPassword(), roles);}
	

	}
}