package com.example.Library.repository;//package com.example.library.repository;

import com.example.Library.entity.Author;
import com.example.Library.entity.TextBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TextBookRepository extends JpaRepository<TextBook, Long> {

    List<TextBook> findAll();

    TextBook findById(long id);

    TextBook save(TextBook textBook);

    void deleteBookById(long id);

    Optional<TextBook> findByTitleAndYearAndAuthorsIn(String title, int year, List<Author> authors);




}
