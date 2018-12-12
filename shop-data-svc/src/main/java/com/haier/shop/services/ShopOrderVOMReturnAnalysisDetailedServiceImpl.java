package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.OrderVOMReturnAnalysisDetailedWriteDao;
import com.haier.shop.model.OrderVOMReturnAnalysisDetailed;
import com.haier.shop.service.ShopOrderVOMReturnAnalysisDetailedService;

@Service
public class ShopOrderVOMReturnAnalysisDetailedServiceImpl implements ShopOrderVOMReturnAnalysisDetailedService {
   @Autowired
   private OrderVOMReturnAnalysisDetailedWriteDao orderVOMReturnAnalysisDetailedWriteDao;


    public int insert(OrderVOMReturnAnalysisDetailed record){
        return orderVOMReturnAnalysisDetailedWriteDao.insert(record);
    }
}
