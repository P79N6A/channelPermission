package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderPriceSourceChannel;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderPriceSourceChannelReadDao {
	 List<OrderPriceSourceChannel> getOrderPriceSourceChannelList();
	/**
	 * 查询[订单价格管控来源渠道对应表]订单来源下拉列表
	 * @return
	 */
	List<Map<String, String>> getOrderPriceSourceList();

	/**
	 * 查询[订单价格管控来源渠道对应表]渠道和订单来源名称对应关系
	 * @return
	 */
	List<Map<String, String>> getOrderPriceSourceChannelMapList();

	/**
	 * 获取 渠道 下拉列表
	 * @return
	 */
	List<Map<String, String>> getGuaranteePriceChannel();

	/**
	 * 获取 订单来源 下拉列表
	 * @return
	 */
	List<Map<String, String>> getGuaranteePriceSource(@Param("channel") String channel);

	/**
	 * 根据订单来源获得渠道名称
	 * @param source
	 * @return
	 */
	OrderPriceSourceChannel getChannelByOrderSource(@Param("source") String source);
}
