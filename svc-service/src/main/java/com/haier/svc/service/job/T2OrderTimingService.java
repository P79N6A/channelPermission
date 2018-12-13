package com.haier.svc.service.job;

import java.util.Calendar;

import com.haier.common.ServiceResult;

public interface T2OrderTimingService {
	public void test();

	public void testAdd();

	public ServiceResult<Boolean> syncCRMOrderType();

	public void syncCrmT2Status();

	public void syncData();

	public ServiceResult<Calendar> GetInventoryTranFromLes();

	public ServiceResult<Calendar> GetInventoryTranFromLesTwo();

	public void cnPurchaseStorageToSap();
	
	public void addLbx();
	
	public void pushLogistics();
	
	/**
	 * 待内部审核的订单自动审核
	 */
	public void t2OrderAutoAudit();
	
	/**
	 * 更新 额度
	 */
	public void syncLimitSum();

	/**
	 * 更新 额度
	 */
	public void syncUpdateMtlInfoBySku();

	/**
	 * 预处理VOM信息失败后再处理
	 * @return
	 */
	ServiceResult<Boolean> processVomReceivedQueueFailStatus();

	/**
	 * JOB：处理库存变化关联的业务
	 * @return
	 */
	ServiceResult<Boolean> processStockBusinessQueues();

	//更新状态从已出wa库到已入日日顺库
	public void updateCrmRejectOrderInRRS();
}
