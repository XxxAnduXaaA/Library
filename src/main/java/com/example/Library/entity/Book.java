package com.example.Library.entity;//package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@Table(name = "book_table")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$")
//    @Size(min = 1, max = 255)
//    @Column(unique = true)
//    private String isbn;

    @NotBlank(message = "Author is required")
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnoreProperties("books")
    private Author author;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @ManyToOne(cascade = CascadeType.MERGE) //позже загуглить
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

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