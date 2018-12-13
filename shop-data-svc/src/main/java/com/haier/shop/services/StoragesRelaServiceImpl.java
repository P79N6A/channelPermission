package com.haier.shop.services;


import com.haier.shop.dao.shopread.StoragesReadDao;
import com.haier.shop.dao.shopread.StoragesRelaReadDao;
import com.haier.shop.dao.shopwrite.StoragesRelaWriteDao;
import com.haier.shop.model.StoragesRela;
import com.haier.shop.service.StoragesRelaService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoragesRelaServiceImpl implements StoragesRelaService {

    @Autowired
    StoragesRelaReadDao storagesRelaReadDao;

    @Autowired
    StoragesReadDao storagesReadDao;

    @Autowired
    StoragesRelaWriteDao storagesRelaWriteDao;

    @Override
    public List<StoragesRela> getList() {
        return storagesRelaReadDao.getList();
    }

    @Override
    public List<StoragesRela> getListByCodes(List<String> codeList) {
        return storagesRelaReadDao.getListByCodes(codeList);
    }

    @Override
    public List<StoragesRela> getListByParam(Map<String, Object> params) {
        List<StoragesRela> list = storagesRelaReadDao.getListByParam(params);
        for (int i = 0 ; i < list.size(); i++) {
            //转换一下用来查询库位名称
            List<String> code = Arrays.asList(list.get(i).getMulStoreCode().split(","));
            //查询库位名称
            List<Map<String, String>> names = storagesReadDao.getInfoByCode(code);
            String mulStoreName = null;
            for (int j = 0; j < names.size(); j++) {
                if (mulStoreName != null ) {
                    mulStoreName += "," + names.get(j).get("name");
                } else {
                    mulStoreName= names.get(j).get("name");
                }
            }
            list.get(i).setMulStoreName(mulStoreName);
        }
        return list;
    }

    @Override
    public Integer getListCountByParam(Map<String, Object> params) {
        return storagesRelaReadDao.getListCountByParam(params);
    }

    @Override
    public int createStoragesRela(StoragesRela storagesRela) {
        return storagesRelaWriteDao.createStoragesRela(storagesRela);
    }

    @Override
    public int updateStoragesRela(StoragesRela storagesRela) {
        return storagesRelaWriteDao.updateStoragesRela(storagesRela);
    }

    @Override
    public int selectByCode(StoragesRela bigStoragesRela) {
        return 0;
    }
}
