package com.example.Library.repository;//package com.example.library.repository;

import com.example.Library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAll();

    Book findById(long id);

    Book findByIsbn(String isbn);

    Book save(Book book);

    void deleteBookById(long id);




}
