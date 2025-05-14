package com.example.Library.controller.userController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PublicController {

    @GetMapping("/")
    public String index() {
        return "public/index";
    }

//    @GetMapping("/books")
//    public String books(Model model) {
//        // TODO: Добавить список книг из БД
//        return "public/books";
//    }

//    @GetMapping("/books/{id}")
//    public String bookDetails(@PathVariable Long id, Model model) {
//        // TODO: Получить книгу по ID и передать в модель
//        return "public/book-details";
//    }

    @GetMapping("/login")
    public String login() {
        return "public/login";
    }

    @GetMapping("/register")
    public String register() {
        return "public/registration";
    }
}

