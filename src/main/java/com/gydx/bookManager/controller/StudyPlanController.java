package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.entity.Course;
import com.gydx.bookManager.entity.MajorCourse;
import com.gydx.bookManager.service.StudyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class StudyPlanController {

    @Autowired
    private StudyPlanService studyPlanService;

    @RequestMapping("/getStudyPlanList")
    public String getStudyPlanList(Integer page, Integer limit, String schoolName, String majorName, String courseName) {
        JSONObject jsonObject = new JSONObject();
        List<MajorCourse> majorCourses, majorCourseList;
        if ((schoolName + majorName + courseName).equals("")) {
            majorCourses = studyPlanService.getStudyPlanListByPage(page, limit);
            majorCourseList = studyPlanService.getAllStudyPlanList();
        } else {
            majorCourses = studyPlanService.getStudyPlanListByPageAndCondition(page, limit, schoolName, majorName, courseName);
            majorCourseList = studyPlanService.getAllStudyPlanListByCondition(schoolName, majorName, courseName);
        }
        jsonObject.put("msg", "查询成功");
        jsonObject.put("code", 0);
        jsonObject.put("count", majorCourseList.size());
        jsonObject.put("data", majorCourses);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/getStudyPlan")
    public String getStudyPlan(String majorName) {
        JSONObject jsonObject = new JSONObject();
        List<MajorCourse> majorCourses = studyPlanService.getStudyPlan(majorName);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "查询成功");
        jsonObject.put("data", majorCourses);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteOneStudyPlan")
    public String deleteOneStudyPlan(@RequestBody MajorCourse majorCourse) {
        JSONObject jsonObject = new JSONObject();
        studyPlanService.deleteOneStudyPlan(majorCourse);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/updateStudyPlan")
    public String updateStudyPlan(@RequestBody MajorCourse majorCourse) {
        JSONObject jsonObject = new JSONObject();
        int i = studyPlanService.updateStudyPlan(majorCourse);
        if (i == 0) {
            jsonObject.put("msg", "无该专业，请检查专业名是否写错");
        } else {
            jsonObject.put("msg", "修改成功");
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteStudyPlans")
    public String deleteStudyPlans(@RequestBody List<MajorCourse> majorCourses) {
        JSONObject jsonObject = new JSONObject();
        studyPlanService.deleteStudyPlans(majorCourses);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/addStudyPlan")
    public String addStudyPlan(@RequestBody MajorCourse majorCourse) {
        JSONObject jsonObject = new JSONObject();
        int i = studyPlanService.addStudyPlan(majorCourse);
        if (i == 0) {
            jsonObject.put("msg", "无该专业，请检查专业名是否写错");
        } else {
            jsonObject.put("msg", "添加成功");
        }
        return jsonObject.toJSONString();
    }
}
