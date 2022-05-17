package com.example.demo.controller;

import com.example.demo.entity.Course;


import com.example.demo.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v2/course")
public class CourseController {
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){

        this.courseService= courseService;
    }

    @GetMapping
    public List<Course> getCourse(){
        return courseService.getCourse();
    }

    @PostMapping
    public Course registerNewCourse(@RequestBody Course course){
        return courseService.addNewCourse(course);
    }

    @DeleteMapping(path = "{courseId}")
    public boolean deleteCourse(@PathVariable("courseId") Long courseId){
        return courseService.deleteCourse(courseId);
    }

    @PutMapping(path = "{courseId}")
    public boolean updateCourse(@PathVariable("courseId") Long courseId,
                                    @RequestParam(required = false) String courseName){

        return courseService.updateCourse(courseId, courseName);
    }

    @PutMapping(path = "{courseId}/department/{departmentId}")
    public Course assignDepartmentToCourse(@PathVariable("courseId") Long courseId,
                                              @PathVariable("departmentId") Long departmentId){

        return courseService.assignDepartmentToCourse(courseId, departmentId);
    }

    @PutMapping(path = "{courseId}/teacher/{teacherId}")
    public Course assignTeacherToCourse(@PathVariable("courseId") Long courseId,
                                           @PathVariable("teacherId") Long teacherId){

        return courseService.assignTeacherToCourse(courseId, teacherId);
    }

    @PutMapping(path = "{courseId}/student/{studentId}")
    public Course enrollStudentToCourse(@PathVariable("courseId") Long courseId,
                                        @PathVariable("studentId") Long studentId){

        return courseService.enrollStudentToCourse(courseId, studentId);
    }


}
