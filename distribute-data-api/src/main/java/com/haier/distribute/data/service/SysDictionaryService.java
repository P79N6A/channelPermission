package com.haier.distribute.data.service;/**
 * Created by Administrator on 2017/11/7 0007.
 */


import com.haier.distribute.data.model.SysDictionary;

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
public interface SysDictionaryService {

    String getSysDictionaryNo(String begin);

    int updateSelectiveBySysDictionaryNo(SysDictionary entity);

    SysDictionary selectOrderCancelFlag();

    List<SysDictionary> getListByCondition(SysDictionary entity);


}
