package es.codeurjc.eolopark.configuration;

import es.codeurjc.eolopark.repository.RepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private RepositoryUserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean

	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	@Order(1)
	public SecurityFilterChain webSecurityFilterChain(HttpSecurity https) throws Exception {
        https.authenticationProvider(authenticationProvider());

		https
				.authorizeHttpRequests(authorize -> authorize

                        // PUBLIC PAGES
						.requestMatchers("/index").permitAll()
						.requestMatchers("/register").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/favicon.ico").permitAll()

                        // PRIVATE PAGES
						.requestMatchers("/DetailsPark/**").hasAnyRole("USER","ADMIN")
						.requestMatchers("/PaginaPrincipal").hasAnyRole("USER", "ADMIN")
						.requestMatchers("/EoloPark").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/EoloPark/Automatic").hasAnyRole("USER","ADMIN")
						.requestMatchers("/EoloPark/Manual").hasAnyRole("USER","ADMIN")
						.requestMatchers("/EoloPark/delete/**").hasAnyRole("ADMIN")
                        .requestMatchers("/Successfully").hasAnyRole("USER","ADMIN")
						.requestMatchers("/EditEoloPark/Edit/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/private").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .requestMatchers("admin/makeUserPremium/{{user.id}}").hasAnyRole("ADMIN")
				)
				.formLogin(formLogin -> formLogin
						.loginPage("/login")
						.failureUrl("/loginerror")
						.defaultSuccessUrl("/PaginaPrincipal")
						.permitAll()
				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
						.permitAll()
				)
				.httpBasic(withDefaults());

		https.csrf(csrf -> csrf.disable());

		return https.build();

	}
	@Bean
	@Order(2)
	public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole("USER", "ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("USER", "ADMIN")
						.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole("USER", "ADMIN")

				)

				.httpBasic(withDefaults());
		//Disable Form login Authentication
		http.formLogin(formLogin -> formLogin.disable());
		// Disable CSRF protection (it is difficult to implement in REST APIs)
		http.csrf(csrf -> csrf.disable());
		// Enable Basic Authentication
		http.httpBasic(Customizer.withDefaults());
		// Stateless session
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();

	}
}
