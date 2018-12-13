/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.services;

import java.sql.SQLException;
import java.util.*;

import com.haier.purchase.data.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.dao.purchase.GateDao;
import com.haier.purchase.data.service.PurchaseGateService;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;

/**
 * 
 * @Filename: GateOfTimeDao.java
 * @Version: 1.0
 * @Author: liujifei 刘骥飞
 * @Email: jifei.liu@dhc.com.cn
 *
 */
@Service
public class PurchaseGateServiceImpl implements PurchaseGateService {

	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(PurchaseGateServiceImpl.class);
	
	//数据权限录入
    private final String                   ADD_DATA_PRIVILEGE    = "add";
    //数据权限修改
    private final String                   MODIFY_DATA_PRIVILEGE = "modify";

	private DataSourceTransactionManager transactionManager;
	@Autowired
	GateDao gateDao;

	/**
	 * 额度闸口检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<GateOfLimitItem> selectGateOfLimit(Map<String, Object> params) {
		return gateDao.selectGateOfLimit(params);
	}

	@Override
	public List<GateOfHistoryLimitItem> selectGateOfHistoryLimit(Map<String, Object> params) {
		return gateDao.selectGateOfHistoryLimit(params);
	}

	/**
	 * 时间闸口检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<GateItem> selectGateItem(Map<String, Object> params) {
		return gateDao.selectGateItem(params);
	}

	/**
	 * 库存超期闸口检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<GateOfStockExceedItem> selectGateOfStockExceed(Map<String, Object> params) {
		return gateDao.selectGateOfStockExceed(params);
	}

	/**
	 * 库存超期闸口保存
	 * @param
	 */
	@Transactional
	public Boolean saveGateOfStockExceed(List<GateOfStockExceedItem> gateOfStockExceedList) {

		try {
			//库存超期闸口录入数据
			gateDao.deleteAllDataOfGateOfStockExceed();
			for (GateOfStockExceedItem gateOfStockExceedItem : gateOfStockExceedList) {
				gateDao.insertGateOfStockExceed(gateOfStockExceedItem);
			}

		} catch (Exception ex) {
			//记录日志
			log.error("[GateModel][saveGateOfStockExceed]:库存超期闸口保存失败:", ex);
			return false;
		}
		//返回执行成功
		return true;
	}

	/**
	 * 库存超期闸口删除数据
	 * @param params
	 * @return
	 */
	public Boolean deleteGateOfStockExceed(Map<String, Object> params) {
		try {
			gateDao.deleteGateOfStockExceed(params);
		} catch (Exception ex) {
			//记录日志
			log.error("[GateModel][deleteGateOfStockExceed]:删除失败:", ex);
			return false;
		}
		//返回执行成功
		return true;
	}

	/**
	 * 库存超期闸口缓存表检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<GateOfStockExceedCatchItem> selectGateOfStockExceedCatch(Map<String, Object> params) {
		return gateDao.selectGateOfStockExceedCatch(params);
	}
	/**
	 * 时间闸口恢复默认
	 * @param params
	 * @return
	 */
	@Override
	public Boolean updateDefaultTime(Map<String, Object> params) {
		try {
			gateDao.updateDefaultTime(params);
		} catch (Exception ex) {
			//记录日志
			log.error("[GateModel][updateDefaultTime]:更新失败:", ex);
			return false;
		}
		//返回执行成功
		return true;
	}
	@Override
	public List<GateLimitSumItem> selectLimitSum(Map<String, Object> params) {
		return gateDao.selectLimitSum(params);
	}

	@Override
	public void updateLimitSumByMonth(GateLimitSumItem gateLimitSumItem) {
		gateDao.updateLimitSumByMonth(gateLimitSumItem);
	}

	@Override
	public void updateGateOfLimitById(GateOfLimitItem gateOfLimitItem) {
		gateDao.updateGateOfLimitById(gateOfLimitItem);
	}

