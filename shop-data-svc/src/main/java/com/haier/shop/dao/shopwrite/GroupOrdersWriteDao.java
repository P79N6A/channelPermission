package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.GroupOrders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupOrdersWriteDao {

    Object insert(GroupOrders groupOrders);

    Object updateStatus(GroupOrders groupOrders);

    Object updateLesStatus(GroupOrders groupOrders);

    Object update(GroupOrders groupOrders);
}
