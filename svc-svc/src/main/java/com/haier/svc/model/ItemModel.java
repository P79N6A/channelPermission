package com.haier.svc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.BusinessException;
import com.haier.common.util.StringUtil;
import com.haier.distribute.data.model.Brands;
import com.haier.distribute.data.model.ProductCates;
import com.haier.distribute.data.service.BrandsService;
import com.haier.distribute.data.service.ProductCatesService;
import com.haier.eis.model.EisExternalSku;
import com.haier.eis.service.EisExternalSkuService;
import com.haier.shop.model.LesFiveYardInfo;
import com.haier.purchase.data.service.PurchaseCgoService;
import com.haier.shop.service.PurchaseLesFiveYardsService;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.NetPoints;
import com.haier.shop.model.Payments;
import com.haier.shop.model.ProductBase;
import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.ProductsNew;
import com.haier.shop.model.Regions;
import com.haier.shop.service.NetPointsService;
import com.haier.shop.service.PaymentsService;
import com.haier.shop.service.ProductTypesService;
import com.haier.shop.service.ProductsNewService;
import com.haier.shop.service.RegionsService;
import com.haier.shop.service.ShopItemAttributeService;
import com.haier.shop.service.ShopItemBaseService;


/**
 * Created by Administrator on 2017/12/13 0013.
 */
@Service("itemModel")
public class ItemModel {
	@Autowired
	PurchaseCgoService purchaseCgoService;
    @Autowired
    private NetPointsService netPointsService;
    @Autowired
    private BrandsService brandsService;
    @Autowired
    private PaymentsService paymentsService;
    @Autowired
    private EisExternalSkuService eisExternalSkuService;
    @Autowired
    private ProductTypesService productTypesService;
    @Autowired
    private ShopItemBaseService shopItemBaseService;
    @Autowired
    private ShopItemAttributeService shopItemAttributeService;
    @Autowired
    private ProductsNewService productsNewService;
    @Autowired
    private ProductCatesService productCatesService;
    private PurchaseLesFiveYardsService purchaseLesFiveYardsService;
    @Autowired
    private RegionsService regionsService;
    
    /**
     * 由网点8码查询网点信息
     * @param
     * @return 网点信息
     */
    public NetPoints getByNetPointN8(String netPointN8) {
        if (StringUtil.isEmpty(netPointN8)) {
            return null;
        }
        return netPointsService.getByNetPointN8(netPointN8);
    }
    /**
     * 根据sku获取产品对象
     * @param sku 物料号
     * @return 商品信息
     */

    public ProductsNew getProductsBySku(String sku) {
        return productsNewService.getBySku(sku);
    }
    public Brands getBrandsById(Integer brandId) {
        return brandsService.get(brandId);
    }

    public Payments getPaymentByCode(String paymentCode) {
        if (paymentCode == null || paymentCode.equals("")) {
            return null;
        }
        return paymentsService.getByCode(paymentCode);
    }

    public NetPoints getNetPoint(Integer id) {
        if (id == null) {
            return null;
        }
        return netPointsService.get(id);
    }

    public EisExternalSku getBySkuType(String sku, String type) {
        if (sku == null || type == null) {
            return null;
        }
        return eisExternalSkuService.getBySkuType(sku, type);
    }
     public ProductTypesNew getProductType(Integer typeId) {
         return productTypesService.getById(typeId);
     }
    public ProductTypesNew getProductTypeNew(Integer typeId) {
        return productTypesService.getByIdNew(typeId);
    }
     
     /**
      * 根据sku更新销量
      * @param products 要更新的商品信息
      */
     public void updateSaleNumBySku(ProductsNew products) {
         productsNewService.updateSaleNumBySku(products);
     }
     

     /**
      * 查询所有上架商品
      * @return list of onSale ProductBase
      */
     public List<ProductBase> getAllOnSaleSkuList() {
         return productsNewService.getAllSkusListBySale("1");
     }
     
