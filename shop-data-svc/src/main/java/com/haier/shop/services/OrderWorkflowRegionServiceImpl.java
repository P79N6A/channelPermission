package com.haier.shop.services;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderWorkflowRegionReadDao;
import com.haier.shop.model.OrderWorkflowRegion;
import com.haier.shop.service.OrderWorkflowRegionService;

/*
* 作者:张波
* 2017/12/19
* */
@Service
public class OrderWorkflowRegionServiceImpl implements OrderWorkflowRegionService {

    @Autowired
    OrderWorkflowRegionReadDao orderWorkflowRegionReadDao;

    @Override
    public OrderWorkflowRegion get(Integer regionId) {
        return orderWorkflowRegionReadDao.get(regionId);
    }

    @Override
    public List<Map<String, String>> getFdArea(String area) {
        return orderWorkflowRegionReadDao.getFdArea(area);
    }

    @Override
    public List<Map<String, String>> getTrade(String area) {
        return orderWorkflowRegionReadDao.getTrade(area);
    }

    @Override
    public List<Map<String, String>> getSCode(String trade) {
        return orderWorkflowRegionReadDao.getSCode(trade);
    }

    @Override
    public List<OrderWorkflowRegion> getOwfRegion() {
        return orderWorkflowRegionReadDao.getOwfRegion();
    }
}
