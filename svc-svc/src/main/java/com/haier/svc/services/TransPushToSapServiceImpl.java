package com.haier.svc.services;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.service.EisInterfaceDataLogService;
import com.haier.svc.service.TransPushToSapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TransPushToSapServiceImpl implements TransPushToSapService{

    @Autowired
    private EisInterfaceDataLogService eisInterfaceDataLogService;

    @Override
    public ServiceResult<List<EisInterfaceDataLog>> getTransPushToSapList(Map<String, Object> params) {
        ServiceResult<List<EisInterfaceDataLog>> result = new ServiceResult<List<EisInterfaceDataLog>>();
        try {
            List<EisInterfaceDataLog> list = eisInterfaceDataLogService
                    .getEisInterfaceList(params);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(eisInterfaceDataLogService.getEisInterfaceCNT(params));
            result.setPager(pi);
            result.setResult(list);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("通过电商库位码获取日日顺仓库List：" + e.getMessage());
        }
        return result;
    }

    @Override
    public List<EisInterfaceDataLog> getEisInterfaceDataList(Map<String, Object> params) {
        return eisInterfaceDataLogService.getEisInterfaceDataList(params);
    }
}
