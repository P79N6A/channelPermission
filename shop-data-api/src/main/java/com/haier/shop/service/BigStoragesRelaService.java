package com.haier.shop.service;

import com.haier.shop.model.BigStoragesRela;
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
}
