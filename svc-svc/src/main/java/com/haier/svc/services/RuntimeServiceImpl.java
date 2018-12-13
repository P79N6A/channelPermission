package com.haier.svc.services;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.RuntimeParametersVO;
import com.haier.svc.model.RuntimeParametersModel;
import com.haier.svc.service.RuntimeService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuntimeServiceImpl implements RuntimeService {

	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(RuntimeServiceImpl.class);

	@Autowired
	private RuntimeParametersModel runtimeParametersModel;

	@Override
	public ServiceResult<RuntimeParametersVO> getRuntimeParameterByKey(String key) {
		ServiceResult<RuntimeParametersVO> result = new ServiceResult<RuntimeParametersVO>();
		try {
			// 提交订单
			result.setResult(runtimeParametersModel.getRuntimeParameterByKey(key));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获得Parameter失败：", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<RuntimeParametersVO>> getRuntimeParameters() {
		ServiceResult<List<RuntimeParametersVO>> result = new ServiceResult<List<RuntimeParametersVO>>();
		try {
			// 提交订单
			result.setResult(runtimeParametersModel.getRuntimeParameters());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获得Parameter失败：", e);
		}
		return result;
	}

	@Override
	public void saveRuntimeParameters(Map<String, String> keyValueMap) {
		runtimeParametersModel.saveRuntimeParameters(keyValueMap);
	}

}
