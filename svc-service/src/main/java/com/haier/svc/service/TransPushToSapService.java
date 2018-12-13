package com.haier.svc.service;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;

import java.util.List;
import java.util.Map;

public interface TransPushToSapService {
    ServiceResult<List<EisInterfaceDataLog>> getTransPushToSapList(Map<String, Object> params);

    List<EisInterfaceDataLog> getEisInterfaceDataList(Map<String, Object> params);
}
