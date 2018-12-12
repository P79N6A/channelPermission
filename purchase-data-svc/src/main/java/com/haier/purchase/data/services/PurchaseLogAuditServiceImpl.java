package com.haier.purchase.data.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.LogAuditDao;
import com.haier.purchase.data.model.LogAuditInfo;
import com.haier.purchase.data.service.PurchaseLogAuditService;

@Service
public class PurchaseLogAuditServiceImpl implements PurchaseLogAuditService{
	
	@Autowired
	LogAuditDao logAuditDao;
	
    /**
     * 保存操作日志信息
     * @param vo
     */
	@Override
    public void saveAuditLog(LogAuditInfo vo){
		logAuditDao.saveAuditLog(vo);
	}


}
