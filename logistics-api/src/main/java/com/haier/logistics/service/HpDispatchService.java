package com.haier.logistics.service;

import com.haier.common.ServiceResult;

public interface HpDispatchService {
    /**
     * 记录处理结果
     * @param requestXml 接收的数据
     * @param responseXml 处理结果
     * @param message 处理消息
     * @param flag 成功与失败标识 1-成功；0-失败
     * @return
     */
    public ServiceResult<String> saveNetPoint(String requestXml);
}
