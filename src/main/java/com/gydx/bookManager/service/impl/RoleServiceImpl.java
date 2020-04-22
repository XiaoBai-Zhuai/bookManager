package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.mapper.RoleMapper;
import com.gydx.bookManager.entity.Role;
import com.gydx.bookManager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRolesByUserId(Integer id) {
        List<Role> roles = roleMapper.selectRolesByUserId(id);
        return roles;
    }

    @Override
    public Role getRoleByUserId(Integer userId) {
        Role role = roleMapper.selectRoleByUserId(userId);
        return role;
    }

    @Override
    public List<Role> getRoleList() {
        return roleMapper.selectAll();
    }

    @Override
    public List<Role> getRoleListByCondition(String roleName) {
        Role r = new Role();
        r.setName(roleName);
        List<Role> roles = roleMapper.select(r);
        return roles;
    }
}
