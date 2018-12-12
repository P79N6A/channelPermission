package com.haier.shop.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.CostPoolsReadDao;
import com.haier.shop.dao.shopwrite.CostPoolsWriteDao;
import com.haier.shop.model.CostPools;
import com.haier.shop.service.CostPoolsService;

@Service
public class CostPoolsServiceImpl implements CostPoolsService {
    @Autowired
    private CostPoolsReadDao costPoolsReadDao;
    @Autowired
    private CostPoolsWriteDao costPoolsWriteDao;

    public List<CostPools> getCouponCostPoolByChannelAndChanYeAndYearMonth(Integer channel, String chanYe, Integer yearMonth){
        return costPoolsReadDao.getCouponCostPoolByChannelAndChanYeAndYearMonth(channel, chanYe, yearMonth);
    }

    public int updateCouponCostPool(Integer id, BigDecimal couponAmount){
        return costPoolsWriteDao.updateCouponCostPool(id, couponAmount);
    }

    public List<Map<String, Object>> getProductCate(){
        return costPoolsReadDao.getProductCate();
        }

    public List<Map<String, Object>> getBrand(){
        return costPoolsReadDao.getBrand();
    }
}
