package com.haier.invoice.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.invoice.service.OdsGatePriceService;
import com.haier.shop.model.Brands;
import com.haier.shop.model.GatePrice;
import com.haier.shop.model.OdsGateGrossprofit;
import com.haier.shop.service.OdsGatePriceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OdsGatePriceServiceImpl implements OdsGatePriceService{

    @Autowired
    private OdsGatePriceDataService odsGatePriceDataService;
    @Override
    public Map<String, Object> queryGatePrice(Map<String, Object> params) {
        return odsGatePriceDataService.queryGatePrice(params);
    }

    @Override
    public ServiceResult<String> updateGatePrice(GatePrice gatePrice) {
        return odsGatePriceDataService.updateGatePrice(gatePrice);
    }


    @Override
    public List<GatePrice> getExportGatePriceList(Map<String, Object> param) {
        return odsGatePriceDataService.getExportGatePriceList(param);
    }

    @Override
    public ServiceResult<String> execExcel(List<GatePrice> list) {
        return odsGatePriceDataService.bulkImport(list);
    }

    @Override
    public ServiceResult<List<String>> selectBrandsList() {

        ServiceResult<List<String>> result = new ServiceResult<List<String>>();
        try {
            result.setResult(odsGatePriceDataService.selectBrandsList());
        } catch (Exception e) {
            result.setMessage("查询所有CBS品类发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ServiceResult<List<String>> selectCategoryList() {
        ServiceResult<List<String>> result = new ServiceResult<List<String>>();
        try {
            result.setResult(odsGatePriceDataService.selectCategoryList());
        } catch (Exception e) {
            result.setMessage("查询所有CBS品类发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
}
