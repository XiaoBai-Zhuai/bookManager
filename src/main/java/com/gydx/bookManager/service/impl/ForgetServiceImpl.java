package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.entity.User;
import com.gydx.bookManager.mapper.UserMapper;
import com.gydx.bookManager.pojo.ReceiveData;
import com.gydx.bookManager.service.ForgetService;
import com.gydx.bookManager.util.MD5Util;
import com.gydx.bookManager.util.SendMailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class ForgetServiceImpl implements ForgetService {

    private Logger logger = LoggerFactory.getLogger(ForgetServiceImpl.class);

    private static Map<String, Map<String, String>> verCode = new HashMap<>();

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SendMailUtil sendMailUtil;
    @Autowired
    private MD5Util md5Util;

    //生成6位数字验证码
    public String createCode() {
        int code = (int)((Math.random() * 9 + 1) * 100000);
        return String.valueOf(code);
    }

    @Override
    public String getCode(String email) {
        User t = new User();
        t.setEmail(email);
        User user = userMapper.selectOne(t);
        if (user == null) {
            return "无该用户！请输入与用户绑定的邮箱！";
        }
        logger.info(user.getUsername() + "申请重置密码");
        String code = createCode();
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("time", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        verCode.put(email, map);
        try {
            sendMailUtil.sendMail(user.getNickname(), email, code);
        } catch (Exception e) {
            logger.info(user.getUsername() + "申请忘记密码，验证码发送失败。" + e);
            return "发送失败！";
        }
        return "发送成功，请注意查收";
    }

    @Override
    public String updatePassword(ReceiveData receiveData) {
        String code = receiveData.getCode(), email = receiveData.getEmail();
        Map<String, String> map = verCode.get(email);
        String vercode = map.get("code");
        String time = map.get("time");
        if (!code.equals(vercode) || (Calendar.getInstance().getTimeInMillis() - Long.parseLong(time)) >= 3600000 ) {
            verCode.remove(email);
            return "验证码错误！";
        }
        User t = new User();
        t.setEmail(email);
        User user = userMapper.selectOne(t);
        String password = md5Util.getMD5(user.getSalt() + receiveData.getPassword());
        try {
            userMapper.updatePassword(password, user.getId());
        } catch (Exception e) {
            logger.error( user.getUsername()+ "更新密码出错，错误：" + e);
            return "服务器出错，密码保存失败！";
        }
        return "保存成功！";
    }
}
