package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Configuration;

import com.haier.shop.model.Regions;

@Configuration
@Mapper
public interface RegionsWriteDao extends BaseWriteDao<Regions> {

	 int deleteByPrimaryKey(Integer id);

	 int insert(Regions record);

	 int insertSelective(Regions record);

	 int updateByPrimaryKeySelective(Regions record);

	 int updateByPrimaryKey(Regions record);
	    
}