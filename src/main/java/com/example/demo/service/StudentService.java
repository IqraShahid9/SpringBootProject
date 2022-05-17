package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.repositories.StudentRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private StudentRepository studentRepository;

    private String message;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public List<Student> getStudents() {
        return studentRepository.findAll();

    }

    @Async("asyncExecutor")
    public CompletableFuture<List<String>>getStudentsNames() {
        logger.info("Getting names by {}",Thread.currentThread().getName());
        List<Student> students = studentRepository.findAll();
        long start = System.currentTimeMillis();
        List<String> names = new ArrayList<String>();
        for (Student s:students) {

            names.add(s.getName());
        }

        return CompletableFuture.completedFuture(names);

    }

    @Async("asyncExecutor")
    public CompletableFuture<List<String>> getStudentsEmails() {
        logger.info("Getting emails by {}",Thread.currentThread().getName());
        List<Student> students = studentRepository.findAll();
        List<String> emails = new ArrayList<String>();
        for (Student s:students) {
            emails.add(s.getEmail());
        }
        return CompletableFuture.completedFuture(emails);

    }

    public Student addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        Student returnStudent= studentRepository.save(student);
        if(returnStudent!=null) {
             message=emailSenderService.sendSimpleEmail("ishahid.bese17seecs@seecs.edu.pk", "Your Account has been registered", "Springboot: Account Registered");
        }
        else {
            message="Email not sent";
        }

        return returnStudent;

    }

    public boolean deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if(!exists){
            throw new IllegalStateException("student with id" + studentId + "does not exists");
        }
        studentRepository.deleteById(studentId);
        return true;
    }

    @Transactional
    public boolean updateStudent(Long studentId, String studentName, String studentEmail) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> {
                    throw new IllegalStateException("student with id" + studentId + "does not exists");
                });
        if (studentName != null &&
                studentName.length()>0 &&
                !Objects.equals(student.getName(),studentName)){
            student.setName(studentName);
        }
        if (studentEmail != null &&
                studentEmail.length()>0 &&
                !Objects.equals(student.getEmail(),studentEmail)){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(studentEmail);

            if (studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(studentEmail);

        }
        return true;
    }


    public Page<Student> getEmployeePagination(Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.ASC,"name");
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        return studentRepository.findAll(pageable);
    }
}