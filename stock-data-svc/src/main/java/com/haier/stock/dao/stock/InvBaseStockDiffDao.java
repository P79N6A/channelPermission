package com.haier.stock.dao.stock;

import java.util.List;

import com.haier.stock.model.InvBaseStockDiff;
import org.apache.ibatis.annotations.Param;



public interface InvBaseStockDiffDao{

	/**
	 * 通过库位编码，物料编码，批次查询
	 * @param maxId
	 * @return
	 */
	List<InvBaseStockDiff> queryInvBaseStockDiff(@Param("maxId")Integer maxId, @Param("topX") int topX);
	
	/**
	 * 通过库位编码，物料编码，批次更新
	 * @param invBaseStockDiff
	 */
	Integer updateInvBaseStockDiff(@Param("invBaseStockDiff") InvBaseStockDiff invBaseStockDiff);
	
}
