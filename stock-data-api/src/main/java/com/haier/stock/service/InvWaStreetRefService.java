package com.haier.stock.service;

import com.haier.stock.model.InvWaStreetRefEntity;

import java.util.List;


/**
 * [WA库位与街道关系表]Dao
 * <p>Table: <strong>inv_wa_street_ref</strong>
 * @Filename: InvWaStreetRefDao.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
public interface InvWaStreetRefService {

	/**
     * 根据街道id取得库位编码list
     * @param  id
     * @return
     */
     public List<String> findInvWaStreetRefByStreetId(Integer streetId);
     /**
      * 获取全部
      * @param streetId
      * @return
      */
     public List<InvWaStreetRefEntity> findInvWaStreetRefAll();


}