package com.haier.eis.service;


import com.haier.common.ServiceResult;
import com.haier.eis.model.VomInOutStoreOrder;

import java.util.List;

/**
 * Created by 吴坤洋 2017-12-22
 */
public interface EisVomInOutStoreOrderService {
	 ServiceResult<VomInOutStoreOrder> insert(VomInOutStoreOrder vomInOutStoreOrder);

    List<VomInOutStoreOrder> getByProcessStatus( Integer processStatus, Integer delay);

    VomInOutStoreOrder getByRefNo(String refNo);

    Integer updateProcessStatus(Integer id, Integer processStatusUpdateTo, Integer processStatusUpdateFrom);

    Integer setDelay(Integer id,String msg);
    
    /**
     * 获取3W正品退仓
     * @param processStatus
     * @param delay
     * @return
     */
    List<VomInOutStoreOrder> getByProcessStatus3W(Integer processStatus, Integer delay);

	List<VomInOutStoreOrder> findInTime();
	int queryVomInOut(String orderNo);//根据网单号查询VOM是否返回啦出入库信息
}
