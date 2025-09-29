package com.example.todolist.repository;

import com.example.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom method required by Spring Security to load user by username
    Optional<User> findByUsername(String username);
}