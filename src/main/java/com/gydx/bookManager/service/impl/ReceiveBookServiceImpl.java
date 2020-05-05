package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.entity.User;
import com.gydx.bookManager.mapper.BookMapper;
import com.gydx.bookManager.mapper.ClassBookMapper;
import com.gydx.bookManager.mapper.ClassMapper;
import com.gydx.bookManager.entity.Book;
import com.gydx.bookManager.entity.Class;
import com.gydx.bookManager.entity.ClassBook;
import com.gydx.bookManager.mapper.UserMapper;
import com.gydx.bookManager.service.ReceiveBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReceiveBookServiceImpl implements ReceiveBookService {

    private Logger logger = LoggerFactory.getLogger(ReceiveBookServiceImpl.class);

    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ClassBookMapper classBookMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 将查出来的教材领取情况添加上对应的教材名、班级名、负责人等信息
     * @param classBooks
     * @return
     */
    public List<ClassBook> getClassBook(List<ClassBook> classBooks) {
        for (ClassBook classBook : classBooks) {
            Integer classId = classBook.getClassId(); Integer bookId = classBook.getBookId();
            Class c = new Class();
            Book b = new Book();
            c.setId(classId); b.setId(bookId);
            Class aClass = classMapper.selectOne(c);
            Book book = bookMapper.selectOne(b);
            User u = new User();
            u.setId(aClass.getUserId());
            User user = userMapper.selectOne(u);
            classBook.setBookName(book.getName()); classBook.setClassName(aClass.getName());
            classBook.setPrincipalName(user.getNickname()); classBook.setPrincipalTel(user.getTel());
            classBook.setAuthor(book.getAuthor()); classBook.setPublishTime(book.getPublishTime());
            classBook.setPublisher(book.getPublisher()); classBook.setBookPrice(book.getPrice());
        }
        return classBooks;
    }

    /**
     * 分页查询出教材领取情况列表
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<ClassBook> getClassBookListByPage(Integer page, Integer limit) {
        List<ClassBook> classBooks = null;
        try {
            classBooks = classBookMapper.selectClassBookListByPage((page - 1) * limit, limit);
        } catch (Exception e) {
            logger.error("领书情况分页查询出错，错误：" + e);
        }
        if (classBooks == null) {
            return null;
        }
        classBooks = getClassBook(classBooks);
        return classBooks;
    }

    /**
     * 查询出所有的教材领取情况列表
     * @return
     */
    @Override
    public List<ClassBook> getAllClassBookList() {
        return classBookMapper.selectAll();
    }

    /**
     * 根据条件分页查询出教材领取情况列表
     * @param page
     * @param limit
     * @param receiveDate
     * @param bookName
     * @param className
     * @param schoolName
     * @return
     */
    @Override
    public List<ClassBook> getClassBookListByPageAndCondition(Integer page, Integer limit, String receiveDate, String bookName, String className, String schoolName) {
        List<ClassBook> classBooks = null;
        try {
            classBooks = classBookMapper.selectClassBookListByPageAndCondition((page - 1) * limit, limit,
                    receiveDate, bookName, className, schoolName);
        } catch (Exception e) {
            logger.error("领书情况带条件查询出错，错误：" + e);
        }
        if (classBooks == null) {
            return null;
        }
        classBooks = getClassBook(classBooks);
        return classBooks;
    }

    /**
     * 删除选择的一条
     * @param classBook
     * @return
     */
    @Override
    public int deleteOneClassBook(ClassBook classBook) {
        Book b = new Book();
        b.setId(classBook.getBookId());
        Book book = bookMapper.selectOne(b);
        //删除一条领书记录时将原来减掉的库存加回去
        bookMapper.updateStockSum(book.getId(), book.getStockSum() + classBook.getBookSum());
        return classBookMapper.delete(classBook);
    }

    /**
     * 更新某条领书情况
     * @param classBook
     * @return
     */
    @Override
    public int updateClassBook(ClassBook classBook) {
        int i = 0;
        ClassBook cb = new ClassBook();
        cb.setId(classBook.getId());
        ClassBook classBook1 = classBookMapper.selectOne(cb);
        Book b = new Book();
        b.setId(classBook1.getId());
        Book book = bookMapper.selectOne(b);
        Book b1 = new Book();
        b1.setPrice(classBook.getBookPrice()); b1.setPublishTime(classBook.getPublishTime());
        b1.setPublisher(classBook.getPublisher()); b1.setAuthor(classBook.getAuthor());
        b1.setName(classBook.getBookName());
        Book book1 = bookMapper.selectOne(b1);
        if (!book.getId().equals(book1.getId())) {
            //如果用户修改了教材，则将原来的教材的库存加回原来用户输入的领取数量
            bookMapper.updateStockSum(book.getId(), book.getStockSum() + classBook1.getBookSum());
            //同时新的教材要减去用户现在输入的领取数量
            bookMapper.updateStockSum(book1.getId(), book1.getStockSum() - classBook.getBookSum());
        } else {
            //如果用户未修改教材则将教材库存量减去用户现在输入的领取数量再加上原来的领取数量
            bookMapper.updateStockSum(book.getId(), book.getStockSum() - classBook.getBookSum() + classBook1.getBookSum());
        }
        try {
            i = classBookMapper.updateClassBook(classBook);
        } catch (Exception e) {
            logger.error("更新领书情况出错，错误：" + e);
        }
        return i;
    }

    /**
     * 批量删除选中的领书情况
     * @param classBooks
     * @return
     */
    @Override
    public int deleteClassBooks(List<ClassBook> classBooks) {
        for (ClassBook classBook : classBooks) {
            Book b = new Book();
            b.setId(classBook.getBookId());
            Book book = bookMapper.selectOne(b);
            bookMapper.updateStockSum(book.getId(), book.getStockSum() + classBook.getBookSum());
        }
        return classBookMapper.deleteClassBooks(classBooks);
    }

    /**
     * 新增领书情况
     * @param classBook
     * @return
     */
    @Transactional
    @Override
    public int addClassBook(ClassBook classBook) {
        classBookMapper.insertClassBook(classBook);
        ClassBook cb = new ClassBook();
        cb.setId(classBook.getId());
        ClassBook classBook1 = classBookMapper.selectOne(cb);
        Book book = new Book();
        book.setId(classBook1.getBookId());
        Book book1 = bookMapper.selectOne(book);
        book1.setStockSum(book1.getStockSum() - classBook.getBookSum());
        bookMapper.updateBook(book1);
        return 1;
    }

    /**
     * 根据条件查询出所有的符合条件的领书情况列表
     * @param receiveDate
     * @param bookName
     * @param className
     * @param schoolName
     * @return
     */
    @Override
    public List<ClassBook> getAllClassBookListByCondition(String receiveDate, String bookName, String className, String schoolName) {
        List<ClassBook> classBooks = null;
        try {
            classBooks = classBookMapper.selectAllClassBookListByCondition(receiveDate, bookName, className, schoolName);
        } catch (Exception e) {
            logger.info("领书情况带条件查询出错，错误：" + e);
        }
        return classBooks;
    }
}
