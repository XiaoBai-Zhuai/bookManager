package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.Class;
import com.gydx.bookManager.pojo.ClassInfoPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ClassMapper extends Mapper<Class> {
    String selectNameById(Integer classId);

    int updateUserIdById(@Param("userId") Integer userId, @Param("classId") Integer classId);

    List<Class> findClassListByPage(@Param("page") Integer page, @Param("limit") Integer limit);

    List<Class> findClassListByPageAndCondition(ClassInfoPojo classInfoPojo);

    List<Class> findAllClassListByCondition(ClassInfoPojo classInfoPojo);

    List<Class> findAllClassList();

    int deleteOneClass(Class c);

    int updateClass(Class c);

    int deleteClasses(@Param("classes") List<Class> classes);

    int addClass(Class c);

    int updateClassStatus(Class aClass1);

    List<Class> findClassByMajor(String majorName);

    List<Class> getAllClassListBySchoolName(@Param("schoolName") String schoolName);

    List<Class> getAllClassListByMajorName(@Param("majorName") String majorName, @Param("schoolName") String schoolName);
}
