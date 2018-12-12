/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.dao.purchase;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.CrmOrderVO;



public interface CrmOrderDao {

    public Integer selectCRMOrder(Map<String, Object> params);

    public Integer insertCRMOrder(Map<String, Object> params);

    public void updateCRMOrder(Map<String, Object> params);

    public void updateCRMCancelOrder(Map<String, Object> params);

    public List<CrmOrderVO> findCRMOrder(Map map);

    public void updateStatusForSOPO(Map map);

    public Integer getMaxFlowFlagInOrder(Map map);

    public Integer selectCrmOrderCount(String wp_order_id);
}