     /**
      * 根据scode 查询所有Products, scode传入参数为空，则查询所有scode不为空的记录
      * @param scode 库位
      * @return list of ProductBase
      */
     public List<ProductBase> getAllProductsBysCode(String scode) {
         return productsNewService.getAllProductsBysCode(scode);
     } 
     /**
      * 根据sku，获取对应的产品基础信息
      * @param sku 物料
      * @return ProductBase
      */
     public ProductBase getProductBaseBySku(String sku) {
         return productsNewService.getBaseBySku(sku);
     }
     
     
     

     public ProductCates getProductCates(Integer id) {
         return productCatesService.get(id);
     }
     
     /**
      * 取所有产品对象
      * @return list of Products
      */
     public List<ProductsNew> getAllProductInfo() {
         return productsNewService.getAllProductInfo();
     }
     /**
      * 获取所有品类
      * @return lsit of ProductCates
      */
     public List<ProductCates> getAllProductCates() {
         return productCatesService.getAllProductCates();
     }

     /**
      * 保存产品属性(ItemAttribute) 
      * 如果已经存在：更新 
      * 如果不存在：插入
      * @param attribute 商品属性
      */
     public void saveItemAttribute(ItemAttribute attribute) {
         List<ItemAttribute> valueList = shopItemAttributeService.getByValueAndValueSetId(attribute);
         if (valueList != null && valueList.size() > 0) {
             attribute.setId(valueList.get(0).getId());
         }
         if (attribute.getId() != null) {
             shopItemAttributeService.update(attribute);
         } else {
             shopItemAttributeService.insert(attribute);
         }
     }
     
     /**
      * 保存物料基本信息 
      * 如果已经存在：更新
      * 如果不存在：插入
      * @param itemBase 物料信息
      */
     public void saveItemBase(ItemBase itemBase) {
         ItemBase tmpItemBase = null;
         //设置查询为非删除数据,有可能更新为已删除
         Integer newDeleteFlag = itemBase.getDeleteFlag();
         itemBase.setDeleteFlag(0);
         List<ItemBase> mtlList = shopItemBaseService.getBySku(itemBase);
         itemBase.setDeleteFlag(newDeleteFlag);
         if (mtlList != null && mtlList.size() > 0) {
             tmpItemBase = mtlList.get(0);
             //如果设置了不自动更新,不更新
             if (tmpItemBase.getIsAutoUpdate() != null && tmpItemBase.getIsAutoUpdate() == 0) {
                 return;
             }
             itemBase.setId(tmpItemBase.getId());
         }

         if (tmpItemBase != null) {
             if (null == tmpItemBase.getIsAutoUpdate()) {
                 itemBase.setIsAutoUpdate(1);
             } else {
                 itemBase.setIsAutoUpdate(tmpItemBase.getIsAutoUpdate());
             }
             if (null == tmpItemBase.getStatus()) {
                 itemBase.setStatus(0);
             } else {
                 itemBase.setStatus(tmpItemBase.getStatus());
             }
             if (null == itemBase.getModifier()) {
                 itemBase.setModifier("system");
             }
             itemBase.setLastUpd(new Date());
             shopItemBaseService.update(itemBase);
         } else {
             itemBase.setIsAutoUpdate(1);
             itemBase.setStatus(0);
             if (!StringUtil.isEmpty(itemBase.getSaleChar())
                 && itemBase.getSaleChar().length() > 1) {
                 itemBase.setSaleChar(itemBase.getSaleChar().trim().substring(0, 1));
             }
             shopItemBaseService.insert(itemBase);
         }
     }

     /**
      * 根据产品属性值(value)和属性类别(valueSetId)取得产品属性
      * @param valueSetId 属性标识
      * @param value 属性值
      * @return 属性信息
      */
     public ItemAttribute getItemAttributeByValueSetIdAndValue(String valueSetId, String value) {
         ItemAttribute param = new ItemAttribute();
         param.setValue(value);
         param.setValueSetId(valueSetId);
         param.setDeleteFlag(0);
         List<ItemAttribute> list = shopItemAttributeService.getByValueAndValueSetId(param);
         if (list == null || list.size() == 0) {
             return null;
         }
         return list.get(0);
     }

