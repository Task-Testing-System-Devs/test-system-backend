package testsystem.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import testsystem.backend.filter.JwtAuthFilter;

/**
 * Configuration class for the User Security in the application.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class UserSecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;

    /**
     * Returns an instance of UserCredsUserDetailsService.
     *
     * @return instance of UserCredsUserDetailsService.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserCredsUserDetailsService();
    }

    /**
     * Configures the SecurityFilterChain for the application.
     *
     * @param http HttpSecurity object to configure SecurityFilterChain.
     * @return SecurityFilterChain.
     * @throws Exception In case of configuration errors.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/auth/register",
                        "/api/auth/login",
                        "/api/contest/get-info"
                ).permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers(
                        "/api/profile/**",
                        "/api/grade/**",
                        "/api/solutions/**",
                        "/api/contest/**"
                )
                .authenticated().and()
                .authorizeHttpRequests().requestMatchers("/api/admin/add-teacher").permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .cors().and().build();
    }

    /**
     * Returns an instance of PasswordEncoder for encoding passwords.
     *
     * @return Instance of BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Returns an instance of AuthenticationManager.
     *
     * @param config AuthenticationConfiguration object.
     * @return Instance of AuthenticationManager.
     * @throws Exception In case of configuration errors.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Returns an instance of AuthenticationProvider with the UserDetailsService and PasswordEncoder.
     *
     * @return Instance of DaoAuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
