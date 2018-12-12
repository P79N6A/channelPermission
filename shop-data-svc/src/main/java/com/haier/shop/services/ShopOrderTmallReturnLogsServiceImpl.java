package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderTmallReturnLogsReadDao;
import com.haier.shop.dao.shopwrite.OrderTmallReturnLogsWriteDao;
import com.haier.shop.model.OrderTmallReturnLogs;
import com.haier.shop.service.ShopOrderTmallReturnLogsService;
@Service
public class ShopOrderTmallReturnLogsServiceImpl implements ShopOrderTmallReturnLogsService {
    @Autowired
    private OrderTmallReturnLogsWriteDao orderTmallReturnLogsWriteDao;
    @Autowired
    private OrderTmallReturnLogsReadDao orderTmallReturnLogsReadDao;


    public int insert(OrderTmallReturnLogs record){
        return orderTmallReturnLogsWriteDao.insert(record);
    }

    public List<OrderTmallReturnLogs> selectByPrimaryKey(){
        return orderTmallReturnLogsReadDao.selectByPrimaryKey();
    }
}
