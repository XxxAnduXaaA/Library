package com.example.Library.controller.userController;//package com.example.Library.controller;

import com.example.Library.entity.Category;
import com.example.Library.entity.Department;
import com.example.Library.entity.Faculty;
import com.example.Library.entity.TextBook;
import com.example.Library.service.CategoryService;
import com.example.Library.service.DepartmentService;
import com.example.Library.service.FacultyService;
import com.example.Library.service.TextBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;


@Controller
@Validated
@RequestMapping("/books")
public class TextBookController {

    private final TextBookService textBookService;
    private final FacultyService facultyService;
    private final DepartmentService departmentService;
    private final CategoryService categoryService;


    private static final Logger LOGGER = LoggerFactory.getLogger(TextBookController.class);

    public TextBookController(TextBookService textBookService, FacultyService facultyService, DepartmentService departmentService, CategoryService categoryService) {
        this.textBookService = textBookService;
        this.facultyService = facultyService;
        this.departmentService = departmentService;
        this.categoryService = categoryService;
    }

//    @GetMapping
//    public ResponseEntity<List<TextBook>> getAllBooks() {
//        LOGGER.info("Getting all books");
//        List<TextBook> textBooks = textBookService.getAllTextBooks();
//        if (textBooks.isEmpty()) {
//            LOGGER.warn("No books found");
//            return ResponseEntity.noContent().build();
//        }
//        LOGGER.info("Found {} books", textBooks.size());
//        return new ResponseEntity<>(textBooks, HttpStatus.OK);
//    }

    @GetMapping
    public String getAllBooks(Model model){
        model.addAttribute("books", textBookService.getAllTextBooks());
        return "admin/books/books1";
    }

    @GetMapping("/search")
    public String searchTextBooks(@RequestParam String query, @RequestParam(value = "facultyId", required = false) Long facultyId,
                                  @RequestParam(value = "departmentId", required = false) Long departmentId,
                                  @RequestParam(value = "categoryId", required = false) Long categoryId,
                                  Model model){
        String tsQuery = query.replaceAll("\\s+", " | ");

        List<TextBook> textBooks = textBookService.searchTextBooks(tsQuery, facultyId, departmentId, categoryId);
        List<Faculty> faculties = facultyService.getAllFaculty();
        List<Department> departments = departmentService.getAllDepartments();
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("textbooks", textBooks);
        model.addAttribute("faculties", faculties);
        model.addAttribute("departments", departments);
        model.addAttribute("categories", categories);

        return "public/searchResults";
    }

    @GetMapping("/{id}")
    public ResponseEntity<TextBook> getBookByid(@PathVariable Long id) {
        LOGGER.info("Getting book by ID: {}", id);
        TextBook textBook = textBookService.getTextBook(id);

        if (textBook != null) {
            LOGGER.info("Book found: {}", textBook);
            return ResponseEntity.ok(textBook);
        } else {
            LOGGER.warn("Book not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<TextBook> createBook(@Valid @RequestBody TextBook textBook) {
        LOGGER.info("Creating book: {}", textBook);
        TextBook savedTextBook = textBookService.createTextBook(textBook);
//        textBook.setCreatedAt(new Date());
//        textBook.setModifiedAt(new Date());
        return new ResponseEntity<>(savedTextBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        LOGGER.info("Deleting book with ID: {}", id);
        textBookService.deleteTextBook(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TextBook> updateBook(@PathVariable Long id, @Valid @RequestBody TextBook updatedTextBook) {
        LOGGER.info("Updating book with ID: {}", id);
        TextBook textBook = textBookService.updateBook(id, updatedTextBook);

        if (textBook != null) {
            LOGGER.info("Updated book: {}", textBook);
            return ResponseEntity.ok(textBook);
        } else {
            LOGGER.warn("Book not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }


//    @PatchMapping("/{id}")
//    public ResponseEntity<TextBook> updatePartialBook(@PathVariable Long id, @Valid @RequestBody TextBook updatedTextBook) {
//        LOGGER.info("Updating book with ID: {}", id);
//        LOGGER.debug("Updated book: {}", updatedTextBook);
//        Optional<TextBook> bookOptional = Optional.ofNullable(textBookService.getBook(id));
//
//        if (bookOptional.isPresent()) {
//            TextBook existingTextBook = bookOptional.get();
//
//            if (updatedTextBook.getIsbn() != null) {
//                existingTextBook.setIsbn(updatedTextBook.getIsbn());
//
//            }
//
//            TextBook updatedTextBookEntity = textBookService.updateBook(id, existingTextBook);
//            return ResponseEntity.ok(updatedTextBookEntity);
//        } else {
//            LOGGER.warn("Book with ID {} not found", id);
//            return ResponseEntity.notFound().build();
//        }
//    }
}