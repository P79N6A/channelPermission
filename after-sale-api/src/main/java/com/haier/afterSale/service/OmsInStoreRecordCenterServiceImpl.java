package com.haier.afterSale.service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.service.CnDataDirectPushService;
import com.haier.shop.model.OmsInStoreRecords;
import com.haier.shop.model.OrderProductsVo;
import com.haier.shop.service.AllocateDefectLogsService;
import com.haier.shop.service.OmsInStoreRecordService;
import com.haier.stock.model.OrderProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OmsInStoreRecordCenterServiceImpl implements OmsInStoreRecordCenterService {

    @Autowired
    private OmsInStoreRecordService omsInStoreRecordService;
    @Autowired
    private AllocateDefectLogsService allocateDefectLogsService;

    /*
    @Override
    public List<OmsInStoreRecords> insert(List<OmsInStoreRecords> list) {
        return null;
    }

    @Override
    public List<String> selectRowId(List<String> list) {
        return null;
    }

    @Override
    public List<OmsInStoreRecords> selectByStatus() {
        return null;
    }

    @Override
    public List<OmsInStoreRecords> selectForStatus() {
        return null;
    }

    @Override
    public void updateByDb(List<String> list) {

    }

    @Override
    public int updateForOrderCode(OmsInStoreRecords oms) {
        return 0;
    }

    @Override
    public void updateDb(List<OmsInStoreRecords> list_db) {

    }

    @Override
    public List<OmsInStoreRecords> selectByVomStatus() {
        return null;
    }

    @Override
    public int updataVomStatus(OmsInStoreRecords oms) {
        return 0;
    }

    @Override
    public List<OmsInStoreRecords> findForVomStatus() {
        return null;
    }
*/

    @Override
    public ServiceResult<List<OmsInStoreRecords>> searchList(OmsInStoreRecords params) {
        ServiceResult<List<OmsInStoreRecords>> result = new ServiceResult<List<OmsInStoreRecords>>();
        List<OmsInStoreRecords>  list =omsInStoreRecordService.findAllocationRemnant(params);
        result.setResult(list);
        PagerInfo pi = new PagerInfo();
        int count = omsInStoreRecordService.findAllocationRemnantCount(params);
        pi.setRowsCount(count);
        result.setPager(pi);
        return result;
    }

    @Override
    public Map<String, Object> operateLog(String id) {
        return allocateDefectLogsService.operateLog(id);
    }
}
