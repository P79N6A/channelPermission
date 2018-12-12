package com.haier.vehicle.services;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haier.vehicle.service.VehicleLogInService;
import com.haier.vehicle.util.HttpUtils;

/**
 * 获取接口登陆信息
 *
 * @author  zzb
 * @create 2017-09-04 11:19
 **/
@Service
public class VehicleLogInServiceImpl implements VehicleLogInService{
	
	@Value("${rrsLocation}")
    private String rrsLocation;

    @Override
    public String getLoginMessage(String appFlag ,String userId) {
        String url = rrsLocation+"login"; //365地址   POM文件配置
        JSONObject js = new JSONObject();

        js.put("appFlag", appFlag);
        js.put("userid", userId);
        Map<String,String> map = new HashMap<String, String>();
        map.put("Content-type","application/json");
        String response = HttpUtils.sendRequest(url, map,js.toJSONString() ,
                HttpUtils.HTTP_METHOD_POST, false, null);
        return response;
    }
}
