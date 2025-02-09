package com.example.Library.service;

import com.example.Library.entity.*;
import com.example.Library.repository.*;
import com.example.Library.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TextBookService {

    @Autowired
    private TextBookRepository textBookRepository;
    @Autowired
    private  AuthorRepository authorRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PublisherRepository publisherRepository;


    public TextBook createBook(TextBook textBook, List<Author> authors, Subject subject, Faculty faculty, Department department, Publisher publisher, int pages, int year) {

    List<Author> existingAuthors = new ArrayList<>();

    for (Author author : authors) {
        Author existingAuthor = authorRepository.findByFirstNameAndMiddleNameAndLastNameAndAcademicPosition(
                author.getFirstName(), author.getMiddleName(), author.getLastName(), author.getAcademicPosition());

        if (existingAuthor == null) {
            authorRepository.save(author);
        } else {
            existingAuthors.add(author);
        }
    }

    textBook.setAuthor(existingAuthors);
    Subject existingSubject = subjectRepository.findByName(subject.getName());

    if (existingSubject == null) {
        subjectRepository.save(subject);
    } else {
        textBook.setSubject(existingSubject);
    }

    Faculty existingFaculty = facultyRepository.findByFacultyName(faculty.getFacultyName());

    if(existingFaculty == null){
        facultyRepository.save(faculty);
    }
    else {
        textBook.setFaculty(existingFaculty);
    }

    Department existingDepartment = departmentRepository.findByDepartmentName(department.getDepartmentName());

    if(existingDepartment == null){
        departmentRepository.save(department);
    }
    else {
        textBook.setDepartment(existingDepartment);
    }

    Publisher existingPublisher = publisherRepository.findByName(publisher.getName());

    if(existingDepartment == null){
        publisherRepository.save(existingPublisher);
    }
    else{
        textBook.setPublisher(publisher);
    }

    textBook.setPages(pages);
    textBook.setYear(year);
    textBook.setCreatedAt(new Date());
    textBook.setModifiedAt(new Date());

    return textBookRepository.save(textBook);
}


    public TextBook updateBook(Long id, TextBook updatedTextBook){
        return textBookRepository.findById(id).map(book -> {
            book.setTitle(updatedTextBook.getTitle());
            book.setAuthor(updatedTextBook.getAuthor());
            book.setGenre(updatedTextBook.getGenre());
            book.setUniversity(updatedTextBook.getUniversity());
            book.setIsbn(updatedTextBook.getIsbn());
            book.setYear(updatedTextBook.getYear());
            book.setPages(updatedTextBook.getPages());
            return textBookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteBook(Long id){
        textBookRepository.deleteBookById(id);
    }

    public TextBook getBook(Long id){
        return textBookRepository.findById(id).orElse(null);
    }

    public List<TextBook> getAllBooks(){
        return textBookRepository.findAll();
    }

}
