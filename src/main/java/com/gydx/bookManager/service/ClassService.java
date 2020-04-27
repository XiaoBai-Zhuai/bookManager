package com.gydx.bookManager.service;

import com.gydx.bookManager.entity.Class;
import com.gydx.bookManager.pojo.ClassInfoPojo;

import java.util.List;

public interface ClassService {
    List<Class> getClassListByPage(Integer page, Integer limit);

    List<Class> getAllClassList();

    List<Class> getClassListByPageAndCondition(ClassInfoPojo classInfoPojo);

    List<Class> getAllClassListByCondition(ClassInfoPojo classInfoPojo);

    int deleteOneClass(Class c);

    int updateClass(Class c);

    int deleteClasses(List<Class> classes);

    int addClass(Class c);

    List<Class> getClassNameByMajorName(String majorName);
}
