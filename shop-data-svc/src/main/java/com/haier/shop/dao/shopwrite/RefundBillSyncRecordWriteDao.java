package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefundBillSyncRecordWriteDao {
	int  updataSynState(String id);//更改天猫日志表状态
}
