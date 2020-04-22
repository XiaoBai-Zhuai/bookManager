package com.gydx.bookManager.service.impl;

import com.gydx.bookManager.pojo.StockInPojo;
import com.gydx.bookManager.pojo.StockOutPojo;
import com.gydx.bookManager.mapper.*;
import com.gydx.bookManager.entity.*;
import com.gydx.bookManager.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    StockInMapper stockInMapper;
    @Autowired
    StockOutMapper stockOutMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    SupplierMapper supplierMapper;
    @Autowired
    SBSMapper sbsMapper;
    @Autowired
    SBMapper sbMapper;

    @Override
    public List<StockIn> getStockInListByPage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<StockIn> stockIns = null;
        try {
            stockIns = stockInMapper.selectStockInListByPage(page, limit);
        } catch (Exception e) {
            logger.error("出库分页查询出错，出错：" + e);
        }
        for (StockIn stockIn : stockIns) {
            StockInBookSupplier stockInBookSupplier = sbsMapper.selectByStockInId(stockIn.getId());
            int bookId = stockInBookSupplier.getBookId(), supplierId = stockInBookSupplier.getSupplierId();
            Book record = new Book();
            record.setId(bookId);
            Book book = bookMapper.selectOne(record);
//            String bookName = bookMapper.selectNameById(bookId);
            stockIn.setBookName(book.getName());
            String supplier = supplierMapper.selectNameById(supplierId);
            stockIn.setSupplier(supplier);
            int sum = stockIn.getBookSum() + book.getStockSum();
            book.setStockSum(sum);
            bookMapper.updateBook(book);

            Double priceSum = stockIn.getPrice() * stockIn.getBookSum();
            stockIn.setPriceSum(priceSum);
        }
        return stockIns;
    }

    @Override
    public List<StockIn> getAllStockInList() {
        return stockInMapper.selectAll();
    }

    @Override
    public List<StockIn> getStockInListByPageAndCondition(StockInPojo stockInPojo) {
        stockInPojo.setPage((stockInPojo.getPage() - 1) * stockInPojo.getLimit());
        List<StockIn> stockIns = null;
        try {
            stockIns = stockInMapper.selectStockInListByPageAndCondition(stockInPojo);
        } catch (Exception e) {
            logger.error("入库带条件分页查询出错，错误：" + e);
        }
        for (StockIn stockIn : stockIns) {
            Double priceSum = stockIn.getBookSum() * stockIn.getPrice();
            stockIn.setPriceSum(priceSum);
            if (stockInPojo.getBookName().equals("") && !stockInPojo.getSupplier().equals("")) {
                Book book = bookMapper.selectByStockInId(stockIn.getId());
                stockIn.setBookName(book.getName());
                stockIn.setSupplier(stockInPojo.getSupplier());
            } else if (stockInPojo.getSupplier().equals("") && !stockInPojo.getBookName().equals("")) {
                Supplier supplier = supplierMapper.selectByStockInId(stockIn.getId());
                stockIn.setSupplier(supplier.getName());
                stockIn.setBookName(stockInPojo.getBookName());
            } else if((stockInPojo.getSupplier().equals("") && stockInPojo.getBookName().equals(""))) {
                Book book = bookMapper.selectByStockInId(stockIn.getId());
                Supplier supplier = supplierMapper.selectByStockInId(stockIn.getId());
                stockIn.setSupplier(supplier.getName());
                stockIn.setBookName(book.getName());
            } else {
                stockIn.setSupplier(stockInPojo.getSupplier());
                stockIn.setBookName(stockInPojo.getBookName());
            }
        }
        return stockIns;
    }

    @Override
    public List<StockIn> getAllStockInListByCondition(StockInPojo stockInPojo) {
        List<StockIn> stockIns = null;
        try {
            stockIns = stockInMapper.selectStockInListByCondition(stockInPojo);
        } catch (Exception e) {
            logger.error("入库带条件查询错误，错误：" + e);
        }
        return stockIns;
    }

    @Transactional
    @Override
    public int deleteStockInById(Integer id) {
        StockIn stockIn = new StockIn();
        stockIn.setId(id);
        int i = stockInMapper.delete(stockIn);

        StockInBookSupplier sbs = new StockInBookSupplier();
        sbs.setStockInId(id);
        int i1 = sbsMapper.delete(sbs);
        return i + i1;
    }

    @Transactional
    @Override
    public int deleteStockIns(List<StockIn> stockIns) {
        for (StockIn stockIn : stockIns) {
            StockInBookSupplier sbs = new StockInBookSupplier();
            sbs.setStockInId(stockIn.getId());
            sbsMapper.delete(sbs);
        }
        int i = 0;
        try {
            i = stockInMapper.deleteBatchById(stockIns);
        } catch (Exception e) {
            logger.error("批量删除入库出错，错误：" + e);
        }
        return i;
    }

    @Transactional
    @Override
    public int addStockIn(StockInPojo stockInPojo) {
        Integer supplierId = supplierMapper.selectIdByName(stockInPojo.getSupplier());
        if (supplierId == null) {
            Supplier supplier = new Supplier();
            supplier.setName(stockInPojo.getSupplier());
            supplier.setTel(stockInPojo.getSupplierTel());
            supplierMapper.insert(supplier);
            supplierId = supplier.getId();
        }
        Integer bookId = bookMapper.selectIdByName(stockInPojo.getBookName());
        if (bookId == null) {
            Book book = new Book();
            book.setStockSum(stockInPojo.getBookSum());
            book.setPublishTime(stockInPojo.getPublisherTime());
            book.setName(stockInPojo.getBookName());
            book.setPublisher(stockInPojo.getPublisher());
            book.setPrice(stockInPojo.getPrice());
            book.setAuthor(stockInPojo.getAuthor());
            bookMapper.insert(book);
            bookId = book.getId();
        }
        StockIn stockIn = new StockIn();
        stockIn.setBookSum(stockInPojo.getBookSum());
        stockIn.setPrice(stockInPojo.getPrice());
        stockIn.setStockInDate(stockInPojo.getStockInDate());
        stockIn.setDepartmentName(stockInPojo.getDepartmentName());
        stockInMapper.insert(stockIn);
        StockInBookSupplier sbs = new StockInBookSupplier();
        sbs.setStockInId(stockIn.getId());
        sbs.setSupplierId(supplierId);
        sbs.setBookId(bookId);
        sbsMapper.insert(sbs);
        return 1;
    }

    @Transactional
    @Override
    public int updateStockIn(StockInPojo stockInPojo) {
        Integer supplierId = supplierMapper.selectIdByName(stockInPojo.getSupplier());
        if (supplierId == null) {
            Supplier supplier = new Supplier();
            supplier.setName(stockInPojo.getSupplier());
            supplier.setTel(stockInPojo.getSupplierTel());
            supplierMapper.insert(supplier);
            supplierId = supplier.getId();
        }
        Integer bookId = bookMapper.selectIdByName(stockInPojo.getBookName());
        if (bookId == null) {
            Book book = new Book();
            book.setStockSum(stockInPojo.getBookSum());
            book.setPublishTime(stockInPojo.getPublisherTime());
            book.setName(stockInPojo.getBookName());
            book.setPublisher(stockInPojo.getPublisher());
            book.setPrice(stockInPojo.getPrice());
            book.setAuthor(stockInPojo.getAuthor());
            bookMapper.insert(book);
            bookId = book.getId();
        }
        stockInMapper.updateStockIn(stockInPojo);
        StockInBookSupplier sbs = new StockInBookSupplier();
        sbs.setStockInId(stockInPojo.getId());
        sbs.setSupplierId(supplierId);
        sbs.setBookId(bookId);
        sbsMapper.updateByStockInId(sbs);
        return 1;
    }

    @Override
    public List<StockOut> getStockOutByPage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<StockOut> stockOuts = null;
        try {
            stockOuts = stockOutMapper.selectStockOutByPage(page, limit);
        } catch (Exception e) {
            logger.error("出库分页查询出错，错误：" + e);
        }
        for (StockOut stockOut : stockOuts) {
            StockOutBook sb = new StockOutBook();
            sb.setStockOutId(stockOut.getId());
            StockOutBook stockOutBook = sbMapper.selectOne(sb);
            String bookName = bookMapper.selectNameById(stockOutBook.getBookId());
            stockOut.setBookName(bookName);
        }
        return stockOuts;
    }

    @Override
    public List<StockOut> getAllStockOutList() {
        return stockOutMapper.selectAll();
    }

    @Override
    public List<StockOut> getStockOutByPageAndCondition(StockOutPojo stockOutPojo) {

        stockOutPojo.setPage((stockOutPojo.getPage() - 1) * stockOutPojo.getLimit());
        List<StockOut> stockOuts = stockOutMapper.selectStockOutByPageAndCondition(stockOutPojo);
        try {
            stockOuts = stockOutMapper.selectStockOutByPageAndCondition(stockOutPojo);
        } catch (Exception e) {
            logger.error("出库带条件分页查询出错，错误：" + e);
        }
        for (StockOut stockOut : stockOuts) {
            StockOutBook sb = new StockOutBook();
            sb.setStockOutId(stockOut.getId());
            StockOutBook stockOutBook = sbMapper.selectOne(sb);
            String bookName = bookMapper.selectNameById(stockOutBook.getBookId());
            stockOut.setBookName(bookName);
        }
        return stockOuts;
    }

    @Override
    public List<StockOut> getAllStockOutListByCondition(StockOutPojo stockOutPojo) {
        List<StockOut> stockOuts = null;
        try {
            stockOuts = stockOutMapper.selectAllStockOutByCondition(stockOutPojo);
        } catch (Exception e) {
            logger.error("出库带条件查询出错，错误：" + e);
        }
        return stockOuts;
    }

    @Transactional
    @Override
    public int deleteOneStockOut(StockOut stockOut) {
        StockOutBook sb = new StockOutBook();
        sb.setStockOutId(stockOut.getId());
        sbMapper.delete(sb);
        return stockOutMapper.delete(stockOut);
    }

    @Override
    public int deleteStockOuts(List<StockOut> stockOuts) {
        for (StockOut stockOut : stockOuts) {
            deleteOneStockOut(stockOut);
        }
        return 1;
    }

    @Transactional
    @Override
    public int updateStockOut(StockOut stockOut) {
        String bookName = stockOut.getBookName();
        Integer bookId = bookMapper.selectIdByName(bookName);
        sbMapper.updateBookIdByStockOutId(stockOut.getId(), bookId);
        try {
            stockOutMapper.updateStockOut(stockOut);
        } catch (Exception e) {
            logger.error("出库更新出错，错误：" + e);
        }
        return 1;
    }

    @Transactional
    @Override
    public int addStockOut(StockOut stockOut) {
        stockOutMapper.insert(stockOut);
        String bookName = stockOut.getBookName();
        Integer bookId = bookMapper.selectIdByName(bookName);
        StockOutBook sb = new StockOutBook();
        sb.setStockOutId(stockOut.getId());
        sb.setBookId(bookId);
        sbMapper.insert(sb);
        Book record = new Book();
        record.setId(bookId);
        Book book = bookMapper.selectOne(record);
        book.setStockSum(book.getStockSum() - stockOut.getBookSum());
        bookMapper.updateBook(book);
        return 1;
    }

}
