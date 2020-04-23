package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.Resource;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ResourceMapper extends Mapper<Resource> {
    List<Resource> selectResourcesByRoleId(Integer id);
}
