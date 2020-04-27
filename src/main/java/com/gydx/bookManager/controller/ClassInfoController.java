package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.entity.Class;
import com.gydx.bookManager.pojo.ClassInfoPojo;
import com.gydx.bookManager.pojo.ReceiveData;
import com.gydx.bookManager.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin
@Controller
public class ClassInfoController {

    @Autowired
    private ClassService classService;

    @RequestMapping("/getClassNameByMajorName")
    @ResponseBody
    public String getClassNameByMajorName(@RequestBody ReceiveData receiveData) {
        JSONObject jsonObject = new JSONObject();
        List<Class> classes = classService.getClassNameByMajorName(receiveData.getMajorName());
        jsonObject.put("msg", "查询成功");
        jsonObject.put("data", classes);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/getClassList")
    @ResponseBody
    public String getClassList(Integer page, Integer limit, String name, String majorName, String schoolName, String principalNumber) {
        ClassInfoPojo classInfoPojo = new ClassInfoPojo(page, limit, name, majorName, schoolName, principalNumber);
        JSONObject jsonObject = new JSONObject();
        List<Class> classes, classList;
        if (classInfoPojo.getCondition().equals("")) {
            classes = classService.getClassListByPage(classInfoPojo.getPage(), classInfoPojo.getLimit());
            classList = classService.getAllClassList();
        } else {
            classes = classService.getClassListByPageAndCondition(classInfoPojo);
            classList = classService.getAllClassListByCondition(classInfoPojo);
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "查询成功");
        jsonObject.put("count", classList.size());
        jsonObject.put("data", classes);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteOneClass")
    @ResponseBody
    public String deleteOneClass(@RequestBody Class c) {
        JSONObject jsonObject = new JSONObject();
        classService.deleteOneClass(c);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/updateClass")
    @ResponseBody
    public String updateClass(@RequestBody Class c) {
        JSONObject jsonObject = new JSONObject();
        int i = classService.updateClass(c);
        if (i == 0) {
            jsonObject.put("msg", "负责人姓名与学号不符，请检查！");
        }
        jsonObject.put("msg", "修改成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteClasses")
    @ResponseBody
    public String deleteClasses(@RequestBody List<Class> classes) {
        JSONObject jsonObject = new JSONObject();
        classService.deleteClasses(classes);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/addClass")
    @ResponseBody
    public String addClass(@RequestBody Class c) {
        JSONObject jsonObject = new JSONObject();
        int i = classService.addClass(c);
        if (i == 0) {
            jsonObject.put("msg", "负责人姓名与学号不符，请检查！");
        }
        jsonObject.put("msg", "增加成功");
        return jsonObject.toJSONString();
    }

}
