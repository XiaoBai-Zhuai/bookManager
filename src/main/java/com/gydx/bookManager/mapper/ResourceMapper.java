package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.Resource;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ResourceMapper extends Mapper<Resource> {
    List<Resource> selectResourcesByRoleId(Integer id);
}
