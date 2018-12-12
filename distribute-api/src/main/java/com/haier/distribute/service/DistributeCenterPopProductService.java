package com.haier.distribute.service;




import java.text.ParseException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.*;

public interface DistributeCenterPopProductService {

    /**
     * 可售商品主表(分页查询)
     * @param page
     * @param condition
     * @return
     */
    JSONObject findProductList(PagerInfo page, Product condition);
    
    JSONArray proucttypeesList();
    
    JSONArray proucttypeesListsku(int id);

	JSONArray productList(Product condition);

	JSONArray productDetailList(int saleid);

	JSONArray productDetailcountyList(String startDateTime, String endDateTime,int saleid,String county);
	int addProduct(Product product);

	int updateProduct(Product product);

	int deleteProduct(int id);

	int deleteProductDetail(int id);

	int deleteProductDetailAuto(int saleid);



	JSONArray regionsPatchId(int id);


	JSONArray regionsList(int parentid, String attributes);

	JSONArray regionsPatchIds(int id);

	JSONArray skuList(Products products);

//	JSONArray DepartmentcodeskuList(String sku);

	int productCodeList(int channelid, String productcode, int id);

	JSONArray channelsList();
	JSONArray channelsInfoList();
	int pricTime(int saleid, int regionId, String starttime, String endtime,int id) throws ParseException;

	int addProductDetail(ProductDetail productDetail);

	int updateProductDetail(ProductDetail productDetail);

	JSONArray brandsList();

	JSONArray brandsIdList(int id);

	JSONArray GetSelectSkuValue(String sku, Integer productTypeId);

	JSONArray departmentproducttypeList();

	JSONArray priceDetailList(int saleid);

	int updatePriceDetail(TSaleProductPrice tSaleProductPrice);

	int addPriceDetail(TSaleProductPrice tSaleProductPrice);

	JSONArray priceGetChanls(String startDateTime, String endDateTime, int saleid);

	long checkPriceTime(int id, int saleid, String startTime, String endTime);

	Products checkSkuFromProducts(String sku);

	Product checkSkuAndChannel(String sku, String channelCode);

	TChannelsInfo getChannelInfo(String channelCode);

	void addTSaleProuctPrice(TSaleProductPrice tSaleProductPrice);

	Producttypes getProductsType(Integer productTypeId);

	DepartmentProductType getDepartment(Integer productTypeId);

	void addProductFromImport(Product productDTO);
}
