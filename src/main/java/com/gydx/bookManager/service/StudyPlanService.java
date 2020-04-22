package com.gydx.bookManager.service;

import com.gydx.bookManager.entity.MajorCourse;

import java.util.List;

public interface StudyPlanService {
    List<MajorCourse> getStudyPlanListByPage(Integer page, Integer limit);

    List<MajorCourse> getAllStudyPlanList();

    List<MajorCourse> getStudyPlanListByPageAndCondition(Integer page, Integer limit, String bookName, String majorName, String courseName);

    List<MajorCourse> getAllStudyPlanListByCondition(String bookName, String majorName, String courseName);

    int deleteOneStudyPlan(MajorCourse majorCourse);

    int updateStudyPlan(MajorCourse majorCourse);

    int deleteStudyPlans(List<MajorCourse> majorCourses);

    int addStudyPlan(MajorCourse majorCourse);

    List<MajorCourse> getStudyPlan(String majorName);
}
