package com.haier.distribute.data.dao.distribute;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;

import com.haier.distribute.data.model.Regions;
@Configuration
public interface RegionsDao extends BaseDao<Regions>{
    int deleteByPrimaryKey(Integer id);

    int insert(Regions record);

    int insertSelective(Regions record);

    Regions selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Regions record);

    int updateByPrimaryKey(Regions record);
    
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

	List<Regions> pushAvailable();
}