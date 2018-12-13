package com.haier.svc.api.controller.youpin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.shop.model.TmypProductDiscount;
import com.haier.shop.service.TmypProductDiscountService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优品折扣率维护
 * @Author:JinXueqian
 * @Date: 2018/8/21 16:42
 */
@Controller
@RequestMapping("youpin")
public class TmypProductDiscountController {

    private static final Logger logger = LogManager.getLogger(TmypProductDiscountController.class);


    @Autowired
    private TmypProductDiscountService tmypProductDiscountService;
    /**
     * 页面跳转
     * @return
     */
    @RequestMapping("/loadTmypProductDiscountPage.html")
    public String loadTmypProductDiscountPage() {
        return "youpin/tmyp_productdiscountList";
    }

    /**
     * 分页查询优品折扣率
     * @param response
     * @param rows
     * @param page
     */
    @PostMapping(value = {"getTmyp_ProductDiscountList"})
    public void getTmyp_ProductDiscountList(HttpServletResponse response,
                                        @RequestParam(required = false) String product_Name,
                                        @RequestParam(required = false) String product_Type,
                                        @RequestParam(required = false) BigDecimal sale_Price,
                                        @RequestParam(required = false) BigDecimal purchase_Price,
                                        @RequestParam(required = false) BigDecimal discount,
                                        @RequestParam(required = false) String sku,
                                        @RequestParam(required = false) String addTimeMin,
                                        @RequestParam(required = false) String addTimeMax,
                                        @RequestParam(required = false) Integer rows,
                                        @RequestParam(required = false) Integer page) {

        try {
            if (rows == null){
                rows = 50;
            }
            if (page == null){
                page = 1;
            }
            Map<String, Object> params = new HashMap();
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            params.put("product_Name", product_Name);
            params.put("product_Type", product_Type);
            params.put("sale_Price", sale_Price);
            params.put("purchase_Price", purchase_Price);
            params.put("discount", discount);
            params.put("sku", sku);
            params.put("addTimeMin", addTimeMin);
            params.put("addTimeMax", addTimeMax);
            Integer resultCount = tmypProductDiscountService.findTmyp_ProductDiscountCNT(params);
            List<TmypProductDiscount> externalOrdersList = tmypProductDiscountService.getTmyp_ProductDiscountList(params);

            Map<String, Object> retMap = new HashMap<>();
            retMap.put("total", resultCount);
            retMap.put("rows", externalOrdersList);
            response.setContentType("application/json;charset=utf-8");
            String json = JSON.toJSONString(retMap);
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("查询优品折扣率失败", e);
        }
    }
    /**
     * 添加优品折扣率页面跳转
     * @return
     */
    @RequestMapping(value = {"tmypProductDiscountAdd.html"},method = {RequestMethod.GET})
    public String storagesAdd() {
        return "youpin/tmyp_productdiscountAdd";
    }

    /**
     * 保存优品折扣率
     * @param request
     * @return
     */
    @RequestMapping(value = {"saveTmypProductDiscountInfo"},method = {RequestMethod.POST})
    @ResponseBody
    public String saveTmypProductDiscountInfo(HttpServletRequest request, HttpServletResponse response, TmypProductDiscount tmypProductDiscount){
        JSONObject json = new JSONObject();
        try {
            //设置默认折扣率为1.00
            if(tmypProductDiscount.getDiscount() == null){
                BigDecimal dicount = new BigDecimal(1.00);
                tmypProductDiscount.setDiscount(dicount);
            }
            //设置添加时间
            tmypProductDiscount.setAddTime((int) (System.currentTimeMillis() / 1000));
            //设置修改时间
            tmypProductDiscount.setModifyTime((int) (System.currentTimeMillis() / 1000));
            int  result = tmypProductDiscountService.insert(tmypProductDiscount);
            if (result > 0 ){
                json.put("flag",1);
                return json.toString();
            }else {
                json.put("flag",2);
                return json.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("添加失败",e);
        }
        json.put("flag",3);
        return json.toString();

    }

    /**
     * 修改优品折扣率
     * @param tmypProductDiscount
     * @param response
     * @return
     */
    @PostMapping(value = { "/updateTmypProductDiscountInfo" })
    @ResponseBody
    public String updateTmypProductDiscountInfo(TmypProductDiscount tmypProductDiscount,
                                         HttpServletResponse response){
        //设置修改时间
        tmypProductDiscount.setModifyTime((int) (System.currentTimeMillis()/1000));
        JSONObject json = new JSONObject();
        try {
            int result = tmypProductDiscountService.updateTmypProductDiscountInfo(tmypProductDiscount);
            if (result > 0 ){
                //成功
                json.put("flag",1);
                return json.toString();
            }else {
                //失败
                json.put("flag",2);
                return json.toString();
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error("修改失败",e);
        }
        //失败,发生异常
        json.put("flag",3);
        return json.toString();
    }
}
