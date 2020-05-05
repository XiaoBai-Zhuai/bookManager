package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.entity.Major;
import com.gydx.bookManager.pojo.MajorInfoPojo;
import com.gydx.bookManager.pojo.ReceiveData;
import com.gydx.bookManager.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class MajorInfoController {

    @Autowired
    private MajorService majorService;

    @RequestMapping("/getMajorNameBySchoolName")
    public String getMajorNameBySchoolName(@RequestBody ReceiveData receiveData) {
        JSONObject jsonObject = new JSONObject();
        List<Major> majors = majorService.getMajorNameBySchoolName(receiveData.getSchoolName());
        jsonObject.put("msg", "查询成功");
        jsonObject.put("data", majors);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/getMajorList")
    public String getMajorList(Integer page, Integer limit, String name, String schoolName) {
        MajorInfoPojo majorInfoPojo = new MajorInfoPojo(page, limit, name, schoolName);
        JSONObject jsonObject = new JSONObject();
        List<Major> majors, majorList;
        if (majorInfoPojo.getCondition().equals("")) {
            majors = majorService.getMajorListByPage(majorInfoPojo.getPage(), majorInfoPojo.getLimit());
            majorList = majorService.getAllMajorList();
        } else {
            majors = majorService.getMajorListByPageAndCondition(majorInfoPojo);
            majorList = majorService.getAllMajorListByCondition(majorInfoPojo);
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "查询成功");
        jsonObject.put("count", majorList.size());
        jsonObject.put("data", majors);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteOneMajor")
    public String deleteOneMajor(@RequestBody Major major) {
        JSONObject jsonObject = new JSONObject();
        majorService.deleteOneMajor(major);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/updateMajor")
    public String updateMajor(@RequestBody Major major) {
        JSONObject jsonObject = new JSONObject();
        majorService.updateMajor(major);
        jsonObject.put("msg", "修改成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteMajors")
    public String deleteMajors(@RequestBody List<Major> majors) {
        JSONObject jsonObject = new JSONObject();
        majorService.deleteMajors(majors);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/addMajor")
    public String addMajor(@RequestBody Major major) {
        JSONObject jsonObject = new JSONObject();
        int i = majorService.addMajor(major);
        if (i == 0) {
            jsonObject.put("msg", "该专业已存在！");
            return jsonObject.toJSONString();
        }
        jsonObject.put("msg", "添加成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/getMajorListBySchoolName")
    public String getMajorListBySchoolName(@RequestBody ReceiveData receiveData) {
        JSONObject jsonObject = new JSONObject();
        List<Major> majors = majorService.getMajorListBySchoolName(receiveData.getSchoolName());
        jsonObject.put("msg", "查询成功");
        jsonObject.put("data", majors);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/getAllMajorList")
    public String getAllMajorList() {
        JSONObject jsonObject = new JSONObject();
        List<Major> majors = majorService.getAllMajorList();
        jsonObject.put("msg", "查询成功");
        jsonObject.put("data", majors);
        return jsonObject.toJSONString();
    }

}
