package com.example.todolist.controller;

import com.example.todolist.model.TodoItem;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController // Marks this class as a REST controller
@RequestMapping("/api/todos") // Base URL for all endpoints in this controller
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // GET /api/todos - Retrieve all tasks
    @GetMapping
    public List<TodoItem> getAllTodos() {
        return todoService.getAllTasks(null);
    }

    // GET /api/todos/{id} - Retrieve a single task by ID
    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodoById(@PathVariable Long id) {
        return todoService.getTaskById(id, null)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/todos - Create a new task
    @PostMapping
    public ResponseEntity<TodoItem> createTodo(@RequestBody TodoItem todoItem) {
        TodoItem savedItem = todoService.saveTask(todoItem, null);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    // PUT /api/todos/{id} - Update an existing task
    @PutMapping("/{id}")
    public ResponseEntity<TodoItem> updateTodo(@PathVariable Long id, @RequestBody TodoItem todoItemDetails) {
        return todoService.updateTask(id, todoItemDetails, null)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/todos/{id} - Delete a task
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Returns 204 No Content on successful deletion
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTask(id, null);
    }
}