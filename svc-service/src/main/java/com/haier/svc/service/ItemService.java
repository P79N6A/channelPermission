package com.haier.svc.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.distribute.data.model.ProductCates;
import com.haier.shop.model.*;
import com.haier.stock.model.InvStockAge;

/**
 * @author 李超
 */
public interface ItemService {
	
    /**`
     * 查询所有CBS品类
     * @return
     */
    ServiceResult<List<String>> getAllCbsCategory();

    /**
     * 根据条件查询产品属性列表,属性组编码大小写不敏感
     * @param itemAttribute
     * @return
     */
    JSONObject queryItemAttribute(ItemAttribute itemAttribute);

    public abstract ServiceResult<List<LesFiveYardInfo>> selectLesFiveYards(Map<String, Object> paramMap);

    /**
     * 由网点8码查询网点信息
     * @param netPointN8
     * @return
     */
    ServiceResult<NetPoints> getByNetPointN8(String netPointN8);
/*
     * 根据sku获取产品对象
     * @param sku
     * @return
     */
    ServiceResult<ProductsNew> getProductBySku(String sku);
    /**
     * 根据标识获取产品类型
     * @param typeId
     * @return
     */
    ServiceResult<ProductTypesNew> getProductType(Integer typeId);
    ServiceResult<ProductTypesNew> getProductTypeNew(Integer typeId);
    /**
     * 根据sku更新销量
     * @param products
     * @return
     */
    ServiceResult<Boolean> updateSaleNumBySku(ProductsNew products);
    /**
     * 查询所有上架商品
     * @return
     */
    ServiceResult<List<ProductBase>> getAllOnSaleProducts();
    
    /**
     * 根据scode 查询所有Products, scode传入参数为空，则查询所有scode不为空的记录
     * @param scode
     * @return
     */
    ServiceResult<List<ProductBase>> getAllProductsBysCode(String scode);
    
    /**
     * 根据sku，获取产品基础信息
     * @param sku
     * @return
     */
    ServiceResult<ProductBase> getPorductBaseBySku(String sku);
    
    
    /**
     * 根据物料sku取得物料基本信息
     * @param materialCode
     * @return
     */
    ServiceResult<ItemBase> getItemBaseBySku(String sku);
    
    /**
     * 根据value和valueSetId取得产品属性(ItemAttribute)
     * @param valueSetId
     * @param value
     * @return
     */
    ServiceResult<ItemAttribute> getItemAttributeByValueSetIdAndValue(String valueSetId,
                                                                      String value);
    /**
     * 手动添加物料基本信息
     * @param itemBase
     * @return
     */
    ServiceResult<Boolean> insertItemBaseInfo(ItemBase itemBase);

    ServiceResult<Boolean> updateItemBaseById(ItemBase base);
    /**
     * 根据id获取产品对象
     * @param id
     * @return
     */

    ServiceResult<ProductsNew> getProductById(Integer id);
    ServiceResult<List<ProductCates>> getChildrenProductCates(Integer parentId);

    /**
     * 查询所有商品
     * @return
     */
    ServiceResult<List<ProductBase>> getAllProducts();


	ServiceResult<List<ItemBase>> getType();


	ServiceResult<List<ItemBase>> getProductBaseData(Map<String, Object> params);

    /**
     * 查询ItemBase全部信息
     * @param material_id 物料号
     * @return ServiceResult<List<ItemBase>> result
     */
    ServiceResult<List<ItemBase>> findItemBaseByMaterialId(String material_id);

    ServiceResult<List<String>> getCbsCategoryByProductGroup(Map<String, Object> map);


    /**
     * 根据CBS品类查询产品组
     * @param cbsCategory
     * @return
     */
    ServiceResult<List<ItemAttribute>> queryProductGroupByCbsCategory(String cbsCategory);

    List<ItemBase> getIncompleteItemBaseList();

    int updateMtlInfoForStockAge(InvStockAge stockAge);

    ServiceResult<String> getRegionsCode(Map<String, Object> param);

	ServiceResult<Boolean> saveItemAttribute(ItemAttribute value);

	ServiceResult<Boolean> saveItemBase(ItemBase value);

	ServiceResult<Boolean> manualSyncMdmBySku(ItemBase mtl, String modifier);
}
