package com.example.Library.service;//package com.example.library.service;

import com.example.Library.entity.Author;
import com.example.Library.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    public Author createAuthor(Author author) {

        if(authorRepository.findByFirstNameAndMiddleNameAndLastName(author.getFirstName(),
                author.getMiddleName(), author.getLastName()) == null){
            author.setCreatedAt(new Date());
            author.setModifiedAt(new Date());
            return authorRepository.save(author);
        }
        else{
            //TO DO: Заменить return на exception
            return null;
        }
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Такой автор не найден"));
    }


    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {

        Author existingAuthor = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        existingAuthor.setFirstName(updatedAuthor.getFirstName());
        existingAuthor.setLastName(updatedAuthor.getLastName());
        existingAuthor.setMiddleName(updatedAuthor.getMiddleName());
        existingAuthor.setAcademicPosition(updatedAuthor.getAcademicPosition());
        existingAuthor.setSciePedExperience(updatedAuthor.getAcademicPosition());
        existingAuthor.setTotalWorkExperience(updatedAuthor.getTotalWorkExperience());

        existingAuthor.setModifiedAt(new Date());

        return authorRepository.save(existingAuthor);
    }

//    public Author updatePartialAuthor(Long id, Author partialAuthor) {
//        Optional<Author> existingAuthor = authorRepository.findById(id);
//
//        if (existingAuthor.isPresent()) {
//            Author author = existingAuthor.get();
//            BeanUtils.copyProperties(partialAuthor, author, "id", "createdAt", "modifiedAt");
//            author.setModifiedAt(new Date());
//            return authorRepository.save(author);
//        } else {
//            return null;
//        }
//    }

}
