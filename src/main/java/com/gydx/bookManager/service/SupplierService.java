package com.gydx.bookManager.service;


import com.gydx.bookManager.entity.Supplier;

import java.util.List;

public interface SupplierService {


    List<Supplier> getSupplierListByPage(Integer page, Integer limit);

    List<Supplier> getAllSupplierList();

    List<Supplier> getSupplierListByPageAndCondition(Integer page, Integer limit, String name, String tel);

    List<Supplier> getAllSupplierListByCondition(String name, String tel);

    int updateSupplier(Supplier supplier);

    int addSupplier(Supplier supplier);
}
