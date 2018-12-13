package com.haier.eop.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.haier.eop.data.service.StocksyncProstorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.eop.data.model.Stocksyncproducts;
import com.haier.eop.data.service.StocksyncproductsService;
import com.haier.eop.service.EopCenterStocksyncproductsService;
import com.haier.shop.model.Products;
import com.haier.shop.service.ProductsService;
@Service
public class EopCenterStocksyncproductsServiceImpl implements EopCenterStocksyncproductsService {
	@Autowired
	StocksyncproductsService stocksyncproductsService;
	@Autowired
	StocksyncProstorageService stocksyncProstorageService;
	@Autowired
	private ProductsService productsService;
	@Override
	public int deleteByPrimaryKey(Integer id,String source,String sku) {
		stocksyncProstorageService.deleteBySourceAndSku(source,sku);
		stocksyncproductsService.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insert(Stocksyncproducts record) {
		record.setAddTime(new Date());
		Stocksyncproducts stocksyncproducts = stocksyncproductsService.getBySourceAndSku(record.getSource(),record.getSku());
		if(stocksyncproducts!=null){
			return 0;
		}
		stocksyncproductsService.insert(record);
		return 1;
	}

	@Override
	public int updateByPrimaryKey(Stocksyncproducts record) {
		record.setUpdateTime(new Date());
		stocksyncproductsService.updateByPrimaryKey(record);
		return 1;
	}

	@Override
	public JSONObject Listf(PagerInfo pager, Stocksyncproducts condition) {
		List<Stocksyncproducts> list = stocksyncproductsService.Listf(condition
    			,pager.getStart(), pager.getPageSize());
    	int total = stocksyncproductsService.getPagerCountS(condition);
        JSONArray res = new JSONArray();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object o : list) {
        	Stocksyncproducts dto = (Stocksyncproducts)o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("source",dto.getSource());
            json.put("productId",dto.getProductId());
            json.put("sku",dto.getSku());
            json.put("sourceProductId",dto.getSourceProductId());
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

	@Override
	public int jiaoyan(String sku) {
		Products  products=productsService.getBySku(sku);
		int id=0;
		if(products != null){
			 id=products.getId();
		}
		return id;
	}




}