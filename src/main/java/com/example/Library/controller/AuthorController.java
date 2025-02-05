package com.example.Library.controller;//package com.example.library.controller;
import com.example.Library.entity.Author;
import com.example.Library.service.AuthorService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;



import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@Validated
@RequestMapping("/authors")
public class AuthorController {



    @Autowired
    private AuthorService authorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);

    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        LOGGER.info("Creating author: {}", author);
        Author createdAuthor = authorService.createAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        LOGGER.info("Getting all authors");
        List<Author> authors = authorService.getAllAuthors();

        if (authors.isEmpty()) {
            LOGGER.warn("No authors found");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Found {} authors", authors.size());
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Author>> getAuthorById(@PathVariable Long id) {
        LOGGER.info("Getting author by ID: {}", id);
        Optional<Author> author = authorService.getAuthorById(id);

        System.out.println(author.get().getBooks());


        if (author != null) {
            LOGGER.info("Author found: {}", author.get());
            return ResponseEntity.ok(author);
        } else {
            LOGGER.warn("Author not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        LOGGER.info("Deleting author with ID: {}", id);
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @Valid @RequestBody Author updatedAuthor) {
        LOGGER.info("Updating author with ID: {}", id);
        Author author = authorService.updateAuthor(id, updatedAuthor);

        if (author != null) {
            LOGGER.info("Updated author: {}", author);
            return ResponseEntity.ok(author);
        } else {
            LOGGER.warn("Author not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Author> updatePartialAuthor(@PathVariable Long id, @Valid @RequestBody Author updatedAuthor) {
        LOGGER.info("Updating author with ID: {}", id);
        LOGGER.debug("Updated author: {}", updatedAuthor);

        Optional<Author> authorOptional = authorService.getAuthorById(id);

        if (authorOptional.isPresent()) {
            Author existingAuthor = authorOptional.get();

            if (updatedAuthor.getFirstName() != null) {
                existingAuthor.setFirstName(updatedAuthor.getFirstName());
            }
            if (updatedAuthor.getMiddleName() != null) {
                existingAuthor.setMiddleName(updatedAuthor.getMiddleName());
            }
            if (updatedAuthor.getLastName() != null) {
                existingAuthor.setLastName(updatedAuthor.getLastName());
            }
            if (updatedAuthor.getBirthDate() != null) {
                existingAuthor.setBirthDate(updatedAuthor.getBirthDate());
            }

            Author updatedAuthorEntity = authorService.updateAuthor(id, existingAuthor);
            return ResponseEntity.ok(updatedAuthorEntity);
        } else {
            LOGGER.warn("Author with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
