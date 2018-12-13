package com.haier.eop.data.service;
import com.haier.eop.data.model.StoreCodeMapping;

import java.util.Map;

/**
 * 仓库编码处理类
 */
public interface StoreCodeService {

    /**
     * 根据海尔仓库编码获得实体类
     * @param StoreCode
     * @return
     */
    StoreCodeMapping findByHaierStoreCode(String StoreCode);

    StoreCodeMapping findByStoreCode(String StoreCode);
    /**
     *
     * 分页获取仓库编码对照信息表
     *
     * @param params
     * @return
     */
    Map<String,Object> getStoreCodeByPage(Map<String, Object> params);

    /**
     * 根据菜鸟仓库编码获得实体类
     * @param cainiaoStoreCode
     * @return
     */
    StoreCodeMapping findByCainiaoStoreCode(String cainiaoStoreCode);

    /**
     * 添加仓库编码对照信息
     * @param storeCode
     * @return
     */
    int insertStoreCode(StoreCodeMapping storeCode);

    /**
     * 修改仓库编码对照信息
     * @param storeCode
     * @return
     */
    int updateStoreCode(StoreCodeMapping storeCode);

    /**
     * 根据StoreCodeId查询
     * @param id
     * @return
     */
    StoreCodeMapping findByStoreCodeId(Integer id);

    /**
     * 根据id删除StoreCode
     * @param id
     * @return
     */
    int deleteStoreCodeById(int id);
}
