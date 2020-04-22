package com.gydx.bookManager.service;

import com.gydx.bookManager.entity.ClassBook;

import java.util.List;

public interface ReceiveBookService {
    List<ClassBook> getAllClassBookListByCondition(String receiveDate, String bookName, String className);

    List<ClassBook> getClassBookListByPage(Integer page, Integer limit);

    List<ClassBook> getAllClassBookList();

    List<ClassBook> getClassBookListByPageAndCondition(Integer page, Integer limit, String receiveDate, String bookName, String className);

    int deleteOneClassBook(ClassBook classBook);

    int updateClassBook(ClassBook classBook);

    int deleteClassBooks(List<ClassBook> classBooks);

    int addClassBook(ClassBook classBook);
}
