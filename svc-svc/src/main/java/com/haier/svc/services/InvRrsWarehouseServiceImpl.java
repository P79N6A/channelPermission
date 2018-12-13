package com.haier.svc.services;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.service.PurchaseLogAuditService;
import com.haier.stock.model.InvRrsWarehouse;
import com.haier.stock.service.StockInvRrsWarehouseService;
import com.haier.svc.service.InvRrsWarehouseService;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvRrsWarehouseServiceImpl implements InvRrsWarehouseService{

    @Autowired
    private StockInvRrsWarehouseService stockInvRrsWarehouseService;
    @Autowired
    private PurchaseLogAuditService purchaseLogAuditService;
    private final static org.apache.log4j.Logger log = LogManager.getLogger(InvRrsWarehouseServiceImpl.class);

    /**
     * @Description:数据库查询操作，默认返回所有结果
     * @param
     *            ，返回筛选后的结果， 可选筛选字段有，rrs_wh_code（日日顺库位码），rrs_wh_name（日日顺仓库名称），
     *            estorge_id（电商库位码），t2_default，transport_prescription
     */
    @Override
    public ServiceResult<List<InvRrsWarehouse>> getPurRrsWhByEstorgeId(Map<String, Object> params) {
        ServiceResult<List<InvRrsWarehouse>> result = new ServiceResult<List<InvRrsWarehouse>>();
        try {
            result.setResult(stockInvRrsWarehouseService.getPurRrsWhByEstorgeId(params));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("通过电商库位码获取日日顺仓库List：" + e.getMessage());
            log.error("通过电商库位码获取日日顺仓库List：", e);
        }
        return result;
    }

    @Override
    public Integer countTotalService(Map<String, Object> param) {
        return stockInvRrsWarehouseService.countTotalService(param);
    }

    @Override
    public Integer checkMainKey(Map<String, Object> param) {
        return stockInvRrsWarehouseService.checkMainKey(param);
    }

    @Override
    public void insertInvRrsWarehouseService(Map<String, Object> param) {
        stockInvRrsWarehouseService.insertInvRrsWarehouseService(param);
    }

    @Override
    public Integer countT2StatusService(Map<String, Object> param) {
        return stockInvRrsWarehouseService.countT2StatusService(param);
    }

    @Override
    public void updateInvRrsWarehouseService(Map<String, Object> map) {
        String rrs_wh_code = (String) map.get("rrs_wh_code");
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("rrs_wh_code", rrs_wh_code);
        List<InvRrsWarehouse> invRrsWarehouseList = stockInvRrsWarehouseService
                .getPurRrsWhByEstorgeId(queryMap);
        InvRrsWarehouse invRrsWarehouse = invRrsWarehouseList.get(0);
        String content = "电商库位与日日顺库位对照更新前数据" + JsonUtil.toJson(invRrsWarehouse) + "更新后数据"
                + "rrs_wh_code=" + map.get("rrs_wh_code") + "rrs_wh_name="
                + map.get("rrs_wh_name") + "estorge_id=" + map.get("estorge_id")
                + "transport_prescription=" + map.get("transport_prescription")
                + "rrs_wh_code=" + map.get("rrs_wh_code") + "estorge_id="
                + map.get("estorge_id");
        purchaseLogAuditService.log("", 1, content, (String) map.get("update_user"), "", "", "", "", "");

        stockInvRrsWarehouseService.updateInvRrsWarehouseService(map);
    }

    @Override
    public void deleteInvRrsWarehouseService(Map<String, Object> param) {
        int i = 0;
        String content = "电商库位与日日顺库位对照" + "rrs_wh_code=" + JsonUtil.toJson(param.get("deleteList"));
        String is_enabled = (String) param.get("is_enabled");
        if (is_enabled.equals("4")) {
            i = 5;
        }
        if (is_enabled.equals("0")) {
            i = 3;
        }
        if (is_enabled.equals("1")) {
            i = 4;
        }
        purchaseLogAuditService.log("", i, content, (String) param.get("openUser"), "", "", "", "", "");

        stockInvRrsWarehouseService.deleteInvRrsWarehouseService(param);
    }

    @Override
    public List<InvRrsWarehouse> selectInvRrsWarehouseExportService(Map<String, Object> param) {
        return stockInvRrsWarehouseService.selectInvRrsWarehouseExportService(param);
    }
}
