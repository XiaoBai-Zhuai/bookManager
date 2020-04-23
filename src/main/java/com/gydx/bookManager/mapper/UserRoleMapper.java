package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.User;
import com.gydx.bookManager.entity.UserRole;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserRoleMapper extends Mapper<UserRole> {
    int insertOne(User user);

    int updateUserIdByRoleName(User user);
}
