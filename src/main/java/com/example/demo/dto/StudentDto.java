package com.example.demo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class StudentDto {

    @NotBlank(message = "Name cannot be blank or empty.")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email format is not valid.")
    private String email;

    @Min(value = 1, message = "Age must be greater than 0")
    @Max(value = 200, message = "Age must be smaller than 200")
    private Integer age;

    private String course;

    public StudentDto() {}

    public StudentDto(String name, String email, Integer age, String course) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
}