package com.haier.svc.services;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.WAAddress;
import com.haier.purchase.data.service.PurchaseLogAuditService;
import com.haier.purchase.data.service.PurchaseWAAddressService;
import com.haier.svc.service.WaAddressService;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WaAddressServiceImpl implements WaAddressService {


    private final static org.apache.log4j.Logger log = LogManager.getLogger(WaAddressServiceImpl.class);

    @Autowired
    private PurchaseWAAddressService purchaseWAAddressService;
    @Autowired
    private PurchaseLogAuditService purchaseLogAuditService;
    @Override
    public List<WAAddress> getWAAddress(Map<String, Object> params) {

        return purchaseWAAddressService.getWAAddress(params);
    }

    @Override
    public Integer getwaAddressCount(Map<String, Object> params) {

        return purchaseWAAddressService.getwaAddressCount(params);
    }

    @Override
    public Integer checkMainKey(Map<String, Object> params) {
        return purchaseWAAddressService.checkMainKey(params);
    }

    @Override
    public void createWaAddress(Map<String, Object> params){
        purchaseWAAddressService.createWaAddress(params);
    }

    @Override
    public void updateWaAddress(Map<String, Object> params) {
        purchaseWAAddressService.updateWaAddress(params);

        try {

            Map<String, Object> logMap = new HashMap<String, Object>();
            WAAddress waAddress = new WAAddress();
            waAddress.setsCode((String) params.get("sCode"));
            logMap.put("waAddress", waAddress);
            WAAddress oldDate = purchaseWAAddressService.getWAAddress(logMap).get(0);
            //往数据库修改数据
            purchaseWAAddressService.updateWaAddress(params);
            //操作日志
            WAAddress newDate = (WAAddress) params.get("waAddress");
            String content = "更新仓库地址联系方式,原数据:" + oldDate.toString() + ",新数据:" + newDate.toString();
            purchaseLogAuditService.log("", 1, content, (String) params.get("update_user"), "", "", "", "",
                    "");
        } catch (Exception ex) {
            log.error("更新仓库地址联系方式:", ex);
        }

    }

    @Override
    public void deleteWaAddress(Map<String, Object> params) {

        try {
            purchaseWAAddressService.deleteWaAddress(params);
            //操作日志
            String content = "删除仓库地址联系方式,sCode=" + JsonUtil.toJson(params.get("deleteList"));
            purchaseLogAuditService.log("", 5, content, (String) params.get("delete_user"), "", "", "", "",
                    "");
        } catch (Exception ex) {
            log.error("删除仓库地址联系方式:", ex);
        }
    }
    /**
     *
     * @title openStatusWaAddress
     * @description 数据状态开启
     */
    @Override
    public void openStatusWaAddress(Map<String, Object> params) {
        purchaseWAAddressService.openStatusWaAddress(params);
        try {
            //操作日志
            String content = "启用仓库地址联系方式,sCode=" + JsonUtil.toJson(params.get("openList"));
            purchaseLogAuditService.log("", 3, content, (String) params.get("open_user"), "", "", "", "", "");
        } catch (Exception ex) {
            log.error("启用仓库地址联系方式:", ex);
        }
    }
    /**
     *
     * @title closeStatusWaAddress
     * @description 数据状态禁用
     */
    @Override
    public void closeStatusWaAddress(Map<String, Object> params) {
        try {
            purchaseWAAddressService.closeStatusWaAddress(params);
            //操作日志
            String content = "禁用仓库地址联系方式,sCode=" + JsonUtil.toJson(params.get("closeList"));
            purchaseLogAuditService
                    .log("", 4, content, (String) params.get("close_user"), "", "", "", "", "");
        } catch (Exception ex) {
            log.error("禁用仓库地址联系方式:", ex);
        }
    }

    /**
     *
     * @title getWAAddressExport
     * @description 导出数据
     */
    @Override
    public ServiceResult<List<WAAddress>> getWAAddressExport(
            Map<String, Object> params) {
        // TODO Auto-generated method stub
        ServiceResult<List<WAAddress>> result = new ServiceResult<List<WAAddress>>();
        try {
            result.setResult(purchaseWAAddressService.getWAAddressExport(params));
        } catch (Exception e) {
            log.error("导出WA库信息时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
}
