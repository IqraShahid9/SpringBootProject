package com.example.demo.controller;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path="api/v2/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService){

        this.departmentService= departmentService;
    }

    @GetMapping(path = "{departmentId}")
    public Department getDepartment(@PathVariable("departmentId") Long departmentId){
        return departmentService.getDepeartmentWithTechers(departmentId);
    }

    @GetMapping
    public List<Department> getDepartment(){
        return departmentService.getDepartment();
    }

    @PostMapping
    public Department registerNewDepartment(@RequestBody Department department){
        return departmentService.addNewDepartment(department);
    }

    @DeleteMapping(path = "{departmentId}")
    public boolean deleteDepartment(@PathVariable("departmentId") Long departmentId){
        return departmentService.deleteDepartment(departmentId);
    }

    @PutMapping(path = "{departmentId}")
    public boolean updateDepartment(@PathVariable("departmentId") Long departmentId,
                              @RequestParam(required = false) String departmentName){

        return departmentService.updateDepartment(departmentId, departmentName);
    }

}
