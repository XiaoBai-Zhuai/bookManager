package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.MajorCourse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface MajorCourseMapper extends Mapper<MajorCourse> {
    int updateMajorIdByCourseId(@Param("majorName") String majorName, @Param("courseId") Integer courseId);

    List<MajorCourse> selectByPage(@Param("page") Integer page, @Param("limit") Integer limit);

    List<MajorCourse> selectByPageAndCondition(@Param("page") Integer page, @Param("limit") Integer limit, @Param("schoolName")
            String schoolName, @Param("majorName") String majorName, @Param("courseName") String courseName);

    List<MajorCourse> selectAllByCondition(@Param("schoolName") String schoolName, @Param("majorName")
            String majorName, @Param("courseName") String courseName);

    int deleteSome(@Param("majorCourses") List<MajorCourse> majorCourses);

    int updateOne(MajorCourse majorCourse);
}
