package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.RefundBillSyncRecord;

public interface RefundBillSyncRecordService {
	//查询天猫返回数据日志
		List<RefundBillSyncRecord> selectByPrimaryKey();
}
