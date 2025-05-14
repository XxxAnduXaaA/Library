package com.example.Library.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "textbook_table")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TextBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$")
//    @Size(min = 1, max = 255)
//    @Column(unique = true)
//    private String isbn;

    @Column(nullable = false)
    private String filePath;

    @Transient // Не сохраняем это поле в БД
    private List<String> authorsInput = new ArrayList<>();

    @Transient // Не сохраняем это поле в БД
    private List<String> publisherInput = new ArrayList<>();

    @Transient // Не сохраняем это поле в БД
    private List<String> subjectInput = new ArrayList<>();

    @NotNull
    @NotEmpty(message = "Author is required")
    @ManyToMany(cascade = CascadeType.PERSIST)
            @JoinTable(
            name = "Author_TextBook",
            joinColumns = {@JoinColumn(name = "textbook_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    //@JsonIgnoreProperties("books")
    private List<Author> authors; //Нужно посмотреть, как сделать, чтобы не было два одинаковых автора в списке

    //@NotBlank
    @Column//(nullable = false)
    private String title;

//    @NotBlank
    @Column//(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "faculty_id") //, nullable = false)
    private Faculty faculty;

    @ManyToOne(cascade = CascadeType.MERGE) //позже загуглить
    @JoinColumn(name = "category_id")//, nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "publisher_id")//, nullable = false)
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "subject_id")//, nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "department_id")//, nullable = false)
    private Department department;

//    @NotBlank
    @Column//(nullable = false)
    private String year;

//    @NotBlank
    @Column(nullable = false)
    private String pages;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date modifiedAt;

    // Добавляем связь с закладками
    @OneToMany(mappedBy = "textBook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "textBook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

//    @Column(name = "tsv_title", columnDefinition = "tsvector")
//    private String tsv_title;
}