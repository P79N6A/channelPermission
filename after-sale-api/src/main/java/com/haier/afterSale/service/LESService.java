package com.haier.afterSale.service;

import java.util.List;

import com.haier.common.ServiceResult;
import com.haier.shop.model.QueryLesStockOutType;
import com.haier.shop.model.QueryLesStockToCbs;
import com.haier.shop.model.QueryPayTimeToLes;

public interface LESService {

    /**
     * LES开提单数据接口
     * @param foreignKey 记录接口日志的数据id,一般记录订单或网单id或网单号
     * @param content 调用接口数据
     * @return
     */
    ServiceResult<String> orderToLes(String foreignKey, String content);

    /**
     * 给物流提供付款时间和尾款时间
     * @param foreignKey 记录接口日志的数据id,一般记录订单或网单id或网单号
     * @param content 调用接口数据
     * @return
     */
    ServiceResult<Boolean> paytimeToLes(String foreignKey, List<QueryPayTimeToLes> param);
    
    /**
     * 查询Les库存，供CBS库存对账使用
     * @param queryLesStockToCbs 入参
     * @return
     */
    ServiceResult<QueryLesStockOutType> queryLesStock(QueryLesStockToCbs queryLesStockToCbs);

}
