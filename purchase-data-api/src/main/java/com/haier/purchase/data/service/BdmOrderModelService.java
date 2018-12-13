package com.haier.purchase.data.service;

import com.haier.purchase.data.model.BdmOrder;
import java.util.List;
import java.util.Map;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/4/24 16:51
 * @modificed by:
 */
public interface BdmOrderModelService {


  /**
   * 查询数据
   */
  public List<BdmOrder> findBdmOrder(Map<String, Object> params);

  /**
   * 查询数据条数
   */
  public Integer findBdmOrderCNT(Map<String, Object> params);

  /**
   * 创建BDM样表
   */
  public void insertBdmOrder(Map<String, Object> params);

  /**
   * 创建BDM样表
   */
  public void updateBdmOrder(Map<String, Object> params);

  /**
   * 删除bdm样表
   */
  public void deleteBdmOrder(Map<String, Object> params);
}
