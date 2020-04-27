package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.entity.*;
import com.gydx.bookManager.entity.Class;
import com.gydx.bookManager.mapper.*;
import com.gydx.bookManager.pojo.StudentInfoPojo;
import com.gydx.bookManager.service.StudentService;
import com.gydx.bookManager.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private MajorMapper majorMapper;
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    MD5Util md5Util;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 将查询出来的学生添加上学院、专业、班级等信息
     * @param students
     * @return
     */
    private List<Student> addInfo(List<Student> students) {
        for (Student student : students) {
            String schoolName = schoolMapper.selectNameById(student.getSchoolId());
            String majorName = majorMapper.selectNameById(student.getMajorId());
            String className = classMapper.selectNameById(student.getClassId());
            student.setSchoolName(schoolName);
            student.setMajorName(majorName);
            student.setClassName(className);
        }
        return students;
    }

    /**
     * 根据用户名查找到学生并添加上相关信息
     * @param username
     * @return
     */
    @Override
    public Student getStudentInfo(String username) {
        Student s = new Student();
        s.setNumber(username);
        s.setStatus(1);
        Student student = studentMapper.selectOne(s);
        String schoolName = schoolMapper.selectNameById(student.getSchoolId());
        String majorName = majorMapper.selectNameById(student.getMajorId());
        String className = classMapper.selectNameById(student.getClassId());
        student.setSchoolName(schoolName);
        student.setMajorName(majorName);
        student.setClassName(className);
        return student;
    }

    /**
     * 分页查询学生列表
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Student> getStudentListByPage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<Student> students = null;
        try {
            students = studentMapper.findStudentListByPage(page, limit);
            students = addInfo(students);
        } catch (Exception e) {
            logger.error("学生分页查询出错，错误：" + e);
        }
        return students;
    }

    /**
     * 查询所有的学生列表
     * @return
     */
    @Override
    public List<Student> getAllStudentList() {
        return studentMapper.selectAllStudentList();
    }

    /**
     * 根据条件分页查询学生列表
     * @param studentInfoPojo
     * @return
     */
    @Override
    public List<Student> getStudentListByPageAndCondition(StudentInfoPojo studentInfoPojo) {
        studentInfoPojo.setPage((studentInfoPojo.getPage() - 1) * studentInfoPojo.getLimit());
        List<Student> students = null;
        try {
            students = studentMapper.findStudentListByPageAndCondition(studentInfoPojo);
            students = addInfo(students);
        } catch (Exception e) {
            logger.error("学生带条件分页查询出错，错误：" + e);
        }
        return students;
    }

    /**
     * 根据条件查询出所有的学生列表
     * @param studentInfoPojo
     * @return
     */
    @Override
    public List<Student> getAllStudentListByCondition(StudentInfoPojo studentInfoPojo) {
        List<Student> students = null;
        try {
            students = studentMapper.findAllStudentListByCondition(studentInfoPojo);
        } catch (Exception e) {
            logger.error("学生带条件查询出错，错误：" + e);
        }
        return students;
    }

    /**
     * 将某个学生的status改为0
     * @param student
     * @return
     */
    @Override
    public int deleteOneStudent(Student student) {
        try {
            studentMapper.deleteOneStudent(student);
            User u = new User();
            u.setUsername(student.getNumber());
            User user = userMapper.selectOne(u);
            userMapper.deleteOneUser(user);
        } catch (Exception e) {
            logger.error("学生删除出错，错误：" + e);
        }
        return 1;
    }

    /**
     * 将选中的多个学生的status改为0
     * @param students
     * @return
     */
    @Override
    public int deleteStudents(List<Student> students) {
        for (Student student : students) {
            deleteOneStudent(student);
        }
        return 1;
    }

    /**
     * 修改学生信息，如果输入的学号已存在则返回告诉用户输入信息有误
     * 如果不存在则增加新的用户 否则 修改信息即可
     * @param student
     * @return
     */
    @Override
    public int updateStudent(Student student) {
        Student s = new Student();
        s.setNumber(student.getNumber());
        Student student1 = studentMapper.selectOne(s);
        if (!student.getId().equals(student1.getId())) {
            return 0;
        } else {
            try {
                studentMapper.updateStudent(student);
                User u = new User();
                u.setUsername(student.getNumber());
                User user = userMapper.selectOne(u);
                if (user == null) {
                    User u1 = new User();
                    u1.setUsername(student.getNumber());
                    u1.setNickname(student.getName());
                    u1.setSalt(student.getNumber());
                    u1.setPassword(MD5Util.getMD5(u.getSalt() + "123456"));
                    userMapper.insertSelective(u);
                    UserRole ur = new UserRole();
                    ur.setUserId(u.getId());
                    Role r = new Role();
                    r.setName("普通学生");
                    Role role = roleMapper.selectOne(r);
                    ur.setRoleId(role.getId());
                    userRoleMapper.insert(ur);
                } else {
                    u.setNickname(student.getName());
                    userMapper.updateUserByUsername(u);
                }
            } catch (Exception e) {
                logger.error("学生修改出错，错误：" + e);
            }
        }
        return 1;
    }

    /**
     * 增加新的用户，看是否曾经存在过，不存在则新增，同时新增用户，存在过则将status改为1即可
     * @param student
     * @return
     */
    @Override
    public int addStudent(Student student) {
        Student s = new Student();
        s.setNumber(student.getNumber());
        Student student1 = studentMapper.selectOne(s);
        if (student1 == null) {
            try {
                Class c = new Class();
                c.setName(student.getClassName());
                Class aClass = classMapper.selectOne(c);
                Major m = new Major();
                m.setId(aClass.getMajorId());
                Major major = majorMapper.selectOne(m);
                student.setClassId(aClass.getId());
                student.setMajorId(aClass.getMajorId());
                student.setSchoolId(major.getSchoolId());
                studentMapper.addStudent(student);
                User u = new User();
                u.setUsername(student.getNumber());
                u.setNickname(student.getName());
                u.setSalt(student.getNumber());
                u.setPassword(MD5Util.getMD5(u.getSalt() + "123456"));
                userMapper.insertSelective(u);
                UserRole ur = new UserRole();
                ur.setUserId(u.getId());
                Role r = new Role();
                r.setName("普通学生");
                Role role = roleMapper.selectOne(r);
                ur.setRoleId(role.getId());
                userRoleMapper.insert(ur);
            } catch (Exception e) {
                logger.error("学生修改出错，错误：" + e);
            }
        } else {
            if (!student.getName().equals(student1.getName())) {
                return 0;
            } else if (!student1.getStatus().equals(0)){
                studentMapper.updateStatus(student1);
                userMapper.updateOneStatusByUsername(student1.getNumber());
            }
        }
        return 1;
    }


}
