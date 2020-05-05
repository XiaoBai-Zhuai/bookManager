package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.entity.Teacher;
import com.gydx.bookManager.pojo.ReceiveData;
import com.gydx.bookManager.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class TeacherInfoController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/getTeacherInfo")
    public String getTeacherInfo(@RequestBody ReceiveData receiveData) {
        JSONObject jsonObject = new JSONObject();
        Teacher teacher = teacherService.getTeacherInfo(receiveData.getUsername());
        jsonObject.put("msg", "查询成功");
        jsonObject.put("data", teacher);
        return jsonObject.toJSONString();
    }

}
