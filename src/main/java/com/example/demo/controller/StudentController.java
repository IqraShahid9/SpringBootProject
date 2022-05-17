package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path="api/v2/student")
public class StudentController {


    private static Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService= studentService;
    }
    @GetMapping
    public List<Student> getStudents(){
        log.info("get Students Start");
        return studentService.getStudents();
    }

    @GetMapping(path = "/names")
    public CompletableFuture<List<String>> getStudentsNames(){
        long start = System.currentTimeMillis();
        log.info("get Students Names");
        long end = System.currentTimeMillis();
        log.info("Time is :{}",(end-start));
        return studentService.getStudentsNames();
    }

    @GetMapping(path = "/emails")
    public CompletableFuture<List<String>> getStudentsEmails(){
        log.info("get Students Emails");
        return studentService.getStudentsEmails();
    }

    @GetMapping(path = "/namesByThread")
    public ResponseEntity getNames(){
        long start = System.currentTimeMillis();
        CompletableFuture<List<String>> students=studentService.getStudentsNames();

        CompletableFuture<List<String>> students1=studentService.getStudentsNames();
        CompletableFuture<List<String>> students2=studentService.getStudentsNames();

        CompletableFuture.allOf(students1,students,students2).join();
        long end = System.currentTimeMillis();
        log.info("Time is :{}",(end-start));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public Student registerNewStudent(@RequestBody Student student) throws Exception {


        return studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public boolean deleteStudent(@PathVariable("studentId") Long studentId){
        return studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public boolean updateStudent(@PathVariable("studentId") Long studentId,
                                 @RequestParam(required = false) String studentName,
                                 @RequestParam(required = false) String studentEmail){

        return studentService.updateStudent(studentId, studentName, studentEmail);
    }

    @RequestMapping(value = "/page/{pageNumber}/pageSize/{pageSize}",method = RequestMethod.GET)
    public Page<Student> studentPagination(@PathVariable Integer pageNumber, @PathVariable Integer pageSize){

        return studentService.getEmployeePagination(pageNumber,pageSize);
    }
}
