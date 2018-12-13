package com.haier.shop.dao.shopread;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.TradingFlow;

@Mapper
public interface TradingFlowReadDao {

    List<TradingFlow> getFindQueryOrderList(QueryZFBOrderParameter queryOrder);

	Integer getFindQueryOrderListCount(QueryZFBOrderParameter queryOrder);
}