package com.haier.distribute.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.haier.common.ServiceResult;
import com.haier.distribute.service.DistributeCenterProductDataService;
import com.haier.distribute.service.DistributeCenterProductTimimgService;
import com.haier.distribute.util.MD5util;
@Configuration
@EnableScheduling
public class DistributeCenterProductTimingServiceImpl implements DistributeCenterProductTimimgService {

	@Autowired
	private DistributeCenterProductDataService distributeCenterProductDataService;

	@Override
	 //@Scheduled(cron="0/5 * *  * * ?")
	 public ServiceResult<Boolean> pushProduct() {
	     String urlPath = "receiveProduct";
	      String appKey = "push";
	      String orderInfo = distributeCenterProductDataService.ProductTiming(1);
	      try {
	        String orderInfo_encode = URLEncoder.encode(orderInfo, "UTF-8");
	        String smd5 = MD5util.Md5("appKey=" + appKey + "&orderInfo="  + orderInfo);
	        String param = "appKey=" + appKey + "&sign="+smd5+"&orderInfo="  + orderInfo_encode;
	        distributeCenterProductDataService.callHttpURL(appKey,urlPath, param);
	        } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	      }
	      return null;
	   }

	@Override
	//@Scheduled(cron = "*/10 * * * * ?")
	 public ServiceResult<Boolean> pushPrice() {
	     String urlPath = "receivePrice";
	      String appKey = "push";
	      String orderInfo = distributeCenterProductDataService.pushPrice(1);
	      try {
	        String orderInfo_encode = URLEncoder.encode(orderInfo, "UTF-8");
	        String smd5 = MD5util.Md5("appKey=" + appKey + "&orderInfo="  + orderInfo);
	        String param = "appKey=" + appKey + "&sign="+smd5+"&orderInfo="  + orderInfo_encode;
	        distributeCenterProductDataService.callHttpURL(appKey,urlPath, param);
	        } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	      }
	      return null;
	   }

	@Override
	//@Scheduled(cron = "*/10 * * * * ?")
	 public ServiceResult<Boolean> pushAvailable() {
	     String urlPath = "receiveAvailable";
	      String appKey = "push";
	      String orderInfo = distributeCenterProductDataService.pushAvailable();
	      try {
	        String orderInfo_encode = URLEncoder.encode(orderInfo, "UTF-8");
	        String smd5 = MD5util.Md5("appKey=" + appKey + "&orderInfo="  + orderInfo);
	        String param = "appKey=" + appKey + "&sign="+smd5+"&orderInfo="  + orderInfo_encode;
	        distributeCenterProductDataService.callHttpURL(appKey,urlPath, param);
	        } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	      }
	      return null;
	   }

}

