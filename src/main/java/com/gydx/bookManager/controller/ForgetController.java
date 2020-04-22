package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.pojo.ReceiveData;
import com.gydx.bookManager.service.ForgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin()
@Controller
@RequestMapping("/forget")
public class ForgetController {

    @Autowired
    private ForgetService forgetService;

    @RequestMapping("/getCode")
    @ResponseBody
    public String getCode(@RequestBody ReceiveData receiveData) {
        JSONObject jsonObject = new JSONObject();
        String s = forgetService.getCode(receiveData.getEmail());
        jsonObject.put("msg", s);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/updatePassword")
    @ResponseBody
    public String updatePassword(@RequestBody ReceiveData receiveData) {
        JSONObject jsonObject = new JSONObject();
        String s = forgetService.updatePassword(receiveData);
        jsonObject.put("msg", s);
        return jsonObject.toJSONString();
    }

}
