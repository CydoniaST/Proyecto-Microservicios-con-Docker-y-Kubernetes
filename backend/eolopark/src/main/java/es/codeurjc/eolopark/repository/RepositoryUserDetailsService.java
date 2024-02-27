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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.User;

@Repository
public class RepositoryUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

    @Value("${security.admin}")
    private String adminUsername;

    @Value("${security.adminEncodedPassword}")
    private String adminEncodedPassword;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
            if (username.equals(adminUsername)) {
                return User.builder()
                        .username(adminUsername)
                        .password(adminEncodedPassword)
                        .roles("ADMIN")
                        .build();
            }else{	 es.codeurjc.eolopark.model.User user = userRepository.findByName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		List<GrantedAuthority> roles = new ArrayList<>();
		for (String role : user.getRoles()) {
			roles.add(new SimpleGrantedAuthority("ROLE_" + role));
		}

        
        

		return new org.springframework.security.core.userdetails.User(user.getName(),
				user.getEncodedPassword(), roles);}
	

	}
}