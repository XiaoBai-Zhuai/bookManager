package com.gydx.bookManager.entity;

import javax.persistence.*;
import java.io.Serializable;

public class StockIn implements Serializable {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Transient
    private String bookName;
    @Column
    private Integer bookSum;
    @Column
    private Double price;
    @Transient
    private Double priceSum;
    @Transient
    private String supplier;
    @Column
    private String stockInDate;
    @Column
    private String departmentName;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookSum() {
        return bookSum;
    }

    public void setBookSum(Integer bookSum) {
        this.bookSum = bookSum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(Double priceSum) {
        this.priceSum = priceSum;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getStockInDate() {
        return stockInDate;
    }

    public void setStockInDate(String stockInDate) {
        this.stockInDate = stockInDate;
    }
}
