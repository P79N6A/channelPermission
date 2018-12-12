package com.haier.shop.dao.shopread;

import com.haier.shop.model.InvSgScodeStreetsRefEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * [顺逛自有库位与街道关系]Dao
 * <p>Table: <strong>inv_sg_scode_streets_ref</strong>
 * @Filename: InvSgScodeStreetsRefDao.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
@Mapper
public interface InvSgScodeStreetsRefReadDao {

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
      public List<String> findScodeByStreetIdAndStoreId(@Param("storeId") String storeId, @Param("streetId") Integer streetId);

}