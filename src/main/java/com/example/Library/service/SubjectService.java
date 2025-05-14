package com.example.Library.service;//package com.example.library.service;

import com.example.Library.entity.Subject;
import com.example.Library.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

//    public Subject createSubject(Subject subject) {
//
//        subjectRepository.findByName(subject.getName()).ifPresent(existingSubject -> {
//            throw new RuntimeException("Такой предмет уже существует");
//        });
//
//        subject.
//
//        existingSubject
//    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id).orElse(null);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject updateSubject(Long id, Subject updatedSubject) {

        Subject existingSubject = subjectRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Такого предмета не существует");});

            existingSubject.setName(updatedSubject.getName());
            existingSubject.setModifiedAt(new Date());

            return subjectRepository.save(existingSubject);
    }


}
