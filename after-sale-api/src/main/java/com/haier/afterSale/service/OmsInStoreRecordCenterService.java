package com.haier.afterSale.service;

import com.haier.common.ServiceResult;
import com.haier.shop.model.OmsInStoreRecords;

import java.util.List;
import java.util.Map;

public interface OmsInStoreRecordCenterService {
   /* public List<OmsInStoreRecords> insert(List<OmsInStoreRecords> list);
    public List<String> selectRowId(List<String> list);
    public List<OmsInStoreRecords> selectByStatus();
    public List<OmsInStoreRecords> selectForStatus();
    public void updateByDb(List<String> list);

    int updateForOrderCode(OmsInStoreRecords oms);

    void updateDb(List<OmsInStoreRecords> list_db);

    List<OmsInStoreRecords> selectByVomStatus();

    int updataVomStatus(OmsInStoreRecords oms);

    List<OmsInStoreRecords> findForVomStatus();*/

    ServiceResult<List<OmsInStoreRecords>> searchList(OmsInStoreRecords vo);

    Map<String,Object> operateLog(String id);
}
