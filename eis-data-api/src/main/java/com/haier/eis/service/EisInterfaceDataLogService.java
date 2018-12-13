package com.haier.eis.service;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;

import java.util.List;
import java.util.Map;

//import com.haier.svc.bean.pop.OrderProductsNew;
//import com.haier.svc.bean.pop.hpDispatch.EisInterfaceDataLog;


/*
* 作者:张波
* 2017/12/25
*/


public interface EisInterfaceDataLogService {

	ServiceResult<EisInterfaceDataLog> insert(EisInterfaceDataLog log);

    Integer insertAndReturnId(EisInterfaceDataLog log);

    List<EisInterfaceDataLog> getEisInterfaceList(Map<String, Object> params);

    int getEisInterfaceCNT(Map<String, Object> params);

    List<EisInterfaceDataLog> getEisInterfaceDataList(Map<String, Object> params);
}
