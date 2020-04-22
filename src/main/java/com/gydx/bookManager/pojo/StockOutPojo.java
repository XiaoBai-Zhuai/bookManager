package com.gydx.bookManager.pojo;

public class StockOutPojo {

    private Integer page;
    private Integer limit;
    private String stockOutDate;
    private String bookName;
    private Integer bookSum;
    private String departmentName;

    public String flag() {
        return this.bookName + this.departmentName + this.stockOutDate;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getStockOutDate() {
        return stockOutDate;
    }

    public void setStockOutDate(String stockOutDate) {
        this.stockOutDate = stockOutDate;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
