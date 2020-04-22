package com.gydx.bookManager.pojo;

public class StockInPojo {
    private Integer id;
    private Integer page;
    private Integer limit;
    private String stockInDate;
    private String bookName;
    private String author;
    private Double price;
    private Integer bookSum;
    private String publisher;
    private String publisherTime;
    private String supplier;
    private String supplierTel;
    private String departmentName;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherTime() {
        return publisherTime;
    }

    public void setPublisherTime(String publisherTime) {
        this.publisherTime = publisherTime;
    }

    public String getSupplierTel() {
        return supplierTel;
    }

    public void setSupplierTel(String supplierTel) {
        this.supplierTel = supplierTel;
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

    public String getStockInDate() {
        return stockInDate;
    }

    public void setStockInDate(String stockInDate) {
        this.stockInDate = stockInDate;
    }

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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String flag() {
        return this.departmentName + this.bookName + this.supplier + this.stockInDate;
    }

    public StockInPojo(Integer page, Integer limit, String stockInDate, String bookName, String author, Double price, Integer bookSum, String publisher, String publisherTime, String supplier, String supplierTel, String departmentName) {
        this.page = page;
        this.limit = limit;
        this.stockInDate = stockInDate;
        this.bookName = bookName;
        this.author = author;
        this.price = price;
        this.bookSum = bookSum;
        this.publisher = publisher;
        this.publisherTime = publisherTime;
        this.supplier = supplier;
        this.supplierTel = supplierTel;
        this.departmentName = departmentName;
    }
}
