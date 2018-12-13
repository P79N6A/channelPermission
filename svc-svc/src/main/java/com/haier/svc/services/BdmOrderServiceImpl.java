package com.haier.svc.services;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.BdmOrder;
import com.haier.purchase.data.service.BdmOrderModelService;
import com.haier.svc.bean.transforecastpracticalfromb2ctooms.OutType;
import com.haier.svc.model.OMSOrderModel;
import com.haier.svc.service.BdmOrderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BdmOrderServiceImpl implements BdmOrderService {

  private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
      .getLogger(BdmOrderServiceImpl.class);

  @Autowired
  private BdmOrderModelService bdmOrderModelService;
  @Autowired
  private OMSOrderModel omsOrderModel;

  /**
   * 查询数据
   *
   */
  @Override
  public ServiceResult<List<BdmOrder>> findBdmOrder(Map<String, Object> params) {
    ServiceResult<List<BdmOrder>> result = new ServiceResult<List<BdmOrder>>();
    try {
      result.setResult(bdmOrderModelService.findBdmOrder(params));
      PagerInfo pi = new PagerInfo();
      pi.setRowsCount(bdmOrderModelService.findBdmOrderCNT(params));
      result.setPager(pi);
    } catch (Exception ex) {
      result.setSuccess(false);
      result.setMessage(ex.getMessage());
      log.error("获取BDM样表信息失败：", ex);
    }
    return result;

  }

  /**
   * 创建bdm样表数据
   *
   */
  @Override
  public int insertBdmOrder(Map<String, Object> params) {
    int status = 0;
    try {
      //创建数据检验重复
      Map<String, Object> repeatMap = new HashMap<String, Object>();
      repeatMap.put("itemcode", params.get("itemcode"));
      int repeatCount = bdmOrderModelService.findBdmOrderCNT(repeatMap);
      //数据重复无法插入，返回-1
      if (repeatCount != 0) {
        status = -1;
      } else {
        //重复校验通过，本地库插入数据
        bdmOrderModelService.insertBdmOrder(params);
      }
      List<OutType> out = new ArrayList<OutType>();
      OutType outType = new OutType();
      //调用接口
      out = omsOrderModel.BdmOrder(params);
      if (out.size() > 0) {
        outType = out.get(0);
        params.put("salesChanManagerID", outType.getSalesChanManagerID());
        params.put("salesChanManagerName", outType.getSalesChanManagerName());
        params.put("pltype", outType.getPltype());
        params.put("productLineId", outType.getProductLineId());
        params.put("plname", outType.getPlname());
        params.put("productSeriesId", outType.getProductSeriesId());
        params.put("categorycode", outType.getCategorycode());
        params.put("catn", outType.getCatn());
        params.put("tradeCode", outType.getTradeCode());
        params.put("smbCcode", outType.getSmbCcode());
        params.put("smbName", outType.getSmbName());
        params.put("message", outType.getMessage());
        bdmOrderModelService.updateBdmOrder(params);
        //调用接口成功返回1；失败返回2；
        if ("S".equals(outType.getMessage())) {
          status = 1;
        } else {
          status = 2;
        }
      }
    } catch (Exception ex) {
      //发生异常返回0；
      log.error("创建BDM样表时发生异常", ex);
      status = 0;
    }
    return status;
  }

  /**
   * 删除数据
   *
   */
  @Override
  public int deleteBdmOrder(Map<String, Object> params) {
    // TODO Auto-generated method stub
    int status = 0;
    try {
      //删除本地数据库
      bdmOrderModelService.deleteBdmOrder(params);
      //调用接口
      List<OutType> out = new ArrayList<OutType>();
      OutType outType = new OutType();
      out = omsOrderModel.BdmOrder(params);
      if (out.size() > 0) {
        outType = out.get(0);
        if ("S".equals(outType.getMessage())) {
          status = 1;
        } else {
          status = 2;
        }
      }
    } catch (Exception ex) {
      log.error("删除BDM样表时发生异常", ex);
      status = 0;
    }
    return status;
  }

}
