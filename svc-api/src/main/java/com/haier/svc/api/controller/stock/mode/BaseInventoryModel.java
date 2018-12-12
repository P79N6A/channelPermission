package com.haier.svc.api.controller.stock.mode;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.haier.stock.model.InvBaseStockExcel;
import com.haier.stock.service.SvcInvBaseStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.stock.model.InvBaseStockEx;
import com.haier.stock.model.InvBaseStockLog;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.service.InvStockLockDesService;
import com.haier.stock.service.StockCommonService;
import com.haier.stock.service.StockInvBaseStockService;
@Service
public class BaseInventoryModel {

	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(BaseInventoryModel.class);
	@Autowired
    private StockInvBaseStockService      stockInvBaseStockService;
	@Autowired
    private StockCommonService   stockCommonService;
	@Autowired
    private InvStockLockDesService invStockLockDesService;
    @Autowired
    private SvcInvBaseStockService svcInvBaseStockService;
    public List<InvBaseStockEx> queryBaseStockRows(InvBaseStockEx stock, PagerInfo pager) {
        List<InvBaseStockEx> baseStock = stockInvBaseStockService.queryInvBaseStockList(stock, pager);
        pager.setRowsCount(stockInvBaseStockService.getRowCnt());
        return baseStock;
    }

    public List<InvBaseStockEx> queryMachineSetRows(InvBaseStockEx stock, PagerInfo pager) {
        List<InvBaseStockEx> invStock = stockInvBaseStockService.queryInvStockList(stock, pager);
        pager.setRowsCount(stockInvBaseStockService.getRowCnt());
        return invStock;
    }

    public List<InvBaseStockLog> queryInvBaseStockLogRows(InvBaseStockLog log, PagerInfo pager) {
        List<InvBaseStockLog> logs = stockInvBaseStockService.queryInvBaseStockLogList(log, pager);
        pager.setRowsCount(stockInvBaseStockService.getRowCnt());
        return logs;

    }

    public List<InvMachineSet> getSubMachinesByMainSku(String mainSku) {
        InvMachineSet machineSet = new InvMachineSet();
        machineSet.setMainSku(mainSku);
        ServiceResult<List<InvMachineSet>> result = stockCommonService
            .getSubMachinesByMainSku(machineSet);
        if (result.getSuccess()) {
            return result.getResult();
        } else {
            return null;
        }
    }

    public List<Map<String, Object>> queryByfrozenQtyGtStockQty(Map<String, Object> paramMap) {
    	List<Map<String, Object>> list = null;
    	try{
    		list = stockInvBaseStockService.queryByfrozenQtyGtStockQty(paramMap);
    	}catch(Exception e){
    		log.error("[BaseInventoryModel_queryByfrozenQtyGtStockQty]查询库存数据时发生异常:" + e.getMessage());
    	}
        return list;

    }
    
    public Integer getfrozenQtyGtStockQtyCount(Map<String, Object> params){
    	try{
    		return stockInvBaseStockService.queryByfrozenQtyGtStockQtyCount(params);
    	}catch(Exception e){
    		log.error("[BaseInventoryModel_getfrozenQtyGtStockQtyCount]查询库存数据数量时发生异常:" + e.getMessage());
    	}
    	 return null;
    }
    
    public List<Map<String, Object>> getBySecCodeAndSku(Map<String, Object> params) {
        return invStockLockDesService.getBySecCodeAndSku(params);
    }
    
    public Integer getBySecCodeAndSkuCount(Map<String, Object> params) {
    	return invStockLockDesService.getBySecCodeAndSkuCount(params);
    }
    public List<InvBaseStockExcel> queryBaseStockRows1(InvBaseStockExcel stock, PagerInfo pager) {
        List<InvBaseStockExcel> baseStock = svcInvBaseStockService.queryInvBaseStockList1(stock, pager);
        pager.setRowsCount(svcInvBaseStockService.getRowCnt());
        return baseStock;
    }
    public JSONObject queryBaseStockRows(com.haier.common.PagerInfo pager, Map<String,String> map)throws Exception {
        JSONObject jsonObject= svcInvBaseStockService.queryInvBaseStockList(map, pager);
        return jsonObject;
    }
}
