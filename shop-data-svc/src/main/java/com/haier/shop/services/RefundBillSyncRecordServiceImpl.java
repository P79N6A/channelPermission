package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.RefundBillSyncRecordDao;
import com.haier.shop.dao.shopwrite.RefundBillSyncRecordWriteDao;
import com.haier.shop.model.RefundBillSyncRecord;
import com.haier.shop.service.RefundBillSyncRecordService;
@Service
public class RefundBillSyncRecordServiceImpl implements RefundBillSyncRecordService{
	@Autowired
	private RefundBillSyncRecordDao syncRecordDao;
	@Autowired
	private RefundBillSyncRecordWriteDao syncRecordWriteDao;
	@Override
	public List<RefundBillSyncRecord> selectByPrimaryKey(int i) {
		// TODO Auto-generated method stub
		return syncRecordDao.selectByPrimaryKey(i);
	}
	@Override
	public int updataSynState(String id) {
		// TODO Auto-generated method stub
		return syncRecordWriteDao.updataSynState(id);
	}

}
