package com.haier.purchase.data.service;

import com.haier.purchase.data.model.PrivilegeItem;


/**
 * Created by 张小丹 on 2014/7/22.
 */
public interface PurchaseCommonService {

    /**
     * 登录者权限情报取得
     * @param userId     ----登陆者ID
     * @return
     */
    public PrivilegeItem findPrivilege(String userId);
    
    /**订单流水号取得
     */
    public int getNextVal();


}
