package com.example.todolist.service;

import com.example.todolist.model.User;
import com.example.todolist.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Required by UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Transactional
    public User registerNewUser(String username, String rawPassword) throws IllegalStateException {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("Username already exists.");
        }
        
        // Hash the password before saving!
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User newUser = new User(username, encodedPassword);
        return userRepository.save(newUser);
    }
}