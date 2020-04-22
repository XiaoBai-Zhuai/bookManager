package com.gydx.bookManager.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "stockOut_book")
public class StockOutBook {

    @Column
    private Integer stockOutId;
    @Column
    private Integer bookId;

    public Integer getStockOutId() {
        return stockOutId;
    }

    public void setStockOutId(Integer stockOutId) {
        this.stockOutId = stockOutId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
