package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.Student;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface StudentMapper extends Mapper<Student> {
}
