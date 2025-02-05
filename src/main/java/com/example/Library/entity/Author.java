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
    @Column
    private Long id;


    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 255)
    @Column
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 255)
    @Column
    private String lastName;

    @Size(min = 1, max = 255)
    @Column
    private String middleName;

    @Column
    private Date birthDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Past(message = "Birth date must be in the past")
    @Column
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date modifiedAt;

    @OneToMany(mappedBy = "author" )
    private List<Book> books;


}

