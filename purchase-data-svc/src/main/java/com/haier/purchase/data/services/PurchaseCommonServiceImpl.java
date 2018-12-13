package com.haier.purchase.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.dao.purchase.PurchaseCommonDao;
import com.haier.purchase.data.model.PrivilegeItem;
import com.haier.purchase.data.service.PurchaseCommonService;


/**
 * Created by 张小丹 on 2014/7/22.
 */
@Service
public class PurchaseCommonServiceImpl implements PurchaseCommonService{
	
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(PurchaseCommonServiceImpl.class);

	@Autowired
	PurchaseCommonDao purchaseCommonDao;
    /**
     * 登录者权限情报取得
     * @param userId     ----登陆者ID
     * @return
     */
	@Override
    public PrivilegeItem findPrivilege(String userId){
		return purchaseCommonDao.findPrivilege(userId);
	}
    
    /**订单流水号取得
     */
	@Override
    public int getNextVal(){
		return purchaseCommonDao.getNextVal();
	}

	/**
	 * 登录者权限情报取得
	 * @param userId     ----登录者ID
	 * @return
	 */
	@Override
	public ServiceResult<PrivilegeItem> getPrivilege(String userId) {
		ServiceResult<PrivilegeItem> result = new ServiceResult<PrivilegeItem>();
		try {
			result.setResult(purchaseCommonDao.findPrivilege(userId));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获取登录者权限情报失败：", e);
		}
		return result;
	}

}
