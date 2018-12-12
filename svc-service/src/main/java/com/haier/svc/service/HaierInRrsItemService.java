package com.haier.svc.service;

import java.util.Map;

/**
 * Created by 李超 on 2018/3/13.
 */
public interface HaierInRrsItemService {

    /**
     * 查询crm订单或自动发货订单数量
     *
     * @param params
     * @return
     */
    public int getOrderNum(Map<String, Object> params);
}
