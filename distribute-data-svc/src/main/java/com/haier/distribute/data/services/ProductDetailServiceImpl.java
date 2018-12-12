package com.haier.distribute.data.services;

import java.util.List;

import com.haier.distribute.data.dao.distribute.ProductDetailDao;
import com.haier.distribute.data.model.ProductDetail;
import com.haier.distribute.data.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailServiceImpl implements ProductDetailService{

    @Autowired
    ProductDetailDao productDetailDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return productDetailDao.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteAuto(Integer saleid) {
        return productDetailDao.deleteAuto(saleid);
    }

    @Override
    public int insert(ProductDetail record) {
        return productDetailDao.insert(record);
    }

    @Override
    public int insertSelective(ProductDetail record) {
        return productDetailDao.insertSelective(record);
    }

    @Override
    public List<ProductDetail> selectByPrimaryKey(Integer id) {
        return productDetailDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ProductDetail> selectBypriceTime(Integer saleId, Integer regionId) {
        return productDetailDao.selectBypriceTime(saleId, regionId);
    }

    @Override
    public List<ProductDetail> selectstartTime(String priceStartTime, String priceEndTime, Integer saleId, Integer regionId, Integer id) {
        return productDetailDao.selectstartTime(priceStartTime,priceEndTime,saleId,regionId,id);
    }

    @Override
    public List<ProductDetail> selectmax(Integer saleId, Integer regionId, Integer id) {
        return productDetailDao.selectmax(saleId,regionId,id);
    }

    @Override
    public int updateByPrimaryKeySelective(ProductDetail record) {
        return productDetailDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ProductDetail record) {
        return productDetailDao.updateByPrimaryKey(record);
    }

    @Override
    public List<ProductDetail> selectBySaleId(Integer saleId) {
        return productDetailDao.selectBySaleId(saleId);
    }

    @Override
    public List<ProductDetail> selectBycounty(String startDateTime, String endDateTime, Integer saleId, String county) {
        return productDetailDao.selectBycounty(startDateTime,endDateTime,saleId,county);
    }

    @Override
    public List<ProductDetail> selecttime(Integer saleId) {
        return productDetailDao.selecttime(saleId);
    }
}