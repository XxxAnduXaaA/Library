package com.example.Library.entity;//package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "author" )
    private List<Book> books;


}

