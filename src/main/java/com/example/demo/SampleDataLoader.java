//package com.example.demo;
//
//import com.example.demo.entity.Student;
//import com.example.demo.entity.Teacher;
//import com.example.demo.repositories.StudentRepository;
//import com.example.demo.repositories.TeacherRepository;
//import com.github.javafaker.Faker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Component
//public class SampleDataLoader implements CommandLineRunner {
//
//    private final TeacherRepository teacherRepository;
//    private final Faker faker;
//
//    public SampleDataLoader(TeacherRepository teacherRepository,Faker faker) {
//        this.teacherRepository = teacherRepository;
//        this.faker = faker;
//    }
//
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        List<Teacher> teachers = IntStream.range(1,10)
//                .mapToObj(i -> new Teacher(
//                        faker.name().firstName(),
//                        faker.internet().emailAddress()
//                )).collect(Collectors.toList());
//        teacherRepository.saveAll(teachers);
//    }
//
//}
