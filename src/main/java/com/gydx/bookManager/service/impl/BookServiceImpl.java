package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.controller.ForgetController;
import com.gydx.bookManager.entity.MajorCourse;
import com.gydx.bookManager.mapper.CourseMapper;
import com.gydx.bookManager.mapper.MajorCourseMapper;
import com.gydx.bookManager.mapper.MajorMapper;
import com.gydx.bookManager.pojo.BookPageInfoPojo;
import com.gydx.bookManager.mapper.BookMapper;
import com.gydx.bookManager.entity.Book;
import com.gydx.bookManager.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private MajorMapper majorMapper;
    @Autowired
    private MajorCourseMapper majorCourseMapper;

    @Override
    public List<Book> getBookList(Integer page, Integer limit) {
        List<Book> books = null;
        try {
            books = bookMapper.selectBookList((page - 1) * limit, limit);
        } catch (Exception e) {
            logger.error("教材分页查询出错，错误：" + e);
        }
        return books;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookMapper.selectAll();
    }

    @Override
    public int deleteOneBookById(Integer id) {
        Book book = new Book();
        book.setId(id);
        return bookMapper.delete(book);
    }

    @Override
    public int updateBookInfo(Book book) {
        int i = 0;
        try {
            i = bookMapper.updateBook(book);
        } catch (Exception e) {
            logger.error("更新教材信息出错，错误：" + e);
        }
        return i;
    }

    @Override
    public int deleteBooks(List<Book> books) {
        int i = 0;
        try {
            i = bookMapper.deleteBatchById(books);
        } catch (Exception e) {
            logger.error("批量删除教材出错，错误：" + e);
        }
        return i;
    }

    @Override
    public int addBook(Book book) {
        return bookMapper.insertSelective(book);
    }

    @Override
    public List<Book> getBookListByCondition(BookPageInfoPojo bookPageInfoPojo) {
        bookPageInfoPojo.setPage((bookPageInfoPojo.getPage() - 1) * bookPageInfoPojo.getLimit());
        List<Book> books = null;
        try {
            books = bookMapper.selectByCondition(bookPageInfoPojo);
        } catch (Exception e) {
            logger.error("教材带条件分页查询出错，错误：" + e);
        }
        return books;
    }

    @Override
    public List<Book> getAllBooksByCondition(BookPageInfoPojo bookPageInfoPojo) {
        Book book = new Book();
        book.setName(bookPageInfoPojo.getName());
        book.setPublisher(bookPageInfoPojo.getPublisher());
        return bookMapper.select(book);
    }

    @Override
    public List<Book> getBookListByMajor(String majorName) {
        Integer majorId = 0;
        try {
            majorId = majorMapper.selectIdByName(majorName);
        } catch (Exception e) {
            logger.info("根据专业名获取专业id出错，错误：" + e);
        }
        MajorCourse mj = new MajorCourse();
        mj.setMajorId(majorId);
        List<MajorCourse> majorCourses = majorCourseMapper.select(mj);
        List<Book> books = null;
        try {
            books = bookMapper.selectByMajorCourse(majorCourses);
        } catch (Exception e) {
            logger.info("根据专业获取教材出错，错误：" + e);
        }
        return books;
    }
}
