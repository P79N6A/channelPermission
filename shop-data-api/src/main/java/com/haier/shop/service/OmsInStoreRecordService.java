package com.haier.shop.service;

import com.haier.common.ServiceResult;
import com.haier.shop.model.OmsInStoreRecords;
import com.haier.shop.model.OrderProductsVo;

import java.util.List;

public interface OmsInStoreRecordService{
    public List<OmsInStoreRecords> insert(List<OmsInStoreRecords>list);
    public List<String> selectRowId(List<String> list);
    public List<OmsInStoreRecords> selectByStatus();
    public List<OmsInStoreRecords> selectForStatus();
    public void updateByDb(List<String> list);

    int updateForOrderCode(OmsInStoreRecords oms);

    void updateDb(List<OmsInStoreRecords> list_db);

    List<OmsInStoreRecords> selectByVomStatus();

    int updataVomStatus(OmsInStoreRecords oms);

    List<OmsInStoreRecords> findForVomStatus();

    public List<OmsInStoreRecords>  findAllocationRemnant(OmsInStoreRecords params);

    List<OmsInStoreRecords> findForSapStatus();

    int updateOutSapStatus(OmsInStoreRecords oms);

    int updateInSapStatus(OmsInStoreRecords oms);

    int findAllocationRemnantCount(OmsInStoreRecords params);

    void updateNotDb(List<String> list);
}
