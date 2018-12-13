package com.haier.shop.service;

import com.haier.shop.model.Storages;
import com.haier.shop.model.StoragesWwwRelas;
import java.util.List;
import java.util.Map;

public interface StoragesService {
    /**
     * 根据编码获取库位信息
     * @param code 库位编码
     * @return
     */
    Storages getByCode(String code);

    /**
     * 根据id获取库位信息
     * @param id 库位id
     * @return
     */
    Storages get(Integer id);
    /**
     * 查询库位编码
     * @param netPointN8
     * @return
     */
    String queryCenterCode(String netPointN8);
    
    /**
     * 根据c码查询出库位
     * @param centerCode
     * @return
     */
    String queryCode(String centerCode);
    /**
     * 根据3w编码获取库位信息
     * @param wwwCode 库位编码
     * @return
     */
    String getBywwwCode(String wwwCode);

    /**
     * 根据3w编码获取国标码
     * @param wwwCode 库位编码
     * @return
     */
    StoragesWwwRelas findForGbCode(String wwwCode);

    /**
     * 根据菜鸟编码获取3w电商编码
     * @param caiNiaoCode
     * @return
     */
    StoragesWwwRelas findByCainiaoStoreCode(String caiNiaoCode);


    /**
     * 根据类型获取库位信息
     * @param type 库位类型
     * @return
     */
    List<Map<String, String>> getInfoByType(Integer type);

    /**
     * 库位列表查询
     * @param params
     * @return
     */
    Map<String,Object> getStoragesList(Map<String,Object> params);

    /**
     * 导出库位列表查询
     * @param params
     * @return
     */
    List<Map<String,Object>> getExportStoragesListByParams(Map<String,Object> params);

    /**
     * 添加库位
     * @param storages
     * @param cityName
     * @return
     */
    int insert(Storages storages, String cityName);


    /**
     * 删除库位
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

}
