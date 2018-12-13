package com.haier.svc.service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.RuntimeParametersVO;
import java.util.List;
import java.util.Map;

/**
 * 运行时服务 用于提供程序运行时修改参数、调用方法等动态调节功能
 * 
 * @Filename: RuntimeService.java
 * @Version: 1.0
 *
 */
public interface RuntimeService {

	public ServiceResult<RuntimeParametersVO> getRuntimeParameterByKey(String key);

	public ServiceResult<List<RuntimeParametersVO>> getRuntimeParameters();

	public void saveRuntimeParameters(Map<String, String> keyValueMap);
}
