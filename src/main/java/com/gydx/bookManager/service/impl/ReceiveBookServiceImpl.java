package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.mapper.BookMapper;
import com.gydx.bookManager.mapper.ClassBookMapper;
import com.gydx.bookManager.mapper.ClassMapper;
import com.gydx.bookManager.entity.Book;
import com.gydx.bookManager.entity.Class;
import com.gydx.bookManager.entity.ClassBook;
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
    ClassMapper classMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    ClassBookMapper classBookMapper;

    public List<ClassBook> getClassBook(List<ClassBook> classBooks) {
        for (ClassBook classBook : classBooks) {
            Integer classId = classBook.getClassId(); Integer bookId = classBook.getBookId();
            Class c = new Class();
            Book b = new Book();
            c.setId(classId); b.setId(bookId);
            Class aClass = classMapper.selectOne(c);
            Book book = bookMapper.selectOne(b);
            classBook.setBookName(book.getName()); classBook.setClassName(aClass.getName());
            classBook.setPrincipalName(aClass.getPrincipalName()); classBook.setPrincipalTel(aClass.getPrincipalTel());
        }
        return classBooks;
    }

    @Override
    public List<ClassBook> getClassBookListByPage(Integer page, Integer limit) {
        List<ClassBook> classBooks = null;
        try {
            classBooks = classBookMapper.selectClassBookListByPage((page - 1) * limit, limit);
        } catch (Exception e) {
            logger.error("领书情况分页查询出错，错误：" + e);
        }
        classBooks = getClassBook(classBooks);
        return classBooks;
    }

    @Override
    public List<ClassBook> getAllClassBookList() {
        return classBookMapper.selectAll();
    }

    @Override
    public List<ClassBook> getClassBookListByPageAndCondition(Integer page, Integer limit, String receiveDate, String bookName, String className) {
        List<ClassBook> classBooks = null;
        try {
            classBooks = classBookMapper.selectClassBookListByPageAndCondition((page - 1) * limit, limit,
                    receiveDate, bookName, className);
        } catch (Exception e) {
            logger.error("领书情况带条件查询出错，错误：" + e);
        }
        classBooks = getClassBook(classBooks);
        return classBooks;
    }

    @Override
    public int deleteOneClassBook(ClassBook classBook) {
        ClassBook cb = new ClassBook();
        cb.setId(classBook.getId());
        return classBookMapper.delete(cb);
    }

    @Override
    public int updateClassBook(ClassBook classBook) {
        int i = 0;
        try {
            i = classBookMapper.updateClassBook(classBook);
        } catch (Exception e) {
            logger.error("更新领书情况出错，错误：" + e);
        }
        return i;
    }

    @Override
    public int deleteClassBooks(List<ClassBook> classBooks) {
        return classBookMapper.deleteClassBooks(classBooks);
    }

    @Transactional
    @Override
    public int addClassBook(ClassBook classBook) {
        classBookMapper.insertClassBook(classBook);
//        System.out.println(classBook.getId());
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

    @Override
    public List<ClassBook> getAllClassBookListByCondition(String receiveDate, String bookName, String className) {
        List<ClassBook> classBooks = null;
        try {
            classBooks = classBookMapper.selectAllClassBookListByCondition(receiveDate, bookName, className);
        } catch (Exception e) {
            logger.info("领书情况带条件查询出错，错误：" + e);
        }
        return classBooks;
    }
}
