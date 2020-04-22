package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.entity.Major;
import com.gydx.bookManager.pojo.ReceiveData;
import com.gydx.bookManager.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin
@Controller
public class MajorInfoController {

    @Autowired
    private MajorService majorService;

    @RequestMapping("/getMajorListBySchool")
    @ResponseBody
    public String getMajorListBySchool(@RequestBody ReceiveData receiveData) {
        JSONObject jsonObject = new JSONObject();
        List<Major> majors = majorService.getMajorListBySchool(receiveData.getSchoolId());
        jsonObject.put("msg", "查询成功");
        jsonObject.put("data", majors);
        return jsonObject.toJSONString();
    }

}
