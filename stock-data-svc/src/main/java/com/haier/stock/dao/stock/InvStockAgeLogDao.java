package com.haier.stock.dao.stock;


import com.haier.stock.model.InvStockAgeLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface InvStockAgeLogDao {

    List<InvStockAgeLog> getGroupByDate(@Param("sku") String sku, @Param("sec_code") String secCode,
                                        @Param("date") Date date);


    Integer insert(InvStockAgeLog log);


    Integer insertList(List<InvStockAgeLog> logs);


    Integer delete(@Param("sec_code") String secCode, @Param("sku") String sku,
                   @Param("channel_code") String channelCode, @Param("date") Date date);


    Integer deleteBySkuSecCode(@Param("sec_code") String secCode, @Param("sku") String sku,
                               @Param("date") Date date);

    List<InvStockAgeLog> get(@Param("sec_code") String secCode, @Param("sku") String sku,
                             @Param("channel_code") String channelCode, @Param("date") Date date);

    /**
     * 取得超过指定库龄的记录
     * @param age 库龄
     * @return
     */
    List<InvStockAgeLog> getLatestInvStockLst(@Param("age") int age);

    List<InvStockAgeLog> getStockLstGreaterThanAge(@Param("age") int age);
    /**
     * 按渠道统计库存
     * @param date
     * @return
     */
    List<InvStockAgeLog> countStockGroupByChannel(@Param("date") Date date);

    /**
     * 按渠道统计制定产品组的库存
     * @param date
     * @return
     */
    List<InvStockAgeLog> countStockGroupByChannelWhthSku(@Param("date") Date date,
                                                         @Param("productGroupName") String productGroupName);
}
