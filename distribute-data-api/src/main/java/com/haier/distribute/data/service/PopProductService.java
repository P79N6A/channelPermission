package com.haier.distribute.data.service;

import java.util.List;

import com.haier.distribute.data.model.Product;


public interface PopProductService {
    //根据Id删除
    int deleteByPrimaryKey(Integer id);

    //添加全部信息
    int insert(Product record);

    //添加部分信息
    int insertSelective(Product c);

    //编辑部分信息（根据ID）
    int updateByPrimaryKeySelective(Product record);

    //编辑所有信息（根据ID）
    int updateByPrimaryKey(Product record);

    //根据Id查询
    Product selectByPrimaryKey(Integer id);

    int getPagerCountS(Product entity);

    //根据条件查询结果List
    List<Product> selectProductList(Product entity);

    List<Product> selectProductListf(Product entity, int start, int rows);
    //根据商品编码查询是否相同

    int selectByproductCode(int channelId, String productCode, int id);

    Product checkSkuAndChannel(String sku, String channelCode);
    public List<String> selectSkuByChannelId(Integer id);
    public List<String> seletSkuAll();

	Product checkSkuAndChannelID(String sku, String channelId);
}