package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.Student;
import com.gydx.bookManager.pojo.StudentInfoPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface StudentMapper extends Mapper<Student> {
    int updateWorkPlaceById(@Param("studentId") Integer studentId, @Param("classId") Integer classId,
                            @Param("majorId") Integer majorId, @Param("schoolId") Integer schoolId);

    List<Student> findStudentListByPage(@Param("page") Integer page, @Param("limit") Integer limit);

    List<Student> findStudentListByPageAndCondition(StudentInfoPojo studentInfoPojo);

    List<Student> findAllStudentListByCondition(StudentInfoPojo studentInfoPojo);

    int updateStudent(Student student);

    int addStudent(Student student);

    int deleteOneStudent(Student student);

    List<Student> selectAllStudentList();

    int updateStatus(Student student1);
}
