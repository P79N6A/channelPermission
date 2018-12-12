package com.haier.stock.model;

import com.haier.common.ServiceResult;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl {
    @Autowired
    private ItemModel itemModel;
    private final static Logger log = LogManager.getLogger(ItemServiceImpl.class);
    public ServiceResult<ItemBase> getItemBaseBySku(String sku) {
        ServiceResult<ItemBase> result = new ServiceResult<ItemBase>();
        try {
            result.setResult(itemModel.getItemBaseBySku(sku));
        } catch (Exception e) {
            log.error("根据物料SKU取得物料基本信息时发生未知异常：", e);
            result.setMessage("根据物料SKU取得物料基本信息时发生未知异常");
            result.setSuccess(false);
        }
        return result;
    }
    public ServiceResult<Boolean> saveItemBase(ItemBase base) {
        ServiceResult<Boolean> ret = new ServiceResult<Boolean>();
        try {
            itemModel.saveItemBase(base);
            ret.setSuccess(true);
            ret.setResult(true);
        } catch (Throwable e) {
            ret.setSuccess(false);
            ret.setResult(false);
            ret.setMessage("保存物料基本信息时发生未知异常");
            log.error("保存物料基本信息时发生未知异常：", e);
        }
        return ret;
    }
    public ServiceResult<ItemAttribute> getItemAttributeByValueSetIdAndValue(String valueSetId,
                                                                             String value) {
        ServiceResult<ItemAttribute> result = new ServiceResult<ItemAttribute>();
        try {
            result.setResult(itemModel.getItemAttributeByValueSetIdAndValue(valueSetId, value));
        } catch (Exception e) {
            log.error("根据产品属性值(value)和属性类别(valueSetId)取得产品属性时发生未知异常：", e);
            result.setMessage("根据产品属性值和属性类别取得产品属性时发生未知异常");
            result.setSuccess(false);
        }
        return result;
    }
}
