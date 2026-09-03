package com.example.demo.utils;

import com.example.demo.dto.StudentDto;
import com.example.demo.model.Student;

public class Mapper {
    public static Student toEntity(StudentDto dto) {
        if (dto == null) {
            return null;
        }

        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
        student.setCourse(dto.getCourse());

        return student;
    }

    // Convert Entity -> DTO (For outgoing API response payloads)
    public static StudentDto toDto(Student student) {
        if (student == null) {
            return null;
        }

        StudentDto dto = new StudentDto();
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setAge(student.getAge());
        dto.setCourse(student.getCourse());

        return dto;
    }
}
