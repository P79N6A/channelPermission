package com.haier.purchase.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.CnReplenishEntryOrderItemDao;
import com.haier.purchase.data.model.CnReplenishEntryOrderItem;
import com.haier.purchase.data.service.CnReplenishEntryOrderItemService;

@Service
public class CnReplenishEntryOrderItemServiceImpl implements CnReplenishEntryOrderItemService {

	@Autowired
	private CnReplenishEntryOrderItemDao cnReplenishEntryOrderItemDao;

	@Override
	public List<CnReplenishEntryOrderItem> getByReplEntryOrderId(Long id) {
		return cnReplenishEntryOrderItemDao.getByReplEntryOrderId(id);
	}

	@Override
	public void updateStatusAfterInPushToSAP(CnReplenishEntryOrderItem item) {
		cnReplenishEntryOrderItemDao.updateItemAfterPush(item);
	}

	@Override
	public void updateStatusAfterOutPushToSAP(CnReplenishEntryOrderItem item) {
		cnReplenishEntryOrderItemDao.updateStatusAfterOutPushToSAP(item);
	}

}
