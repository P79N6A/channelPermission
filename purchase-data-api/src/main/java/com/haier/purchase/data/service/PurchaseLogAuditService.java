package com.haier.purchase.data.service;


import com.alibaba.fastjson.JSONObject;
import com.haier.purchase.data.model.LogAuditInfo;

import java.util.List;
import java.util.Map;

public interface PurchaseLogAuditService {
    /**
     * 保存操作日志信息
     * @param vo
     */
    public void saveAuditLog(LogAuditInfo vo);

    Map<String, Object> queryLogAudit(Map<String, Object> params);

    List<LogAuditInfo> queryLogAuditExcle(Map<String, Object> params);

    public void log(String orderId, int type, String content, String operUserName,
                    String operUserId, String jude_way_channel, String gate_way_channel,
                    String channel, String category);
    public void unionLog(String model, Object oldData, Object newData, String type, String operUserName, String orderId);

}
