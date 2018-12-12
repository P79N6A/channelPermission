package com.haier.svc.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.distribute.data.model.ProductCates;
import com.haier.purchase.data.model.LesFiveYardInfo;
import com.haier.shop.model.*;

/**
 * @author 李超
 */
public interface ItemService {
	
    /**`
     * 查询所有CBS品类
     * @return
     */
    ServiceResult<List<String>> getAllCbsCategory();
    

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
    
}
