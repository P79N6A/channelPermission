package com.haier.afterSale.model;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.afterSale.helper.PropertyHolder;
import com.haier.stock.model.InvStockOrderLockEntity;
import com.haier.stock.service.InvStockOrderLockService;

@Service
public class StockOrderLockModel {
	@Autowired
    private InvStockOrderLockService invStockOrderLockDao;

    public InvStockOrderLockService getInvStockOrderLockDao() {
        return invStockOrderLockDao;
    }

    public void setInvStockOrderLockDao(InvStockOrderLockService invStockOrderLockDao) {
        this.invStockOrderLockDao = invStockOrderLockDao;
    }

    /**
     * 下单锁库
     * 
     * @param sku 物料编码
     * @param storeCode 店铺ID
     * @param scode 库位
     * @param refNo 单据号(网单号)
     * @param source 订单来源
     * @param lockQty 锁定数量
     * @param lockUser 锁定人
     * @return
     * 
     */
    public Integer insertStockOrderLock(String sku, String storeCode, String scode, String refNo,
                                        String source, Integer lockQty, String lockUser) {
        String minute = PropertyHolder.getContextProperty("stock.lock.time.minute");
        Integer lockTime = Integer.valueOf(minute) * 60 * 1000;
        Date afterDate = new Date(System.currentTimeMillis() + lockTime);

        InvStockOrderLockEntity entity = invStockOrderLockDao.findInvStockOrderLockByRefNo(refNo);
        if (entity != null) {
            entity.setSku(sku);
            entity.setStoreCode(storeCode);
            entity.setScode(scode);
            entity.setRefNo(refNo);
            entity.setSource(source);
            entity.setLockQty(lockQty);
            entity.setLockTime(afterDate);
            entity.setLockUser(lockUser);
            Integer result = invStockOrderLockDao.updateStockOrderLock(entity);
            return result;
        } else {
            InvStockOrderLockEntity stockLockEntity = new InvStockOrderLockEntity();
            stockLockEntity.setSku(sku);
            stockLockEntity.setStoreCode(storeCode);
            stockLockEntity.setScode(scode);
            stockLockEntity.setRefNo(refNo);
            stockLockEntity.setSource(source);
            stockLockEntity.setLockQty(lockQty);
            stockLockEntity.setLockTime(afterDate);
            stockLockEntity.setLockUser(lockUser);
            Integer result = invStockOrderLockDao.insertStockOrderLock(stockLockEntity);
            return result;
        }

        //        InvStockOrderLockEntity entity = invStockOrderLockDao.findInvStockOrderLockByRefNo(refNo);
        //        if (entity != null) {
        //            return 0;
        //        }
        //        String minute = PropertyHolder.getContextProperty("stock.lock.time.minute");
        //        Integer lockTime = Integer.valueOf(minute) * 60 * 1000;
        //        Date date = new Date();
        //        Date afterDate = new Date(date.getTime() + lockTime);
        //
        //        InvStockOrderLockEntity stockLockEntity = new InvStockOrderLockEntity();
        //        stockLockEntity.setSku(sku);
        //        stockLockEntity.setStoreCode(storeCode);
        //        stockLockEntity.setScode(scode);
        //        stockLockEntity.setRefNo(refNo);
        //        stockLockEntity.setSource(source);
        //        stockLockEntity.setLockQty(lockQty);
        //        stockLockEntity.setLockTime(afterDate);
        //        stockLockEntity.setLockUser(lockUser);
        //        Integer result = invStockOrderLockDao.insertStockOrderLock(stockLockEntity);
        //        return result;
    }

    /**
     * 根据refNo查询数据
     * 
     * @param refNo
     * @return
     */
    public InvStockOrderLockEntity findInvStockOrderLockByRefNo(String refNo) {
        InvStockOrderLockEntity result = invStockOrderLockDao.findInvStockOrderLockByRefNo(refNo);
        return result;
    }

    /**
     * 锁库释放，删除数据
     * 
     * @return
     */
    public Integer releaseOrderLockByrefNo() {
        Integer result = invStockOrderLockDao.releaseOrderLockByRefNo();
        return result;
    }

    /**
     * 付款成功锁库释放，删除数据
     * @param refNo
     * @return
     */
    public Integer paymentSuccessReleaseOrderLock(String refNo) {
        Integer result = invStockOrderLockDao.paymentSuccessReleaseOrderLock(refNo);
        return result;
    }

}
