package com.haier.vehicle.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.vehcile.MaterielInfoDTO;
import com.haier.purchase.data.service.vechile.PurchaseMaterielInfoService;
import com.haier.purchase.data.service.vechile.PurchaseVehicleInterfaceLogService;
import com.haier.vehicle.service.VehicleLogInService;
import com.haier.vehicle.service.VehicleTimingService;
import com.haier.vehicle.util.HttpUtils;

@Service
public class VehicleTimingServiceImpl implements VehicleTimingService{

	@Autowired
	private PurchaseVehicleInterfaceLogService vehicleInterfaceLogService;
	@Autowired
	private VehicleLogInService vehicleLogInService;
	@Autowired
	private PurchaseMaterielInfoService materielInfoDao;
	@Value("${materielPath}")
	private String materielPath;

	public String getUserId() {
		return "8800017840";
	}

	public String getLogIn() {
		return "true";
	}
	public void vehicleTest(){
		System.out.println("vehicleTest，this time：" + new Date());
	}
	
	@Override
	public void gainMateriel() {
		ServiceResult<String> result1 = new ServiceResult<String>();
		// 总条数
		int rowcount = 0;
		// 查询次数
		int getcount = 0;
		String result = vehicleLogInService.getLoginMessage(this.getLogIn(),
				this.getUserId());
		
		String response = null;
		Map map2 = null;
		Map json = (Map) JSONObject.parse(result);
		if (json != null && "220".equals(json.get("code").toString())) {
			result1.setSuccess(false);
			result1.setMessage(json.get("message").toString());
		} else {
			String longfeiCATNAME = json.get("data").toString();
			Map longfeiCATNAMEJson = (Map) JSON.parse(longfeiCATNAME);
			String url = materielPath; // 365地址 POM文件配置
			JSONObject js = new JSONObject();

			js.put("userid", getUserId());
			js.put("lfSession",
					longfeiCATNAMEJson
							.get("lfSession")
							.toString()
							.substring(
									1,
									longfeiCATNAMEJson.get("lfSession")
											.toString().length() - 1));
			// 插入总条数
			int count = 0;
			for (int k = 0; k < 2; k++) {
				if (getcount == 0) {
					js.put("appFlag", "true");
					js.put("pageNo", 1);
					js.put("pageSize", 1000);
					Map<String, String> map = new HashMap<String, String>();
					map.put("Content-type", "application/json");
					response = HttpUtils.sendRequest(url, map,
							js.toJSONString(), HttpUtils.HTTP_METHOD_POST,
							false, null);
					Map map1 = (Map) JSONObject.parse(response);
					if ("200".equals(map1.get("code").toString())) {
						map2 = (Map) JSONObject.parse(map1.get("data").toString());
						List<JSONObject> list = (List<JSONObject>) JSONObject
								.parse(JSON.toJSONString(map2.get("items")));
						List<MaterielInfoDTO> miList = getMaterielInfoList(list);
						if (miList != null && miList.size() > 0) {
							// 只在第一次清空表
							materielInfoDao.truncateMaterielInfo();
							count += materielInfoDao.batchAdd(miList);
						}
						// for(int q = 0;q<itemList.size();q++){
						// itemList.get(q).getWLCODE();
						// mi.setMaterielCode(item.getWLCODE());
						// mi.setMaterielName(item.getWLNAME());
						// mi.setStatus(1);
						// mi.setProductGroupCode(productGroupCode);
						// }
						rowcount = Integer.parseInt(map2.get("rowcount")
								.toString());

					} else {
						result1.setSuccess(false);
						result1.setMessage(map1.get("errorMsg").toString());
					}
					getcount++;
//					 System.out.println("总条数："+rowcount);
				} else {
					js.put("appFlag", "true");
					int pageTottle = 0;
					if (rowcount % 1000 == 0) {
						pageTottle = rowcount / 1000;
					} else {
						pageTottle = rowcount / 1000 + 1;
					}
					// System.out.println("pageTottle = "+pageTottle);
					for (int i = 1; i < pageTottle; i++) {
						Map<String, String> map = new HashMap<String, String>();
						js.put("pageNo", i + 1);
						map.put("Content-type", "application/json");
						response = HttpUtils.sendRequest(url, map,
								js.toJSONString(), HttpUtils.HTTP_METHOD_POST,
								false, null);
						Map map1 = (Map) JSONObject.parse(response);
						if ("200".equals(map1.get("code").toString())) {
							map2 = (Map) JSONObject.parse(map1.get("data")
									.toString());
							List<JSONObject> list = (List<JSONObject>) JSONObject
									.parse(JSON.toJSONString(map2.get("items")));
							List<MaterielInfoDTO> miList = getMaterielInfoList(list);
							if (miList != null && miList.size() > 0) {
								count += materielInfoDao.batchAdd(miList);
							}
						} else {
							result1.setSuccess(false);
							result1.setMessage(map1.get("errorMsg").toString());
							vehicleInterfaceLogService.insertLog(
									"365整车物料样表数据获取", map1.get("errorMsg")
											.toString());
						}
						getcount++;
						// System.out.println("查询次数："+getcount);
					}
				}
			}
			vehicleInterfaceLogService.insertLog("365整车物料样表数据获取", "获取总条数："
					+ rowcount + "插入总条数：" + count);
			// System.out.println("获取总条数："+rowcount);
			// System.out.println("插入总条数："+count);
		}
	}
	
