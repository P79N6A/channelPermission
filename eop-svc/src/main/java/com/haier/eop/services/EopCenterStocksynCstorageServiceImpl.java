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
import com.haier.eop.data.service.StocksynCstorageService;
import com.haier.eop.service.EopCenterStocksynCstorageService;
import com.haier.shop.model.Storages;
import com.haier.shop.service.StoragesService;
@Service
public class EopCenterStocksynCstorageServiceImpl implements EopCenterStocksynCstorageService {
@Autowired
StocksynCstorageService stocksynCstorageService;
@Autowired
StoragesService storagesService;
@Override
public int deleteByPrimaryKey(Integer id) {
	stocksynCstorageService.deleteByPrimaryKey(id);
	return 1;
}

@Override
public int insert(StocksynCstorage record) {
	record.setAddTime(new Date());
	Storages id=storagesService.getByCode(record.getsCode());
	record.setStorageId(id.getId());
	stocksynCstorageService.insert(record);
	return 1;
}

@Override
public int updateByPrimaryKeySelective(StocksynCstorage record) {
	record.setUpdateTime(new Date());
	Storages id=storagesService.getByCode(record.getsCode());
	record.setStorageId(id.getId());
	stocksynCstorageService.updateByPrimaryKeySelective(record);
	return 1;
}



@Override
public int getPagerCountS(StocksynCstorage entity) {
	// TODO Auto-generated method stub
	return stocksynCstorageService.getPagerCountS(entity);
}

@Override
public JSONObject Listf(PagerInfo pager, StocksynCstorage condition) {
	List<StocksynCstorage> list = stocksynCstorageService.Listf(condition
			,pager.getStart(), pager.getPageSize());
	int total = stocksynCstorageService.getPagerCountS(condition);
    JSONArray res = new JSONArray();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    for (Object o : list) {
    	StocksynCstorage dto = (StocksynCstorage)o;
        JSONObject json = new JSONObject();
        json.put("id", dto.getId());
        json.put("storageId", dto.getStorageId());
        json.put("sCode", dto.getsCode());
        json.put("sourceStoreCode", dto.getSourceStoreCode());
        json.put("source",dto.getSource());
//        json.put("addTime",sdf.format(dto.getAddTime()));
//        json.put("updateTime",dto.getUpdateTime());
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

@Override
public int jiaoyan(String sCode) {
	int Code=0;
	Storages id=storagesService.getByCode(sCode);
	if(id != null){
		Code=id.getId();
		return Code;
	}
	return 0;
}




}


	
    
