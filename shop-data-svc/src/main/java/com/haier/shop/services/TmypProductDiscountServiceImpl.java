package com.haier.shop.services;

import com.haier.shop.dao.shopread.TmypProductDiscountReadDao;
import com.haier.shop.dao.shopwrite.TmypProductDiscountWriteDao;
import com.haier.shop.model.TmypProductDiscount;
import com.haier.shop.service.TmypProductDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/8/21 17:14
 */
@Service
public class TmypProductDiscountServiceImpl implements TmypProductDiscountService {

    @Autowired
    TmypProductDiscountReadDao tmypProductDiscountReadDao;

    @Autowired
    TmypProductDiscountWriteDao tmypProductDiscountWriteDao;


    @Override
    public Integer findTmyp_ProductDiscountCNT(Map<String, Object> params) {
        return tmypProductDiscountReadDao.findTmyp_ProductDiscountCNT(params);
    }

    @Override
    public List<TmypProductDiscount> getTmyp_ProductDiscountList(Map<String, Object> params) {
        return tmypProductDiscountReadDao.getTmyp_ProductDiscountList(params);
    }

    @Override
    public int insert(TmypProductDiscount tmypProductDiscount) {
        return tmypProductDiscountWriteDao.insert(tmypProductDiscount);
    }

    @Override
    public int updateTmypProductDiscountInfo(TmypProductDiscount tmypProductDiscount) {
        return tmypProductDiscountWriteDao.updateTmypProductDiscountInfo(tmypProductDiscount);
    }

    @Override
    public TmypProductDiscount getTmyp_ProductDiscountBySKU(String itemCode) {
        return tmypProductDiscountReadDao.getTmyp_ProductDiscountBySKU(itemCode);
    }
}
