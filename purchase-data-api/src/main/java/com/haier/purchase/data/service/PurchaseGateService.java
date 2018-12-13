/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.*;

/**
 *                       
 * @Filename: GateOfTimeDao.java
 * @Version: 1.0
 * @Author: liujifei 刘骥飞
 * @Email: jifei.liu@dhc.com.cn
 *
 */
public interface PurchaseGateService {
	
	/**
     * 额度闸口检索
     * @param params
     * @return
     */
    public List<GateOfLimitItem> selectGateOfLimit(Map<String, Object> params);


	public List<GateOfHistoryLimitItem> selectGateOfHistoryLimit(Map<String, Object> params);

	/**
     * 时间闸口检索
     * @param params
     * @return
     */
    public List<GateItem> selectGateItem(Map<String, Object> params);

    /**
     * 库存超期闸口检索
     * @param params
     * @return
     */
    public List<GateOfStockExceedItem> selectGateOfStockExceed(Map<String, Object> params);

	public Boolean deleteGateOfStockExceed(Map<String, Object> params) ;
	public Boolean saveGateOfStockExceed(List<GateOfStockExceedItem> gateOfStockExceedList);
    /**
     * 库存超期闸口缓存表检索
     * @param params
     * @return
     */
    public List<GateOfStockExceedCatchItem> selectGateOfStockExceedCatch(Map<String, Object> params);

	Boolean updateDefaultTime(Map<String, Object> params);

	public List<GateLimitSumItem> selectLimitSum(Map<String, Object> params);

	void updateLimitSumByMonth(GateLimitSumItem gateLimitSumItem);

	void updateGateOfLimitById(GateOfLimitItem gateOfLimitItem);

	public List<GateOfLimitItem> selectAllGateOfLimit();

	public ServiceResult<Map<String, Integer>> insertGateOfLimit(List<Map<String, Object>> insertObj);

	public ServiceResult<Map<String, Integer>> updateGateOfLimit(List<Map<String, Object>> updateObj);

	public void deleteGateOfLimit();

	public void updateDeleteFlag();

	public ServiceResult<Boolean> isExistGateOfLimit(String category, String channelCode, int i);

	public Boolean updateGateOfLimitById(List<GateOfLimitItem> gateOfLimitList);

	public Boolean updateLimitSum(Map<String, Object> params);

	public Boolean updateLoanNum(List<GateOfLimitItem> gateOfLimitList);


	public List<GateOfDataPrivilegeItem> selectGateOfDataPrivilege(Map<String, Object> params);


	public Boolean deleteGateOfDataPrivilege(Map<String, Object> params);


	public Boolean saveGateOfUserPrivilege(List<GateOfDataPrivilegeItem> gateOfDataPrivilegeItemList);


	public Boolean saveGateOfDataPrivilege(String operatorType, GateOfDataPrivilegeItem gateOfDataPrivilegeItem);


	public List<GateOfDataPrivilegeItem> selectGateOfUserPrivilege(Map<String, Object> params);

	Boolean updateGateItem(GateItemForTransfer gateItemData);
}
