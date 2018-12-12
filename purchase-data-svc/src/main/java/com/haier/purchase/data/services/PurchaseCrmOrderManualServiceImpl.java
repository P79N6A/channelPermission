/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.CrmOrderManualDao;
import com.haier.purchase.data.model.CrmOrderManualDetailItem;
import com.haier.purchase.data.model.CrmOrderManualItem;
import com.haier.purchase.data.service.PurchaseCrmOrderManualService;


/**
 *                       
 * @Filename: CrmOrderManualDao.java
 * @Version: 1.0
 * @Author: yanrp 燕如朋
 * @Email: yanrp110428@dhc.com.cn
 *
 */
@Service
public class PurchaseCrmOrderManualServiceImpl implements PurchaseCrmOrderManualService{

	@Autowired
	CrmOrderManualDao crmOrderManualDao;
    /**
     * CRM采购订单表数据删除
     * @param CrmOrderManualItem
     * @return
     */
	@Override
    public void deleteCRMOrderManual(Map<String, Object> params){
    	crmOrderManualDao.deleteCRMOrderManual(params);
    }

    /**
     * CRM采购订单表数据提交
     * @param CrmOrderManualItem
     * @return
     */
	@Override
    public void commitCRMOrderManual(Map<String, Object> params){
		crmOrderManualDao.commitCRMOrderManual(params);
    }

    /**
     * CRM系统提交状态更新
     * @param CrmOrderManualItem
     * @return
     */
	@Override
    public void commitCRMOrderManualForCRM(CrmOrderManualItem rmOrderManualItem){
		crmOrderManualDao.commitCRMOrderManualForCRM(rmOrderManualItem);
	}

    /**
     * CRM系统提交后SO单号更新
     * @param CrmOrderManualDetailItem
     * @return
     */
	@Override
    public void updateCRMOrderManualDetailForCRM(CrmOrderManualDetailItem crmOrderManualDetailItem){
		crmOrderManualDao.updateCRMOrderManualDetailForCRM(crmOrderManualDetailItem);
	}

    /**
     * CRM采购订单表数据插入
     * @param CrmOrderManualItem
     * @return
     */
	@Override
    public void insertCRMOrderManual(CrmOrderManualItem rmOrderManualItem){
		crmOrderManualDao.insertCRMOrderManual(rmOrderManualItem);
	}

    /**
     * CRM手工采购单详情表录入
     * @param CrmOrderManualDetailItem
     * @return
     */
	@Override
    public void insertCRMOrderManualDetail(CrmOrderManualDetailItem crmOrderManualDetailItem){
		crmOrderManualDao.insertCRMOrderManualDetail(crmOrderManualDetailItem);
	}

    /**
     * CRM采购订单表数据更新
     * @param CrmOrderManualItem
     * @return
     */
	@Override
    public int updateCRMOrderManual(CrmOrderManualItem rmOrderManualItem){
		return crmOrderManualDao.updateCRMOrderManual(rmOrderManualItem);
	}

    /**
     * CRM手工采购单详情表更新
     * @param CrmOrderManualDetailItem
     * @return
     */
	@Override
    public void updateCRMOrderManualDetail(CrmOrderManualDetailItem crmOrderManualDetailItem){
		crmOrderManualDao.updateCRMOrderManualDetail(crmOrderManualDetailItem);
	}

    /**
     * 获取CRM手工采购订单信息
     * @param Map<String, Object> params
     * @return
     */
	@Override
    public List<CrmOrderManualDetailItem> findCrmOrderManuals(Map<String, Object> params){
		return crmOrderManualDao.findCrmOrderManuals(params);
	}

    /**
     * 获取CRM手工采购订单条数
     * @param params
     * @return
     */
	@Override
    public Integer findCrmOrderManualsCNT(Map<String, Object> params){
		return crmOrderManualDao.findCrmOrderManualsCNT(params);
	}

    /**
     * 获得条数
     * @return
     */
	@Override
    public int getRowCnts(){
		return crmOrderManualDao.getRowCnts();
	}

    /**
     * 更新CRM手工采购状态
     * @param map
     */
	@Override
    public void updateStatusFromCRM(Map map){
		crmOrderManualDao.updateStatusFromCRM(map);
	}

    /**
     * 更新CRM各入库出库时间
     * @param map
     */
	@Override
    public void updateTimeFromCRM(Map map){
		crmOrderManualDao.updateTimeFromCRM(map);;	
	}
}
