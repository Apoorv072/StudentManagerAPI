package com.restAPI.restApi.dao;

import com.restAPI.restApi.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface StudentDao {

    Student save(Student theStudent);

    List<Student> findAll();

    Student findbyId(int id);

    void deleteStudent(int id);

    Student upadteStudentWithMap(int id, Map<Object, Object> map);
}