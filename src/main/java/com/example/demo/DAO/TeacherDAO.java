package com.example.demo.DAO;

import com.example.demo.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TeacherDAO {
    @Autowired
    private EntityManager entityManager;



    @SuppressWarnings("unchecked")
    public Teacher getTeacherWithCourse(Integer input){
        EntityGraph entityGraph = entityManager.getEntityGraph("teacher-entity-graph");
        Map<String, Object> properties = new HashMap<>();


        properties.put("javax.persistence.loadgraph", entityGraph);
        Teacher teacher = entityManager.find(Teacher.class, Long.valueOf(input));
        System.out.println(teacher.getCourses());


        return teacher;
    }



    @SuppressWarnings("unchecked")
    public List<Teacher> getTeacherByDeptId(Integer input){

        return entityManager.createNamedStoredProcedureQuery("getTeacherFromDB").setParameter("dept_id",input).getResultList();
    }
}
