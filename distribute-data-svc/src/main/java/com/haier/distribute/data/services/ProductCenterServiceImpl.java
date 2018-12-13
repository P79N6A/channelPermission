package com.haier.distribute.data.services;

import com.haier.distribute.data.dao.distribute.ProductCenterDao;
import com.haier.distribute.data.model.ProductCenterDTO;
import com.haier.distribute.data.service.ProductCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCenterServiceImpl implements ProductCenterService{
    @Autowired
    private ProductCenterDao productCenterDao;
    public ProductCenterDTO selectByCode(String code){
        return productCenterDao.selectByCode(code);
    }
    public ProductCenterDTO insertProductInfo(ProductCenterDTO productCenterDTO){
        productCenterDao.insertProductInfo(productCenterDTO);
        return productCenterDTO;
    }
    public void updateById(ProductCenterDTO productCenterDTO){
        productCenterDao.updateById(productCenterDTO);
    }
    public List<ProductCenterDTO> selectAll(){
        return productCenterDao.selectAll();
    }
    public List<ProductCenterDTO> selectBySku(List<String> list){
        return productCenterDao.selectBySku(list);
    }
	@Override
	public List<ProductCenterDTO> selectByProductCode(List<String> list) {
		return productCenterDao.selectByProductCode(list);
	}
}
