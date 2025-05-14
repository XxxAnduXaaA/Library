package com.example.Library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_table")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

//    @Column(nullable = false)
//    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "linkedUser")
    private Author linkedAuthor;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks;


}

