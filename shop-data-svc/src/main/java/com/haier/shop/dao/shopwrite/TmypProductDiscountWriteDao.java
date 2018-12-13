package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.TmypProductDiscount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:JinXueqian
 * @Date: 2018/8/22 18:03
 */
@Mapper
public interface TmypProductDiscountWriteDao {

    /**
     * 添加
     * @param tmypProductDiscount
     * @return
     */
    int insert(TmypProductDiscount tmypProductDiscount);

    /**
     * 修改
     * @param tmypProductDiscount
     * @return
     */
    int updateTmypProductDiscountInfo(TmypProductDiscount tmypProductDiscount);
}
