package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.entity.StockInBookSupplier;
import com.gydx.bookManager.entity.Supplier;
import com.gydx.bookManager.mapper.SBSMapper;
import com.gydx.bookManager.mapper.SupplierMapper;
import com.gydx.bookManager.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    /**
     * 分页查询出供应商列表
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Supplier> getSupplierListByPage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<Supplier> suppliers = supplierMapper.selectSupplierListByPage(page, limit);
        return suppliers;
    }

    /**
     * 获取全部的供应商列表
     * @return
     */
    @Override
    public List<Supplier> getAllSupplierList() {
        return supplierMapper.selectAll();
    }

    /**
     * 根据条件分页查询出供应商列表
     * @param page
     * @param limit
     * @param name
     * @param tel
     * @return
     */
    @Override
    public List<Supplier> getSupplierListByPageAndCondition(Integer page, Integer limit, String name, String tel) {
        page = (page - 1) * limit;
        List<Supplier> suppliers = supplierMapper.selectSupplierListByPageAndCondition(page, limit, name, tel);
        return suppliers;
    }

    /**
     * 根据条件获取全部的供应商列表
     * @param name
     * @param tel
     * @return
     */
    @Override
    public List<Supplier> getAllSupplierListByCondition(String name, String tel) {
        return supplierMapper.selectAllSupplierListByCondition(name, tel);
    }

    /**
     * 修改供应商信息
     * @param supplier
     * @return
     */
    @Override
    public int updateSupplier(Supplier supplier) {
        return supplierMapper.updateSupplier(supplier);
    }

    /**
     * 增加新的供应商
     * @param supplier
     * @return
     */
    @Override
    public int addSupplier(Supplier supplier) {
        return supplierMapper.insert(supplier);
    }
}
