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

    /**
     * 先根据邮箱查找是否有对应用户，若没有则返回提示用户邮箱填错
     * 然后将邮箱作为键，验证码和发送时间的map作为值，存储在全局变量verCode里，后面检验用户输入的验证码时使用
     * @param email
     * @return
     */
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

    /**
     * 先检验用户填写的验证码是否有误或者时间是否超时，如果验证码错了或者超时了就将该键值为该邮箱的从verCode里删除
     * 然后更改该用户的密码
     * @param receiveData
     * @return
     */
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
        String password = MD5Util.getMD5(user.getSalt() + receiveData.getPassword());
        try {
            userMapper.updatePassword(password, user.getId());
        } catch (Exception e) {
            logger.error( user.getUsername()+ "更新密码出错，错误：" + e);
            return "服务器出错，密码保存失败！";
        }
        return "保存成功！";
    }
}
