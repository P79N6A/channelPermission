package com.haier.shop.dao.shopread;

import com.haier.shop.model.GroupOrders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupOrdersReadDao {

    GroupOrders getByDepositOrderId(Integer depositOrderId);

    List<GroupOrders> getGroupOrdersQueues(@Param("topX") Integer topX);

    public GroupOrders getByTailOrderId(Integer tailOrderId);

    List<GroupOrders> getListByDepositOrderProductId(Integer depositOrderProductId);

    GroupOrders getByDepositOrderProductId(Integer depositOrderProductId);

}
