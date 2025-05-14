package com.example.Library.controller;//package com.example.Library.controller;

import com.example.Library.entity.Subject;
import com.example.Library.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);

//    @PostMapping
//    public ResponseEntity<Subject> createSubject(@Valid @RequestBody Subject subject){
//        LOGGER.info("Creating Subject: {}", subject);
//        Subject createdSubject = subjectService.createS(subject);
//        return ResponseEntity.status(HttpStatus.CREATED).ody(createdSubject);
//    }

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects(){
        LOGGER.info("Getting all Subjects");
        List<Subject> subjects = subjectService.getAllSubjects();

        if(subjects.isEmpty()){
            LOGGER.warn("No Subjects found");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Found {} Subjects", subjects.size());
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id){
        LOGGER.info("Getting Subject by ID: {}", id);
        Subject subject = subjectService.getSubjectById(id);

        if(subject != null){
            LOGGER.info("Subject found: {}", subject);
            return ResponseEntity.ok(subject);
        }
        else {
            LOGGER.warn("Subject not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @Valid @RequestBody Subject updatedSubject){
        Subject subject = subjectService.updateSubject(id, updatedSubject);

        if(subject != null){
            return ResponseEntity.ok(subject);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<Subject> updatePartialSubject(@PathVariable Long id, @Valid @RequestBody Subject updatedSubject) {
//        LOGGER.info("Updating Subject with ID: {}", id);
//        Optional<Subject> SubjectOptional = Optional.ofNullable(subjectService.getSubjectById(id));
//
//        if (SubjectOptional.isPresent()) {
//            LOGGER.info("Updated Subject: {}", SubjectOptional.get());
//            Subject existingSubject = SubjectOptional.get();
//
//            if (updatedSubject.getName() != null) {
//                existingSubject.setName(updatedSubject.getName());
//            }
//
//            Subject updatedSubjectEntity = subjectService.updateSubject(id, existingSubject);
//            return ResponseEntity.ok(updatedSubjectEntity);
//        } else {
//            LOGGER.warn("Subject not found with ID: {}", id);
//            return ResponseEntity.notFound().build();
//        }
//    }
}
