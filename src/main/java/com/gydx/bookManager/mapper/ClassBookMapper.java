package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.ClassBook;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ClassBookMapper extends Mapper<ClassBook> {
    List<ClassBook> selectClassBookListByPage(@Param("page") Integer page, @Param("limit") Integer limit);

    List<ClassBook> selectClassBookListByPageAndCondition(@Param("page") Integer page, @Param("limit") Integer limit, @Param("receiveDate")
            String receiveDate, @Param("bookName") String bookName, @Param("className") String className);

    List<ClassBook> selectAllClassBookListByCondition(@Param("receiveDate") String receiveDate, @Param("bookName")
            String bookName, @Param("className") String className);

    int updateClassBook(ClassBook classBook);

    int deleteClassBooks(@Param("classBooks") List<ClassBook> classBooks);

    int insertClassBook(ClassBook classBook);
}
