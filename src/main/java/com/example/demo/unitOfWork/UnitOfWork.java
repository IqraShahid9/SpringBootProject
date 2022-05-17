package com.example.demo.unitOfWork;

import com.example.demo.entity.Student;
import com.example.demo.repositories.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnitOfWork implements IUnitOfWork<Student> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentRepository.class);

    private Map<String, List<Student>> context;
    private StudentRepository studentRepository;


    public UnitOfWork (Map<String, List<Student>> context, StudentRepository studentRepository) {
        this.context = context;
        this.studentRepository = studentRepository;
    }

    @Override
    public void registerNew(Student student) {
        LOGGER.info("Registering {} for insert in context.", student.getName());
        register(student, IUnitOfWork.INSERT);
    }

    @Override
    public void registerModified(Student student) {
        LOGGER.info("Registering {} for modify in context.", student.getName());
        register(student, IUnitOfWork.MODIFY);

    }

    @Override
    public void registerDelete(Student student) {
        LOGGER.info("Registering {} for delete in context.", student.getName());
        register(student, IUnitOfWork.DELETE);
    }

    private void register(Student student, String operation) {
        List<Student> studentsToOperate = context.get(operation);
        if (studentsToOperate == null) {
            studentsToOperate = new ArrayList<>();
        }
        studentsToOperate.add(student);
        context.put(operation, studentsToOperate);
    }

    /**
     * All UnitOfWork operations are batched and executed together on commit only.
     */
    @Override
    public void commit() {
        if (context == null || context.size() == 0) {
            return;
        }
        LOGGER.info("Commit started");
        if (context.containsKey(IUnitOfWork.INSERT)) {
            commitInsert();
        }

        if (context.containsKey(IUnitOfWork.MODIFY)) {
            commitModify();
        }
        if (context.containsKey(IUnitOfWork.DELETE)) {
            commitDelete();
        }
        LOGGER.info("Commit finished.");
    }

    private void commitInsert() {
        List<Student> studentsToBeInserted = context.get(IUnitOfWork.INSERT);
        for (Student student : studentsToBeInserted) {
            LOGGER.info("Saving {} to database.", student.getName());
            studentRepository.save(student);
        }
    }

    private void commitModify() {
        List<Student> modifiedStudents = context.get(IUnitOfWork.MODIFY);
        for (Student student : modifiedStudents) {
            LOGGER.info("Modifying {} to database.", student.getName());

        }
    }

    private void commitDelete() {
        List<Student> deletedStudents = context.get(IUnitOfWork.DELETE);
        for (Student student : deletedStudents) {
            LOGGER.info("Deleting {} to database.", student.getName());
            studentRepository.delete(student);
        }
    }
}

