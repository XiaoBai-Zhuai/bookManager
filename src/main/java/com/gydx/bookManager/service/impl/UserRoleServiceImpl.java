package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.entity.*;
import com.gydx.bookManager.entity.Class;
import com.gydx.bookManager.mapper.*;
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
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private MajorMapper majorMapper;

    /**
     * 用户和角色表分页查询
     * @param page
     * @param limit
     * @return
     */
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

    /**
     * 查出全部的用户
     * @return
     */
    @Override
    public List<User> getAllUserRoleList() {
        return userMapper.selectAllUserList();
    }

    /**
     * 根据条件分页查询出用户和角色的联合表
     * @param page
     * @param limit
     * @param roleName
     * @param username
     * @param nickname
     * @param email
     * @param sex
     * @return
     */
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

    /**
     * 根据条件查询出所有的用户和角色联合
     * @param roleName
     * @param username
     * @param nickname
     * @param email
     * @param sex
     * @return
     */
    @Override
    public List<User> getAllUserRoleListByCondition(String roleName, String username, String nickname, String email, Character sex) {
        return userMapper.selectAllByCondition(roleName, username, nickname, email, sex);
    }

    /**
     * 删除某个用户，同时删除该用户与角色的联合信息
     * @param user
     * @return
     */
    @Transactional
    @Override
    public int deleteOneUserRole(User user) {
        UserRole ur = new UserRole();
        ur.setUserId(user.getId());
        userMapper.deleteOneUser(user);
        return 1;
    }

    /**
     * 批量删除用户
     * @param users
     * @return
     */
    @Override
    public int deleteUserRoles(List<User> users) {
        userMapper.deleteUsers(users);
        return 1;
    }

    /**
     * 在插入新用户时检查，如果身份为普通学生或者班级负责人，如果为班级负责人，则检查对应班级是否已经存在负责人，如果存在
     * 则返回0表示该班级已经存在负责人，如果不存在则将该班级的负责人设置成该用户，然后检查学生表里是否已经存在该学生，如果不存在
     * 则新增
     * 如果身份为学院负责人，则检查该学院是否已经存在负责人，不存在则将新增的用户设置成该学院的负责人，然后检查教师表中是否已存在
     * 该学院负责人，如果不存在则新增
     * @param user
     * @return
     */
    @Transactional
    @Override
    public int addUserRole(User user) {
        user.setSalt(user.getUsername());
        user.setPassword(md5Util.getMD5(user.getSalt() + "123456"));
        String roleName = user.getRoleName();
        userMapper.insertSelective(user);
        if (roleName.equals("普通学生") || roleName.equals("班级负责人")) {
            Student t = new Student();
            t.setNumber(user.getUsername());
            Student student = studentMapper.selectOne(t);
            Class c = new Class();
            c.setName(user.getClassName());
            Class aClass = classMapper.selectOne(c);
            if (roleName.equals("班级负责人")) {
                if (aClass.getUserId() != null)
                    return 0;
                else {
                    aClass.setUserId(user.getId());
                }
            }
            if (student == null) {
                Student s = new Student();
                s.setNumber(user.getUsername());
                s.setClassId(aClass.getId()); s.setMajorId(aClass.getMajorId());
                Major m = new Major(); m.setId(aClass.getMajorId());
                Major major = majorMapper.selectOne(m);
                s.setSchoolId(major.getSchoolId());
                s.setName(user.getNickname());
                studentMapper.insertSelective(s);
            }
        } else if (user.getRoleName().equals("学院负责人")) {
            Teacher t = new Teacher(); t.setNumber(user.getUsername());
            Teacher teacher = teacherMapper.selectOne(t);
            School s = new School();
            s.setName(user.getSchoolName());
            School school = schoolMapper.selectOne(s);
            if (school.getUserId() != null) {
                return 2;
            } else {
                s.setUserId(user.getId());
            }
            if (teacher == null) {
                Teacher aTeacher = new Teacher();
                aTeacher.setNumber(user.getUsername()); aTeacher.setName(user.getNickname());
                aTeacher.setSchoolId(s.getId());
                teacherMapper.insert(aTeacher);
            }
        }
        userRoleMapper.insertOne(user);
        return 1;
    }

    /**
     * 更新用户角色信息
     * @param u
     * @return
     */
    @Override
    public int updateUserRole(User u) {
        userMapper.updateOneUser(u);
        return 1;
    }


}
