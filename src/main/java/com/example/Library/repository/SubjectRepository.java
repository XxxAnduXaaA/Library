package com.example.Library.repository;//package com.example.library.repository;

import com.example.Library.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

//    Genre save(Genre genre);
//
//    List<Genre> findAll();
//
//    Genre findById(long id);

    Subject findByName(String genre);
}
