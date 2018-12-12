package com.haier.shop.dao.shopread;

import com.haier.shop.model.StoragesRela;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}
