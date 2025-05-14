//package com.example.Library.controller.teacherController;
//
//import com.example.Library.entity.Author;
//import com.example.Library.entity.TextBook;
//import com.example.Library.repository.AuthorRepository;
//import com.example.Library.service.TextBookService;
//import com.example.Library.service.UserService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/teacher")
//public class TeacherPanelController {
//
//    private final TextBookService textBookService;
//
//    private final AuthorRepository authorRepository;
//
//    public TeacherPanelController(TextBookService textBookService, AuthorRepository authorRepository) {
//        this.textBookService = textBookService;
//        this.authorRepository = authorRepository;
//    }
//
//    @GetMapping()
//    public String dashboard() {
//        return "teacher/teacherPanel";
//    }
//
//
//    @GetMapping("/myBooks")
//    public String getMyBooks(Model model) {
//        model.addAttribute("textBook", textBookService.getUserBooks());
//        return "teacher/teacherBooks";
//    }
//
//    @GetMapping("/add")
//    public String addBookForm(@ModelAttribute Model model) {
//        model.addAttribute("textBook", new TextBook());
//        model.addAttribute("authors", authorRepository.findAll());
//        return "teacher/teacherBooksForm";
//    }
//
//    @PostMapping("/add")
//    public String addBook(@ModelAttribute TextBook textBook) {
//        List<Author> authorsList = new ArrayList<>();
//        authorsList.add(textBookService.getAuthorByUser());
//        for (String authorData : textBook.getAuthorsInput()) {
//            authorData = authorData.trim();
//            if (authorData.isEmpty()) continue;
//            Long authorId = Long.parseLong(authorData);
//            authorsList.add(authorRepository.findById(authorId).orElseThrow());
//        }
//
//        textBook.setAuthors(authorsList);
//        return "redirect:/teacher/myBooks";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editBookForm(@PathVariable Long id, Model model) {
//        TextBook textBook = textBookService.getTextBook(id);
//
//        List<String> authorIds = textBook.getAuthors().stream()
//                .map(author -> String.valueOf(author.getId()))
//                .collect(Collectors.toList());
//        textBook.setAuthorsInput(authorIds);
//
//        model.addAttribute("authors", authorRepository.findAll());
//        model.addAttribute("textBook", textBook);
//        return "teacher/teacherBooksFormEdit";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String editBook(@PathVariable Long id, @ModelAttribute TextBook textBook) {
//        List<Author> authorsList = new ArrayList<>();
//        for (String authorData : textBook.getAuthorsInput()) {
//            authorData = authorData.trim();
//            if (authorData.isEmpty()) continue;
//            // Если введено число — это ID существующего автора
//            Long authorId = Long.parseLong(authorData);
//            authorsList.add(authorRepository.findById(authorId).orElseThrow());
//
//        }
//
//        textBook.setAuthors(authorsList);
//        textBookService.updateBook(id, textBook);
//        return "redirect:/teacher/myBooks";
//    }
//
//
//    @PostMapping("/delete/{id}")
//    public String deleteBook(@PathVariable Long id) {
//        textBookService.deleteTextBook(id);
//        return "redirect:/teacher/myBooks";
//    }
//}