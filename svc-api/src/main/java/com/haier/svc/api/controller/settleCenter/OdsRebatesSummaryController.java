package com.haier.svc.api.controller.settleCenter;

import com.haier.invoice.service.OdsTMFXRebatesSummaryService;
import com.haier.shop.model.OdsTMFXIndustrySummary;
import com.haier.shop.model.OdsTMFXRebatesSummary;
import com.haier.shop.model.OdsTMFXShopSummary;
import com.haier.svc.api.controller.util.excel.ExcelHandler;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.svc.api.controller.settleCenter
 * @Date: Created in 2018/5/29 15:45
 * @Modified By:
 */
@Controller
@RequestMapping("/scRebatesSummary")
public class OdsRebatesSummaryController {

  @Resource
  private OdsTMFXRebatesSummaryService odsTMFXRebatesSummaryService;

  @GetMapping("toShopSummary")
  public String toShopSummary() {
    return "settleCenter/shopSummary";
  }

  @GetMapping("toRebatesSummary")
  public String toRebatesSummary() {
    return "settleCenter/rebatesSummary";
  }

  @GetMapping("toIndustrySummary")
  public String toIndustrySummary() {
    return "settleCenter/industrySummary";
  }

  /**
   * 分页查询
   */
  @GetMapping("shopSummaryPage")
  @ResponseBody
  public Object shopSummaryPage(OdsTMFXShopSummary param) {
    return odsTMFXRebatesSummaryService.queryShopSummary(param);
  }

  /**
   * 导出
   */
  @GetMapping("shopSummaryExport")
  @ResponseBody
  public void shopSummaryExport(OdsTMFXShopSummary param, HttpServletResponse response) {
    List<OdsTMFXShopSummary> data = odsTMFXRebatesSummaryService.queryShopSummaryExcel(param);
    ExcelHandler eh = new ExcelHandler(OdsTMFXShopSummary.class);
    eh.setData(data);
    eh.exportxls(response);
  }

  /**
   * 分页查询
   */
  @GetMapping("rebatesSummaryPage")
  @ResponseBody
  public Object rebatesSummaryPage(OdsTMFXRebatesSummary param) {
    return odsTMFXRebatesSummaryService.queryRebatesSummary(param);
  }

  /**
   * 导出
   */
  @GetMapping("rebatesSummaryExport")
  @ResponseBody
  public void rebatesSummaryExport(OdsTMFXRebatesSummary param, HttpServletResponse response) {
    List<OdsTMFXRebatesSummary> data = odsTMFXRebatesSummaryService.queryRebatesSummaryExcel(param);
    ExcelHandler eh = new ExcelHandler(OdsTMFXRebatesSummary.class);
    eh.setData(data);
    eh.exportxls(response);
  }

  /**
   * 分页查询
   */
  @GetMapping("industrySummaryPage")
  @ResponseBody
  public Object industrySummaryPage(OdsTMFXIndustrySummary param) {
    return odsTMFXRebatesSummaryService.queryIndustrySummary(param);
  }

  /**
   * 导出
   */
  @GetMapping("industrySummaryExport")
  @ResponseBody
  public void industrySummaryExport(OdsTMFXIndustrySummary param, HttpServletResponse response) {
    List<OdsTMFXIndustrySummary> data = odsTMFXRebatesSummaryService
        .queryIndustrySummaryExcel(param);
    ExcelHandler eh = new ExcelHandler(OdsTMFXIndustrySummary.class);
    eh.setData(data);
    eh.exportxls(response);
  }

}
