package com.haier.eop.data.dao;

import com.haier.eop.data.model.StoreCodeMapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StoreCodeMappingDao {

    /**
    * 根据haierStoreCode
    * @param haierStoreCode
    * @return
    */
    StoreCodeMapping findByHaierStoreCode(@Param("haierStoreCode") String haierStoreCode);

    /**
     * 分页条件查询仓库编码
     * @param params
     * @return
     */
    List<StoreCodeMapping> getStoreCode(Map<String, Object> params);

    /**
     * 获取总条数
     * @return
     */
    int getRowCnts();

    /**
     * 根据cainiaoStoreCode获得实体类
     * @param cainiaoStoreCode
     * @return
     */
    StoreCodeMapping findByCainiaoStoreCode(@Param("cainiaoStoreCode")String cainiaoStoreCode);

    /**
     * 新增仓库编码对照信息
     * @param storeCode
     * @return
     */
    int insertStoreCode(StoreCodeMapping storeCode);

    /**
     * 修改指定的仓库编码对照信息
     * @param storeCode
     * @return
     */
    int updateStoreCode(StoreCodeMapping storeCode);

    /**
     * 根据StoreCodeId查询
     * @param id
     * @return
     */
    StoreCodeMapping findByStoreCodeId(@Param("id") Integer id);

    /**
     * 根据ID删除StoreCode
     * @param id
     * @return
     */
    int deleteStoreCodeById(@Param(value = "id") int id);
}
