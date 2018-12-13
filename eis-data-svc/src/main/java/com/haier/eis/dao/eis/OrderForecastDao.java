package com.haier.eis.dao.eis;

import com.haier.eis.model.OrderForecast;
import java.util.List;
import java.util.Map;

public interface OrderForecastDao {
    /**
     * 获取总记录数
     * @param params 
     * @param params
     * @return
     */
    Integer getCount(Map<String, Object> params);

    /**
     * 取出表中所有数据
     * @return
     */
    List<OrderForecast> getAll(Map<String, Object> params);
}
