package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.RefundBillSyncRecordDao;
import com.haier.shop.model.RefundBillSyncRecord;
import com.haier.shop.service.RefundBillSyncRecordService;
@Service
public class RefundBillSyncRecordServiceImpl implements RefundBillSyncRecordService{
	@Autowired
private RefundBillSyncRecordDao syncRecordDao;
	@Override
	public List<RefundBillSyncRecord> selectByPrimaryKey() {
		// TODO Auto-generated method stub
		return syncRecordDao.selectByPrimaryKey();
	}

}
