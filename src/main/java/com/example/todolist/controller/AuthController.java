package com.example.todolist.controller;

import com.example.todolist.model.User;
import com.example.todolist.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // --- Login Page ---
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password.");
        }
        return "login"; // Renders login.html
    }
    
    // --- Sign Up (Registration) Form ---
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User(null, null));
        return "signup"; // Renders signup.html
    }

    // --- Process Sign Up ---
    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        try {
            userService.registerNewUser(user.getUsername(), user.getPassword());
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
            return "redirect:/login";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/signup";
        }
    }
}
