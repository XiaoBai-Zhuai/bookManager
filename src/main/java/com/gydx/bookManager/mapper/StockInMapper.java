package com.gydx.bookManager.mapper;

import com.gydx.bookManager.pojo.StockInPojo;
import com.gydx.bookManager.entity.StockIn;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface StockInMapper extends Mapper<StockIn> {
    List<StockIn> selectStockInListByPage(Integer page, Integer limit);

    List<StockIn> selectStockInListByPageAndCondition(StockInPojo stockInPojo);

    List<StockIn> selectStockInListByCondition(StockInPojo stockInPojo);

    int deleteBatchById(List<StockIn> stockIns);

    int updateStockIn(StockInPojo stockInPojo);
}
