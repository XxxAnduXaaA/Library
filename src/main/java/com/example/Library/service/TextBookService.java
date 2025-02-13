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

        TextBook existingTextBook = textBookRepository.findById(id).orElseThrow(() -> new RuntimeException("Textbook not found with id: " + id));

        existingTextBook.setTitle(updatedTextBook.getTitle());
        existingTextBook.setDescription(updatedTextBook.getDescription());
        existingTextBook.setYear(updatedTextBook.getYear());
        existingTextBook.setPages(updatedTextBook.getPages());
        existingTextBook.setModifiedAt(new Date());

        List<Author> updatedAuthors = updatedTextBook.getAuthors();
        List<Author> existingAuthors = new ArrayList<>();

        for( Author author : updatedAuthors){
            Author existingAuthor = authorRepository.findByFirstNameAndMiddleNameAndLastNameAndAcademicPosition(author.getFirstName(), author.getMiddleName(), author.getLastName(), author.getAcademicPosition());

            if(existingAuthor == null){
                existingAuthors.add(authorRepository.save(author));
            }
            else{
                existingAuthors.add(author);
            }

        }

        existingTextBook.setAuthors(existingAuthors);

        Subject subject = updatedTextBook.getSubject();
        Subject existingSubject = subjectRepository.findByName(subject.getName());

        if (existingSubject == null) {
            existingTextBook.setSubject(subjectRepository.save(subject));
        } else {
            existingTextBook.setSubject(existingSubject);
        }

        Faculty faculty = updatedTextBook.getFaculty();
        Faculty existingFaculty = facultyRepository.findByFacultyName(faculty.getFacultyName());

        if(existingFaculty == null){
            existingTextBook.setFaculty(facultyRepository.save(faculty));
        }
        else {
            existingTextBook.setFaculty(existingFaculty);
        }

        Department department = updatedTextBook.getDepartment();
        Department existingDepartment = departmentRepository.findByDepartmentName(department.getDepartmentName());

        if(existingDepartment == null){
            existingTextBook.setDepartment(departmentRepository.save(department));
        }
        else {
            existingTextBook.setDepartment(existingDepartment);
        }

        Publisher publisher = updatedTextBook.getPublisher();
        Publisher existingPublisher = publisherRepository.findByName(publisher.getName());

        if(existingDepartment == null){
            existingTextBook.setPublisher(publisherRepository.save(existingPublisher));
        }
        else{
            existingTextBook.setPublisher(publisher);
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
