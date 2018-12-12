package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.OmsSyncDao;
import com.haier.purchase.data.model.OmsOrderVO;
import com.haier.purchase.data.service.PurchaseOmsSyncService;

@Service
public class PurchaseOmsSyncServiceImpl implements PurchaseOmsSyncService {

	@Autowired
	OmsSyncDao omsSyncDao;

	/**
	 * 获得需要同步状态的订单列表
	 * 
	 * @return
	 */

	@Override
	public List<String> getOrderIds(OmsOrderVO.QueryCondition condition) {
		return omsSyncDao.getOrderIds(condition);
	}

	@Override
	public List<OmsOrderVO> getOmsOrder(OmsOrderVO.QueryCondition condition) {
		return omsSyncDao.getOmsOrder(condition);
	}

	/**
	 * 更新订单数据
	 * 
	 * @param omsOrderList
	 */
	@Override
	public void updateOrderStatus(OmsOrderVO omsOrderVO) {
		omsSyncDao.updateOrderStatus(omsOrderVO);
	}

	/**
	 * 更新订单状态
	 * 
	 * @param omsOrderList
	 */
	@Override
	public void updateOrderFlowFlag(OmsOrderVO omsOrderVO) {
		omsSyncDao.updateOrderFlowFlag(omsOrderVO);
	}

	@Override
	public void callUpdateProc(Map map) {
		omsSyncDao.callUpdateProc(map);
	}

	@Override
	public void replaceOrderStatus(OmsOrderVO omsOrderVO) {
		omsSyncDao.replaceOrderStatus(omsOrderVO);
	}

	@Override
	public void insertUpdateTable(OmsOrderVO omsOrderVO) {
		omsSyncDao.insertUpdateTable(omsOrderVO);
	}

	@Override
	public void syncDataFromUpdateTable() {
		omsSyncDao.syncDataFromUpdateTable();
	}

	@Override
	public void deleteUpdateTable() {
		omsSyncDao.deleteUpdateTable();
	}
}
