package com.haier.svc.service;


import com.alibaba.fastjson.JSONObject;
import com.haier.purchase.data.model.LogAuditInfo;

import java.util.List;
import java.util.Map;

public interface LogAuditService {
	/**
	 * 日志录入
	 * @param orderId
	 * @param type
	 * @param content
	 * @param operUserName
	 * @param operUserId
	 * @param jude_way_channel
	 * @param gate_way_channel
	 * @param channel
	 * @param category
	 */
    public void log(String orderId, int type, String content, String operUserName, String operUserId,
    		String jude_way_channel, String gate_way_channel, String channel, String category);

	public void unionLog(String model, Object oldData, Object newData, String type, String operUserName, String orderId);


	Map<String, Object> queryLogAudit(Map<String, Object> params);

	List<LogAuditInfo> queryLogAuditExcle(Map<String, Object> params);

}