     /**
      * 根据物料SKU取得物料基本信息
      * @param sku 物料
      * @return 物料信息
      */
     public ItemBase getItemBaseBySku(String sku) {
         ItemBase param = new ItemBase();
         param.setMaterialCode(sku);
         param.setDeleteFlag(0);
         List<ItemBase> list = shopItemBaseService.getBySku(param);
         if (list == null || list.size() == 0) {
             return null;
         }
         return list.get(0);
     }

     /**
      * 取得信息不完整的物料基本信息
      * @return list of ItemBase
      */
     public List<ItemBase> getIncompleteItemBaseList() {
         return shopItemBaseService.getIncompleteItemBaseList();
     }

     /**
      * 根据sku列表，获取对应产品列表
      * @param skuList list of sku
      * @return list of ProductBase
      */
     public List<ProductBase> getProductListBySkus(List<String> skuList) {
         return productsNewService.getListBySkus(skuList);
     }
     /**
      * 根据条件查询产品列表,物流编码大小写不敏感
      * @param base {"Department": "产品组,"MaterialCode": "物料","DeleteFlag": "删除标识"}
      * @return search result
      */
     public List<ItemBase> queryItemBaseByParamWithLike(ItemBase base) {
         if (base == null) {
             return null;
         }
         if (!StringUtils.isBlank(base.getDepartment())) {
             base.setDepartments(new String[] { base.getDepartment() });
         }
         if (!StringUtils.isBlank(base.getMaterialCode())) {
             base.setMaterialCode(base.getMaterialCode().toLowerCase());
         }
         if (null == base.getDeleteFlag()) {
             base.setDeleteFlag(0);
         }
         return shopItemBaseService.queryItemBaseByParamWithLike(base);
     }
     /**
      * 查询所有CBS品类
      * @return 品类
      */
     public List<String> getAllCbsCategory() {
         return shopItemAttributeService.getAllCbsCategory();
     }
     /**
      * 根据条件查询产品属性列表,属性组编码大小写不敏感
      * @param itemAttribute 查询条件
      * @return 查询结果
      */
     public List<ItemAttribute> queryItemAttributeWithLike(ItemAttribute itemAttribute) {
         if (itemAttribute == null) {
             return null;
         }
         if (!StringUtils.isBlank(itemAttribute.getValueSetId())) {
             itemAttribute.setValueSetId(itemAttribute.getValueSetId().toLowerCase());
         }
         if (null == itemAttribute.getActiveFlag()) {
             itemAttribute.setActiveFlag(1);
         }
         if (null == itemAttribute.getDeleteFlag()) {
             itemAttribute.setDeleteFlag(0);
         }
         return shopItemAttributeService.queryItemAttributeWithLike(itemAttribute);
     }

     /**
      * 根据条件查询产品属性记录数,属性组编码大小写不敏感
      * @param itemAttribute 查询条件
      * @return 查询结果
      */
     public Integer countItemAttributeWithLike(ItemAttribute itemAttribute) {
         if (itemAttribute == null) {
             return null;
         }
         if (!StringUtils.isBlank(itemAttribute.getValueSetId())) {
             itemAttribute.setValueSetId(itemAttribute.getValueSetId().toLowerCase());
         }
         if (null == itemAttribute.getActiveFlag()) {
             itemAttribute.setActiveFlag(1);
         }
         if (null == itemAttribute.getDeleteFlag()) {
             itemAttribute.setDeleteFlag(0);
         }
         return shopItemAttributeService.countItemAttributeWithLike(itemAttribute);
     }

