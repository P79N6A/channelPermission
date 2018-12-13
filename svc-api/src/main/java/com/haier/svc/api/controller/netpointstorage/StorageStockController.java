package com.haier.svc.api.controller.netpointstorage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @Author:JinXueqian
 * @Date: 2018/7/16 16:01
 */
@Controller
@RequestMapping("stock/")
public class StorageStockController {

    /**
     * 库位列表页面跳转
     *
     * @return
     */
    @RequestMapping(value = {"loadStockListPage.html"}, method = {RequestMethod.GET})
    public String loadNetPointListPage() {
        return "netpointstorage/stockList";
    }

}
