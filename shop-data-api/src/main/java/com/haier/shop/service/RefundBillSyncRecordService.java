package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.RefundBillSyncRecord;

public interface RefundBillSyncRecordService {
	// 查询天猫返回数据日志
	/**
	 * @param i  1为包含return_shopconfig中参数  2为不包含return_shopconfig中参数
	 * @return
	 */
	List<RefundBillSyncRecord> selectByPrimaryKey(int i);

	int updataSynState(String id);// 更改天猫日志表状态
}
