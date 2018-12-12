/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.service;

import java.util.List;

import com.haier.purchase.data.model.ZWDTABLEEntity;


public interface PurchaseLesRRSOutService {

	public void insertOutInfo(ZWDTABLEEntity info);

	public Integer isExist(ZWDTABLEEntity info);

	public List<ZWDTABLEEntity> findOutInfoBySO(List<String> so);
}
