package com.haier.svc.api.controller.settleCenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.ServiceResult;
import com.haier.invoice.service.SNOdsTMFXPromotionCostService;
import com.haier.shop.model.SNOdsTMFXPromotionCost;
import com.haier.svc.api.controller.excel.ExcelReadHandler;
import com.haier.svc.api.controller.util.excel.ExcelHandler;

/**
 * @Author: wsh
 * @Description:苏宁其他费用维护
 * @ProjectName: svc
 * @PackageName: com.haier.svc.api.controller.settleCenter
 * @Date: Created in 2018/5/22 16:58
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/SNOtherCost")
public class SNOdsTMFXPromotionCostController {
	
  @Resource
  private SNOdsTMFXPromotionCostService sNodsTMFXPromotionCostService;


  @GetMapping("toPage")
  public String toPage() {
    return "settleCenter/SNotherCost";
  }

  /**
   * 分页查询
   */
  @GetMapping("queryPage")
  @ResponseBody
  public Object queryPage(SNOdsTMFXPromotionCost param) {
    return sNodsTMFXPromotionCostService.queryPromptionCost(param);
  }

  /**
   * 新增或修改
   */
  @PostMapping("editRecord")
  @ResponseBody
  public Object editRecord(SNOdsTMFXPromotionCost param) {
    if (param.getId()==0) {
      param.setCreateBy(this.getUserName());
    }
    return sNodsTMFXPromotionCostService.editPromotionCost(param);
  }


  /**
   * 财务审核或业务审核
   */
  @PostMapping("audit")
  @ResponseBody
  public Object audit(String ids, String audit) {
    return sNodsTMFXPromotionCostService.auditPromotionCost(ids, audit, this.getUserName());
  }

  /**
   * 财务审核或业务审核
   */
  @PostMapping("calculate")
  @ResponseBody
  public Object calculate(String ids, String audit) {
    return sNodsTMFXPromotionCostService.auditPromotionCost(ids, audit, this.getUserName());
  }

  /**
   * 导出
   */
  @GetMapping("export")
  @ResponseBody
  public void export(SNOdsTMFXPromotionCost param, HttpServletResponse response) {
    List<SNOdsTMFXPromotionCost> data = sNodsTMFXPromotionCostService.queryPromptionCostExcel(param);
    ExcelHandler eh = new ExcelHandler(SNOdsTMFXPromotionCost.class);
    eh.setData(data);
    eh.exportxls(response);
  }

  /**
   * 删除
   */
  @PostMapping("delete")
  @ResponseBody
  public Object delete(String ids) {
    return sNodsTMFXPromotionCostService.deletePromotionCost(ids);
  }

  /**
   * 导入
   */
  @PostMapping("import")
  @ResponseBody
  public Object importExcel(@RequestParam MultipartFile file) {
    ServiceResult<String> result = new ServiceResult<>();
    ExcelReadHandler reader = new ExcelReadHandler();
    List<SNOdsTMFXPromotionCost> data = new ArrayList<>();
    ExcelHandler eh = new ExcelHandler(SNOdsTMFXPromotionCost.class);
    try {
      eh.convertToEntity(reader.readExcel(file), data);
    } catch (IOException e) {
      e.printStackTrace();
      result.setSuccess(false);
    }
    String userName = getUserName();
    for (SNOdsTMFXPromotionCost o : data) {
      o.setCreateBy(userName);
    }
    return sNodsTMFXPromotionCostService.execExcel(data);
  }


  /**
   * 获取当前登录的用户
   */
  private String getUserName() {
    ServletRequestAttributes attr = (ServletRequestAttributes)
        RequestContextHolder.currentRequestAttributes();
    return (String) attr.getRequest().getSession().getAttribute("userName");
  }
}
