package com.haier.system.service;


import com.haier.common.ServiceResult;
import com.haier.system.model.BaseErr;

import java.util.List;
import java.util.Map;

public interface SystemCenterBaseErrService {

	Integer adderr(BaseErr base);

	ServiceResult<List<BaseErr>> queryErr(Map<String, Object> map);
}
