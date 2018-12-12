package com.haier.stock.dao.stock;


import com.haier.stock.model.InvSgStockLogEntity;

/**
 * 吴坤洋
 * @author wukunyang
 *
 */
public interface InvSgStockLogDao {


     /**
     * 新增数据
     * @param  invSgStockLog
     * @return
     */
     public Integer insertInvSgStockLog(InvSgStockLogEntity invSgStockLog);

}