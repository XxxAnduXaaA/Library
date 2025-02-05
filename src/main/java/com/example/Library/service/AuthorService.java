package com.example.Library.service;//package com.example.library.service;

import com.example.Library.entity.Author;
import com.example.Library.repository.AuthorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;


    public Author createAuthor(Author author) {

        if(authorRepository.findByFirstNameAndMiddleNameAndLastNameAndBirthDate(author.getFirstName(),
                author.getMiddleName(), author.getLastName(), author.getBirthDate()) == null){
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
        List<Author> authors = authorRepository.findAll();
        return authors;
    }

    public Optional<Author> getAuthorById(Long id) {
        return Optional.ofNullable(authorRepository.findById(id).orElse(null));
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {

        Optional<Author> existingAuthor = authorRepository.findById(id);

        Author author = existingAuthor.get();



        if (existingAuthor != null) {
            author.setFirstName(updatedAuthor.getFirstName());
            author.setLastName(updatedAuthor.getLastName());
            author.setMiddleName(updatedAuthor.getMiddleName());
            author.setBirthDate(updatedAuthor.getBirthDate());

            author.setModifiedAt(new Date());

            return authorRepository.save(author);
        }

        else {
            return null;
        }
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
