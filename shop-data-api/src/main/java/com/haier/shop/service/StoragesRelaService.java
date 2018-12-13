package com.haier.shop.service;


import com.haier.shop.model.StoragesRela;
import java.util.List;
import java.util.Map;

public interface StoragesRelaService {
    /**
     * 获取小家电多层级关系列表
     * @return
     */
    List<StoragesRela> getList();

    /**
     * 根据code列表，获取小家电多层级关系列表
     * @param codeList
     * @return
     */
    List<StoragesRela> getListByCodes( List<String> codeList);

    /**
     * 获取小家电多层级列表 带条件
     * @param params
     * @return
     */
    List<StoragesRela> getListByParam(Map<String, Object> params);

    /**
     * 获取小家电多层级列表数量 带条件
     * @param params
     * @return
     */
    Integer getListCountByParam(Map<String, Object> params);

    /**
     * 创建小家电多层级列表
     * @param storagesRela
     * @return
     */
    int createStoragesRela(StoragesRela storagesRela);
    /**
     * 更新小家电多层级列表
     * @param storagesRela
     * @return
     */
    int updateStoragesRela(StoragesRela storagesRela);

    /**
     * 根据库位主库位中心库位判断是否存在
     * @param bigStoragesRela
     * @return
     */
    int selectByCode(StoragesRela bigStoragesRela);
}
