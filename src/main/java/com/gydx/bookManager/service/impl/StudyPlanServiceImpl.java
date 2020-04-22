package com.gydx.bookManager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.entity.Course;
import com.gydx.bookManager.entity.Major;
import com.gydx.bookManager.entity.MajorCourse;
import com.gydx.bookManager.mapper.*;
import com.gydx.bookManager.service.StudyPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudyPlanServiceImpl implements StudyPlanService {

    private Logger logger = LoggerFactory.getLogger(StudyPlanServiceImpl.class);

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private MajorMapper majorMapper;
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private MajorCourseMapper majorCourseMapper;

    public List<MajorCourse> getStudyPlan(List<MajorCourse> majorCourses) {
        for (MajorCourse majorCourse : majorCourses) {
            Integer courseId = majorCourse.getCourseId();
            Integer majorId = majorCourse.getMajorId();
            Course c = new Course(); Major m = new Major();
            c.setId(courseId); m.setId(majorId);
            Course course = courseMapper.selectOne(c);
            Major major = majorMapper.selectOne(m);
            Integer bookId = course.getBookId();
            Integer schoolId = major.getSchoolId();
            String bookName = bookMapper.selectNameById(bookId);
            String schoolName = schoolMapper.selectNameById(schoolId);
            majorCourse.setBookName(bookName); majorCourse.setSchoolName(schoolName);
            majorCourse.setHour(course.getHour()); majorCourse.setMajorName(major.getName());
            majorCourse.setCourseName(course.getName());
        }
        return majorCourses;
    }

    @Override
    public List<MajorCourse> getStudyPlanListByPage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<MajorCourse> majorCourses = null;
        try {
            majorCourses = majorCourseMapper.selectByPage(page, limit);
        } catch (Exception e) {
            logger.error("教学计划分页查询出错，错误：" + e);
        }
        majorCourses = getStudyPlan(majorCourses);
        return majorCourses;
    }

    @Override
    public List<MajorCourse> getAllStudyPlanList() {
        return majorCourseMapper.selectAll();
    }

    @Override
    public List<MajorCourse> getStudyPlanListByPageAndCondition(Integer page, Integer limit, String schoolName, String majorName, String courseName) {
        page = (page - 1) * limit;
        List<MajorCourse> MajorCourse = null;
        try {
            MajorCourse = majorCourseMapper.selectByPageAndCondition(page, limit, schoolName, majorName, courseName);
        } catch (Exception e) {
            logger.error("教学计划带条件分页查询出错，错误：" + e);
        }
        MajorCourse = getStudyPlan(MajorCourse);
        return MajorCourse;
    }

    @Override
    public List<MajorCourse> getAllStudyPlanListByCondition(String schoolName, String majorName, String courseName) {
        return majorCourseMapper.selectAllByCondition(schoolName, majorName, courseName);
    }

    @Override
    public int deleteOneStudyPlan(MajorCourse majorCourse) {
        return majorCourseMapper.delete(majorCourse);
    }

    @Transactional
    @Override
    public int updateStudyPlan(MajorCourse majorCourse) {
        Integer majorId = majorMapper.selectIdByName(majorCourse.getMajorName());
        if (majorId == null)
            return 0;
        Course course = new Course();
        Integer bookId = bookMapper.selectIdByName(majorCourse.getBookName());
        course.setBookId(bookId); course.setHour(majorCourse.getHour()); course.setName(majorCourse.getCourseName());
        Course course1 = courseMapper.selectOne(course);
        if (course1 == null) {
            courseMapper.insert(course);
            majorCourse.setMajorId(majorId); majorCourse.setCourseId(course.getId());
        } else {
            majorCourse.setMajorId(majorId); majorCourse.setCourseId(course1.getId());
        }
        majorCourseMapper.updateOne(majorCourse);
        return 1;
    }

    @Override
    public int deleteStudyPlans(List<MajorCourse> majorCourses) {
        majorCourseMapper.deleteSome(majorCourses);
        return 1;
    }

    @Transactional
    @Override
    public int addStudyPlan(MajorCourse majorCourse) {
        Integer majorId = majorMapper.selectIdByName(majorCourse.getMajorName());
        if (majorId == null)
            return 0;
        Course course = new Course();
        Integer bookId = bookMapper.selectIdByName(majorCourse.getBookName());
        course.setBookId(bookId); course.setHour(majorCourse.getHour()); course.setName(majorCourse.getCourseName());
        Course course1 = courseMapper.selectOne(course);
        if (course1 == null) {
            courseMapper.insert(course);
            majorCourse.setMajorId(majorId); majorCourse.setCourseId(course.getId());
        } else {
            majorCourse.setMajorId(majorId); majorCourse.setCourseId(course1.getId());
        }
        majorCourseMapper.insert(majorCourse);
        return 1;
    }

    @Override
    public List<MajorCourse> getStudyPlan(String majorName) {
        Integer majorId = majorMapper.selectIdByName(majorName);
        MajorCourse mc = new MajorCourse();
        mc.setMajorId(majorId);
        List<MajorCourse> majorCourses = majorCourseMapper.select(mc);
        majorCourses = getStudyPlan(majorCourses);
        return majorCourses;
    }
}
