package com.gydx.bookManager.service;

import com.gydx.bookManager.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCourseListBySchoolName(String schoolName);

    List<Course> getCourseListByMajorName(String majorName, String schoolName);

    List<Course> getAllCourseList();
}
