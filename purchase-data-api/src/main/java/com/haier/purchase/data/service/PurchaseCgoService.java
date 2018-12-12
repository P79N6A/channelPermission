package com.haier.purchase.data.service;

import java.util.List;

import com.haier.purchase.data.model.CgoOrderItem;

/**
 * Created by 黄俊 on 2014/8/4.
 */
public interface PurchaseCgoService {


	/**
	 * 根据品类渠道取得在途
	 * 
	 * @param category_id
	 *            品类
	 * @param ed_channel_id
	 *            渠道
	 * @return
	 */
	public List<CgoOrderItem> getOnwayNumByCateChan(String category_id, String ed_channel_id);

	/**
	 * 根据品类渠道取得本周已用
	 * 
	 * @param report_year_week
	 *            本周
	 * @param category_id
	 *            品类
	 * @param ed_channel_id
	 *            渠道
	 * @return
	 */
	public List<CgoOrderItem> getUsedNumByCateChan(String report_year_week, String category_id, String ed_channel_id);

}
