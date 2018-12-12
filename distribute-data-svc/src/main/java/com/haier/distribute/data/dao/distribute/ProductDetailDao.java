package com.haier.distribute.data.dao.distribute;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.haier.distribute.data.model.ProductDetail;


public interface ProductDetailDao extends BaseDao<ProductDetail>{
    int deleteByPrimaryKey(Integer id);
    int deleteAuto(Integer saleid);
    int insert(ProductDetail record);

    int insertSelective(ProductDetail record);

    List<ProductDetail> selectByPrimaryKey(Integer id);
    List<ProductDetail>selectBypriceTime(@Param("saleId")Integer saleId, @Param("regionId")Integer regionId);
    List<ProductDetail> selectstartTime(@Param("priceStartTime")String priceStartTime,@Param("priceEndTime")String priceEndTime,@Param("saleId")Integer saleId,@Param("regionId")Integer regionId,@Param("id")Integer id);
    List<ProductDetail> selectmax(@Param("saleId")Integer saleId,@Param("regionId")Integer regionId,@Param("id")Integer id);
    
    int updateByPrimaryKeySelective(ProductDetail record);

    int updateByPrimaryKey(ProductDetail record);

    //根据saleId查询字表数据
    List<ProductDetail> selectBySaleId(Integer saleId);
    //根据saleId，区域查询字表数据
    List<ProductDetail> selectBycounty(@Param("startDateTime")String startDateTime, @Param("endDateTime")String endDateTime,@Param("saleId")Integer saleId,@Param("county")String county);
    
  //根据saleId查询时间
    List<ProductDetail> selecttime(Integer saleId);
}