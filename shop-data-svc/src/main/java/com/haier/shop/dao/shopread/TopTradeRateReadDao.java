package com.haier.shop.dao.shopread;



import com.haier.shop.model.QueryTopTradeRateParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by LuJun on 2017/11/6.
 */
@Mapper
public interface TopTradeRateReadDao {

    List<QueryTopTradeRateParameter> getAllData(QueryTopTradeRateParameter queryTopTradeRateParameter);


    int getCount(QueryTopTradeRateParameter queryTopTradeRateParameter);

}
