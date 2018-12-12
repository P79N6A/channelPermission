/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.dao.purchase;

import java.util.List;

import com.haier.purchase.data.model.ZWDTABLEEntity;


public interface LesRRSOutDao {

	public void insertOutInfo(ZWDTABLEEntity info);

	public Integer isExist(ZWDTABLEEntity info);

	public List<ZWDTABLEEntity> findOutInfoBySO(List<String> so);
}
