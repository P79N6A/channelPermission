package com.haier.stock.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvStockLockDao;
import com.haier.stock.model.InvStockLock;
import com.haier.stock.service.StockInvStockLockService;
@Service
public class StockInvStockLockServiceImpl implements StockInvStockLockService{
	@Autowired
	private InvStockLockDao invStockLockDao;
	@Override
	public InvStockLock getNotReleased(String refNo, String sku, String secCode) {
		// TODO Auto-generated method stub
		return invStockLockDao.getNotReleased(refNo, sku, secCode);
	}

	@Override
	public InvStockLock getLast(String refNo, String sku, String secCode) {
		// TODO Auto-generated method stub
		return invStockLockDao.getLast(refNo, sku, secCode);
	}
	 /**
     * 根据单据号获取冻结记录
     * @param refNo
     * @return
     */
	@Override
	public List<InvStockLock> getNotReleasedByRefNo(String refNo) {
		// TODO Auto-generated method stub
		return invStockLockDao.getNotReleasedByRefNo(refNo);
	}

	@Override
	public List<InvStockLock> getNotReleaseBySkuAndWh(String whCode, String sku, String channelCode) {
		// TODO Auto-generated method stub
		return invStockLockDao.getNotReleaseBySkuAndWh(whCode, sku, channelCode);
	}
	 /**
     * 获得需要重新分配渠道的记录
     * @return
     */
	@Override
	public List<InvStockLock> getProcessStockLock() {
		// TODO Auto-generated method stub
		return invStockLockDao.getProcessStockLock();
	}
	 /**
     * 根据单据号获取冻结记录
     * @param refNo
     * @return
     */
	@Override
	public List<InvStockLock> getNotReleasedByRefNoSku(String refNo, String sku) {
		// TODO Auto-generated method stub
		return invStockLockDao.getNotReleasedByRefNoSku(refNo, sku);
	}

	@Override
	public List<InvStockLock> getByRefNoAndSku(String refNo, String sku) {
		// TODO Auto-generated method stub
		return invStockLockDao.getByRefNoAndSku(refNo, sku);
	}

	@Override
	public Integer insert(InvStockLock stockLock) {
		// TODO Auto-generated method stub
		return invStockLockDao.insert(stockLock);
	}

	@Override
	public Integer update(InvStockLock stockLock) {
		// TODO Auto-generated method stub
		return invStockLockDao.update(stockLock);
	}
	 /**
     * 增加释放数
     * @param id
     * @param releaseQty
     * @return
     */
	@Override
	public Integer updateReleaseQty(Integer id, Integer releaseQty, String optUser) {
		// TODO Auto-generated method stub
		return invStockLockDao.updateReleaseQty(id, releaseQty, optUser);
	}
	 /**
	    * 增加占用数
	    * @param id
	    * @param lockQty
	    * @return
	    */
	@Override
	public Integer updateLockQty(Integer id, Integer lockQty, String optUser) {
		// TODO Auto-generated method stub
		return invStockLockDao.updateLockQty(id, lockQty, optUser);
	}
	   /**
     * 查询一段时间后没有释放的库存
     * @param lockTime
     * @param topx
     * @return
     */
	@Override
	public List<InvStockLock> getNoReleaseByLockTime(String lockTime, Integer topx) {
		// TODO Auto-generated method stub
		return invStockLockDao.getNoReleaseByLockTime(lockTime, topx);
	}

}
