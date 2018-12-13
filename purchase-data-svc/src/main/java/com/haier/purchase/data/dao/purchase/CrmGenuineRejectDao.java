package com.haier.purchase.data.dao.purchase;

import com.haier.purchase.data.model.CrmGenuineRejectItem;
import com.haier.purchase.data.model.CrmOrderRejectItem;
import com.haier.purchase.data.model.WAAddressInfo;

import java.util.List;
import java.util.Map;

public interface CrmGenuineRejectDao {

    List<CrmGenuineRejectItem> getCrmGenuineRejectList(Map<String, Object> params);

    int getCrmGenuineRejectListCNT(Map<String, Object> params);

    int checkSoidSame(String so_id);

    void insert(Map<String, Object> params);

    void updateCrmGenuineRejectStatus(Map<String, Object> params);

    List<WAAddressInfo> findWAAddressInfo(Map<String, Object> params);

    List<Map<String,Object>> findCrmReturnOrderInfo(Map<String, Object> params);

    void updateCrmReturnInfo(Map<String, Object> params);

    void deleteCrmGenuineRejectStatus(Map<String, Object> params);

    void updateFlowFlagCancel(Map<String, Object> params);

    void updateAfterCommit(Map<String, Object> params);

    List<CrmOrderRejectItem> findCrmOrderRejectList(Map<String, Object> orderRejectMap);

    void updateFlowFlagCancelInWa(Map<String, Object> params);

    List<CrmGenuineRejectItem> getRejectWdOrderId(String wpOrderId);

    void updateRemark(CrmGenuineRejectItem crmGenuineRejectItem);

    void updateStatusToInRRS(Map<String, Object> params);
}