     /**
      * 根据CBS品类查询产品组
      * @param cbsCategory 品类
      * @return 对应的属性信息
      */
     public List<ItemAttribute> queryProductGroupByCbsCategory(String cbsCategory) {
         return shopItemAttributeService.queryProductGroupByCbsCategory(cbsCategory);
     }

     /**
      * 根据id更新产品信息
      * @param base 要更新的物料信息
      * @return 更新数量
      */
     public Integer updateItemBaseById(ItemBase base) {
         if (base == null || base.getId() == null) {
             return null;
         }
         base.setLastUpd(new Date());
         return shopItemBaseService.updateNotNull(base);
     }

     /**
      * 根据条件查询产品记录数,物流编码大小写不敏感
      * @param base {"Department": "产品组,"MaterialCode": "物料","DeleteFlag": "删除标识"}
      * @return 符合条件的物料信息数
      */
     public Integer countItemBaseByParamWithLike(ItemBase base) {
         if (base == null) {
             return null;
         }
         if (!StringUtils.isBlank(base.getDepartment())) {
             base.setDepartments(new String[] { base.getDepartment() });
         }
         if (!StringUtils.isBlank(base.getMaterialCode())) {
             base.setMaterialCode(base.getMaterialCode().toLowerCase());
         }
         if (null == base.getDeleteFlag()) {
             base.setDeleteFlag(0);
         }
         return shopItemBaseService.countItemBaseByParamWithLike(base);
     }
     
     public Payments getPayment(Integer id) {
         if (id == null) {
             return null;
         }
         return paymentsService.get(id);
     }
     

     public NetPoints getByNetPointByCode(String netPointCode) {
         if (StringUtil.isEmpty(netPointCode)) {
             return null;
         }
         return netPointsService.getByNetPointByCode(netPointCode);
     }
     

     public List<EisExternalSku> getBySku(String sku) {
         if (sku == null) {
             return null;
         }
         return eisExternalSkuService.getBySku(sku);
     }

     public EisExternalSku getByExternalSku(String externalSku) {
         if (externalSku == null) {
             return null;
         }
         return eisExternalSkuService.getByExternalSku(externalSku);
     }
     /**
      * 查询ItemBase全部信息
      * @param material_id 物料号
      * @return 物料信息
      */
     public List<ItemBase> findItemBaseByMaterialId(String material_id) {

         return shopItemBaseService.findItemBaseByMaterialId(material_id);
     }
     /**
      * 通过ValueSetId检索ItemAttribute List
      * @param valueSetId 属性标识
      * @return 属性信息
      */
     public List<ItemAttribute> getByValueSetId(String valueSetId) {
         return shopItemAttributeService.getByValueSetId(valueSetId);
     }
     public List<String> getCbsCategoryByProductGroup(Map<String, Object> map) {
         return shopItemAttributeService.getCbsCategoryByProductGroup(map);
     }

     public List<LesFiveYardInfo> selectLesFiveYards(Map<String, Object> params) {
         return purchaseLesFiveYardsService.selectLesFiveYards(params);
     }
     
     /**
      * 查询 -- 根据产品组获取ItemBase
      * @param depList list of 产品组
      * @return query result 
      */
     public List<ItemBase> getItemBaseListByDepList(List<String> depList) {
         if (depList == null) {
             return null;
         }
         return shopItemBaseService.getItemListByDepList(depList);
     }
     
