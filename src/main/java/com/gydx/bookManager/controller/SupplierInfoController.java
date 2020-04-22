package com.gydx.bookManager.controller;

import com.alibaba.fastjson.JSONObject;
import com.gydx.bookManager.entity.Supplier;
import com.gydx.bookManager.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin
@Controller
public class SupplierInfoController {

    @Autowired
    private SupplierService supplierService;

    @RequestMapping("/getSupplierList")
    @ResponseBody
    public String getSupplierList(Integer page, Integer limit, String name, String tel) {
        JSONObject jsonObject = new JSONObject();
        List<Supplier> suppliers, supplierList;
        if ((name + tel).equals("")) {
            suppliers = supplierService.getSupplierListByPage(page, limit);
            supplierList = supplierService.getAllSupplierList();
        } else {
            suppliers = supplierService.getSupplierListByPageAndCondition(page, limit, name, tel);
            supplierList = supplierService.getAllSupplierListByCondition(name, tel);
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "查询成功");
        jsonObject.put("count", supplierList.size());
        jsonObject.put("data", suppliers);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/updateSupplier")
    @ResponseBody
    public String updateSupplier(@RequestBody Supplier supplier) {
        JSONObject jsonObject = new JSONObject();
        supplierService.updateSupplier(supplier);
        jsonObject.put("msg", "修改成功");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/addSupplier")
    @ResponseBody
    public String addSupplier(@RequestBody Supplier supplier) {
        JSONObject jsonObject = new JSONObject();
        supplierService.addSupplier(supplier);
        jsonObject.put("msg", "添加成功");
        return jsonObject.toJSONString();
    }
}
