package com.haier.shop.dao.shopread;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.ZfbOrdersDetails;
@Mapper
public interface ZfbOrdersDetailsReadDao {
   
    ZfbOrdersDetails selectByPrimaryKey(Long id);

	List<ZfbOrdersDetails> getFindQueryOrderList(QueryZFBOrderParameter queryOrder);

	Integer getFindQueryOrderListCount(QueryZFBOrderParameter queryOrder);
	
	List<ZfbOrdersDetails> getReportFormList(QueryZFBOrderParameter queryOrder);

	Integer getReportFormListCount(QueryZFBOrderParameter queryOrder);

}