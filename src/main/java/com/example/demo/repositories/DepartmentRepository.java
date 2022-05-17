package com.example.demo.repositories;

import com.example.demo.entity.Department;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Optional<Department> findDepartmentByName(String name);
}

