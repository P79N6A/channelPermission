package com.haier.vehicle.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.vehcile.TmallCAMachineDTO;
import com.haier.purchase.data.service.PurchaseTmallCAMachineService;
import com.haier.vehicle.model.TmallCAMachine;
import com.haier.vehicle.service.TMallCAMachineService;
import com.haier.vehicle.service.VehicleLogInService;
import com.haier.vehicle.util.HttpUtils;

@Service
public class TMallCAMachineServiceImpl implements TMallCAMachineService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${tmallCAMachineUrl}")
	private String tmallCAMachineUrl;
	@Autowired
	private VehicleLogInService vehicleLogInService;
	@Autowired
	private PurchaseTmallCAMachineService purchaseTmallCAMachineService;

	private String userId = "8800017840";

	@Override
	public ServiceResult<String> getTmallCaMachine() {
		ServiceResult<String> result1 = new ServiceResult<String>();
		int pageSize = 200;
		try {
			purchaseTmallCAMachineService.updateDeleteMark();
			String result = vehicleLogInService.getLoginMessage("true", userId);
			Map map2;
			Map map3;
			String response;
			Map json = (Map) JSONObject.parse(result);
			if (json != null && "220".equals(json.get("code").toString())) {
				result1.setSuccess(false);
				result1.setMessage(json.get("message").toString());
			} else {
				String longfeiCATNAME = json.get("data").toString();
				Map longfeiCATNAMEJson = (Map) JSON.parse(longfeiCATNAME);
				String url = tmallCAMachineUrl; // 365地址 POM文件配置
				
				String lfSession = longfeiCATNAMEJson.get("lfSession").toString().substring(1,
						longfeiCATNAMEJson.get("lfSession").toString().length() - 1);
				
				Map map = sendRequest(1, pageSize, lfSession, "true", null);
				
				if("success".equals(map.get("result"))){
					List<TmallCAMachine> list = (List<TmallCAMachine>) map.get("items");
					int rowcount = Integer.valueOf(map.get("rowcount").toString());
					int totalPages = rowcount / pageSize + (rowcount % pageSize == 0 ? 0 : 1);
					List<TmallCAMachineDTO> list2 = new ArrayList<>();
					for(TmallCAMachine t : list){
						TmallCAMachineDTO dto = new TmallCAMachineDTO();
						BeanUtils.copyProperties(t, dto);
						list2.add(dto);
					}
					log.info("开始保存天猫CA套机接口数据……");
					purchaseTmallCAMachineService.save(list2);
					log.info("保存天猫CA套机接口数据成功");
					for(int i = 2; i <= totalPages; i++){
						map = sendRequest(i, pageSize, lfSession, "true", null);
						list = (List<TmallCAMachine>) map.get("items");
						list2 = new ArrayList<>();
						if(list != null && list.size() > 0){
							for(TmallCAMachine t : list){
								TmallCAMachineDTO dto = new TmallCAMachineDTO();
								BeanUtils.copyProperties(t, dto);
								list2.add(dto);
							}
						}
						log.info("开始保存天猫CA套机接口数据，本次共" + list2.size() + "……");
						purchaseTmallCAMachineService.save(list2);
						log.info("保存天猫CA套机接口数据成功");
					}
				}else{
					log.warn("获取天猫CA套机接口失败:" + map.get("msg"));
				}
			}
			purchaseTmallCAMachineService.deleteAllMarked();
		} catch (Exception e) {
			purchaseTmallCAMachineService.restoreDeleted();
			result1.setSuccess(false);
			result1.setMessage("false");
			result1.setResult("");
			e.printStackTrace();
		}
		return result1;
	}
	
	private Map sendRequest(int pageNo, int pageSize, String lfSession, String appFlag, String materialCode){
		Map result = new HashMap();
		log.info("开始查询天猫CA套机数据，页数：" + pageNo);
		try {
			Map map2;
			String response;
			String url = tmallCAMachineUrl; // 365地址 POM文件配置
			
			JSONObject js = new JSONObject();
			js.put("appFlag", appFlag);
			js.put("userid", userId);
			js.put("lfSession", lfSession);
			js.put("pageNo", pageNo);
			js.put("pageSize", pageSize);
			if(!StringUtils.isBlank(materialCode)){
				js.put("invcode", materialCode);
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("Content-type", "application/json");
			response = HttpUtils.sendRequest(url, map, js.toJSONString(), HttpUtils.HTTP_METHOD_POST, false, null);
			Map map1 = (Map) JSONObject.parse(response);
			if ("200".equals(map1.get("code").toString())) {
				map2 = (Map) JSONObject.parse(map1.get("data").toString());
				Long rowcount = Long.valueOf(map2.get("rowcount").toString());
				log.info("获取成功,总数量：" + rowcount);
				result.put("rowcount", rowcount);
				String items = map2.get("items").toString();
				Gson gson = new Gson();
				List<TmallCAMachine> list = gson.fromJson(items,  new TypeToken<List<TmallCAMachine>>(){}.getType());
				result.put("items", list);
				result.put("result", "success");
			} else {
				result.put("result", "failed");
				result.put("msg", map1.get("code").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "failed");
			result.put("msg", e.toString());
		}
		return result;
	}

	@Override
	public List<TmallCAMachineDTO> getByMaterialCode(String vbelnDn1) {
		return purchaseTmallCAMachineService.getByMaterialCode(vbelnDn1);
	}

	@Override
	public List<TmallCAMachine> getTmallCaMachine(String materialCode) {
		List<TmallCAMachine> resultList = new ArrayList<>();
		int pageSize = 200;
		try {
			purchaseTmallCAMachineService.updateDeleteMark();
			String result = vehicleLogInService.getLoginMessage("true", userId);
			Map map2;
			Map map3;
			String response;
			Map json = (Map) JSONObject.parse(result);
			if (json != null && "220".equals(json.get("code").toString())) {
				
			} else {
				String longfeiCATNAME = json.get("data").toString();
				Map longfeiCATNAMEJson = (Map) JSON.parse(longfeiCATNAME);
				String url = tmallCAMachineUrl; // 365地址 POM文件配置
				
				String lfSession = longfeiCATNAMEJson.get("lfSession").toString().substring(1,
						longfeiCATNAMEJson.get("lfSession").toString().length() - 1);
				
				Map map = sendRequest(1, pageSize, lfSession, "true", materialCode);
				
				if("success".equals(map.get("result"))){
					resultList = (List<TmallCAMachine>) map.get("items");
				}else{
					log.warn("获取天猫CA套机接口失败:" + map.get("msg"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

}

