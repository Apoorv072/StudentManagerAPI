package com.restAPI.restApi.rest;

import com.restAPI.restApi.Student;
import com.restAPI.restApi.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
public class StudentRestController { // Responsible for all HTTP Request
    public StudentDao studentDao;

    public StudentRestController(StudentDao studentDao) {
        this.studentDao = studentDao;
    }



    @QueryMapping("allStudents")
    List<Student> findAll() {
        return studentDao.findAll();
    }

    @QueryMapping("getStudent")
    Student findbyId(@Argument int studentId) {
       Student temp = studentDao.findbyId(studentId);
        System.out.println("Rest api"+temp);
        return temp;
    }

    @PostMapping("/students")
    Student save(@RequestBody Student student) {
        student.setId(0);
        Student thestudent = studentDao.save(student);
        System.out.println(thestudent);
        return thestudent;
    }

    @PutMapping("/students")
    void updateStudent(@RequestBody Student student) {
        studentDao.save(student);
    }

    @PatchMapping("/students/{id}")
    Student updateStudentWithMap(@PathVariable int id, @RequestBody Map<Object,Object> map){
        return studentDao.upadteStudentWithMap(id,map);
    }

    @DeleteMapping("/students/{studentId}")
    String deleteStudent(@PathVariable int studentId) {
        studentDao.deleteStudent(studentId);
        return "Student deleted with ID : " + studentId;
    }

}
