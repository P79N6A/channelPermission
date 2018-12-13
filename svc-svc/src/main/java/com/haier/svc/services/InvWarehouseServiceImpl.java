package com.haier.svc.services;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.InvBaseCityCode;
import com.haier.purchase.data.service.InvBaseCityCodeService;
import com.haier.purchase.data.service.PurchaseLogAuditService;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.service.StockInvWarehouseService;
import com.haier.svc.service.InvWarehouseService;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvWarehouseServiceImpl implements InvWarehouseService{

    private final static org.apache.log4j.Logger log = LogManager.getLogger(InvWarehouseServiceImpl.class);

    @Autowired
    private StockInvWarehouseService stockInvWarehouseService;
    @Autowired
    private PurchaseLogAuditService purchaseLogAuditService;
    @Autowired
    private InvBaseCityCodeService invBaseCityCodeService;
    @Override
    public ServiceResult<List<InvWarehouse>> getInvWarehouseInfo(Map<String, Object> param) {
        ServiceResult<List<InvWarehouse>> result = new ServiceResult<List<InvWarehouse>>();
        try {
            result.setResult(stockInvWarehouseService.getInvWarehouseInfo(param));
        } catch (Exception e) {
            log.error("获取WA库信息时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;    }

    /**
     *
     * @title getInvWareHouseCount
     * @description 查询数据条数
     */
    @Override
    public Integer getInvWareHouseCount(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return stockInvWarehouseService.getInvWarehouseCount(params);
    }

    /**
     *
     *
     * @title:checkMainKey
     * @description:查询主键是否重复
     */
    @Override
    public int checkMainKey(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return stockInvWarehouseService.checkMainKey(params);
    }

    /**
     *
     * @title createInvWareHouse
     * @description 添加数据
     */
    @Override
    public void createInvWareHouse(Map<String, Object> params) {
        // TODO Auto-generated method stub
        stockInvWarehouseService.createInvWarehouse(params);
    }

    /**
     *
     * @title updateInvWareHouse
     * @description 更新数据
     */
    @Override
    public void updateInvWareHouse(Map<String, Object> params) {
        // TODO Auto-generated method stub
        try {
            //操作日志
            Map<String, Object> logMap = new HashMap<String, Object>();
            logMap.put("wh_code", params.get("wh_code"));
            InvWarehouse oldDate = stockInvWarehouseService.getInvWarehouseInfo(logMap).get(0);
            InvWarehouse newDate = new InvWarehouse();
            newDate.setWhCode((String) params.get("wh_code"));
            newDate.setWhName((String) params.get("wh_name"));
            newDate.setStatus(Integer.parseInt((String) params.get("status")));
            newDate.setCenterCode((String) params.get("center_code"));
            newDate.setSupportCod(Integer.parseInt((String) params.get("support_cod")));
            newDate.setLesAccepter((String) params.get("les_accepter"));
            newDate.setEstorge_id((String) params.get("estorge_id"));
            newDate.setEstorge_name((String) params.get("estorge_name"));
            newDate.setTransmit_id((String) params.get("transmit_id"));
            newDate.setTransmit_desc((String) params.get("transmit_desc"));
            newDate.setLes_OE_id((String) params.get("les_OE_id"));
            newDate.setCustom_id((String) params.get("custom_id"));
            newDate.setCustom_desc((String) params.get("custom_desc"));
            newDate.setIndustry_trade_id((String) params.get("industry_trade_id"));
            newDate.setIndustry_trade_desc((String) params.get("industry_trade_desc"));
            newDate.setGraininess_id((String) params.get("graininess_id"));
            newDate.setNet_management_id((String) params.get("net_management_id"));
            newDate.setEsale_id((String) params.get("esale_id"));
            newDate.setEsale_name((String) params.get("esale_name"));
            newDate.setSale_org_id((String) params.get("sale_org_id"));
            newDate.setBranch((String) params.get("branch"));
            newDate.setPayment_id((String) params.get("payment_id"));
            newDate.setPayment_name((String) params.get("payment_name"));
            newDate.setArea_id((String) params.get("area_id"));
            newDate.setRrs_distribution_id((String) params.get("rrs_distribution_id"));
            newDate.setRrs_distribution_name((String) params.get("rrs_distribution_name"));
            newDate.setRrs_sale_id((String) params.get("rrs_sale_id"));
            newDate.setRrs_sale_name((String) params.get("rrs_sale_name"));
            newDate.setOms_rrs_payment_id((String) params.get("oms_rrs_payment_id"));
            newDate.setOms_rrs_payment_name((String) params.get("oms_rrs_payment_name"));
            newDate.setCity_desc((String) params.get("city_desc"));
            newDate.setCity_code((String) params.get("city_code"));
            String content = "修改电商OMS送达方对照,原数据:" + oldDate.toString() + ",新数据:"
                    + newDate.toString();
            purchaseLogAuditService.log("", 1, content, (String) params.get("update_user"), "", "", "", "",
                    "");
            stockInvWarehouseService.updateInvWarehouse(params);
        } catch (Exception ex) {
            log.error("修改电商OMS送达方对照", ex);
        }
    }

    /**
     *
     * @title deleteInvWareHouse
     * @description 删除数据
     */
    @Override
    public void deleteInvWareHouse(Map<String, Object> params) {
        // TODO Auto-generated method stub
        try {
            //操作日志
            String content = "删除电商OMS送达方对照,wh_code=" + JsonUtil.toJson(params.get("deleteList"));
            purchaseLogAuditService.log("", 5, content, (String) params.get("delete_user"), "", "", "", "",
                    "");
            stockInvWarehouseService.deleteInvWareHouse(params);
        } catch (Exception ex) {
            log.error("删除电商OMS送达方对照", ex);
        }

    }

    /**
     *
     * @title openStatusInvWarehouse
     * @description 数据状态开启
     */
    @Override
    public void openStatusInvWarehouse(Map<String, Object> params) {
        // TODO Auto-generated method stub
        try {
            //操作日志
            String content = "启用电商OMS送达方对照,wh_code=" + JsonUtil.toJson(params.get("openList"));
            purchaseLogAuditService.log("", 3, content, (String) params.get("open_user"), "", "", "", "", "");
        } catch (Exception ex) {
            log.error("启用电商OMS送达方对照", ex);
        }
        stockInvWarehouseService.openStatusInvWarehouse(params);
    }

    /**
     *
     * @title closeStatusInvWarehouse
     * @description 数据状态关闭
     */
    @Override
    public void closeStatusInvWarehouse(Map<String, Object> params) {
        // TODO Auto-generated method stub
        try {
            //操作日志
            String content = "禁用电商OMS送达方对照,wh_code=" + JsonUtil.toJson(params.get("closeList"));
            purchaseLogAuditService
                    .log("", 4, content, (String) params.get("close_user"), "", "", "", "", "");
        } catch (Exception ex) {
            log.error("禁用电商OMS送达方对照", ex);
        }
        stockInvWarehouseService.closeStatusInvWarehouse(params);
    }

    /**
     *
     * @title getInvWarehouseExport
     * @description 导出数据
     */
    @Override
    public ServiceResult<List<InvWarehouse>> getInvWarehouseExport(
            Map<String, Object> params) {
        // TODO Auto-generated method stub
        ServiceResult<List<InvWarehouse>> result = new ServiceResult<List<InvWarehouse>>();
        try {
            result.setResult(stockInvWarehouseService.getInvWarehouseExport(params));
        } catch (Exception e) {
            log.error("导出电商OMS送达方对照信息时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvBaseCityCode>> getInvBaseCityCode(Map<String, Object> params) {
        ServiceResult<List<InvBaseCityCode>> result = new ServiceResult<List<InvBaseCityCode>>();
        try {
            result.setResult(invBaseCityCodeService.getInvBaseCityCode(params));
            result.setSuccess(true);
        } catch (Exception ex) {
            log.error("getInvBaseCityCode:", ex);
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage(ex.getMessage());
        }
        return result;
    }
}