	@Override
	public List<GateOfLimitItem> selectAllGateOfLimit() {
		List<GateOfLimitItem> result = new ArrayList<GateOfLimitItem>();
		try {
			result = gateDao.selectAllGateOfLimit();
		} catch (Exception ex) {
			// log.error("[GateModel][selectAllGateOfLimit]:查询失败:", ex);
			return result;
		}
		return result;
	}

	@Override
	public ServiceResult<Map<String, Integer>> insertGateOfLimit(List<Map<String, Object>> params) {
		ServiceResult<Map<String, Integer>> result = new ServiceResult<Map<String, Integer>>();
		int success = 0;
		int failure = 0;
		try {
			// 录入数据
			for (Map<String, Object> param : params) {
				try {
					gateDao.insertGateOfLimit(param);
					success++;
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					log.error("[GateModel][insertGateOfLimit]:数据插入失败");
					log.error(JsonUtil.toJson(param));
					failure++;
				}
			}
		} catch (Exception ex) {
			// 记录日志
			log.error("[GateModel][insertGateOfLimit]:录入额度闸口数据失败:", ex);

			success = 0;
			failure = params.size();
		}
		// 返回执行成功
		Map map = new HashMap<String, Integer>();
		map.put("success", success);
		map.put("failure", failure);
		result.setResult(map);
		result.setSuccess(true);
		return result;
	}

	@Override
	public ServiceResult<Map<String, Integer>> updateGateOfLimit(List<Map<String, Object>> params) {
		ServiceResult<Map<String, Integer>> result = new ServiceResult<Map<String, Integer>>();
		int success = 0;
		int failure = 0;
		try {
			// 情报更新
			for (Map<String, Object> param : params) {
				try {
					gateDao.updateGateOfLimit(param);
					success++;
				} catch (Exception e) {
					if (e instanceof SQLException) {
						log.error("[GateModel][insertGateOfLimit]:数据更新失败");
						log.error(JsonUtil.toJson(param));
						failure++;
					} else {
						throw e;
					}
				}
			}
		} catch (Exception ex) {
			// 记录日志
			log.error("修改额度闸口数据失败:", ex);

			success = 0;
			failure = params.size();
		}
		// 返回执行成功
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("success", success);
		map.put("failure", failure);
		result.setResult(map);
		result.setSuccess(true);
		return result;
	}

	@Override
	public void deleteGateOfLimit() {
		gateDao.deleteGateOfLimit();
	}

	@Override
	public void updateDeleteFlag() {
		gateDao.updateDeleteFlag();
	}

