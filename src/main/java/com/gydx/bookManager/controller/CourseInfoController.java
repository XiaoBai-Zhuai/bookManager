package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.entity.Course;
import com.gydx.bookManager.pojo.ReceiveData;
import com.gydx.bookManager.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class CourseInfoController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("/getCourseListBySchoolName")
    public String getCourseListBySchoolName(@RequestBody ReceiveData receiveData) {
        JSONObject jsonObject = new JSONObject();
        List<Course> courseList = courseService.getCourseListBySchoolName(receiveData.getSchoolName());
        jsonObject.put("msg", "查询成功");
        jsonObject.put("data", courseList);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/getCourseListByMajorName")
    public String getCourseListByMajorName(@RequestBody ReceiveData receiveData) {
        JSONObject jsonObject = new JSONObject();
        List<Course> courseList = courseService.getCourseListByMajorName(receiveData.getMajorName(), receiveData.getSchoolName());
        jsonObject.put("msg", "查询成功");
        jsonObject.put("data", courseList);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/getAllCourseList")
    public String getAllCourseList() {
        JSONObject jsonObject = new JSONObject();
        List<Course> courseList = courseService.getAllCourseList();
        jsonObject.put("msg", "查询成功");
        jsonObject.put("data", courseList);
        return jsonObject.toJSONString();
    }

}
