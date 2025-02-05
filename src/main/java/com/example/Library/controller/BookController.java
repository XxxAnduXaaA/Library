package com.example.Library.controller;//package com.example.Library.controller;

import com.example.Library.entity.Author;
import com.example.Library.entity.Book;
import com.example.Library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@Validated
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        LOGGER.info("Getting all books");
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            LOGGER.warn("No books found");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Found {} books", books.size());
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookByid(@PathVariable Long id) {
        LOGGER.info("Getting book by ID: {}", id);
        Book book = bookService.getBook(id);

        if (book != null) {
            LOGGER.info("Book found: {}", book);
            return ResponseEntity.ok(book);
        } else {
            LOGGER.warn("Book not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        LOGGER.info("Creating book: {}", book);
        book.setCreatedAt(new Date());
        book.setModifiedAt(new Date());
        Book savedBook = bookService.createBook(book, book.getAuthor(), book.getGenre());
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        LOGGER.info("Deleting book with ID: {}", id);
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book updatedBook) {
        LOGGER.info("Updating book with ID: {}", id);
        Book book = bookService.updateBook(id, updatedBook);

        if (book != null) {
            LOGGER.info("Updated book: {}", book);
            return ResponseEntity.ok(book);
        } else {
            LOGGER.warn("Book not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Book> updatePartialBook(@PathVariable Long id, @Valid @RequestBody Book updatedBook) {
        LOGGER.info("Updating book with ID: {}", id);
        LOGGER.debug("Updated book: {}", updatedBook);
        Optional<Book> bookOptional = Optional.ofNullable(bookService.getBook(id));

        if (bookOptional.isPresent()) {
            Book existingBook = bookOptional.get();

            if (updatedBook.getIsbn() != null) {
                existingBook.setIsbn(updatedBook.getIsbn());

            }

            Book updatedBookEntity = bookService.updateBook(id, existingBook);
            return ResponseEntity.ok(updatedBookEntity);
        } else {
            LOGGER.warn("Book with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}