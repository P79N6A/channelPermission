/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.svc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.haier.purchase.data.model.GateLimitSumItem;
import com.haier.purchase.data.model.GateOfLimitItem;
import com.haier.svc.service.GateService;

/**
 * 
 * @Filename: DataDictionaryModel.java
 * @Version: 1.0
 * @Author: liujifei 刘骥飞
 * @Email: jifei.liu@dhc.com.cn
 *
 */
@Component
public class GateModel {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(GateModel.class);

	// 数据权限录入
	private final String ADD_DATA_PRIVILEGE = "add";
	// 数据权限修改
	private final String MODIFY_DATA_PRIVILEGE = "modify";
	@Autowired
	private GateService gateService;
//	private DataSourceTransactionManager transactionManager;

//	/**
//	 * @param transactionManager
//	 *            The transactionManager to set.
//	 */
//	public void setTransactionManager(DataSourceTransactionManager transactionManager) {
//		this.transactionManager = transactionManager;
//	}
//
//	/**
//	 * 时间闸口检索
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public List<GateItem> selectGateItem(Map<String, Object> params) {
//		List<GateItem> result = new ArrayList<GateItem>();
//		try {
//			result = gateDao.selectGateItem(params);
//			if (result != null && result.size() > 0) {
//				for (GateItem gateItem : result) {
//					if (gateItem.getSetting_week() == null || "".equals(gateItem.getSetting_week())) {
//						gateItem.setSetting_week(gateItem.getDefault_setting_week());
//					}
//					if (gateItem.getSetting_hour() == null || "".equals(gateItem.getSetting_hour())) {
//						gateItem.setSetting_hour(gateItem.getDefault_setting_hour());
//					}
//					if (gateItem.getSetting_minute() == null || "".equals(gateItem.getSetting_minute())) {
//						gateItem.setSetting_minute(gateItem.getDefault_setting_minute());
//					}
//					if (gateItem.getSetting_second() == null || "".equals(gateItem.getSetting_second())) {
//						gateItem.setSetting_second(gateItem.getDefault_setting_second());
//					}
//				}
//			}
//		} catch (Exception ex) {
//			log.error("[GateModel][selectGateItem]:查询失败:", ex);
//			return result;
//		}
//		return result;
//	}
//
//	/**
//	 * 时间闸口更新
//	 * 
//	 * @param GateItem
//	 * @return
//	 */
//	public Boolean updateGateItem(GateItemForTransfer gateItem) {
//		List<GateItem> GateDataList = Arrays.asList(gateItem.getGateItemData());
//		try {
//			// 情报更新
//			for (GateItem temp : GateDataList) {
//				temp.setModify_user(gateItem.getUser());
//				gateDao.updateGateItem(temp);
//			}
//		} catch (Exception ex) {
//			// 记录日志
//			log.error("[GateModel][updateGateItem]:更新失败:", ex);
//			return false;
//		}
//		// 返回执行成功
//		return true;
//	}
//
//	/**
//	 * 时间闸口恢复默认
//	 * 
//	 * @param GateItem
//	 * @return
//	 */
//	public Boolean updateDefaultTime(Map<String, Object> params) {
//		try {
//			gateDao.updateDefaultTime(params);
//		} catch (Exception ex) {
//			// 记录日志
//			log.error("[GateModel][updateDefaultTime]:更新失败:", ex);
//			return false;
//		}
//		// 返回执行成功
//		return true;
//	}
//
//	/**
//	 * 库存超期闸口检索
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public List<GateOfStockExceedItem> selectGateOfStockExceed(Map<String, Object> params) {
//		List<GateOfStockExceedItem> result = new ArrayList<GateOfStockExceedItem>();
//		try {
//			result = gateDao.selectGateOfStockExceed(params);
//		} catch (Exception ex) {
//			log.error("[GateModel][selectGateOfStockExceed]:查询失败:", ex);
//			return result;
//		}
//		return result;
//	}
//
//	/**
//	 * 库存超期闸口保存
//	 * 
//	 * @param
//	 */
//	public Boolean saveGateOfStockExceed(List<GateOfStockExceedItem> gateOfStockExceedList) {
//
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		Assert.notNull(transactionManager, "transactionManager不能为空!");
//		TransactionStatus status = transactionManager.getTransaction(def);
//		try {
//			// 库存超期闸口录入数据
//			gateDao.deleteAllDataOfGateOfStockExceed();
//			for (GateOfStockExceedItem gateOfStockExceedItem : gateOfStockExceedList) {
//				gateDao.insertGateOfStockExceed(gateOfStockExceedItem);
//			}
//			// 提交事务
//			transactionManager.commit(status);
//		} catch (Exception ex) {
//			// 回滚事务
//			transactionManager.rollback(status);
//			// 记录日志
//			log.error("[GateModel][saveGateOfStockExceed]:库存超期闸口保存失败:", ex);
//			return false;
//		}
//		// 返回执行成功
//		return true;
//	}
//
//	/**
//	 * 库存超期闸口删除数据
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public Boolean deleteGateOfStockExceed(Map<String, Object> params) {
//		try {
//			gateDao.deleteGateOfStockExceed(params);
//		} catch (Exception ex) {
//			// 记录日志
//			log.error("[GateModel][deleteGateOfStockExceed]:删除失败:", ex);
//			return false;
//		}
//		// 返回执行成功
//		return true;
//	}
//
//	/**
//	 * 库存超期闸口缓存表检索
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public List<GateOfStockExceedCatchItem> selectGateOfStockExceedCatch(Map<String, Object> params) {
//		List<GateOfStockExceedCatchItem> result = new ArrayList<GateOfStockExceedCatchItem>();
//		try {
//			result = gateDao.selectGateOfStockExceedCatch(params);
//		} catch (Exception ex) {
//			log.error("[GateModel][selectGateOfStockExceedCatch]:查询失败:", ex);
//			return result;
//		}
//		return result;
//	}
//
//	/**
//	 * 备料准确率闸口检索
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public List<GateOfMaterialsAccRateItem> selectGateOfMaterialsAccRate(Map<String, Object> params) {
//		List<GateOfMaterialsAccRateItem> result = new ArrayList<GateOfMaterialsAccRateItem>();
//		try {
//			result = gateDao.selectGateOfMaterialsAccRate(params);
//		} catch (Exception ex) {
//			log.error("[GateModel][selectGateOfMaterialsAccRate]:查询失败:", ex);
//			return result;
//		}
//		return result;
//	}
//
//	/**
//	 * 备料准确率闸口保存
//	 * 
//	 * @param
//	 */
//	public Boolean saveGateOfMaterialsAccRate(List<GateOfMaterialsAccRateItem> gateOfMaterialsAccRateList) {
//
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		Assert.notNull(transactionManager, "transactionManager不能为空!");
//		TransactionStatus status = transactionManager.getTransaction(def);
//		try {
//			// 备料准确率闸口保存数据
//			gateDao.deleteAllDataOfMaterialsAccRate();
//			for (GateOfMaterialsAccRateItem gateOfMaterialsAccRateItem : gateOfMaterialsAccRateList) {
//				gateDao.insertGateOfMaterialsAccRate(gateOfMaterialsAccRateItem);
//			}
//			// 提交事务
//			transactionManager.commit(status);
//		} catch (Exception ex) {
//			// 回滚事务
//			transactionManager.rollback(status);
//			// 记录日志
//			log.error("[GateModel][saveGateOfMaterialsAccRate]:备料准确率闸口保存失败:", ex);
//			return false;
//		}
//		// 返回执行成功
//		return true;
//	}
//
//	/**
//	 * 备料准确率闸口删除数据
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public Boolean deleteGateOfMaterialsAccRate(Map<String, Object> params) {
//		try {
//			gateDao.deleteGateOfMaterialsAccRate(params);
//		} catch (Exception ex) {
//			// 记录日志
//			log.error("[GateModel][deleteGateOfMaterialsAccRate]:删除失败:", ex);
//			return false;
//		}
//		// 返回执行成功
//		return true;
//	}
//
//	/**
//	 * 数据权限设置闸口检索
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public List<GateOfDataPrivilegeItem> selectGateOfDataPrivilege(Map<String, Object> params) {
//		List<GateOfDataPrivilegeItem> result = new ArrayList<GateOfDataPrivilegeItem>();
//		try {
//			result = gateDao.selectGateOfDataPrivilege(params);
//		} catch (Exception ex) {
//			log.error("[GateModel][selectGateOfDataPrivilege]:查询失败:", ex);
//			return result;
//		}
//		return result;
//	}
//
//	/**
//	 * 用户数据权限检索
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public List<GateOfDataPrivilegeItem> selectGateOfUserPrivilege(Map<String, Object> params) {
//		List<GateOfDataPrivilegeItem> result = new ArrayList<GateOfDataPrivilegeItem>();
//		try {
//			result = gateDao.selectGateOfUserPrivilege(params);
//		} catch (Exception ex) {
//			log.error("[GateModel][selectGateOfUserPrivilege]:查询失败:", ex);
//			return result;
//		}
//		return result;
//	}
//
//	/**
//	 * 数据权限设置闸口删除数据
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public Boolean deleteGateOfDataPrivilege(Map<String, Object> params) {
//		try {
//			gateDao.deleteGateOfDataPrivilege(params);
//			gateDao.deleteGateOfUserPrivilege(params);
//		} catch (Exception ex) {
//			// 记录日志
//			log.error("[GateModel][deleteGateOfDataPrivilege]:删除失败:", ex);
//			return false;
//		}
//		// 返回执行成功
//		return true;
//	}
//
//	/**
//	 * 数据权限设置闸口保存数据
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public Boolean saveGateOfDataPrivilege(String operatorType, GateOfDataPrivilegeItem gateOfDataPrivilegeItem) {
//		Boolean result = true;
//		try {
//			if (ADD_DATA_PRIVILEGE.equals(operatorType)) {
//				result = gateDao.insertGateOfDataPrivilege(gateOfDataPrivilegeItem);
//			} else if (MODIFY_DATA_PRIVILEGE.equals(operatorType)) {
//				result = gateDao.updateGateOfDataPrivilege(gateOfDataPrivilegeItem);
//			} else {
//				result = false;
//			}
//
//		} catch (Exception ex) {
//			// 记录日志
//			log.error("[GateModel][insertGateOfDataPrivilege]:数据保存失败:", ex);
//			result = false;
//		}
//		// 返回执行成功
//		return result;
//	}
//
//	/**
//	 * 数据权限设置闸口保存数据
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public Boolean saveGateOfUserPrivilege(List<GateOfDataPrivilegeItem> gateOfDataPrivilegeItemList) {
//		Boolean result = true;
//		try {
//			for (GateOfDataPrivilegeItem gateOfDataPrivilegeItem : gateOfDataPrivilegeItemList) {
//				Map<String, Object> params = new HashMap<String, Object>();
//				params.put("user_id", gateOfDataPrivilegeItem.getUser_id());
//				List<GateOfDataPrivilegeItem> listTemp = gateDao.selectGateOfUserPrivilege(params);
//				if (listTemp.size() > 0) {
//					gateDao.updateGateOfUserPrivilege(gateOfDataPrivilegeItem);
//				} else {
//					gateDao.insertGateOfUserPrivilege(gateOfDataPrivilegeItem);
//				}
//			}
//
//		} catch (Exception ex) {
//			// 记录日志
//			log.error("[GateModel][insertGateOfDataPrivilege]:数据保存失败:", ex);
//			result = false;
//		}
//		// 返回执行成功
//		return result;
//	}
//
//	/**
//	 * 额度闸口历史检索
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public List<GateOfHistoryLimitItem> selectGateOfHistoryLimit(Map<String, Object> params) {
//		List<GateOfHistoryLimitItem> result = new ArrayList<GateOfHistoryLimitItem>();
//		try {
//			result = gateDao.selectGateOfHistoryLimit(params);
//		} catch (Exception ex) {
//			log.error("[GateModel][selectGateOfHistoryLimit]:查询失败:", ex);
//			return result;
//		}
//		return result;
//	}
//
	/**
	 * 额度闸口检索
	 * 
	 * @param params
	 * @return
	 */
	public List<GateOfLimitItem> selectGateOfLimit(Map<String, Object> params) {
		List<GateOfLimitItem> result = new ArrayList<GateOfLimitItem>();
		try {
			result = gateService.selectGateOfLimit(params);
		} catch (Exception ex) {
			log.error("[GateModel][selectGateOfLimit]:查询失败:", ex);
			return result;
		}
		return result;
	}

//	/**
//	 * 获得额度闸口设置表所有品类
//	 * 
//	 * @return
//	 */
//	public String[] getAllCategorys() {
//		String[] result = new String[] {};
//		try {
//			result = gateDao.getAllCategorys();
//		} catch (Exception ex) {
//			log.error("[GateModel][getAllCategorys]:查询失败:", ex);
//			return result;
//		}
//		return result;
//	}
//
//	/**
//	 * 额度闸口设置录入数据
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public Boolean insertGateOfLimit(Map<String, Object> params) {
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(def);
//		try {
//			// 录入数据
//			gateDao.insertGateOfLimit(params);
//			// 提交事务
//			transactionManager.commit(status);
//		} catch (Exception ex) {
//			// 回滚事务
//			transactionManager.rollback(status);
//			// 记录日志
//			log.error("[GateModel][insertGateOfLimit]:录入额度闸口数据失败:", ex);
//			return false;
//		}
//		// 返回执行成功
//		return true;
//	}
//
//	public Map<String, Integer> insertGateOfLimit(List<Map<String, Object>> params) {
//		int success = 0;
//		int failure = 0;
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(def);
//		try {
//			// 录入数据
//			for (Map<String, Object> param : params) {
//				try {
//					gateDao.insertGateOfLimit(param);
//					success++;
//				} catch (Exception e) {
//					if (e instanceof SQLException) {
//						log.error("[GateModel][insertGateOfLimit]:数据插入失败");
//						log.error(JsonUtil.toJson(param));
//						failure++;
//					} else {
//						throw e;
//					}
//				}
//			}
//			// 提交事务
//			transactionManager.commit(status);
//		} catch (Exception ex) {
//			// 回滚事务
//			transactionManager.rollback(status);
//			// 记录日志
//			log.error("[GateModel][insertGateOfLimit]:录入额度闸口数据失败:", ex);
//
//			success = 0;
//			failure = params.size();
//		}
//		// 返回执行成功
//		Map map = new HashMap<String, Integer>();
//		map.put("success", success);
//		map.put("failure", failure);
//		return map;
//	}
//
//	/**
//	 * 根据渠道，品类，月份验证是否存在额度闸口录入数据记录
//	 * 
//	 * @param category_id
//	 * @param ed_channel_id
//	 * @param month
//	 * @return
//	 */
//	public Boolean isExistGateOfLimit(String category_id, String ed_channel_id, Integer month) {
//		int i = gateDao.isExistGateOfLimit(category_id, ed_channel_id, month);
//		return i > 0;
//	}
//
//	/**
//	 * 修改额度闸口数据
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public Boolean updateGateOfLimit(Map<String, Object> params) {
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(def);
//		try {
//			// 情报更新
//			gateDao.updateGateOfLimit(params);
//			// 提交事务
//			transactionManager.commit(status);
//		} catch (Exception ex) {
//			// 回滚事务
//			transactionManager.rollback(status);
//			// 记录日志
//			log.error("修改额度闸口数据失败:", ex);
//			return false;
//		}
//		// 返回执行成功
//		return true;
//	}
//
//	/**
//	 * 更新总额度
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public Boolean updateLimitSum(Map<String, Object> params) {
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(def);
//		try {
//			// 情报更新
//			gateDao.updateLimitSum(params);
//			// 提交事务
//			transactionManager.commit(status);
//		} catch (Exception ex) {
//			// 回滚事务
//			transactionManager.rollback(status);
//			// 记录日志
//			log.error("更新总额度失败:", ex);
//			return false;
//		}
//		// 返回执行成功
//		return true;
//	}

	/**
	 * 取得指定月份总额度
	 * 
	 * @param params
	 * @return
	 */
	public List<GateLimitSumItem> findLimitSum(Map<String, Object> params) {
		List<GateLimitSumItem> result = new ArrayList<GateLimitSumItem>();
		try {
			result = gateService.selectLimitSum(params);
		} catch (Exception ex) {
			log.error("[GateModel][selectLimitSum]:查询失败:", ex);
			return result;
		}
		return result;
	}

	/**
	 * 滚动总额度
	 * 
	 * @param params
	 * @return
	 */
	public Boolean updateLimitSumByMonth(GateLimitSumItem gateLimitSumItem) {
		try {
			// 情报更新
			gateService.updateLimitSumByMonth(gateLimitSumItem);
		} catch (Exception ex) {
			// 记录日志
			log.error("滚动总额度失败:", ex);
			return false;
		}
		// 返回执行成功
		return true;
	}

//	public Map<String, Integer> updateGateOfLimit(List<Map<String, Object>> params) {
//		int success = 0;
//		int failure = 0;
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(def);
//		try {
//			// 情报更新
//			for (Map<String, Object> param : params) {
//				try {
//					gateDao.updateGateOfLimit(param);
//					success++;
//				} catch (Exception e) {
//					if (e instanceof SQLException) {
//						log.error("[GateModel][insertGateOfLimit]:数据更新失败");
//						log.error(JsonUtil.toJson(param));
//						failure++;
//					} else {
//						throw e;
//					}
//				}
//			}
//			// 提交事务
//			transactionManager.commit(status);
//		} catch (Exception ex) {
//			// 回滚事务
//			transactionManager.rollback(status);
//			// 记录日志
//			log.error("修改额度闸口数据失败:", ex);
//
//			success = 0;
//			failure = params.size();
//		}
//		// 返回执行成功
//		Map<String, Integer> map = new HashMap<String, Integer>();
//		map.put("success", success);
//		map.put("failure", failure);
//		return map;
//	}
//
	/**
	 * 根据ID修改额度闸口数据
	 * 
	 * @param gateOfLimitList
	 * @return
	 */
	public Boolean updateGateOfLimitById(List<GateOfLimitItem> gateOfLimitList) {
		try {
			// 情报更新
			for (GateOfLimitItem gateOfLimitItem : gateOfLimitList) {
				gateService.updateGateOfLimitById(gateOfLimitItem);
			}
		} catch (Exception ex) {
			// 记录日志
			log.error("根据ID修改额度闸口数据失败:", ex);
			return false;
		}
		// 返回执行成功
		return true;
	}

//	public void clearStockExceedCache(Map params) {
//		gateDao.clearStockExceedCache(params);
//	}
//
//	public void saveStockExceedCache(List<HaierStockExceedCacheVO> list) {
//		gateDao.saveStockExceedCache(list);
//	}
//
//	/**
//	 * 删除额度闸口数据
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public Boolean deleteGateOfLimit() {
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(def);
//		try {
//			// 情报更新
//			gateDao.deleteGateOfLimit();
//			// 提交事务
//			transactionManager.commit(status);
//		} catch (Exception ex) {
//			// 回滚事务
//			transactionManager.rollback(status);
//			// 记录日志
//			log.error("删除额度闸口数据失败:", ex);
//			return false;
//		}
//		// 返回执行成功
//		return true;
//	}
//
//	/**
//	 * 更新额度闸口数据
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public Boolean updateDeleteFlag() {
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(def);
//		try {
//			// 情报更新
//			gateDao.updateDeleteFlag();
//			// 提交事务
//			transactionManager.commit(status);
//		} catch (Exception ex) {
//			// 回滚事务
//			transactionManager.rollback(status);
//			// 记录日志
//			log.error("更新额度闸口数据失败:", ex);
//			return false;
//		}
//		// 返回执行成功
//		return true;
//	}
//
//	/**
//	 * 额度闸口全检索
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public List<GateOfLimitItem> selectAllGateOfLimit() {
//		List<GateOfLimitItem> result = new ArrayList<GateOfLimitItem>();
//		try {
//			result = gateDao.selectAllGateOfLimit();
//		} catch (Exception ex) {
//			log.error("[GateModel][selectAllGateOfLimit]:查询失败:", ex);
//			return result;
//		}
//		return result;
//	}
//
//	/**
//	 * 拆借数据修改
//	 * 
//	 * @param gateOfLimitList
//	 * @return
//	 */
//	public Boolean updateLoanNum(List<GateOfLimitItem> gateOfLimitList) {
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(def);
//		try {
//			// 情报更新
//			for (GateOfLimitItem gateOfLimitItem : gateOfLimitList) {
//				gateDao.updateLoanNum(gateOfLimitItem);
//			}
//			// 提交事务
//			transactionManager.commit(status);
//		} catch (Exception ex) {
//			// 回滚事务
//			transactionManager.rollback(status);
//			// 记录日志
//			log.error("拆借数据修改失败:", ex);
//			return false;
//		}
//		// 返回执行成功
//		return true;
//	}
}
