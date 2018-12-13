package com.haier.vehicle.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.vehcile.MaterielInfoDTO;
import com.haier.purchase.data.service.vechile.PurchaseMaterielInfoService;
import com.haier.vehicle.service.VehicleAppService;
import com.haier.vehicle.service.VehicleLogInService;
import com.haier.vehicle.util.HttpUtils;

/**
 * APP
 *
 * @author zzb
 * @create 2017-09-04 9:33
 **/
@Service
public class VehicleAppServiceImpl implements VehicleAppService {

	@Autowired
	private VehicleLogInService vehicleLogInService;
	@Autowired
	private PurchaseMaterielInfoService materielInfoService;

	@Value("${rrsLocation}")
	private String rrsLocation;

	@Autowired
	private VehicleInterfaceLogServiceImpl vehicleInterfaceLogService;

	public String getUserId() {
		return "8800017840";
	}

	public String getLogIn() {
		return "true";
	}

	// 获取送达方
	@Override
	public ServiceResult<String> getSendTo() {
		ServiceResult<String> result1 = new ServiceResult<String>();
		try {
			String result = vehicleLogInService.getLoginMessage(getLogIn(),
					getUserId());
			Map map2;
			Map map3;
			String response;
			Map json = (Map) JSONObject.parse(result);
			if (json != null && "220".equals(json.get("code").toString())) {
				result1.setSuccess(false);
				result1.setMessage(json.get("message").toString());
				return result1;
			} else if(json.get("data") == null || json.get("code") == null){
				result1.setSuccess(false);
				result1.setMessage("获取送达方接口异常！");
				return result1;
			} else {
				String longfeiCATNAME = json.get("data").toString();
				Map longfeiCATNAMEJson = (Map) JSON.parse(longfeiCATNAME);
				String url = rrsLocation + "tmallSendTo"; // 365地址 POM文件配置
				JSONObject js = new JSONObject();
				js.put("appFlag", "true");
				js.put("userid", getUserId());
				js.put("lfSession",
						longfeiCATNAMEJson
								.get("lfSession")
								.toString()
								.substring(
										1,
										longfeiCATNAMEJson.get("lfSession")
												.toString().length() - 1));
				Map<String, String> map = new HashMap<String, String>();
				map.put("Content-type", "application/json");
				response = HttpUtils.sendRequest(url, map, js.toJSONString(),
						HttpUtils.HTTP_METHOD_POST, false, null);
				Map map1 = (Map) JSONObject.parse(response);
				if ("200".equals(map1.get("code").toString())) {
					map2 = (Map) JSONObject.parse(map1.get("data").toString());
					result1.setMessage(map2.get("success").toString());
					if ("true".equals(map2.get("success").toString())) {
						result1.setResult(map2.get("data").toString());
					} else {
						result1.setResult(map2.get("info").toString());
					}

				} else {
					result1.setSuccess(false);
					result1.setMessage(map1.get("message").toString());
					return result1;
				}
			}
		} catch (Exception e) {
			result1.setSuccess(false);
			result1.setMessage("false");
			result1.setResult("");
			result1.setMessage("获取送达方接口异常！");
			e.printStackTrace();
			return result1;
		}

		return result1;
	}

