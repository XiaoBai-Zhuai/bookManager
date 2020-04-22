package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.Role;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {
    List<Role> selectRolesByUserId(Integer id);

    Role selectRoleByUserId(Integer userId);

    String selectNameByUserId(Integer userId);
}
