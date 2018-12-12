package com.haier.svc.api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.T2OrderItem;
import com.haier.stock.model.InvStockAge;
import com.haier.svc.bean.StockAgeWapped;
import com.haier.svc.service.T2OrderService;

@Service
public class T2OrderModel {

	private Logger              logger          = LogManager.getLogger(T2OrderModel.class);
	@Autowired
	T2OrderService t2OrderService;

	public ServiceResult<List<T2OrderItem>> getT2OrderList(
			Map<String, Object> params) {
		return t2OrderService.getT2OrderList(params);
	}

	public ServiceResult<Integer> getRowCnts() {
		// TODO Auto-generated method stub
		return t2OrderService.getRowCnts();
	}

	public Map<String, String> getValueMeaningMap(String valueSetId) {
		// TODO Auto-generated method stub
		return t2OrderService.getValueMeaningMap(valueSetId);
	}

	public ServiceResult<BigDecimal> getOnwayNumByCateChan(String catagory,
			String ed_channel_id) {
		return t2OrderService.getOnwayNumByCateChan(catagory, ed_channel_id);
	}

	public ServiceResult<BigDecimal> getUsedNumByCateChan(
			String report_year_week, String catagory, String ed_channel_id) {
		// TODO Auto-generated method stub
		return t2OrderService.getOnwayNumByCateChan(catagory, ed_channel_id);
	}

	
	/**
     * 按报表要求包装数据
     * @param stockAges
     * @return
     */
    private List<StockAgeWapped> wappedStockAges(List<InvStockAge> stockAges) {
        List<StockAgeWapped> stockAgeWappeds = new ArrayList<StockAgeWapped>();
        for (InvStockAge invStockAge : stockAges) {
            StockAgeWapped ageWapped = new StockAgeWapped(invStockAge);
            ageWapped.wappenAgeDatas();
            stockAgeWappeds.add(ageWapped);
        }
        return stockAgeWappeds;
    }

	public ServiceResult<Map<String, Integer>> insertT2Order(
			List<T2OrderItem> t2OrderItems) {
		ServiceResult<Map<String, Integer>> insResult = t2OrderService
                .insertT2Order(t2OrderItems);
		return insResult;
	}

}
