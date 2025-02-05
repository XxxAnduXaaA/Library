package com.example.Library.service;//package com.example.Library.service;

import com.example.Library.entity.Author;
import com.example.Library.entity.Book;
import com.example.Library.entity.Genre;
import com.example.Library.repository.AuthorRepository;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private  AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;


public Book createBook(Book book, Author author, Genre genre) {


    Author existingAuthor = authorRepository.findByFirstNameAndMiddleNameAndLastNameAndBirthDate(
            author.getFirstName(), author.getMiddleName(), author.getLastName(), author.getBirthDate());

    if (existingAuthor == null) {
        authorRepository.save(author);
    } else {
        book.setAuthor(existingAuthor);
    }

    Genre existingGenre = genreRepository.findByName(genre.getName());

    if (existingGenre == null) {
        genreRepository.save(genre);
    } else {
        book.setGenre(existingGenre);
    }

    book.setCreatedAt(new Date());
    book.setModifiedAt(new Date());

    return bookRepository.save(book);
}


    public Book updateBook(Long id, Book updatedBook){
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setGenre(updatedBook.getGenre());
            book.setPublisher(updatedBook.getPublisher());
            book.setIsbn(updatedBook.getIsbn());
            book.setYear(updatedBook.getYear());
            book.setPages(updatedBook.getPages());
            return bookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteBook(Long id){
        bookRepository.deleteBookById(id);
    }

    public Book getBook(Long id){
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

}
