package com.haier.shop.dao.settleCenter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haier.common.PagerInfo;
import com.haier.shop.model.SNOdsTMFXPeculiarCategory;

@Mapper
public interface SNOdsTMFXPeculiarCategoryDao {
    /**
     * 通过主键删除母婴产品主数据维护表<br>
     * 此方法为自动生成.<br>
     * 此方法对应的数据表为： ODS_TMFX_PECULIAR_CATEGORY<br>
     *
     * DATE: 2018-05-14 16:26
     */
    public int deleteByPrimaryKey(String id);

    /**
     * 添加数据到母婴产品主数据维护表<br>
     * 此方法为自动生成.<br>
     * 此方法对应的数据表为： ODS_TMFX_PECULIAR_CATEGORY<br>
     *
     * DATE: 2018-05-14 16:26
     */
    public int insertSelective(SNOdsTMFXPeculiarCategory record);

    /**
     * 通过主键查询母婴产品主数据维护表<br>
     * 此方法为自动生成.<br>
     * 此方法对应的数据表为： ODS_TMFX_PECULIAR_CATEGORY<br>
     *
     * DATE: 2018-05-14 16:26
     */
    public SNOdsTMFXPeculiarCategory selectByPrimaryKey(String id);

    /**
     * 通过主键更新母婴产品主数据维护表<br>
     * 此方法为自动生成.<br>
     * 此方法对应的数据表为： ODS_TMFX_PECULIAR_CATEGORY<br>
     *
     * DATE: 2018-05-14 16:26
     */
    public int updateByPrimaryKeySelective(SNOdsTMFXPeculiarCategory record);

    public List<SNOdsTMFXPeculiarCategory> paging(@Param("param") SNOdsTMFXPeculiarCategory param, @Param("page") PagerInfo pagerInfo);

    public Long count(@Param("param")SNOdsTMFXPeculiarCategory record);

    public List<SNOdsTMFXPeculiarCategory> export(@Param("param")SNOdsTMFXPeculiarCategory map);

    public SNOdsTMFXPeculiarCategory queryBySkuCategory(SNOdsTMFXPeculiarCategory odsTMFXPeculiarCategory);

    public List<SNOdsTMFXPeculiarCategory> queryPeculiarCategoryAllList();

    int delBatch(List<String> ids);
}