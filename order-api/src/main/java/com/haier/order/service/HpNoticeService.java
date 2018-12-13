package com.haier.order.service;

import com.haier.common.ServiceResult;


public interface HpNoticeService {

    /**
     * 合并尾款通知hp发货接口
     */
    ServiceResult<Boolean> syncNoticeToHp();
}
