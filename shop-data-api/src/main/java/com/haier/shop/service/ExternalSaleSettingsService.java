package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.model.ExternalSaleSettings;

import java.util.List;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/12 16:16
 */
public interface ExternalSaleSettingsService {
    int deleteByPrimaryKey(Integer id);
    int insert(ExternalSaleSettings record);
    int updateByPrimaryKeySelective(ExternalSaleSettings record);
    List<ExternalSaleSettings> Listf(ExternalSaleSettings entity, int start, int rows);
    int getPagerCountS(ExternalSaleSettings entity);
    ExternalSaleSettings findByWhere(String externalSkus);
}
