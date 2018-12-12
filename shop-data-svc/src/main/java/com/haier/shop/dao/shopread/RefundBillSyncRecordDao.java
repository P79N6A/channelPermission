package com.haier.shop.dao.shopread;

import java.util.List;

import com.haier.shop.model.RefundBillSyncRecord;

public interface RefundBillSyncRecordDao {
	//查询天猫返回数据日志
	List<RefundBillSyncRecord> selectByPrimaryKey();

}
