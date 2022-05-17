package com.example.demo.entity;

import com.example.demo.repositories.CourseRepository;
import com.example.demo.repositories.StudentRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Data
@Entity
@Table
public class Student{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "enrolledStudents")
    private Set<Course> courses = new HashSet<>();

    private String name;
    private String email;

    public Student() {
    }

    public Student(Long id,
                   String name,
                   String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }

    public Student(String name,
                   String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
