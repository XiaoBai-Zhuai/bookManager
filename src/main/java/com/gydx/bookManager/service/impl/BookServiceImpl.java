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

    /**
     * 教材列表分页查询
     * @param page
     * @param limit
     * @return
     */
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

    /**
     * 获取全部教材列表
     * @return
     */
    @Override
    public List<Book> getAllBooks() {
        Book book = new Book();
        book.setStatus(1);
        return bookMapper.select(book);
    }

    /**
     * 根据教材id删除某本教材
     * @param id
     * @return
     */
    @Override
    public int deleteOneBookById(Integer id) {
        Book book = new Book();
        book.setId(id);
        return bookMapper.deleteOne(book);
    }

    /**
     * 修改教材信息
     * @param book
     * @return
     */
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

    /**
     * 批量删除教材
     * @param books
     * @return
     */
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

    /**
     * 添加教材
     * @param book
     * @return
     */
    @Override
    public int addBook(Book book) {
        Book b = new Book();
        b.setName(book.getName()); b.setAuthor(book.getAuthor());
        b.setPublisher(book.getPublisher()); b.setPublishTime(book.getPublishTime());
        Book book1 = bookMapper.selectOne(b);
        if (book1 != null) {
            if (book1.getStatus() == 0) {
                bookMapper.updateBookStatus(book1);
                return 1;
            } else if(book1.getStatus() == 1) {
                return 0;
            }
        } else {
            bookMapper.insertSelective(book);
        }
        return 1;
    }

    /**
     * 根据条件分页查询出教材
     * @param bookPageInfoPojo
     * @return
     */
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

    /**
     * 根据条件查询出所有的教材
     * @param bookPageInfoPojo
     * @return
     */
    @Override
    public List<Book> getAllBooksByCondition(BookPageInfoPojo bookPageInfoPojo) {
        Book book = new Book();
        book.setStatus(1);
        book.setName(bookPageInfoPojo.getName());
        book.setPublisher(bookPageInfoPojo.getPublisher());
        return bookMapper.select(book);
    }

    /**
     * 根据专业名获取该专业下应该用到的教材，在班级负责人提交教材领取情况时使用
     * @param majorName
     * @return
     */
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

    /**
     * 获取全部教材列表
     * @return
     */
    @Override
    public List<Book> getAllBookList() {
        List<Book> books = null;
        try {
            Book b = new Book();
            b.setStatus(1);
            books = bookMapper.select(b);
        } catch (Exception e) {
            logger.error("获取全部教材列表出错，错误：" + e);
        }
        return books;
    }

    /**
     * 查询出所有教材中不重复的教材名
     * @return
     */
    @Override
    public List<Book> getAllDBookName() {
        List<Book> books = null;
        try {
            books = bookMapper.getAllDBookName();
        } catch (Exception e) {
            logger.error("查询全部不重复的教材名列表出错，错误：" + e);
        }
        return books;
    }

    /**
     * 查询出所有教材里不重复的作者名
     * @return
     */
    @Override
    public List<Book> getAllDBookAuthor() {
        List<Book> books = null;
        try {
            books = bookMapper.getAllDBookAuthor();
        } catch (Exception e) {
            logger.error("查询全部不重复的作者名出错，错误：" + e);
        }
        return books;
    }

    /**
     * 根据教材名查询出所有的作者
     * @param bookName
     * @return
     */
    @Override
    public List<Book> getAllDBookAuthorByBookName(String bookName) {
        List<Book> books = null;
        try {
            books = bookMapper.getAllDBookAuthorByBookName(bookName);
        } catch (Exception e) {
            logger.error("根据教材名查询出所有的作者出错，错误：" + e);
        }
        return books;
    }

    /**
     * 根据教材名和作者查询出所有的出版时间
     * @param bookName
     * @param author
     * @return
     */
    @Override
    public List<Book> getAllDBookPublishTimeByBookAuthor(String bookName, String author) {
        List<Book> books = null;
        try {
            books = bookMapper.getAllDBookPublishTimeByBookAuthor(bookName, author);
        } catch (Exception e) {
            logger.error("根据教材名和作者查询出所有的出版时间出错，错误：" + e);
        }
        return books;
    }

    /**
     * 根据教材名和作者和出版日期查询出所有的出版社名字
     * @param bookName
     * @param author
     * @param publishTime
     * @return
     */
    @Override
    public List<Book> getAllDBookPublisherByBookPublishTime(String bookName, String author, String publishTime) {
        List<Book> books = null;
        try {
            books = bookMapper.getAllDBookPublisherByBookPublishTime(bookName, author, publishTime);
        } catch (Exception e) {
            logger.error("根据教材名和作者和出版日期查询出所有的出版社名字出错，错误：" + e);
        }
        return books;
    }

    /**
     * 根据教材名和作者和出版日期和出版社名查询出所有的教材定价
     * @param bookName
     * @param author
     * @param publishTime
     * @param publisher
     * @return
     */
    @Override
    public List<Book> getAllDBookPriceByBookPublisher(String bookName, String author, String publishTime, String publisher) {
        List<Book> books = null;
        try {
            books = bookMapper.getAllDBookPriceByBookPublisher(bookName, author, publishTime, publisher);
        } catch (Exception e) {
            logger.error("根据教材名和作者和出版日期和出版社名查询出所有的教材定价出错，错误：" + e);
        }
        return books;
    }
}
