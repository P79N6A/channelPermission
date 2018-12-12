package com.haier.shop.service;

import com.haier.shop.model.Storages;

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
}
