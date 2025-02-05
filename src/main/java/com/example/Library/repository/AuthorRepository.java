package com.example.Library.repository;//package com.example.library.repository;

import com.example.Library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {


    Author findByFirstNameAndMiddleNameAndLastNameAndBirthDate(String firstName, String middleName, String lastName, Date birthDate);

//
//    Optional<Author> findById(Long id);
//
//    List<Author> findByGenre(Genre genre);
//

//    List<Author> findAll();
//
//    void deleteById(long id);
//
//    Author save(Author author);
}