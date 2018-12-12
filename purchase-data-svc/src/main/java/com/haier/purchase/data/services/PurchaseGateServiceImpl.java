/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.GateDao;
import com.haier.purchase.data.model.GateItem;
import com.haier.purchase.data.model.GateOfLimitItem;
import com.haier.purchase.data.model.GateOfStockExceedCatchItem;
import com.haier.purchase.data.model.GateOfStockExceedItem;
import com.haier.purchase.data.service.PurchaseGateService;

/**
 *                       
 * @Filename: GateOfTimeDao.java
 * @Version: 1.0
 * @Author: liujifei 刘骥飞
 * @Email: jifei.liu@dhc.com.cn
 *
 */
@Service
public class PurchaseGateServiceImpl implements PurchaseGateService{
	
	@Autowired
	GateDao gateDao;
	/**
     * 额度闸口检索
     * @param params
     * @return
     */
	@Override
    public List<GateOfLimitItem> selectGateOfLimit(Map<String, Object> params){
    	return gateDao.selectGateOfLimit(params);
    }

    /**
     * 时间闸口检索
     * @param params
     * @return
     */
	@Override
    public List<GateItem> selectGateItem(Map<String, Object> params){
    	return gateDao.selectGateItem(params);
    }

    /**
     * 库存超期闸口检索
     * @param params
     * @return
     */
	@Override
    public List<GateOfStockExceedItem> selectGateOfStockExceed(Map<String, Object> params){
    	return gateDao.selectGateOfStockExceed(params);
    }

    /**
     * 库存超期闸口缓存表检索
     * @param params
     * @return
     */
	@Override
    public List<GateOfStockExceedCatchItem> selectGateOfStockExceedCatch(Map<String, Object> params){
    	return gateDao.selectGateOfStockExceedCatch(params);
    }
	
}
