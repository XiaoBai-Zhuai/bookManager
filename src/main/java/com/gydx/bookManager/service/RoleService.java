package com.gydx.bookManager.service;

import com.gydx.bookManager.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRolesByUserId(Integer id);

    Role getRoleByUserId(Integer userId);

    List<Role> getRoleList();

    List<Role> getRoleListByCondition(String role);
}
