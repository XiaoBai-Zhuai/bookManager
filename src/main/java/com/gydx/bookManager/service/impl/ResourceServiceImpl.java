package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.mapper.ResourceMapper;
import com.gydx.bookManager.mapper.RoleMapper;
import com.gydx.bookManager.entity.Resource;
import com.gydx.bookManager.entity.Role;
import com.gydx.bookManager.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Resource> getResourcesByUserId(Integer id) {
        List<Role> roles = roleMapper.selectRolesByUserId(id);
        List<Resource> resources = new ArrayList<>();
        for (Role role : roles) {
            List<Resource> list = resourceMapper.selectResourcesByRoleId(role.getId());
            for (Resource resource : list) {
                resources.add(resource);
            }
        }
        return resources;
    }
}
