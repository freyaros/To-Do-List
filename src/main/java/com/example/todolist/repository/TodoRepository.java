package com.example.todolist.repository;

import com.example.todolist.model.TodoItem;
import com.example.todolist.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository provides methods like save(), findById(), findAll(), deleteById(), etc.
@Repository
public interface TodoRepository extends JpaRepository<TodoItem, Long> {
    // You can add custom query methods here if needed, but the basic ones are inherited.
	// Filter tasks by the currently logged-in user
    List<TodoItem> findByUser(User user);
    
    Optional<TodoItem> findByIdAndUser(Long id, User user);
    
    void deleteByIdAndUser(Long id, User user);
}