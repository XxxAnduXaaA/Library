package com.example.Library.service;

import com.example.Library.entity.Bookmark;
import com.example.Library.entity.Note;
import com.example.Library.entity.TextBook;
import com.example.Library.entity.User;
import com.example.Library.repository.BookmarkRepository;
import com.example.Library.repository.NoteRepository;
import com.example.Library.repository.TextBookRepository;
import com.example.Library.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadBookService {

    private final TextBookRepository textBookRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final NoteRepository noteRepository;

    public ReadBookService(TextBookRepository textBookRepository, UserRepository userRepository, BookmarkRepository bookmarkRepository, NoteRepository noteRepository) {
        this.textBookRepository = textBookRepository;
        this.userRepository = userRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.noteRepository = noteRepository;
    }

    public TextBook getBookId(Long id) {
        return textBookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Такой книги не найдено"));
    }

    public Bookmark addBookMark(Long bookId, int pageNumber) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Такого пользователя не существует"));

        TextBook textbook = textBookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Такой книги не найдено"));

        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setTextBook(textbook);
        bookmark.setPageNumber(pageNumber);

        return bookmarkRepository.save(bookmark);
    }

    public List<Bookmark> getBookmarksByUserAndBook(TextBook textbook) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Такого пользователя не существует"));
        return bookmarkRepository.getBookmarksByUserIdAndTextBookId(user.getId(), textbook.getId());
    }

    public List<Bookmark> getAllBookmarksByUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Такого пользователя не существует"));
        return bookmarkRepository.getAllByUserId(user.getId());
    }

    public List<Note> gettAllNotesByUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Такого пользователя не существует"));
        return noteRepository.getNotesByUserId(user.getId());
    }

    public void deleteBookmark(Long bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }

    public List<Note> getNotesByUserAndBook(TextBook textBook){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Такого пользователя не существует"));
        return noteRepository.getNoteByUserIdAndTextBookId(user.getId(), textBook.getId());
    }

    public Note addNote(Long bookId, int pageNumber, String text, String selectedText){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Такого пользователя не существует"));

        TextBook book = textBookRepository.findById(bookId).orElseThrow();

        Note note = new Note();
        note.setUser(user);
        note.setTextBook(book);
        note.setPageNumber(pageNumber);
        note.setComment(text);
        note.setSelectedText(selectedText);

        return noteRepository.save(note);
    }

    public void deleteNote(Long noteId){
        noteRepository.deleteById(noteId);
    }
}
