package com.gydx.bookManager.service;

import com.gydx.bookManager.pojo.StockInPojo;
import com.gydx.bookManager.pojo.StockOutPojo;
import com.gydx.bookManager.entity.StockIn;
import com.gydx.bookManager.entity.StockOut;

import java.util.List;

public interface StockService {
    List<StockIn> getStockInListByPage(Integer page, Integer limit);

    List<StockIn> getAllStockInList();

    List<StockIn> getStockInListByPageAndCondition(StockInPojo stockInPojo);

    List<StockIn> getAllStockInListByCondition(StockInPojo stockInPojo);

    int deleteStockInById(Integer id);

    int deleteStockIns(List<StockIn> stockIns);

    int addStockIn(StockInPojo stockInPojo);

    int updateStockIn(StockInPojo stockInPojo);

    List<StockOut> getStockOutByPage(Integer page, Integer limit);

    List<StockOut> getAllStockOutList();

    List<StockOut> getStockOutByPageAndCondition(StockOutPojo stockOutPojo);

    List<StockOut> getAllStockOutListByCondition(StockOutPojo stockOutPojo);

    int deleteOneStockOut(StockOut stockOut);

    int deleteStockOuts(List<StockOut> stockOuts);

    int updateStockOut(StockOut stockOut);

    int addStockOut(StockOut stockOut);
}
