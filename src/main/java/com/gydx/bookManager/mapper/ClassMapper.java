package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.Class;
import tk.mybatis.mapper.common.Mapper;

public interface ClassMapper extends Mapper<Class> {
    String selectNameById(Integer classId);
}
