package com.example.Library.service;

import com.example.Library.entity.Department;
import com.example.Library.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }
    public Department getDepartmentByName(String name){
        return departmentRepository.findByDepartmentName(name);
    }
}
