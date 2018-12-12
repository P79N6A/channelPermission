package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.shop.dao.shopread.ItemBaseReadDao;
import com.haier.shop.dao.shopwrite.ItemBaseWriteDao;
import com.haier.shop.model.ItemBase;
import com.haier.shop.service.ShopItemBaseService;
@Service
public class ShopItemBaseServiceImpl implements ShopItemBaseService {
    @Autowired
    private ItemBaseReadDao itemBaseReadDao;
    @Autowired
    private ItemBaseWriteDao itemBaseWriteDao;
    @Override
    /**
     * 根据物料ID取得品牌code和型号 & 根据物料号取得产品组code 李超
     * @param material_id 物料ID 物料号
     * @return
     */
    public  List<ItemBase> findItemBaseByMaterialId( String material_id){
        return itemBaseReadDao.findItemBaseByMaterialId(material_id);
    }
    @Override
    /**
     * 根据物料SKU取得物料基本信息 李超
     * @param subSku 物料
     * @return
     */
    public List<ItemBase> getBySku(ItemBase param){
        List<ItemBase> x=itemBaseReadDao.getBySku(param);
        return x;
    }
    @Override
    public Integer update(ItemBase base){
        return itemBaseWriteDao.update(base);
    }
    @Override
    public Integer insert(ItemBase base){
        return itemBaseWriteDao.insert(base);
    }

    public  List<ItemBase> getIncompleteItemBaseList(){
        return itemBaseReadDao.getIncompleteItemBaseList();
    }
    @Override
    public List<ItemBase> queryItemBaseByParamWithLike(ItemBase base){
        return itemBaseReadDao.queryItemBaseByParamWithLike(base);
    }
    @Override
    public Integer updateNotNull(ItemBase base){
        return itemBaseWriteDao.updateNotNull(base);
    }
    @Override
    public Integer countItemBaseByParamWithLike(ItemBase base){
        return itemBaseReadDao.countItemBaseByParamWithLike(base);
    }
    @Override
    /**
     * 查询 -- 按照产品组查询
     * @param depList
     * @return
     */
    public List<ItemBase> getItemListByDepList(List<String> depList){
        return itemBaseReadDao.getItemListByDepList(depList);
    }
	@Override
	public List<ItemBase> getType() {
		// TODO Auto-generated method stub
		return itemBaseReadDao.getType();
	}
	@Override
	public List<ItemBase> getProductBaseData(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return itemBaseReadDao.getProductBaseData(params);
	}
	@Override
	public int getRowCnts(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return itemBaseReadDao.getRowCnts(params);
	}
	
}
