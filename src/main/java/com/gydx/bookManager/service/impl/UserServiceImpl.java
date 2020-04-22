package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.pojo.UserInfoPojo;
import com.gydx.bookManager.mapper.UserMapper;
import com.gydx.bookManager.entity.User;
import com.gydx.bookManager.service.UserService;
import com.gydx.bookManager.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    MD5Util md5Util;

    @Override
    public User getUserByUsername(String username) {
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);
        return user;
    }

    @Override
    public User getUserByUserId(Integer userId) {
        User record = new User();
        record.setId(userId);
        User user = userMapper.selectOne(record);
        return user;
    }

    @Override
    public User updateUser(UserInfoPojo userInfoPojo) {
        userMapper.updateUser(userInfoPojo);
        User record = new User();
        record.setUsername(userInfoPojo.getUsername());
        User user = userMapper.selectOne(record);
        return user;
    }

    @Override
    public int updatePassword(String oldPassword, String password, String username) {
        User t = new User();
        t.setUsername(username);
        User user = userMapper.selectOne(t);
        String s = md5Util.getMD5(user.getSalt() + oldPassword);
        if (s.equals(user.getPassword())) {
            String md5Password = md5Util.getMD5(user.getSalt() + password);
            userMapper.updatePassword(md5Password, user.getId());
            return 1;
        }
        return 0;
    }
}
