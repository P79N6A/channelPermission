package com.haier.purchase.data.dao.purchase;


import com.haier.purchase.data.model.LogAuditInfo;

public interface LogAuditDao {
    /**
     * 保存操作日志信息
     * @param vo
     */
    public void saveAuditLog(LogAuditInfo vo);


}
