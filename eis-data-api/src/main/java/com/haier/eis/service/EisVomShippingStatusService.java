package com.haier.eis.service;

import java.util.List;

import com.haier.eis.model.VomShippingStatus;

/*
* 作者:张波
* 2017/12/22
* */

public interface EisVomShippingStatusService {
    List<VomShippingStatus> getByProcessStatus( Integer processStatus);
    Integer updateProcessStatus( Integer id, Integer processStatusUpdateTo, Integer processStatusUpdateFrom, String msg);
    
    /**
     * 获取3W正品
     * @param processStatus
     * @return
     */
    List<VomShippingStatus> getByProcessStatus3W(Integer processStatus);
    Integer insert(VomShippingStatus shippingStatus);

    /**
     * 根据网单号查询是否已下发顺丰
     * @param orderNo
     * @return
     */
    int queryCountByOrderNo(String orderNo);
}
