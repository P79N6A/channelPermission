package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dao.settleCenter.SettlementInvoiceDataDao;
import com.haier.shop.model.DistributionDetails;
import com.haier.shop.model.OdsTMFXPointMaintain;
import com.haier.shop.model.SettlementInvoiceData;
import com.haier.shop.service.SettlementInvoiceDataDataService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SettlementInvoiceDataDataServiceImpl implements SettlementInvoiceDataDataService {

    @Autowired
    SettlementInvoiceDataDao settlementInvoiceDataDao;

    @Override
    public List<SettlementInvoiceData> querySettlementInvoiceData(SettlementInvoiceData model) {
        return settlementInvoiceDataDao.list(model);
    }

    @Override
    public List<SettlementInvoiceData> querySettlementInvoiceDataById(String id) {
        return settlementInvoiceDataDao.load(id);
    }

    @Override
    public void create(SettlementInvoiceData model) {
        settlementInvoiceDataDao.create(model);

    }

    @Override
    public void creates(List<SettlementInvoiceData> modelList) {
        settlementInvoiceDataDao.creates(modelList);
    }

    @Override
    public void updateSettlementInvoiceData(SettlementInvoiceData model) {
        settlementInvoiceDataDao.update(model);
    }

    @Override
    public JSONObject paging(Map<String, String> param, PagerInfo page) {

        JSONObject result = new JSONObject();
        List<SettlementInvoiceData> settlementInvoiceDataList = settlementInvoiceDataDao.paging(param,page);
        if(settlementInvoiceDataList == null){
            settlementInvoiceDataList = new ArrayList<SettlementInvoiceData>();
        }
        result.put("rows", settlementInvoiceDataList);
        result.put("total", settlementInvoiceDataDao.count(param));
        return result;

    }

    @Override
    public JSONObject export(Map<String, String> param) {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = settlementInvoiceDataDao.export(param);
        if(list == null){
            list = new ArrayList<Map<String,Object>>();
        }
        result.put("rows", list);
        return result;
    }

    @Override
    public void bulkImport(List<SettlementInvoiceData> list){
        settlementInvoiceDataDao.bulkImport(list);
    }

    @Override
    public SettlementInvoiceData getByCOrderSnAndStatusType(String cOrderSn,int statusType){
        return settlementInvoiceDataDao.getByCOrderSnAndStatusType(cOrderSn,statusType);
    }

	@Override
	public List<String> getSelections() {
		return settlementInvoiceDataDao.getSelections();
	}
}
