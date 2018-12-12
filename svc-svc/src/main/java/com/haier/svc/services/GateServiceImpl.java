package com.haier.svc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.GateItem;
import com.haier.purchase.data.model.GateOfLimitItem;
import com.haier.purchase.data.model.GateOfStockExceedCatchItem;
import com.haier.purchase.data.model.GateOfStockExceedItem;
import com.haier.purchase.data.service.PurchaseGateService;
import com.haier.svc.service.GateService;

@Service
public class GateServiceImpl implements GateService {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(GateServiceImpl.class);
	@Autowired
	private PurchaseGateService purchaseGateService;

	/**
	 * 额度闸口检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public ServiceResult<List<GateOfLimitItem>> findGateOfLimit(
			Map<String, Object> params) {
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
					if (gateItem.getSetting_week() == null
							|| "".equals(gateItem.getSetting_week())) {
						gateItem.setSetting_week(gateItem
								.getDefault_setting_week());
					}
					if (gateItem.getSetting_hour() == null
							|| "".equals(gateItem.getSetting_hour())) {
						gateItem.setSetting_hour(gateItem
								.getDefault_setting_hour());
					}
					if (gateItem.getSetting_minute() == null
							|| "".equals(gateItem.getSetting_minute())) {
						gateItem.setSetting_minute(gateItem
								.getDefault_setting_minute());
					}
					if (gateItem.getSetting_second() == null
							|| "".equals(gateItem.getSetting_second())) {
						gateItem.setSetting_second(gateItem
								.getDefault_setting_second());
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
     * 库存超期闸口缓存表检索
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
}
