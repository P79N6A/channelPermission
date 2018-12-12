package com.haier.stock.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.stock.model.QueryTotalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.stock.model.InvReservedConfig;
import com.haier.stock.service.StockReservedService;
@Service
public class StockReservedServiceImpl implements StockReservedService{
	@Autowired
	private StockReservedModel stockReservedModel;
	
	
    @Override
    public ServiceResult<Boolean> updateReservedConfigById(InvReservedConfig config) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        boolean isSuc = this.stockReservedModel.updateStockReservedConfig(config);
        result.setResult(isSuc);
        result.setMessage(isSuc ? "更新配置信息成功！" : "更新配置信息失败！");
        return result;
    }
    @Override
    public ServiceResult<List<InvReservedConfig>> queryInvReservedConfigs(InvReservedConfig config) {
        ServiceResult<List<InvReservedConfig>> result = new ServiceResult<List<InvReservedConfig>>();
        result.setResult(this.stockReservedModel.queryInvReservedConfigs(config));
        return result;
    }
    
    @Override
    public ServiceResult<Boolean> insertInvReservedConfig(InvReservedConfig config) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        boolean isSuc = this.stockReservedModel.insertStockReservedConfig(config);
        result.setResult(isSuc);
        result.setMessage(isSuc ? "新增配置信息成功！" : "新增配置信息失败！");
        return result;
    }

    public List<Map<String,String>> queryTotalPage(Map<String,Object> map){
        System.out.println("我来all");
        return stockReservedModel.queryTotalPage(map);

    }
    public  JSONObject queryTotalData(PagerInfo pager,Map<String, Object> map){
        List<QueryTotalData> list=stockReservedModel.queryTotalData(map,pager.getStart(), pager.getPageSize());
        for (QueryTotalData queryTotalData:list){
            queryTotalData.setUpdateTime(queryTotalData.getUpdateTime().substring(0,queryTotalData.getUpdateTime().length()-2));
        }
        int total=stockReservedModel.queryTotalPage(map).size();
        return jsonResult(list,total);

    }
    private <T> JSONObject jsonResult(List<T> list, long total) {
        JSONObject json = new JSONObject();
        json.put("total", total);
        if (list == null || list.isEmpty()) {
            json.put("rows", new ArrayList<T>());
        } else {
            json.put("rows", list);
        }
        return json;
    }
}
