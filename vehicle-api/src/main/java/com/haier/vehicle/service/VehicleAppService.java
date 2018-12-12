package com.haier.vehicle.service;

import com.haier.common.ServiceResult;

import java.util.Map;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2017年09月04日} APP
 *
 * @author <a href="mailto: Zhangzengbao32@camelotchina.com">zzb</a>
 * @version 1.0 Copyright (c) 2016 北京柯莱特科技有限公司 交付部
 */
public interface VehicleAppService {
	// 获取送达方
	ServiceResult<String> getSendTo();

	ServiceResult<String> getBaseCode();

	ServiceResult<String> getCarCode(String baseCode, String sendCode);

	ServiceResult<String> getPayTo(String saleTo);

	/*
	 * sendto 是 送达方 soldto 是 售达方 invcode 是 物料号 basecode 是 基地
	 */
	ServiceResult<String> getproList(String soldto, String sendto,
			String invcode, String basecode);

	/**
	 * <p>
	 * Description: []
	 * </p>
	 * Created on 2017年09月12日} map.put("carCode", ""); map.put("orderNo", "");
	 * map.put("custCode", ""); map.put("centerCode", ""); map.put("gbid", "");
	 * map.put("gbName", ""); map.put("gbName", "");
	 *
	 * @author <a href="mailto: Zhangzengbao32@camelotchina.com">zzb</a>
	 * @version 1.0 Copyright (c) 2016 北京柯莱特科技有限公司 交付部
	 */
	ServiceResult<String> checkCar(Map<String, String> map);
}
