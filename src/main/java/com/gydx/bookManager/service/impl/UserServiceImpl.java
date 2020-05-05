package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.pojo.UserInfoPojo;
import com.gydx.bookManager.mapper.UserMapper;
import com.gydx.bookManager.entity.User;
import com.gydx.bookManager.service.UserService;
import com.gydx.bookManager.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    MD5Util md5Util;

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public User getUserByUsername(String username) {
        User record = new User();
        record.setUsername(username);
        User user = userMapper.selectOneUser(record);
        return user;
    }

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    @Override
    public User getUserByUserId(Integer userId) {
        User record = new User();
        record.setId(userId);
        User user = userMapper.selectOne(record);
        return user;
    }

    /**
     * 更新用户信息
     * @param userInfoPojo
     * @return
     */
    @Override
    public User updateUser(UserInfoPojo userInfoPojo) {
        userMapper.updateUser(userInfoPojo);
        User record = new User();
        record.setUsername(userInfoPojo.getUsername());
        User user = userMapper.selectOne(record);
        return user;
    }

    /**
     * 修改用户密码
     * @param oldPassword
     * @param password
     * @param username
     * @return
     */
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

    @Override
    public List<User> getAllUserList() {
        return userMapper.selectAllUserList();
    }

    /**
     * 获取不重复的所有用户的昵称
     * @return
     */
    @Override
    public List<User> getDUserNickname() {
        List<User> users = null;
        try {
            users = userMapper.getDUserNickname();
        } catch (Exception e) {
            logger.error("获取不重复的所有用户的昵称出错，错误：" + e);
        }
        return users;
    }
}
