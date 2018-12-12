package com.haier.distribute.data.services;/**
 * Created by Administrator on 2017/11/7 0007.
 */


import com.haier.distribute.data.dao.distribute.SysDictionaryDao;
import com.haier.distribute.data.model.SysDictionary;
import com.haier.distribute.data.service.SysDictionaryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/7 0007
 * \* Time: 10:56
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class SysDictionaryServiceImpl implements SysDictionaryService {

    @Autowired
    SysDictionaryDao sysDictionaryDao;

    @Override
    public String getSysDictionaryNo(String begin) {
        return sysDictionaryDao.getSysDictionaryNo(begin);
    }

    @Override
    public int updateSelectiveBySysDictionaryNo(SysDictionary entity) {
        return sysDictionaryDao.updateSelectiveBySysDictionaryNo(entity);
    }

    @Override
    public SysDictionary selectOrderCancelFlag() {
        return sysDictionaryDao.selectOrderCancelFlag();
    }

    @Override
    public List<SysDictionary> getListByCondition(SysDictionary entity){
        return sysDictionaryDao.getListByCondition(entity);
    }
}
