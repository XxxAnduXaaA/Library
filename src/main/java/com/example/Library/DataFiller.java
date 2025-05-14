//package com.example.Library;
//
//import com.example.Library.entity.*;
//import com.example.Library.repository.*;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.data.domain.Example;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class DataFiller implements CommandLineRunner {
//    private final AuthorRepository authorRepository;
//    private final CategoryRepository categoryRepository;
//    private final FacultyRepository facultyRepository;
//    private final DepartmentRepository departmentRepository;
//    private final PublisherRepository publisherRepository;
//    private final TextBookRepository textBookRepository;
//
//    public DataFiller(AuthorRepository authorRepository, CategoryRepository categoryRepository,
//                           FacultyRepository facultyRepository, DepartmentRepository departmentRepository,
//                           PublisherRepository publisherRepository, TextBookRepository textBookRepository) {
//        this.authorRepository = authorRepository;
//        this.categoryRepository = categoryRepository;
//        this.facultyRepository = facultyRepository;
//        this.departmentRepository = departmentRepository;
//        this.publisherRepository = publisherRepository;
//        this.textBookRepository = textBookRepository;
//    }
//
//    @Override
//    public void run(String... args) {
//        // Создаем авторов
//        Author author1 = new Author();
//        author1.setFirstName("Иван");
//        author1.setLastName("Иванов");
//        author1.setMiddleName("Петрович");
//        author1.setTotalWorkExperience("10 лет");
//        author1.setSciePedExperience("5 лет");
//        author1.setAcademicPosition("Профессор");
//
//        Author author2 = new Author();
//        author2.setFirstName("Алексей");
//        author2.setLastName("Смирнов");
//        author2.setMiddleName("Викторович");
//        author2.setTotalWorkExperience("8 лет");
//        author2.setSciePedExperience("3 года");
//        author2.setAcademicPosition("Доцент");
//
//        authorRepository.saveAll(List.of(author1, author2));
//
//        // Создаем категории
//        Category category1 = new Category();
//        category1.setCategoryName("Программирование");
//
//        Category category2 = new Category();
//        category2.setCategoryName("Математика");
//
//        categoryRepository.saveAll(List.of(category1, category2));
//
//        // Создаем факультеты
//        Faculty faculty1 = new Faculty();
//        faculty1.setFacultyName("Факультет информатики");
//
//        Faculty faculty2 = new Faculty();
//        faculty2.setFacultyName("Факультет математики");
//
//        facultyRepository.saveAll(List.of(faculty1, faculty2));
//
//        // Создаем кафедры
//        Department department1 = new Department();
//        department1.setDepartmentName("Кафедра ПО");
//        department1.setFaculty(faculty1);
//
//        Department department2 = new Department();
//        department2.setDepartmentName("Кафедра высшей математики");
//        department2.setFaculty(faculty2);
//
//        departmentRepository.saveAll(List.of(department1, department2));
//
//        // Создаем издателей
//        Publisher publisher1 = new Publisher();
//        publisher1.setName("Издательство Наука");
//
//        Publisher publisher2 = new Publisher();
//        publisher2.setName("Издательство Техно");
//
//        publisherRepository.saveAll(List.of(publisher1, publisher2));
//
//        // Создаем учебники
//        TextBook book1 = new TextBook();
//        book1.setTitle("Основы Java");
//        book1.setDescription("Книга про программирование на Java.");
//        book1.setFaculty(facultyRepository.findByFacultyName(faculty1.getFacultyName()));
//        book1.setCategory(categoryRepository.getReferenceById(category1.getId()));
//        book1.setPublisher(publisherRepository.findByName(publisher1.getName()));
//        book1.setDepartment(departmentRepository.findByDepartmentName(department1.getDepartmentName()));
//        book1.setYear("2023");
//        book1.setPages("500");
//        book1.setAuthors(List.of(authorRepository.findByFirstNameAndMiddleNameAndLastNameAndAcademicPosition(author1.getFirstName(),
//                author1.getMiddleName(), author1.getLastName(), author1.getAcademicPosition())));
//
//        TextBook book2 = new TextBook();
//        book2.setTitle("Алгебра и анализ");
//        book2.setDescription("Учебник по высшей математике.");
//        book2.setFaculty(facultyRepository.findByFacultyName(faculty2.getFacultyName()));
//        book2.setCategory(categoryRepository.findOne(Example.of(category1)).orElse(null));
//        book2.setPublisher(publisher2);
//        book2.setDepartment(department2);
//        book2.setYear("2022");
//        book2.setPages("600");
//        book2.setAuthors(List.of(author2));
//
//        textBookRepository.saveAll(List.of(book1, book2));
//
//        System.out.println("Mock-данные успешно загружены!");
//    }
//}
