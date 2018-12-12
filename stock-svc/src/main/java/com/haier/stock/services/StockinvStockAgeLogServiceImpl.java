package com.haier.stock.services;

import com.haier.stock.model.InvStockAgeLog;
import com.haier.stock.service.InvStockAgeLogService;
import com.haier.stock.service.StockinvStockAgeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class StockinvStockAgeLogServiceImpl implements StockinvStockAgeLogService {
    @Autowired
    private InvStockAgeLogService invStockAgeLogService;
    /**
     * 按渠道统计库存
     * @param date
     * @return
     */
    public List<InvStockAgeLog> countStockGroupByChannel(Date date){
        return invStockAgeLogService.countStockGroupByChannel(date);
    }
    /**
     * 按渠道统计制定产品组的库存
     * @param date
     * @return
     */
    public List<InvStockAgeLog> countStockGroupByChannelWhthSku(Date date, String productGroupName){
        return invStockAgeLogService.countStockGroupByChannelWhthSku(date,productGroupName);
    }
}
