
package com.haier.distribute.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.distribute.data.model.*;
import com.haier.distribute.data.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.service.DistributeCenterPopProductService;

/**
 * 可售商品
 * 孙玉凯 2017-11-06
 *
 * @author sunyukai
 */

/***
 * 主表分页查询
 */
@Service
public class DistributeCenterPopProductServiceImpl implements DistributeCenterPopProductService {
    @Autowired
    private com.haier.distribute.data.service.PopProductService popProductDao;
    @Autowired
    private ProductTypesService productTypesService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private RegionsService regionsService;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private ProductsShopService productsShopService;
    @Autowired
    private ChannelsService channelsService;
    @Autowired
    private BrandsService brandsService;
    @Autowired
    private DistributionInfoService distributionInfoService;
    @Autowired
    private DepartmentProductTypeService departmentProductTypeService;
    @Autowired
    TSaleProductPriceService tSaleProductPriceService;
    @Autowired
    TChanneclsInfoService tChanneclsInfoService;

    /***
     * 主表分页查询
     */
    @Override
    public JSONObject findProductList(PagerInfo pager, Product condition) {
        List<Product> list = popProductDao.selectProductListf(condition, pager.getStart(), pager.getPageSize());
        int total = popProductDao.getPagerCountS(condition);
        JSONArray res = new JSONArray();
        for (Object o : list) {
            Product dto = (Product) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("channelId", dto.getChannelId());
            json.put("sku", dto.getSku());
            json.put("skuName", dto.getChannelName());
            json.put("productTypeId", dto.getProductCode());
            json.put("productCode", dto.getProductCode());
            json.put("onSale", dto.getOnSale());
            json.put("createBy", dto.getCreateBy());
            json.put("createTime", dto.getCreateTime());
            json.put("updateTime", dto.getUpdateTime());
            json.put("remark", dto.getRemark());
            json.put("channelName", dto.getChannelName());
            json.put("productTypeName", dto.getProductTypeName());
            json.put("productCodeName", dto.getProductCodeName());
            json.put("departmentName", dto.getDepartmentName());
            res.add(json);
        }
        return jsonResult(res, total);
    }

    //品类查询
    public JSONArray proucttypeesList() {
        List<Producttypes> list = productTypesService.selectByProducttypes();
        JSONArray res = new JSONArray();
        for (Object o : list) {
            Producttypes dto = (Producttypes) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("typeName", dto.getTypeName());
            res.add(json);
        }
        return res;
    }

    /**
     * 根据id查询商品子表数据.
     *
     * @param saleid
     * @return List<ProductDetailDTO>
     */
    public JSONArray productDetailList(int saleid) {
        List<ProductDetail> list = productDetailService.selectBySaleId(saleid);
        JSONArray res = new JSONArray();
        for (Object o : list) {
            ProductDetail dto = (ProductDetail) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("saleid", dto.getSaleId());
            json.put("regionid", dto.getRegionId());
            json.put("supplyprice", dto.getSupplyPrice());
            json.put("saleprice", dto.getSalePrice());
            json.put("limitprice", dto.getLimitPrice());
            json.put("remark", dto.getRemark());
            json.put("province", dto.getProvince());
            json.put("city", dto.getCity());
            json.put("county", dto.getCounty());
            json.put("pricestarttime", dto.getPriceStartTime());
            json.put("priceendtime", dto.getPriceEndTime());
            res.add(json);
        }
        return res;
    }

    //根据区域查询子表数据
    public JSONArray productDetailcountyList(String startDateTime, String endDateTime, int saleId, String county) {
        List<ProductDetail> list = productDetailService.selectBycounty(startDateTime, endDateTime, saleId, county);
        JSONArray res = new JSONArray();
        for (Object o : list) {
            ProductDetail dto = (ProductDetail) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("saleid", dto.getSaleId());
            json.put("regionid", dto.getRegionId());
            json.put("supplyprice", dto.getSupplyPrice());
            json.put("saleprice", dto.getSalePrice());
            json.put("limitprice", dto.getLimitPrice());
            json.put("remark", dto.getRemark());
            json.put("province", dto.getProvince());
            json.put("city", dto.getCity());
            json.put("county", dto.getCounty());
            json.put("pricestarttime", dto.getPriceStartTime());
            json.put("priceendtime", dto.getPriceEndTime());
            res.add(json);
        }
        return res;
    }

