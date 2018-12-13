package com.haier.svc.api.controller;




import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.haier.common.ServiceResult;
import com.haier.distribute.data.model.PushData;
import com.haier.distribute.data.model.TsendInfoLog;
import com.haier.distribute.service.DistributeCenterPopProductService;
import com.haier.distribute.service.DistributeCenterProductDataService;
import com.haier.shop.model.ItemBase;
import com.haier.svc.api.controller.util.MD5util;
import com.haier.svc.api.controller.util.WebUtil;



@Controller
@RequestMapping(value="push/")
public class PushController {
	@Autowired
	private DistributeCenterPopProductService distributeCenterPopProductService;
	@Autowired
	private DistributeCenterProductDataService distributeCenterProductDataService;  
	@RequestMapping(value = { "receiveProduct" }, method = { RequestMethod.GET, RequestMethod.POST })
		public void receiveProduct(@RequestParam(required = false) String appKey, @RequestParam(required = false) String sign,
				@RequestParam(required = true) String orderInfo, HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			System.out.println("appKey:" + appKey);
			System.out.println("sign:" + sign);
			String sign_ok = MD5util.Md5("appKey=" + appKey + "&orderInfo="  + orderInfo);
			if(!sign_ok.equals(sign)) {
				WebUtil.setJsonRet(response, "{success:false,result:\"\",msg:\""+"签名错误"+"\"}",true);
				return;
			}
			JsonParser parser = new JsonParser();
			JsonArray jsonArray = parser.parse(orderInfo).getAsJsonArray();
	
	    }
	  @RequestMapping(value = { "receivePrice" }, method = { RequestMethod.GET, RequestMethod.POST })
		public void receivePrice(@RequestParam(required = false) String appKey, @RequestParam(required = false) String sign,
				@RequestParam(required = true) String orderInfo, HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			System.out.println("appKey:" + appKey);
			System.out.println("sign:" + sign);
			String sign_ok = MD5util.Md5("appKey=" + appKey + "&orderInfo="  + orderInfo);
			if(!sign_ok.equals(sign)) {
				WebUtil.setJsonRet(response, "{success:false,result:\"\",msg:\""+"签名错误"+"\"}",true);
				return;
			}
			JsonParser parser = new JsonParser();
			JsonArray jsonArray = parser.parse(orderInfo).getAsJsonArray();
	
	    }
	  @RequestMapping(value = { "receiveAvailable" }, method = { RequestMethod.GET, RequestMethod.POST })
		public void receiveAvailable(@RequestParam(required = false) String appKey, @RequestParam(required = false) String sign,
				@RequestParam(required = true) String orderInfo, HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			System.out.println("appKey:" + appKey);
			System.out.println("sign:" + sign);
			String sign_ok = MD5util.Md5("appKey=" + appKey + "&orderInfo="  + orderInfo);
			if(!sign_ok.equals(sign)) {
				WebUtil.setJsonRet(response, "{success:false,result:\"\",msg:\""+"签名错误"+"\"}",true);
				return;
			}
			JsonParser parser = new JsonParser();
			JsonArray jsonArray = parser.parse(orderInfo).getAsJsonArray();
	
	    }
		 @RequestMapping("/pushData")
		 public String pushData(){
			 return  "pop/distribute/pushData";
		 }
		 
		    //查询渠道
		    @RequestMapping(value = "/channelsList", method = RequestMethod.POST)
		    @ResponseBody
		    public JSONArray channelsList() {
		        return distributeCenterPopProductService.channelsList();
		    }
		    
		    
		    @RequestMapping(value = "/findPushData", method = RequestMethod.POST)
		    @ResponseBody
		    public void findPushData(HttpServletResponse response,String channelName) throws  IOException {
		    	if(channelName.equals("全部")) {
		    		channelName = "";
		    	}
		        List<PushData> list = distributeCenterPopProductService.findPushData(channelName);
		        response.setContentType("text/html;charset=utf-8");
		        Map<String, Object> retMap = new HashMap<String, Object>();
                Gson gson = new Gson();
                retMap.put("rows", list);
                retMap.put("total", list.size());
                response.addHeader("Content-type", "text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(retMap));
                response.getWriter().flush();
                response.getWriter().close();
		    }
		    
		    
			@ResponseBody
			@RequestMapping("channelCodeSelect")
			public ModelAndView channelCodeSelect(String channelCode){
				ModelAndView mv  = new ModelAndView();
				mv.addObject("bitMap", channelCode);
				mv.setViewName("pop/distribute/pushDataLog");
				return  mv;
			}
		    
		    @RequestMapping(value = "/findPushDataLog", method = RequestMethod.POST)
		    @ResponseBody
		    public void findPushDataLog(HttpServletResponse response,
		    		@RequestParam(required = false) String channelCode,
		    		@RequestParam(required = false) String infoType,
                    @RequestParam(required = false) String sendStatus,
                    @RequestParam(required = false) String sendType,
                    @RequestParam(required = false) String startDate,
                    @RequestParam(required = false) String endDate,
                    @RequestParam(required = false) Integer rows,
                    @RequestParam(required = false) Integer page
		    		
		    		) throws  IOException {
		         if (rows == null)
		                rows = 20;
		            if (page == null)
		                page = 1;
		        	if(infoType.equals("-1")) {
		        		infoType = "";
			    	}
		        	if(sendStatus.equals("-1")) {
		        		sendStatus = "";
			    	}
		        	if(sendType.equals("-1")) {
		        		sendType = "";
			    	}
		            Map<String, Object> params = new HashMap<String, Object>();
		            params.put("channelCode", channelCode);
		            params.put("infoType", infoType);
		            params.put("sendStatus", sendStatus);
		            params.put("sendType", sendType);
		            params.put("startDate", startDate);
		            params.put("endDate", endDate);
		            params.put("m", (page - 1) * rows);
		            params.put("n", rows);
				List<TsendInfoLog> list =distributeCenterPopProductService.channelCodeSelect(params);
		        response.setContentType("text/html;charset=utf-8");
		        Map<String, Object> retMap = new HashMap<String, Object>();
                Gson gson = new Gson();
                retMap.put("rows", list);
                retMap.put("total", list.size());
                response.addHeader("Content-type", "text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(retMap));
                response.getWriter().flush();
                response.getWriter().close();
		    }
			
			@ResponseBody
			@RequestMapping("pushProduct")
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

			 @ResponseBody
				@RequestMapping("pushPrice")
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

			 @ResponseBody
				@RequestMapping("pushAvailable")
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



