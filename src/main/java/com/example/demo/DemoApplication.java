package com.example.demo;


import com.example.demo.entity.Student;
import com.example.demo.unitOfWork.UnitOfWork;
import com.example.demo.unitOfWork.StudentDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class DemoApplication {




	public static void main(String[] args) {


				SpringApplication.run(DemoApplication.class, args);

	}
}




//		Student ram = new Student("Ram", "Street 9, Cupertino");
//		Student shyam = new Student ("Shyam", "Z bridge, Pune");
//		Student gopi = new Student( "Gopi", "Street 10, Mumbai");
//
//		HashMap<String, List<Student>> context = new HashMap<>();
//		StudentDatabase studentDatabase = new StudentDatabase();
//		UnitOfWork unitOfwork = new UnitOfWork (context, studentDatabase);
//
//		unitOfwork .registerNew(ram);
//		unitOfwork .registerModified(shyam);
//		unitOfwork .registerDelete(gopi);
//		unitOfwork .commit();