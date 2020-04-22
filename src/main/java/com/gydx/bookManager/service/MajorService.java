package com.gydx.bookManager.service;

import com.gydx.bookManager.entity.Major;

import java.util.List;

public interface MajorService {
    List<Major> getMajorListBySchool(Integer schoolId);
}
