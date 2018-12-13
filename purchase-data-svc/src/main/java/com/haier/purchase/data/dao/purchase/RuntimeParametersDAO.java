package com.haier.purchase.data.dao.purchase;

import java.util.List;

import com.haier.purchase.data.model.RuntimeParametersVO;

public interface RuntimeParametersDAO {

	public RuntimeParametersVO getRuntimeParameterByKey(String key);

	public List<RuntimeParametersVO> getRuntimeParameters();

	public void insertRuntimeParameters(RuntimeParametersVO vo);

	public void updateRuntimeParameters(RuntimeParametersVO vo);

	public void updateRuntimeWpOrderId(RuntimeParametersVO vo);

}
