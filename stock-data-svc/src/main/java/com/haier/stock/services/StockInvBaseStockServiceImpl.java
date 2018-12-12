package com.haier.stock.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.stock.dao.stock.InvBaseStockLogDao;
import com.haier.stock.dao.stock.InvStockDao;
import com.haier.stock.dao.stock.InvStoreDao;
import com.haier.stock.model.BaseStock;
import com.haier.stock.model.InvBaseStockLog;
import com.haier.stock.model.InvStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.stock.dao.stock.InvBaseStockDao;
import com.haier.stock.model.InvBaseStock;
import com.haier.stock.model.InvBaseStockEx;
import com.haier.stock.service.StockInvBaseStockService;
@Service
public class StockInvBaseStockServiceImpl implements StockInvBaseStockService{
	@Autowired
	private InvBaseStockDao invBaseStockDao;
	@Autowired
	InvStockDao invStockDao;
	@Autowired
	InvBaseStockLogDao invBaseStockLogDao;
	@Autowired
	InvStoreDao invStoreDao;
	 /**
     * 获取并锁定
     * @param sku
     * @param secCode
     * @return
     */
	@Override
	public InvBaseStock getForUpdate(String sku, String secCode) {
		// TODO Auto-generated method stub
		return invBaseStockDao.getForUpdate(sku, secCode);
	}

	@Override
	public Integer insert(InvBaseStock baseStock) {
		// TODO Auto-generated method stub
		return invBaseStockDao.insert(baseStock);
	}

	@Override
	public Integer update(InvBaseStock baseStock) {
		// TODO Auto-generated method stub
		return invBaseStockDao.update(baseStock);
	}

	@Override
	public Integer updateQtyForFrozen(InvBaseStock baseStock) {
		// TODO Auto-generated method stub
		return invBaseStockDao.updateQtyForFrozen(baseStock);
	}
	 /**
     * 更新stockQty 和 frozenQty
     * @param sto_id
     * @param qty
     * @param releaseQty
     * @param time
     * @return
     */
	@Override
	public Integer updateQty(Integer sto_id, Integer qty, Integer releaseQty, Date time) {
		// TODO Auto-generated method stub
		return invBaseStockDao.updateQty(sto_id, qty, releaseQty, time);
	}
	 /**
     * 更新stockQty 
     * @param sto_id
     * @param qty
     * @param time
     * @return
     */
	@Override
	public Integer updateStockQty(Integer sto_id, Integer qty, Date time) {
		// TODO Auto-generated method stub
		return invBaseStockDao.updateStockQty(sto_id, qty, time);
	}

    /**
     * 冻结库存
     * @param stoId
     * @param frozenQty
     * @param time
     * @return
     */
	@Override
	public Integer frozenQty(Integer stoId, Integer frozenQty, Date time) {
		// TODO Auto-generated method stub
		return invBaseStockDao.frozenQty(stoId, frozenQty, time);
	}
	/**
     * 释放冻结库存
     * @param stoId
     * @param releaseQty
     * @param time
     * @return
     */
	@Override
	public Integer releaseQty(Integer stoId, Integer releaseQty, Date time) {
		// TODO Auto-generated method stub
		return invBaseStockDao.releaseQty(stoId, releaseQty, time);
	}
	   
    /**
     * 根据物料编码和库存编码查询库存，批次10
     * @param sku
     * @param lesSecCode
     * @return
     */
	@Override
	public InvBaseStock queryBySkuAndLesSecCode(String sku, String lesSecCode) {
		// TODO Auto-generated method stub
		return invBaseStockDao.queryBySkuAndLesSecCode(sku, lesSecCode);
	}
	public BaseStock get(String sku, String code){
		return invBaseStockDao.get(sku, code);
	}

	public BaseStock getByItemProperty( String sku,  String code, String itemProperty){
		return invBaseStockDao.getByItemProperty(sku, code,itemProperty);

	}

	@Override
	public List<InvBaseStock> getPageByCondition(InvBaseStock entity, int start, int rows) {
		return invBaseStockDao.getPageByCondition(entity,start,rows);
	}

	@Override
	public long getPagerCount(InvBaseStock entity) {
		return invBaseStockDao.getPagerCount(entity);
	}

	@Override
	public List<InvBaseStock> getMachinePageByCondition(InvBaseStock invBaseStock, int start, int rows) {
		return invBaseStockDao.getMachinePageByCondition(invBaseStock,start,rows);
	}

	@Override
	public long getMachinePagerCount(InvBaseStock invBaseStock) {
		return invBaseStockDao.getMachinePagerCount(invBaseStock);
	}

	@Override
	public List<InvBaseStockLog> getLogPageByCondition(InvBaseStockLog condition, int start, int pageSize) {
		return invBaseStockLogDao.getLogPageByCondition(condition,start,pageSize);
	}

	@Override
	public long getLogPagerCount(InvBaseStockLog condition) {
		return invBaseStockLogDao.getLogPagerCount(condition);
	}

	@Override
	public List<InvStore> getStorePageByCondition(InvStore condition, int start, int pageSize) {
		return invStoreDao.getStorePageByCondition(condition,start,pageSize);
	}

	@Override
	public long getStorePagerCount(InvStore condition) {
		return invStoreDao.getStorePagerCount(condition);
	}

	@Override
	public List<InvBaseStockEx> queryInvBaseStockList(InvBaseStockEx invBaseStock, PagerInfo pager) {
		// TODO Auto-generated method stub
		return invBaseStockDao.queryInvBaseStockList(invBaseStock, pager);
	}

	@Override
	public int getRowCnt() {
		// TODO Auto-generated method stub
		return invBaseStockDao.getRowCnt();
	}

	@Override
	public List<InvBaseStockEx> queryInvStockList(InvBaseStockEx invBaseStock, PagerInfo pager) {
		// TODO Auto-generated method stub
		return invBaseStockDao.queryInvStockList(invBaseStock, pager);
	}

	@Override
	public List<InvBaseStockLog> queryInvBaseStockLogList(InvBaseStockLog log, PagerInfo pager) {
		// TODO Auto-generated method stub
		return invBaseStockDao.queryInvBaseStockLogList(log, pager);
	}

	@Override
	public List<Map<String, Object>> queryByfrozenQtyGtStockQty(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return invBaseStockDao.queryByfrozenQtyGtStockQty(params);
	}

	@Override
	public Integer queryByfrozenQtyGtStockQtyCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return invBaseStockDao.queryByfrozenQtyGtStockQtyCount(params);
	}
	
}
