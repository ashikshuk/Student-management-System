package com.example.demo.service;

import com.example.demo.dto.StudentDto;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return repository.findById(id);
    }

    public Student addStudent(Student student) {
        if (repository.findByEmail(student.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already present.");
        }

        return repository.save(student);
    }

    public Student updateStudentCourse(Long id, String newCourse) {
        return repository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setCourse(newCourse); // Update ONLY the course field
                    return repository.save(existingStudent);
                })
                .orElse(null); // Returns null if the student ID doesn't exist
    }

    public boolean deleteStudent(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}