package com.haier.purchase.data.dao.purchase;


import com.haier.purchase.data.model.BdmOrder;
import com.haier.purchase.data.model.OutType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface BdmOrderDao {

  /**
   * 查询数据
   */
  public List<BdmOrder> findBdmOrder(Map<String, Object> params);

  /**
   * 查询数据条数
   */
  public Integer findBdmOrderCNT(Map<String, Object> params);

  /**
   * 创建数据
   */
  public void insertBdmOrder(Map<String, Object> params);

  /**
   * 创建时调用接口后更新本地数据库
   */
  public void updateBdmOrder(Map<String, Object> params);

  /**
   * 删除数据
   */
  public int deleteBdmOrder(Map<String, Object> params);

  /**
   * 定时任务，清空本地数据库
   */
  public void syncDeleteBdmOrder();

  /**
   * 定时任务，同步本地数据库
   */
  public void syncInsertBdmOrder(List<OutType> outList);

}
