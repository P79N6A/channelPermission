package com.haier.purchase.data.service;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.WAAddress;

/**
 * 
 *                       
 * @Filename: WAAddressDao.java
 * @Version: 1.0
 * @Author: lizhen
 * @Email: zhen1.li@dhc.com.cn
 *
 */
public interface PurchaseWAAddressService {
    /**
     * 
     * @title getWAAddressInfo
     * @description 暴露的接口dao层
     */
    public List<WAAddress> getWAAddressInfo(String waCode);

    /**
     * 查询所有数据
     * @title getWAAddressInfo
     * @description 暴露的接口dao层
     */
    public List<WAAddress> getALlWAAddressInfo(String waCode);

    /**
     * 
     * @title getWAAddress
     * @description 查询数据
     */
    public List<WAAddress> getWAAddress(Map<String, Object> params);

    /**
     * 
     * @title getwaAddressCount
     * @description 查询数据条数
     */
    public Integer getwaAddressCount(Map<String, Object> params);

    /**
     * 
     * @title createWaAddress
     * @description 添加数据
     */
    public void createWaAddress(Map<String, Object> params);

    /**
     * 
     * @title updateWaAddress
     * @description 更新数据
     */
    public void updateWaAddress(Map<String, Object> params);

    /**
     * 
     * @title deleteWaAddress
     * @description 删除数据
     */
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

    /**
     * 
     * @Title: getWAAddressExport
     * @Description: 导出数据
     * @param params
     * @return List<WAAddress>
     */
    public List<WAAddress> getWAAddressExport(Map<String, Object> params);

    /**
     * 
     *
     *@title:checkMainKey
     *@description:查询主键是否重复
     */
    public int checkMainKey(Map<String, Object> params);
}