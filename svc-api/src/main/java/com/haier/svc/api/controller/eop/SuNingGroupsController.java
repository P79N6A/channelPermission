package com.haier.svc.api.controller.eop;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.order.service.OrderCenterSuNingGroupsService;
import com.haier.shop.model.SuningGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value ="eop/SuNingGroups")
public class SuNingGroupsController {
    @Autowired
    private OrderCenterSuNingGroupsService orderCenterSuNingGroupsService;

    @RequestMapping("/SuNingGroupsList")
    public String showCommissionList() {

        return "eop/monitoring/SuNingGroupsList";
    }

    @RequestMapping(value = "/commission_productListF", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findOrderProductList(int page, int rows,SuningGroups condition){
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        return  orderCenterSuNingGroupsService.Listf(pager, condition);
    }
    @RequestMapping(value="/addcommission_product", method = RequestMethod.POST)
    @ResponseBody
    public String addCommission(SuningGroups commission ) {


        return orderCenterSuNingGroupsService.insert(commission);
    }

    //修改
    @RequestMapping(value="/updatecommission_product", method = RequestMethod.POST)
    @ResponseBody
    public String updateCommission(SuningGroups commission) {

        return orderCenterSuNingGroupsService.updateByPrimaryKey(commission);
    }
    //删除
    @RequestMapping(value="/deletecommission_product", method = RequestMethod.POST)
    @ResponseBody
    public int deleteCommission(int id) {

        return orderCenterSuNingGroupsService.deleteByPrimaryKey(id);
    }
   //校验
    @RequestMapping(value="/jiaoyan", method = RequestMethod.POST)
    @ResponseBody
    public int jiaoyan(String sku) {
        return orderCenterSuNingGroupsService.jiaoyan(sku);
    }
}
