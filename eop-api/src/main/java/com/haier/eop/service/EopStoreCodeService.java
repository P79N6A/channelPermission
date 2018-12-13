package com.haier.eop.service;

import com.haier.eop.data.model.StoreCodeMapping;

import java.util.Map;

/**
 * 仓库编码处理类
 */
public interface EopStoreCodeService {

    /**
     * 获取仓库编码列表
     * @param params
     * @return
     */
    Map<String,Object> findStoreCodeList(Map<String, Object> params);

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
    int createStoreCode(StoreCodeMapping storeCode);

    /**
     * 修改仓库编码对照信息
     * @param storeCode
     * @return
     */
    int updateStoreCode(StoreCodeMapping storeCode);

    /**
     * 根据ID删除仓库编码对照信息
     * @param id
     * @return
     */
    int deleteStoreCodeById(String id);
}