	// 获取基地
	@Override
	public ServiceResult<String> getBaseCode() {
		ServiceResult<String> result1 = new ServiceResult<String>();
		try {
			String result = vehicleLogInService.getLoginMessage(
					this.getLogIn(), this.getUserId());
			String response = null;
			Map map2 = null;
			Map json = (Map) JSONObject.parse(result);
			if (json !=null && "220".equals(json.get("code").toString())) {
				result1.setSuccess(false);
				result1.setMessage(json.get("message").toString());
				return result1;
			}  else if(json.get("data") == null || json.get("code") == null){
				result1.setSuccess(false);
				result1.setMessage("获取基地接口异常！");
				return result1;
			}else {
				String longfeiCATNAME = json.get("data").toString();
				Map longfeiCATNAMEJson = (Map) JSON.parse(longfeiCATNAME);
				String url = rrsLocation + "basecode"; // 365地址 POM文件配置
				JSONObject js = new JSONObject();
				js.put("appFlag", "true");
				js.put("userid", this.getUserId());
				js.put("lfSession",
						longfeiCATNAMEJson
								.get("lfSession")
								.toString()
								.substring(
										1,
										longfeiCATNAMEJson.get("lfSession")
												.toString().length() - 1));
				Map<String, String> map = new HashMap<String, String>();
				map.put("Content-type", "application/json");
				response = HttpUtils.sendRequest(url, map, js.toJSONString(),
						HttpUtils.HTTP_METHOD_POST, false, null);
				Map map1 = (Map) JSONObject.parse(response);
				if ("200".equals(map1.get("code").toString())) {
					map2 = (Map) JSONObject.parse(map1.get("data").toString());
					result1.setMessage(map2.get("success").toString());
					if ("true".equals(map2.get("success").toString())) {
						result1.setResult(map2.get("data").toString());
					} else {
						result1.setResult(map2.get("info").toString());
					}

				} else {
					result1.setSuccess(false);
					result1.setMessage(map1.get("message").toString());
					return result1;
				}
			}
		} catch (Exception e) {
			result1.setSuccess(false);
			result1.setMessage("false");
			result1.setResult("");
			result1.setMessage("获取基地接口异常！");
			e.printStackTrace();
			return result1;
		}
		return result1;
	}

	// 获取车辆信息
	/*
	 * userId 用户Id baseCode 基地编码 sendCode 送达方
	 */
	@Override
	public ServiceResult<String> getCarCode(String baseCode, String sendCode) {
		ServiceResult<String> result1 = new ServiceResult<String>();
		try {
			String result = vehicleLogInService.getLoginMessage(
					this.getLogIn(), this.getUserId());
			String response = null;
			Map map2 = null;
			Map json = (Map) JSONObject.parse(result);
			if ("220".equals(json.get("code").toString())) {
				result1.setSuccess(false);
				result1.setMessage(json.get("message").toString());
				return result1;
			} else {
				String longfeiCATNAME = json.get("data").toString();
				Map longfeiCATNAMEJson = (Map) JSON.parse(longfeiCATNAME);
				String url = rrsLocation + "carcode"; // 365地址 POM文件配置
				JSONObject js = new JSONObject();
				js.put("appFlag", "true");
				js.put("userid", this.getUserId());
				js.put("jdCode", baseCode);
				js.put("sendto", sendCode);
				js.put("lfSession",
						longfeiCATNAMEJson
								.get("lfSession")
								.toString()
								.substring(
										1,
										longfeiCATNAMEJson.get("lfSession")
												.toString().length() - 1));
				Map<String, String> map = new HashMap<String, String>();
				map.put("Content-type", "application/json");
				response = HttpUtils.sendRequest(url, map, js.toJSONString(),
						HttpUtils.HTTP_METHOD_POST, false, null);
				Map map1 = (Map) JSONObject.parse(response);
				if ("200".equals(map1.get("code").toString())) {
					map2 = (Map) JSONObject.parse(map1.get("data").toString());
					result1.setMessage(map2.get("success").toString());
					if ("true".equals(map2.get("success").toString())) {
						result1.setResult(map2.get("data").toString());
					} else {
						result1.setResult(map2.get("info").toString());
						result1.setSuccess(false);
					}

				} else {
					result1.setSuccess(false);
					result1.setMessage(map1.get("message").toString());
					return result1;
				}
			}
		} catch (Exception e) {
			result1.setSuccess(false);
			result1.setMessage("false");
			result1.setResult("");
			result1.setMessage("获取车辆接口异常！");
			e.printStackTrace();
			return result1;
		}
		return result1;
	}

