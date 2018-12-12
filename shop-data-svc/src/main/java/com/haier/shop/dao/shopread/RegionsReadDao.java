package com.haier.shop.dao.shopread;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haier.shop.model.Regions;

@Mapper
public interface RegionsReadDao extends BaseReadDao<Regions> {

	    Regions selectByPrimaryKey(Integer id);

	    List<Regions> selectByParentId(Integer parentid);

	    List<Regions> selectByParentPatchId(Integer patchId);

	    List<Regions>selectregionprovince(int id);

	    List<Regions>selectregioncity(int id);

	    List<Regions>selectregioncounty(int id);

		int getRegionType(int id);

		String selectPatchId(int id);

		String selectregionName(int id);

		List<Regions> getRegionsAll();

	    Regions get(Integer id);

	    List<Regions> getByIds(@Param("ids") String ids);

	    public Regions getRegions(Map<String, Object> params);
}