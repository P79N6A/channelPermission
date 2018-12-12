package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.OrderVOMReturnAnalysisWriteDao;
import com.haier.shop.model.OrderVOMReturnAnalysis;
import com.haier.shop.service.ShopOrderVOMReturnAnalysisService;

@Service
public class ShopOrderVOMReturnAnalysisServiceImpl implements ShopOrderVOMReturnAnalysisService {
    @Autowired
    private OrderVOMReturnAnalysisWriteDao orderVOMReturnAnalysisWriteDao;

    public int insert(OrderVOMReturnAnalysis record){
      return   orderVOMReturnAnalysisWriteDao.insert(record);
    }
}
