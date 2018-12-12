package com.haier.stock.service;

import com.haier.stock.model.InvStockAgeLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface StockinvStockAgeLogService {
    /**
     * 按渠道统计库存
     * @param date
     * @return
     */
    public List<InvStockAgeLog> countStockGroupByChannel(Date date);
    /**
     * 按渠道统计制定产品组的库存
     * @param date
     * @return
     */
    List<InvStockAgeLog> countStockGroupByChannelWhthSku(Date date, String productGroupName);
}
