package com.haier.distribute.data.services;

import com.haier.distribute.data.dao.distribute.ProductGatefoldDao;
import com.haier.distribute.data.model.ProductGatefoldsDTO;
import com.haier.distribute.data.service.ProductGatefoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductGatefoldServiceImpl implements ProductGatefoldService {
    @Autowired
    private ProductGatefoldDao productGatefoldDao;
    public void insertBath(List<ProductGatefoldsDTO> list){
        productGatefoldDao.insertBath(list);
    }
    public List<ProductGatefoldsDTO> selectByProductId(Integer id){
        return productGatefoldDao.selectByProductId(id);
    }
    public void  deleteById(Integer id){
        productGatefoldDao.deleteById(id);
    }
    public List<ProductGatefoldsDTO> selectAll(){
        return productGatefoldDao.selectAll();
    }

}
