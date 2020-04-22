package com.gydx.bookManager.mapper;

import com.gydx.bookManager.pojo.StockOutPojo;
import com.gydx.bookManager.entity.StockOut;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StockOutMapper extends Mapper<StockOut> {
    List<StockOut> selectStockOutByPage(@Param("page") Integer page, @Param("limit") Integer limit);

    List<StockOut> selectStockOutByPageAndCondition(StockOutPojo stockOutPojo);

    List<StockOut> selectAllStockOutByCondition(StockOutPojo stockOutPojo);

    int updateStockOut(StockOut stockOut);
}
