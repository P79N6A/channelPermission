package com.haier.purchase.data.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.CnDataDirectPush;
import com.haier.purchase.data.model.CnT2PurchaseStock;
import com.haier.purchase.data.model.ExchangeGoods;
import com.haier.purchase.data.model.ReturnTable;

/**
 * Created by fuzhenxing on 2018/1/19.
 */
public interface CnDataDirectPushService {

	public List<ReturnTable> find3WInStock(); //查询退货新表的数据

	public List<CnDataDirectPush> find3WExchange(int max);//查询换货新表最大的id

	public int update(CnDataDirectPush cnData);//匹配成功修改CnDataDirectPush的status
	
	public int insertEx(List<ExchangeGoods> list);//批量插入换货新表数据
	
	public List<ExchangeGoods> findExchangeTable();//查询换货新表数据

	public int updateExpection(ExchangeGoods ex);//匹配异常修改状态
	
	public int updateExchange(ExchangeGoods ex);

	public String findMax();

	public String findReturnMax();//查找退货新表最大id

	public List<CnDataDirectPush> find3WReturn(int parseInt);//查找符合退货的数据

	public int insertRt(List<ReturnTable> list);//批量插入退货新表

	public int updateRtExpection(ReturnTable rt);//匹配异常

    int updateWD(ExchangeGoods ex);//更新换货表新旧网单号和成功状态

	int updateReturn(ReturnTable rt);//更新成功状态

	int update3WDataMaxId(String max);

	List<ExchangeGoods> findAll(ExchangeGoods params);//查询换货列表

    List<ReturnTable> findReturnGoods(ReturnTable params);//查询退货拦截列表

    ReturnTable findOneReturn(String id);

    int findReturnGoodsCount(ReturnTable params);

	int findAllCount(ExchangeGoods params);
}
