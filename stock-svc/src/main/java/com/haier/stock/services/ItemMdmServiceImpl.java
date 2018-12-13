package com.haier.stock.services;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.ProductBase;
import com.haier.shop.service.ShopItemBaseService;
import com.haier.stock.model.InvStockAge;
import com.haier.stock.service.InvStockAgeService;
import com.haier.stock.service.ItemMdmService;
@Service
public class ItemMdmServiceImpl implements ItemMdmService {

    private final static Logger log = LogManager.getLogger(ItemMdmServiceImpl.class);

    @Autowired
    private ShopItemBaseService shopItemBaseService;

    @Autowired
    private ItemModel itemModel;

    @Autowired
    private InvStockAgeService invStockAgeService;

    public ServiceResult<List<ProductBase>> getAllProductsBysCode(String scode) {
        ServiceResult<List<ProductBase>> result = new ServiceResult<List<ProductBase>>();
        try {
            result.setResult(itemModel.getAllProductsBysCode(scode));
        } catch (Exception e) {
            log.error("根据scode取得所有产品时,scode=" + scode + "，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }


    @Override
    public ServiceResult<String> getRegionsCode(Map<String, Object> params) {
        ServiceResult<String> result = new ServiceResult<String>();
        try {
            String code = itemModel.getRegionsCode(params);
            result.setSuccess(true);
            result.setResult(code);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            if (e instanceof BusinessException) {
                result.setMessage(e.getMessage());
            } else {
                result.setMessage("查询国标码等信息失败");
            }
            log.error("查询国标码等信息失败：", e);
        }
        return result;
    }
    /**
     * 根据产品属性值(value)和属性类别(valueSetId)取得产品属性
     * @param valueSetId 属性列别Id
     * @param value 属性值
     * @return ItemAttribute
     */
    @Override
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
    
    @Override
    public ServiceResult<List<ItemAttribute>> queryProductGroupByCbsCategory(String cbsCategory) {
        ServiceResult<List<ItemAttribute>> result = new ServiceResult<List<ItemAttribute>>();
        try {
            result.setResult(itemModel.queryProductGroupByCbsCategory(cbsCategory));
        } catch (Exception e) {
            log.error("根据CBS品类查询产品组时，发生未知异常：", e);
            result.setMessage("根据CBS品类查询产品组发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public List<ItemBase> getIncompleteItemBaseList() {
        return shopItemBaseService.getIncompleteItemBaseList();
    }

    @Override
    public int updateMtlInfoForStockAge(InvStockAge stockAge) {
        return invStockAgeService.updateMtlInfoForStockAge(stockAge);
    }

    /**
     * 保存产品属性(ItemAttribute) 
     * 如果已经存在：更新 
     * 如果不存在：插入
     * @param attribute 产品属性
     * @return 更新结果
     * @see com.haier.ItemMdmService.item.service.ItemService#saveItemAttribute(com.haier.cbs.item.entity.ItemAttribute)
     */
    @Override
    public ServiceResult<Boolean> saveItemAttribute(ItemAttribute attribute) {
        ServiceResult<Boolean> ret = new ServiceResult<Boolean>();
        try {
            itemModel.saveItemAttribute(attribute);
            ret.setSuccess(true);
            ret.setResult(true);
        } catch (Throwable e) {
            ret.setSuccess(false);
            ret.setResult(false);
            ret.setMessage("保存产品属性时发生未知异常");
            log.error("保存产品属性时发生未知异常：", e);
        }
        return ret;
    }

    /**
     * 保存物料基本信息 
     * 如果已经存在：更新
     * 如果不存在：插入
     * @param base 产品治疗
     * @return 执行结果
     * @see com.haier.ItemMdmService.item.service.ItemService#saveItemBase(com.haier.cbs.item.entity.ItemBase)
     */
    @Override
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

    /**
     * 手动同步物料基本信息
     * @param itemBase 物料信息
     * @param modifier 修改人
     * @return 修复结果
     * @see com.haier.cbs.item.service.ItemService#manualSyncMdmBySku(com.haier.cbs.item.entity.ItemBase,String modifier)
     */
    @Override
    public ServiceResult<Boolean> manualSyncMdmBySku(ItemBase itemBase, String modifier) {
        ServiceResult<Boolean> ret = new ServiceResult<Boolean>();
        try {
            int count = itemModel.manualSyncMdmBySku(itemBase, modifier);
            if (count == 0) {
                ret.setSuccess(true);
                ret.setResult(false);
                ret.setMessage("MDM中没有此物料可以同步的信息！");
            } else {
                ret.setSuccess(true);
                ret.setResult(true);
            }
        } catch (Throwable e) {
            ret.setSuccess(false);
            ret.setResult(false);
            ret.setMessage("手动同步物料基本信息时发生未知异常");
            log.error("手动同步物料基本信息时发生未知异常：", e);
        }
        return ret;
    }

}
