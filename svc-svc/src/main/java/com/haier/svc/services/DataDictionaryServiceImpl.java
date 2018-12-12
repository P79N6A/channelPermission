package com.haier.svc.services;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.DataDictionary;
import com.haier.purchase.data.service.PurchaseDataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.svc.service.DataDictionaryService;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(DataDictionaryServiceImpl.class);
	@Autowired
	private PurchaseDataDictionaryService purchaseDataDictionaryService;
 
	/** 
	 * 数据字典分类检索
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public ServiceResult<List<DataDictionary>> getByValueSetId(
			Map<String, Object> params) {
		ServiceResult<List<DataDictionary>> result = new ServiceResult<List<DataDictionary>>();
		List<DataDictionary> list = null;
		try {
			list = purchaseDataDictionaryService.getByValueSetId(params);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("数据字典分类检索失败：", e);
		}
		result.setResult(list);
		return result;
	}
}
