package com.example.demo.service;


import com.example.demo.entity.Department;
import com.example.demo.repositories.DepartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class DepartmentService {
    private DepartmentRepository departmentRepository;

    @Autowired
    EntityManager em;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository){
        this.departmentRepository=departmentRepository;
    }

    public List<Department> getDepartment() {

        return departmentRepository.findAll();

    }

    public Department getDepeartmentWithTechers(Long id){
        EntityGraph graph=this.em.getEntityGraph("graph.Department.teachers");
        Map hints = new HashMap();
        hints.put("javax.persistence.loadgraph", graph);

        return this.em.find(Department.class, id,hints);
    }

    public Department addNewDepartment(Department department) {
        Optional<Department> departmentOptional = departmentRepository.findDepartmentByName(department.getName());
        if (departmentOptional.isPresent()){
            throw new IllegalStateException("name is taken");
        }
        return departmentRepository.save(department);

    }

    public Boolean deleteDepartment(Long departmentId) {
        boolean exists = departmentRepository.existsById(departmentId);

        if(!exists){
            throw new IllegalStateException("department with id" + departmentId + "does not exists");
        }
        departmentRepository.deleteById(departmentId);
        return true;
    }

    @Transactional
    public boolean updateDepartment(Long departmentId, String departmentName) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(()-> {
                    throw new IllegalStateException("department with id" + departmentId + "does not exists");
                });
        if (departmentName != null &&
                departmentName.length()>0 &&
                !Objects.equals(department.getName(),departmentName)){
            department.setName(departmentName);
        }
        return true;
    }
}
