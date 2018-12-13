package com.haier.afterSale.services;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.afterSale.model.ItemModel;
import com.haier.afterSale.service.ItemServiceNew;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.distribute.data.model.Brands;
import com.haier.distribute.data.model.ProductCates;
import com.haier.eis.model.EisExternalSku;
import com.haier.shop.model.LesFiveYardInfo;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.NetPoints;
import com.haier.shop.model.Payments;
import com.haier.shop.model.ProductBase;
import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.ProductsNew;
@Service
public class ItemServiceImplNew implements ItemServiceNew {
    private static Logger log = LoggerFactory.getLogger(ItemServiceImplNew.class);
    @Autowired
    private ItemModel itemModel;

    @Override
    public void hello() {
        log.info(">>> hello job scheduler"); //test
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
    public ServiceResult<Brands> getBrands(Integer brandId) {
        ServiceResult<Brands> result = new ServiceResult<Brands>();
        try {
            result.setResult(itemModel.getBrandsById(brandId));
        } catch (Exception e) {
            log.error("根据id(" + brandId + ")获取品牌时，发生未知异常：", e);
            result.setSuccess(false);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<ProductCates> getProductCates(Integer id) {
        ServiceResult<ProductCates> result = new ServiceResult<ProductCates>();
        try {
            result.setResult(itemModel.getProductCates(id));
        } catch (Exception e) {
            log.error("根据id(" + id + ")获取品类时，发生未知异常：", e);
            result.setSuccess(false);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<List<ProductsNew>> getAllProductInfo() {
        ServiceResult<List<ProductsNew>> result = new ServiceResult<List<ProductsNew>>();
        try {
            result.setResult(itemModel.getAllProductInfo());
        } catch (Exception e) {
            log.error("取得所有产品时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
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
    public ServiceResult<List<ProductCates>> getAllProductCates() {
        ServiceResult<List<ProductCates>> result = new ServiceResult<List<ProductCates>>();
        try {
            result.setResult(itemModel.getAllProductCates());
        } catch (Exception e) {
            log.error("取得所有产品时，发生未知异常：", e);
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
     * 根据产品属性值(value)和属性类别(valueSetId)取得产品属性
     * @param valueSetId 属性列别Id
     * @param value 属性值
     * @return ItemAttribute
     * @see com.haier.cbs.item.service.ItemService#getItemAttributeByValueSetIdAndValue(java.lang.String, java.lang.String)
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
     * 根据物料SKU取得物料基本信息
     * @param sku 物料
     * @return ItemBase
     * @see com.haier.cbs.item.service.ItemService#getItemBaseBySku(java.lang.String)
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
     * 取得信息不完整的物料基本信息
     * @return list of ItemBase
     * @see com.haier.cbs.item.service.ItemService#getIncompleteItemBaseList()
     */
    @Override
    public ServiceResult<List<ItemBase>> getIncompleteItemBaseList() {
        ServiceResult<List<ItemBase>> result = new ServiceResult<List<ItemBase>>();
        try {
            List<ItemBase> list = itemModel.getIncompleteItemBaseList();
            result.setResult(list);
        } catch (Exception e) {
            log.error("取得信息不完整的物料基本信息时发生未知异常：", e);
            result.setMessage("取得信息不完整的物料基本信息时发生未知异常");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ServiceResult<List<ProductBase>> getProductListBySkus(List<String> skuList) {
        ServiceResult<List<ProductBase>> result = new ServiceResult<List<ProductBase>>();
        try {
            List<ProductBase> list = itemModel.getProductListBySkus(skuList);
            result.setResult(list);
        } catch (Exception e) {
            log.error("根据sku列表，获取产品列表时，发生未知异常：", e);
            result.setMessage("发生未知异常：" + e.getMessage());
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

    @Override
    public ServiceResult<List<ItemBase>> queryItemBaseByParamWithLike(ItemBase base) {
        ServiceResult<List<ItemBase>> result = new ServiceResult<List<ItemBase>>();
        try {
            result.setResult(itemModel.queryItemBaseByParamWithLike(base));
        } catch (Exception e) {
            log.error("根据条件查询产品列表,物流编码大小写不敏感时，发生未知异常：", e);
            result.setMessage("根据条件查询产品列表,物流编码大小写不敏感发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ServiceResult<List<String>> getAllCbsCategory() {
        ServiceResult<List<String>> result = new ServiceResult<List<String>>();
        try {
            result.setResult(itemModel.getAllCbsCategory());
        } catch (Exception e) {
            log.error("查询所有CBS品类时，发生未知异常：", e);
            result.setMessage("查询所有CBS品类发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ServiceResult<List<ItemAttribute>> queryItemAttributeWithLike(ItemAttribute itemAttribute) {
        ServiceResult<List<ItemAttribute>> result = new ServiceResult<List<ItemAttribute>>();
        try {
            result.setResult(itemModel.queryItemAttributeWithLike(itemAttribute));
        } catch (Exception e) {
            log.error("根据条件查询产品属性列表,属性组编码大小写不敏感时，发生未知异常：", e);
            result.setMessage("根据条件查询产品属性列表,属性组编码大小写不敏感发生未知异常：" + e.getMessage());
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
    public ServiceResult<Integer> countItemAttributeWithLike(ItemAttribute itemAttribute) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(itemModel.countItemAttributeWithLike(itemAttribute));
        } catch (Exception e) {
            log.error("根据条件查询产品属性记录数,属性组编码大小写不敏感时，发生未知异常：", e);
            result.setMessage("根据条件查询产品属性记录数,属性组编码大小写不敏感发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> countItemBaseByParamWithLike(ItemBase base) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(itemModel.countItemBaseByParamWithLike(base));
        } catch (Exception e) {
            log.error("根据条件查询产品记录数,,物流编码大小写不敏感时，发生未知异常：", e);
            result.setMessage("根据条件查询产品记录数,物流编码大小写不敏感发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<Payments> getPayment(Integer id) {
        ServiceResult<Payments> result = new ServiceResult<Payments>();
        try {
            result.setResult(itemModel.getPayment(id));
        } catch (Exception e) {
            log.error("根据id查询付款方式时，发生未知异常：", e);
            result.setMessage("根据id查询付款方式发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<Payments> getPaymentByCode(String paymentCode) {
        ServiceResult<Payments> result = new ServiceResult<Payments>();
        try {
            result.setResult(itemModel.getPaymentByCode(paymentCode));
        } catch (Exception e) {
            log.error("根据code查询付款方式时，发生未知异常：", e);
            result.setMessage("根据code查询付款方式发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<NetPoints> getNetPoint(Integer id) {
        ServiceResult<NetPoints> result = new ServiceResult<NetPoints>();
        try {
            result.setResult(itemModel.getNetPoint(id));
        } catch (Exception e) {
            log.error("根据id查询网点时，发生未知异常：", e);
            result.setMessage("根据id查询网点发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<NetPoints> getByNetPointByCode(String netPointCode) {
        ServiceResult<NetPoints> result = new ServiceResult<NetPoints>();
        try {
            result.setResult(itemModel.getByNetPointByCode(netPointCode));
        } catch (Exception e) {
            log.error("根据netPointCode查询网点时，发生未知异常：", e);
            result.setMessage("根据netPointCode查询网点发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

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

    public ServiceResult<List<EisExternalSku>> getBySku(String sku) {
        ServiceResult<List<EisExternalSku>> result = new ServiceResult<List<EisExternalSku>>();
        try {
            result.setResult(itemModel.getBySku(sku));
        } catch (Exception e) {
            log.error("根据sku查询物料对照信息时，发生未知异常：", e);
            result.setMessage("根据sku查询物料对照信息发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<EisExternalSku> getBySkuType(String sku, String type) {
        ServiceResult<EisExternalSku> result = new ServiceResult<EisExternalSku>();
        try {
            result.setResult(itemModel.getBySkuType(sku, type));
        } catch (Exception e) {
            log.error("根据sku和type查询物料对照信息时，发生未知异常：", e);
            result.setMessage("根据sku和type查询物料对照信息发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<EisExternalSku> getByExternalSku(String EisExternalSku) {
        ServiceResult<EisExternalSku> result = new ServiceResult<EisExternalSku>();
        try {
            result.setResult(itemModel.getByExternalSku(EisExternalSku));
        } catch (Exception e) {
            log.error("根据EisExternalSku查询物料对照信息时，发生未知异常：", e);
            result.setMessage("根据EisExternalSku查询物料对照信息发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    //-------------------------   ioc   ----------------------
    public void setItemModel(ItemModel itemModel) {
        this.itemModel = itemModel;
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

    /**
     * 查询ItemBase全部信息
     * @param material_id 物料号
     * @return list of ItemBase
     */
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

    /**
     * 通过ValueSetId检索ItemAttribute List
     * @param valueSetId 属性标识
     * @return list of ItemAttribute
     */
    @Override
    public ServiceResult<List<ItemAttribute>> getByValueSetId(String valueSetId) {
        ServiceResult<List<ItemAttribute>> result = new ServiceResult<List<ItemAttribute>>();
        try {
            result.setResult(itemModel.getByValueSetId(valueSetId));
        } catch (Exception e) {
            log.error("检索ItemAttribute List，发生未知异常：", e);
            result.setMessage("检索ItemAttribute List，发生未知异常：" + e.getMessage());
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
    public ServiceResult<List<LesFiveYardInfo>> selectLesFiveYards(Map<String, Object> params) {
        ServiceResult<List<LesFiveYardInfo>> result = new ServiceResult<List<LesFiveYardInfo>>();
        try {
            result.setResult(itemModel.selectLesFiveYards(params));
        } catch (Exception e) {
            log.error("取les5码失败：", e);
            result.setMessage("取les5码失败：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ServiceResult<List<ItemBase>> getItemBaseListByProductGroup(List<String> depList) {
        ServiceResult<List<ItemBase>> result = new ServiceResult<List<ItemBase>>();
        try {
            result.setResult(itemModel.getItemBaseListByDepList(depList));
        } catch (Exception e) {
            log.error("getItemBaseListByProductGroup:根据产品组查询物料List发生异常，", e);
            result.setMessage("根据产品组查询物料列表发生异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
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

    @Override
    public ServiceResult<List<Map<String, Object>>> getOnSaleBigProducts() {
        ServiceResult<List<Map<String, Object>>> result = new ServiceResult<List<Map<String, Object>>>();
        try {
            result.setSuccess(true);
            result.setResult(itemModel.getOnSaleBigProducts());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("获取大家电的物料编号、销售指导价、是否上架失败");
            log.error("获取大家电的物料编号、销售指导价、是否上架失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<List<Map<String, Object>>> getOnSaleProducts(Map<String, Object> paramMap) {
        ServiceResult<List<Map<String, Object>>> result = new ServiceResult<List<Map<String, Object>>>();
        try {
            result.setSuccess(true);
            result.setResult(itemModel.getOnSaleProducts(paramMap));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("获取所有上架商品信息失败：" + e.getMessage());
            log.error("获取所有上架商品信息失败：" + e.getMessage());
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