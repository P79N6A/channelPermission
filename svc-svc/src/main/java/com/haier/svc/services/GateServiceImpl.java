package com.haier.svc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.*;
import com.haier.purchase.data.service.PurchaseLogAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.service.PurchaseGateService;
import com.haier.svc.service.GateService;

@Service
public class GateServiceImpl implements GateService {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(GateServiceImpl.class);
	@Autowired
	private PurchaseGateService purchaseGateService;
	@Autowired
	private PurchaseLogAuditService purchaseLogAuditService;
	/**
	 * 额度闸口检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public ServiceResult<List<GateOfLimitItem>> findGateOfLimit(Map<String, Object> params) {
		ServiceResult<List<GateOfLimitItem>> result = new ServiceResult<List<GateOfLimitItem>>();
		try {
			result.setResult(purchaseGateService.selectGateOfLimit(params));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][findGateOfLimit]:额度闸口检索失败:", e);
		}
		return result;
	}

	/**
	 * 额度闸口历史检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public ServiceResult<List<GateOfHistoryLimitItem>> findGateOfHistoryLimit(Map<String, Object> params) {
		ServiceResult<List<GateOfHistoryLimitItem>> result = new ServiceResult<List<GateOfHistoryLimitItem>>();
		try {
			result.setResult(purchaseGateService.selectGateOfHistoryLimit(params));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][findGateOfLimit]:额度闸口检索失败:", e);
		}
		return result;
	}

	/**
	 * 时间闸口恢复默认
	 * @param params
	 * @return
	 */
	@Override
	public ServiceResult<Boolean> modifyDefaultTime(Map<String, Object> params) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseGateService.updateDefaultTime(params));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][modifyDefaultTime]:时间闸口恢复默认失败:", e);
		}
		return result;
	}

	/**
	 * 时间闸口检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public ServiceResult<List<GateItem>> findGateItem(Map<String, Object> params) {
		ServiceResult<List<GateItem>> result = new ServiceResult<List<GateItem>>();
		try {

			List<GateItem> list = new ArrayList<GateItem>();
			list = purchaseGateService.selectGateItem(params);
			if (list != null && list.size() > 0) {
				for (GateItem gateItem : list) {
					if (gateItem.getSetting_week() == null || "".equals(gateItem.getSetting_week())) {
						gateItem.setSetting_week(gateItem.getDefault_setting_week());
					}
					if (gateItem.getSetting_hour() == null || "".equals(gateItem.getSetting_hour())) {
						gateItem.setSetting_hour(gateItem.getDefault_setting_hour());
					}
					if (gateItem.getSetting_minute() == null || "".equals(gateItem.getSetting_minute())) {
						gateItem.setSetting_minute(gateItem.getDefault_setting_minute());
					}
					if (gateItem.getSetting_second() == null || "".equals(gateItem.getSetting_second())) {
						gateItem.setSetting_second(gateItem.getDefault_setting_second());
					}
				}
			}

			result.setResult(list);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][findGateItem]:时间闸口检索失败:", e);
		}
		return result;
	}

	/**
	 * 库存超期闸口检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public ServiceResult<List<GateOfStockExceedItem>> findGateOfStockExceed(Map<String, Object> params) {
		ServiceResult<List<GateOfStockExceedItem>> result = new ServiceResult<List<GateOfStockExceedItem>>();
		try {
			List<GateOfStockExceedItem> list = purchaseGateService.selectGateOfStockExceed(params);
			result.setResult(list);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][findGateOfStockExceed]:库存超期闸口检索失败:", e);
		}
		return result;
	}

	/**
	 * 库存超期闸口删除
	 * @param
	 */
	@Override
	public ServiceResult<Boolean> removeGateOfStockExceed(Map<String, Object> params) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseGateService.deleteGateOfStockExceed(params));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][removeGateOfStockExceed]:库存超期闸口删除失败:", e);
		}
		return result;
	}

	/**
	 * 库存超期闸口保存
	 * @param
	 */
	@Override
	public ServiceResult<Boolean> saveGateOfStockExceed(List<GateOfStockExceedItem> gateOfStockExceedList) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseGateService.saveGateOfStockExceed(gateOfStockExceedList));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][saveGateOfStockExceed]:库存超期闸口保存失败:", e);
		}
		return result;
	}

	@Override
	public void unionLog(String model, Object oldData, Object newData, String type, String operUserName, String orderId) {
		purchaseLogAuditService.unionLog(model,oldData,newData,type,operUserName,orderId);
	}

	/**
	 * 库存超期闸口缓存表检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public ServiceResult<List<GateOfStockExceedCatchItem>> findGateOfStockExceedCatch(Map<String, Object> params) {
		ServiceResult<List<GateOfStockExceedCatchItem>> result = new ServiceResult<List<GateOfStockExceedCatchItem>>();
		try {
			List<GateOfStockExceedCatchItem> list = purchaseGateService.selectGateOfStockExceedCatch(params);
			result.setResult(list);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][findGateOfStockExceedCatch]:库存超期闸口缓存表检索失败:", e);
		}
		return result;
	}

	/**
	 * 额度闸口全检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public ServiceResult<List<GateOfLimitItem>> findAllGateOfLimit() {
		ServiceResult<List<GateOfLimitItem>> result = new ServiceResult<List<GateOfLimitItem>>();
		try {
			result.setResult(purchaseGateService.selectAllGateOfLimit());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][findAllGateOfLimit]:额度闸口全检索失败:", e);
		}
		return result;
	}

	@Override
	public List<GateLimitSumItem> selectLimitSum(Map<String, Object> params) {
		return purchaseGateService.selectLimitSum(params);
	}

	@Override
	public void updateLimitSumByMonth(GateLimitSumItem gateLimitSumItem) {
		purchaseGateService.updateLimitSumByMonth(gateLimitSumItem);
	}

	@Override
	public List<GateOfLimitItem> selectGateOfLimit(Map<String, Object> params) {
		return purchaseGateService.selectGateOfLimit(params);
	}

	@Override
	public void updateGateOfLimitById(GateOfLimitItem gateOfLimitItem) {
		purchaseGateService.updateGateOfLimitById(gateOfLimitItem);
	}

	@Override
	public ServiceResult<Map<String, Integer>> insertGateOfLimit(List<Map<String, Object>> insertObj) {
		return purchaseGateService.insertGateOfLimit(insertObj);
	}

	@Override
	public ServiceResult<Map<String, Integer>> updateGateOfLimit(List<Map<String, Object>> updateObj) {
		return purchaseGateService.updateGateOfLimit(updateObj);
	}

	@Override
	public void deleteGateOfLimit() {
		purchaseGateService.deleteGateOfLimit();
	}

	@Override
	public void updateDeleteFlag() {
		purchaseGateService.updateDeleteFlag();
	}

	@Override
	public ServiceResult<Boolean> isExistGateOfLimit(String category, String channelCode, int i) {
		return purchaseGateService.isExistGateOfLimit(category, channelCode, i);
	}

	/**
	 * 根据ID修改额度闸口数据
	 * 
	 * @param gateOfLimitList
	 * @return
	 */
	@Override
	public ServiceResult<Boolean> modifyGateOfLimitById(List<GateOfLimitItem> gateOfLimitList) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			// 修改数据
			result.setResult(purchaseGateService.updateGateOfLimitById(gateOfLimitList));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("根据ID修改额度闸口数据：", e);
		}
		return result;
	}

	/**
	 * 更新总额度
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public ServiceResult<Boolean> updateLimitSum(Map<String, Object> params) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			// 修改数据
			result.setResult(purchaseGateService.updateLimitSum(params));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("更新总额度：", e);
		}
		return result;
	}

	/**
	 * 拆借数据修改
	 * 
	 * @param gateOfLimitList
	 * @return
	 */
	@Override
	public ServiceResult<Boolean> modifyLoanNum(List<GateOfLimitItem> gateOfLimitList) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			// 修改数据
			result.setResult(purchaseGateService.updateLoanNum(gateOfLimitList));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("拆借数据修改：", e);
		}
		return result;
	}

	/**
	 * 数据权限设置闸口检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public ServiceResult<List<GateOfDataPrivilegeItem>> findGateOfDataPrivilege(Map<String, Object> params) {
		ServiceResult<List<GateOfDataPrivilegeItem>> result = new ServiceResult<List<GateOfDataPrivilegeItem>>();
		try {

			result.setResult(purchaseGateService.selectGateOfDataPrivilege(params));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][findGateOfDataPrivilege]:数据权限设置闸口检索失败:", e);
		}
		return result;
	}

	/**
	 * 数据权限设置闸口删除
	 * 
	 * @param
	 */
	@Override
	public ServiceResult<Boolean> removeGateOfDataPrivilege(Map<String, Object> params) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseGateService.deleteGateOfDataPrivilege(params));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][removeGateOfDataPrivilege]:数据权限设置闸口删除失败:", e);
		}
		return result;
	}

	/**
	 * 用户权限数据保存
	 * 
	 * @param
	 */
	@Override
	public ServiceResult<Boolean> saveGateOfUserPrivilege(List<GateOfDataPrivilegeItem> gateOfDataPrivilegeItemList) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseGateService.saveGateOfUserPrivilege(gateOfDataPrivilegeItemList));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][saveGateOfUserPrivilege]:用户权限数据保存失败:", e);
		}
		return result;
	}

	/**
	 * 数据权限设置闸口保存
	 * 
	 * @param
	 */
	@Override
	public ServiceResult<Boolean> saveGateOfDataPrivilege(String operatorType,
			GateOfDataPrivilegeItem gateOfDataPrivilegeItem) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseGateService.saveGateOfDataPrivilege(operatorType, gateOfDataPrivilegeItem));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][saveGateOfDataPrivilege]:数据权限设置闸口数据保存失败:", e);
		}
		return result;
	}

	/**
	 * 用户数据权限检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public ServiceResult<List<GateOfDataPrivilegeItem>> findGateOfUserPrivilege(Map<String, Object> params) {
		ServiceResult<List<GateOfDataPrivilegeItem>> result = new ServiceResult<List<GateOfDataPrivilegeItem>>();
		try {
			result.setResult(purchaseGateService.selectGateOfUserPrivilege(params));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][findGateOfUserPrivilege]:用户数据权限检索:", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> modifyGateItem(GateItemForTransfer gateItemData) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			result.setResult(purchaseGateService.updateGateItem(gateItemData));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("[GateServiceImpl][modifyGateItem]:时间闸口更新失败:", e);
		}
		return result;
	}
}
