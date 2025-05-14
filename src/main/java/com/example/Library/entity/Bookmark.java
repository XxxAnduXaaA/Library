package com.example.Library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private TextBook textBook;

    @Column(nullable = false)
    private int pageNumber;


    @ManyToOne
    private User user;


}