	// 付款方
	/*
	 * saleTo 售达方
	 */
	@Override
	public ServiceResult<String> getPayTo(String saleTo) {
		ServiceResult<String> result1 = new ServiceResult<String>();
		try {
			String result = vehicleLogInService.getLoginMessage(
					this.getLogIn(), this.getUserId());
			String response = null;
			Map map2 = null;
			Map json = (Map) JSONObject.parse(result);
			if ("220".equals(json.get("code").toString())) {
				result1.setSuccess(false);
				result1.setMessage(json.get("message").toString());
				return result1;
			} else {
				String longfeiCATNAME = json.get("data").toString();
				Map longfeiCATNAMEJson = (Map) JSON.parse(longfeiCATNAME);
				String url = rrsLocation + "payto"; // 365地址 POM文件配置
				JSONObject js = new JSONObject();
				js.put("appFlag", "true");
				js.put("userid", this.getUserId());
				js.put("soldto", saleTo);
				js.put("lfSession",
						longfeiCATNAMEJson
								.get("lfSession")
								.toString()
								.substring(
										1,
										longfeiCATNAMEJson.get("lfSession")
												.toString().length() - 1));
				Map<String, String> map = new HashMap<String, String>();
				map.put("Content-type", "application/json");
				response = HttpUtils.sendRequest(url, map, js.toJSONString(),
						HttpUtils.HTTP_METHOD_POST, false, null);
				Map map1 = (Map) JSONObject.parse(response);
				map2 = (Map) JSONObject.parse(map1.get("data").toString());
				if ("220".equals(map1.get("code").toString())) {
					result1.setSuccess(false);
					result1.setMessage(map1.get("message").toString());
					return result1;
				} else {
					result1.setMessage(map2.get("success").toString());
					if ("true".equals(map2.get("success").toString())) {
						result1.setResult(map2.get("data").toString());
					} else {
						result1.setResult(map2.get("info").toString());
						result1.setSuccess(false);
					}
				}
			}
		} catch (Exception e) {
			result1.setSuccess(false);
			result1.setMessage("false");
			result1.setResult("");
			result1.setMessage("获取付款方接口异常！");
			e.printStackTrace();
			return result1;
		}
		return result1;
	}

	// 产品信息

	@Override
	public ServiceResult<String> getproList(String soldto, String sendto,
			String invcode, String basecode) {
		ServiceResult<String> result1 = new ServiceResult<String>();
		MaterielInfoDTO entity = new MaterielInfoDTO();
		entity.setMaterielCode(invcode);
		entity.setDeliveryToCode(sendto);
		entity.setBaseCode(basecode);
		List<MaterielInfoDTO> materielList = materielInfoService.getListByParams(entity);
		if(materielList == null || materielList.size()==0 || materielList.get(0).getMinqty() == null ||
				materielList.get(0).getMaxqty() == null){
			result1.setSuccess(false);
			result1.setMessage("请维护最大上单量和最小上单量");
			return result1;
		}else{
			JSONObject js1 = new JSONObject();
			js1.put("MINQTY", materielList.get(0).getMinqty());
			js1.put("MAXQTY", materielList.get(0).getMaxqty());
			result1.setResult(js1.toJSONString());
		}
//		try {
//			String result = vehicleLogInService.getLoginMessage(
//					this.getLogIn(), this.getUserId());
//			String response;
//			Map map2;
////			Map 
//			json = (Map) JSONObject.parse(result);
//			if ("220".equals(json.get("code").toString())) {
//				result1.setSuccess(false);
//				result1.setMessage(json.get("message").toString());
//				return result1;
//			} else {
//				String longfeiCATNAME = json.get("data").toString();
//				Map longfeiCATNAMEJson = (Map) JSON.parse(longfeiCATNAME);
//				String url = rrsLocation + "tmallSample"; // 365地址 POM文件配置
//				JSONObject js = new JSONObject();
//				js.put("appFlag", "true");
//				js.put("soldto", soldto);
//				js.put("sendto", sendto);
//				js.put("invcode", invcode);
//				js.put("basecode", basecode);
//				js.put("lfSession",
//						longfeiCATNAMEJson
//								.get("lfSession")
//								.toString()
//								.substring(
//										1,
//										longfeiCATNAMEJson.get("lfSession")
//												.toString().length() - 1));
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("Content-type", "application/json");
//				response = HttpUtils.sendRequest(url, map, js.toJSONString(),
//						HttpUtils.HTTP_METHOD_POST, false, null);
//				Map map1 = (Map) JSONObject.parse(response);
//				
//				if ("200".equals(map1.get("code").toString())) {
//					map2 = (Map) JSONObject.parse(map1.get("data").toString());
//					JSONArray ja = JSON
//							.parseArray(map2.get("items").toString());
//					Iterator<Object> it = ja.iterator();
//					while (it.hasNext()) {
//						JSONObject ob = (JSONObject) it.next();
//						if ("true".equals(ob.getString("success"))) {
//							JSONObject js1 = new JSONObject();
//							js1.put("MINQTY", ob.get("MINQTY"));
//							js1.put("MAXQTY", ob.get("MAXQTY"));
//							result1.setResult(js1.toJSONString());
//						} else {
//							result1.setSuccess(false);
//							result1.setMessage(ob.get("ErrorText").toString());
//						}
//
//					}
//				} else {
//					result1.setSuccess(false);
//					result1.setMessage(map1.get("message").toString());
//					return result1;
//				}
//			}
//		} catch (Exception e) {
//			result1.setSuccess(false);
//			result1.setMessage("false");
//			result1.setResult("");
//			result1.setMessage("获取样表接口异常！");
//			e.printStackTrace();
//			return result1;
//		}
		return result1;
	}

