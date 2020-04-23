package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.School;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SchoolMapper extends Mapper<School> {
    String selectNameById(Integer schoolId);
}
