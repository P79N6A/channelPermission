package com.haier.svc.api.controller.workorder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.svc.api.controller.workorder
 * @Date: Created in 2018/4/20 14:30
 * @Modified By:
 */
@Controller
@RequestMapping("/orderSourceAdmin")
public class WorkOrderSourceAdminController {

  @GetMapping("/orderSourceAdmin")
  public String orderList(){
    return "workorder/orderSourceAdmin";
  }




}
