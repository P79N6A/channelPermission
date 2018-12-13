package com.haier.svc.api.controller.stock.mode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.stock.model.InvReservedConfig;
import com.haier.stock.model.InvStockAge;
import com.haier.stock.service.InvStockLockDesService;
import com.haier.stock.service.StockAgeService;
import com.haier.stock.service.StockInvStockAgeService;
import com.haier.stock.service.StockReservedConfigService;
import com.haier.stock.service.StockReservedService;
@Service
public class StockReservedModel {
	@Autowired
    private StockReservedService   stockReservedService;
	@Autowired
    private StockReservedConfigService stockReservedConfigService;
	@Autowired
    private InvStockLockDesService     invStockLockDesService;
	@Autowired
    private StockInvStockAgeService         stockInvStockAgeService;
	@Autowired
    private StockAgeService        stockAgeService;

    public List<Map<String, Object>> queryStockReservedList(PagerInfo pager,
                                                            Map<String, Object> params) {
        /*
                ServiceResult<List<InvStockAge>> stockAgeResult = stockAgeService.getStockAgeList(pager,
                    params);
                pager.setRowsCount(stockAgeResult.getPager().getRowsCount());*/
        // pager = stockAgeResult.getPager();
        params.put("start",(pager.getPageIndex()-1)*pager.getPageSize());
        params.put("size", pager.getPageSize());
        List<Map<String, Object>> list = stockInvStockAgeService.getStockAgeListTo2(params);
        pager.setRowsCount(stockInvStockAgeService.getRowCnt());
        return list;
    }

    /**
     * 查询预留配置列表: 无分页
     * @param config
     * @return
     */
    public ServiceResult<List<InvReservedConfig>> queryReservedConfigs(InvReservedConfig config) {
        return stockReservedService.queryInvReservedConfigs(config);
    }
    /**
     * 查询预留配置列表: 总页数
     * @param map
     * @return
     */
    public List<Map<String,String>> queryTotalPage(Map<String,Object> map) {
        return stockReservedService.queryTotalPage(map);
    }
    public JSONObject queryTotalData(PagerInfo pagerInfo,Map<String,Object> map) {
        return stockReservedService.queryTotalData(pagerInfo,map);
    }
    public List<InvReservedConfig> queryReservedConfigs(InvReservedConfig config, PagerInfo pager) {
        List<InvReservedConfig> reservedConfig = this.stockReservedConfigService.getReservedConfig(
            config, pager);
        pager.setRowsCount(stockReservedConfigService.getRowCnt());
        return reservedConfig;
    }


    public List<Map<String, Object>> queryLockDetails(String secCode, String sku,
                                                      String channnelCode, String iswa) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("secCode", secCode);
        params.put("sku", sku);
        params.put("channnelCode", channnelCode);
        List<Map<String, Object>> detailsMap;
        if (iswa.equals("1")) {
            detailsMap = this.invStockLockDesService.queryWaLockDetails(params);
        } else {
            detailsMap = this.invStockLockDesService.queryChannelLockDetails(params);
        }
        return detailsMap;

    }

    /**
     * 查询品类List
     * @return
     */
//    public List<String> getProductTypes() {
//        return stockAgeService.getProductTypesInStockAge().getResult();
//    }

    /**
     * 插入预留配置信息
     * @param config
     * @return
     */
    public ServiceResult<Boolean> insertReservedConfig(InvReservedConfig config) {
        return stockReservedService.insertInvReservedConfig(config);
    }

    /**
     * 更新预留配置信息
     * @param config
     * @return
     */
    public ServiceResult<Boolean> updateReservedConfig(InvReservedConfig config) {
        return stockReservedService.updateReservedConfigById(config);
    }



}
