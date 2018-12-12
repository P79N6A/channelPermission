package com.haier.svc.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haier.purchase.data.model.T2OrderItem;
import com.haier.purchase.data.service.PurchaseT2OrderService;

@Service("t2OrderModel")
public class T2OrderModel {
    private static org.apache.log4j.Logger log                  = org.apache.log4j.LogManager
                                                                    .getLogger(T2OrderModel.class);

    @Autowired
    private PurchaseT2OrderService purchaseT2OrderService;

    private static int                     FLOW_FLAG_NOCOMMITED = 0;

    private static String                  DELETE_FLAG_UNDELETE = "0";




    /**
     * 提报OMS的状态更新
     * 
     * @param params
     * @return
     */
    public Boolean updateByOms(T2OrderItem t2OrderItem) {
        try {
            // 情报更新
            purchaseT2OrderService.updateByOms(t2OrderItem);
            if (t2OrderItem.getFlow_flag() != null)
                purchaseT2OrderService.updateOmsFlowFlagOnly(t2OrderItem);
        } catch (Exception ex) {
            // 记录日志
            log.error("[T2OrderModel][updateByOms]:提报OMS的状态更新失败:", ex);
            return false;
        }
        // 返回执行成功
        return true;
    }
    
    public List<String> get85DNFromHaierT2(Map<String, Object> params) {
        return purchaseT2OrderService.get85DNFromHaierT2(params);
    }

	public void updateStatusToInWAByDN(Map<String, Object> params) {
		purchaseT2OrderService.updateStatusToInWAByDN(params);
	}

}

