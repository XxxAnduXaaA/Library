package com.example.Library.controller;//package com.example.library.controller;

import com.example.Library.entity.Category;
import com.example.Library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
//
//    @PostMapping
//    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
//        Category newCategory = categoryService.createCategory(category);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
//        try {
//            Category updatedCategory = categoryService.updateCategory(id, category);
//            return ResponseEntity.ok(updatedCategory);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
//        try {
//            categoryService.deleteCategory(id);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
}


