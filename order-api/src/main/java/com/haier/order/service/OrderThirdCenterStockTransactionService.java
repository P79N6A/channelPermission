//package com.haier.orderthird.service;
//
//import java.util.List;
//
//import com.haier.common.ServiceResult;
//import com.haier.stock.model.InvStockTransaction;
//
///**
// * 库存交易服务
// * Created by 钊 on 2014/4/1.
// */
//public interface OrderThirdCenterStockTransactionService {
//
//    /**
//     * 新增CBS交易记录
//     * @param stockTransaction
//     * @return
//     */
//    ServiceResult<Boolean> addStockTransaction(InvStockTransaction stockTransaction);
//
//    /**
//     * 新增CBS交易记录，验证les_stock_trans_queue是否执行过
//     * @param stockTransaction
//     * @param lesStockTransQueueId
//     * @return
//     */
//    ServiceResult<Boolean> addStockTransactionWithCheck(InvStockTransaction stockTransaction,
//                                                        Integer lesStockTransQueueId);
//
//    /**
//     * 获取没有处理关联业务的库存交易记录
//     * @return
//     */
//    ServiceResult<List<InvStockTransaction>> getNotProcessBusiness();
//
//    /**
//     * 更新关联业务处理状态
//     * @param id
//     * @param businessProcessStatus
//     * @return
//     */
//    ServiceResult<Boolean> updateBusinessProcessStatus(Integer id, Integer businessProcessStatus);
//
//    ServiceResult<Boolean> processForGenerateStockAgeInOut();
//
//    /**
//     * 更新库存
//     * @return
//     */
//    ServiceResult<Boolean> processForUpdateStock();
//
//    ServiceResult<Boolean> processForDelay();
//
//    ServiceResult<List<InvStockTransaction>> queryData(Integer id, Integer num);
//}
