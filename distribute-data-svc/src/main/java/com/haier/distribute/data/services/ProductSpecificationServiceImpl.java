package com.haier.distribute.data.services;

import com.haier.distribute.data.dao.distribute.ProductSpecificationDao;
import com.haier.distribute.data.model.ProductSpecificationsDTO;
import com.haier.distribute.data.service.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductSpecificationServiceImpl implements ProductSpecificationService {
    @Autowired
    private ProductSpecificationDao productSpecificationDao;
    public List<ProductSpecificationsDTO> selectByProductId(Integer id){
        return productSpecificationDao.selectByProductId(id);
    }
    public void insertBath(List<ProductSpecificationsDTO> list){
        productSpecificationDao.insertBath(list);
    }
    public void deleteById(Integer id){
        productSpecificationDao.deleteById(id);
    }
    public  List<ProductSpecificationsDTO> selectAll(){
        return productSpecificationDao.selectAll();
    }
}
