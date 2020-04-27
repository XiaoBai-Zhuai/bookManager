package com.gydx.bookManager.service;

import com.gydx.bookManager.entity.School;
import com.gydx.bookManager.pojo.SchoolInfoPojo;

import java.util.List;

public interface SchoolService {
    List<School> getSchoolListByPage(Integer page, Integer limit);

    List<School> getAllSchoolList();

    List<School> getSchoolListByPageAndCondition(SchoolInfoPojo schoolInfoPojo);

    List<School> getAllSchoolListByCondition(SchoolInfoPojo schoolInfoPojo);

    int deleteOneSchool(School school);

    int updateSchool(School school);

    int deleteSchools(List<School> schools);

    int addSchool(School school);
}