	@Override
	public ServiceResult<String> checkCar(Map<String, String> map) {
		ServiceResult<String> result1 = new ServiceResult<String>();
		try {
			String result = vehicleLogInService.getLoginMessage(
					this.getLogIn(), this.getUserId());
			String response;
			Map map2;
			Map json = (Map) JSONObject.parse(result);
			if ("220".equals(json.get("code").toString())) {
				result1.setSuccess(false);
				result1.setMessage(json.get("message").toString());
				return result1;
			} else {
				String longfeiCATNAME = json.get("data").toString();
				Map longfeiCATNAMEJson = (Map) JSON.parse(longfeiCATNAME);
				String url = rrsLocation + "tmallCheck"; // 365地址 POM文件配置
				JSONObject js = new JSONObject();
				js.put("carCode", map.get("carCode"));
				js.put("orderNo", map.get("orderNo"));
				js.put("custCode", map.get("custCode"));
				js.put("appFlag", "true");
				js.put("lfSession",
						longfeiCATNAMEJson
								.get("lfSession")
								.toString()
								.substring(
										1,
										longfeiCATNAMEJson.get("lfSession")
												.toString().length() - 1));
				JSON js1 = (JSON) JSON.parse(map.get("items"));
				js.put("goodstr", js1.toJSONString().replace("\"", "\'"));

				Map<String, String> map11 = new HashMap<String, String>();
				map11.put("Content-type", "application/json");
				response = HttpUtils.sendRequest(url, map11, js.toJSONString(),
						HttpUtils.HTTP_METHOD_POST, false, null);
				Map map1 = (Map) JSONObject.parse(response);
				if ("200".equals(map1.get("code").toString())) {
					map2 = (Map) JSONObject.parse(map1.get("data").toString());
					if (!"true".equals(map2.get("success").toString())) {
						result1.setSuccess(false);
						result1.setMessage(map2.get("Info").toString());
						result1.setResult(map2.get("items").toString());
						return result1;
					}
					result1.setSuccess(true);
					result1.setMessage(map2.get("Info").toString());
					result1.setResult(map2.get("items").toString());

				} else {
					result1.setSuccess(false);
					result1.setMessage(map1.get("message").toString());
					return result1;
				}
			}
		} catch (Exception e) {
			result1.setSuccess(false);
			result1.setMessage("false");
			result1.setResult("");
			result1.setMessage("装车检查接口异常！");
			e.printStackTrace();
			return result1;
		}
		return result1;
	}
}
