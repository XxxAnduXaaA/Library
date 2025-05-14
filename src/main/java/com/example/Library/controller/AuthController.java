package com.example.Library.controller;

import com.example.Library.entity.User;
import com.example.Library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "public/registration";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute User user){
        userService.registerUser(user.getEmail(), user.getPassword(), "USER");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "public/login";
    }



}
