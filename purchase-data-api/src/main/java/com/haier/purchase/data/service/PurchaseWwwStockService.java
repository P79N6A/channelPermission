package com.haier.purchase.data.service;


import com.haier.purchase.data.model.WwwStockSaveRequire;
import java.util.List;

/**
 * @author:
 * @description:
 * @date:created in 2018/4/24 11:24
 * @modificed by:
 */
public interface PurchaseWwwStockService {

  /**
   * 接口日志
   * @param message
   * @param createdDate
   * @return zzb
   */
  int saveLog(String message, String createdDate);

  /**
   * 保存
   * @param list
   * @return
   */
  int save(List<WwwStockSaveRequire> list);

  /**
   * 删除
   * @return
   */
  int delete();

  /**
   * 更新
   * @param list
   * @return
   */
  int update(List<WwwStockSaveRequire> list);

  /**
   * 查询
   * @param productTypeName
   * @param enChannleId
   * @return
   */
  List<WwwStockSaveRequire> select(String productTypeName, String enChannleId);
}
