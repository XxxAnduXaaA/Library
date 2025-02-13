package com.example.Library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Table(name = "department_table")
@Entity
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    @Column
    private Faculty faculty;

    @NotBlank
    @Column(nullable = false)
    private String departmentName;

    @OneToMany(mappedBy = "department")
    @Column
    private List<TextBook> textBooks;

}
