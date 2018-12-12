package com.haier.shop.dao.shopread;/**
 * Created by Administrator on 2017/11/7 0007.
 */

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface BaseReadDao<T> {

    List<T> selectByKeys(@Param("keys") List<String> keys);

    T getOneById(long id);

    T getOneByCondition(@Param("entity") T entity);

    List<T> getListByCondition(@Param("entity") T entity);

    List<T> listByCondition(@Param("entity") T entity);

    List<T> getPageByCondition(@Param("entity") T entity, @Param("start") int start, @Param("rows") int rows);

    long getPagerCount(@Param("entity") T entity);

    T getOneByDeliveryToCode(@Param("deliveryToCode") String deliveryToCode);

//    List<PopOrderProducts> commodityList(@Param("orderId")Long orderId);

    List<T> checkCode(@Param("entity") T entity);
}
