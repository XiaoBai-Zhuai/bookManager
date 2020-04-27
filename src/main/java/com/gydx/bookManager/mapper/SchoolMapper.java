package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.School;
import com.gydx.bookManager.pojo.SchoolInfoPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SchoolMapper extends Mapper<School> {
    String selectNameById(Integer schoolId);

    int updateUserIdById(@Param("userId") Integer userId, @Param("schoolId") Integer schoolId);

    List<School> findSchoolListByPage(@Param("page") Integer page, @Param("limit") Integer limit);

    List<School> findAll();

    List<School> findSchoolListByPageAndCondition(SchoolInfoPojo schoolInfoPojo);

    List<School> findAllSchoolListByCondition(SchoolInfoPojo schoolInfoPojo);

    int deleteOneSchool(School school);

    int updateSchool(School school);

    int deleteSchools(@Param("schools") List<School> schools);

    int addSchool(School school);

    int updateSchoolStatus(School school);

}
