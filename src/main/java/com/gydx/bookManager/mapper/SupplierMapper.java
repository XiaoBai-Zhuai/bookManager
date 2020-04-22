package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.Supplier;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SupplierMapper extends Mapper<Supplier> {
    String selectNameById(@Param("supplierId") int supplierId);

    Integer selectIdByName(@Param("supplier") String supplier);

    Supplier selectByStockInId(Integer id);

    List<Supplier> selectSupplierListByPage(@Param("page") Integer page, @Param("limit") Integer limit);

    List<Supplier> selectSupplierListByPageAndCondition(@Param("page") Integer page, @Param("limit") Integer limit, @Param("name")
            String name, @Param("tel") String tel);

    List<Supplier> selectAllSupplierListByCondition(@Param("name") String name, @Param("tel") String tel);

    int updateSupplier(Supplier supplier);
}
