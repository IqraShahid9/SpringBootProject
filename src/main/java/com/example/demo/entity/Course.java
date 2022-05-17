package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "course_sequence")
    @SequenceGenerator(name = "course_sequence", sequenceName = "course_sequence", allocationSize = 1)
    @Column(name = "course_id", nullable = false)
    private Long id;
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="student_course",
            joinColumns= @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name="student_id")
    )
    private Set<Student> enrolledStudents = new HashSet<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="department_id", referencedColumnName = "department_id")
    private Department parentdepartment;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="teacher_id", referencedColumnName = "id")
    private Teacher teacher;


    public Course() {
    }

    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Course(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getParentdepartment() {
        return parentdepartment;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Set<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void assignDepartment(Department department) {
        this.parentdepartment=department;
    }

    public void assignTeacher(Teacher teacher) {
        this.teacher=teacher;
    }


    public void assignStudent(Student student) {
        enrolledStudents.add(student);
    }
}
