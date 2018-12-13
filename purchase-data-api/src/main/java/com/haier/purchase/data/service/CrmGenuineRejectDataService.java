package com.haier.purchase.data.service;

import com.haier.purchase.data.model.CrmGenuineRejectItem;
import com.haier.purchase.data.model.CrmOrderRejectItem;
import com.haier.purchase.data.model.WAAddressInfo;

import java.util.List;
import java.util.Map;

public interface CrmGenuineRejectDataService {
    List<CrmGenuineRejectItem> getCrmGenuineRejectList(Map<String, Object> params);

    int getCrmGenuineRejectListCNT(Map<String, Object> params);

    int checkSoidSame(String so_id);

    Boolean insertCrmReturnInfoList(Map<String, Object> params);

    Boolean commitCrmGenuineRejectList(Map<String, Object> params);

    List<WAAddressInfo> findWAAddressInfo(Map<String, Object> params);

    List<Map<String,Object>> findCrmReturnOrderInfo(Map<String, Object> params);

    Boolean updateCrmReturnInfo(Map<String, Object> paramMap);

    Boolean deleteCrmGenuineRejectList(Map<String, Object> params);

    void updateFlowFlagCancel(List<String> list);

    Boolean updateAfterCancelFailed(Map<String, String> failed);

    List<CrmOrderRejectItem> findCrmOrderRejectList(Map<String, Object> orderRejectMap);

    Boolean updateFlowFlagCancelInWa(Map<String, Object> orderRejectMap);

    List<CrmGenuineRejectItem> getRejectWdOrderId(String wpOrderId);

    void updateRemark(CrmGenuineRejectItem crmGenuineRejectItem);

    void updateStatusToInRRS(String so_id, String s);
}
