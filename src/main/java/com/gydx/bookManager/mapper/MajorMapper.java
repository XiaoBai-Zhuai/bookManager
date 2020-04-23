package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.Major;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface MajorMapper extends Mapper<Major> {
    String selectNameById(Integer majorId);

    Integer selectIdByName(String majorName);

    List<Major> selectByCourseId(Integer courseId);
}
