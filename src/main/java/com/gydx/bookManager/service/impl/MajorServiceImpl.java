package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.entity.Major;
import com.gydx.bookManager.mapper.MajorMapper;
import com.gydx.bookManager.mapper.SchoolMapper;
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
}
