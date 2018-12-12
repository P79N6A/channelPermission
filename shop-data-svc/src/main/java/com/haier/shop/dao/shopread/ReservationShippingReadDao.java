package com.haier.shop.dao.shopread;

import com.haier.shop.model.ReservationShipping;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationShippingReadDao {

    ReservationShipping get(Integer orderId);
}
