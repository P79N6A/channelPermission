package com.haier.purchase.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.CgoDao;
import com.haier.purchase.data.model.CgoOrderItem;
import com.haier.purchase.data.service.PurchaseCgoService;

/**
 * Created by 黄俊 on 2014/8/4.
 */
@Service
public class PurchaseCgoServiceImpl implements PurchaseCgoService{

	
	@Autowired
	CgoDao cgoDao;
	/**
	 * 根据品类渠道取得在途
	 * 
	 * @param category_id
	 *            品类
	 * @param ed_channel_id
	 *            渠道
	 * @return
	 */
	@Override
	public List<CgoOrderItem> getOnwayNumByCateChan(String category_id, String ed_channel_id){
		return cgoDao.getOnwayNumByCateChan(category_id, ed_channel_id);
	}

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
	@Override
	public List<CgoOrderItem> getUsedNumByCateChan(String report_year_week, String category_id, String ed_channel_id){
		return cgoDao.getUsedNumByCateChan(report_year_week, category_id, ed_channel_id);
	}

}
