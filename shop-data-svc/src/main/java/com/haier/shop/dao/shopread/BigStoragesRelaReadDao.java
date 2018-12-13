package com.haier.shop.dao.shopread;

import com.haier.shop.model.BigStoragesRela;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface BigStoragesRelaReadDao {
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
     * 根据库位主库位中心库位判断是否存在
     * @param bigStoragesRela
     * @return
     */
    Integer selectByCode(@Param("entity") BigStoragesRela bigStoragesRela);
}
