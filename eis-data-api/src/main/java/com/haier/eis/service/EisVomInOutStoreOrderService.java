package com.haier.eis.service;


import java.util.List;

import com.haier.common.ServiceResult;
import com.haier.eis.model.VomInOutStoreOrder;

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
	VomInOutStoreOrder queryVomInOut(String orderNo);//根据网单号查询VOM是否返回啦出入库信息
	VomInOutStoreOrder queryGetStoreCode(String storageType,String busType,String orderNo);//根据出入库单号查询vomC码

    public VomInOutStoreOrder getByStockInfoByOrderNo(String orderNo);

	VomInOutStoreOrder 	queryVomInTenlibrary(String orderNo);
	
}
