package com.haier.svc.model;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.RuntimeParametersVO;
import com.haier.purchase.data.service.PurchaseRuntimeParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("runtimeParametersModel")
public class RuntimeParametersModel {
	@Autowired
	private PurchaseRuntimeParametersService purchaseRuntimeParametersService;


	public RuntimeParametersVO getRuntimeParameterByKey(String key) {
		return purchaseRuntimeParametersService.getRuntimeParameterByKey(key);
	}

	public void updateRuntimeWpOrderId(RuntimeParametersVO vo) {
		purchaseRuntimeParametersService.updateRuntimeWpOrderId(vo);
	}

	public List<RuntimeParametersVO> getRuntimeParameters() {
		return purchaseRuntimeParametersService.getRuntimeParameters();
	}

	public void saveRuntimeParameters(Map<String, String> keyValueMap) {
		for (String key : keyValueMap.keySet()) {
			RuntimeParametersVO vo = purchaseRuntimeParametersService.getRuntimeParameterByKey(key);
			RuntimeParametersVO newVo = new RuntimeParametersVO();
			newVo.setKey(key);
			newVo.setValue(keyValueMap.get(key));
			if (vo != null) {
				purchaseRuntimeParametersService.updateRuntimeParameters(newVo);
			} else {
				purchaseRuntimeParametersService.insertRuntimeParameters(newVo);
			}
		}

	}
}
