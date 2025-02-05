package com.example.Library.repository;//package com.example.library.repository;

import com.example.Library.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

//    Genre save(Genre genre);
//
//    List<Genre> findAll();
//
//    Genre findById(long id);

    Genre findByName(String genre);
}
