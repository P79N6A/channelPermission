package com.haier.purchase.data.dao.purchase;


import com.haier.purchase.data.model.LogAuditInfo;

import java.util.List;
import java.util.Map;

public interface LogAuditDao {
    /**
     * 保存操作日志信息
     * @param vo
     */
    public void saveAuditLog(LogAuditInfo vo);

    Integer countLogAuditWithLike(Map<String, Object> params);

    List<LogAuditInfo> getLogAuditList(Map<String, Object> params);
}