    //根据sku查询品类
    public JSONArray proucttypeesListsku(int id) {
        List<Producttypes> list = productTypesService.selectByProducttypesSku(id);
        JSONArray res = new JSONArray();
        for (Object o : list) {
            Producttypes dto = (Producttypes) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("typeName", dto.getTypeName());
            res.add(json);
        }
        return res;
    }

    private <T> JSONObject jsonResult(List<T> list, long total) {
        JSONObject json = new JSONObject();
        json.put("total", total);
        if (list == null || list.isEmpty()) {
            json.put("rows", new ArrayList<T>());
        } else {
            json.put("rows", list);
        }
        return json;
    }

    public JSONArray productList(Product condition) {
        List<Product> list = popProductDao.selectProductList(condition);
        JSONArray res = new JSONArray();
        for (Object o : list) {
            Product dto = (Product) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("channelId", dto.getChannelId());
            json.put("sku", dto.getSku());
            json.put("skuName", dto.getSkuName());
            json.put("productTypeId", dto.getProductTypeId());
            json.put("productCode", dto.getProductCode());
            json.put("onSale", dto.getOnSale());
            json.put("createBy", dto.getCreateBy());
            json.put("salePrice", dto.getSalePrice());
            json.put("createTime", dto.getCreateTime());
            json.put("updateTime", dto.getUpdateTime());
            json.put("remark", dto.getRemark());
            json.put("channelName", dto.getChannelName());
            json.put("productTypeName", dto.getProductTypeName());
            json.put("productCodeName", dto.getProductCodeName());
            json.put("departmentName", dto.getDepartmentName());
            res.add(json);
        }
        return res;
    }

