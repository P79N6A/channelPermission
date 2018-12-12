package com.haier.afterSale.services;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.afterSale.model.ItemModel;
import com.haier.afterSale.service.StockCenterItemService;
import com.haier.common.ServiceResult;
import com.haier.distribute.data.model.ProductCates;
import com.haier.purchase.data.model.LesFiveYardInfo;
import com.haier.purchase.data.service.PurchaseLesFiveYardsService;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.NetPoints;
import com.haier.shop.model.ProductBase;
import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.ProductsNew;
import com.haier.shop.service.ShopItemAttributeService;
@Service
public class StockCenterItemServiceImpl implements StockCenterItemService {

    private final static Logger log = LogManager.getLogger(StockCenterItemServiceImpl.class);

    @Autowired
    private ShopItemAttributeService shopItemAttributeService;

    @Autowired
    private PurchaseLesFiveYardsService purchaseLesFiveYardsService;

    @Autowired
    private ItemModel itemModel;

    @Override
    public ServiceResult<List<String>> getAllCbsCategory() {
        ServiceResult<List<String>> result = new ServiceResult<List<String>>();
        try {
            result.setResult(shopItemAttributeService.getAllCbsCategory());
        } catch (Exception e) {
            log.error("查询所有CBS品类时，发生未知异常：", e);
            result.setMessage("查询所有CBS品类发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ServiceResult<List<LesFiveYardInfo>> selectLesFiveYards(Map<String, Object> params) {
        ServiceResult<List<LesFiveYardInfo>> result = new ServiceResult<List<LesFiveYardInfo>>();
        try {
            result.setResult(purchaseLesFiveYardsService.selectLesFiveYards(params));
        } catch (Exception e) {
            log.error("取les5码失败：", e);
            result.setMessage("取les5码失败：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    /*
     * 作者:张波
     * */
    @Override
    public ServiceResult<NetPoints> getByNetPointN8(String netPointN8) {
        ServiceResult<NetPoints> result = new ServiceResult<NetPoints>();
        try {
            NetPoints netPoints = itemModel.getByNetPointN8(netPointN8);
            result.setResult(netPoints);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("根据netPointN8查询网点时，发生未知异常：", e);
            result.setMessage("根据netPointN8查询网点发生未知异常：" + e.getMessage());
            result.setResult(null);
            result.setSuccess(false);
        }
        return result;
    }
    @Override
    public ServiceResult<ProductsNew> getProductBySku(String sku) {
        ServiceResult<ProductsNew> result = new ServiceResult<ProductsNew>();
        try {
            result.setResult(itemModel.getProductsBySku(sku));
        } catch (Exception e) {
            log.error("根据sku(" + sku + ")获取产品时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    @Override
    public ServiceResult<ProductTypesNew> getProductType(Integer typeId) {
        ServiceResult<ProductTypesNew> result = new ServiceResult<ProductTypesNew>();
        try {
            result.setResult(itemModel.getProductType(typeId));
        } catch (Exception e) {
            log.error("根据id(" + typeId + ")获取产品类型时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    @Override
    public ServiceResult<Boolean> updateSaleNumBySku(ProductsNew products) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            itemModel.updateSaleNumBySku(products);
            result.setResult(Boolean.TRUE);
        } catch (Exception e) {
            log.error("根据sku更新销量时，发生异常：", e);
            result.setMessage("根据sku更新销量发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(Boolean.FALSE);
        }
        return result;
    }

    @Override
    public ServiceResult<List<ProductBase>> getAllOnSaleProducts() {
        ServiceResult<List<ProductBase>> result = new ServiceResult<List<ProductBase>>();
        try {
            List<ProductBase> pbList = itemModel.getAllOnSaleSkuList();
            result.setResult(pbList);
        } catch (Exception e) {
            log.error("获取所有上架产品基础信息时，发生未知异常：", e);
            result.setMessage("发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

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
    public ServiceResult<ProductBase> getPorductBaseBySku(String sku) {
        ServiceResult<ProductBase> result = new ServiceResult<ProductBase>();
        try {
            ProductBase pb = itemModel.getProductBaseBySku(sku);
            result.setResult(pb);
        } catch (Exception e) {
            log.error("根据sku(" + sku + ")，获取产品基础信息时，发生未知异常：", e);
            result.setMessage("发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 根据物料SKU取得物料基本信息
     * @param sku 物料
     * @return ItemBase
     * @see com.haier.StockCenterItemService.item.service.ItemService#getItemBaseBySku(java.lang.String)
     */
    @Override
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

    /**
     * 根据产品属性值(value)和属性类别(valueSetId)取得产品属性
     * @param valueSetId 属性列别Id
     * @param value 属性值
     * @return ItemAttribute
     * @see com.haier.StockCenterItemService.item.service.ItemService#getItemAttributeByValueSetIdAndValue(java.lang.String, java.lang.String)
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
    public ServiceResult<ProductTypesNew> getProductTypeNew(Integer typeId) {
        ServiceResult<ProductTypesNew> result = new ServiceResult<ProductTypesNew>();
        try {
            result.setResult(itemModel.getProductTypeNew(typeId));
        } catch (Exception e) {
            log.error("根据id(" + typeId + ")获取产品类型时，发生未知异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<ProductsNew> getProductById(Integer id) {
        ServiceResult<ProductsNew> result = new ServiceResult<ProductsNew>();
        try {
            result.setResult(itemModel.getProductsById(id));
        } catch (Exception e) {
            log.error("根据id(" + id + ")获取产品时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    @Override
    public ServiceResult<List<ProductCates>> getChildrenProductCates(Integer parentId) {
        ServiceResult<List<ProductCates>> result = new ServiceResult<List<ProductCates>>();
        try {
            result.setResult(itemModel.getAllChildrenProductCates(parentId));
        } catch (Exception e) {
            log.error("取得所有子商品类目时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ServiceResult<List<ProductBase>> getAllProducts() {
        ServiceResult<List<ProductBase>> result = new ServiceResult<List<ProductBase>>();
        try {
            List<ProductBase> pbList = itemModel.getAllSkuList();
            result.setResult(pbList);
        } catch (Exception e) {
            log.error("获取所有商品基础信息时，发生未知异常：", e);
            result.setMessage("发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
}
