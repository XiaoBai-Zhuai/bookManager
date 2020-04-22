package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.Course;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CourseMapper extends Mapper<Course> {
    List<Course> selectCourseListByPage(@Param("page") Integer page,@Param("limit") Integer limit);

    int updateCourse(Course course);
}
