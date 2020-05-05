package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.entity.Role;
import com.gydx.bookManager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class RoleInfoController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/getRoleList")
    public String getRoleList(String role) {
        JSONObject jsonObject = new JSONObject();
        List<Role> roles;
        if (role == null || role.equals("")) {
            roles = roleService.getRoleList();
        } else {
            roles = roleService.getRoleListByCondition(role);
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "查询成功");
        jsonObject.put("data", roles);
        return jsonObject.toJSONString();
    }

}
