package com.example.Library.controller.adminController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public String dashboard() {
        return "admin/adminPanel";
    }

//    @GetMapping("/books")
//    public String manageBooks(Model model) {
//        // TODO: Добавить книги для управления
//        return "admin/books1";
//    }

}

