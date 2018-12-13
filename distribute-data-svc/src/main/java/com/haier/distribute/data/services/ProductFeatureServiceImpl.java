package com.haier.distribute.data.services;

import com.haier.distribute.data.dao.distribute.ProductFeatureDao;
import com.haier.distribute.data.model.ProductFeaturesDTO;
import com.haier.distribute.data.service.ProductFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductFeatureServiceImpl implements ProductFeatureService {
    @Autowired
    private ProductFeatureDao productFeatureDao;
    public void insertBath(List<ProductFeaturesDTO> list){
        productFeatureDao.insertBath(list);
    }
    public List<ProductFeaturesDTO> selectByProductId(Integer id){
        return productFeatureDao.selectByProductId(id);
    }
    public void deleteById(Integer id){
        productFeatureDao.deleteById(id);
    }
    public List<ProductFeaturesDTO> selectAll(){
        return productFeatureDao.selectAll();
    }
}
