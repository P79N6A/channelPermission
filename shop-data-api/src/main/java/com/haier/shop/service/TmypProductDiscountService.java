package com.haier.shop.service;

import com.haier.shop.model.TmypProductDiscount;

import java.util.List;
import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/8/21 17:13
 */
public interface TmypProductDiscountService {
    /**
     * 查询总条数
     * @param params
     * @return
     */
    Integer findTmyp_ProductDiscountCNT(Map<String,Object> params);

    /**
     * 分页查询
     * @param params
     * @return
     */
    List<TmypProductDiscount> getTmyp_ProductDiscountList(Map<String,Object> params);

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

    /**
     * 根据sku查询
     * @param itemCode
     * @return
     */
    TmypProductDiscount getTmyp_ProductDiscountBySKU(String itemCode);
}
