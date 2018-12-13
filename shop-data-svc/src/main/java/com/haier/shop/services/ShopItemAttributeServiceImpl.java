package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.ItemAttributeReadDao;
import com.haier.shop.dao.shopwrite.ItemAttributeWriteDao;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.service.ShopItemAttributeService;
@Service
public class ShopItemAttributeServiceImpl implements ShopItemAttributeService {
    @Autowired
    private ItemAttributeReadDao itemAttributeReadDao;
    @Autowired
    private ItemAttributeWriteDao itemAttributeWriteDao;


    @Override
    public ItemAttribute get(Integer id){
        return itemAttributeReadDao.get(id);
    }

    @Override
    public Integer insert(ItemAttribute attribute){
        return itemAttributeWriteDao.insert(attribute);
    }

    @Override
    public  Integer update(ItemAttribute attribute){
        return itemAttributeWriteDao.update(attribute);
    }

    @Override
    public Integer updateNotNull(ItemAttribute attribute){
        return itemAttributeWriteDao.updateNotNull(attribute);
    }
    @Override
    public  List<ItemAttribute> list(ItemAttribute attribute){
        return itemAttributeReadDao.list(attribute);
    }
    @Override
    public  List<ItemAttribute> getByValueAndValueSetId(ItemAttribute attribute){
        return itemAttributeReadDao.getByValueAndValueSetId(attribute);
    }
    @Override
    public List<String> getAllCbsCategory(){
        return itemAttributeReadDao.getAllCbsCategory();
    }
    @Override
    public  Integer countItemAttributeWithLike(ItemAttribute itemAttribute){
        return itemAttributeReadDao.countItemAttributeWithLike(itemAttribute);
    }
    @Override
    public List<ItemAttribute> queryItemAttributeWithLike(ItemAttribute itemAttribute){
        return itemAttributeReadDao.queryItemAttributeWithLike(itemAttribute);
    }

    @Override
    public JSONObject queryItemAttribute(ItemAttribute itemAttribute) {
        JSONObject result = new JSONObject();
        itemAttribute.setStart((itemAttribute.getPage()-1)*100);
        itemAttribute.setSize(itemAttribute.getRows());
        result.put("total", itemAttributeReadDao.countItemAttributeWithLike(itemAttribute));
        result.put("rows", itemAttributeReadDao.queryItemAttributeWithLike(itemAttribute));
        return result;
    }

    @Override
    public  List<ItemAttribute> queryProductGroupByCbsCategory(String cbsCategory){
        return itemAttributeReadDao.queryProductGroupByCbsCategory(cbsCategory);
    }
    /**
     * 通过ValueSetId检索ItemAttribute List
     * @param valueSetId
     * @return
     */
    @Override
    public List<ItemAttribute> getByValueSetId(String valueSetId){
        return itemAttributeReadDao.getByValueSetId(valueSetId);
    }
    /**
     * 基地直发类别下拉列表数据获取
     * @param map
     * @return
     */
    @Override
    public List<String> getCbsCategoryByProductGroup(Map<String, Object> map){
        return itemAttributeReadDao.getCbsCategoryByProductGroup(map);
    }

    @Override
    public List<ItemAttribute> getProductTypes() {
        return itemAttributeReadDao.getProductTypes();
    }

	@Override
	public List<String> getProductTypesTo2() {
		// TODO Auto-generated method stub
		return itemAttributeWriteDao.getProductTypesTo2();
	}
}
