package com.haier.stock.service;

import java.util.List;
import java.util.Map;

import com.haier.stock.model.JdStorage;

public interface StockJdStorageService {
	/**
	 * 
	 * @Title: getRrsWhByEstorgeOriginal
	 * @Description:对外暴露服务的dao层接口，默认通过estorge_id字段查询，可选查询字段有t2_default， 
	 *                                                                  rrs_wh_code
	 */
	public List<JdStorage> getAllRrsWhByEstorgeOriginal(
			Map<String, Object> params);

}
