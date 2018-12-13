package com.haier.eop.services;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lorecraft.phparser.SerializedPhpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.eop.data.model.Salesettings;
import com.haier.eop.data.model.Ustring;
import com.haier.eop.data.service.SalesettingsService;
import com.haier.eop.service.EopCenterSalesettingsService;
import com.haier.shop.model.Products;
import com.haier.shop.service.ProductsService;
@Service
public class EopCenterSalesettingsServiceImpl implements EopCenterSalesettingsService {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(EopCenterSalesettingsServiceImpl.class);
	
	@Autowired
	SalesettingsService salesettingsService;
	@Autowired
	ProductsService productsService;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		salesettingsService.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public String insert(Salesettings record) {
		record.setAddTime((int) (Calendar.getInstance().getTimeInMillis() / 1000));
		StringBuilder sBuilder = new StringBuilder();
		String skuandamounts[] = record.getProductSpecs().split(";");
		sBuilder.append("a:").append(skuandamounts.length).append(":{");
		//1、校验套装中的sku是否存在
		for(int i= 0; i < skuandamounts.length;i++ ){
			//1.根据商品sku验证是否存在
			sBuilder.append("s:").append(skuandamounts[i].substring(0, skuandamounts[i].indexOf("--")).length()).append(":\"").append(skuandamounts[i].substring(0, skuandamounts[i].indexOf("--"))).append("\"").append(";");
			//对套装中单独的sku的价格进行判断
			if(skuandamounts[i].substring(skuandamounts[i].indexOf("--") + 1, skuandamounts[i].length()-1).length()==0){
				return "操作失败！sku编码:"+skuandamounts[i].substring(0, skuandamounts[i].indexOf("--"))+"价格没有设置";
			}
			sBuilder.append("s:").append(skuandamounts[i].substring(skuandamounts[i].indexOf("--") + 1, skuandamounts[i].length()-1).length()).append(":\"").append(skuandamounts[i].substring(skuandamounts[i].indexOf("--") + 2, skuandamounts[i].length())).append("\"").append(";");
			Products products = productsService.getBySku((skuandamounts[i].substring(0, skuandamounts[i].indexOf("--"))));
			if(products== null)
			{
				
				return "操作失败！sku编码:"+skuandamounts[i].substring(0, skuandamounts[i].indexOf("--"))+" 不存在";
			}
		}
		sBuilder.append("}");
		record.setProductSpecs(sBuilder.toString());
		record.setSiteId(1);
		salesettingsService.insert(record);
		return "操作成功";
	}
    public List<Products> getProductList(String productSpecs) {
        List<Products> list = new ArrayList<Products>();
        SerializedPhpParser serializedPhpParser = new SerializedPhpParser(productSpecs);
        Object result = serializedPhpParser.parse(); //通过php序列化后的字符串，获取java对象
        if (result != null) {
            @SuppressWarnings("rawtypes")
            Map myMap = (HashMap) result;
            @SuppressWarnings("rawtypes")
            Set keys = myMap.keySet();
            for (@SuppressWarnings("rawtypes")
            Iterator i = keys.iterator(); i.hasNext();) {
                String key = (String) i.next(); //获取sku编号
				BigDecimal price =new BigDecimal(0);
				try{
					price = new BigDecimal(Ustring.getString0(myMap.get(key).toString()));//外部促销价格
					String skutrim = key.trim();
					Products p = productsService.getBySku(skutrim);
					if(p==null) {
	                	log.info("套装管理列表查询,根据sku:"+skutrim+"查询不到数据");
	                	continue;
	                }
					p.setExternalSalePrice(price);
					list.add(p);
				}catch (RuntimeException e){
					log.error("套装管理列表查询价格有误，[productSpecs]:"+productSpecs+"；异常信息e："+e);
				}

            }
        }
        return list;
    }
	@Override
	public String updateByPrimaryKey(Salesettings record) {
		StringBuilder sBuilder = new StringBuilder();
		String skuandamounts[] = record.getProductSpecs().split(";");
		sBuilder.append("a:").append(skuandamounts.length).append(":{");
		//1、校验套装中的sku是否存在
		for(int i= 0; i < skuandamounts.length;i++ ){
			//1.根据商品sku验证是否存在
			sBuilder.append("s:").append(skuandamounts[i].substring(0, skuandamounts[i].indexOf("--")).length()).append(":\"").append(skuandamounts[i].substring(0, skuandamounts[i].indexOf("--"))).append("\"").append(";");
			//对套装中单独的sku的价格进行判断
			if(skuandamounts[i].substring(skuandamounts[i].indexOf("--") + 1, skuandamounts[i].length()-1).length()==0){
				return "操作失败！sku编码:"+skuandamounts[i].substring(0, skuandamounts[i].indexOf("--"))+"价格没有设置";
			}
			sBuilder.append("s:").append(skuandamounts[i].substring(skuandamounts[i].indexOf("--") + 1, skuandamounts[i].length()-1).length()).append(":\"").append(skuandamounts[i].substring(skuandamounts[i].indexOf("--") + 2, skuandamounts[i].length())).append("\"").append(";");
			Products products = productsService.getBySku((skuandamounts[i].substring(0, skuandamounts[i].indexOf("--"))));
			if(products== null)
			{
				
				return "操作失败！sku编码:"+skuandamounts[i].substring(0, skuandamounts[i].indexOf("--"))+" 不存在";
			}
		}
		sBuilder.append("}");
		record.setProductSpecs(sBuilder.toString());
		record.setSiteId(1);
		salesettingsService.updateByPrimaryKeySelective(record);
		return "操作成功";
	}

