package com.gydx.bookManager.service;

import com.gydx.bookManager.pojo.BookPageInfoPojo;
import com.gydx.bookManager.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getBookList(Integer page, Integer limit);

    List<Book> getAllBooks();

    int deleteOneBookById(Integer id);

    int updateBookInfo(Book book);

    int deleteBooks(List<Book> books);

    int addBook(Book book);

    List<Book> getBookListByCondition(BookPageInfoPojo bookPageInfoPojo);

    List<Book> getAllBooksByCondition(BookPageInfoPojo bookPageInfoPojo);

    List<Book> getBookListByMajor(String majorName);

    List<Book> getAllBookList();

    List<Book> getAllDBookName();

    List<Book> getAllDBookAuthor();

    List<Book> getAllDBookAuthorByBookName(String bookName);

    List<Book> getAllDBookPublishTimeByBookAuthor(String bookName, String author);

    List<Book> getAllDBookPublisherByBookPublishTime(String bookName, String author, String publishTime);

    List<Book> getAllDBookPriceByBookPublisher(String bookName, String author, String publishTime, String publisher);

}
