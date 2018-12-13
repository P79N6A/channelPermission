package com.haier.svc.services;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.CnT2PurchaseStock;
import com.haier.purchase.data.service.CnT2PurchaseStockService;
import com.haier.svc.service.PurchaseStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PurchaseStockServiceImpl implements PurchaseStockService{

    @Autowired
    private CnT2PurchaseStockService cnT2PurchaseStockService;
    @Override
    public ServiceResult<List<CnT2PurchaseStock>> getPurchaseStockList(Map<String, Object> params) {

        ServiceResult<List<CnT2PurchaseStock>> result = new ServiceResult<List<CnT2PurchaseStock>>();
        try {
            List<CnT2PurchaseStock> list = cnT2PurchaseStockService
                    .getPurchaseStockList(params);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(cnT2PurchaseStockService.getCnT2PurchaseStockCNT(params));
            result.setPager(pi);
            result.setResult(list);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("通过电商库位码获取日日顺仓库List：" + e.getMessage());
        }
        return result;
    }

    @Override
    public List<CnT2PurchaseStock> getPurchaseStocDatakList(Map<String, Object> params) {
            List<CnT2PurchaseStock> list = cnT2PurchaseStockService
                    .getPurchaseStockList(params);
        return list;
    }
}
