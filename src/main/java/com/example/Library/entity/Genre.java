package com.example.Library.entity;//package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Table(name = "genre_table")
@Data
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotBlank
    @Size(min = 1, max = 255)
    @Column
    private String name;


    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date modifiedAt;

    @JsonIgnoreProperties("genre")
    @OneToMany(mappedBy = "genre")
    private List<Book> books;

}