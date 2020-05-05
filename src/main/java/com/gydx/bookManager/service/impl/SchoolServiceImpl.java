package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.entity.*;
import com.gydx.bookManager.mapper.*;
import com.gydx.bookManager.pojo.SchoolInfoPojo;
import com.gydx.bookManager.service.SchoolService;
import com.gydx.bookManager.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    private Logger logger = LoggerFactory.getLogger(SchoolServiceImpl.class);

    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    MD5Util md5Util;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 将查询出来的学院列表中的每个学院添加上负责人的信息
     * @param schools
     * @return
     */
    private List<School> addInfo(List<School> schools) {
        for (School school : schools) {
            User u = new User();
            u.setId(school.getUserId());
            User user = userMapper.selectOne(u);
            school.setPrincipalName(user.getNickname());
            school.setPrincipalNumber(user.getUsername());
        }
        return schools;
    }

    /**
     * 分页查询status为1的学院列表
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<School> getSchoolListByPage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<School> schools = null;
        try {
            schools = schoolMapper.findSchoolListByPage(page, limit);
        } catch (Exception e) {
            logger.error("学院分页查询出错，错误：" + e);
        }
        if (schools == null) {
            return null;
        }
        schools = addInfo(schools);
        return schools;
    }

    /**
     * 查询出所有的status为1的学院列表
     * @return
     */
    @Override
    public List<School> getAllSchoolList() {
        return schoolMapper.findAll();
    }

    /**
     * 根据条件分页查询出所有status为1的学院列表
     * @param schoolInfoPojo
     * @return
     */
    @Override
    public List<School> getSchoolListByPageAndCondition(SchoolInfoPojo schoolInfoPojo) {
        schoolInfoPojo.setPage((schoolInfoPojo.getPage() - 1) * schoolInfoPojo.getLimit());
        List<School> schools = null;
        try {
            schools = schoolMapper.findSchoolListByPageAndCondition(schoolInfoPojo);
        } catch (Exception e) {
            logger.error("学院带条件分页查询出错，错误：" + e);
        }
        if (schools == null) {
            return null;
        }
        schools = addInfo(schools);
        return schools;
    }

    /**
     * 根据条件查询出所有的status为1 的学院列表
     * @param schoolInfoPojo
     * @return
     */
    @Override
    public List<School> getAllSchoolListByCondition(SchoolInfoPojo schoolInfoPojo) {
        List<School> schools = null;
        try {
            schools = schoolMapper.findAllSchoolListByCondition(schoolInfoPojo);
        } catch (Exception e) {
            logger.error("学院带条件查询出错，错误：" + e);
        }
        return schools;
    }

    /**
     * 将选中的一条学院信息的status改为0
     * @param school
     * @return
     */
    @Override
    public int deleteOneSchool(School school) {
        try {
            schoolMapper.deleteOneSchool(school);
        } catch (Exception e) {
            logger.error("学院删除出错，错误：" + e);
        }
        return 1;
    }

    /**
     * 将选中的多条学院信息status改为0
     * @param schools
     * @return
     */
    @Override
    public int deleteSchools(List<School> schools) {
        try {
            schoolMapper.deleteSchools(schools);
        } catch (Exception e) {
            logger.error("学院批量删除出错，错误：" + e);
        }
        return 1;
    }

    /**
     * 与班级负责人的添加一样
     * @param school
     * @return
     */
    @Transactional
    public School addUserId(School school) {
        User u = new User();
        u.setUsername(school.getPrincipalNumber());
        User user = userMapper.selectOne(u);
        if (user == null) {
            u.setNickname(school.getPrincipalName());
            u.setSalt(school.getPrincipalNumber());
            u.setPassword(MD5Util.getMD5(u.getSalt() + "123456"));
            userMapper.insertSelective(u);
            UserRole ur = new UserRole();
            ur.setUserId(u.getId());
            Role r = new Role();
            r.setName("学院负责人");
            Role role = roleMapper.selectOne(r);
            ur.setRoleId(role.getId());
            userRoleMapper.insert(ur);
            school.setUserId(u.getId());
        } else {
            if (!user.getNickname().equals(school.getPrincipalName())) {
                return null;
            } else if (user.getStatus() == 0){
                userMapper.updateOneStatus(user);
            }
            school.setUserId(user.getId());
        }
        return school;
    }

    //添加对应的老师
    private void addTeacher(School school) {
        Teacher t = new Teacher();
        t.setSchoolId(school.getId());
        t.setName(school.getPrincipalName());
        t.setNumber(school.getPrincipalNumber());
        teacherMapper.insertSelective(t);
    }

    @Transactional
    @Override
    public int updateSchool(School school) {
        //将之前的学院负责人的账户注销
        School s = new School();
        s.setId(school.getId());
        School school1 = schoolMapper.selectOne(s);
        User u1 = new User();
        u1.setId(school1.getUserId());
        userMapper.deleteOneUser(u1);

        User u = new User();
        u.setUsername(school.getPrincipalNumber());
        User user = userMapper.selectOne(u);
        school = addUserId(school);
        if (school == null) {
            return 0;
        }
        try {
            schoolMapper.updateSchool(school);
        } catch (Exception e) {
            logger.error("学院修改出错，错误：" + e);
        }
        if (user == null) {
            addTeacher(school);
        }
        return 1;
    }

    @Transactional
    @Override
    public int addSchool(School school) {
        User u = new User();
        u.setUsername(school.getPrincipalNumber());
        User user = userMapper.selectOne(u);
        school = addUserId(school);
        School s = new School();
        s.setName(school.getName());
        School school1 = schoolMapper.selectOne(s);
        if (school1 != null) {
            school1.setUserId(school.getUserId());
            schoolMapper.updateSchoolStatus(school1);
            return 1;
        }
        if (school == null) {
            return 0;
        }
        try {
            schoolMapper.addSchool(school);
        } catch (Exception e) {
            logger.error("学院增加出错，错误：" + e);
        }
        if (user == null) {
            addTeacher(school);
        }
        return 1;
    }
}
