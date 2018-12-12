package com.haier.purchase.data.service;

import java.util.List;

import com.haier.purchase.data.model.RuntimeParametersVO;

public interface PurchaseRuntimeParametersService {

	public RuntimeParametersVO getRuntimeParameterByKey(String key);

	public List<RuntimeParametersVO> getRuntimeParameters();

	public void insertRuntimeParameters(RuntimeParametersVO vo);

	public void updateRuntimeParameters(RuntimeParametersVO vo);
}