	@Override
	public ServiceResult<Boolean> isExistGateOfLimit(String category, String channelCode, int i) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(gateDao.isExistGateOfLimit(category, channelCode, i) > 0);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("根据渠道，品类验证是否存在额度闸口录入数据记录失败！，" + e.getMessage());
			// log.error("根据渠道，品类验证是否存在额度闸口录入数据记录失败", e);
		}
		return result;
	}

	@Override
	public Boolean updateGateOfLimitById(List<GateOfLimitItem> gateOfLimitList) {
		/**
		 * 根据ID修改额度闸口数据
		 * 
		 * @param gateOfLimitList
		 * @return
		 */
		try {
			// 情报更新
			for (GateOfLimitItem gateOfLimitItem : gateOfLimitList) {
				gateDao.updateGateOfLimitById(gateOfLimitItem);
			}
		} catch (Exception ex) {
			// 记录日志
			log.error("根据ID修改额度闸口数据失败:", ex);
			return false;
		}
		// 返回执行成功
		return true;
	}

	@Override
	public Boolean updateLimitSum(Map<String, Object> params) {
		try {
			// 情报更新
			gateDao.updateLimitSum(params);
		} catch (Exception ex) {
			// 记录日志
			log.error("更新总额度失败:", ex);
			return false;
		}
		// 返回执行成功
		return true;
	}

	/**
	 * 拆借数据修改
	 * 
	 * @param gateOfLimitList
	 * @return
	 */
	public Boolean updateLoanNum(List<GateOfLimitItem> gateOfLimitList) {
		try {
			// 情报更新
			for (GateOfLimitItem gateOfLimitItem : gateOfLimitList) {
				gateDao.updateLoanNum(gateOfLimitItem);
			}
		} catch (Exception ex) {
			// 记录日志
			log.error("拆借数据修改失败:", ex);
			return false;
		}
		// 返回执行成功
		return true;
	}

	@Override
	public List<GateOfDataPrivilegeItem> selectGateOfDataPrivilege(Map<String, Object> params) {
		List<GateOfDataPrivilegeItem> result = new ArrayList<GateOfDataPrivilegeItem>();
		try {
			result = gateDao.selectGateOfDataPrivilege(params);
		} catch (Exception ex) {
			log.error("[GateModel][selectGateOfDataPrivilege]:查询失败:", ex);
			return result;
		}
		return result;
	}

	@Override
	public Boolean deleteGateOfDataPrivilege(Map<String, Object> params) {
		try {
			gateDao.deleteGateOfDataPrivilege(params);
			gateDao.deleteGateOfUserPrivilege(params);
		} catch (Exception ex) {
			// 记录日志
			log.error("[GateModel][deleteGateOfDataPrivilege]:删除失败:", ex);
			ex.printStackTrace();
			return false;
		}
		// 返回执行成功
		return true;
	}

	@Override
	public Boolean saveGateOfUserPrivilege(List<GateOfDataPrivilegeItem> gateOfDataPrivilegeItemList) {
		Boolean result = true;
		try {
			for (GateOfDataPrivilegeItem gateOfDataPrivilegeItem : gateOfDataPrivilegeItemList) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("user_id", gateOfDataPrivilegeItem.getUser_id());
				List<GateOfDataPrivilegeItem> listTemp = gateDao.selectGateOfUserPrivilege(params);
				if (listTemp.size() > 0) {
					gateDao.updateGateOfUserPrivilege(gateOfDataPrivilegeItem);
				} else {
					gateDao.insertGateOfUserPrivilege(gateOfDataPrivilegeItem);
				}
			}

		} catch (Exception ex) {
			// 记录日志
			log.error("[GateModel][insertGateOfDataPrivilege]:数据保存失败:", ex);
			result = false;
		}
		// 返回执行成功
		return result;
	}

	@Override
	public Boolean saveGateOfDataPrivilege(String operatorType, GateOfDataPrivilegeItem gateOfDataPrivilegeItem) {
		Boolean result = true;
        try {
            if (ADD_DATA_PRIVILEGE.equals(operatorType)) {
                result = gateDao.insertGateOfDataPrivilege(gateOfDataPrivilegeItem);
            } else if (MODIFY_DATA_PRIVILEGE.equals(operatorType)) {
                result = gateDao.updateGateOfDataPrivilege(gateOfDataPrivilegeItem);
            } else {
                result = false;
            }

        } catch (Exception ex) {
            //记录日志
            log.error("[GateModel][insertGateOfDataPrivilege]:数据保存失败:", ex);
            result = false;
        }
        //返回执行成功
        return result;
	}

	@Override
	public List<GateOfDataPrivilegeItem> selectGateOfUserPrivilege(Map<String, Object> params) {
		List<GateOfDataPrivilegeItem> result = new ArrayList<GateOfDataPrivilegeItem>();
        try {
            result = gateDao.selectGateOfUserPrivilege(params);
        } catch (Exception ex) {
            log.error("[GateModel][selectGateOfUserPrivilege]:查询失败:", ex);
            return result;
        }
        return result;
	}

	@Override
	public Boolean updateGateItem(GateItemForTransfer gateItemData) {
		List<GateItem> GateDataList = Arrays.asList(gateItemData.getGateItemData());
		try {
			// 情报更新
			for (GateItem temp : GateDataList) {
				temp.setModify_user(gateItemData.getUser());
				gateDao.updateGateItem(temp);
			}
		} catch (Exception ex) {
			//记录日志
			log.error("[GateModel][updateGateItem]:更新失败:", ex);
			return false;
		}
		//返回执行成功
		return true;
	}

}
