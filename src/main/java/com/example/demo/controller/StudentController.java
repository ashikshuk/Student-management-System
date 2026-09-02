package com.example.demo.controller;

import com.example.demo.dao.StudentDao;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import com.example.demo.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addStudent(@Valid @RequestBody Student student) {
        Student savedStudent = service.addStudent(student);
        ApiResponse apiResponse = ApiResponse.success(HttpStatus.CREATED.value(), "Student's data created successfully.", savedStudent);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        List<Student> students = service.getAllStudents();
        String message = students.isEmpty() ? "No student found in the database.": "Students retrieved successfully.";
        ApiResponse<List<Student>> apiResponse = ApiResponse.success(
                HttpStatus.OK.value(),
                message,
                students
        );

        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable Long id) {
        return service.getStudentById(id)
                .map(student -> {
                    ApiResponse<Student> apiResponse = ApiResponse.success(
                            HttpStatus.OK.value(),
                            "Student details retrieved successfully.",
                            student
                    );
                    return ResponseEntity.ok(apiResponse);
                })
                .orElseGet(() -> {
                    ApiResponse<Student> apiResponse = ApiResponse.error(
                            HttpStatus.NOT_FOUND.value(),
                            "Student not found with ID: " + id
                    );
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
                });
    }



    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@PathVariable Long id, @RequestBody StudentDao studentDetails) {
        Student updatedStudent = service.updateStudent(id, studentDetails);

        if (updatedStudent != null) {
            ApiResponse<Student> apiResponse = ApiResponse.success(
                    HttpStatus.OK.value(),
                    "Student's data updated successfully.",
                    updatedStudent
            );
            return ResponseEntity.ok(apiResponse);
        }

        ApiResponse<Student> apiResponse = ApiResponse.error(
                HttpStatus.NOT_FOUND.value(),
                "Student not found with ID: " + id
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        if (service.deleteStudent(id)) {
            ApiResponse<Void> apiResponse = ApiResponse.success(
                    HttpStatus.OK.value(),
                    "Student deleted successfully.",
                    null
            );
            return ResponseEntity.ok(apiResponse);
        }

        ApiResponse<Void> apiResponse = ApiResponse.error(
                HttpStatus.NOT_FOUND.value(),
                "Student not found with ID: " + id
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
}
