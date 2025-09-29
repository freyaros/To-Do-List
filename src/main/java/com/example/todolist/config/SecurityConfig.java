package com.example.todolist.config;

import com.example.todolist.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Public URLs: allow access to registration and static assets (CSS, images)
                .requestMatchers("/signup", "/login", "/css/**", "/images/**").permitAll() 
                
                // All other URLs require authentication
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Use a custom login page
                .defaultSuccessUrl("/", true) // Redirect to home on successful login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Disable CSRF for simplicity in this example

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use BCrypt for secure password hashing
        return new BCryptPasswordEncoder(); 
    }
}