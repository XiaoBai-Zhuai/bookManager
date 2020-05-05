package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.entity.School;
import com.gydx.bookManager.pojo.SchoolInfoPojo;
import com.gydx.bookManager.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class SchoolInfoController {

    @Autowired
    private SchoolService schoolService;

    @RequestMapping("/getSchoolList")
    public String getSchoolList(Integer page, Integer limit, String name, String principalName, String principalNumber) {
        SchoolInfoPojo schoolInfoPojo = new SchoolInfoPojo(page, limit, name, principalName, principalNumber);
        JSONObject jsonObject = new JSONObject();
        List<School> schools, schoolList;
        if (schoolInfoPojo.getCondition().equals("")) {
            schools = schoolService.getSchoolListByPage(schoolInfoPojo.getPage(), schoolInfoPojo.getLimit());
            schoolList = schoolService.getAllSchoolList();
        } else {
            schools = schoolService.getSchoolListByPageAndCondition(schoolInfoPojo);
            schoolList = schoolService.getAllSchoolListByCondition(schoolInfoPojo);
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "查询成功");
        jsonObject.put("count", schoolList.size());
        jsonObject.put("data", schools);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteOneSchool")
    public String deleteOneSchool(@RequestBody School school) {
        JSONObject jsonObject = new JSONObject();
        schoolService.deleteOneSchool(school);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/updateSchool")
    public String updateSchool(@RequestBody School school) {
        JSONObject jsonObject = new JSONObject();
        int i = schoolService.updateSchool(school);
        if (i == 0) {
            jsonObject.put("msg", "负责人姓名与工号不匹配，请检查！");
            return jsonObject.toJSONString();
        }
        jsonObject.put("msg", "修改成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteSchools")
    public String deleteSchools(@RequestBody List<School> schools) {
        JSONObject jsonObject = new JSONObject();
        schoolService.deleteSchools(schools);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/addSchool")
    public String addSchool(@RequestBody School school) {
        JSONObject jsonObject = new JSONObject();
        int i = schoolService.addSchool(school);
        if (i == 0) {
            jsonObject.put("msg", "负责人姓名与工号不匹配，请检查！");
            return jsonObject.toJSONString();
        }
        jsonObject.put("msg", "增加成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/getAllSchoolList")
    public String getAllSchoolList() {
        JSONObject jsonObject = new JSONObject();
        List<School> schools = schoolService.getAllSchoolList();
        jsonObject.put("msg", "查询成功");
        jsonObject.put("data", schools);
        return jsonObject.toJSONString();
    }

}
