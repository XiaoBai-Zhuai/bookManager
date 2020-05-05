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


    /**
     * 将查出来的入库订单表里的每个入库订单都添加上入库的教材的信息以及供应商信息
     * @param stockIns
     * @return
     */
    public List<StockIn> addStockInInfo(List<StockIn> stockIns) {
        for (StockIn stockIn : stockIns) {
            StockInBookSupplier stockInBookSupplier = sbsMapper.selectByStockInId(stockIn.getId());
            int bookId = stockInBookSupplier.getBookId(), supplierId = stockInBookSupplier.getSupplierId();
            Book record = new Book();
            record.setId(bookId);
            Book book = bookMapper.selectOne(record);
            stockIn.setBookPrice(book.getPrice());
            stockIn.setBookName(book.getName());
            stockIn.setAuthor(book.getAuthor());
            stockIn.setPublisher(book.getPublisher());
            stockIn.setPublishTime(book.getPublishTime());
            Supplier s = new Supplier();
            s.setId(supplierId);
            Supplier supplier = supplierMapper.selectOne(s);
            stockIn.setSupplier(supplier.getName());
            stockIn.setSupplierTel(supplier.getTel());
            //将入库订单的价格与数量相乘获得总价
            Double priceSum = stockIn.getPrice() * stockIn.getBookSum();
            stockIn.setPriceSum(priceSum);
        }
        return stockIns;
    }

    /**
     * 分页查询出入库信息列表
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<StockIn> getStockInListByPage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<StockIn> stockIns = null;
        try {
            stockIns = stockInMapper.selectStockInListByPage(page, limit);
        } catch (Exception e) {
            logger.error("出库分页查询出错，出错：" + e);
        }
        if (stockIns == null) {
            return null;
        }
        stockIns = addStockInInfo(stockIns);
        return stockIns;
    }

    /**
     * 查询出所有的入库信息列表
     *
     * @return
     */
    @Override
    public List<StockIn> getAllStockInList() {
        return stockInMapper.selectAll();
    }

    /**
     * 根据条件分页查询出入库列表
     *
     * @param stockInPojo
     * @return
     */
    @Override
    public List<StockIn> getStockInListByPageAndCondition(StockInPojo stockInPojo) {
        stockInPojo.setPage((stockInPojo.getPage() - 1) * stockInPojo.getLimit());
        List<StockIn> stockIns = null;
        try {
            stockIns = stockInMapper.selectStockInListByPageAndCondition(stockInPojo);
        } catch (Exception e) {
            logger.error("入库带条件分页查询出错，错误：" + e);
        }
        if (stockIns == null) {
            return null;
        }
        stockIns = addStockInInfo(stockIns);
        return stockIns;
    }

    /**
     * 根据条件查询出所有的教材入库信息列表
     *
     * @param stockInPojo
     * @return
     */
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

    /**
     * 删除某条入库信息
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public int deleteStockInById(Integer id) {
        StockInBookSupplier sbs = new StockInBookSupplier();
        sbs.setStockInId(id);
        StockInBookSupplier stockInBookSupplier = sbsMapper.selectOne(sbs);

        Book b = new Book();
        b.setId(stockInBookSupplier.getBookId());
        Book book = bookMapper.selectOne(b);

        StockIn stockIn = new StockIn();
        stockIn.setId(id);
        StockIn sti = stockInMapper.selectOne(stockIn);

        bookMapper.updateStockSum(book.getId(), book.getStockSum() - sti.getBookSum());

        int i = stockInMapper.delete(stockIn);
        int i1 = sbsMapper.delete(sbs);
        return i + i1;
    }

    /**
     * 批量删除选中的入库信息
     *
     * @param stockIns
     * @return
     */
    @Transactional
    @Override
    public int deleteStockIns(List<StockIn> stockIns) {

        for (StockIn stockIn : stockIns) {
            deleteStockInById(stockIn.getId());
        }

        return 1;
    }

    /**
     * 检查用户输入的教材信息和供应商信息是否已存在，如果存在就直接返回各自id
     * 如果不存在就添加之后再返回各自id
     *
     * @param stockInPojo
     * @return
     */
    public String findSupplierAndBook(StockInPojo stockInPojo) {
        Integer supplierId = supplierMapper.selectIdByName(stockInPojo.getSupplier());
        if (supplierId == null) {
            Supplier supplier = new Supplier();
            supplier.setName(stockInPojo.getSupplier());
            supplier.setTel(stockInPojo.getSupplierTel());
            supplierMapper.insert(supplier);
            supplierId = supplier.getId();
        }
        Book b = new Book();
        b.setName(stockInPojo.getBookName());
        b.setPublisher(stockInPojo.getPublisher());
        b.setPublishTime(stockInPojo.getPublisherTime());
        b.setAuthor(stockInPojo.getAuthor());
        b.setPrice(stockInPojo.getBookPrice());
        Book book1 = bookMapper.selectOne(b);
        Integer bookId;
        if (book1 == null) {
            Book book = new Book();
            book.setPublishTime(stockInPojo.getPublisherTime());
            book.setName(stockInPojo.getBookName());
            book.setPublisher(stockInPojo.getPublisher());
            book.setPrice(stockInPojo.getBookPrice());
            book.setAuthor(stockInPojo.getAuthor());
            bookMapper.insertSelective(book);
            bookId = book.getId();
        } else {
            bookId = book1.getId();
        }
        return supplierId + " " + bookId;
    }

    /**
     * 添加入库信息
     *
     * @param stockInPojo
     * @return
     */
    @Transactional
    @Override
    public int addStockIn(StockInPojo stockInPojo) {

        String supplierAndBook = findSupplierAndBook(stockInPojo);
        String[] split = supplierAndBook.split(" ");

        Integer supplierId = Integer.valueOf(split[0]);
        Integer bookId = Integer.valueOf(split[1]);

        Book b = new Book();
        b.setId(bookId);
        Book book = bookMapper.selectOne(b);
        //将入库数量加入到教材库存中
        bookMapper.updateStockSum(bookId, book.getStockSum() + stockInPojo.getBookSum());

        StockIn stockIn = new StockIn();
        stockIn.setBookSum(stockInPojo.getBookSum());
        stockIn.setPrice(stockInPojo.getPrice());
        stockIn.setStockInDate(stockInPojo.getStockInDate());
        stockIn.setDepartmentName(stockInPojo.getDepartmentName());
        stockInMapper.insertSelective(stockIn);
        StockInBookSupplier sbs = new StockInBookSupplier();
        sbs.setStockInId(stockIn.getId());
        sbs.setSupplierId(supplierId);
        sbs.setBookId(bookId);
        sbsMapper.insertSelective(sbs);
        return 1;
    }

    /**
     * 修改某条入库信息
     *
     * @param stockInPojo
     * @return
     */
    @Transactional
    @Override
    public int updateStockIn(StockInPojo stockInPojo) {
        StockInBookSupplier sbs1 = new StockInBookSupplier();
        sbs1.setStockInId(stockInPojo.getId());
        StockInBookSupplier stockInBookSupplier = sbsMapper.selectOne(sbs1);

        StockIn sti = new StockIn();
        sti.setId(stockInPojo.getId());
        StockIn stockIn = stockInMapper.selectOne(sti);

        String supplierAndBook = findSupplierAndBook(stockInPojo);
        String[] split = supplierAndBook.split(" ");

        Integer supplierId = Integer.valueOf(split[0]);
        Integer bookId = Integer.valueOf(split[1]);

        Book b = new Book();
        if (!stockInBookSupplier.getBookId().equals(bookId)) {
            b.setId(stockInBookSupplier.getBookId());
            Book book = bookMapper.selectOne(b);
            //如果用户修改了教材则将原来选择的教材的库存减去之前的入库数量
            bookMapper.updateStockSum(book.getId(), book.getStockSum() - stockIn.getBookSum());
            Book t = new Book();
            b.setId(bookId);
            Book book1 = bookMapper.selectOne(t);
            //同时将现在用户选择的教材的库存加上现在的入库数量
            bookMapper.updateStockSum(bookId, book1.getStockSum() + stockInPojo.getBookSum());
        } else {
            b.setId(bookId);
            Book book = bookMapper.selectOne(b);
            //如果未改变教材，则将库存数量减去原来的入库数量，加上现在的入库数量
            bookMapper.updateStockSum(bookId, book.getStockSum() - stockIn.getBookSum() + stockInPojo.getBookSum());
        }

        stockInMapper.updateStockIn(stockInPojo);
        StockInBookSupplier sbs = new StockInBookSupplier();
        sbs.setStockInId(stockInPojo.getId());
        sbs.setSupplierId(supplierId);
        sbs.setBookId(bookId);
        sbsMapper.updateByStockInId(sbs);
        return 1;
    }

    /**
     * 将查询出来的出库订单表添加上教材信息
     * @param stockOuts
     * @return
     */
    public List<StockOut> addStockOutInfo(List<StockOut> stockOuts) {
        for (StockOut stockOut : stockOuts) {
            StockOutBook sb = new StockOutBook();
            sb.setStockOutId(stockOut.getId());
            StockOutBook stockOutBook = sbMapper.selectOne(sb);
            Book b = new Book();
            b.setId(stockOutBook.getBookId());
            Book book = bookMapper.selectOne(b);
            stockOut.setBookName(book.getName()); stockOut.setAuthor(book.getAuthor());
            stockOut.setPublishTime(book.getPublishTime());
            stockOut.setPublisher(book.getPublisher());
            stockOut.setBookPrice(book.getPrice());
        }
        return stockOuts;
    }

    /**
     * 分页查询出库信息
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<StockOut> getStockOutByPage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<StockOut> stockOuts = null;
        try {
            stockOuts = stockOutMapper.selectStockOutByPage(page, limit);
        } catch (Exception e) {
            logger.error("出库分页查询出错，错误：" + e);
        }
        if (stockOuts == null) {
            return null;
        }
        stockOuts = addStockOutInfo(stockOuts);
        return stockOuts;
    }

    /**
     * 查询出所有的出库信息
     *
     * @return
     */
    @Override
    public List<StockOut> getAllStockOutList() {
        return stockOutMapper.selectAll();
    }

    /**
     * 根据条件分页查询出出库信息
     *
     * @param stockOutPojo
     * @return
     */
    @Override
    public List<StockOut> getStockOutByPageAndCondition(StockOutPojo stockOutPojo) {

        stockOutPojo.setPage((stockOutPojo.getPage() - 1) * stockOutPojo.getLimit());
        List<StockOut> stockOuts = stockOutMapper.selectStockOutByPageAndCondition(stockOutPojo);
        try {
            stockOuts = stockOutMapper.selectStockOutByPageAndCondition(stockOutPojo);
        } catch (Exception e) {
            logger.error("出库带条件分页查询出错，错误：" + e);
        }
        if (stockOuts == null) {
            return null;
        }
        stockOuts = addStockOutInfo(stockOuts);
        return stockOuts;
    }

    /**
     * 根据条件查询出所有的出库信息
     *
     * @param stockOutPojo
     * @return
     */
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

    /**
     * 删除某条出库信息
     *
     * @param stockOut
     * @return
     */
    @Transactional
    @Override
    public int deleteOneStockOut(StockOut stockOut) {
        Integer bookId = getBookId(stockOut);
        Book b = new Book();
        b.setId(bookId);
        Book book = bookMapper.selectOne(b);
        //出库记录删除则将出库的数量加回库存
        bookMapper.updateStockSum(bookId, book.getStockSum() + stockOut.getBookSum());
        StockOutBook sb = new StockOutBook();
        sb.setStockOutId(stockOut.getId());
        sbMapper.delete(sb);
        return stockOutMapper.delete(stockOut);
    }

    /**
     * 删除选中的多条出库信息
     *
     * @param stockOuts
     * @return
     */
    @Override
    public int deleteStockOuts(List<StockOut> stockOuts) {
        for (StockOut stockOut : stockOuts) {
            deleteOneStockOut(stockOut);
        }
        return 1;
    }

    /**
     * 根据用户选择的教材信息获取该教材id
     * @param stockOut
     * @return
     */
    public Integer getBookId(StockOut stockOut) {
        Book b = new Book();
        b.setPrice(stockOut.getBookPrice()); b.setAuthor(stockOut.getAuthor());
        b.setPublisher(stockOut.getPublisher()); b.setPublishTime(stockOut.getPublishTime());
        b.setPrice(b.getPrice());
        Book book = bookMapper.selectOne(b);
        return book.getId();
    }

    /**
     * 修改某条出库信息
     *
     * @param stockOut
     * @return
     */
    @Transactional
    @Override
    public int updateStockOut(StockOut stockOut) {
        StockOutBook sb = new StockOutBook();
        sb.setStockOutId(stockOut.getId());
        StockOutBook stockOutBook = sbMapper.selectOne(sb);
        Integer bookId = getBookId(stockOut);
        sbMapper.updateBookIdByStockOutId(stockOut.getId(), bookId);
        StockOut sto = new StockOut();
        sto.setId(stockOut.getId());
        StockOut stockOut1 = stockOutMapper.selectOne(sto);

        if (!stockOutBook.getBookId().equals(bookId)) {
            Book b = new Book();
            b.setId(stockOutBook.getBookId());
            Book book = bookMapper.selectOne(b);
            //如果用户改变了教材，则将库存加回去(加的是之前用户填入的)
            bookMapper.updateStockSum(book.getId(), book.getStockSum() + stockOut1.getBookSum());
            Book t = new Book();
            t.setId(bookId);
            Book book1 = bookMapper.selectOne(t);
            //同时将用户选择的教材的库存数量减掉(减的是用户现在填入的)
            bookMapper.updateStockSum(bookId, book1.getStockSum() - stockOut.getBookSum());
        } else {
            Book b = new Book();
            b.setId(stockOutBook.getBookId());
            Book book = bookMapper.selectOne(b);
            //如果用户未改变教材，则将库存减去用户现在填入的减去用户原来填入的
            bookMapper.updateStockSum(book.getId(), book.getStockSum() - (stockOut.getBookSum() - stockOut1.getBookSum()));
        }
        try {
            stockOutMapper.updateStockOut(stockOut);
        } catch (Exception e) {
            logger.error("出库更新出错，错误：" + e);
        }
        return 1;
    }

    /**
     * 添加出库信息
     *
     * @param stockOut
     * @return
     */
    @Transactional
    @Override
    public int addStockOut(StockOut stockOut) {
        stockOutMapper.insert(stockOut);
        Integer bookId = getBookId(stockOut);
        StockOutBook sb = new StockOutBook();
        sb.setStockOutId(stockOut.getId());
        sb.setBookId(bookId);
        sbMapper.insert(sb);
        Book record = new Book();
        record.setId(bookId);
        Book book = bookMapper.selectOne(record);
        //从库存中减去出库的数量
        book.setStockSum(book.getStockSum() - stockOut.getBookSum());
        bookMapper.updateBook(book);
        return 1;
    }

}
