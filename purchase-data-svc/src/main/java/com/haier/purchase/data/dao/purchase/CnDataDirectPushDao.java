package com.haier.purchase.data.dao.purchase;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.CnDataDirectPush;
import com.haier.purchase.data.model.CnT2PurchaseStock;

/**
 * Created by fuzhenxing on 2018/1/19.
 */
public interface CnDataDirectPushDao {


	public List<CnDataDirectPush> find3WExchange(int max);

	public int update(CnDataDirectPush cnData);

	public List<CnDataDirectPush> find3WReturn(int max);

}
