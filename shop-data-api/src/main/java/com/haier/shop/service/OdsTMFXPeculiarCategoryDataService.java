package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.model.OdsTMFXPeculiarCategory;

import java.util.List;
import java.util.Map;

public interface OdsTMFXPeculiarCategoryDataService {

    /**
     * 通过主键删除母婴产品主数据维护表<br>
     * 此方法为自动生成.<br>
     * 此方法对应的数据表为： ODS_TMFX_PECULIAR_CATEGORY<br>
     *
     */
    public int deleteByPrimaryKey(String id);

    /**
     * 添加数据到母婴产品主数据维护表<br>
     * 此方法为自动生成.<br>
     * 此方法对应的数据表为： ODS_TMFX_PECULIAR_CATEGORY<br>
     *
     */
    public int insertSelective(OdsTMFXPeculiarCategory record);

    /**
     * 通过主键查询母婴产品主数据维护表<br>
     * 此方法为自动生成.<br>
     * 此方法对应的数据表为： ODS_TMFX_PECULIAR_CATEGORY<br>
     *
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

    public OdsTMFXPeculiarCategory queryBySkuCategory(OdsTMFXPeculiarCategory odsTMFXPeculiarCategory);

    public List<OdsTMFXPeculiarCategory> queryPeculiarCategoryAllList();

    public JSONObject paging(OdsTMFXPeculiarCategory param, PagerInfo page) ;

    public JSONObject export(OdsTMFXPeculiarCategory map) ;

    public int delBatch(List<String> ids);
}
