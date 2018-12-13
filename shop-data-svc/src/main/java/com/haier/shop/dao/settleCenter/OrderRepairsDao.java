package com.haier.shop.dao.settleCenter;

import java.util.List;

import com.haier.shop.model.OrderRepairs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderRepairsDao {

    List<OrderRepairs> getByOrderProductId(Integer orderProductId);

    List<OrderRepairs> getOrderRepairDetailByOrderProductId(Integer orderProductId);

}
