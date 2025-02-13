package com.example.Library.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

    @NotBlank(message = "Author is required")
    @ManyToMany(mappedBy = "textbooks")
    //@JsonIgnoreProperties("books")
    private List<Author> authors; //Нужно посмотреть, как сделать, чтобы не было два одинаковых автора в списке

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @NotBlank
    @ManyToOne(cascade = CascadeType.MERGE) //позже загуглить
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

//    @OneToMany(mappedBy = "book")
//    private List<Review> reviews;
//
//    @OneToMany(mappedBy = "book")
//    private List<Rating> ratings;

    @NotBlank
    @Column(nullable = false)
    private int year;

    @NotBlank
    @Column(nullable = false)
    private int pages;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date modifiedAt;
}