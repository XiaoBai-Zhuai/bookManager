package com.gydx.bookManager.service;

import com.gydx.bookManager.pojo.UserInfoPojo;
import com.gydx.bookManager.entity.User;

import java.util.List;

public interface UserService {

    User getUserByUsername(String username);

    User getUserByUserId(Integer userId);

    User updateUser(UserInfoPojo userInfoPojo);

    int updatePassword(String oldPassword, String password, String username);

    List<User> getAllUserList();

    List<User> getDUserNickname();
}
