package com.haier.shop.service;


import com.haier.shop.model.HpNoticeQueues;

import java.util.List;


public interface HpNoticeQueuesService {
    List<HpNoticeQueues> getNoticeQueuesList(Integer max);

    int update(HpNoticeQueues hpNoticeQueues);
    
    //根据是否成功和网单id更新队列
    int updateQueuesBySuccessAndOrderProId( Integer orderProductId);
}
