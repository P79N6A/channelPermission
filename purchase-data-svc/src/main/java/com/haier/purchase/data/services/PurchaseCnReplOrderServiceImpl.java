package com.haier.purchase.data.services;

import com.haier.purchase.data.dao.purchase.CnReplOrderDao;
import com.haier.purchase.data.model.ReplenishOrder;
import com.haier.purchase.data.model.ReplenishOrderConfirmDisplayItem;
import com.haier.purchase.data.model.ReplenishOrderDisplayItem;
import com.haier.purchase.data.service.PurchaseCnReplOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜鸟预约入库
 **/
@Service
public class PurchaseCnReplOrderServiceImpl implements PurchaseCnReplOrderService {

    @Autowired
    private CnReplOrderDao cnReplOrderDao;

    @Override
    public Map<String, Object> getReplOrdersByPage(Map<String, Object> paramMap) {
        //获取开单列表List
        List<ReplenishOrderDisplayItem> result = cnReplOrderDao.getReplOrders(paramMap);
        //获得条数
        int resultcount = cnReplOrderDao.getRowCnts();
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("total", resultcount);
        retMap.put("rows", result);
        return retMap;
    }

    @Override
    public ReplenishOrderConfirmDisplayItem getById4Confirm(Integer id) {
        return cnReplOrderDao.getById4Confirm(id);
    }

    @Override
    public ReplenishOrder getById(Integer id) {
        return cnReplOrderDao.getById(id);
    }

    @Override
    public int confirmOrder(ReplenishOrder order) {
        return cnReplOrderDao.confirmOrder(order);
    }
}