     /**
      * 手动同步物料基本信息
      * @param itemBase 物料信息
      * @param modifier 修改人
      */
     public Integer manualSyncMdmBySku(ItemBase itemBase, String modifier) {
         ItemBase tmpItemBase = null;
         //设置查询为非删除数据,有可能更新为已删除
         Integer newDeleteFlag = itemBase.getDeleteFlag();
         itemBase.setDeleteFlag(0);
         List<ItemBase> mtlList = shopItemBaseService.getBySku(itemBase);
         itemBase.setDeleteFlag(newDeleteFlag);
         if (mtlList != null && mtlList.size() > 0) {
             tmpItemBase = mtlList.get(0);
             //            //如果设置了不自动更新,不更新
             //            if (tmpItemBase.getIsAutoUpdate() != null && tmpItemBase.getIsAutoUpdate() == 0) {
             //                return;
             //            }
             itemBase.setId(tmpItemBase.getId());
         }

         if (tmpItemBase != null) {
             if (null == tmpItemBase.getIsAutoUpdate()) {
                 itemBase.setIsAutoUpdate(1);
             } else {
                 itemBase.setIsAutoUpdate(tmpItemBase.getIsAutoUpdate());
             }
             if (null == tmpItemBase.getStatus()) {
                 itemBase.setStatus(0);
             } else {
                 itemBase.setStatus(tmpItemBase.getStatus());
             }
             if (null == itemBase.getModifier()) {
                 itemBase.setModifier(modifier);
             }
             itemBase.setLastUpd(new Date());
             return shopItemBaseService.updateNotNull(itemBase);
         }
         return 0;
         //        else {
         //            itemBase.setIsAutoUpdate(1);
         //            itemBase.setStatus(0);
         //            shopItemBaseService.insert(itemBase);
         //        }
     }

     /**
      * 手动添加物料基本信息
      * @param itemBase 物料信息
      */
     public void insertItemBaseInfo(ItemBase itemBase) {
         saveItemBase(itemBase);
     }
     
     /**
      * 通过省、市、区查询国标码
      * @param params 
      * {
      *  "provinceName": "北京",
      *  "cityName": "北京",
      *  "areaName": "海淀区"
      *}
      * @return 国标码
      */
     public String getRegionsCode(Map<String, Object> params) {

         if (params == null || params.isEmpty()) {
             throw new BusinessException("参数不允许为空！");
         }

         String provinceName = (String) params.get("provinceName");
         String cityName = (String) params.get("cityName");
         String areaName = (String) params.get("areaName");
         if (StringUtil.isEmpty(provinceName)) {
             throw new BusinessException("省不允许为空！");
         }

         //通过省份查询Region
         params.put("regionName", provinceName);
         params.put("parentId", 0);
         Regions provinceRegion = regionsService.getRegions(params);
         if (provinceRegion == null) {
             throw new BusinessException("对应的省份不存在！");
         }

         //市、区为空，则返回省份的国标码
         if (StringUtil.isEmpty(cityName) && StringUtil.isEmpty(areaName)) {
             return provinceRegion.getCode();
         }

         if (StringUtil.isEmpty(cityName) && !StringUtil.isEmpty(areaName)) {
             throw new BusinessException("查询区的国标码时，省、市都不允许为空！");
         }

         //通过省、市查询Region
         params.remove("regionName");
         params.remove("parentId");
         params.put("regionName", cityName);
         params.put("parentId", provinceRegion.getId());
         Regions cityRegion = regionsService.getRegions(params);
         if (cityRegion == null) {
             throw new BusinessException("对应的市不存在！");
         }

         //区为空，则返回市的国标码
         if (StringUtil.isEmpty(areaName)) {
             return cityRegion.getCode();
         }

         //通过省、市、区查询Region
         params.remove("regionName");
         params.remove("parentId");
         params.put("regionName", areaName);
         params.put("parentId", cityRegion.getId());
         Regions areaRegion = regionsService.getRegions(params);
         if (areaRegion == null) {
             throw new BusinessException("对应地区不存在！");
         }

         return areaRegion.getCode();
     }
     
     /**
      * 获取大家电的物料编号、销售指导价、是否上架（0-下架；1-上架），不包含O2O商品
      * @return
      */
     public List<Map<String, Object>> getOnSaleBigProducts() {

         List<ProductsNew> products = productsNewService.getOnSaleBigProducts();
         if (products == null || products.size() <= 0) {
             return null;
         }
         List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
         for (ProductsNew product : products) {
             Map<String, Object> map = new HashMap<String, Object>();
             map.put("sku", product.getSku());//物料编码
             map.put("saleGuidePrice", String.valueOf(product.getSaleGuidePrice()));//销售指导价
             map.put("onSale", product.getOnSale());//是否上架（0-下架；1-上架）
             result.add(map);
         }
         return result;

     }

