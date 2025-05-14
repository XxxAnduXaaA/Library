package com.example.Library.controller.adminController;

import com.example.Library.entity.Author;
import com.example.Library.entity.Publisher;
import com.example.Library.entity.Subject;
import com.example.Library.entity.TextBook;
import com.example.Library.repository.AuthorRepository;
import com.example.Library.repository.PublisherRepository;
import com.example.Library.repository.SubjectRepository;
import com.example.Library.repository.TextBookRepository;
import com.example.Library.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.homyakin.iuliia.Schemas;
import ru.homyakin.iuliia.Translator;

@Controller
@RequestMapping("/admin/books")
public class AdminBookController {

    private final TextBookService textBookService;
    private final TextBookRepository textBookRepository;
    private final AuthorRepository authorRepository;
    private final FacultyService facultyService;
    private final DepartmentService departmentService;
    private final CategoryService categoryService;
    private final PublisherService publisherService;
    private final SubjectService subjectService;
    private final PublisherRepository publisherRepository;
    private final SubjectRepository subjectRepository;

    public AdminBookController(TextBookService textBookService, TextBookRepository textBookRepository, AuthorRepository authorRepository, FacultyService facultyService, DepartmentService departmentService, CategoryService categoryService, PublisherService publisherService, SubjectService subjectService, PublisherRepository publisherRepository, SubjectRepository subjectRepository) {
        this.textBookService = textBookService;
        this.textBookRepository = textBookRepository;
        this.authorRepository = authorRepository;
        this.facultyService = facultyService;
        this.departmentService = departmentService;
        this.categoryService = categoryService;
        this.publisherService = publisherService;
        this.subjectService = subjectService;
        this.publisherRepository = publisherRepository;
        this.subjectRepository = subjectRepository;
    }


    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("textBook", textBookService.getAllTextBooks());
        return "admin/books/books1";
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("textBook", new TextBook());
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("faculties", facultyService.getAllFaculty());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("publishers", publisherService.getAllPublishers());  // Добавить издателей
        model.addAttribute("subjects", subjectService.getAllSubjects());

