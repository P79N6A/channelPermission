package com.haier.purchase.data.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.InvBudgetOrgDao;
import com.haier.purchase.data.model.InvBudgetOrg;
import com.haier.purchase.data.service.PurchaseInvBudgetOrgService;
@Service
public class PurchaseInvBudgetOrgServiceImpl implements PurchaseInvBudgetOrgService {
	@Autowired
	private InvBudgetOrgDao budgetOrgDao;
	  /**
     * 外部查询所有预算体
     * @param params 查询条件
     * @return
     */
	@Override
	public InvBudgetOrg getAllBudgetOrg(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return budgetOrgDao.getAllBudgetOrg(params);
	}
	 /**
     * 获取预算体
     * @param params
     * @return
     */
	@Override
	public InvBudgetOrg getBudgetOrg(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return budgetOrgDao.getBudgetOrg(params);
	}

}
