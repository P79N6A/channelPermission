package com.haier.shop.service;

import com.haier.shop.model.ExternalOrders;
import com.haier.shop.model.ExternalOrdersVo;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据原订单号获取外部订单对象
     * @param sourceOrderSn
     * @return
     */
    public ExternalOrders getExternalOrdersBySourceOrderSn(String sourceOrderSn);

    /**
     * 分页查询错误来源订单
     * @param params
     * @return
     */
    List<ExternalOrdersVo> getExternalOrdersList(Map<String,Object> params);

    /**
     * 添加
     * @param extOrder
     * @return
     */
    int insertExternalOrdersInfo(ExternalOrders extOrder);

    /**
     * 修改
     * @param sourceOrderSn
     * @param orderState
     * @param errorLog
     * @return
     */
    Integer updateShopOrdersInfo(String sourceOrderSn, Integer orderState, String errorLog);

    /**
     * 查询总条数
     * @param params
     * @return
     */
    Integer findExternalOrdersCNT(Map<String,Object> params);
}
