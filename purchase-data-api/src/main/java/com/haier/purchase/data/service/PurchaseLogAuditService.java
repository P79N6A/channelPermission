package com.haier.purchase.data.service;


import com.haier.purchase.data.model.LogAuditInfo;

public interface PurchaseLogAuditService {
    /**
     * 保存操作日志信息
     * @param vo
     */
    public void saveAuditLog(LogAuditInfo vo);


}
