package com.haier.stock.services;

import com.haier.stock.dao.stock.InvStockAgeLogDao;
import com.haier.stock.model.InvStockAgeLog;
import com.haier.stock.service.InvStockAgeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class InvStockAgeLogServiceImpl implements InvStockAgeLogService {
    @Autowired
    private InvStockAgeLogDao invStockAgeLogDao;
    public List<InvStockAgeLog> getGroupByDate(String sku, String secCode, Date date){
        return invStockAgeLogDao.getGroupByDate(sku, secCode, date);
    }


    public Integer insert(InvStockAgeLog log){
        return invStockAgeLogDao.insert(log);
    }


    public Integer insertList(List<InvStockAgeLog> logs){
        return invStockAgeLogDao.insertList(logs);
    }


    public Integer delete(String secCode,String sku, String channelCode,Date date){
        return invStockAgeLogDao.delete(secCode, sku, channelCode, date);
    }

    public Integer deleteBySkuSecCode(String secCode,String sku,Date date){
        return invStockAgeLogDao.deleteBySkuSecCode(secCode, sku, date);
    }

    public List<InvStockAgeLog> get(String secCode,String sku,String channelCode,Date date){
        return invStockAgeLogDao.get(secCode, sku, channelCode, date);
    }

    /**
     * 取得超过指定库龄的记录
     * @param age 库龄
     * @return
     */
    public List<InvStockAgeLog> getLatestInvStockLst(int age){
        return invStockAgeLogDao.getLatestInvStockLst(age);
    }

    public List<InvStockAgeLog> getStockLstGreaterThanAge(int age){
        return invStockAgeLogDao.getStockLstGreaterThanAge(age);
    }
    /**
     * 按渠道统计库存
     * @param date
     * @return
     */
    public List<InvStockAgeLog> countStockGroupByChannel(Date date){
        return invStockAgeLogDao.countStockGroupByChannel(date);
    }

    /**
     * 按渠道统计制定产品组的库存
     * @param date
     * @return
     */
    public List<InvStockAgeLog> countStockGroupByChannelWhthSku(Date date,String productGroupName){
        return invStockAgeLogDao.countStockGroupByChannelWhthSku(date, productGroupName);
    }
}
