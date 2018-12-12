package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.WAAddressDao;
import com.haier.purchase.data.model.WAAddress;
import com.haier.purchase.data.service.PurchaseWAAddressService;

/**
 * 
 * 
 * @Filename: WAAddressDao.java
 * @Version: 1.0
 * @Author: lizhen
 * @Email: zhen1.li@dhc.com.cn
 *
 */
@Service
public class PurchaseWAAddressServiceImpl implements PurchaseWAAddressService {

	@Autowired
	WAAddressDao wAAddressDao;

	/**
	 * 
	 * @title getWAAddressInfo
	 * @description 暴露的接口dao层
	 */
	@Override
	public List<WAAddress> getWAAddressInfo(String waCode) {
		return wAAddressDao.getWAAddressInfo(waCode);
	}

	/**
	 * 查询所有数据
	 * 
	 * @title getWAAddressInfo
	 * @description 暴露的接口dao层
	 */
	public List<WAAddress> getALlWAAddressInfo(String waCode) {
		return wAAddressDao.getALlWAAddressInfo(waCode);
	}

	/**
	 * 
	 * @title getWAAddress
	 * @description 查询数据
	 */
	@Override
	public List<WAAddress> getWAAddress(Map<String, Object> params) {
		return wAAddressDao.getWAAddress(params);
	}

	/**
	 * 
	 * @title getwaAddressCount
	 * @description 查询数据条数
	 */
	@Override
	public Integer getwaAddressCount(Map<String, Object> params) {
		return wAAddressDao.getwaAddressCount(params);
	}

	/**
	 * 
	 * @title createWaAddress
	 * @description 添加数据
	 */
	@Override
	public void createWaAddress(Map<String, Object> params) {
		wAAddressDao.createWaAddress(params);
	}

	/**
	 * 
	 * @title updateWaAddress
	 * @description 更新数据
	 */
	@Override
	public void updateWaAddress(Map<String, Object> params) {
		wAAddressDao.updateWaAddress(params);
	}

	/**
	 * 
	 * @title deleteWaAddress
	 * @description 删除数据
	 */
	@Override
	public void deleteWaAddress(Map<String, Object> params) {
		wAAddressDao.deleteWaAddress(params);
	}

	/**
	 * 
	 * @title openStatusWaAddress
	 * @description 数据状态开启
	 */
	@Override
	public void openStatusWaAddress(Map<String, Object> params) {
		wAAddressDao.openStatusWaAddress(params);
	}

	/**
	 * 
	 * @title closeStatusWaAddress
	 * @description 数据状态禁用
	 */
	@Override
	public void closeStatusWaAddress(Map<String, Object> params) {
		wAAddressDao.closeStatusWaAddress(params);
	}

	/**
	 * 
	 * @Title: getWAAddressExport
	 * @Description: 导出数据
	 * @param params
	 * @return List<WAAddress>
	 */
	@Override
	public List<WAAddress> getWAAddressExport(Map<String, Object> params) {
		return wAAddressDao.getWAAddressExport(params);
	}

	/**
	 * 
	 *
	 * @title:checkMainKey
	 * @description:查询主键是否重复
	 */
	@Override
	public int checkMainKey(Map<String, Object> params) {
		return wAAddressDao.checkMainKey(params);
	}
}