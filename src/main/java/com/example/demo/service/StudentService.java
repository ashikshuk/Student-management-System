package com.example.demo.service;

import com.example.demo.dao.StudentDao;
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

    public Student updateStudent(Long id, StudentDao updatedDetails) {
        return repository.findById(id).map(student -> {
            student.setCourse(updatedDetails.getCourse());
            return repository.save(student);
        }).orElse(null);
    }

    public boolean deleteStudent(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}