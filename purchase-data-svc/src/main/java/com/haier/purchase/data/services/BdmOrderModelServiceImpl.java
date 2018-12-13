package com.haier.purchase.data.services;

import com.haier.purchase.data.dao.purchase.BdmOrderDao;
import com.haier.purchase.data.model.BdmOrder;
import com.haier.purchase.data.model.OutType;
import com.haier.purchase.data.service.BdmOrderModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.haier.svc.model.OMSOrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/4/24 16:54
 * @modificed by:
 */
@Service
public class BdmOrderModelServiceImpl implements BdmOrderModelService {

  private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
      .getLogger(BdmOrderModelServiceImpl.class);

  @Autowired
  private BdmOrderDao bdmOrderDao;
//  @Autowired
//  private OMSOrderModel omsOrderModel;

  /**
   * 查询数据
   */
  @Override
  public List<BdmOrder> findBdmOrder(Map<String, Object> params) {
    return bdmOrderDao.findBdmOrder(params);
  }

  /**
   * 查询数据条数
   */
  @Override
  public Integer findBdmOrderCNT(Map<String, Object> params) {
    return bdmOrderDao.findBdmOrderCNT(params);
  }

  /**
   * 创建BDM样表
   */
  @Override
  public void insertBdmOrder(Map<String, Object> params) {
     bdmOrderDao.insertBdmOrder(params);
  }

  /**
   * 更新BDM样表
   */
  @Override
  public void updateBdmOrder(Map<String, Object> params) {
     bdmOrderDao.updateBdmOrder(params);
  }

  /**
   * 删除BDM样表
   */
  @Override
  public void deleteBdmOrder(Map<String, Object> params) {
    bdmOrderDao.deleteBdmOrder(params);
  }

}
