package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.pojo.StockOutPojo;
import com.gydx.bookManager.entity.StockOut;
import com.gydx.bookManager.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class StockOutController {

    @Autowired
    StockService stockService;

    @RequestMapping("/getStockOutList")
    public String getStockOutList(Integer page, Integer limit, String stockOutDate, String bookName, Integer bookSum, String departmentName) {
        JSONObject jsonObject = new JSONObject();
        StockOutPojo stockOutPojo = new StockOutPojo();
        stockOutPojo.setBookName(bookName); stockOutPojo.setBookSum(bookSum); stockOutPojo.setDepartmentName(departmentName);
        stockOutPojo.setStockOutDate(stockOutDate); stockOutPojo.setPage(page); stockOutPojo.setLimit(limit);
        List<StockOut> stockOuts, stockOutList;
        if (stockOutPojo.flag().equals("")) {
            stockOuts = stockService.getStockOutByPage(stockOutPojo.getPage(), stockOutPojo.getLimit());
            stockOutList = stockService.getAllStockOutList();
        } else {
            stockOuts = stockService.getStockOutByPageAndCondition(stockOutPojo);
            stockOutList = stockService.getAllStockOutListByCondition(stockOutPojo);
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "查询成功");
        jsonObject.put("count", stockOutList.size());
        jsonObject.put("data", stockOuts);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteOneStockOut")
    public String deleteOneStockOut(@RequestBody StockOut stockOut) {
        JSONObject jsonObject = new JSONObject();
        stockService.deleteOneStockOut(stockOut);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteStockOuts")
    public String deleteStockOuts(@RequestBody List<StockOut> stockOuts) {
        JSONObject jsonObject = new JSONObject();
        stockService.deleteStockOuts(stockOuts);
        jsonObject.put("msg", "删除成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/updateStockOut")
    public String updateStockOut(@RequestBody StockOut stockOut) {
        JSONObject jsonObject = new JSONObject();
        stockService.updateStockOut(stockOut);
        jsonObject.put("msg", "更新成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/addStockOut")
    public String addStockOut(@RequestBody StockOut stockOut) {
        JSONObject jsonObject = new JSONObject();
        stockService.addStockOut(stockOut);
        jsonObject.put("msg", "添加成功");
        return jsonObject.toJSONString();
    }

}
