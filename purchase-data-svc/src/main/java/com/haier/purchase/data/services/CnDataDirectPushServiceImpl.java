package com.haier.purchase.data.services;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.dao.purchase.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.CnDataDirectPush;
import com.haier.purchase.data.model.CnT2PurchaseStock;
import com.haier.purchase.data.model.ExchangeGoods;
import com.haier.purchase.data.model.ReturnTable;
import com.haier.purchase.data.service.CnDataDirectPushService;
import com.haier.purchase.data.service.CnT2PurchaseStockService;

/**
 * Created by fuzhenxing on 2018/1/19.
 */
@Service
public class CnDataDirectPushServiceImpl implements CnDataDirectPushService{
	
	@Autowired
	CnDataDirectPushDao cnDataDirectPushDao;
	@Autowired
	ExchangeGoodsDao exchangeGoodsDao;
	@Autowired
	ReturnTableDao returnTableDao;
	@Autowired
	SequenceDao sequenceDao;
	@Override
	public List<ReturnTable> find3WInStock() {
		// TODO Auto-generated method stub
		return returnTableDao.find3WInStock();
	}

	@Override
	public List<CnDataDirectPush> find3WExchange(int max) {
		// TODO Auto-generated method stub
		return cnDataDirectPushDao.find3WExchange(max);
	}

	@Override
	public int update(CnDataDirectPush cnData) {
		// TODO Auto-generated method stub
		return cnDataDirectPushDao.update(cnData);
	}

	@Override
	public int insertEx(List<ExchangeGoods> list) {
		// TODO Auto-generated method stub
		return exchangeGoodsDao.insertEx(list);
	}
	@Override
	public List<ExchangeGoods> findExchangeTable() {
		// TODO Auto-generated method stub
		return exchangeGoodsDao.findExchangeTable();
	}

	@Override
	public int updateExpection(ExchangeGoods ex) {
		// TODO Auto-generated method stub
		 return exchangeGoodsDao.updateByPrimaryKey(ex);
	}
	
	@Override
	public int updateExchange(ExchangeGoods ex) {
		// TODO Auto-generated method stub
		 return exchangeGoodsDao.updateExchange(ex);
	}

	@Override
	public String findMax() {
		// TODO Auto-generated method stub
		 return sequenceDao.findMax();
	}

	@Override
	public String findReturnMax() {
		// TODO Auto-generated method stub
		return returnTableDao.findReturnMax();
	}

	@Override
	public List<CnDataDirectPush> find3WReturn(int max) {
		// TODO Auto-generated method stub
		return cnDataDirectPushDao.find3WReturn(max);
	}

	@Override
	public int insertRt(List<ReturnTable> list) {
		// TODO Auto-generated method stub
		return returnTableDao.insertRt(list);
	}

	@Override
	public int updateRtExpection(ReturnTable rt) {
		// TODO Auto-generated method stub
		return returnTableDao.updateRtExpection(rt);
	}

    @Override
    public int updateWD(ExchangeGoods ex) {
		return exchangeGoodsDao.updateWD(ex);
    }
	@Override
	public int updateReturn(ReturnTable rt) {
		return returnTableDao.updateReturn(rt);
	}

	@Override
	public int update3WDataMaxId(String max) {
		return sequenceDao.update3WDataMaxId(max);
	}

	@Override
	public List<ExchangeGoods> findAll(ExchangeGoods params) {
		return exchangeGoodsDao.findAll(params);
	}

    @Override
    public List<ReturnTable> findReturnGoods(ReturnTable params) {
        return returnTableDao.findReturnGoods(params);
    }

	@Override
	public int findReturnGoodsCount(ReturnTable params) {
		return returnTableDao.findReturnGoodsCount(params);
	}

	@Override
	public int findAllCount(ExchangeGoods params) {
		return exchangeGoodsDao.findAllCount(params);

	}

	@Override
    public ReturnTable findOneReturn(String id) {
        return returnTableDao.selectByPrimaryKey(Integer.parseInt(id));
    }


}
