package com.haier.distribute.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.distribute.data.model.ProductTiming;
import com.haier.distribute.data.model.Regions;
import com.haier.distribute.data.service.DistributeProductDataService;
import com.haier.distribute.service.DistributeCenterProductDataService;
@Service
public class DistributeCenterProductDataServiceImpl implements DistributeCenterProductDataService {

	@Autowired
	private DistributeProductDataService distributeProductTimingService;
	@Override
	public String ProductTiming(int channelId){
		List<ProductTiming> list = distributeProductTimingService.pushProductInfo(channelId);
		JSONArray json = new JSONArray();
		 String sale = "";
		 String delete = "";
         for(ProductTiming pt : list){
        	 JSONObject jo = new JSONObject();
             jo.put("id", pt.getId());
             jo.put("sku", pt.getSku());
             jo.put("productName", pt.getProductName());
             jo.put("productTitle", pt.getProductTitle());
             jo.put("packagePrice", pt.getPackagePrice());
             jo.put("brandName", pt.getBrandName());
             jo.put("saleGuidePrice", pt.getSaleGuidePrice());
             if(pt.getOnSale()==1) {
            	 sale = "上架";
             }
             else {
            	 sale = "下架";
             }
             jo.put("onSale", sale);
             jo.put("typeName", pt.getTypeName());
             jo.put("productDetail", pt.getProductDetail());
             jo.put("productDetail2", pt.getProductDetail2());
             jo.put("cateName", pt.getCateName());
             if(pt.getIsDelete()==1) {
            	 delete = "已删除";
             }
             else {
            	 delete = "未删除";
             }
             jo.put("isDelete", delete);
             jo.put("packageId", pt.getPackageId());
             jo.put("salePrice", pt.getSku());  
            json.put(jo);
         } 
         System.out.println(json.toString());
        return json.toString();
	}
	
	
	
	@Override
	public String pushPrice(int channelId){
		List<ProductTiming> list = distributeProductTimingService.pushProductInfo(channelId);
		JSONArray json = new JSONArray();

         for(ProductTiming pt : list){
        	 JSONObject jo = new JSONObject();
             jo.put("id", pt.getId());
             jo.put("sku", pt.getSku());
             jo.put("price", pt.getPrice());
             jo.put("priceStartTime", pt.getPriceStartTime());
             jo.put("priceEndTime", pt.getPriceEndTime());
            json.put(jo);
         } 
         System.out.println(json.toString());
        return json.toString();
	}



	
	
	@Override
	public String pushAvailable(){
		List<Regions> list = distributeProductTimingService.pushAvailable();
		JSONArray json = new JSONArray();
		 String activeFlag = "";
         for(Regions regions : list){
        	 JSONObject jo = new JSONObject();
             if(regions.getActiveFlag()==1) {
            	 activeFlag = "有效";
             }
             else {
            	 activeFlag = "无效";
             }
             jo.put("activeFlag", activeFlag);
             jo.put("province", regions.getProvince());
             jo.put("city", regions.getCity());
             jo.put("region", regions.getRegion());
             jo.put("code", regions.getCode());
            json.put(jo);
         } 
         System.out.println(json.toString());
        return json.toString();
	}
	@Override
	 public void callHttpURL(String appKey,String suffixUrl, String param) {
	      String urlPath = new String( "http://localhost:8081/"+appKey+"/"+suffixUrl);
	      try {
	        // 建立连接
	        URL url = new URL(urlPath);
	        HttpURLConnection httpConn = (HttpURLConnection) url
	            .openConnection();
	        // 设置参数
	        httpConn.setDoOutput(true); // 需要输出
	        httpConn.setDoInput(true); // 需要输入
	        httpConn.setUseCaches(false); // 不允许缓存
	        httpConn.setRequestMethod("POST"); // 设置POST方式连接
	        // 设置请求属性
	        httpConn.setRequestProperty("Content-Type",
	            "application/x-www-form-urlencoded");
	        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
	        httpConn.setRequestProperty("Charset", "UTF-8");
	        // 连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
	        httpConn.connect();
	        // 建立输入流，向指向的URL传入参数
	        DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
	        dos.writeBytes(param);
	        dos.flush();
	        dos.close();
	        // 获得响应状态
	        int resultCode = httpConn.getResponseCode();
	        if (HttpURLConnection.HTTP_OK == resultCode) {
	          StringBuffer sb = new StringBuffer();
	          String readLine = new String();
	          BufferedReader responseReader = new BufferedReader(
	              new InputStreamReader(httpConn.getInputStream(),
	                  "UTF-8"));
	          while ((readLine = responseReader.readLine()) != null) {
	            sb.append(readLine).append("\n");
	          }
	          responseReader.close();

	        }
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    }
}
