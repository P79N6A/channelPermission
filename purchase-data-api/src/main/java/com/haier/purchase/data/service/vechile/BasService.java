package com.haier.purchase.data.service.vechile;

import java.util.List;

/**
 * <p>Description: </p>
 * ClassName:BasService
 * Created on 2017/9/12
 *
 * @author wsh
 * @version 1.0
 * Copyright (c) 2015 北京柯莱特科技有限公司 交付部
 */
public interface BasService<T> {

    int insertSelective(T entity);

    int updateSelectiveById(T entity);

    T getOneById(long id);

    T getOneByCondition(T entity);

    List<T> getListByCondition(T entity);

    List<T> getPageByCondition(T entity, int start, int rows);

    long getPagerCount(T entity);

    T getOneByDeliveryToCode(String deliveryToCode);
}
