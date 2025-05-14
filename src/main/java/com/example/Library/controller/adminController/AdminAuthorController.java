package com.example.Library.controller.adminController;

import com.example.Library.controller.AuthorController;
import com.example.Library.entity.Author;
import com.example.Library.service.AuthorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/authors")
public class AdminAuthorController {

    @Autowired
    private AuthorService authorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);

    @GetMapping
    public String listAuthors(Model model){
        model.addAttribute("authors", authorService.getAllAuthors());
        return "admin/authors/authors";
    }

    @GetMapping("/add")
    public String addAuthorForm(Model model){
        model.addAttribute("author", new Author());
        return "admin/authors/authorForm";
    }

    @PostMapping("/add")
    public String addAuthor(@ModelAttribute Author author) {
       authorService.createAuthor(author);
       return "redirect:/admin/authors";
    }

    @GetMapping("/edit/{id}")
    public String editAuthorForm(@PathVariable Long id, Model model){
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "admin/authors/authorFormEdit";
    }

    @PostMapping("edit/{id}")
    public String editAuthor(@PathVariable Long id, @ModelAttribute Author author){
        authorService.updateAuthor(id, author);
        return "redirect:/admin/authors";
    }



    @PostMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
//        LOGGER.info("Deleting author with ID: {}", id);
        authorService.deleteAuthor(id);
        return "redirect:/admin/authors";
    }

}
