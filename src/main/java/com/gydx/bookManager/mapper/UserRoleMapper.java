package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.User;
import com.gydx.bookManager.entity.UserRole;
import tk.mybatis.mapper.common.Mapper;

public interface UserRoleMapper extends Mapper<UserRole> {
    int insertOne(User user);

    int updateUserIdByRoleName(User user);
}
