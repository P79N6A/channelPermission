package com.haier.shop.service;



import com.haier.shop.model.QueryTopTradeRateParameter;

import java.util.List;

/**
 * Created by LuJun on 2017/11/6.
 */
public interface ShopTopTradeRateService {

    List<QueryTopTradeRateParameter> getAllData(QueryTopTradeRateParameter queryTopTradeRateParameter);


    int getCount(QueryTopTradeRateParameter queryTopTradeRateParameter);

}
