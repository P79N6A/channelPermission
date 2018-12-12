package com.haier.svc.model;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.CrmOrderVO;
import com.haier.purchase.data.service.PurchaseCrmOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("crmOrderModel")
public class CRMOrderModel {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(CRMOrderModel.class);

	@Autowired
	PurchaseCrmOrderService purchaseCgoServicce;

	public void updateStatusForSOPO(Map map) {
		purchaseCgoServicce.updateStatusForSOPO(map);
	}

	public Integer getMaxFlowFlagInOrder(Map<String, Object> params) {
		return purchaseCgoServicce.getMaxFlowFlagInOrder(params);
	}

	public List<CrmOrderVO> findCRMOrder(Map map) {
		return purchaseCgoServicce.findCRMOrder(map);
	}
}