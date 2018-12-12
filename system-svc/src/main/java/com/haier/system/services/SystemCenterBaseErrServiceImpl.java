package com.haier.system.services;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.system.model.BaseErr;
import com.haier.system.service.BaseErrService;
import com.haier.system.service.SystemCenterBaseErrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SystemCenterBaseErrServiceImpl implements SystemCenterBaseErrService {
	
	@Autowired
	private BaseErrService baseErrService;
	
	@Override
	public Integer adderr(BaseErr base) {
		int count = baseErrService.adderr(base);
		return count;
	}

	@Override
	public ServiceResult<List<BaseErr>> queryErr(Map<String, Object> map) {
        ServiceResult<List<BaseErr>> result = new ServiceResult<List<BaseErr>>();
        try {
            result.setResult(baseErrService.queryErr(map));
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(baseErrService.getRowCnts(map));
            result.setPager(pi);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
		return result;
	}
}
