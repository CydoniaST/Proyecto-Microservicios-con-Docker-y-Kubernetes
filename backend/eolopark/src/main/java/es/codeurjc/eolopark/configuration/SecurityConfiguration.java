package es.codeurjc.eolopark.configuration;

import es.codeurjc.eolopark.repository.RepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
	
    //esto en esta clase no sirve
	@Value("${security.admin}") //Para que lo lea desde el fichero propierties
    private String adminUsername;

    @Value("${security.adminEncodedPassword}")
    private String adminEncodedPassword;

	
	@Autowired
    public RepositoryUserDetailsService userDetailService; //inyeccion de independencias

	

	@Bean
	public PasswordEncoder passwordEncoder() {    //Declarar ese tipo de variable 
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity https) throws Exception {
		
		https.authenticationProvider(authenticationProvider());
		
		https
			.authorizeHttpRequests(authorize -> authorize
					//.requestMatchers("/login").permitAll()//clases publicas
                    .requestMatchers(("/index")).permitAll()
					.requestMatchers("/register").permitAll()
					//.requestMatchers("/Error").permitAll()
					.requestMatchers("/DetailsPark/**").permitAll()
					//.requestMatchers("/DetallesSubstation/**").permitAll()
					.requestMatchers("/PaginaPrincipal").hasAnyRole("USER","ADMIN")
					.requestMatchers("/EoloPark").permitAll()
					.requestMatchers("/").permitAll()
					.requestMatchers("/Successfully").permitAll()

					// PRIVATE PAGES
                    .requestMatchers("/private").hasAnyRole("USER","ADMIN")
                    .requestMatchers("/admin").hasAnyRole("ADMIN")
                    
                    //CAMBIAR ESTO A LAS PAGINAS NUESTRAS
					//.requestMatchers("/PaginaPrincipal").hasAnyRole("USER") 
                    //.requestMatchers("/InfoEoloPark").hasAnyRole("USER")  //privadas que solo hacen usuarios
					//.requestMatchers("/newPark/A").hasAnyRole("ADMIN")  -////privadas que solo hacen admin y a los admin hhay que hacerles tambien usuarios
			)
			.formLogin(formLogin -> formLogin
					.loginPage("/login")    //PAGINA LOGIN
					.failureUrl("/loginerror") //PAGINA ERROR
					.defaultSuccessUrl("/PaginaPrincipal")  // donde te lleva si el login es correcto
					.permitAll()
            )
			.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/")
					.permitAll()
			);
		
		// Disable CSRF at the moment HAY QUE HABILITARLO
		https.csrf(csrf -> csrf.disable());

		return https.build();
	}

}
