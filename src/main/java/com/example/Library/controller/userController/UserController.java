package com.example.Library.controller.userController;//package com.example.library.controller;

import com.example.Library.entity.Bookmark;
import com.example.Library.entity.Note;
import com.example.Library.service.ReadBookService;
import com.example.Library.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ReadBookService readBookService;

    public UserController(UserService userService, ReadBookService readBookService) {
        this.userService = userService;
        this.readBookService = readBookService;
    }


//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        Optional<User> user = userService.getUserById(id);
//        return user.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//    }

//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        User newUser = userService.createUser(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
//        try {
//            User updatedUser = userService.updateUser(id, user);
//            return ResponseEntity.ok(updatedUser);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        try {
//            userService.deleteUser(id);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }


    @GetMapping("/profile")
    public String allBookmarks(Model model) {
        List<Bookmark> allBookmarks = readBookService.getAllBookmarksByUser();
        List<Note> allNotes = readBookService.gettAllNotesByUser();
        model.addAttribute("bookmarks", allBookmarks);
        model.addAttribute("notes", allNotes);
        return "user/bookmarks";
    }

}

