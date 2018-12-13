package com.haier.purchase.data.services;

import com.haier.purchase.data.dao.purchase.CrmGenuineRejectDao;
import com.haier.purchase.data.model.CrmGenuineRejectItem;
import com.haier.purchase.data.model.CrmOrderRejectItem;
import com.haier.purchase.data.model.WAAddressInfo;
import com.haier.purchase.data.service.CrmGenuineRejectDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrmGenuineRejectDataServiceImpl implements CrmGenuineRejectDataService{

    private static Logger log = LoggerFactory
            .getLogger(CrmGenuineRejectDataServiceImpl.class);

    @Autowired
    private CrmGenuineRejectDao crmGenuineRejectDao;
    @Override
    public List<CrmGenuineRejectItem> getCrmGenuineRejectList(Map<String, Object> params) {
        return crmGenuineRejectDao.getCrmGenuineRejectList(params);
    }

    @Override
    public int getCrmGenuineRejectListCNT(Map<String, Object> params) {
        return crmGenuineRejectDao.getCrmGenuineRejectListCNT(params);
    }

    @Override
    public int checkSoidSame(String so_id) {
        return crmGenuineRejectDao.checkSoidSame(so_id);

    }

    /**
     * 创建退货单
     * @param params
     * @return
     */
    @Transactional
    public Boolean insertCrmReturnInfoList(Map<String, Object> params) {

        try {
            //创建退货单数据
            crmGenuineRejectDao.insert(params);
        } catch (Exception ex) {
            //记录日志
            log.error("[CrmGenuineRejectModel][insertCrmReturnInfoList]:CRM退货单创建失败:", ex);
            return false;
        }
        //返回执行成功
        return true;
    }


    /**
     * 更新订单状态为已提交
     * @param params
     * @return
     */
    @Transactional
    public Boolean commitCrmGenuineRejectList(Map<String, Object> params) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 情报更新
            crmGenuineRejectDao.updateCrmGenuineRejectStatus(params);

        } catch (Exception ex) {
            //记录日志
            log.error("[CrmGenuineRejectModel][commitCrmGenuineRejectList]:订单提交失败:", ex);
            return false;
        }
        //返回执行成功
        return true;
    }

    public List<WAAddressInfo> findWAAddressInfo(Map<String, Object> params) {
        return crmGenuineRejectDao.findWAAddressInfo(params);
    }

    public List<Map<String, Object>> findCrmReturnOrderInfo(Map<String, Object> params) {
        return crmGenuineRejectDao.findCrmReturnOrderInfo(params);
    }
    @Transactional
    public Boolean updateCrmReturnInfo(Map<String, Object> params) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        try {
            // 订单删除
            crmGenuineRejectDao.updateCrmReturnInfo(params);

        } catch (Exception ex) {

            //记录日志
            log.error("[CrmGenuineRejectModel][updateCrmReturnInfo]:订单更新失败:", ex);
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
    @Transactional
    public Boolean deleteCrmGenuineRejectList(Map<String, Object> params) {
        try {
            // 订单删除
            crmGenuineRejectDao.deleteCrmGenuineRejectStatus(params);

        } catch (Exception ex) {
            //记录日志
            log.error("[CrmGenuineRejectModel][deleteCrmGenuineRejectStatus]:订单删除失败:", ex);
            return false;
        }
        //返回执行成功
        return true;
    }

    /**
     * 取消退货单
     * @param cancelList
     * @return
     */
    @Transactional
    public void updateFlowFlagCancel(List<String> cancelList) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        try {
            //取消退货单
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("cancelList", cancelList);
            crmGenuineRejectDao.updateFlowFlagCancel(params);

        } catch (Exception ex) {
            //记录日志
            log.error("[CrmGenuineRejectModel][updateFlowFlagCancel]:CRM退货单取消失败:", ex);

        }

    }

    public List<CrmOrderRejectItem> findCrmOrderRejectList(Map<String, Object> orderRejectMap) {
        return crmGenuineRejectDao.findCrmOrderRejectList(orderRejectMap);
    }
    @Transactional
    public Boolean updateFlowFlagCancelInWa(Map<String, Object> params) {
        try {
            // 订单删除
            crmGenuineRejectDao.updateFlowFlagCancelInWa(params);

        } catch (Exception ex) {

            //记录日志
            log.error("[CrmGenuineRejectModel][updateFlowFlagCancelInWa]:订单更新失败:", ex);
            return false;
        }
        //返回执行成功
        return true;
    }

    @Override
    public List<CrmGenuineRejectItem> getRejectWdOrderId(String wpOrderId) {
        return  crmGenuineRejectDao.getRejectWdOrderId(wpOrderId);
    }

    @Override
    public void updateRemark(CrmGenuineRejectItem crmGenuineRejectItem) {
        crmGenuineRejectDao.updateRemark(crmGenuineRejectItem);
    }

    public void updateStatusToInRRS(String soID, String inTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("so_id", soID);
        params.put("warehouse_in_time", inTime);
        crmGenuineRejectDao.updateStatusToInRRS(params);
    }

    /**
     * 取消退货失败，插入失败原因
     * @param
     * @return
     */
    public Boolean updateAfterCancelFailed(Map<String, String> result) {

        try {
            for (String orderID : result.keySet()) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("wp_order_id", orderID);

                String msg = result.get(orderID).length() > 2000 ? result.get(orderID).substring(0,
                        1999) : result.get(orderID);
                params.put("error_msg", msg);
                // 情报更新
                crmGenuineRejectDao.updateAfterCommit(params);
            }

        } catch (Exception ex) {

            //记录日志
            log.error("[CrmGenuineRejectModel][updateAfterCommit]:更新提交状态提交失败:", ex);
            return false;
        }
        //返回执行成功
        return true;
    }
}
