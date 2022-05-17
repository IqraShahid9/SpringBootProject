package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.persistence.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Entity
@Table
@NamedEntityGraph(name="graph.Department.teachers",
                attributeNodes = @NamedAttributeNode(value="teachers",subgraph = "teachers"),
                subgraphs = @NamedSubgraph(name="teachers", attributeNodes = @NamedAttributeNode("courses")))


public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_sequence")
    @SequenceGenerator(name = "department_sequence", sequenceName = "department_sequence", allocationSize = 1)
    @Column(name = "department_id", nullable = false)
    private Long department_id;
    private String name;



    @OneToMany(mappedBy = "department")
    private Set<Teacher> teachers = new HashSet<>();


    @OneToMany(mappedBy = "parentdepartment")
    private Set<Course> courses = new HashSet<>();


    public Department() {
    }

    public Department(Long department_id,
                   String name) {
        this.department_id = department_id;
        this.name = name;

    }

    public Department(String name) {
        this.name = name;
    }

    public Long getId() {
        return department_id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long department_id) {
        this.department_id = department_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Teacher> getTeacher() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + department_id +
                ", name='" + name + '\'' +
                '}';
    }



}
