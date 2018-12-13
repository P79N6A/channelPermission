package com.haier.shop.dao.settleCenter;

import com.haier.common.PagerInfo;
import com.haier.shop.model.OdsTMFXPeculiarCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OdsTMFXPeculiarCategoryDao {
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
    public int insertSelective(OdsTMFXPeculiarCategory record);

    /**
     * 通过主键查询母婴产品主数据维护表<br>
     * 此方法为自动生成.<br>
     * 此方法对应的数据表为： ODS_TMFX_PECULIAR_CATEGORY<br>
     *
     * DATE: 2018-05-14 16:26
     */
    public OdsTMFXPeculiarCategory selectByPrimaryKey(String id);

    /**
     * 通过主键更新母婴产品主数据维护表<br>
     * 此方法为自动生成.<br>
     * 此方法对应的数据表为： ODS_TMFX_PECULIAR_CATEGORY<br>
     *
     * DATE: 2018-05-14 16:26
     */
    public int updateByPrimaryKeySelective(OdsTMFXPeculiarCategory record);

    public List<OdsTMFXPeculiarCategory> paging(@Param("param") OdsTMFXPeculiarCategory param, @Param("page") PagerInfo pagerInfo);

    public Long count(@Param("param")OdsTMFXPeculiarCategory record);

    public List<OdsTMFXPeculiarCategory> export(@Param("param")OdsTMFXPeculiarCategory map);

    public OdsTMFXPeculiarCategory queryBySkuCategory(OdsTMFXPeculiarCategory odsTMFXPeculiarCategory);

    public List<OdsTMFXPeculiarCategory> queryPeculiarCategoryAllList();

    int delBatch(List<String> ids);
}