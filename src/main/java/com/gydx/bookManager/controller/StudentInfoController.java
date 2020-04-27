package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.entity.Student;
import com.gydx.bookManager.pojo.ReceiveData;
import com.gydx.bookManager.pojo.StudentInfoPojo;
import com.gydx.bookManager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping("/getStudentList")
    @ResponseBody
    public String getStudentList(Integer page, Integer limit, String name, String number,
                                 String className, String majorName, String schoolName) {
        StudentInfoPojo studentInfoPojo = new StudentInfoPojo(page, limit, name, number, className, majorName, schoolName);
        JSONObject jsonObject = new JSONObject();
        List<Student> students, studentList;
        if (studentInfoPojo.getCondition().equals("")) {
            students = studentService.getStudentListByPage(studentInfoPojo.getPage(), studentInfoPojo.getLimit());
            studentList = studentService.getAllStudentList();
        } else {
            students = studentService.getStudentListByPageAndCondition(studentInfoPojo);
            studentList = studentService.getAllStudentListByCondition(studentInfoPojo);
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "查询成功");
        jsonObject.put("count", studentList.size());
        jsonObject.put("data", students);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteOneStudent")
    @ResponseBody
    public String deleteOneStudent(@RequestBody Student student) {
        JSONObject jsonObject = new JSONObject();
        studentService.deleteOneStudent(student);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/updateStudent")
    @ResponseBody
    public String updateStudent(@RequestBody Student student) {
        JSONObject jsonObject = new JSONObject();
        int i = studentService.updateStudent(student);
        if (i == 0) {
            jsonObject.put("msg", "该学号对应学生已存在，请检查学号是否写错！");
            return jsonObject.toJSONString();
        }
        jsonObject.put("msg", "修改成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/addStudent")
    @ResponseBody
    public String addStudent(@RequestBody Student student) {
        JSONObject jsonObject = new JSONObject();
        int i = studentService.addStudent(student);
        if (i == 0) {
            jsonObject.put("msg", "该学号对应学生已存在，请检查学号是否写错！");
            return jsonObject.toJSONString();
        }
        jsonObject.put("msg", "增加成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteStudents")
    @ResponseBody
    public String deleteStudents(@RequestBody List<Student> students) {
        JSONObject jsonObject = new JSONObject();
        studentService.deleteStudents(students);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }
}
