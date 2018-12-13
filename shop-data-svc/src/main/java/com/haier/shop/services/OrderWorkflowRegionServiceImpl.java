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
    public List<Map<String, String>> getSCodeTradeWl() {
        return orderWorkflowRegionReadDao.getSCodeTradeWl();
    }

    @Override
    public List<Map<String, String>> getSCodeByLes(String les) {
        return orderWorkflowRegionReadDao.getSCodeByLes(les);
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

    @Override
    public List<Map<String, String>> getCommissioner(String area) {
        return orderWorkflowRegionReadDao.getCommissioner(area);
    }

    @Override
    public List<Map<String, String>> getCommissionerTrade(String areaCommissioner) {
        return orderWorkflowRegionReadDao.getCommissionerTrade(areaCommissioner);
    }

    @Override
    public List<Map<String, String>> getSmallChannelPeopleTrade(String areaCommissioner) {
        return orderWorkflowRegionReadDao.getSmallChannelPeopleTrade(areaCommissioner);
    }

    @Override
    public List<Map<String, Object>> getCateInfo() {
        return orderWorkflowRegionReadDao.getCateInfo();
    }
    @Override
    public List<Map<String, Object>> getStoreIdAndName() {
        return orderWorkflowRegionReadDao.getStoreIdAndName();
    }

    @Override
    public String getLoginPersonChannel(String userName) {
        return orderWorkflowRegionReadDao.getLoginPersonChannel(userName);
    }
}
