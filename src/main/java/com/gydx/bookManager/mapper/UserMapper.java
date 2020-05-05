package com.gydx.bookManager.mapper;


import com.gydx.bookManager.pojo.UserInfoPojo;
import com.gydx.bookManager.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserMapper extends Mapper<User> {

    int updateUser(UserInfoPojo userInfoPojo);

    List<User> selectByPage(@Param("page") Integer page, @Param("limit") Integer limit);

    List<User> selectByPageAndCondition(@Param("page") Integer page, @Param("limit") Integer limit, @Param("roleName")
            String roleName, @Param("username") String username, @Param("nickname") String nickname, @Param("email")
            String email, @Param("sex") Character sex);

    List<User> selectAllByCondition(@Param("roleName") String roleName, @Param("username")
            String username, @Param("nickname") String nickname, @Param("email")
            String email, @Param("sex") Character sex);

    int deleteUsers(@Param("users") List<User> users);

    int updateOneUser(User user);

    int updatePassword(@Param("password") String md5Password, @Param("id") Integer id);

    int deleteOneUser(User user);

    User selectOneUser(User record);

    List<User> selectAllUserList();

    int updateOneStatus(User user);

    int updateUserByUsername(User user);

    int updateOneStatusByUsername(String number);

    List<User> getDUserNickname();

    void updateAUser(User u);
}
