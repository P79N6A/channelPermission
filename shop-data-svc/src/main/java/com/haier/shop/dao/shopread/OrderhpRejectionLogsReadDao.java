package com.haier.shop.dao.shopread;


import com.haier.shop.model.OrderhpRejectionLogs;
import com.haier.shop.model.ProductToIndustry;
import com.haier.shop.model.PutAway;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderhpRejectionLogsReadDao {


    OrderhpRejectionLogs selectByPrimaryKey(Integer id);

	List<PutAway> findbox();

	List<PutAway> findmach();

	List<PutAway> findnotinstock();
	String findoutnum();
	
	String quereHPRejection(String id);//根据退货单号查询主键


	List<PutAway> findNotFit();

	List<PutAway> findNoChangeWarning();

	int notfitcount();

	List<PutAway> findNotInStockWarning();


	List<OrderhpRejectionLogs> quereEmptyOutSAP();//查询HP返回到CBS为不良品的数据 进行虚入虚出的操作

}