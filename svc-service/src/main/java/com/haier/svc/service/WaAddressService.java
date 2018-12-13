package com.haier.svc.service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.WAAddress;

import java.util.List;
import java.util.Map;

public interface WaAddressService {

    /**
     *
     * @title getWAAddress
     * @description 查询数据接口
     */
    public List<WAAddress> getWAAddress(Map<String, Object> params);

    /**
     *
     * @title getWAAddressCount
     * @description 查询数据条数接口
     */
    public Integer getwaAddressCount(Map<String, Object> params);

    public Integer checkMainKey(Map<String, Object> params);

    public void createWaAddress(Map<String, Object> params);

    public void updateWaAddress(Map<String, Object> params);

    public void deleteWaAddress(Map<String, Object> params);
    /**
     *
     * @title openStatusWaAddress
     * @description 数据状态开启
     */
    public void openStatusWaAddress(Map<String, Object> params);
    /**
     *
     * @title closeStatusWaAddress
     * @description 数据状态禁用
     */
    public void closeStatusWaAddress(Map<String, Object> params);

    public ServiceResult<List<WAAddress>> getWAAddressExport(
            Map<String, Object> params);
}
