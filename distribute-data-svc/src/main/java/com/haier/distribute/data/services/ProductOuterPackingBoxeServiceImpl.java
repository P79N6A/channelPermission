package com.haier.distribute.data.services;

import com.haier.distribute.data.dao.distribute.ProductOuterPackingBoxeDao;
import com.haier.distribute.data.model.ProductOuterPackingBoxesDTO;
import com.haier.distribute.data.service.ProductOuterPackingBoxeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductOuterPackingBoxeServiceImpl implements ProductOuterPackingBoxeService {
    @Autowired
    private ProductOuterPackingBoxeDao productOuterPackingBoxeDao;
    public void insertBath(List<ProductOuterPackingBoxesDTO> list){
        productOuterPackingBoxeDao.insertBath(list);
    }
    public List<ProductOuterPackingBoxesDTO> selectByProductId(Integer id){
        return productOuterPackingBoxeDao.selectByProductId(id);
    }
    public void deleteById(Integer id){
        productOuterPackingBoxeDao.deleteById(id);
    }
    public List<ProductOuterPackingBoxesDTO> selectAll(){
        return productOuterPackingBoxeDao.selectAll();
    }
}
