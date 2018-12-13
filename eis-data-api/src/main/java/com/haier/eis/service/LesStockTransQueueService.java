package com.haier.eis.service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import java.util.List;
import java.util.Map;

import com.haier.eis.model.LesStockTransQueue;

public interface LesStockTransQueueService {

  LesStockTransQueue getByLesBillNo(String lesBillNo);

  Integer insert(LesStockTransQueue stockTransQueues);

  List<LesStockTransQueue> getLesStockTransQueueByCorderSn(String corderSn);

  LesStockTransQueue queryCorderSn(String orderSn, String mark, String charg); //根据退货单号查询入库信息

  List<LesStockTransQueue> getForFinance();

  List<LesStockTransQueue> getForFinanceByParams(Map<String,Object> params);

  Integer updateAfterDoFinance(Integer id);

  LesStockTransQueue getById(Integer id);

  /**
   * 查询调拨单的状态更新
   * @author zhangming
   * @param list
   * @return
   */
List<Map<String, Object>> getDbStatusUpdated(List<Map<String, Object>> list);

    LesStockTransQueue findInStockCode(String corderSn);

  List<LesStockTransQueue> getLesStockTransQueueByCorderSnBillType(String lineNum);

  /**
   * 查询推送SAP信息
   * @param startTime
   * @param endTime
   * @param corderSn
   * @param status
   * @param billType
   * @param pagerInfo
   * @return
   */
  ServiceResult<List<LesStockTransQueue>> getPushSapResult(String startTime, String endTime, String corderSn,
      String status, String billType, PagerInfo pagerInfo);
}
