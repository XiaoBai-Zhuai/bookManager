package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.pojo.BookPageInfoPojo;
import com.gydx.bookManager.entity.Book;
import com.gydx.bookManager.pojo.ReceiveData;
import com.gydx.bookManager.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
public class BookInfoController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/getBookList")
    @ResponseBody
    public String getBookList(Integer page, Integer limit, String name, String author, String publisher) {
        BookPageInfoPojo bookPageInfoPojo = new BookPageInfoPojo();
        bookPageInfoPojo.setPage(page);
        bookPageInfoPojo.setLimit(limit);
        bookPageInfoPojo.setAuthor(author);
        bookPageInfoPojo.setName(name);
        bookPageInfoPojo.setPublisher(publisher);
        JSONObject jsonObject = new JSONObject();
        List<Book> books1, books2;
        if (bookPageInfoPojo.getName().equals("") && bookPageInfoPojo.getAuthor().equals("") && bookPageInfoPojo.getPublisher().equals("")) {
            books1 = bookService.getBookList(bookPageInfoPojo.getPage(), bookPageInfoPojo.getLimit());
            books2 = bookService.getAllBooks();
        } else {
            books1 = bookService.getBookListByCondition(bookPageInfoPojo);
            books2 = bookService.getAllBooksByCondition(bookPageInfoPojo);
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "查询成功");
        jsonObject.put("count", books2.size());
        jsonObject.put("data", books1);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteOneBookById")
    @ResponseBody
    public String deleteOneBookById(@RequestBody Book book) {
        JSONObject jsonObject = new JSONObject();
        bookService.deleteOneBookById(book.getId());
        jsonObject.put("msg", "删除成功！");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/updateBookInfo")
    @ResponseBody
    public String updateBookInfo(@RequestBody Book book) {
        JSONObject jsonObject = new JSONObject();
        bookService.updateBookInfo(book);
        jsonObject.put("msg", "更新成功！");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteBooks")
    @ResponseBody
    public String deleteBooks(@RequestBody List<Book> books) {
        JSONObject jsonObject = new JSONObject();
        bookService.deleteBooks(books);
        jsonObject.put("msg", "已删除！");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/addBook")
    @ResponseBody
    public String addBook(@RequestBody Book book) {
        JSONObject jsonObject = new JSONObject();
        bookService.addBook(book);
        jsonObject.put("msg", "添加成功！");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/getBookListByMajor")
    @ResponseBody
    public String getBookListByMajor(@RequestBody ReceiveData receiveData) {
        JSONObject jsonObject = new JSONObject();
        List<Book> books = bookService.getBookListByMajor(receiveData.getMajorName());
        jsonObject.put("msg", "查询成功");
        jsonObject.put("data", books);
        return jsonObject.toJSONString();
    }
}
