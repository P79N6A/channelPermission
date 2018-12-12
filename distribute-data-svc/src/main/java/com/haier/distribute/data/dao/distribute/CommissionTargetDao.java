package com.haier.distribute.data.dao.distribute;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.distribute.data.model.CommissionTarget;


public interface CommissionTargetDao {
	 int deleteByPrimaryKey(Integer id);

	    int insert(CommissionTarget record);

	    int insertSelective(CommissionTarget record);

	    CommissionTarget selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(CommissionTarget record);

	    int updateByPrimaryKey(CommissionTarget record);

		List<CommissionTarget> selectCommission_productListf(@Param("entity")CommissionTarget entity,@Param("start") int start, @Param("rows") int rows);
		int getPagerCountS(@Param("entity")CommissionTarget entity);

		List<CommissionTarget> selectCommission_target_invoice(@Param("start") int start, @Param("rows") int rows,
				@Param("categoryId")String categoryId, @Param(" startTime")String startTime, @Param("endTime")String endTime,
				@Param("policyYear")String policyYear, @Param("channelId")int channelId, @Param("channelType")int channelType, @Param("brandId")int brandId);
		//月度
		List<CommissionTarget> selectCommission_target_invoice1(@Param("start") int start, @Param("rows") int rows,
				@Param("categoryId")String categoryId, @Param(" startTime")String startTime, @Param("endTime")String endTime,
				@Param("policyYear")String policyYear, @Param("channelId")int channelId, @Param("channelType")int channelType, @Param("brandId")int brandId);
		//月度导出
		List<CommissionTarget> selectCommission_target_invoice3(
				@Param("categoryId")String categoryId,
				@Param("policyYear")String policyYear, @Param("channelId")int channelId, @Param("channelType")int channelType, @Param("brandId")int brandId);

		//季度导出
		List<CommissionTarget> selectCommission_target_invoice2(
				@Param("categoryId")String categoryId, 
				@Param("policyYear")String policyYear, @Param("channelId")int channelId, @Param("channelType")int channelType, @Param("brandId")int brandId);

		List<CommissionTarget> jiaoyan(@Param("entity")CommissionTarget entity);

		int getPagerCountInvoice(@Param("categoryId")String categoryId, @Param(" startTime")String startTime, @Param("endTime")String endTime,
				@Param("policyYear")String policyYear, @Param("channelId")int channelId, @Param("channelType")int channelType, @Param("brandId")int brandId);


}