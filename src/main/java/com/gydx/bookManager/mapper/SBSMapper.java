package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.StockInBookSupplier;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SBSMapper extends Mapper<StockInBookSupplier> {

    StockInBookSupplier selectByStockInId(Integer stockInId);

    int updateByStockInId(StockInBookSupplier sbs);
}
