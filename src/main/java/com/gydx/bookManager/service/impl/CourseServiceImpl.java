package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.entity.Course;
import com.gydx.bookManager.mapper.CourseMapper;
import com.gydx.bookManager.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseMapper courseMapper;

    /**
     * 根据学院名查出该学院下所有的课程列表
     *
     * @param schoolName
     * @return
     */
    @Override
    public List<Course> getCourseListBySchoolName(String schoolName) {
        List<Course> courseList = null;
        try {
            courseList = courseMapper.getCourseListBySchoolName(schoolName);
        } catch (Exception e) {
            logger.error("根据学院名查询课程列表出错，错误：" + e);
        }
        return courseList;
    }

    /**
     * 根据专业名查询出该专业下的所有课程列表
     * @param majorName
     * @return
     */
    @Override
    public List<Course> getCourseListByMajorName(String majorName, String schoolName) {
        List<Course> courseList = null;
        try {
            courseList = courseMapper.getCourseListByMajorName(majorName, schoolName);
        } catch (Exception e) {
            logger.error("根据专业名查询课程列表出错，错误：" + e);
        }
        return courseList;
    }

    /**
     * 获取全部课程列表
     * @return
     */
    @Override
    public List<Course> getAllCourseList() {
        List<Course> courseList = null;
        try {
            courseList = courseMapper.findAll();
        } catch (Exception e) {
            logger.error("获取全部课程列表出错，错误：" + e);
        }
        return courseList;
    }
}
