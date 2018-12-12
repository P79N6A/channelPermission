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
import com.haier.eop.data.model.StocksynCstorage;
import com.haier.eop.data.model.StocksyncProstorage;
import com.haier.eop.data.model.Stocksyncproducts;
import com.haier.eop.data.service.StocksynCstorageService;
import com.haier.eop.data.service.StocksyncProstorageService;
import com.haier.eop.data.service.StocksyncproductsService;
import com.haier.eop.service.EopCenterStocksyncProstorageService;
@Service
public class EopCenterStocksyncProstorageServiceImpl implements EopCenterStocksyncProstorageService {
	@Autowired
	StocksyncProstorageService StocksyncProstorageService;
	@Autowired
	StocksynCstorageService stocksynCstorageService;
	
	@Autowired
	StocksyncproductsService stocksyncproductsService;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		StocksyncProstorageService.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insert(StocksyncProstorage record) {
		if(record.getKuwei() == 1){
		List<StocksynCstorage> cid=stocksynCstorageService.getsCode(record.getSource());
		Stocksyncproducts id =stocksyncproductsService.getId(record.getSku(),record.getSource());
	    for (Object o : cid) {
	    	StocksynCstorage dto = (StocksynCstorage)o;
	    	record.setIsOn(record.getIsOn());
	    	record.setSource(record.getSource());
	    	record.setSku(record.getSku());
	    	record.setStype(record.getStype());
	    	record.setsCode(dto.getsCode());
	    	record.setSyncStorageId(dto.getId());
	    	record.setSyncProductId(id.getId());
	    	record.setAddTime(new Date());
			StocksyncProstorageService.insert(record);
	    }
		}else{
			 String[] sCode = record.getsCode().split(",");
			 Stocksyncproducts id =stocksyncproductsService.getId(record.getSku(),record.getSource());
			 StocksynCstorage centity=new StocksynCstorage();
				
			 for (int i = 0; i < sCode.length; i++) {
				 	centity.setsCode(sCode[i]);
					centity.setSource(record.getSource());
					StocksynCstorage cid=stocksynCstorageService.getId(centity);
					record.setIsOn(record.getIsOn());
			    	record.setSource(record.getSource());
			    	record.setSku(record.getSku());
			    	record.setStype(record.getStype());
			    	record.setsCode(sCode[i]);
			    	record.setSyncStorageId(cid.getId());
			    	record.setSyncProductId(id.getId());
			    	record.setAddTime(new Date());
					StocksyncProstorageService.insert(record);
			 }
			List<StocksynCstorage> cid=stocksynCstorageService.getsCode(record.getSource());
			
		    for (Object o : cid) {
		    	StocksynCstorage dto = (StocksynCstorage)o;
		    	
		    }
		}
		
		return 1;
	}

	@Override
	public int updateByPrimaryKey(StocksyncProstorage record) {
		record.setUpdateTime(new Date());
		StocksyncProstorageService.updateByPrimaryKeySelective(record);
		return 1;
	}

	@Override
	public JSONObject Listf(PagerInfo pager, StocksyncProstorage condition) {
		List<StocksyncProstorage> list = StocksyncProstorageService.Listf(condition
    			,pager.getStart(), pager.getPageSize());
    	int total = StocksyncProstorageService.getPagerCountS(condition);
        JSONArray res = new JSONArray();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object o : list) {
        	StocksyncProstorage dto = (StocksyncProstorage)o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("syncProductId", dto.getSyncProductId());
            json.put("syncStorageId", dto.getSyncStorageId());
            json.put("sCode", dto.getsCode());
            json.put("isOn", dto.getIsOn());
            json.put("source",dto.getSource());
            json.put("sku",dto.getSku());
            json.put("tzSku",dto.getTzSku());
            json.put("addTime",sdf.format(dto.getAddTime()));
            json.put("updateTime",dto.getUpdateTime());
            json.put("stype",dto.getStype());
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

	public JSONObject jiaoyan(String sku, String sCode, String source) {
		StocksynCstorage centity=new StocksynCstorage();
		centity.setsCode(sCode);
		centity.setSource(source);
		StocksynCstorage cid=stocksynCstorageService.getId(centity);
		Stocksyncproducts id =stocksyncproductsService.getId(sku,source);
		 JSONObject json = new JSONObject();
		 if(cid != null){
			  json.put("cid", cid.getId());
		 }else{
         json.put("cid", 0);
		 }
		 if(id != null){
			   json.put("id", id.getId());
		 }else{
			 json.put("id", 0);
		 }
		return json;
	}






	




}