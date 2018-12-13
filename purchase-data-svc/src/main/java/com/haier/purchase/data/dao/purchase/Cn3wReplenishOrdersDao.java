package com.haier.purchase.data.dao.purchase;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.haier.purchase.data.model.Cn3wReplenishOrders;

@Component
public interface Cn3wReplenishOrdersDao {

	Cn3wReplenishOrders getByLBX(@Param("storeCode") String storeCode);

}
