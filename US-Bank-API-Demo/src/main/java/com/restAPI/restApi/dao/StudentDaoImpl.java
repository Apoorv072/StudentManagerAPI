package com.restAPI.restApi.dao;

import com.restAPI.restApi.Student;
import com.restAPI.restApi.rest.StudentNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.springframework.util.ReflectionUtils.findField;

@Repository
public class StudentDaoImpl implements StudentDao {

    private EntityManager entityManager;

    @Autowired // dependency injection
    public StudentDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Student> findAll() {
        TypedQuery<Student> theQuery = entityManager.createQuery("FROM Student", Student.class);
        return theQuery.getResultList();
    }

    @Override
    public Student findbyId(int id) {
        Student tempStudent = entityManager.find(Student.class, id);
        if (tempStudent == null) {
            throw new StudentNotFoundException("Student does not exist with ID : " + id);
        }
        return tempStudent;
    }

    @Transactional
    @Override
    public void deleteStudent(int id) {
        Student tempStudent = entityManager.find(Student.class, id);
        if (tempStudent == null) {
            throw new StudentNotFoundException("Student does not exist with ID : " + id);
        }
        entityManager.remove(tempStudent);
    }

    @Transactional
    @Override
    public Student save(Student theStudent) {
        Student tempStudent = entityManager.merge(theStudent);
        return tempStudent;
    }

    @Transactional
    @Override
    public Student upadteStudentWithMap(int id, Map<Object, Object> map) {
        Student student = entityManager.find(Student.class, id);
        if(student==null){
            throw new StudentNotFoundException("Student does not exist with ID : " + id);
        }
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            Field field = ReflectionUtils.findField(Student.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, student, value);
        }
        return entityManager.merge(student);
    }
}