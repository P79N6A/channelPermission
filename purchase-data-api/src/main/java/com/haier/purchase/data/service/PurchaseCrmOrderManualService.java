/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.service;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.CrmOrderManualDetailItem;
import com.haier.purchase.data.model.CrmOrderManualItem;


/**
 *                       
 * @Filename: CrmOrderManualDao.java
 * @Version: 1.0
 * @Author: yanrp 燕如朋
 * @Email: yanrp110428@dhc.com.cn
 *
 */
public interface PurchaseCrmOrderManualService {

    /**
     * CRM采购订单表数据删除
     * @param CrmOrderManualItem
     * @return
     */
    public void deleteCRMOrderManual(Map<String, Object> params);

    /**
     * CRM采购订单表数据提交
     * @param CrmOrderManualItem
     * @return
     */
    public void commitCRMOrderManual(Map<String, Object> params);

    /**
     * CRM系统提交状态更新
     * @param CrmOrderManualItem
     * @return
     */
    public void commitCRMOrderManualForCRM(CrmOrderManualItem rmOrderManualItem);

    /**
     * CRM系统提交后SO单号更新
     * @param CrmOrderManualDetailItem
     * @return
     */
    public void updateCRMOrderManualDetailForCRM(CrmOrderManualDetailItem crmOrderManualDetailItem);

    /**
     * CRM采购订单表数据插入
     * @param CrmOrderManualItem
     * @return
     */
    public void insertCRMOrderManual(CrmOrderManualItem rmOrderManualItem);

    /**
     * CRM手工采购单详情表录入
     * @param CrmOrderManualDetailItem
     * @return
     */
    public void insertCRMOrderManualDetail(CrmOrderManualDetailItem crmOrderManualDetailItem);

    /**
     * CRM采购订单表数据更新
     * @param CrmOrderManualItem
     * @return
     */
    public int updateCRMOrderManual(CrmOrderManualItem rmOrderManualItem);

    /**
     * CRM手工采购单详情表更新
     * @param CrmOrderManualDetailItem
     * @return
     */
    public void updateCRMOrderManualDetail(CrmOrderManualDetailItem crmOrderManualDetailItem);

    /**
     * 获取CRM手工采购订单信息
     * @param Map<String, Object> params
     * @return
     */
    public List<CrmOrderManualDetailItem> findCrmOrderManuals(Map<String, Object> params);

    /**
     * 获取CRM手工采购订单条数
     * @param params
     * @return
     */
    public Integer findCrmOrderManualsCNT(Map<String, Object> params);

    /**
     * 获得条数
     * @return
     */
    public int getRowCnts();

    /**
     * 更新CRM手工采购状态
     * @param map
     */
    public void updateStatusFromCRM(Map map);

    /**
     * 更新CRM各入库出库时间
     * @param map
     */
    public void updateTimeFromCRM(Map map);
}
