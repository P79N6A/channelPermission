package com.haier.stock.dao.stock;/**
 * Created by Administrator on 2017/11/7 0007.
 */

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/7 0007
 * \* Time: 9:10
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public interface BaseDao<T> {


    int insertSelective(T entity);

    int updateSelectiveById(@Param("entity") T entity);

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
