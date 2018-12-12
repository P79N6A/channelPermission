package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.TaoBaoGroupsReadDao;
import com.haier.shop.dao.shopwrite.TaoBaoGroupsWriteDao;
import com.haier.shop.model.TaoBaoGroups;
import com.haier.shop.service.ShopTaoBaoGroupsService;
@Service
public class ShopTaoBaoGroupsServiceImpl implements ShopTaoBaoGroupsService {
    @Autowired
    private TaoBaoGroupsWriteDao taoBaoGroupsWriteDao;
    @Autowired
    private TaoBaoGroupsReadDao taoBaoGroupsReadDao;
    /**
     * 通过SKU查询定金尾款信息
     * @param sku
     * @return
     */
    public List<Map<String,Object>> getTaoBaoGroupsListBySku(String sku, Integer page, Integer rows){
        return taoBaoGroupsReadDao.getTaoBaoGroupsListBySku(sku, page, rows);
    }

    /**
     * 通过SKU查询定金尾款信息数量
     * @param sku
     * @return
     */
    public Integer getTaoBaoGroupsListBySkuCount(String sku){
        return taoBaoGroupsReadDao.getTaoBaoGroupsListBySkuCount(sku);
    }

    /**
     * 添加定金尾款
     * @param map
     * @return
     */
    public Integer addTaoBaoGroups(Map<String, Object> map){
        return taoBaoGroupsWriteDao.addTaoBaoGroups(map);
    }

    /**
     * 更新定金尾款
     * @param map
     * @return
     */
    public Integer updateTaoBaoGroups(Map<String, Object> map){
        return taoBaoGroupsWriteDao.updateTaoBaoGroups(map);
    }

    /**
     * 删除定金尾款
     * @param ids
     * @return
     */
    public Integer delTaoBaoGroups(Integer[] ids){
        return taoBaoGroupsWriteDao.delTaoBaoGroups(ids);

    }

    /**
     * 通过sku和商品名称查询
     * @param sku
     * @param groupName
     * @return
     */
    public Map<String,Object> getTaoBaoGroupsBySkuAndName(String sku,String groupName){
        return taoBaoGroupsReadDao.getTaoBaoGroupsBySkuAndName(sku, groupName);
    }

    public TaoBaoGroups get(Integer id){
        return taoBaoGroupsReadDao.get(id);
    }

	public Map<String, Object> getTaoBaoGroupsById(Integer id) {
		return taoBaoGroupsReadDao.getTaoBaoGroupsById(id);
	}
}
