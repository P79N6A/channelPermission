package com.haier.stock.service;

import java.util.Date;
import java.util.List;


import com.haier.stock.model.InvStockInOut;

public interface InvStockInOutService {

    /**
     * 获取指定时间段的出库数
     * @param fromDate
     * @param toDate
     * @return
     */
    public Integer getOutStock( String sku, String sCode,
                                String channel, Date fromDate,
                              Date toDate);

    /**
     * 获取指定时间段的入库数
     * @param sku
     * @param sCode
     * @param channel
     * @param fromDate
     * @param toDate
     * @return
     */
    public Integer getInStock(String sku, String sCode,
                              String channel,Date fromDate,
                              Date toDate);

    /**
     * 插入
     * @param invStockInOut
     * @return
     */
    public Integer insert(InvStockInOut invStockInOut);

    /**
     * 获取billNo对应的记录数
     * @param billNo
     * @return
     */
    public Integer getCountByBillNo(String billNo);

    /**
     * 获取关联的记录
     * @param sku
     * @param secCode
     * @param from
     * @param to
     * @return
     */
    public List<InvStockInOut> getReference(String sku,
                                            String secCode,
                                             Date from,  Date to);

    public List<InvStockInOut> getByAgeStatus(Integer ageStatus);

    public Integer updateAgeStatus(Integer id, Integer status,
                                   Integer oldStatus);
}
