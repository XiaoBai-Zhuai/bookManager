package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.StockInBookSupplier;
import tk.mybatis.mapper.common.Mapper;

public interface SBSMapper extends Mapper<StockInBookSupplier> {

    StockInBookSupplier selectByStockInId(Integer stockInId);

    int updateByStockInId(StockInBookSupplier sbs);
}
