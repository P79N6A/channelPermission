package com.haier.shop.dao.shopread;

import com.haier.shop.model.TmypProductDiscount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/8/21 17:19
 */
@Mapper
public interface TmypProductDiscountReadDao {

    Integer findTmyp_ProductDiscountCNT(Map<String,Object> params);

    List<TmypProductDiscount> getTmyp_ProductDiscountList(Map<String,Object> params);

    /**
     * 根据sku查询
     * @param itemCode
     * @return
     */
    TmypProductDiscount getTmyp_ProductDiscountBySKU(String itemCode);
}
