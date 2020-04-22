package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.pojo.StockInPojo;
import com.gydx.bookManager.entity.StockIn;
import com.gydx.bookManager.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin
@Controller
public class StockInController {

    @Autowired
    StockService stockService;

    @RequestMapping("/getStockInList")
    @ResponseBody
    public String getStockInList(Integer page, Integer limit, String stockInDate, String bookName, String author,
                                 Double price, String publisher, String publishTime, Integer bookSum, String supplier,
                                 String departmentName, String supplierTel) {
        StockInPojo stockInPojo = new StockInPojo(page, limit, stockInDate, bookName, author, price, bookSum, publisher,
                publishTime, supplier, supplierTel, departmentName);
        JSONObject jsonObject = new JSONObject();
        List<StockIn> stockIns, stockInList;
        if (stockInPojo.flag().equals("")) {
            stockIns = stockService.getStockInListByPage(stockInPojo.getPage(), stockInPojo.getLimit());
            stockInList = stockService.getAllStockInList();
        } else {
            stockIns = stockService.getStockInListByPageAndCondition(stockInPojo);
            stockInList = stockService.getAllStockInListByCondition(stockInPojo);
        }
        jsonObject.put("msg", "查询成功");
        jsonObject.put("code", 0);
        jsonObject.put("count", stockInList.size());
        jsonObject.put("data", stockIns);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteOneStockInById")
    @ResponseBody
    public String deleteOneStockInById(@RequestBody StockIn stockIn) {
        JSONObject jsonObject = new JSONObject();
        stockService.deleteStockInById(stockIn.getId());
        jsonObject.put("msg", "删除成功！");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/deleteStockIns")
    @ResponseBody
    public String deleteStockIns(@RequestBody List<StockIn> stockIns) {
        JSONObject jsonObject = new JSONObject();
        stockService.deleteStockIns(stockIns);
        jsonObject.put("msg", "已删除！");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/addStockIn")
    @ResponseBody
    public String addStockIn(@RequestBody StockInPojo stockInPojo) {
        JSONObject jsonObject = new JSONObject();
        stockService.addStockIn(stockInPojo);
        jsonObject.put("msg", "添加成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/updateStockIn")
    @ResponseBody
    public String updateStockIn(@RequestBody StockInPojo stockInPojo) {
        JSONObject jsonObject = new JSONObject();
        stockService.updateStockIn(stockInPojo);
        jsonObject.put("msg", "修改成功");
        return jsonObject.toJSONString();
    }
}
