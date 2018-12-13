package com.haier.svc.api.controller.workorder;

import com.haier.traderate.service.WoJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.svc.api.controller.workorder
 * @Date: Created in 2018/4/20 14:30
 * @Modified By:
 */
@Controller
@RequestMapping("/workOrder")
public class WorkOrderController {

    @Autowired
    WoJobsService woJobsService;

    @RequestMapping(value = {"/test"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String dutyStatistic(){

        try {
            woJobsService.sendPjToHp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }



}
