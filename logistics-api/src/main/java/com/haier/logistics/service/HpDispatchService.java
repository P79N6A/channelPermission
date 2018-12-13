package com.haier.logistics.service;

import com.haier.common.ServiceResult;

public interface HpDispatchService {

    /**
     *
     * 保存从hp系统推送过来的网点数据，二期被启用，改成从VOM获取返回节点数据
     * @param requestXml 解析数据
     * @return 处理结果
     */
    @Deprecated
    public ServiceResult<String> saveNetPoint(String requestXml);

    /**
     * 保存从VOM推送的派工返回的网点数据
     * @param json  解析数据
     * @return 处理结果
     */
    public ServiceResult<String> saveNetPointFromVom(String json);
}
