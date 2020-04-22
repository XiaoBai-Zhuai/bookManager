package com.gydx.bookManager.entity;

import javax.persistence.*;
import java.io.Serializable;

public class StockOut implements Serializable {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Transient
    private String bookName;
    @Column
    private Integer bookSum;
    @Column
    private String stockOutDate;
    @Column
    private String departmentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getBookSum() {
        return bookSum;
    }

    public void setBookSum(Integer bookSum) {
        this.bookSum = bookSum;
    }

    public String getStockOutDate() {
        return stockOutDate;
    }

    public void setStockOutDate(String stockOutDate) {
        this.stockOutDate = stockOutDate;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
