package com.example.Library.controller.userController;

import com.example.Library.entity.Bookmark;
import com.example.Library.entity.Note;
import com.example.Library.entity.TextBook;
import com.example.Library.service.ReadBookService;
import com.example.Library.service.TextBookService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookReaderController {

    private final TextBookService textBookService;
    private final ReadBookService readBookService;

    public BookReaderController(TextBookService textBookService, ReadBookService readBookService) {
        this.textBookService = textBookService;
        this.readBookService = readBookService;
    }

    // Открытие книги для чтения
    @GetMapping("/read/{id}")
    public String readBook(@PathVariable Long id, @RequestParam(required = false, defaultValue = "1") int page, Model model) {
        TextBook book = textBookService.getTextBook(id);
        List<Note> notes = readBookService.getNotesByUserAndBook(book);
        List<Bookmark> bookmarks = readBookService.getBookmarksByUserAndBook(book);

        model.addAttribute("book", book);
        model.addAttribute("notes", notes);
        model.addAttribute("bookmarks", bookmarks);
        model.addAttribute("page", page);
        return "public/readBook"; // Thymeleaf-шаблон
    }



    // Добавление заметки
    @PostMapping("/{bookId}/notes")
    @ResponseBody
    public ResponseEntity<String> addNote(@PathVariable Long bookId,
                                          @RequestParam String text,
                                          @RequestParam String selectedText,
                                          @RequestParam int page
                                          ) {
        try {
            readBookService.addNote(bookId, page, text, selectedText);
            return ResponseEntity.ok("Note added");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    // Добавление закладки
    @PostMapping("/{bookId}/bookmarks")
    @ResponseBody
    public ResponseEntity<String> addBookmark(@PathVariable Long bookId,
                                              @RequestParam int page) {

        try {
            readBookService.addBookMark(bookId, page);
            return ResponseEntity.ok("Bookmark added");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    // Удаление заметки
    @PostMapping("/{bookId}/notes/delete/{noteId}")
    public String deleteNote(@PathVariable Long bookId, @PathVariable Long noteId) {
        readBookService.deleteNote(noteId);
        return "redirect:/books/read/" + bookId;
    }

    // Удаление закладки
    @PostMapping("/{bookId}/bookmarks/delete/{bookmarkId}")
    public String deleteBookmark(@PathVariable Long bookId, @PathVariable Long bookmarkId) {
        readBookService.deleteBookmark(bookmarkId);
        return "redirect:/books/read/" + bookId;
    }


}

