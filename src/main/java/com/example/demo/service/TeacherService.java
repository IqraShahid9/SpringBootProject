package com.example.demo.service;


import com.example.demo.entity.Department;
import com.example.demo.entity.Teacher;
import com.example.demo.repositories.DepartmentRepository;
import com.example.demo.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeacherService {
    private TeacherRepository teacherRepository;



    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository){
        this.teacherRepository=teacherRepository;
    }

    public List<Teacher> getTeacher() {

        return teacherRepository.findAll();

    }

    public Teacher addNewStudent(Teacher teacher) {
        Optional<Teacher> studentOptional = teacherRepository.findTeacherByEmail(teacher.getEmail());
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        return teacherRepository.save(teacher);

    }

    public boolean deleteStudent(Long teacherId) {
        boolean exists = teacherRepository.existsById(teacherId);

        if(!exists){
            throw new IllegalStateException("teacher with id" + teacherId + "does not exists");
        }
        teacherRepository.deleteById(teacherId);
        return true;
    }

    @Transactional
    public boolean updateTeacher(Long teacherId, String teacherName, String teacherEmail) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(()-> {
                    throw new IllegalStateException("teacher with id" + teacherId + "does not exists");
                });
        if (teacherName != null &&
                teacherName.length()>0 &&
                !Objects.equals(teacher.getName(),teacherName)){
            teacher.setName(teacherName);
        }
        if (teacherEmail != null &&
                teacherEmail.length()>0 &&
                !Objects.equals(teacher.getEmail(),teacherEmail)){
            Optional<Teacher> teacherOptional= teacherRepository.findTeacherByEmail(teacherEmail);

            if (teacherOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            teacher.setEmail(teacherEmail);
        }
        return true;
    }


    public Teacher assignDepartmentToTeachers(Long teacherId, Long departmentId){
        Teacher teacher = teacherRepository.findById(teacherId).get();
        Department department = departmentRepository.findById(departmentId).get();
        teacher.assignDepartment(department);
        return teacherRepository.save(teacher);
    }

}
