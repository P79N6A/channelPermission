package com.haier.order.services;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.order.service.StorageStreetsService;

@Service
public class StorageStreetsServiceImpl implements StorageStreetsService{
	
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(StorageStreetsServiceImpl.class);

	@Autowired
	com.haier.shop.service.StorageStreetsService storageStreetsService;
	
	@Override
	public ServiceResult<List<Map<String, Object>>> getCityIdByProvinceId(Integer provinceId) {
		ServiceResult<List<Map<String,Object>>> result = new ServiceResult<List<Map<String, Object>>>();
		try {
			if(provinceId==null){
				result.setSuccess(false);
				result.setMessage("参数省id为空");
				log.error("【StorageStreetsServiceImpl】【getCityIdByProvinceId】参数省id为空");
				return result;
			}
			List<Map<String,Object>> cityIdByProvinceId = storageStreetsService.getCityIdByProvinceId(provinceId);
			if(cityIdByProvinceId!=null && cityIdByProvinceId.size()>0){
				result.setSuccess(true);
				result.setResult(cityIdByProvinceId);
				return result;
			}else{
				result.setSuccess(false);
				result.setMessage("不存在的省id");
				log.error("【StorageStreetsServiceImpl】【getCityIdByProvinceId】不存在的省id,provinceId:"+provinceId);
				return result;
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("【StorageStreetsServiceImpl】【getCityIdByProvinceId】出现异常,e:"+e.getMessage());
			log.error("【StorageStreetsServiceImpl】【getCityIdByProvinceId】出现异常,e:"+e.getMessage());
			return result;
		}
	}

	@Override
	public ServiceResult<List<Map<String, Object>>> getCityByCityIds(String[] cityIds) {
		ServiceResult<List<Map<String,Object>>> result = new ServiceResult<List<Map<String, Object>>>();
		try {
			if(cityIds!=null && cityIds.length > 0){
				List<Map<String,Object>> cityByCityIds = storageStreetsService.getCityByCityIds(cityIds);
				result.setResult(cityByCityIds);
				result.setSuccess(true);
			}else{
				result.setSuccess(false);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("【StorageStreetsServiceImpl】【getCityByCityIds】出现异常,e:"+e.getMessage());
			log.error("【StorageStreetsServiceImpl】【getCityByCityIds】出现异常,e:"+e.getMessage());
		}
		return result;
	}

}
