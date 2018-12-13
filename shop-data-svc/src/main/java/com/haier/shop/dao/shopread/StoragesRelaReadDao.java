package com.haier.shop.dao.shopread;

import com.haier.shop.model.StoragesRela;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StoragesRelaReadDao {
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
    List<StoragesRela> getListByCodes(@Param("codeList") List<String> codeList);

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
     * 根据库位主库位中心库位判断是否存在
     * @param storagesRela
     * @return
     */
    Integer selectByCode(@Param("entity") StoragesRela storagesRela);
}
