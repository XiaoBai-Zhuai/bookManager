package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.School;
import tk.mybatis.mapper.common.Mapper;

public interface SchoolMapper extends Mapper<School> {
    String selectNameById(Integer schoolId);
}
