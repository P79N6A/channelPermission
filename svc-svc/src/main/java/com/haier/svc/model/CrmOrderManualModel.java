package com.haier.svc.model;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.CrmOrderManualDetailItem;
import com.haier.purchase.data.model.CrmOrderManualItem;
import com.haier.purchase.data.service.PurchaseCrmOrderManualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CrmOrderManualModel {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(CrmOrderManualModel.class);

    //CRM手工采购订单录入
    private final String                   ADD_CRM_ORDER_MANUAL    = "add";
    //CRM手工采购订单修改
    private final String                   MODIFY_CRM_ORDER_MANUAL = "modify";

	@Autowired
	PurchaseCrmOrderManualService purchaseCrmOrderManualService;

	public void updateTimeFromCRM(Map<String, Object> params) {
		purchaseCrmOrderManualService.updateTimeFromCRM(params);
	}

	/**
	 * 获取CRM手工采购订单信息
	 * 
	 * @param
	 *            <String, Object> params
	 * @return
	 */
	public List<CrmOrderManualDetailItem> getCrmOrderManualList(
			Map<String, Object> params) {
		return purchaseCrmOrderManualService.findCrmOrderManuals(params);

	}

	public void updateStatusFromCRM(Map map) {
		purchaseCrmOrderManualService.updateStatusFromCRM(map);
	}

	/**
	 * 提交订单
	 * @param params
	 * @return
	 */
	public Boolean commitOrderManual(Map<String, Object> params) {
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			// 情报更新
            purchaseCrmOrderManualService.commitCRMOrderManual(params);
			//提交事务
//			transactionManager.commit(status);
		} catch (Exception ex) {
			//回滚事务
//			transactionManager.rollback(status);
			//记录日志
			log.error("[CrmOrderManualModel][commitOrderManual]:订单提交失败:", ex);
			return false;
		}
		//返回执行成功
		return true;
	}

    /**
     * 删除订单
     * @param params
     * @return
     */
    public Boolean deleteOrderManual(Map<String, Object> params) {
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 订单删除
            purchaseCrmOrderManualService.deleteCRMOrderManual(params);
            //提交事务
//            transactionManager.commit(status);
        } catch (Exception ex) {
            //回滚事务
//            transactionManager.rollback(status);
            //记录日志
            log.error("[CrmOrderManualModel][deleteOrderManual]:订单删除失败:", ex);
            return false;
        }
        //返回执行成功
        return true;
    }

    /**
     * CRM手工采购订单编辑
     * @param
     */
    public Boolean editCRMOrderManual(Map<String, Object> params) {

//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);

        //在map中获取两个entity
        CrmOrderManualItem crmOrderManualItem = (CrmOrderManualItem) params
                .get("CrmOrderManualItem");
        CrmOrderManualDetailItem crmOrderManualDetailItem = (CrmOrderManualDetailItem) params
                .get("CrmOrderManualDetailItem");
        //获取操作类型
        String operatorType = (String) params.get("operatorType");
        try {
            if (ADD_CRM_ORDER_MANUAL.equals(operatorType)) {
                //CRM手工采购订单录入数据
                purchaseCrmOrderManualService.insertCRMOrderManual(crmOrderManualItem);
                purchaseCrmOrderManualService.insertCRMOrderManualDetail(crmOrderManualDetailItem);
                //提交事务
                //  transactionManager.commit(status);
            } else if (MODIFY_CRM_ORDER_MANUAL.equals(operatorType)) {
                //CRM手工采购订单更新数据
                int i = purchaseCrmOrderManualService.updateCRMOrderManual(crmOrderManualItem);
                log.debug("********************************************");
                log.debug("更新条数：" + i);
                purchaseCrmOrderManualService.updateCRMOrderManualDetail(crmOrderManualDetailItem);
                //提交事务
                // transactionManager.commit(status);
            }
            //提交事物移到判断条件外边  zzb  2017-07-16
//            transactionManager.commit(status);
        } catch (Exception ex) {
            //回滚事务
//            transactionManager.rollback(status);
            //记录日志
            log.error("[CrmOrderManualModel][editCRMOrderManual]:CRM手工采购订单编辑失败:", ex);
            return false;
        }
        //返回执行成功
        return true;
    }

    /**
     * 获取CRM手工采购订单条数
     * @param params
     * @return
     */
    public Integer getCrmOrderManualListCNT(Map<String, Object> params) {
        return purchaseCrmOrderManualService.findCrmOrderManualsCNT(params);
    }

    /**
     * 获得条数
     * @return
     */
    public Integer getRowCnts() {
        return purchaseCrmOrderManualService.getRowCnts();
    }

    /**
     * CRM提交后SO单号更新
     * @param crmOrderManualDetailItem
     * @return
     */
    public Boolean updateCRMOrderManualDetailForCRM(CrmOrderManualDetailItem crmOrderManualDetailItem) {
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 情报更新
            purchaseCrmOrderManualService.updateCRMOrderManualDetailForCRM(crmOrderManualDetailItem);
            //提交事务
//            transactionManager.commit(status);
        } catch (Exception ex) {
            //回滚事务
//            transactionManager.rollback(status);
            //记录日志
            log.error("[CrmOrderManualModel][updateCRMOrderManualDetailForCRM]:CRM提交后SO单号更新失败:", ex);
            return false;
        }
        //返回执行成功
        return true;
    }

    /**
     * CRM提交状态更新
     * @param crmOrderManualItem
     * @return
     */
    public Boolean commitOrderManualForCRM(CrmOrderManualItem crmOrderManualItem) {
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 情报更新
            purchaseCrmOrderManualService.commitCRMOrderManualForCRM(crmOrderManualItem);
            //提交事务
//            transactionManager.commit(status);
        } catch (Exception ex) {
            //回滚事务
//            transactionManager.rollback(status);
            //记录日志
            log.error("[CrmOrderManualModel][commitOrderManualForCRM]:CRM提交状态更新失败:", ex);
            return false;
        }
        //返回执行成功
        return true;
    }
}