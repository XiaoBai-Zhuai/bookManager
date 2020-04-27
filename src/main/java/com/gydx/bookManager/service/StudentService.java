package com.gydx.bookManager.service;

import com.gydx.bookManager.entity.Student;
import com.gydx.bookManager.pojo.StudentInfoPojo;

import java.util.List;

public interface StudentService {
    Student getStudentInfo(String username);

    List<Student> getStudentListByPage(Integer page, Integer limit);

    List<Student> getAllStudentList();

    List<Student> getStudentListByPageAndCondition(StudentInfoPojo studentInfoPojo);

    List<Student> getAllStudentListByCondition(StudentInfoPojo studentInfoPojo);

    int deleteOneStudent(Student student);

    int updateStudent(Student student);

    int addStudent(Student student);

    int deleteStudents(List<Student> students);
}
