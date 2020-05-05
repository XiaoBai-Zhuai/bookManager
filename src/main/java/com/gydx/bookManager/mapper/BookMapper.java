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

    int deleteOne(Book book);

    int updateBookStatus(Book book1);

    List<Book> getAllDBookName();

    List<Book> getAllDBookAuthor();

    List<Book> getAllDBookAuthorByBookName(@Param("bookName") String bookName);

    List<Book> getAllDBookPublishTimeByBookAuthor(@Param("bookName") String bookName, @Param("author") String author);

    List<Book> getAllDBookPublisherByBookPublishTime(@Param("bookName") String bookName, @Param("author")
            String author, @Param("publishTime") String publishTime);

    List<Book> getAllDBookPriceByBookPublisher(@Param("bookName") String bookName, @Param("author")
            String author, @Param("publishTime") String publishTime, @Param("publisher") String publisher);

    void updateStockSum(@Param("bookId") Integer bookId, @Param("bookSum") Integer bookSum);
}
