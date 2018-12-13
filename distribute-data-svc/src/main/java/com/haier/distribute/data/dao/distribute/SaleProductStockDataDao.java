package com.haier.distribute.data.dao.distribute;

import java.util.List;
import java.util.Map;

public interface SaleProductStockDataDao {
    //查询可售商品表数据
    public List<Map<String,Object>> selectBystatus();
}