        return "admin/books/bookForm";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute TextBook textBook,
                          @RequestParam(value = "file", required = false) MultipartFile file,
                          RedirectAttributes redirectAttributes) {
        List<Author> authorsList = new ArrayList<>();
        for (String authorData : textBook.getAuthorsInput()) {
            authorData = authorData.trim();
            if (authorData.isEmpty()) continue;

            try {
                // Если введено число — это ID существующего автора
                Long authorId = Long.parseLong(authorData);
                authorsList.add(authorRepository.findById(authorId).orElseThrow());
            } catch (NumberFormatException e) {
                // Если строка — это новый автор, создаём его
                String[] parts = authorData.split(" ");
                if (parts.length >= 2) {
                    String firstName = parts[0];
                    String middleName = (parts.length > 2) ? parts[1] : "";
                    String lastName = parts[parts.length - 1];

                    Author newAuthor = new Author();
                    newAuthor.setFirstName(firstName);
                    newAuthor.setMiddleName(middleName);
                    newAuthor.setLastName(lastName);
                    newAuthor.setTotalWorkExperience("0 лет");
                    newAuthor.setSciePedExperience("0 лет");
                    newAuthor.setAcademicPosition("Не указана");
                    newAuthor.setCreatedAt(new Date());
                    newAuthor.setModifiedAt(new Date());

                    authorsList.add(authorRepository.save(newAuthor));
                }
            }
        }

        textBook.setAuthors(authorsList);

        // Обработка файла (если был загружен)
        if (file != null && !file.isEmpty()) {

            try {
                final var translator = new Translator(Schemas.ICAO_DOC_9303);
                String fileName = translator.translate(file.getOriginalFilename()).replaceAll(" ", "_");
                Path uploadPath = Paths.get("C:\\Users\\Администратор\\IdeaProjects\\Library\\uploads");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Saving to path: " + filePath.toAbsolutePath());


                textBook.setFilePath(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error", "Ошибка при загрузке файла.");
                return "redirect:/admin/books";
            }
        }
        for(String publisherData : textBook.getPublisherInput()){
            publisherData = publisherData.trim();
            if(publisherData.isEmpty()) continue;

            try {
                Long publisherId = Long.parseLong(publisherData); // Пытаемся преобразовать в Long
                Publisher publisher = publisherRepository.findById(publisherId).orElseThrow();
                textBook.setPublisher(publisher);
            } catch (NumberFormatException e) {
                // Если это не ID, пытаемся найти по имени
                Publisher publisher = publisherRepository.findByName(publisherData).orElse(null);
                if (publisher == null) {
                    publisher = new Publisher();
                    publisher.setName(publisherData);
                    publisherRepository.save(publisher);
                }
                textBook.setPublisher(publisher);
            }

        }
        // Проверка и создание издателя, если его нет
//        if (textBook.getPublisherInput() != null && !textBook.getPublisherInput().isEmpty()) {

        for(String subjectData : textBook.getSubjectInput()){
        subjectData = subjectData.trim();
        if(subjectData.isEmpty()) continue;
        // Проверка и создание предмета, если его нет
//        if ( != null && !subjectInput.isEmpty()) {
            try {
                Long subjectId = Long.parseLong(subjectData); // Пытаемся преобразовать в Long
                Subject subject = subjectRepository.findById(subjectId).orElseThrow();
                textBook.setSubject(subject);
            } catch (NumberFormatException e) {
                // Если это не ID, пытаемся найти по имени
                Subject subject = subjectRepository.findByName(subjectData).orElse(null);
                if (subject == null) {
                    subject = new Subject();
                    subject.setName(subjectData);
                    subjectRepository.save(subject);
                }
                textBook.setSubject(subject);
            }
        }


        // Сохраняем учебник
        textBookService.createTextBook(textBook);
        redirectAttributes.addFlashAttribute("message", "Учебник успешно добавлен!");

        return "redirect:/admin/books";
    }


//    @PostMapping("/add")
//    public String uploadBook(@ModelAttribute TextBook textBook,
//                             @RequestParam("file") MultipartFile file,
//                             RedirectAttributes redirectAttributes) {
//        try {
//            // 1. Сохраняем файл
//            if (!file.isEmpty()) {
//                System.out.println("givb");
//                // Генерируем уникальное имя файла
//                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//
//                // Путь, куда сохраняем (например, папка uploads в корне проекта)
//                Path uploadPath = Paths.get("C:\\Users\\Администратор\\IdeaProjects\\Library\\src\\main\\resources\\static\\uploads");
//                if (!Files.exists(uploadPath)) {
//                    Files.createDirectories(uploadPath);
//                }
//
//                Path filePath = uploadPath.resolve(fileName);
//                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//                // 2. Сохраняем путь к файлу в сущность
//                textBook.setFilePath(fileName);
//            }
//
//            System.out.println("ALE");
//
//            // 3. Сохраняем сам учебник в базу
//            textBookService.createTextBook(textBook); // или repository.save()
//
//            redirectAttributes.addFlashAttribute("message", "Учебник успешно загружен!");
//            return "redirect:/admin/books";
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            redirectAttributes.addFlashAttribute("error", "Ошибка при загрузке файла.");
//            return "redirect:/admin/books";
//        }
//    }



    @PostMapping("/teacher/books/upload")
    public String uploadBook(@RequestParam("file") MultipartFile file) throws IOException {


        // Сохраняем файл
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


        return "redirect:/myBooks";
    }



    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        TextBook textBook = textBookService.getTextBook(id);

        List<String> authorIds = textBook.getAuthors().stream()
                .map(author -> String.valueOf(author.getId()))
                .collect(Collectors.toList());
        textBook.setAuthorsInput(authorIds);

        model.addAttribute("textBook", textBook);
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("faculties", facultyService.getAllFaculty());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("publishers", publisherService.getAllPublishers());  // Добавить издателей
        model.addAttribute("subjects", subjectService.getAllSubjects());

        return "admin/books/bookFormEdit";
    }

    @PostMapping("edit/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute TextBook textBook) {
//        List<Author> authors = authorsInput.stream()
//                .map(Long::parseLong)
//                .map(authorId -> authorRepository.findById(authorId).orElse(null))
//                .filter(Objects::nonNull)
//                .toList();

        List<Author> authorsList = new ArrayList<>();
        for (String authorData : textBook.getAuthorsInput()) {
            authorData = authorData.trim();
            if (authorData.isEmpty()) continue;

            try {
                // Если введено число — это ID существующего автора
                Long authorId = Long.parseLong(authorData);
                authorsList.add(authorRepository.findById(authorId).orElseThrow());
            } catch (NumberFormatException e) {
                // Если строка — это новый автор, создаём его
                String[] parts = authorData.split(" ");
                if (parts.length >= 2) {
                    String firstName = parts[0];
                    String middleName = (parts.length > 2) ? parts[1] : "";
                    String lastName = parts[parts.length - 1];

                    Author newAuthor = new Author();
                    newAuthor.setFirstName(firstName);
                    newAuthor.setMiddleName(middleName);
                    newAuthor.setLastName(lastName);

                    // Заполняем обязательные поля
                    newAuthor.setTotalWorkExperience("0 лет");
                    newAuthor.setSciePedExperience("0 лет");
                    newAuthor.setAcademicPosition("Не указана");
                    newAuthor.setCreatedAt(new Date());
                    newAuthor.setModifiedAt(new Date());

                    authorsList.add(authorRepository.save(newAuthor));
                }
            }
        }

        for(String publisherData : textBook.getPublisherInput()){
            publisherData = publisherData.trim();
            if(publisherData.isEmpty()) continue;

            try {
                Long publisherId = Long.parseLong(publisherData); // Пытаемся преобразовать в Long
                Publisher publisher = publisherRepository.findById(publisherId).orElseThrow();
                textBook.setPublisher(publisher);
            } catch (NumberFormatException e) {
                // Если это не ID, пытаемся найти по имени
                Publisher publisher = publisherRepository.findByName(publisherData).orElse(null);
                if (publisher == null) {
                    publisher = new Publisher();
                    publisher.setName(publisherData);
                    publisherRepository.save(publisher);
                }
                textBook.setPublisher(publisher);
            }

        }
        // Проверка и создание издателя, если его нет
//        if (textBook.getPublisherInput() != null && !textBook.getPublisherInput().isEmpty()) {

        for(String subjectData : textBook.getSubjectInput()){
            subjectData = subjectData.trim();
            if(subjectData.isEmpty()) continue;
            // Проверка и создание предмета, если его нет
//        if ( != null && !subjectInput.isEmpty()) {
            try {
                Long subjectId = Long.parseLong(subjectData); // Пытаемся преобразовать в Long
                Subject subject = subjectRepository.findById(subjectId).orElseThrow();
                textBook.setSubject(subject);
            } catch (NumberFormatException e) {
                // Если это не ID, пытаемся найти по имени
                Subject subject = subjectRepository.findByName(subjectData).orElse(null);
                if (subject == null) {
                    subject = new Subject();
                    subject.setName(subjectData);
                    subjectRepository.save(subject);
                }
                textBook.setSubject(subject);
            }
        }

        textBook.setAuthors(authorsList);
        textBookService.updateBook(id, textBook);
        return "redirect:/admin/books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        textBookService.deleteTextBook(id);
        return "redirect:/admin/books";
    }
}
