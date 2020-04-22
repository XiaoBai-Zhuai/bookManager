package com.gydx.bookManager.service;

import com.gydx.bookManager.entity.Resource;

import java.util.List;

public interface ResourceService {
    List<Resource> getResourcesByUserId(Integer id);
}
