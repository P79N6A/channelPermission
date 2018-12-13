package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.T2OrderQueryDao;
import com.haier.purchase.data.model.CrmOrderItem;
import com.haier.purchase.data.model.T2OrderItem;
import com.haier.purchase.data.service.PurchaseT2OrderQueryService;

/**
 * @author 李超
 *
 */
@Service
public class PurchaseT2OrderQueryServiceImpl implements PurchaseT2OrderQueryService {

	@Autowired
	T2OrderQueryDao t2OrderQueryDao;

	/**
	 * 根据条件查询获取T+2订单信息
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<T2OrderItem> findT2OrderMultipleList(Map<String, Object> params) {
		return t2OrderQueryDao.findT2OrderMultipleList(params);
	}

	/**
	 * 获取入库时间为空的订单
	 *
	 * @param params
	 * @return
	 */
	@Override
	public CrmOrderItem getIsNullWaInTime(Map<String, Object> params) {
		return t2OrderQueryDao.getIsNullWaInTime(params);
	}

	/**
	 * 获得条数
	 * 
	 * @return
	 */
	@Override
	public int getRowCnts(Map<String, Object> params) {
		return t2OrderQueryDao.getRowCnts(params);
	}

	/**
	 * 手工关单
	 * 
	 * @param params
	 */
	@Override
	public void manualCloseOrder(Map<String, Object> params) {
		t2OrderQueryDao.manualCloseOrder(params);
	}

	/**
	 * 撤销手工关单
	 * 
	 * @param params
	 */
	@Override
	public void cancelCloseOrder(Map<String, Object> params) {
		t2OrderQueryDao.cancelCloseOrder(params);
	}

	/**
	 * OMS已冻结推送
	 * 
	 * @param params
	 */
	@Override
	public int commitAgainOrderMultiple(Map<String, Object> params) {
		return t2OrderQueryDao.commitAgainOrderMultiple(params);
	}

	/**
	 * 获取PO查询信息
	 * 
	 * @param Map
	 *            <String, Object> params
	 * @return
	 */
	@Override
	public List<CrmOrderItem> findPOList(Map<String, Object> params) {
		return t2OrderQueryDao.findPOList(params);
	}

	/**
	 * 获取PO信息条数
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public int findPOListCNT(Map<String, Object> params) {
		return t2OrderQueryDao.findPOListCNT(params);
	}

	@Override
	public List<T2OrderItem> findT2OrderMultipleExportList(Map<String, Object> params) {
		return  t2OrderQueryDao.findT2OrderMultipleExportList(params);
	}

	@Override
	public Integer getByOrderId(String orderId) {
		return t2OrderQueryDao.getByOrderId(orderId);
	}
}
