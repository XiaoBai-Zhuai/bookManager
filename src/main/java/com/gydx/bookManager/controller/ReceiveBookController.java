package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.entity.ClassBook;
import com.gydx.bookManager.service.ReceiveBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin
@Controller
public class ReceiveBookController {

    @Autowired
    ReceiveBookService receiveBookService;

    @RequestMapping("/getClassBookList")
    @ResponseBody
    public String getClassBookList(Integer page, Integer limit, String receiveDate, String bookName, String className, Integer bookSum) {
        JSONObject jsonObject = new JSONObject();
        List<ClassBook> classes, classList;
        if ((receiveDate + bookName + className).equals("")) {
            classes = receiveBookService.getClassBookListByPage(page, limit);
            classList = receiveBookService.getAllClassBookList();
        } else {
            classes = receiveBookService.getClassBookListByPageAndCondition(page, limit, receiveDate, bookName, className);
            classList = receiveBookService.getAllClassBookListByCondition(receiveDate, bookName, className);
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "查询成功");
        jsonObject.put("count", classList.size());
        jsonObject.put("data", classes);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteOneClassBook")
    @ResponseBody
    public String deleteOneClassBook(@RequestBody ClassBook classBook) {
        JSONObject jsonObject = new JSONObject();
        receiveBookService.deleteOneClassBook(classBook);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/updateClassBook")
    @ResponseBody
    public String updateClassBook(@RequestBody ClassBook classBook) {
        JSONObject jsonObject = new JSONObject();
        receiveBookService.updateClassBook(classBook);
        jsonObject.put("msg", "修改成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteClassBooks")
    @ResponseBody
    public String deleteClassBooks(@RequestBody List<ClassBook> classBooks) {
        JSONObject jsonObject = new JSONObject();
        receiveBookService.deleteClassBooks(classBooks);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping(value = {"/addClassBook", "/submitClassBook"})
    @ResponseBody
    public String addClassBook(@RequestBody ClassBook classBook) {
        JSONObject jsonObject = new JSONObject();
        receiveBookService.addClassBook(classBook);
        jsonObject.put("msg", "添加成功");
        return jsonObject.toJSONString();
    }

}
