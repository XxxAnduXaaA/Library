package com.example.Library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Table(name = "author_table")
@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String lastName;

    @Size(min = 1, max = 255)
    @Column
    private String middleName;

    @NotBlank
    @Column(nullable = false)
    private String totalWorkExperience;

    @NotBlank
    @Column(nullable = false)
    private String SciePedExperience;

    @NotBlank
    @Column(nullable = false)
    private String AcademicPosition;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date modifiedAt;

    @ManyToMany(mappedBy = "author", cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Author_TextBook",
            joinColumns = { @JoinColumn(name = "author_id") },
            inverseJoinColumns = {@JoinColumn(name = "textbook_id") }
    )
    private List<TextBook> textBooks;


}

