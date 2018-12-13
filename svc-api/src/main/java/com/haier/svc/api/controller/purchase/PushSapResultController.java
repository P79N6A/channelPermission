package com.haier.svc.api.controller.purchase;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.ConvertUtil;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.purchase.data.model.PurchaseOrderQueueWrapper;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: liwei
 * @Description:
 * @Date: Create in 10:36 2018/8/27
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/pushSapResult")
public class PushSapResultController {

  private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager
      .getLogger(PushSapResultController.class);

  @Autowired
  private LesStockTransQueueService lesStockTransQueueService;

  @RequestMapping(value = {"/getPushSapResult"}, method = {RequestMethod.GET})
  public String importPurchaseOrder() {
    return "purchase/pushSapResult";
  }

  @RequestMapping(value = {"/getPurchaseOrderQueue"})
  @ResponseBody
  public JSONObject getPurchaseOrderQueue(
      @RequestParam(required = false) String startTime,
      @RequestParam(required = false) String endTime,
      @RequestParam(required = false) String corderSn,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String billType,
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer rows) {
    PagerInfo pagerInfo = new PagerInfo(rows, page);
    JSONObject json = new JSONObject();
    try {

      //根据条件查询采购订单明细列表
      ServiceResult<List<LesStockTransQueue>> result = lesStockTransQueueService.getPushSapResult(startTime,
          endTime, corderSn, status, billType, pagerInfo);
      if (result.getSuccess() && result.getResult() != null) {
        json.put("rows", result.getResult());
        json.put("total", Long.valueOf(result.getPager().getRowsCount()));
      }

    } catch (Exception e) {
      json.put("rows", "");
      json.put("total", "");
      logger.error("查询【推送SAP信息】失败", e);
    }
    return json;
  }

}
