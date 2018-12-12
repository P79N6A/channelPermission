package com.haier.distribute.data.dao.distribute;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.distribute.data.model.CommissionProduct;


public interface CommissionProductDao extends BaseDao<CommissionProduct> {
    int deleteByPrimaryKey(Integer id);

    int insert(CommissionProduct record);

    int insertSelective(CommissionProduct record);

    CommissionProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommissionProduct record);

    int updateByPrimaryKey(CommissionProduct record);
    int getPagerCountS(@Param("entity") CommissionProduct entity);
	//根据条件查询结果List
	List<CommissionProduct> selectCommission(@Param("entity") CommissionProduct entity);
	List<CommissionProduct> selectCommission_productListf(@Param("entity") CommissionProduct entity, @Param("start") int start, @Param("rows") int rows);
	List<CommissionProduct> selectexportCommission_productListf(@Param("entity") CommissionProduct entity);
	List<CommissionProduct> skuAll(@Param("sku")String sku,@Param("channtype")Integer channtype,@Param("policyYear")Integer policyYear);
	List<CommissionProduct> jiaoyan(@Param("entity")CommissionProduct commission);

}