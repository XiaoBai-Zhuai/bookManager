package com.gydx.bookManager.mapper;

import com.gydx.bookManager.entity.StockOutBook;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface SBMapper extends Mapper<StockOutBook> {
    int updateBookIdByStockOutId(@Param("stockOutId") Integer id, @Param("bookId") Integer bookId);
}
