package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderPriceSourceChannel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderPriceSourceChannelReadDao {
	 List<OrderPriceSourceChannel> getOrderPriceSourceChannelList();
}
