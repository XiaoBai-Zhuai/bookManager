package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.entity.*;
import com.gydx.bookManager.entity.Class;
import com.gydx.bookManager.mapper.*;
import com.gydx.bookManager.pojo.ClassInfoPojo;
import com.gydx.bookManager.service.ClassService;
import com.gydx.bookManager.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    private Logger logger = LoggerFactory.getLogger(ClassServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private MajorMapper majorMapper;
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    MD5Util md5Util;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 给查出来的班级列表中的每一个班级添加上专业、学院、负责人相关信息
     * @param classes
     * @return
     */
    public List<Class> addInfo(List<Class> classes) {
        for (Class aClass : classes) {
            Integer majorId = aClass.getMajorId();
            Major m = new Major();
            m.setId(majorId);
            Major major = majorMapper.selectOne(m);
            Integer schoolId = major.getSchoolId();
            String schoolName = schoolMapper.selectNameById(schoolId);
            aClass.setMajorName(major.getName()); aClass.setSchoolName(schoolName);
            Integer userId = aClass.getUserId();
            if (userId == null) {
                continue;
            }
            User u = new User(); u.setId(userId);
            User user = userMapper.selectOne(u);
            aClass.setPrincipalName(user.getNickname());
            aClass.setPrincipalNumber(user.getUsername());
        }
        return classes;
    }

    /**
     * 分页查询班级列表
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Class> getClassListByPage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<Class> classes = null;
        try {
            classes = classMapper.findClassListByPage(page, limit);
            classes = addInfo(classes);
        } catch (Exception e) {
            logger.error("分页查询班级列表出错，错误：" + e);
        }
        return classes;
    }

    /**
     * 获取班级全部的列表
     * @return
     */
    @Override
    public List<Class> getAllClassList() {
        return classMapper.findAllClassList();
    }

    /**
     * 根据条件分页查询班级列表
     * @param classInfoPojo
     * @return
     */
    @Override
    public List<Class> getClassListByPageAndCondition(ClassInfoPojo classInfoPojo) {
        classInfoPojo.setPage((classInfoPojo.getPage() - 1) * classInfoPojo.getLimit());
        List<Class> classes = null;
        try {
            classes = classMapper.findClassListByPageAndCondition(classInfoPojo);
            classes = addInfo(classes);
        } catch (Exception e) {
            logger.error("班级列表分页条件查询出错，错误：" + e);
        }
        return classes;
    }

    /**
     * 根据条件查询出全部的班级列表
     * @param classInfoPojo
     * @return
     */
    @Override
    public List<Class> getAllClassListByCondition(ClassInfoPojo classInfoPojo) {
        List<Class> classes = null;
        try {
            classes = classMapper.findAllClassListByCondition(classInfoPojo);
        } catch (Exception e) {
            logger.error("班级列表条件查询出错，错误：" + e);
        }
        return classes;
    }

    /**
     * 删除指定的班级，这里的删除是将字段status的值改成0
     * @param c
     * @return
     */
    @Override
    public int deleteOneClass(Class c) {
        try {
            classMapper.deleteOneClass(c);
        } catch (Exception e) {
            logger.error("删除班级出错，错误：" + e);
        }
        return 1;
    }

    /**
     * 根据传进来的班级，先查出是否已经存在对应的负责人，
     * 如果不存在负责人，则先插入新的用户，并将初始密码设置成123456
     * 盐值为该用户的用户名，同时增加user_role表中该用户与“班级负责人”对应记录。
     * 如果已存在该用户，则先判断该用户的昵称跟用户输入的名字是否一致，不一致则返回告诉用户信息输入错误
     * 再判断该用户的status为 1 还是 0
     * 如果为 1 则只需要改变user_role表中的对应关系即可
     * 如果为 0 则要先将该用户的status改为1，再改变user_role表中的对应关系
     * @param c
     * @return
     */
    @Transactional
    public Class addUserId(Class c) {
        User u = new User();
        u.setUsername(c.getPrincipalNumber());
        User user = userMapper.selectOne(u);
        if (user == null) {
            u.setNickname(c.getPrincipalName());
            u.setSalt(c.getPrincipalNumber());
            u.setPassword(MD5Util.getMD5(u.getSalt() + "123456"));
            userMapper.insertSelective(u);
            UserRole ur = new UserRole();
            ur.setUserId(u.getId());
            Role r = new Role();
            r.setName("班级负责人");
            Role role = roleMapper.selectOne(r);
            ur.setRoleId(role.getId());
            userRoleMapper.insert(ur);
            c.setUserId(u.getId());
        } else {
            if (!user.getNickname().equals(c.getPrincipalName())) {
                return null;
            } else if (user.getStatus() == 0){
                userMapper.updateOneStatus(user);
            }
            c.setUserId(user.getId());
            userRoleMapper.updateByClass(user.getId());
        }
        return c;
    }

    //增加对应学生
    @Transactional
    public void addStudent(Class c) {
        Student s = new Student();
        s.setNumber(c.getPrincipalNumber());
        s.setName(c.getPrincipalName());
        s.setClassId(c.getId());
        Major m = new Major(); m.setName(c.getMajorName());
        Major major = majorMapper.selectOne(m);
        s.setMajorId(major.getId());
        School school = new School();
        school.setId(major.getSchoolId());
        School aSchool = schoolMapper.selectOne(school);
        s.setSchoolId(aSchool.getId());
        studentMapper.insertSelective(s);
    }

    /**
     * 更新班级信息
     * @param c
     * @return
     */
    @Transactional
    @Override
    public int updateClass(Class c) {
        User u = new User();
        u.setUsername(c.getPrincipalNumber());
        User user = userMapper.selectOne(u);
        Class c1 = new Class();
        c1.setId(c.getId());
        Class aClass = classMapper.selectOne(c1);
        c = addUserId(c);
        //如果返回来的c为null，代表用户输入信息有误
        if (c == null) {
            return 0;
        }
        //如果修改班级信息的时候，负责人改变了，则要将原负责人的身份改为普通学生
        if (!aClass.getUserId().equals(user.getId())) {
            UserRole ur = new UserRole();
            ur.setUserId(aClass.getUserId());
            userRoleMapper.updateByStudent(ur);
        }
        try {
            classMapper.updateClass(c);
        } catch (Exception e) {
            logger.error("修改班级出错，错误：" + e);
        }
        //如果用户为空则说明对应的学生也不存在，则要添加上对应的学生
        if (user == null) {
            addStudent(c);
        }
        return 1;
    }

    /**
     * 批量将班级的status改为0
     * @param classes
     * @return
     */
    @Override
    public int deleteClasses(List<Class> classes) {
        try {
            classMapper.deleteClasses(classes);
        } catch (Exception e) {
            logger.error("班级批量删除出错，错误：" + e);
        }
        return 1;
    }

    /**
     * 跟修改班级信息类似的流程，只不过要判断要新增的班级是否曾经存在过，如果存在过
     * 只需要将status改为1即可，如果没有再新增
     * @param c
     * @return
     */
    @Transactional
    @Override
    public int addClass(Class c) {
        User u = new User();
        u.setUsername(c.getPrincipalNumber());
        User user = userMapper.selectOne(u);
        c = addUserId(c);
        Class aClass = new Class();
        aClass.setName(c.getName());
        Class aClass1 = classMapper.selectOne(aClass);
        if (c == null) {
            return 0;
        }
        if (aClass1 != null) {
            classMapper.updateClassStatus(aClass1);
            return 1;
        }
        try {
            classMapper.addClass(c);
        } catch (Exception e) {
            logger.error("班级添加出错，错误：" + e);
        }
        if (user == null) {
            addStudent(c);
        }
        return 1;
    }

    /**
     * 根据专业名查询该专业下的课程名，在添加学生时使用
     * @param majorName
     * @return
     */
    @Override
    public List<Class> getClassNameByMajorName(String majorName) {
        List<Class> classes = null;
        try {
            classes = classMapper.findClassByMajor(majorName);
        } catch (Exception e) {
            logger.error("根据专业名查询班级名出错，错误：" + e);
        }
        return classes;
    }
}
