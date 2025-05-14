package com.example.Library.service;

import com.example.Library.entity.Faculty;
import com.example.Library.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

   public List<Faculty> getAllFaculty(){
        return facultyRepository.findAll();
   }

   public Faculty getFacultyByName(String name){
        return facultyRepository.findByFacultyName(name);
   }
}
