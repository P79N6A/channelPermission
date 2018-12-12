package com.haier.distribute.data.dao.distribute;

import com.haier.distribute.data.model.PopOrderProducts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderProductsDao extends BaseDao<PopOrderProducts> {
	int orderOpertion(@Param("orderStatus") String orderStatus, @Param("Id") String Id);

//	int orderOpertionToSure(@Param("orderStatus") String orderStatus,@Param("oId") String oId, @Param("Id")String Id);

	List<PopOrderProducts> checkStatus(@Param("id") String id);

	List<PopOrderProducts> checkOid(@Param("oid") String oid);

	PopOrderProducts getOneByOrderSn(String cOrderSn);

	List<PopOrderProducts> commodityList(@Param("orderId")Long orderId);
	
	List<PopOrderProducts> exportOrderProductsList(@Param("entity") PopOrderProducts entity);

	List<PopOrderProducts> commission_product_invoice(@Param("start") int start, @Param("rows") int rows,@Param("categoryId")String categoryId,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("policyYear")String policyYear,@Param("channelId")int channelId,@Param("channelType")int channelType,@Param("brandId")int brandId);
	List<PopOrderProducts> commission_product_invoice2(@Param("start") int start, @Param("rows") int rows,@Param("categoryId")String categoryId,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("policyYear")String policyYear,@Param("channelId")int channelId,@Param("channelType")int channelType,@Param("brandId")int brandId);
	List<PopOrderProducts> commission_product_invoice3(@Param("categoryId")String categoryId,@Param("policyYear")String policyYear,@Param("channelId")int channelId,@Param("channelType")int channelType,@Param("brandId")int brandId);

	List<PopOrderProducts> commission_product_invoice1(@Param("categoryId")String categoryId,@Param("policyYear")String policyYear,@Param("channelId")int channelId,@Param("channelType")int channelType,@Param("brandId")int brandId);

	int getPagerCountS(@Param("categoryId")String categoryId,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("policyYear")String policyYear,@Param("channelId")int channelId,@Param("channelType")int channelType,@Param("brandId")int brandId);

	Integer updateHPAllotNetPoint(PopOrderProducts orderProduct);


}
