package com.haier.afterSale.service;

import java.util.List;
import java.util.Map;

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

public interface ItemServiceNew {
    void hello();

    /**
     * 根据id获取产品对象
     * @param id
     * @return
     */
    ServiceResult<ProductsNew> getProductById(Integer id);

    /**
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

    /**
     * 获取品牌
     * @param brandId
     * @return
     */
    ServiceResult<Brands> getBrands(Integer brandId);

    /**
     * 获取品类
     * @param id
     * @return
     */
    ServiceResult<ProductCates> getProductCates(Integer id);

    /**
     * 根据sku列表，取所有产品列表(只查询上架的sku)
     * @param skuList
     * @return
     */
    ServiceResult<List<ProductBase>> getProductListBySkus(List<String> skuList);

    /**
     * 取所有产品对象
     * @return
     */
    ServiceResult<List<ProductsNew>> getAllProductInfo();

    /**
     * 根据scode 查询所有Products, scode传入参数为空，则查询所有scode不为空的记录
     * @param scode
     * @return
     */
    ServiceResult<List<ProductBase>> getAllProductsBysCode(String scode);

    /**
     * 查询所有上架商品
     * @return
     */
    ServiceResult<List<ProductBase>> getAllOnSaleProducts();
    
    /**
     * 查询所有商品
     * @return
     */
    ServiceResult<List<ProductBase>> getAllProducts();

    /**
     * 获取所有品类
     * @return
     */
    ServiceResult<List<ProductCates>> getAllProductCates();

    /**
     * 获取子商品类目
     * @param parentId
     * @return
     */
    ServiceResult<List<ProductCates>> getChildrenProductCates(Integer parentId);

    /**
     * 保存产品属性(ItemAttribute)
     * 如果已经存在：更新
     * 如果不存在：插入
     * @param itemAttribute
     * @return
     */
    ServiceResult<Boolean> saveItemAttribute(ItemAttribute itemAttribute);

    /**
     * 保存物料基本信息
     * 如果已经存在：更新
     * 如果不存在：插入
     * @param itemBase
     * @return
     */
    ServiceResult<Boolean> saveItemBase(ItemBase itemBase);

    /**
     * 根据value和valueSetId取得产品属性(ItemAttribute)
     * @param valueSetId
     * @param value
     * @return
     */
    ServiceResult<ItemAttribute> getItemAttributeByValueSetIdAndValue(String valueSetId,
                                                                      String value);

    /**
     * 根据物料sku取得物料基本信息
     * @param materialCode
     * @return
     */
    ServiceResult<ItemBase> getItemBaseBySku(String sku);

    /**
     * 取得信息不完整的物料基本(ItemBase)信息
     * @return
     */
    ServiceResult<List<ItemBase>> getIncompleteItemBaseList();

    /**
     * 根据sku，获取产品基础信息
     * @param sku
     * @return
     */
    ServiceResult<ProductBase> getPorductBaseBySku(String sku);

    /**
     * 根据条件查询产品列表,物流编码大小写不敏感
     * @param base
     * @return
     */
    ServiceResult<List<ItemBase>> queryItemBaseByParamWithLike(ItemBase base);

    /**
     * 查询所有CBS品类
     * @return
     */
    ServiceResult<List<String>> getAllCbsCategory();

    /**
     * 根据条件查询产品属性列表,属性组编码大小写不敏感
     * @param itemAttribute
     * @return
     */
    ServiceResult<List<ItemAttribute>> queryItemAttributeWithLike(ItemAttribute itemAttribute);

    /**
     * 根据CBS品类查询产品组
     * @param cbsCategory
     * @return
     */
    ServiceResult<List<ItemAttribute>> queryProductGroupByCbsCategory(String cbsCategory);

    /**
     * 根据id更新产品信息
     * @param base
     * @return
     */
    ServiceResult<Boolean> updateItemBaseById(ItemBase base);

    /**
     * 根据条件查询产品属性记录数,属性组编码大小写不敏感
     * @param itemAttribute
     * @return
     */
    ServiceResult<Integer> countItemAttributeWithLike(ItemAttribute itemAttribute);

    /**
     * 根据条件查询产品记录数,物流编码大小写不敏感
     * @param base
     * @return
     */
    ServiceResult<Integer> countItemBaseByParamWithLike(ItemBase base);

