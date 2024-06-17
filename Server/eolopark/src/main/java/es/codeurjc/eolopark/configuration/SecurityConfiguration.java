package es.codeurjc.eolopark.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.codeurjc.eolopark.configuration.jwt.UnauthorizedHandlerJwt;
import es.codeurjc.eolopark.configuration.jwt.JwtRequestFilter;

import es.codeurjc.eolopark.repository.RepositoryUserDetailsService;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public RepositoryUserDetailsService userDetailService;

    @Autowired
    private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
                .securityMatcher("/api/**")
                .exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));

        http
                .authorizeHttpRequests(authorize -> authorize
                        // PRIVATE ENDPOINTS
                        .requestMatchers(HttpMethod.POST, "/api/**").permitAll()//hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").permitAll()//hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()//hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").permitAll()//hasAnyRole("USER","ADMIN")
                        // PUBLIC ENDPOINTS
                        .anyRequest().permitAll()
                );

        // Disable Form login Authentication
        http.formLogin(formLogin -> formLogin.disable());

        // Disable CSRF protection (it is difficult to implement in REST APIs)
        http.csrf(csrf -> csrf.disable());

        // Disable Basic Authentication
        http.httpBasic(httpBasic -> httpBasic.disable());

        // Stateless session
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add JWT Token filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
                .authorizeHttpRequests(authorize -> authorize
                        // PUBLIC PAGES
                        .requestMatchers("/index").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/favicon.ico").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/**").permitAll()
                        .requestMatchers("/index.html").permitAll()
                        .requestMatchers("/client.js").permitAll()
                        .requestMatchers("/notifications").permitAll()
                        .requestMatchers("/creation").permitAll()
                        .requestMatchers("/park-creation-progress").permitAll()
                        .requestMatchers("/report-updater.js").permitAll()
                        .requestMatchers("/create-park").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()

                        // PRIVATE PAGES
                        .requestMatchers("/DetailsPark/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/MainPage").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/EoloPark").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/EoloPark/Automatic").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/EoloPark/Manual").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/EoloPark/delete/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/Successfully").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/EditEoloPark/Edit/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/NewAerogenerator/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/EditAerogenerator/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("EditAerogenerator/delete/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/NewSubstation/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/EditSubstation/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("Substation/").hasAnyRole("USER","ADMIN")
                        .requestMatchers("Substation/delete/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/private").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .requestMatchers("admin/makeUserPremium/{{user.id}}").hasAnyRole("ADMIN")
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/loginerror")
                        .defaultSuccessUrl("/MainPage")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .httpBasic(withDefaults());

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
