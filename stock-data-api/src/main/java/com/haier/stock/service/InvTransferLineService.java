package com.haier.stock.service;

import com.haier.stock.model.InvTransferLine;

public interface InvTransferLineService {
	InvTransferLine getTransferLine(String refDHNo);

	/**
	 * 根据调拨网单号码查询数据是否存在
	 * @param lineNum
	 * @return
	 */
	Integer getByLineNum(String lineNum);
}
