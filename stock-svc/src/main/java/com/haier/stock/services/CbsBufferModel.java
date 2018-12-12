package com.haier.stock.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.PurchaseOrderInfoItem;
import com.haier.purchase.data.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.haier.common.util.JsonUtil;
@Service
public class  CbsBufferModel {
	 private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
             .getLogger(CbsBufferModel.class);
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	 public List<PurchaseOrderInfoItem> getOrderInfoByDnSi(List<String> dn_si_list) {
	        Map param = new HashMap();
	        param.put("si_dn_ids", dn_si_list);
	        List<PurchaseOrderInfoItem> l = purchaseOrderService.getOrderInfoByDnSi(param);
	        /*log.info("查询订单信息:" + JsonUtil.toJson(dn_si_list));
	        log.info("返回" + JsonUtil.toJson(l));*/
	        return l;
	    }
}
