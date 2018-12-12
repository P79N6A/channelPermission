package com.haier.shop.service;



import java.util.List;

import com.haier.shop.model.StoragesRela;

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
}
