package com.example.Library.controller.adminController;

import com.example.Library.entity.User;
import com.example.Library.repository.UserRepository;
import com.example.Library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;
    private final UserRepository userRepository;



    public AdminUserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users/users";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model){
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/users/userEdit";
    }

    @PostMapping("edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user){
        userService.updateUser(id, user);
        return "redirect:/admin/users";
    }

    @PostMapping("delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/users/userForm";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.registerUser(user.getEmail(), user.getPassword(), user.getRoles().stream().collect(Collectors.joining(", ")));
        return "redirect:/admin/users";
    }

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
}
