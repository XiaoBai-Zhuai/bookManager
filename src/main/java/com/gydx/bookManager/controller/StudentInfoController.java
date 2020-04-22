package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.entity.Student;
import com.gydx.bookManager.pojo.ReceiveData;
import com.gydx.bookManager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
public class StudentInfoController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/getStudentInfo")
    @ResponseBody
    public String getStudentInfo(@RequestBody ReceiveData receiveData) {
        JSONObject jsonObject = new JSONObject();
        Student student = studentService.getStudentInfo(receiveData.getUsername());
        jsonObject.put("msg", "查询成功");
        jsonObject.put("student", student);
        return jsonObject.toJSONString();
    }

}
