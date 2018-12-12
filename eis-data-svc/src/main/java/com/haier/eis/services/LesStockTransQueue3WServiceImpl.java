package com.haier.eis.services;

import java.util.List;
import java.util.Map;

import com.haier.eis.dao.eis.LesStockTransQueue3WDao;
import com.haier.eis.model.InventoryListOutput;
import com.haier.eis.model.LesStockTransQueue3W;
import com.haier.eis.service.LesStockTransQueue3WService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class LesStockTransQueue3WServiceImpl implements LesStockTransQueue3WService {
    @Autowired
    LesStockTransQueue3WDao lesStockTransQueue3WDao;

    @Override
    public LesStockTransQueue3W getByLesBillNo(String billNo) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.getByLesBillNo(billNo);
    }

    @Override
    public Integer insert(LesStockTransQueue3W stockTransQueues) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.insert(stockTransQueues);
    }

    @Override
    public Integer batchInsert(List<LesStockTransQueue3W> stockTransQueues) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.batchInsert(stockTransQueues);
    }

    @Override
    public List<Integer> getNotProcessedLesBatchId() {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.getNotProcessedLesBatchId();
    }

    @Override
    public List<Integer> getNotConvertedLesBatchId() {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.getNotConvertedLesBatchId();
    }

    @Override
    public Integer update(LesStockTransQueue3W stockTransQueue) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.update(stockTransQueue);
    }

    @Override
    public Integer updateAfterConvert(LesStockTransQueue3W stockTransQueue) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.updateAfterConvert(stockTransQueue);
    }

    @Override
    public List<LesStockTransQueue3W> getNotProcessByBatchId(Integer batchId) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.getNotProcessByBatchId(batchId);
    }

    @Override
    public List<LesStockTransQueue3W> getNotConvertedByBatchId(Integer batchId) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.getNotConvertedByBatchId(batchId);
    }

    @Override
    public LesStockTransQueue3W getById(Integer id) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.getById(id);
    }

    @Override
    public List<LesStockTransQueue3W> getByStatus(Integer status, Integer topx) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.getByStatus(status, topx);
    }

    @Override
    public List<LesStockTransQueue3W> getForFinance() {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.getForFinance();
    }

    @Override
    public Integer updateAfterDoFinance(Integer id) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.updateAfterDoFinance(id);
    }

    @Override
    public Integer updateStatus(Integer id, Integer status, Integer oldStatus, String msg) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.updateStatus(id, status, oldStatus, msg);
    }

    @Override
    public Integer updateSKU(Integer id, String sku) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.updateSKU(id, sku);
    }

    @Override
    public Integer updateIsManualSetChannel(Integer id, Integer status, Integer oldStatus, String msg,
                                            Integer isManualSetChannel) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.updateIsManualSetChannel(id, status, oldStatus, msg, isManualSetChannel);
    }

    @Override
    public Integer updateChannel(Integer id, String channel, String user) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.updateChannel(id, channel, user);
    }

    @Override
    public LesStockTransQueue3W getByOrderSn(String orderSn, String sku) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.getByOrderSn(orderSn, sku);
    }

    @Override
    public List<InventoryListOutput> queryLesStockTrans(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.queryLesStockTrans(paramMap);
    }

    @Override
    public int getRowCnts() {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.getRowCnts();
    }

    @Override
    public List<LesStockTransQueue3W> getLesStockTransQueue3WByCorderSn(String corderSn) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.getLesStockTransQueue3WByCorderSn(corderSn);
    }

    @Override
    public Integer delete(Integer id) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.delete(id);
    }

    @Override
    public List<LesStockTransQueue3W> getBySnSku(String orderSn, String sku) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.getBySnSku(orderSn, sku);
    }

    @Override
    public Integer updateLesStatus(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        return lesStockTransQueue3WDao.updateLesStatus(paramMap);
    }

	@Override
	public String querySecCode(String corderSn, String sku) {
		// TODO Auto-generated method stub
		return lesStockTransQueue3WDao.querySecCode(corderSn, sku);
	}
}
