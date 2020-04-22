package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.entity.User;
import com.gydx.bookManager.entity.UserRole;
import com.gydx.bookManager.mapper.RoleMapper;
import com.gydx.bookManager.mapper.UserMapper;
import com.gydx.bookManager.mapper.UserRoleMapper;
import com.gydx.bookManager.service.UserRoleService;
import com.gydx.bookManager.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    MD5Util md5Util;

    @Override
    public List<User> getUserRoleListByPage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<User> users = userMapper.selectByPage(page, limit);
        for (User user : users) {
            String roleName = roleMapper.selectNameByUserId(user.getId());
            user.setRoleName(roleName);
        }
        return users;
    }

    @Override
    public List<User> getAllUserRoleList() {
        return userMapper.selectAll();
    }

    @Override
    public List<User> getUserRoleListByPageAndCondition(Integer page, Integer limit, String roleName, String username, String nickname, String email, Character sex) {
        page = (page - 1) * limit;
        List<User> users = userMapper.selectByPageAndCondition(page, limit, roleName, username, nickname, email, sex);
        for (User user : users) {
            String r = roleMapper.selectNameByUserId(user.getId());
            user.setRoleName(r);
        }
        return users;
    }

    @Override
    public List<User> getAllUserRoleListByCondition(String roleName, String username, String nickname, String email, Character sex) {
        return userMapper.selectAllByCondition(roleName, username, nickname, email, sex);
    }

    @Transactional
    @Override
    public int deleteOneUserRole(User user) {
        UserRole ur = new UserRole();
        ur.setUserId(user.getId());
        userRoleMapper.delete(ur);
        userMapper.delete(user);
        return 1;
    }

    @Override
    public int deleteUserRoles(List<User> users) {
        for (User user : users) {
            UserRole ur = new UserRole();
            ur.setUserId(user.getId());
            userRoleMapper.delete(ur);
        }
        userMapper.deleteUsers(users);
        return 1;
    }

    @Transactional
    @Override
    public int addUserRole(User user) {
        user.setSalt(user.getUsername());
        user.setPassword(md5Util.getMD5(user.getSalt() + "123456"));
        userMapper.insert(user);
        userRoleMapper.insertOne(user);
        return 1;
    }

    @Transactional
    @Override
    public int updateUserRole(User user) {
        userMapper.updateOneUser(user);
        if (user.getRoleName() != null && !user.getRoleName().equals("")) {
            userRoleMapper.updateUserIdByRoleName(user);
        }
        return 1;
    }


}
