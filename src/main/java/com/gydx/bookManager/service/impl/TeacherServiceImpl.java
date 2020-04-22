package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.entity.Teacher;
import com.gydx.bookManager.mapper.SchoolMapper;
import com.gydx.bookManager.mapper.TeacherMapper;
import com.gydx.bookManager.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public Teacher getTeacherInfo(String username) {
        Teacher t = new Teacher();
        t.setNumber(username);
        Teacher teacher = teacherMapper.selectOne(t);
        String schoolName = schoolMapper.selectNameById(teacher.getSchoolId());
        teacher.setSchoolName(schoolName);
        return teacher;
    }
}
