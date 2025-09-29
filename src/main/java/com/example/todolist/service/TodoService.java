package com.example.todolist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todolist.model.TodoItem;
import com.example.todolist.model.User;
import com.example.todolist.repository.TodoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // --- CRUD Operations filtered by User ---

    public List<TodoItem> getAllTasks(User user) {
        return todoRepository.findByUser(user);
    }

    public Optional<TodoItem> getTaskById(Long id, User user) {
        return todoRepository.findByIdAndUser(id, user);
    }

    // Save a task (new or update), enforcing user ownership
    public TodoItem saveTask(TodoItem task, User user) {
        task.setUser(user); // Ensure correct ownership
        return todoRepository.save(task);
    }

    public void deleteTask(Long id, User user) {
        todoRepository.deleteByIdAndUser(id, user);
    }

    public Optional<TodoItem> updateTask(Long id, TodoItem updatedItem, User user) {
        return todoRepository.findByIdAndUser(id, user).map(existingItem -> {
            existingItem.setTask(updatedItem.getTask());
            existingItem.setCompleted(updatedItem.getCompleted() != null ? updatedItem.getCompleted() : false);
            return saveTask(existingItem, user);
        });
    }
}
