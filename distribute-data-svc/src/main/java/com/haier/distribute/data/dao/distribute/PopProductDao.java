package com.haier.distribute.data.dao.distribute;

import java.util.List;

import com.haier.distribute.data.model.Product;
import com.haier.distribute.data.model.ProductTiming;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PopProductDao extends BaseDao<Product>{
	//根据Id删除
    int deleteByPrimaryKey(Integer id);
	//添加全部信息
    int insert(Product record);
	//添加部分信息
    int insertSelective(List<Product> c);
	//编辑部分信息（根据ID）
    int updateByPrimaryKeySelective(Product record);
	//编辑所有信息（根据ID）
    int updateByPrimaryKey(Product record);
	//根据Id查询
    Product selectByPrimaryKey(Integer id);
    int getPagerCountS(@Param("entity") Product entity);
	//根据条件查询结果List
	List<Product> selectProductList(@Param("entity") Product entity);
	List<Product> selectProductListf(@Param("entity") Product entity, @Param("start") int start, @Param("rows") int rows);
	//根据商品编码查询是否相同

	int selectByproductCode(@Param("channelId") int channelId, @Param("productCode") String productCode, @Param("id") int id);

    Product checkSkuAndChannel(String sku, String channelCode);
    //推送商品信息
	List<ProductTiming> pushProductInfo(int channelId);
	public List<String> selectAllSku();
	public List<String> selectSkuByChannelId(@Param("channelId") Integer id);
	public List<String> seletSkuAll();
	Product checkSkuAndChannelId(String sku, String channelId);
}