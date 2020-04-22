package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.pojo.LoginPojo;
import com.gydx.bookManager.entity.Resource;
import com.gydx.bookManager.entity.Role;
import com.gydx.bookManager.entity.User;
import com.gydx.bookManager.service.ResourceService;
import com.gydx.bookManager.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestBody LoginPojo loginPojo) {
        logger.info( loginPojo.getUsername()+ "登录");
        JSONObject jsonObject = new JSONObject();
        UsernamePasswordToken token;
        if (loginPojo.getRemember() != null) {
            token = new UsernamePasswordToken(loginPojo.getUsername(), loginPojo.getPassword(), loginPojo.getRemember().equals("on"));
        } else {
            token = new UsernamePasswordToken(loginPojo.getUsername(), loginPojo.getPassword());
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            jsonObject.put("token", subject.getSession().getId());
            jsonObject.put("msg", "登录成功");
        } catch (UnknownAccountException e) {
//            e.printStackTrace();
            jsonObject.put("msg", "该用户不存在");
            return jsonObject.toJSONString();
        } catch (Exception e) {
            jsonObject.put("msg", "用户名或密码错误");
            return jsonObject.toJSONString();
        }
        Session session = subject.getSession();
        User user = (User) session.getAttribute("user");
        List<Resource> resources = resourceService.getResourcesByUserId(user.getId());
        Role role = roleService.getRoleByUserId(user.getId());
        user.setRole(role);
        jsonObject.put("user", user);
        jsonObject.put("resources", resources);
        jsonObject.put("role", role);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/logout")
    @ResponseBody
    public String logout() {
        JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        try {
            logger.info(subject.getPreviousPrincipals().getRealmNames().toString() + "退出登录");
            subject.logout();
            jsonObject.put("msg", "退出成功");
        } catch (Exception e) {
            jsonObject.put("msg", "退出失败");
        }
        return jsonObject.toJSONString();
    }

}
