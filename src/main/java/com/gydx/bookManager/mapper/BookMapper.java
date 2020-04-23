package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.MajorCourse;
import com.gydx.bookManager.pojo.BookPageInfoPojo;
import com.gydx.bookManager.entity.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface BookMapper extends Mapper<Book> {
    List<Book> selectBookList(@Param("page") Integer page,@Param("limit") Integer limit);

    int updateBook(Book book);

    int deleteBatchById(@Param("books") List<Book> books);

    List<Book> selectByCondition(BookPageInfoPojo bookPageInfoPojo);

    String selectNameById(int bookId);

    Book selectByStockInId(Integer id);

    Integer selectIdByName(String bookName);

    List<Book> selectByMajorCourse(@Param("majorCourses") List<MajorCourse> majorCourses);
}
