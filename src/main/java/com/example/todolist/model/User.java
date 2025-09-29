package com.example.todolist.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users") // Use 'users' to avoid conflicts with 'user' keyword in some DBs
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password; // Will store the HASHED password

    // --- UserDetails Implementation (Required by Spring Security) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For simplicity, all users get the 'USER' role
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    
    
 // Inside the User class:

 // ... (other methods)

     // --- UserDetails Implementation (Explicit Getters) ---

     @Override
     public String getPassword() {
         return password;
     }

     @Override
     public String getUsername() {
         return username;
     }

     // You should also explicitly add this getter if you see similar errors for the ID
     public Long getId() {
         return id;
     }
     
  // 1. ADD THIS EXPLICIT NO-ARGUMENT CONSTRUCTOR (REQUIRED BY JPA)
     public User() {
         // Default constructor is mandatory for Hibernate to instantiate the User object
     }

     // 2. KEEP YOUR CUSTOM CONSTRUCTOR FOR REGISTRATION
     public User(String username, String password) {
         this.username = username;
         this.password = password;
     }

 // ... (rest of the class)
}