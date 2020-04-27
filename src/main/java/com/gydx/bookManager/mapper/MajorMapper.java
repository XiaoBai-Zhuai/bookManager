package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.Major;
import com.gydx.bookManager.pojo.MajorInfoPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface MajorMapper extends Mapper<Major> {
    String selectNameById(Integer majorId);

    Integer selectIdByName(String majorName);

    List<Major> selectByCourseId(Integer courseId);

    List<Major> findMajorListByPage(@Param("page") Integer page, @Param("limit") Integer limit);

    List<Major> findAllMajorList();

    List<Major> findMajorListByPageAndCondition(MajorInfoPojo majorInfoPojo);

    List<Major> findAllMajorListByCondition(MajorInfoPojo majorInfoPojo);

    int deleteOneMajor(Major major);

    int updateMajor(Major major);

    int deleteMajors(@Param("majors") List<Major> majors);

    int addMajor(Major major);

    List<Major> findMajorBySchool(String schoolName);
}
