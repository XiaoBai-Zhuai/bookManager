package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.pojo.ReceiveData;
import com.gydx.bookManager.pojo.UserInfoPojo;
import com.gydx.bookManager.entity.User;
import com.gydx.bookManager.service.RoleService;
import com.gydx.bookManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
public class UserInfoController {

    @Autowired
    private UserService userService;

    @RequestMapping("/updateUserInfo")
    public String updateUserInfo(@RequestBody UserInfoPojo userInfoPojo) {
        JSONObject jsonObject = new JSONObject();
        User user = userService.updateUser(userInfoPojo);
        jsonObject.put("user", user);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/updatePassword")
    public String updatePassword(@RequestBody ReceiveData receiveData) {
        JSONObject jsonObject = new JSONObject();
        int i = userService.updatePassword(receiveData.getOldPassword(), receiveData.getPassword(),
                receiveData.getUsername());
        if (i == 0) {
            jsonObject.put("msg", "当前密码输入错误！");
        } else {
            jsonObject.put("msg", "修改成功");
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping("/getAllUserList")
    public String getAllUserList() {
        JSONObject jsonObject = new JSONObject();
        List<User> users = userService.getAllUserList();
        jsonObject.put("data", users);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/getDUserNickname")
    public String getDUserNickname() {
        JSONObject jsonObject = new JSONObject();
        List<User> users = userService.getDUserNickname();
        jsonObject.put("data", users);
        return jsonObject.toJSONString();
    }

}
