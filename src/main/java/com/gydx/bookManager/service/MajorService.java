package com.gydx.bookManager.service;

import com.gydx.bookManager.entity.Major;
import com.gydx.bookManager.pojo.MajorInfoPojo;

import java.util.List;

public interface MajorService {
    List<Major> getMajorListBySchool(Integer schoolId);

    List<Major> getMajorListByPage(Integer page, Integer limit);

    List<Major> getAllMajorList();

    List<Major> getMajorListByPageAndCondition(MajorInfoPojo majorInfoPojo);

    List<Major> getAllMajorListByCondition(MajorInfoPojo majorInfoPojo);

    int deleteOneMajor(Major major);

    int updateMajor(Major major);

    int deleteMajors(List<Major> majors);

    int addMajor(Major major);

    List<Major> getMajorNameBySchoolName(String schoolName);

    List<Major> getMajorListBySchoolName(String schoolName);
}