    /**
     * 根据id查询付款方式
     * @param id
     * @return
     */
    ServiceResult<Payments> getPayment(Integer id);

    /**
     * 根据code查询付款方式
     * @param paymentCode
     * @return
     */
    ServiceResult<Payments> getPaymentByCode(String paymentCode);

    /**
     * 根据id查询网点
     * @param id
     * @return
     */
    ServiceResult<NetPoints> getNetPoint(Integer id);

    /**
     * 根据netPointCode查询网点
     * @param netPointCode
     * @return
     */
    ServiceResult<NetPoints> getByNetPointByCode(String netPointCode);

    /**
     * 由网点8码查询网点信息
     * @param netPointN8
     * @return
     */
    ServiceResult<NetPoints> getByNetPointN8(String netPointN8);

    /**
     * 根据sku更新销量
     * @param products
     * @return
     */
    ServiceResult<Boolean> updateSaleNumBySku(ProductsNew products);

    /**
     * 根据sku查询物料对照信息
     * @param sku
     * @return
     */
    ServiceResult<List<EisExternalSku>> getBySku(String sku);

    /**
     * 根据sku和type查询物料对照信息
     * @param sku,type
     * @return
     */
    ServiceResult<EisExternalSku> getBySkuType(String sku, String type);

    /**
     * 根据externalSku查询物料对照信息
     * @param externalSku
     * @return
     */
    ServiceResult<EisExternalSku> getByExternalSku(String externalSku);

    /**
     * 查询ItemBase全部信息
     * @param material_id 物料号
     * @return ServiceResult<List<ItemBase>> result
     */
    ServiceResult<List<ItemBase>> findItemBaseByMaterialId(String material_id);

    /**
     * 通过ValueSetId检索ItemAttribute List
     * @param valueSetId
     * @return
     */
    ServiceResult<List<ItemAttribute>> getByValueSetId(String valueSetId);

    ServiceResult<List<String>> getCbsCategoryByProductGroup(Map<String, Object> map);

    /**
     * @param params
     * @return
     */
    ServiceResult<List<LesFiveYardInfo>> selectLesFiveYards(Map<String, Object> params);

    /**
     * 通过产品组获取物料
     * @return
     */
    ServiceResult<List<ItemBase>> getItemBaseListByProductGroup(List<String> depList);

    /**
     * 手动同步物料基本信息
     * @param itemBase
     * @param modifier
     * @return
     */
    ServiceResult<Boolean> manualSyncMdmBySku(ItemBase itemBase, String modifier);

    /**
     * 手动添加物料基本信息
     * @param itemBase
     * @param modifier
     * @return
     */
    ServiceResult<Boolean> insertItemBaseInfo(ItemBase itemBase);

    /**
     * 通过省、市、区查询国标码<br/>
     * 输入省名称时，返回省的国标码<br/>
     * 输入省、市名称时，返回市的国标码<br/>
     * 输入省、市、区名称时，返回区的国标码<br/>
     * @param params
     * @return
     */
    public ServiceResult<String> getRegionsCode(Map<String, Object> params);

    /**
     * 获取大家电的物料编号、销售指导价、是否上架（0-下架；1-上架）<br/>
     * key:<br/>
     * &nbsp; sku 物料编码<br/>
     * &nbsp; saleGuidePrice 销售指导价<br/>
     * &nbsp; onSale 是否上架（0-下架；1-上架）<br/>
     * @returns
     *  返回key所对应的value值
     */
    public ServiceResult<List<Map<String, Object>>> getOnSaleBigProducts();

    /**
     * 获取所有商品的物料编号、品牌id、品牌名称、品类id、品类名称<br/>
     * key:<br/>
     * &nbsp; sku 物料编码<br/>
     * &nbsp; productName 商品名称<br/>
     * &nbsp; brandId 品牌id<br/>
     * &nbsp; brandName 品牌名称<br/>
     * &nbsp; cateId 品类id<br/>
     * &nbsp; cateName 品类名称<br/>
     * @returns
     *  返回key所对应的value值
     */
    public ServiceResult<List<Map<String, Object>>> getOnSaleProducts(Map<String, Object> paramMap);

}