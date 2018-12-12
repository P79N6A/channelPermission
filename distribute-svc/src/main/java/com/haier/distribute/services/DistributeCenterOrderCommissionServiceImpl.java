package com.haier.distribute.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.TSaleProductPrice;
import com.haier.distribute.data.service.TSaleProductPriceService;
import com.haier.distribute.service.DistributeCenterOrderCommissionService;
import com.haier.distribute.util.CommUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 胡万来 on 2018/1/13 0013.
 */
@Service
public class DistributeCenterOrderCommissionServiceImpl implements DistributeCenterOrderCommissionService {

   @Autowired
    TSaleProductPriceService tSaleProductPriceService;

    @Override
    public JSONObject orderCommissionInfoList(PagerInfo pager, TSaleProductPrice tSaleProductPrice) {

        List<TSaleProductPrice> list = tSaleProductPriceService.getPageByCondition(tSaleProductPrice,
                pager.getStart(), pager.getPageSize());

        List<TSaleProductPrice> exportList = tSaleProductPriceService.getPageByCondition(tSaleProductPrice,
                0, Integer.MAX_VALUE);

        long total = tSaleProductPriceService.getPagerCount(tSaleProductPrice);
        BigDecimal supplyAmount = new BigDecimal(0);
        BigDecimal commissionAmount = new BigDecimal(0);
        for (TSaleProductPrice tspp : list) {
            BigDecimal price;
            BigDecimal number;
            BigDecimal policy;
            BigDecimal sPrice;
            String n;

            price = tspp.getPrice() == null ? new BigDecimal(0) : tspp.getPrice();
            n = tspp.getNumber() == null ? "0" : tspp.getNumber();
            number = new BigDecimal(n) == null ? new BigDecimal(0) : new BigDecimal(n);
            policy = tspp.getMonthPolicy() == null ? new BigDecimal(0) : tspp.getMonthPolicy();
            sPrice = tspp.getSupplyPrice() == null ? new BigDecimal(0) : tspp.getSupplyPrice();

            tspp.setpAmount(price.multiply(number));
            tspp.setSupplyAmount(sPrice.multiply(number));
            tspp.setCommission(tspp.getSupplyAmount().multiply(policy.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_DOWN)));

            if (tspp.getSupplyPrice() == null || tspp.getMonthPolicy() == null) {
                tspp.setCommission(null);
            }
            if (tspp.getSupplyPrice() == null) {
                tspp.setSupplyAmount(null);
            }
            tspp.setFirstConfirmTimeStr(CommUtil.convertLongDateToString(tspp.getFirstConfirmTime() * 1000L));
        }

        //为了导出而循环
        for (TSaleProductPrice tspp : exportList) {
            BigDecimal price;
            BigDecimal number;
            BigDecimal policy;
            BigDecimal sPrice;
            String n;

            price = tspp.getPrice() == null ? new BigDecimal(0) : tspp.getPrice();
            n = tspp.getNumber() == null ? "0" : tspp.getNumber();
            number = new BigDecimal(n) == null ? new BigDecimal(0) : new BigDecimal(n);
            policy = tspp.getMonthPolicy() == null ? new BigDecimal(0) : tspp.getMonthPolicy();
            sPrice = tspp.getSupplyPrice() == null ? new BigDecimal(0) : tspp.getSupplyPrice();

            tspp.setpAmount(price.multiply(number));
            tspp.setSupplyAmount(sPrice.multiply(number));
            tspp.setCommission(tspp.getSupplyAmount().multiply(policy.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_DOWN)));
            supplyAmount = supplyAmount.add(tspp.getSupplyAmount());
            commissionAmount = commissionAmount.add(tspp.getCommission());

            if (tspp.getSupplyPrice() == null || tspp.getMonthPolicy() == null) {
                tspp.setCommission(null);
            }
            if (tspp.getSupplyPrice() == null) {
                tspp.setSupplyAmount(null);
            }

        }

        return jsonResultForProduct(list, total, supplyAmount, commissionAmount);
    }

    @Override
    public List<TSaleProductPrice> getExportData(TSaleProductPrice condition) {
        return tSaleProductPriceService.getExportData(condition);
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

    private <T> JSONObject jsonResultForProduct(List<T> list, long total, BigDecimal a, BigDecimal b) {
        JSONObject json = new JSONObject();
        json.put("total", total);
        List<Map<String, Object>> mapList1 = new ArrayList<Map<String, Object>>();
        Map<String, Object> tmap1 = new HashMap<String, Object>();
        tmap1.put("orderSn", "结算总金额（元）");
        tmap1.put("source", a);
        tmap1.put("productName", "佣金总金额（元）");
        tmap1.put("sku", b);
        mapList1.add(tmap1);

        if (list == null || list.isEmpty()) {
            json.put("rows", new ArrayList<T>());
            json.put("footer", mapList1);
        } else {
            json.put("rows", list);
            json.put("footer", mapList1);
        }
        return json;
    }
}
