package com.haier.svc.service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.CrmGenuineRejectItem;
import com.haier.purchase.data.model.OrderOperationLog;

import java.util.List;
import java.util.Map;

public interface CrmGenuineRejectService {
    ServiceResult<List<CrmGenuineRejectItem>> getCrmGenuineRejectList(Map<String, Object> params);

    /**
     * 查找重复的SO-ID单号
     */
    ServiceResult<Boolean> checkSoidSame(String so_id);

    ServiceResult<Boolean> insertCrmReturnInfoList(Map<String, Object> params);

    void insertOrderOperationLog(List<OrderOperationLog> loglist);

    ServiceResult<Boolean> commitCrmGenuineRejectList(Map<String, Object> params);

    void deleteCrmGenuineRejectList(Map<String, Object> params);

    String updateFlowFlagCancel(List<String> cancelList);

    ServiceResult<Boolean> stopCrmGenuineRejectList(List<String> stopList);

    ServiceResult<Boolean> cancelInWaCrmGenuineRejectList(List<String> stopList);

    /**
     *
     * @title getOrderOperationLogById
     * @description 通过表字段order_id和sub_order_id查询表order_operation_log_t中数据
     */
    public ServiceResult<List<OrderOperationLog>> getOrderOperationLogInfo(
            Map<String, Object> params);

    List<CrmGenuineRejectItem> getRejectWdOrderId(String wpOrderId);

    void updateRemark(CrmGenuineRejectItem crmGenuineRejectItem);
}
