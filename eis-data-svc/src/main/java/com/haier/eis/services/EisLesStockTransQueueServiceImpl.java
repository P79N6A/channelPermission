package com.haier.eis.services;


import com.haier.eis.dao.eis.LesStockTransQueueDao;
import com.haier.eis.model.InventoryListOutput;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.EisLesStockTransQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class EisLesStockTransQueueServiceImpl implements EisLesStockTransQueueService {
    @Autowired
    private LesStockTransQueueDao lesStockTransQueueDao;
    @Override
    public LesStockTransQueue getByLesBillNo(String billNo){
            return lesStockTransQueueDao.getByLesBillNo(billNo);
    }
    @Override
    public Integer insert(LesStockTransQueue stockTransQueues){
        return lesStockTransQueueDao.insert(stockTransQueues);
    }
    @Override
    public Integer batchInsert(List<LesStockTransQueue> stockTransQueues){
        return lesStockTransQueueDao.batchInsert(stockTransQueues);
    }
    @Override
    public List<Integer> getNotProcessedLesBatchId(){
        return lesStockTransQueueDao.getNotProcessedLesBatchId();
    }
    @Override
    public List<Integer> getNotConvertedLesBatchId(){
        return lesStockTransQueueDao.getNotConvertedLesBatchId();
    }
    @Override
    public Integer update(LesStockTransQueue stockTransQueue){
        return lesStockTransQueueDao.update(stockTransQueue);
    }
    @Override
    public Integer updateAfterConvert(LesStockTransQueue stockTransQueue){
        return lesStockTransQueueDao.updateAfterConvert(stockTransQueue);
    }
    @Override
    public List<LesStockTransQueue> getNotProcessByBatchId(Integer batchId){
        return lesStockTransQueueDao.getNotProcessByBatchId(batchId);
    }
    @Override
    public List<LesStockTransQueue> getNotConvertedByBatchId(Integer batchId){
        return lesStockTransQueueDao.getNotConvertedByBatchId(batchId);
    }
    @Override
    public LesStockTransQueue getById(Integer id){
        return lesStockTransQueueDao.getById(id);
    }

    /**
     * 获取指定状态的队列
     * @param status
     * @param topx 数量
     * @return
     */
    @Override
    public List<LesStockTransQueue> getByStatus(Integer status, Integer topx){
        return lesStockTransQueueDao.getByStatus(status,topx);
    }
    @Override
    public List<LesStockTransQueue> getForFinance(){
        return lesStockTransQueueDao.getForFinance();
    }
    @Override
    public Integer updateAfterDoFinance(Integer id){
        return lesStockTransQueueDao.updateAfterDoFinance(id);
    }
    @Override
    public Integer updateStatus(Integer id,Integer status, Integer oldStatus,String msg){
        return lesStockTransQueueDao.updateStatus(id,status,oldStatus,msg);
    }
    @Override
    public Integer updateSKU(Integer id,String sku){
        return lesStockTransQueueDao.updateSKU(id,sku);
    }

    /**
     * 指定是否可以手工修正渠道
     * @param id
     * @param status
     * @param oldStatus
     * @param msg
     * @param isManualSetChannel
     * @return
     */
    @Override
    public Integer updateIsManualSetChannel(Integer id,Integer status,
                                     Integer oldStatus,
                                     String msg,
                                     Integer isManualSetChannel){
        return lesStockTransQueueDao.updateIsManualSetChannel(id, status, oldStatus, msg, isManualSetChannel);
    }
    @Override
    public Integer updateChannel(Integer id, String channel,
                          String user){
        return lesStockTransQueueDao.updateChannel(id, channel, user);
    }
    @Override
    public LesStockTransQueue getByOrderSn(String orderSn,String sku){
        return lesStockTransQueueDao.getByOrderSn(orderSn, sku);
    }
    @Override
    public List<InventoryListOutput> queryLesStockTrans(Map<String, Object> paramMap){
        return lesStockTransQueueDao.queryLesStockTrans(paramMap);
    }

    /**
     * 获得条数
     *
     * @return
     */
    @Override
    public int getRowCnts(){
        return lesStockTransQueueDao.getRowCnts();
    }
    @Override
    public List<LesStockTransQueue> getLesStockTransQueueByCorderSn(String corderSn){
        return lesStockTransQueueDao.getLesStockTransQueueByCorderSn(corderSn);
    }
    @Override
    public Integer delete(Integer id){
        return lesStockTransQueueDao.delete(id);
    }
    @Override
    public List<LesStockTransQueue> getBySnSku(String orderSn, String sku){
        return lesStockTransQueueDao.getBySnSku(orderSn,sku);
    }
    @Override
    public Integer updateLesStatus(Map<String, Object> paramMap){
        return lesStockTransQueueDao.updateLesStatus(paramMap);
    }
	@Override
	public List<LesStockTransQueue> getDelayTrans(int page, int size) {
		// TODO Auto-generated method stub
		return lesStockTransQueueDao.getDelayTrans(page, size);
	}
	@Override
	public int getDelayTransCount() {
		// TODO Auto-generated method stub
		return lesStockTransQueueDao.getDelayTransCount();
	}
}
