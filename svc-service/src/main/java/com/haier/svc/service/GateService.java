package com.haier.svc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.*;

public interface GateService {
	/**
	 * 额度闸口检索
	 * 
	 * @param params
	 * @return
	 */
	public ServiceResult<List<GateOfLimitItem>> findGateOfLimit(Map<String, Object> params);

	/**
	 * 额度闸口历史检索
	 * 
	 * @param params
	 * @return
	 */
	public ServiceResult<List<GateOfHistoryLimitItem>> findGateOfHistoryLimit(Map<String, Object> params);

	ServiceResult<Boolean> modifyDefaultTime(Map<String, Object> params);

	/**
	 * 时间闸口检索
	 * 
	 * @param params
	 * @return
	 */
	public ServiceResult<List<GateItem>> findGateItem(Map<String, Object> params);

	/**
	 * 库存超期闸口检索
	 * 
	 * @param params
	 * @return
	 */
	public ServiceResult<List<GateOfStockExceedItem>> findGateOfStockExceed(Map<String, Object> params);
	public ServiceResult<Boolean> removeGateOfStockExceed(Map<String, Object> params);

	ServiceResult<Boolean> saveGateOfStockExceed(List<GateOfStockExceedItem> gateOfStockExceedList);

	public void unionLog(String model, Object oldData, Object newData, String type, String operUserName, String orderId);

	/**
	 * 库存超期闸口缓存表检索
	 * 
	 * @param params
	 * @return
	 */
	public ServiceResult<List<GateOfStockExceedCatchItem>> findGateOfStockExceedCatch(Map<String, Object> params);

	public List<GateLimitSumItem> selectLimitSum(Map<String, Object> params);

	public void updateLimitSumByMonth(GateLimitSumItem gateLimitSumItem);

	public List<GateOfLimitItem> selectGateOfLimit(Map<String, Object> params);

	public void updateGateOfLimitById(GateOfLimitItem gateOfLimitItem);

	public ServiceResult<List<GateOfLimitItem>> findAllGateOfLimit();

	public ServiceResult<Map<String, Integer>> insertGateOfLimit(List<Map<String, Object>> insertObj);

	public ServiceResult<Map<String, Integer>> updateGateOfLimit(List<Map<String, Object>> updateObj);

	public void deleteGateOfLimit();

	public void updateDeleteFlag();

	public ServiceResult<Boolean> isExistGateOfLimit(String category, String channelCode, int i);

	public ServiceResult<Boolean> modifyGateOfLimitById(List<GateOfLimitItem> gateOfLimitList);

	public ServiceResult<Boolean> updateLimitSum(Map<String, Object> params);

	public ServiceResult<Boolean> modifyLoanNum(List<GateOfLimitItem> gateOfLimitList);

	public ServiceResult<List<GateOfDataPrivilegeItem>> findGateOfDataPrivilege(Map<String, Object> params);

	public ServiceResult<Boolean> removeGateOfDataPrivilege(Map<String, Object> params);

	public ServiceResult<Boolean> saveGateOfUserPrivilege(List<GateOfDataPrivilegeItem> gateOfDataPrivilegeItemList);

	public ServiceResult<Boolean> saveGateOfDataPrivilege(String operatorType,
            GateOfDataPrivilegeItem gateOfDataPrivilegeItem);
	
	public ServiceResult<List<GateOfDataPrivilegeItem>> findGateOfUserPrivilege(Map<String, Object> params);

	ServiceResult<Boolean> modifyGateItem(GateItemForTransfer gateItemData);
}
