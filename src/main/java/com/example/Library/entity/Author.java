package com.example.Library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;


@Table(name = "author_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column
    private String totalWorkExperience;

    @Column
    private String sciePedExperience;

    @Column
    private String academicPosition;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date modifiedAt;

    @ManyToMany(mappedBy = "authors", cascade = {CascadeType.ALL})
    private List<TextBook> textBooks;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User linkedUser; // кто это в системе

    @Override
    public String toString() {
        return firstName + ' ' +
                lastName + ' ' +
                middleName;
    }
}

