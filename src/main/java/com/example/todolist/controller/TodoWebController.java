package com.example.todolist.controller;

import com.example.todolist.model.TodoItem;
import com.example.todolist.model.User;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // Use @Controller to render views (HTML)
@RequestMapping("/") // Maps to the root URL (http://localhost:8080/)
public class TodoWebController {

    private final TodoService todoService;

    @Autowired
    public TodoWebController(TodoService todoService) {
        this.todoService = todoService;
    }

    // --- VIEW ALL TASKS (Index Page) ---
    @GetMapping
    public String listTasks(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("todoItems", todoService.getAllTasks(user)); // Pass user
        model.addAttribute("newTodoItem", new TodoItem()); 
        return "todolist"; 
    }
    // --- ADD NEW TASK ---
 // --- ADD NEW TASK ---
    @PostMapping("/add")
    public String addTask(@AuthenticationPrincipal User loggedInUser,
                          @ModelAttribute("newTodoItem") TodoItem todoItem) {
        // Attach the logged-in user directly
        todoItem.setUser(loggedInUser);

        // Save the task
        todoService.saveTask(todoItem, loggedInUser);

        return "redirect:/";
    }


    
    // --- TOGGLE COMPLETION STATUS ---
    @GetMapping("/toggle/{id}")
    public String toggleTaskCompletion(@AuthenticationPrincipal User user, @PathVariable Long id) {
        todoService.getTaskById(id, user).ifPresent(task -> { // Pass user to service
            task.setCompleted(!task.isCompleted()); 
            todoService.saveTask(task, user); // Pass user
        });
        return "redirect:/";
    }

    // --- DELETE TASK ---
    @GetMapping("/delete/{id}")
    public String deleteTask(@AuthenticationPrincipal User user, @PathVariable Long id) {
        todoService.deleteTask(id, user); // Pass user
        return "redirect:/";
    }
}