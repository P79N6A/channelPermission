package com.haier.stock.services;

import com.haier.common.util.StringUtil;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStockBatch;
import java.util.ArrayList;
import java.util.HashMap;
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

  @Autowired
  private StockCommonModel stockCommonModel;
  @Autowired
  private TransferLineModel transferLineModel;
	
	
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

    @Override
    public List<Map<String,String>> queryTotalPage(Map<String,Object> map){
        System.out.println("我来all");
        return stockReservedModel.queryTotalPage(map);

    }
    @Override
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

  @Override
  public ServiceResult<Boolean> stockReservedToRelease(InvStockBatch stockBatch,
      List<InvReservedConfig> configs) {
    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    Map<String, InvReservedConfig> channelMap = convertReservedChannelConfigs(configs, true);
    Map<String, InvReservedConfig> refMap = convertReservedChannelConfigs(configs, false);
    InvSection invSection = this.stockCommonModel.getSectionByCode(stockBatch.getSecCode());
    if (invSection == null) {
      result.setResult(false);
      result.setMessage("库位编码不存在！, refno:" + stockBatch.getRefno());
      return result;
    }
    try {
      StringBuffer info = new StringBuffer();
      boolean isSuc = this.stockReservedModel.stockReservedToRelease(stockBatch, invSection,
          refMap, channelMap, info);
      //只有需走调拨的流程返回false
      if (!isSuc) {
        InvReservedConfig reserveConfig = refMap.get(stockBatch.getRefno());
        this.transferLineModel.transferLineByReservedConfig(stockBatch, invSection,
            reserveConfig);
      }
      result.setResult(true);
      result.setMessage(info.toString());
    } catch (Exception e) {
      result.setMessage(e.getMessage());
      result.setResult(false);
    }
    return result;
  }

  private Map<String, InvReservedConfig> convertReservedChannelConfigs(
      List<InvReservedConfig> configs,
      boolean channelMap) {
    Map<String, InvReservedConfig> map = new HashMap<String, InvReservedConfig>();
    for (InvReservedConfig config : configs) {
      //渠道MAP
      if (channelMap && !StringUtil.isEmpty(config.getChannelCode(), true)
          && StringUtil.isEmpty(config.getRef(), true)) {
        map.put(config.getChannelCode().toUpperCase(), config);
      } else {
        if (!StringUtil.isEmpty(config.getRef(), true)) {
          map.put(config.getRef(), config);
        }
      }
    }
    return map;
  }
}
