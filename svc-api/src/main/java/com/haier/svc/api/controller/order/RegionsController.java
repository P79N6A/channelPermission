package com.haier.svc.api.controller.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.afterSale.model.Json;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.Regions;
import com.haier.shop.service.ShopRegionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping(value = "order/regions/")
public class RegionsController {

    @Autowired
    private ShopRegionsService ShopRegionsService;


    @RequestMapping(value = {"regions"})
    public String addOrderList(HttpServletRequest request, HttpServletResponse response){
        return "order/regions";
    }

    @RequestMapping(value = "/ListF", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findOrderProductList(int page, int rows, Regions condition){
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        return ShopRegionsService.Listf(pager, condition);
    }

    /**
     * 查询 Region表中的省市区街
     * @param pid
     * @return
     */
    @ResponseBody
    @RequestMapping("getRegion")
    public JSONArray getRegion(int pid){
        return ShopRegionsService.getRegion(pid);
    }

    @ResponseBody
    @RequestMapping("addOneRegion")
    public Json addOneRegion(Regions regions){
        ServiceResult<Boolean> result = ShopRegionsService.insert(regions);
        Json json = new Json();
        json.setSuccess(result.getSuccess());
        json.setMsg(result.getMessage());
        return json;
    }


    @ResponseBody
    @RequestMapping("updateOneRegion")
    public Json updateOneRegion(Regions regions){
        ServiceResult<Boolean> result = ShopRegionsService.update(regions);
        Json json = new Json();
        json.setSuccess(result.getSuccess());
        json.setMsg(result.getMessage());
        return json;
    }

    @ResponseBody
    @RequestMapping("deleteOneRegion")
    public Json deleteOneRegion(int id){
        ServiceResult<Boolean> result = ShopRegionsService.delete(id);
        Json json = new Json();
        json.setSuccess(result.getSuccess());
        json.setMsg(result.getMessage());
        return json;
    }

    @ResponseBody
    @RequestMapping("deleteSubordinateRegion")
    public Json deleteSubordinateRegion(int id,int regionType){
        ServiceResult<Boolean> result = ShopRegionsService.deleteSubordinateRegion(id,regionType);
        Json json = new Json();
        json.setSuccess(result.getSuccess());
        json.setMsg(result.getMessage());
        return json;
    }

}
