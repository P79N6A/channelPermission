package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderGuaranteePriceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 接收保本价信息 dao层
 * @Version: 1.0
 */
@Mapper
public interface OrderGuaranteePriceInfoWriteDao {


    /**
     * 插入保本价信息<br/>
     * 渠道、物流模式，品类id、物料编码相同则不插入
     * @param orderGuaranteePriceInfo
     * @return
     */
    public int insert(OrderGuaranteePriceInfo orderGuaranteePriceInfo);

    public int insertNew(OrderGuaranteePriceInfo orderGuaranteePriceInfo);

    /**
     * 由渠道、物流模式，品类id、物料编码更新保本价信息
     * @param orderGuaranteePriceInfo
     * @return
     */
    public int update(OrderGuaranteePriceInfo orderGuaranteePriceInfo);

}