    //添加主表
    public int addProduct(Product product) {
        //暂时没有登录人信息
        //product.setCreateBy(popOrderService.thisUserName());
    	 Product validSkuAndChannel = popProductDao.checkSkuAndChannelID(product.getSku(), product.getChannelId().toString());
         if (validSkuAndChannel != null) {
        	 return -1;
         }
        product.setCreateTime(new Date());
        int idd = popProductDao.insertSelective(product);
//        int idd = product.getId();
        String[] city = product.getCitys().split(",");
        String[] id = product.getIds().split(",");
        String[] saleId = product.getSaleIds().split(",");
        String[] county = product.getCountys().split(",");
        String[] regionId = product.getRegionIds().split(",");
        String[] supplyPrice = product.getSupplyPrices().split(",");
        String[] salePrice = product.getSalePrices().split(",");
        String[] limitPrice = product.getLimitPrices().split(",");
        String[] priceStartTime = product.getPriceStartTimes().split(",");
        String[] priceEndTime = product.getPriceEndTimes().split(",");
        String[] province = product.getProvinces().split(",");
        try {

            for (int i = 0; i < city.length; i++) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setCity(city[i]);

                productDetail.setSaleId(idd);
                productDetail.setCounty(county[i]);
                productDetail.setRegionId(Integer.parseInt(regionId[i]));
                productDetail.setSupplyPrice(new BigDecimal(supplyPrice[i]));
                productDetail.setSalePrice(new BigDecimal(salePrice[i]));
                productDetail.setLimitPrice(new BigDecimal(limitPrice[i]));
                productDetail.setPriceStartTime(priceStartTime[i]);
                productDetail.setPriceEndTime(priceEndTime[i]);
                productDetail.setProvince(province[i]);
                if (Integer.parseInt(id[i]) == 0) {
                    //暂时没有登录人信息
                    //productDetail.setCreateBy(popOrderService.thisUserName());
                    productDetail.setCreateTime(new Date());
                    productDetailService.insertSelective(productDetail);
                } else {
                    //暂时没有登录人信息
                    //productDetail.setUpdateBy(popOrderService.thisUserName());
                    productDetail.setUpdateTime(new Date());
                    productDetail.setId(Integer.parseInt(id[i]));
                    productDetailService.updateByPrimaryKeySelective(productDetail);
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return idd;
    }

    //修改
    public int updateProduct(Product product) {
        //productDTO.setUpdateBy(popOrderService.thisUserName());
        product.setUpdateTime(new Date());
        popProductDao.updateByPrimaryKeySelective(product);
        int idd = product.getId();
        String[] city = product.getCitys().split(",");
        String[] id = product.getIds().split(",");
        String[] saleId = product.getSaleIds().split(",");
        String[] county = product.getCountys().split(",");
        String[] regionId = product.getRegionIds().split(",");
        String[] supplyPrice = product.getSupplyPrices().split(",");
        String[] salePrice = product.getSalePrices().split(",");
        String[] limitPrice = product.getLimitPrices().split(",");
        String[] priceStartTime = product.getPriceStartTimes().split(",");
        String[] priceEndTime = product.getPriceEndTimes().split(",");
        String[] province = product.getProvinces().split(",");

        try {

            for (int i = 0; i < city.length; i++) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setCity(city[i]);

                productDetail.setSaleId(Integer.parseInt(saleId[i]));
                productDetail.setCounty(county[i]);
                productDetail.setRegionId(Integer.parseInt(regionId[i]));
                productDetail.setSupplyPrice(new BigDecimal(supplyPrice[i]));
                productDetail.setSalePrice(new BigDecimal(salePrice[i]));
                productDetail.setLimitPrice(new BigDecimal(limitPrice[i]));
                productDetail.setPriceStartTime(priceStartTime[i]);
                productDetail.setPriceEndTime(priceEndTime[i]);
                productDetail.setProvince(province[i]);

                if (Integer.parseInt(id[i]) == 0) {
                    //productDetail.setCreateBy(popOrderService.thisUserName());
                    productDetail.setCreateTime(new Date());

                    productDetailService.insertSelective(productDetail);

                } else {
                    //productDetail.setUpdateBy(popOrderService.thisUserName());
                    productDetail.setUpdateTime(new Date());
                    productDetail.setId(Integer.parseInt(id[i]));
                    productDetailService.updateByPrimaryKeySelective(productDetail);
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return idd;
    }

    //删除
    public int deleteProduct(int id) {
        if (id == 0) {
            return 0;
        }
        return popProductDao.deleteByPrimaryKey(id);
    }

    //子表删除
    public int deleteProductDetail(int id) {
        if (id == 0) {
            return 0;
        }
        return tSaleProductPriceService.deleteByPrimaryKey(id);
    }

    //子表全部删除
    public int deleteProductDetailAuto(int saleid) {
        if (saleid == 0) {
            return 0;
        }
        return tSaleProductPriceService.deleteAuto(saleid);
    }

    @Override
    public JSONArray regionsPatchId(int id) {
        List<Regions> list = regionsService.selectByParentPatchId(id);
        JSONArray res = new JSONArray();
        for (Object o : list) {
            Regions dto = (Regions) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("regionname", dto.getRegionName());
            json.put("parentpath", dto.getParentPath());
            json.put("regiontype", dto.getRegionType());
            res.add(json);
        }

        return res;
    }

    @Override
    public JSONArray regionsList(int parentid, String attributes) {

        //省
        if (("q").equals(attributes)) {
            List<Regions> list = regionsService.selectByParentId(parentid);
            String state = "closed";
            JSONArray res = new JSONArray();
            for (Object o : list) {
                Regions dto = (Regions) o;
                JSONObject json = new JSONObject();
                json.put("id", dto.getId());
                json.put("text", dto.getRegionName());
                json.put("state", state);
                json.put("attributes", "w");
                res.add(json);
            }

            return res;
        }
        //市
        if (("w").equals(attributes)) {
            List<Regions> list = regionsService.selectByParentId(parentid);
            String state = "closed";
            JSONArray res = new JSONArray();
            for (Object o : list) {
                Regions dto = (Regions) o;
                JSONObject json = new JSONObject();
                json.put("id", dto.getId());
                json.put("text", dto.getRegionName());
                json.put("state", state);
                json.put("attributes", "e");
                res.add(json);
            }

            return res;
        }
        //县
        if (("e").equals(attributes)) {
            List<Regions> list = regionsService.selectByParentId(parentid);
            String state = "closed";
            JSONArray res = new JSONArray();
            for (Object o : list) {
                Regions dto = (Regions) o;
                JSONObject json = new JSONObject();
                json.put("id", dto.getId());
                json.put("text", dto.getRegionName());
                json.put("state", state);
                json.put("attributes", "r");
                res.add(json);
            }

            return res;
        }
        if (("r").equals(attributes)) {

            return null;
        }
        return null;

    }

    @Override
    public JSONArray regionsPatchIds(int id) {

        int regionty = regionsService.getRegionType(id);
        //查询省
        if (regionty == 1) {
            List<Regions> list = regionsService.selectregionprovince(id);
            JSONArray res = new JSONArray();
            for (Object o : list) {
                Regions dto = (Regions) o;
                JSONObject json = new JSONObject();
                json.put("regionid", dto.getId());
                json.put("province", dto.getCol01());
                json.put("city", dto.getCol02());
                json.put("county", dto.getCol03());
                res.add(json);
            }
            return res;
        }
        //查询市
        if (regionty == 2) {
            List<Regions> list = regionsService.selectregioncity(id);
            String patch = regionsService.selectPatchId(id);
            String[] patchId = patch.split("/");
            String province = regionsService.selectregionName(Integer.parseInt(patchId[1]));
            JSONArray res = new JSONArray();
            for (Object o : list) {
                Regions dto = (Regions) o;
                JSONObject json = new JSONObject();
                json.put("regionid", dto.getId());
                json.put("province", province);
                json.put("city", dto.getCol01());
                json.put("county", dto.getCol02());
                res.add(json);
            }
            return res;
        }
        if (regionty == 3) {
            List<Regions> list = regionsService.selectByParentPatchId(id);
            String patch = regionsService.selectPatchId(id);
            String[] patchId = patch.split("/");
            String province = regionsService.selectregionName(Integer.parseInt(patchId[1]));
            String city = regionsService.selectregionName(Integer.parseInt(patchId[2]));
            String county = regionsService.selectregionName(id);
            JSONArray res = new JSONArray();
            for (Object o : list) {
                Regions dto = (Regions) o;
                JSONObject json = new JSONObject();
                json.put("regionid", dto.getId());
                json.put("province", province);
                json.put("city", city);
                json.put("county", dto.getRegionName());
                res.add(json);
            }
            return res;
        }

        return null;

    }

    @Override
    public JSONArray skuList(Products products) {
        List<Products> list = productsShopService.selectProducts(products);
        JSONArray res = new JSONArray();
        for (Object o : list) {
            Products dto = (Products) o;
            JSONObject json = new JSONObject();
            json.put("sku", dto.getSku());
            json.put("productName", dto.getProductName());
            json.put("productTypeId", dto.getProductCateId());
            json.put("productTypeId1", dto.getProductTypeId());
            json.put("brandId", dto.getBrandId());
            res.add(json);
        }
        return res;
    }


    @Override
    public int productCodeList(int channelid, String productcode, int id) {
        int list = popProductDao.selectByproductCode(channelid, productcode, id);
        return list;
    }

    @Override
    public JSONArray channelsList() {
        List<Channels> list = channelsService.selectChannels();
        JSONArray res = new JSONArray();

        for (Object o : list) {
            Channels dto = (Channels) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("channelcode", dto.getChannelName());
            res.add(json);
        }
        return res;
    }

    @Override
    public JSONArray channelsInfoList() {
        List<DistributionInfo> list = distributionInfoService.selectChannelsInfo();
        JSONArray res = new JSONArray();

        for (Object o : list) {
            DistributionInfo dto = (DistributionInfo) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("channelcode", dto.getDistributionName());
            res.add(json);
        }
        return res;
    }

    @Override
    //校验时间
    public int pricTime(int saleId, int regionId, String starttime, String endtime, int id) throws ParseException {

        JSONArray value = regionsPatchIds(regionId);
        int t = 0;

        for (int i = 0; i < value.size(); i++) {
            JSONObject job = value.getJSONObject(i);
            List<ProductDetail> list = productDetailService.selectBypriceTime(saleId, Integer.valueOf(job.get("regionid").toString()));

            if (list.size() > 0) {
                for (Object o : list) {
                    List<ProductDetail> max = productDetailService.selectmax(saleId, Integer.valueOf(job.get("regionid").toString()), id);
                    List<ProductDetail> strt = productDetailService.selectstartTime(starttime, endtime, saleId, Integer.valueOf(job.get("regionid").toString()), id);
                    if (max.size() != 0 && (strt.size() < max.size())) {
                        t = 1;
                        break;
                    }

                }
            }
        }
        return t;
    }

    @Override
    public int addProductDetail(ProductDetail c) {
        JSONArray value = regionsPatchIds(c.getRegionId());
        for (int i = 0; i < value.size(); i++) {
            JSONObject job = value.getJSONObject(i);
            c.setRegionId(Integer.valueOf(job.get("regionid").toString()));
            c.setProvince(job.get("province").toString());
            c.setCounty(job.get("county").toString());
            c.setCity(job.get("city").toString());
            c.setId(0);
            c.setCreateTime(new Date());
            productDetailService.insertSelective(c);
        }

        return 1;
    }

    @Override
    public int updateProductDetail(ProductDetail productDetailDTO) {
        JSONArray res = regionsPatchIds(productDetailDTO.getRegionId());
        res.get(0);
        for (int i = 0; i < res.size(); i++) {
            JSONObject job = res.getJSONObject(i);
            productDetailDTO.setProvince(job.get("province").toString());
            productDetailDTO.setCity(job.get("city").toString());
            productDetailDTO.setUpdateTime(new Date());
        }
        return productDetailService.updateByPrimaryKeySelective(productDetailDTO);
    }

    @Override
    public JSONArray brandsList() {
        List<Brands> list = brandsService.selectBrandsList();
        JSONArray res = new JSONArray();
        for (Object o : list) {
            Brands dto = (Brands) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("typeName", dto.getBrandName());
            res.add(json);
        }
        return res;
    }

    @Override
    public JSONArray brandsIdList(int id) {
        List<Brands> list = brandsService.selectBrandsIdList(id);
        JSONArray res = new JSONArray();
        for (Object o : list) {
            Brands dto = (Brands) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("brandName", dto.getBrandName());
            res.add(json);
        }
        return res;
    }

    @Override
    public JSONArray GetSelectSkuValue(String sku, Integer productTypeId) {
        // 根据sku获取产品组信息
        List<DepartmentProductType> departmentList = departmentProductTypeService.selectname(productTypeId);
        // 根据商品类型ID获取品类信息
        List<Producttypes> productTypeList = productTypesService.selectByProducttypesSku(productTypeId);

        JSONArray res = new JSONArray();
        JSONObject json = new JSONObject();
        if (null != departmentList && departmentList.size() > 0) {
            json.put("departmentName", departmentList.get(0).getDepartmentName());
        } else {
            json.put("departmentName", "");
        }

        if (null != productTypeList && productTypeList.size() > 0) {
            json.put("typeName", productTypeList.get(0).getTypeName());
        } else {
            json.put("typeName", "");
        }

        res.add(json);

        return res;
    }

    @Override
    public JSONArray departmentproducttypeList() {
        List<DepartmentProductType> list = departmentProductTypeService.selectproduct();
        JSONArray res = new JSONArray();
        for (Object o : list) {
            DepartmentProductType dto = (DepartmentProductType) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getDepartmentCode());
            json.put("typeName", dto.getDepartmentName());
            res.add(json);
        }
        return res;
    }

    @Override
    public JSONArray priceDetailList(int saleid) {

        List<TSaleProductPrice> list = tSaleProductPriceService.selectBySaleId(saleid);
        JSONArray res = new JSONArray();
        for (Object o : list) {
            TSaleProductPrice dto = (TSaleProductPrice) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("saleid", dto.getSaleId());
            json.put("supplyprice", dto.getSupplyPrice());
            json.put("saleprice", dto.getSalePrice());
            json.put("limitprice", dto.getLimitPrice());
            json.put("remark", dto.getRemark());
            json.put("pricestarttime", dto.getPriceStartTime());
            json.put("priceendtime", dto.getPriceEndTime());
            res.add(json);
        }
        return res;
    }

    @Override
    public int updatePriceDetail(TSaleProductPrice tSaleProductPrice) {
        return tSaleProductPriceService.updateByPrimaryKeySelective(tSaleProductPrice);

    }

    @Override
    public int addPriceDetail(TSaleProductPrice tSaleProductPrice) {
        return tSaleProductPriceService.insertSelective(tSaleProductPrice);
    }

    @Override
    public JSONArray priceGetChanls(String startDateTime, String endDateTime, int saleid) {

        List<TSaleProductPrice> list = tSaleProductPriceService.selectCount(startDateTime, endDateTime, saleid);
        JSONArray res = new JSONArray();
        for (Object o : list) {
            TSaleProductPrice dto = (TSaleProductPrice) o;
            JSONObject json = new JSONObject();
            json.put("id", dto.getId());
            json.put("saleid", dto.getSaleId());
            json.put("supplyprice", dto.getSupplyPrice());
            json.put("saleprice", dto.getSalePrice());
            json.put("limitprice", dto.getLimitPrice());
            json.put("remark", dto.getRemark());
            json.put("pricestarttime", dto.getPriceStartTime());
            json.put("priceendtime", dto.getPriceEndTime());
            res.add(json);
        }
        return res;
    }

    @Override
    public long checkPriceTime(int id, int saleid, String startTime, String endTime) {
        return tSaleProductPriceService.checkPriceTime(id, saleid, startTime, endTime);
    }

    @Override
    public Products checkSkuFromProducts(String sku) {
        return productsService.checkSkuFromProducts(sku);
    }

    @Override
    public Product checkSkuAndChannel(String sku, String channelCode) {
        return popProductDao.checkSkuAndChannel(sku, channelCode);
    }

    @Override
    public TChannelsInfo getChannelInfo(String channelCode) {
        return tChanneclsInfoService.getOneByCode(channelCode);
    }

    @Override
    public void addTSaleProuctPrice(TSaleProductPrice tSaleProductPrice) {
        tSaleProductPriceService.insertSelective(tSaleProductPrice);

    }

    @Override
    public Producttypes getProductsType(Integer productTypeId) {
        return productTypesService.getOneById(productTypeId.longValue());

    }

    @Override
    public DepartmentProductType getDepartment(Integer productTypeId) {
        return departmentProductTypeService.getDepartment(productTypeId);
    }

    @Override
    public int addProductFromImport(Product productDTO) {
         int id=popProductDao.insert(productDTO);
         return id;
    }
    
    @Override
    public List<PushData> findPushData(String channelName) {
        List<PushData> list = channelsService.findPushData(channelName);
   
        return list;
    }

	@Override
	public List<TsendInfoLog> channelCodeSelect(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return channelsService.channelCodeSelect(params);
	}

	@Override
	public Producttypes getProductsTypeBySKU(String sku) {
		  return productTypesService.getProductsTypeBySKU(sku);
	}

}
