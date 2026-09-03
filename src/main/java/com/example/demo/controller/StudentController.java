package com.example.demo.controller;

import com.example.demo.dto.StudentDto;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import com.example.demo.dto.ApiResponse;
import com.example.demo.utils.Mapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addStudent(@Valid @RequestBody StudentDto studentDto) {
        Student studentEntity = Mapper.toEntity(studentDto);
        Student savedStudent = studentService.addStudent(studentEntity);
        StudentDto responseDto = Mapper.toDto(savedStudent);
        ApiResponse<StudentDto> apiResponse = ApiResponse.success(
                HttpStatus.CREATED.value(),
                "Student's data created successfully.",
                responseDto
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();

        String message = students.isEmpty() ? "No student found in the database." : "Students retrieved successfully.";
        ApiResponse<List<Student>> apiResponse = ApiResponse.success(
                HttpStatus.OK.value(),
                message,
                students
        );
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(Mapper::toDto) // Convert Entity to DTO if found
                .map(dto -> {
                    ApiResponse<StudentDto> apiResponse = ApiResponse.success(
                            HttpStatus.OK.value(),
                            "Student details retrieved successfully.",
                            dto
                    );
                    return ResponseEntity.ok(apiResponse);
                })
                .orElseGet(() -> {
                    ApiResponse<StudentDto> apiResponse = ApiResponse.error(
                            HttpStatus.NOT_FOUND.value(),
                            "Student not found with ID: " + id
                    );
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
                });
    }



    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> updateStudentCourse(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {

        // Extract the "course" field safely from the JSON request body
        String newCourse = requestBody.get("course");

        Student updatedStudent = studentService.updateStudentCourse(id, newCourse);

        if (updatedStudent != null) {
            StudentDto responseDto = Mapper.toDto(updatedStudent);
            ApiResponse<StudentDto> apiResponse = ApiResponse.success(
                    HttpStatus.OK.value(),
                    "Student's course updated successfully.",
                    responseDto
            );
            return ResponseEntity.ok(apiResponse);
        }

        ApiResponse<StudentDto> apiResponse = ApiResponse.error(
                HttpStatus.NOT_FOUND.value(),
                "Student not found with ID: " + id
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        if (studentService.deleteStudent(id)) {
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
