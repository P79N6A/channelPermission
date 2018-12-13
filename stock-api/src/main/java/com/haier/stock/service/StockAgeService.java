package com.haier.stock.service;

import java.util.List;
import java.util.Map;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.ItemBase;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvStockInOut;
import com.haier.stock.model.StockAgeWapped;


public interface  StockAgeService {

	ServiceResult<List<StockAgeWapped>> getStockAgeList(PagerInfo pagerInfo,
			Map<String, Object> skuParams);
	
	ServiceResult<List<InvStockChannel>> getChannels();

	/**
	 * 批量记录出入库信息,出入库记录不存在则新增
	 * @param invStockInOuts 出入库记录列表
	 * @return 新增的出入库记录数量
	 */
	ServiceResult<Integer> stockInOutRecord(List<InvStockInOut> invStockInOuts);

    /**
     * 记录出入库信息,出入库记录不存在则新增
     * @param invStockInOut 出入库记录
     * @return 新增的出入库记录数量,新增为1,否则为0
     */
    ServiceResult<Integer> stockInOutRecord(InvStockInOut invStockInOut);

	ServiceResult<Integer> updateMtlInfoForStockAge(ItemBase mtl);
    
    

	
}
