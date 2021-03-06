package com.haier.shop.service;

import java.util.List;
import java.util.Map;

import com.haier.shop.model.Regions;

public interface RegionsService  {
    int deleteByPrimaryKey(Integer id);

    int insert(Regions record);

    int insertSelective(Regions record);

    Regions selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Regions record);

    int updateByPrimaryKey(Regions record);

    int updateByParentId(Regions record);
    
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

    List<Regions> getByIds( String ids);
    Regions getRegions(Map<String, Object> params);
    
    String selectCode(String id);//根据主键查询国际编码

    List<Regions> Listf(Regions entity, int start, int rows);

    int getPagerCountS(Regions entity);

    public List<Regions> getRegion(int id);
}