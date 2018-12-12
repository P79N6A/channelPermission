package com.haier.shop.service;

import com.haier.common.ServiceResult;

/*
*
* 作者:张波
* 2017/12/20
* */
public interface ExternalOrdersService {
    /**
     * 正向、逆向数据推送给结算中心
     * @param foreignKey 记录接口日志的数据id,一般记录订单或网单id
     * @param content 调用接口数据
     * @return
     */
 /*   ServiceResult<String> sendForwardReverseToAccount(String foreignKey, String content);*/
    /**
     * 网单发货后，更新发货标志
     * @param orderId
     * @return
     */
    Integer updateAtferShipped(Integer orderId);
}
