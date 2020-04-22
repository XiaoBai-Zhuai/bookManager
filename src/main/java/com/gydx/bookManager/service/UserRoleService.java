package com.gydx.bookManager.service;

import com.gydx.bookManager.entity.User;

import java.util.List;

public interface UserRoleService {
    List<User> getUserRoleListByPage(Integer page, Integer limit);

    List<User> getAllUserRoleList();

    List<User> getUserRoleListByPageAndCondition(Integer page, Integer limit, String roleName, String username, String nickname, String email, Character sex);

    List<User> getAllUserRoleListByCondition(String roleName, String username, String nickname, String email, Character sex);

    int deleteOneUserRole(User user);

    int deleteUserRoles(List<User> users);

    int addUserRole(User user);

    int updateUserRole(User user);
}
