package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.Class;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ClassMapper extends Mapper<Class> {
    String selectNameById(Integer classId);
}
