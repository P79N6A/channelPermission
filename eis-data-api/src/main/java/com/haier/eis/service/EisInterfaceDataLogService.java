package com.haier.eis.service;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;

//import com.haier.svc.bean.pop.OrderProductsNew;
//import com.haier.svc.bean.pop.hpDispatch.EisInterfaceDataLog;


/*
* 作者:张波
* 2017/12/25
*/


public interface EisInterfaceDataLogService {

	ServiceResult<EisInterfaceDataLog> insert(EisInterfaceDataLog log);

    Integer insertAndReturnId(EisInterfaceDataLog log);
}
