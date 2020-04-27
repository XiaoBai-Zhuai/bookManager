package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface TeacherMapper extends Mapper<Teacher> {
    int updateSchoolIdById(@Param("schoolId") Integer schoolId, @Param("teacherId") Integer teacherId);
}
