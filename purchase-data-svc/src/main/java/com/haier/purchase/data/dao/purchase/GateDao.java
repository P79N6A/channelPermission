/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.dao.purchase;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.GateItem;
import com.haier.purchase.data.model.GateOfLimitItem;
import com.haier.purchase.data.model.GateOfStockExceedCatchItem;
import com.haier.purchase.data.model.GateOfStockExceedItem;

/**
 *                       
 * @Filename: GateOfTimeDao.java
 * @Version: 1.0
 * @Author: liujifei 刘骥飞
 * @Email: jifei.liu@dhc.com.cn
 *
 */
public interface GateDao {
	
	/**
     * 额度闸口检索
     * @param params
     * @return
     */
    public List<GateOfLimitItem> selectGateOfLimit(Map<String, Object> params);

    /**
     * 时间闸口检索
     * @param params
     * @return
     */
    public List<GateItem> selectGateItem(Map<String, Object> params);

    /**
     * 库存超期闸口检索
     * @param params
     * @return
     */
    public List<GateOfStockExceedItem> selectGateOfStockExceed(Map<String, Object> params);

    /**
     * 库存超期闸口缓存表检索
     * @param params
     * @return
     */
    public List<GateOfStockExceedCatchItem> selectGateOfStockExceedCatch(Map<String, Object> params);
	
}
