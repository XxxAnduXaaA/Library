package com.example.Library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Table(name = "faculty_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String facultyName;

    @OneToMany(mappedBy = "faculty")
    private List<Department> departments;


}