	private List<MaterielInfoDTO> getMaterielInfoList(List<JSONObject> list) {
		List<MaterielInfoDTO> miList = new ArrayList<MaterielInfoDTO>();
		MaterielInfoDTO miDto = null;
		int i = 0;
		for (JSONObject obj : list) {
			miDto = new MaterielInfoDTO();
			if (obj.get("WLCODE") != null) {
				miDto.setMaterielCode(obj.get("WLCODE").toString());
			}
			if (obj.get("WLNAME") != null) {
				miDto.setMaterielName(obj.get("WLNAME").toString());
			}
			if (obj.getString("INVSORT") != null) {
				miDto.setProductGroupCode(obj.getString("INVSORT").toString());
			}
			if (obj.getString("INVNAME") != null) {
				miDto.setProductGroupName(obj.getString("INVNAME").toString());
			}
			if (obj.getString("BAND") != null) {
				miDto.setBrandCode(obj.getString("BAND").toString());
			}
			if (obj.getString("BANDNAME") != null) {
				miDto.setBrandName(obj.getString("BANDNAME").toString());
			}
			if (obj.getString("GBVOLUME") != null
					&& !"".equals(obj.getString("GBVOLUME"))) {
				miDto.setVolume(new BigDecimal(Double.valueOf(obj.getString(
						"GBVOLUME").toString()) / 1000000).setScale(6,
						BigDecimal.ROUND_HALF_UP).doubleValue());
			}
			if (obj.getString("BASECODE") != null) {
				miDto.setBaseCode(obj.getString("BASECODE").toString());
			}
			if (obj.getString("BASENAME") != null) {
				miDto.setBaseName(obj.getString("BASENAME").toString());
			}
			if (obj.getString("TMCS2S_SENDTO") != null) {
				miDto.setDeliveryToCode(obj.getString("TMCS2S_SENDTO")
						.toString());
			}
			if (obj.getString("MAXQTY") != null
					&& !"".equals(obj.getString("MAXQTY"))) {
				miDto.setMaxqty(Integer.parseInt(obj.getString("MAXQTY")
						.toString()));
			}
			if (obj.getString("MINQTY") != null
					&& !"".equals(obj.getString("MINQTY"))) {
				miDto.setMinqty(Integer.parseInt(obj.getString("MINQTY")
						.toString()));
			}
			// TODO
			miDto.setActiveFlag("1");
			miDto.setStatus(1);
			miDto.setCreatedBy("job");
			miDto.setLastUpdBy("job");
			miList.add(miDto);
			i++;
		}
		return miList;
	}

	
}
