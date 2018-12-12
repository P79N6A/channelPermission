package com.haier.distribute.data.service;

import java.util.List;

import com.haier.distribute.data.model.ProductDetail;


public interface ProductDetailService {
    int deleteByPrimaryKey(Integer id);

    int deleteAuto(Integer saleid);

    int insert(ProductDetail record);

    int insertSelective(ProductDetail record);

    List<ProductDetail> selectByPrimaryKey(Integer id);

    List<ProductDetail> selectBypriceTime(Integer saleId, Integer regionId);

    List<ProductDetail> selectstartTime(String priceStartTime, String priceEndTime, Integer saleId, Integer regionId, Integer id);

    List<ProductDetail> selectmax(Integer saleId, Integer regionId, Integer id);

    int updateByPrimaryKeySelective(ProductDetail record);

    int updateByPrimaryKey(ProductDetail record);

    //根据saleId查询字表数据
    List<ProductDetail> selectBySaleId(Integer saleId);

    //根据saleId，区域查询字表数据
    List<ProductDetail> selectBycounty(String startDateTime, String endDateTime, Integer saleId, String county);

    //根据saleId查询时间
    List<ProductDetail> selecttime(Integer saleId);
}