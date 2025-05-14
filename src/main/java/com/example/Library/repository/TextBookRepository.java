package com.example.Library.repository;//package com.example.library.repository;

import com.example.Library.entity.Author;
import com.example.Library.entity.TextBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface TextBookRepository extends JpaRepository<TextBook, Long> {

    List<TextBook> findAll();

    TextBook findById(long id);

    TextBook save(TextBook textBook);

    void deleteBookById(long id);

    Optional<TextBook> findByTitleAndYearAndAuthorsIn(String title, String year, List<Author> authors);
    @Query(value = "WITH full_text_search AS (" +
            "    SELECT * FROM textbook_table " +
            "    WHERE to_tsvector('russian', title) @@ to_tsquery('russian', :query) " +
            "    ORDER BY ts_rank(to_tsvector('russian', title), to_tsquery('russian', :query)) DESC" +
            ") " +
            "SELECT * FROM full_text_search " +
            "UNION ALL " +
            "SELECT * FROM textbook_table " +
            "WHERE title ILIKE CONCAT('%', :query, '%') " +
            "AND NOT EXISTS (SELECT 1 FROM full_text_search)", nativeQuery = true)
    List<TextBook> searchBooksByTitle(@Param("query") String query);
}
