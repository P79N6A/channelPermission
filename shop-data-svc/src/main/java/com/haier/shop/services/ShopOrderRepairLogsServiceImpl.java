package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderRepairLogsReadDao;
import com.haier.shop.dao.shopwrite.OrderRepairLogsWriteDao;
import com.haier.shop.model.OrderRepairLogs;
import com.haier.shop.service.ShopOrderRepairLogsService;
@Service
public class ShopOrderRepairLogsServiceImpl implements ShopOrderRepairLogsService {
    @Autowired
    private OrderRepairLogsReadDao orderRepairLogsReadDao;
    @Autowired
    private OrderRepairLogsWriteDao orderRepairLogsWriteDao;

    public int insert(OrderRepairLogs record){
        return  orderRepairLogsWriteDao.insert(record);
    }
    public int getNextValId(){
        return  orderRepairLogsReadDao.getNextValId();  
        }
    public List<OrderRepairLogs> queryLogs(String id){
        return  orderRepairLogsReadDao.queryLogs(id);
    }
}
