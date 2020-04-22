package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.entity.Student;
import com.gydx.bookManager.mapper.ClassMapper;
import com.gydx.bookManager.mapper.MajorMapper;
import com.gydx.bookManager.mapper.SchoolMapper;
import com.gydx.bookManager.mapper.StudentMapper;
import com.gydx.bookManager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private MajorMapper majorMapper;
    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public Student getStudentInfo(String username) {
        Student s = new Student();
        s.setNumber(username);
        Student student = studentMapper.selectOne(s);
        String schoolName = schoolMapper.selectNameById(student.getSchoolId());
        String majorName = majorMapper.selectNameById(student.getMajorId());
        String className = classMapper.selectNameById(student.getClassId());
        student.setSchoolName(schoolName); student.setMajorName(majorName); student.setClassName(className);
        return student;
    }
}
