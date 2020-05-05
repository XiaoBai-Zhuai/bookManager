package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.entity.Major;
import com.gydx.bookManager.entity.School;
import com.gydx.bookManager.mapper.MajorMapper;
import com.gydx.bookManager.mapper.SchoolMapper;
import com.gydx.bookManager.pojo.MajorInfoPojo;
import com.gydx.bookManager.service.MajorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {

    private Logger logger = LoggerFactory.getLogger(MajorServiceImpl.class);

    @Autowired
    private MajorMapper majorMapper;
    @Autowired
    private SchoolMapper schoolMapper;

    /**
     * 根据学院id获取该学院的所有专业
     * @param schoolId
     * @return
     */
    @Override
    public List<Major> getMajorListBySchool(Integer schoolId) {
        Major major = new Major();
        major.setSchoolId(schoolId);
        List<Major> majors = null;
        try {
            majors = majorMapper.select(major);
        }catch (Exception e) {
            logger.error("根据学院名获取专业名出错，错误：" + e);
        }
        return majors;
    }

    /**
     * 将获取到的专业列表里的每个专业添加上学院的信息
     * @param majors
     * @return
     */
    public List<Major> addInfo(List<Major> majors) {
        for (Major major : majors) {
            School s = new School();
            s.setId(major.getSchoolId());
            School school = schoolMapper.selectOne(s);
            major.setSchoolName(school.getName());
        }
        return majors;
    }

    /**
     * 分页查询status为1的专业列表
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Major> getMajorListByPage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<Major> majors = null;
        try {
            majors = majorMapper.findMajorListByPage(page, limit);
            majors = addInfo(majors);
        } catch (Exception e) {
            logger.error("专业列表分页查询出错，错误：" + e);
        }
        return majors;
    }

    /**
     * 查询出所有status为1的专业
     * @return
     */
    @Override
    public List<Major> getAllMajorList() {
        return majorMapper.findAllMajorList();
    }

    /**
     * 根据条件分页查询出所有status为1的专业
     * @param majorInfoPojo
     * @return
     */
    @Override
    public List<Major> getMajorListByPageAndCondition(MajorInfoPojo majorInfoPojo) {
        majorInfoPojo.setPage((majorInfoPojo.getPage() - 1) * majorInfoPojo.getLimit());
        List<Major> majors = null;
        try {
            majors = majorMapper.findMajorListByPageAndCondition(majorInfoPojo);
            majors = addInfo(majors);
        } catch (Exception e) {
            logger.error("专业列表带条件分页查询出错，错误：" + e);
        }
        return majors;
    }

    /**
     * 根据条件查询出所有status为1的专业列表
     * @param majorInfoPojo
     * @return
     */
    @Override
    public List<Major> getAllMajorListByCondition(MajorInfoPojo majorInfoPojo) {
        List<Major> majors = null;
        try {
            majors = majorMapper.findAllMajorListByCondition(majorInfoPojo);
        } catch (Exception e) {
            logger.error("专业列表待条件查询出错，错误：" + e);
        }
        return majors;
    }

    /**
     * 将选择的专业的status改为0
     * @param major
     * @return
     */
    @Override
    public int deleteOneMajor(Major major) {
        try {
            majorMapper.deleteOneMajor(major);
        } catch (Exception e) {
            logger.error("专业删除时出错，错误：" + e);
        }
        return 1;
    }

    /**
     * 修改专业的信息
     * @param major
     * @return
     */
    @Override
    public int updateMajor(Major major) {
        try {
            majorMapper.updateMajor(major);
        } catch (Exception e) {
            logger.error("专业修改出错，错误：" + e);
        }
        return 1;
    }

    /**
     * 将选择的多个专业的status改为0
     * @param majors
     * @return
     */
    @Override
    public int deleteMajors(List<Major> majors) {
        try {
            majorMapper.deleteMajors(majors);
        } catch (Exception e) {
            logger.error("专业批量删除出错，错误：" + e);
        }
        return 1;
    }

    /**
     * 添加专业
     * @param major
     * @return
     */
    @Override
    public int addMajor(Major major) {
        Major m = new Major();
        m.setName(major.getName());
        m.setSchoolName(major.getSchoolName());
        Major major1 = majorMapper.selectOne(m);
        if (major1 != null) {
            if (major1.getStatus() == 1) {
                return 0;
            } else if (major1.getStatus() == 0) {
                try {
                    majorMapper.updateMajorStatus(major1);
                } catch (Exception e) {
                    logger.error("将专业的status由 0 改为 1 出错，错误：" + e);
                }
                return 1;
            }
        }
        try {
            majorMapper.addMajor(major);
        } catch (Exception e) {
            logger.error("专业添加出错，错误：" + e);
        }
        return 1;
    }

    /**
     * 根据学院名查出所有的专业名
     * @param schoolName
     * @return
     */
    @Override
    public List<Major> getMajorNameBySchoolName(String schoolName) {
        List<Major> majors = null;
        try {
            majors = majorMapper.findMajorBySchool(schoolName);
        } catch (Exception e) {
            logger.error("根据学院名查询专业名出错，错误：" + e);
        }
        return majors;
    }

    /**
     * 根据学院名查询出该学院下所有的专业
     * @param schoolName
     * @return
     */
    @Override
    public List<Major> getMajorListBySchoolName(String schoolName) {
        List<Major> majors = null;
        try {
            majors = majorMapper.getMajorListBySchoolName(schoolName);
        } catch (Exception e) {
            logger.error("根据学院名查询专业列表出错，错误：" + e);
        }
        return majors;
    }
}
