/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.dao.purchase;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.*;

/**
 *                       
 * @Filename: GateOfTimeDao.java
 * @Version: 1.0
 * @Author: liujifei 刘骥飞
 * @Email: jifei.liu@dhc.com.cn
 *
 */
public interface GateDao {
	
	/**
     * 额度闸口检索
     * @param params
     * @return
     */
    public List<GateOfLimitItem> selectGateOfLimit(Map<String, Object> params);
	/**
	 * 历史额度闸口检索
	 * @param params
	 * @return
	 */
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
	/**
	 * 库存超期闸口插入数据
	 * @return
	 */
	public Boolean insertGateOfStockExceed(GateOfStockExceedItem gateOfStockExceedItem);
	/**
	 * 库存超期闸口删除数据
	 * @param params
	 * @return
	 */
	public Boolean deleteGateOfStockExceed(Map<String, Object> params);
	/**
	 * 库存超期闸口删除全部数据
	 * @return
	 */
	public Boolean deleteAllDataOfGateOfStockExceed();
	/**
	 * 时间闸口恢复默认
	 * @param params
	 * @return
	 */
	public Boolean updateDefaultTime(Map<String, Object> params);
    /**
     * 库存超期闸口缓存表检索
     * @param params
     * @return
     */
    public List<GateOfStockExceedCatchItem> selectGateOfStockExceedCatch(Map<String, Object> params);

	public List<GateLimitSumItem> selectLimitSum(Map<String, Object> params);

	public void updateLimitSumByMonth(GateLimitSumItem gateLimitSumItem);

	public void updateGateOfLimitById(GateOfLimitItem gateOfLimitItem);

	public List<GateOfLimitItem> selectAllGateOfLimit();

	public void insertGateOfLimit(Map<String, Object> param);

	public void updateGateOfLimit(Map<String, Object> param);

	public void deleteGateOfLimit();

	public void updateDeleteFlag();

	public int isExistGateOfLimit(String category, String channelCode, int month);

	public void updateLimitSum(Map<String, Object> params);

	public void updateLoanNum(GateOfLimitItem gateOfLimitItem);
	
	public List<GateOfDataPrivilegeItem> selectGateOfDataPrivilege(Map<String, Object> params);
	
	public void deleteGateOfUserPrivilege(Map<String, Object> params);
	
	public void deleteGateOfDataPrivilege(Map<String, Object> params);
	
	public List<GateOfDataPrivilegeItem> selectGateOfUserPrivilege(Map<String, Object> params);
	
	public void updateGateOfUserPrivilege(GateOfDataPrivilegeItem gateOfDataPrivilegeItem);
	
	public void insertGateOfUserPrivilege(GateOfDataPrivilegeItem gateOfDataPrivilegeItem);
	
	public Boolean insertGateOfDataPrivilege(GateOfDataPrivilegeItem gateOfDataPrivilegeItem);
	
	public Boolean updateGateOfDataPrivilege(GateOfDataPrivilegeItem gateOfDataPrivilegeItem);

	void updateGateItem(GateItem temp);
}
