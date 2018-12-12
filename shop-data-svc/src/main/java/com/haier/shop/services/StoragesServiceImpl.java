package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.StoragesReadDao;
import com.haier.shop.model.Storages;
import com.haier.shop.service.StoragesService;

@Service
public class StoragesServiceImpl implements StoragesService{
    @Autowired
    private StoragesReadDao storagesReadDao;

    /**
     * 根据编码获取库位信息
     * @param code 库位编码
     * @return
     */
   public Storages getByCode(String code){
       return storagesReadDao.getByCode(code);
   }

    /**
     * 根据id获取库位信息
     * @param id 库位id
     * @return
     */
    public Storages get(Integer id){
        return storagesReadDao.get(id);
    }
}
