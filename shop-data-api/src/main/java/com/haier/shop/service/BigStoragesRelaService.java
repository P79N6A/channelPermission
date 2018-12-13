package com.haier.shop.service;

import com.haier.shop.model.BigStoragesRela;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 李超 on 2018/1/10.
 */
public interface BigStoragesRelaService {
    /**
     * 获取大家电多层级列表
     * @return
     */
    List<BigStoragesRela> getList();

    /**
     * 根据编号列表，获取对应的大家电多层级列表
     * @param codeList
     * @return
     */
    List<BigStoragesRela> getListByCodes(@Param("codeList") List<String> codeList);

    /**
     * 获取大家电多层级列表 带条件
     * @param params
     * @return
     */
    List<BigStoragesRela> getListByParam(Map<String, Object> params);

    /**
     * 获取大家电多层级列表数量 带条件
     * @param params
     * @return
     */
    Integer getListCountByParam(Map<String, Object> params);

    /**
     * 创建大家电多层级列表
     * @param bigStoragesRela
     * @return
     */
    int createBigStoragesRela(BigStoragesRela bigStoragesRela);
    /**
     * 更新大家电多层级列表
     * @param bigStoragesRela
     * @return
     */
    int updateBigStoragesRela(BigStoragesRela bigStoragesRela);

    /**
     * 根据库位主库位中心库位判断是否存在
     * @param bigStoragesRela
     * @return
     */
    int selectByCode(BigStoragesRela bigStoragesRela);
}
