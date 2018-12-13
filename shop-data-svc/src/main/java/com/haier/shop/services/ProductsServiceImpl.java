package com.haier.shop.services;

import java.math.BigDecimal;
import java.util.*;

import com.haier.shop.util.SerializedPhpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.ProductsReadDao;
import com.haier.shop.dao.shopwrite.ProductsWriteDao;
import com.haier.shop.model.ProductBase;
import com.haier.shop.model.Products;
import com.haier.shop.model.ProductsNew;
import com.haier.shop.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductsReadDao productsReadDao;
    @Autowired
    ProductsWriteDao productsWriteDao;

    @Override
    public List<Products> selectProducts1(String sku, String productname) {
        return productsReadDao.selectProducts1(sku, productname);
    }

    @Override
    public List<Products> selectProducts(Products products) {
        return productsReadDao.selectProducts(products);
    }

    @Override
    public Products getBySku(String sku) {
        return productsReadDao.getBySku(sku);
    }
    public Products getBySku2(String sku) {
        return productsReadDao.getBySku2(sku);
    }

    @Override
    public int updateSaleNumBySku(ProductsNew products) {
        return productsWriteDao.updateSaleNumBySku(products);
    }

    @Override
    public List<ProductBase> getAllSkusListBySale(String onSale) {
        return productsReadDao.getAllSkusListBySale(onSale);
    }

    @Override
    public List<ProductBase> getAllProductsBysCode(String sCode) {
        return productsReadDao.getAllProductsBysCode(sCode);
    }

    @Override
    public ProductBase getBaseBySku(String sku) {
        return productsReadDao.getBaseBySku(sku);
    }

    @Override
    public Products get(Integer id) {
        return productsReadDao.get(id);
    }

    @Override
    public List<Products> getAllProductInfo() {
        return productsReadDao.getAllProductInfo();
    }

    @Override
    public List<ProductBase> getListBySkus(List<String> skuList) {
        return productsReadDao.getListBySkus(skuList);
    }

    @Override
    public List<Products> getOnSaleBigProducts() {
        return productsReadDao.getOnSaleBigProducts();
    }

    @Override
    public List<ProductBase> getAllSkusList(Map<String, Object> paramMap) {
        return productsReadDao.getAllSkusList(paramMap);
    }

	@Override
	public List<Map<String, Object>> queryProductList(Map<String, Object> map) {
		return productsReadDao.queryProductList(map);
	}

	@Override
	public Integer queryProductListCount(Map<String, Object> map) {
		return productsReadDao.queryProductListCount(map);
	}

	@Override
	public List<Map<String, Object>> getOnSaleProductIds() {
		return productsReadDao.getOnSaleProductIds();
	}

	@Override
	public Map<String, Object> findProductBySku(String sku) {
		return productsReadDao.findProductBySku(sku);
	}

	@Override
	public Map<String, Object> findProductByName(String name) {
		return productsReadDao.findProductByName(name);
	}

	@Override
	public Integer addProduct(Products products) {
		return productsWriteDao.addProduct(products);
	}

	@Override
	public Integer updateProduct(Products products) {
		return productsWriteDao.updateProduct(products);
	}

	@Override
	public Integer delProduct(Integer id) {
		return productsWriteDao.delProduct(id);
	}

	@Override
    public List<Products> getProductList(String productSpecs) {
        List<Products> list = new ArrayList<Products>();
        SerializedPhpParser serializedPhpParser = new SerializedPhpParser(productSpecs);
        Object result = serializedPhpParser.parse(); //通过php序列化后的字符串，获取java对象
        if (result != null) {
            @SuppressWarnings("rawtypes")
            Map myMap = (HashMap) result;
            @SuppressWarnings("rawtypes")
            Set keys = myMap.keySet();
            for (@SuppressWarnings("rawtypes")
                 Iterator i = keys.iterator(); i.hasNext(); ) {
                String key = (String) i.next(); //获取sku编号
                BigDecimal price = new BigDecimal(myMap.get(key).toString());//外部促销价格
                String skutrim = key.trim();
                Products p = getBySku(skutrim);
                p.setExternalSalePrice(price);
                list.add(p);
            }
        }
        return list;
    }

    @Override
    public List<String> seletSkuAll() {
        return productsReadDao.seletSkuAll();
    }

    @Override
    public Products selectBySku(String sku) {
        return productsReadDao.selectBySku(sku);
    }

    @Override
    public int updateProductBySku(String sku) {
        return productsReadDao.updateProductBySku(sku);
    }

}