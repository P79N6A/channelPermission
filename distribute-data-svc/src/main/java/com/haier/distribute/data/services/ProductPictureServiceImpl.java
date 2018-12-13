package com.haier.distribute.data.services;

import com.haier.distribute.data.dao.distribute.ProductPictureDao;
import com.haier.distribute.data.model.ProductPicturesDTO;
import com.haier.distribute.data.service.ProductPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductPictureServiceImpl implements ProductPictureService {
    @Autowired
    private ProductPictureDao productPictureDao;
    public List<ProductPicturesDTO> selectByProductId(Integer id){
        return productPictureDao.selectByProductId(id);
    }
    public void insertBath(List<ProductPicturesDTO> list){
        productPictureDao.insertBath(list);
    }
    public void deleteById(Integer id){
        productPictureDao.deleteById(id);
    }
    public List<ProductPicturesDTO> selectAll(){
        return productPictureDao.selectAll();
    }
}
