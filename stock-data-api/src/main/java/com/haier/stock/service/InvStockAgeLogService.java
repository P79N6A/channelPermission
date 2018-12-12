package com.haier.stock.service;

import com.haier.stock.model.InvStockAgeLog;

import java.util.Date;
import java.util.List;

public interface InvStockAgeLogService {
    public List<InvStockAgeLog> getGroupByDate(String sku,String secCode,Date date);


    public Integer insert(InvStockAgeLog log);


    public Integer insertList(List<InvStockAgeLog> logs);


    public Integer delete(String secCode,String sku, String channelCode,Date date);


    public Integer deleteBySkuSecCode(String secCode,String sku,Date date);

    public List<InvStockAgeLog> get(String secCode,String sku,String channelCode,Date date);

    /**
     * 取得超过指定库龄的记录
     * @param age 库龄
     * @return
     */
    public List<InvStockAgeLog> getLatestInvStockLst(int age);

    public List<InvStockAgeLog> getStockLstGreaterThanAge(int age);
    /**
     * 按渠道统计库存
     * @param date
     * @return
     */
    List<InvStockAgeLog> countStockGroupByChannel(Date date);

    /**
     * 按渠道统计制定产品组的库存
     * @param date
     * @return
     */
    List<InvStockAgeLog> countStockGroupByChannelWhthSku(Date date,String productGroupName);

}
