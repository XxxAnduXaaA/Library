    package com.example.Library.service;

    import com.example.Library.entity.*;
    import com.example.Library.repository.*;
    import com.example.Library.repository.DepartmentRepository;
    import jakarta.transaction.Transactional;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    import java.util.Optional;

    @Service
    public class TextBookService {

        public TextBookService(TextBookRepository textBookRepository, AuthorRepository authorRepository, SubjectRepository subjectRepository, FacultyRepository facultyRepository, DepartmentRepository departmentRepository, PublisherRepository publisherRepository, UserRepository userRepository) {
            this.textBookRepository = textBookRepository;
            this.authorRepository = authorRepository;
            this.subjectRepository = subjectRepository;
            this.facultyRepository = facultyRepository;
            this.departmentRepository = departmentRepository;
            this.publisherRepository = publisherRepository;
            this.userRepository = userRepository;
        }

        private final TextBookRepository textBookRepository;
        private final AuthorRepository authorRepository;
        private final SubjectRepository subjectRepository;
        private final FacultyRepository facultyRepository;
        private final DepartmentRepository departmentRepository;
        private final PublisherRepository publisherRepository;
        private final UserRepository userRepository;



        @Transactional
        public TextBook createTextBook(TextBook textBook) {

        textBookRepository.findByTitleAndYearAndAuthorsIn
                        (textBook.getTitle(), textBook.getYear(), textBook.getAuthors())
                .ifPresent(existingBook -> {
                throw new RuntimeException("Такой учебник уже существует");
                });


        List<Author> textBookAuthors = new ArrayList<>();//textBook.getAuthors();
        List<Author> existingAuthors = new ArrayList<>();

        for (Author author : textBook.getAuthors()) {
            Author existingAuthor = authorRepository.findByFirstNameAndMiddleNameAndLastNameAndAcademicPosition(
                    author.getFirstName(), author.getMiddleName(), author.getLastName(), author.getAcademicPosition());

            if (existingAuthor == null) {
                existingAuthors.add(authorRepository.save(author));
            } else {
                existingAuthors.add(author);
            }
        }

        textBook.setAuthors(existingAuthors);

//        Subject subject = textBook.getSubject();
//        Optional<Subject> existingSubject = subjectRepository.findByName(subject.getName());
//
//        if (existingSubject.isEmpty()) {
//            subjectRepository.save(subject);
//        } else {
//            textBook.setSubject(existingSubject.get());
//        }
//
//        Faculty faculty = textBook.getFaculty();
//        Faculty existingFaculty = facultyRepository.findByFacultyName(faculty.getFacultyName());
//
//        if(existingFaculty == null){
//            facultyRepository.save(faculty);
//        }
//        else {
//            textBook.setFaculty(existingFaculty);
//        }
//
//        Department department = textBook.getDepartment();
//        Department existingDepartment = departmentRepository.findByDepartmentName(department.getDepartmentName());
//
//        if(existingDepartment == null){
//            departmentRepository.save(department);
//        }
//        else {
//            textBook.setDepartment(existingDepartment);
//        }
//
//        Publisher publisher = textBook.getPublisher();
//        Optional<Publisher> existingPublisher = publisherRepository.findByName(publisher.getName());
//
//        if(existingPublisher.isEmpty()){
//            publisherRepository.save(publisher);
//        }
//        else{
//            textBook.setPublisher(publisher);
//        }

        textBook.setCreatedAt(new Date());
        textBook.setModifiedAt(new Date());

        return textBookRepository.save(textBook);
    }

        @Transactional
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

//            Subject subject = updatedTextBook.getSubject();
//              Optional<Subject> existingSubject = subjectRepository.findByName(subject.getName());

//        if (existingSubject == null) {
//                existingTextBook.setSubject(subjectRepository.save(subject));
//            } else {
//                existingTextBook.setSubject(existingSubject.get());
//            }
//
//            Faculty faculty = updatedTextBook.getFaculty();
//            Faculty existingFaculty = facultyRepository.findByFacultyName(faculty.getFacultyName());
//
//            if(existingFaculty == null){
//                existingTextBook.setFaculty(facultyRepository.save(faculty));
//            }
//            else {
//                existingTextBook.setFaculty(existingFaculty);
//            }
//
//            Department department = updatedTextBook.getDepartment();
//            Department existingDepartment = departmentRepository.findByDepartmentName(department.getDepartmentName());
//
//            if(existingDepartment == null){
//                existingTextBook.setDepartment(departmentRepository.save(department));
//            }
//            else {
//                existingTextBook.setDepartment(existingDepartment);
//            }
//
//            Publisher publisher = updatedTextBook.getPublisher();
//            Optional<Publisher> existingPublisher = publisherRepository.findByName(publisher.getName());
//
//            if(existingDepartment == null){
//                existingTextBook.setPublisher(publisherRepository.save(existingPublisher.get()));
//            }
//            else{
//                existingTextBook.setPublisher(publisher);
//            }

            existingTextBook.setModifiedAt(new Date());

            return textBookRepository.save(existingTextBook);

        }

        public List<TextBook> getUserBooks(){
            Author author = getAuthorByUser();
            return author.getTextBooks();
        }

        public Author getAuthorByUser(){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User user = userRepository
                    .findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Такого пользователя не существует"));
            Author author = authorRepository.findByLinkedUser_Id(user.getId());
            return author;
        }

        @Transactional
        public void deleteTextBook(Long id){
            textBookRepository.deleteBookById(id);
        }

        public TextBook getTextBook(Long id){
            return textBookRepository.findById(id).orElse(null);
        }

        public List<TextBook> getAllTextBooks(){
            return textBookRepository.findAll();
        }

        public List<TextBook> searchTextBooks(String query, Long facultyId, Long departmentId, Long categoryId) {
            List<TextBook> results = textBookRepository.searchBooksByTitle(query);

            // Фильтрация по факультету
            if (facultyId != null) {
                results = results.stream()
                        .filter(book -> book.getFaculty() != null && book.getFaculty().getId().equals(facultyId))
                        .toList();
            }

            // Фильтрация по кафедре
            if (departmentId != null) {
                results = results.stream()
                        .filter(book -> book.getDepartment() != null && book.getDepartment().getId().equals(departmentId))
                        .toList();
            }

            // Фильтрация по категории
            if (categoryId != null) {
                results = results.stream()
                        .filter(book -> book.getCategory() != null && book.getCategory().getId().equals(categoryId))
                        .toList();
            }

            return results;
        }


    }
