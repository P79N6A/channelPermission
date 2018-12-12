package com.haier.stock.service;

import com.haier.stock.model.InvBaseStockDiff;

import java.util.List;


public interface InvBaseStockDiffService{

	/**
	 * 通过库位编码，物料编码，批次查询
	 * @param invBaseStockDiff
	 * @return
	 */
	List<InvBaseStockDiff> queryInvBaseStockDiff(Integer maxId, int topX);
	
	/**
	 * 通过库位编码，物料编码，批次更新
	 * @param invBaseStockDiff
	 */
	Integer updateInvBaseStockDiff( InvBaseStockDiff invBaseStockDiff);
	
}
