package com.haier.purchase.data.dao.purchase;

import java.util.Map;

import com.haier.purchase.data.model.InvBudgetOrg;



/**
 * 
 * @author 马军
 *
 */
public interface InvBudgetOrgDao {

    /**
     * 外部查询所有预算体
     * @param params 查询条件
     * @return
     */
    public InvBudgetOrg getAllBudgetOrg(Map<String, Object> params);


    /**
     * 获取预算体
     * @param params
     * @return
     */
    InvBudgetOrg getBudgetOrg(Map<String, Object> params);

}
