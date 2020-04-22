package com.gydx.bookManager.entity;

import javax.persistence.Table;

@Table(name = "stockIn_book_supplier")
public class StockInBookSupplier {
    private Integer stockInId;
    private Integer bookId;
    private Integer supplierId;

    public Integer getStockInId() {
        return stockInId;
    }

    public void setStockInId(Integer stockInId) {
        this.stockInId = stockInId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
}
