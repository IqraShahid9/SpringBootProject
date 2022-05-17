package com.example.demo.service;

import com.example.demo.entity.Course;

import com.example.demo.entity.Department;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
import com.example.demo.repositories.CourseRepository;

import com.example.demo.repositories.DepartmentRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository){
        this.courseRepository=courseRepository;
    }

    public List<Course> getCourse() {
        return courseRepository.findAll();

    }

    public Course addNewCourse(Course course) {
        Optional<Course> courseOptional = courseRepository.findCourseByName(course.getName());
        if (courseOptional.isPresent()){
            throw new IllegalStateException("name is taken");
        }
        return courseRepository.save(course);

    }

    public Boolean deleteCourse(Long courseId) {
        boolean exists = courseRepository.existsById(courseId);

        if(!exists){
            throw new IllegalStateException("course with id" + courseId + "does not exists");
        }
        courseRepository.deleteById(courseId);
        return true;
    }

    @Transactional
    public boolean updateCourse(Long courseId, String courseName) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> {
                    throw new IllegalStateException("course with id" + courseId + "does not exists");
                });
        if (courseName != null &&
                courseName.length()>0 &&
                !Objects.equals(course.getName(),courseName)){
            course.setName(courseName);
        }
        return true;
    }

    public Course assignDepartmentToCourse(Long courseId, Long departmentId){
        Course course = courseRepository.findById(courseId).get();
        Department department = departmentRepository.findById(departmentId).get();
        course.assignDepartment(department);
        return courseRepository.save(course);
    }

    public Course assignTeacherToCourse(Long courseId, Long teacherId){
        Course course = courseRepository.findById(courseId).get();
        Teacher teacher = teacherRepository.findById(teacherId).get();
        course.assignTeacher(teacher);
        return courseRepository.save(course);
    }

    public Course enrollStudentToCourse(Long courseId, Long studentId) {

        Course course = courseRepository.findById(courseId).get();
        Student student = studentRepository.findById(studentId).get();
        log.info("Student {} enrolled in Course {}",student.getName(),course.getName());
        course.assignStudent(student);
        return courseRepository.save(course);
    }
}