    /**
     * 根据id获取产品对象
     * @param id 商品id
     * @return 商品信息
     */

    public ProductsNew getProductsById(Integer id) {
        return productsNewService.get1(id);
    }
    /**
     * 获取所有品类
     * @return list of ProductCates
     */
    public List<ProductCates> getAllChildrenProductCates(Integer parentId) {
        return productCatesService.getAllChildren(parentId);
    }

     
     /**
      * 获取所有商品的品牌id，品牌名称、品类id，品类名称、sku
      * @return
      */
     public List<Map<String, Object>> getOnSaleProducts(Map<String, Object> paramMap) {

         List<ProductBase> productBases = productsNewService.getAllSkusList(paramMap);//获取所有sku
         if (productBases == null || productBases.isEmpty()) {
             throw new BusinessException("没有上架的商品");
         }
         List<Brands> brands = brandsService.getAllBrands();//获取所有品牌
         if (brands == null || brands.isEmpty()) {
             throw new BusinessException("没有获取到品牌信息");
         }
         Map<Integer, String> brandIdMap = new HashMap<Integer, String>();//id和name
         //        Map<String, String> brandCodeMap = new HashMap<String, String>();//code和name
         for (Brands brand : brands) {
             brandIdMap.put(brand.getId(), brand.getBrandName());
             //            brandCodeMap.put(brand.getBrandCode(), brand.getBrandName());
         }
         List<ProductCates> productCates = productCatesService.getAllProductCates();//获取所有品类
         if (productCates == null || productCates.isEmpty()) {
             throw new BusinessException("没有获取到品类信息");
         }
         Map<Integer, String> idNameMap = new HashMap<Integer, String>();
         Map<Integer, Integer> idParentIdMap = new HashMap<Integer, Integer>();
         for (ProductCates cates : productCates) {
             idNameMap.put(cates.getId(), cates.getCateName());//id和name
             idParentIdMap.put(cates.getId(), cates.getParentId());//id和parentId
         }

         List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
         for (ProductBase product : productBases) {
             Map<String, Object> map = new HashMap<String, Object>();
             map.put("flag", "S");//成功标识
             map.put("error", "");//错误信息
             map.put("sku", product.getSku());//物料编码
             map.put("productName", product.getProductName());//商品名称
             map.put("brandId", product.getBrandId());//品牌id
             map.put("brandName ", brandIdMap.get(product.getBrandId()));//品牌名称

             Integer cateId = getParentId(idParentIdMap, product.getProductCateId());
             if (cateId == null) {
                 map.put("cateId", "");
                 map.put("cateName", "");
             } else {
                 map.put("cateId", cateId);//品类id
                 map.put("cateName", idNameMap.get(cateId));//品类名称
             }

             map.put("onSale", product.getOnSale());//1上架0下架
             result.add(map);
         }
         return result;

     }
     
     /**
      * 查询所有商品
      * @return list of ProductBase
      */
     public List<ProductBase> getAllSkuList() {
         return productsNewService.getAllSkusListBySale(null);
     }
     /**
      * 获取子品类id和子品类名称对应父品类id和父品类名称
      * @param idParentIdMap
      * @param id
      * @return
      */
     private Integer getParentId(Map<Integer, Integer> idParentIdMap, Integer id) {
         if (id == null) {
             return null;
         }
         Integer parentId = idParentIdMap.get(id);
         if (parentId == null) {
             return null;
         }
         if (parentId.intValue() == 0) {
             return id;
         } else {
             return getParentId(idParentIdMap, parentId);
         }
     }

}
