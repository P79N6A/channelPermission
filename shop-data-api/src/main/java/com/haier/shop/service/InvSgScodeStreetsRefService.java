package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.InvSgScodeStreetsRefEntity;




/**
 * [顺逛自有库位与街道关系]Dao
 * <p>Table: <strong>inv_sg_scode_streets_ref</strong>
 * @Filename: InvSgScodeStreetsRefDao.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
public interface InvSgScodeStreetsRefService {

	/**
     * 根据id取得数据
     * @param  id
     * @return
     */
     public InvSgScodeStreetsRefEntity findInvSgScodeStreetsRefById(String id);

     /**
      * 根据街道与店铺ID获取库位信息
      * @param  storeId
      * @return
      */
      public List<String> findScodeByStreetIdAndStoreId( String storeId, Integer streetId);

}