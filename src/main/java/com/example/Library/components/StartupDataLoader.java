//package com.example.Library.components;
//
//import com.example.Library.entity.Category;
//import com.example.Library.entity.Department;
//import com.example.Library.entity.Faculty;
//import com.example.Library.entity.enums.CategoryType;
//import com.example.Library.entity.enums.FacultyType;
//import com.example.Library.repository.CategoryRepository;
//import com.example.Library.repository.DepartmentRepository;
//import com.example.Library.repository.FacultyRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component
//public class StartupDataLoader implements CommandLineRunner {
//
//    private final FacultyRepository facultyRepository;
//    private final DepartmentRepository departmentRepository;
//    private final CategoryRepository categoryRepository;
//
//    public StartupDataLoader(FacultyRepository facultyRepository, DepartmentRepository departmentRepository, CategoryRepository categoryRepository) {
//        this.facultyRepository = facultyRepository;
//        this.departmentRepository = departmentRepository;
//        this.categoryRepository = categoryRepository;
//    }
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        for (FacultyType facultyType: FacultyType.values()) {
//            Faculty faculty = new Faculty();
//            faculty.setFacultyName(facultyType.getDisplayName());
//            facultyRepository.save(faculty);
//
//            for(String departmentName: facultyType.getDepartments()){
//                Department department = new Department();
//                department.setDepartmentName(departmentName);
//                department.setFaculty(faculty);
//                departmentRepository.save(department);
//            }
//        }
//
//        for (CategoryType categoryType: CategoryType.values()){
//            Category category = new Category();
//            category.setCategoryName(categoryType.getDisplayName());
//            categoryRepository.save(category);
//        }
//    }
//}
