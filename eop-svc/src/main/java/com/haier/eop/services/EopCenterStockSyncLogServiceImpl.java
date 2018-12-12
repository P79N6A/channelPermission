package com.haier.eop.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.eop.data.model.StockSyncLog;
import com.haier.eop.data.model.Stocksyncproducts;
import com.haier.eop.data.service.StockSyncLogService;
import com.haier.eop.data.service.StocksyncproductsService;
import com.haier.eop.service.EopCenterStockSyncLogService;
import com.haier.eop.service.EopCenterStocksyncproductsService;
import com.haier.shop.model.Products;
import com.haier.shop.service.ProductsService;
@Service
public class EopCenterStockSyncLogServiceImpl implements  EopCenterStockSyncLogService {
	@Autowired
	StockSyncLogService stockSyncLogService;

	@Override
	public  JSONObject LogListf(PagerInfo pager, String sse, String sku,
			String sourceProductId, String sCode, String sourceStoreCode, String stockSyncResult, String addTimeStart,
			String addTimeEnd) {
		// TODO Auto-generated method stub
		List<StockSyncLog> list = stockSyncLogService.LogListf(
    			pager.getStart(), pager.getPageSize(),  sse,  sku,
    			 sourceProductId,  sCode,  sourceStoreCode,  stockSyncResult,  addTimeStart,
    			 addTimeEnd);
    	int total = stockSyncLogService.getPagerCountS(sse,  sku,
   			 sourceProductId,  sCode,  sourceStoreCode,  stockSyncResult,  addTimeStart,
   			 addTimeEnd);
        JSONArray res = new JSONArray();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object o : list) {
        	StockSyncLog dto = (StockSyncLog)o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("addTime",sdf.format(dto.getAddTime()));
            json.put("sse",dto.getSse());
            json.put("sku",dto.getSku());
            json.put("channelStockNum",dto.getChannelStockNum());
            json.put("ehaierStockNum",dto.getEhaierStockNum());
            json.put("unconfirmStockNum",dto.getUnconfirmStockNum());
            json.put("finalStockNum",dto.getFinalStockNum());
            json.put("stockSyncResult",dto.getStockSyncResult());
            json.put("systemInfo",dto.getSystemInfo());
            json.put("textInfo",dto.getTextInfo());
            json.put("sourceStoreCode",dto.getSourceStoreCode());
            json.put("sCode",dto.getScode());
            json.put("sourceproductId",dto.getSourceproductId());
            res.add(json);
        }
        return jsonResult(res,total);
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