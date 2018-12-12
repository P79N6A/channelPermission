/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.LesStockInfoDao;
import com.haier.purchase.data.model.GetKUCUNInfoFromLESToEHAIERResponseStockQtyEntity;
import com.haier.purchase.data.model.GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity;
import com.haier.purchase.data.service.PurchaseLesStockInfoService;

@Service
public class PurchaseLesStockInfoServiceImpl implements PurchaseLesStockInfoService {

	@Autowired
	LesStockInfoDao lesStockInfoDao;

	@Override
	public void updateInOutInfo(
			GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity info) {
		lesStockInfoDao.updateInOutInfo(info);
	}

	@Override
	public void insertInOutInfo(
			GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity info) {
		lesStockInfoDao.insertInOutInfo(info);
	}

	@Override
	public Integer selectInOutInfo(
			GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity info) {
		return lesStockInfoDao.selectInOutInfo(info);
	}

	@Override
	public int selectStockInfo(
			GetKUCUNInfoFromLESToEHAIERResponseStockQtyEntity info) {
		return lesStockInfoDao.selectStockInfo(info);
	}

	@Override
	public void updateStockInfo(
			GetKUCUNInfoFromLESToEHAIERResponseStockQtyEntity info) {
		lesStockInfoDao.updateStockInfo(info);
	}

	@Override
	public void insertStockInfo(
			GetKUCUNInfoFromLESToEHAIERResponseStockQtyEntity info) {
		lesStockInfoDao.insertStockInfo(info);
	}

	// 根据提单号查询WA出库信息
	@Override
	public List<GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity> selectInOutInfoByBSTNK(
			List<String> ids) {
		return lesStockInfoDao.selectInOutInfoByBSTNK(ids);
	}

	// 根据Les订单号查询WA出库信息
	@Override
	public List<GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity> selectInOutInfoBySo(
			List<String> ids) {
		return lesStockInfoDao.selectInOutInfoBySo(ids);
	}

	// 根据85DN号查询WA出库信息
	@Override
	public List<GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity> selectInOutInfoByDn(
			List<String> dn) {
		return lesStockInfoDao.selectInOutInfoByDn(dn);
	}

	// 根据85DN号查询WA出库信息
	@Override
	public List<GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity> selectInOutInfoBySingleDn(
			String dn) {
		return lesStockInfoDao.selectInOutInfoBySingleDn(dn);
	}

	/**
	 * 查询最后一次更新LES时间
	 * 
	 * @return
	 */
	@Override
	public String selectLastSyncTime() {
		return lesStockInfoDao.selectLastSyncTime();
	}
}
