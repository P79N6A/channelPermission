package com.haier.shop.dao.shopread;


import com.haier.shop.model.OrderhpRejectionLogs;
import com.haier.shop.model.OrderhpRejectionLogsVO;
import com.haier.shop.model.ProductToIndustry;
import com.haier.shop.model.PutAway;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderhpRejectionLogsReadDao {


    OrderhpRejectionLogs selectByPrimaryKey(Integer id);

	List<PutAway> findbox();

	List<PutAway> findmach();

	List<PutAway> findnotinstock();
	String findoutnum();
	
	String quereHPRejection(@Param("orderReprisSn")String orderReprisSn,@Param("hpLesId")String hpLesId);//根据退货单号查询主键


	List<PutAway> findNotFit();

	List<PutAway> findNoChangeWarning();

	int notfitcount();

	List<PutAway> findNotInStockWarning();


	List<OrderhpRejectionLogsVO> quereEmptyOutSAP();//查询HP返回到CBS为不良品的数据 进行虚入虚出的操作
	
	List<OrderhpRejectionLogsVO> queryTheVirtualInto();//查询虚入
	
	List<OrderhpRejectionLogsVO> queryRealOutofData();//查询需要实入数据
	
	String quereHpLesId(String channelOrderSn);//根据退货单号查询38单号
	
	List<OrderhpRejectionLogs> queryVirtualEntryState(String channelOrderSn);//根据退货单号查询虚入虚出状态
}