	@Override
	public JSONObject Listf(PagerInfo pager, Salesettings condition) {
		List<Salesettings> list = salesettingsService.Listf(condition
    			,pager.getStart(), pager.getPageSize());
    	int total = salesettingsService.getPagerCountS(condition);
        JSONArray res = new JSONArray();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
        for (Object o : list) {
        	Salesettings dto = (Salesettings)o;
            JSONObject json = new JSONObject();
            json.put("ConfigIdsMM",dto.getConfigIds());
            StringBuilder sbProductsSpec = new StringBuilder();
    		StringBuilder ConfigsName = new StringBuilder();
    		List<Products> products = getProductList(dto.getProductSpecs());
    		for(int i = 0 ; i < products.size(); i++){
    			sbProductsSpec.append(products.get(i).getSku()).append("--").append(products.get(i).getExternalSalePrice()+";");
    			 json.put("productSpecs",sbProductsSpec);
    		}
            
            if(dto.getConfigIds().contains(",")){
    			String configidsString[] = dto.getConfigIds().split(",");
    			for(int j = 0 ; j < configidsString.length; j++){
    				if("SNYG".equals(configidsString[j])){
    					dto.setConfigIds("苏宁统帅官方旗舰店");
    				}
    				if("SNQDZX".equals(configidsString[j])){
    					dto.setConfigIds("海尔渠道中心");
    				}
    				if("GMZX".equals(configidsString[j])){
    					dto.setConfigIds("国美海尔官方旗舰店");
    				}
    				if("GMZXTS".equals(configidsString[j])){
    					dto.setConfigIds("国美统帅官方旗舰店");
    				}
    				if("SNHEGQ".equals(configidsString[j])){
    					dto.setConfigIds("苏宁海尔官方旗舰店");
    				}
    				if("JDHEBXGQ".equals(configidsString[j])){
    					dto.setConfigIds("京东海尔集团冰箱官方旗舰店");
    				}
					if("JDHEGQ".equals(configidsString[j])){
						dto.setConfigIds("京东海尔官方旗舰店");
					}
    			}
    		}
    		else{ 
    			if("SNYG".equals(dto.getConfigIds())){
    				dto.setConfigIds("苏宁统帅官方旗舰店");
    			}
    			if("SNQDZX".equals(dto.getConfigIds())){
    				dto.setConfigIds("海尔渠道中心");
    			}
    			if("GMZX".equals(dto.getConfigIds())){
    				dto.setConfigIds("国美海尔官方旗舰店");
    			}
    			if("GMZXTS".equals(dto.getConfigIds())){
    				dto.setConfigIds("国美统帅官方旗舰店");
    			}
    			if("SNHEGQ".equals(dto.getConfigIds())){
    				dto.setConfigIds("苏宁海尔官方旗舰店");
    			}
    			if("JDHEBXGQ".equals(dto.getConfigIds())){
    				dto.setConfigIds("京东海尔集团冰箱官方旗舰店");
    			}
				if("JDHEGQ".equals(dto.getConfigIds())){
					dto.setConfigIds("京东海尔官方旗舰店");
				}
    		}
            json.put("id", dto.getId());
            json.put("siteId",dto.getSiteId());
            json.put("settingName",dto.getSettingName());
            json.put("externalSkus",dto.getExternalSkus());
           
            json.put("configIds",dto.getConfigIds());
            json.put("addTime",sdf.format(new Date(Long.valueOf(dto.getAddTime()+"000"))));
            json.put("type",dto.getType());
            json.put("startTime",sdf.format(new Date(Long.valueOf(dto.getStartTime()+"000"))));
            json.put("endTime",sdf.format(new Date(Long.valueOf(dto.getEndTime()+"000"))));
            json.put("effect",dto.getEffect());
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