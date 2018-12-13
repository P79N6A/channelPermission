package com.haier.svc.services;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.BusinessException;
import com.haier.distribute.data.model.ProductCates;
import com.haier.shop.model.*;
import com.haier.shop.service.ShopItemAttributeService;
import com.haier.shop.service.ShopItemBaseService;

import com.haier.stock.model.InvStockAge;
import com.haier.stock.service.InvStockAgeService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.service.PurchaseLesFiveYardsService;
import com.haier.svc.model.ItemModel;
import com.haier.svc.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {

    private final static Logger log = LogManager.getLogger(ItemServiceImpl.class);

    @Autowired
    private ShopItemAttributeService shopItemAttributeService;
    @Autowired
    private ShopItemBaseService shopItemBaseService;
    
    @Autowired
    private PurchaseLesFiveYardsService purchaseLesFiveYardsService;

    @Autowired
    private ItemModel itemModel;

    @Autowired
    private InvStockAgeService invStockAgeService;
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
    public JSONObject queryItemAttribute(ItemAttribute itemAttribute) {
        return shopItemAttributeService.queryItemAttribute(itemAttribute);
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
    /**
     * 手动添加物料基本信息
     * @param itemBase 物料信息
     * @return 执行结果
     */
    @Override
    public ServiceResult<Boolean> insertItemBaseInfo(ItemBase itemBase) {
        ServiceResult<Boolean> ret = new ServiceResult<Boolean>();
        try {
            itemModel.insertItemBaseInfo(itemBase);
            ret.setSuccess(true);
            ret.setResult(true);
        } catch (Throwable e) {
            ret.setSuccess(false);
            ret.setResult(false);
            ret.setMessage("手动添加物料基本信息时发生未知异常");
            log.error("手动添加物料基本信息时发生未知异常：", e);
        }
        return ret;
    }
    @Override
    public ServiceResult<Boolean> updateItemBaseById(ItemBase base) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            itemModel.updateItemBaseById(base);
            result.setResult(true);
        } catch (Exception e) {
            log.error("更新产品基础信息时，发生未知异常：", e);
            result.setMessage("更新产品信息发生未知异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(false);
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

	@Override
	public ServiceResult<List<ItemBase>> getType() {
		// TODO Auto-generated method stub
		ServiceResult<List<ItemBase>> result = new ServiceResult<List<ItemBase>>();
		
        try {
            List<ItemBase> type = shopItemBaseService.getType();
            result.setResult(type);
        } catch (Exception e) {
            log.error("获取所有商品基础信息时，发生未知异常：", e);
            result.setMessage("发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
		return result;
	}
	@Override
	public ServiceResult<List<ItemBase>> getProductBaseData(Map<String, Object> params) {
		// TODO Auto-generated method stub
		ServiceResult<List<ItemBase>> result = new ServiceResult<List<ItemBase>>();
		
        try {
            List<ItemBase> type = shopItemBaseService.getProductBaseData(params);
            result.setResult(type);
            int pagecount = shopItemBaseService.getRowCnts(params);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(pagecount);
            result.setPager(pi);
        } catch (Exception e) {
            log.error("获取所有商品基础信息时，发生未知异常：", e);
            result.setMessage("发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
		return result;
	}

    @Override
    public ServiceResult<List<ItemBase>> findItemBaseByMaterialId(String material_id) {
        ServiceResult<List<ItemBase>> result = new ServiceResult<List<ItemBase>>();
        try {
            result.setResult(itemModel.findItemBaseByMaterialId(material_id));
        } catch (Exception e) {
            log.error("查询品牌和型号，发生未知异常：", e);
            result.setMessage("查询品牌和型号，发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ServiceResult<List<String>> getCbsCategoryByProductGroup(Map<String, Object> map) {
        ServiceResult<List<String>> result = new ServiceResult<List<String>>();
        try {
            result.setResult(itemModel.getCbsCategoryByProductGroup(map));
        } catch (Exception e) {
            log.error("根据产品组查询品类时，发生未知异常：", e);
            result.setMessage("根据产品组查询品类时，发生未知异常：" + e.getMessage());
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
     * @see com.haier.cbs.item.service.ItemService#saveItemAttribute(com.haier.cbs.item.entity.ItemAttribute)
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
     * @see com.haier.cbs.item.service.ItemService#saveItemBase(com.haier.cbs.item.entity.ItemBase)
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
