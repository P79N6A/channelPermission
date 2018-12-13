package com.haier.svc.api.controller.stock;

import com.alibaba.fastjson.JSONObject;
import com.haier.afterSale.model.Ustring;
import com.haier.common.PagerInfo;
import com.haier.invoice.util.HttpJsonResult;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.service.OrderProductsNewService;
import com.haier.shop.service.OrdersNewService;
import com.haier.shop.service.ShopOrderOperateLogsService;
import com.haier.stock.model.InvSection;
import com.haier.stock.service.StockCenterOrderPubService;

import java.util.*;

import com.haier.stock.service.StockInvSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("occupyStockFailController")
public class OccupyStockFailController {

    @Autowired
    private OrderProductsNewService orderProductsNewService;
    @Autowired
    private StockCenterOrderPubService stockCenterOrderPubService;
    @Autowired
    private ShopOrderOperateLogsService shopOrderOperateLogsService;

    @Autowired
    private StockInvSectionService stockInvSectionService;
    @Autowired
    private OrdersNewService ordersNewService;

    private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager
            .getLogger(OccupyStockFailController.class);

    @RequestMapping(value = { "/loadStockFail" }, method = { RequestMethod.GET })
    public String loadStockFail() {
        return "stock/occupyStockFailList";
    }

    @RequestMapping(value = { "/getOccupyStockFail" })
    @ResponseBody
    public JSONObject getOccupyStockFail(@RequestParam(required = false) String orderSn,
                                         @RequestParam(required = false) String corderSn,
                                         @RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer rows) {
        PagerInfo pagerInfo = new PagerInfo(rows, page);
        JSONObject json = new JSONObject();
        try {
            if (pagerInfo.getPageIndex() > 0) {
                page = (page - 1) * rows;
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderSn", orderSn);
            params.put("corderSn", corderSn);
            params.put("start", page);
            params.put("size", rows);

            int count = orderProductsNewService.queryOccupyStockFailCount(params);
            List<OrderProductsNew> list = orderProductsNewService.queryOccupyStockFail(params);

            json.put("rows", list);
            json.put("total", Long.valueOf(count));
        } catch (Exception e) {
            logger.error("查询【占用库存失败的数据】失败", e);
        }
        return json;
    }

    @RequestMapping(value = { "/againOccupyStock" })
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> againOccupyStock(@RequestParam(required = false) String orderSn,
                                                                @RequestParam(required = false) String corderSn,
                                                                @RequestParam(required = false) Integer id,
                                                                HttpServletRequest request){
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orderSn", orderSn);
            params.put("corderSn", corderSn);
            //重新占用库存
            stockCenterOrderPubService.occupyStockAgain(orderProductsNewService.queryOccupyStockFail(params));
            //查询日志表 是否占用成功
            OrderOperateLogs orderOperateLogs = shopOrderOperateLogsService.getLogsNew(id);
            result.setMessage(orderOperateLogs.getRemark());
            //更新操作人为当前登陆人
            //获取当前登陆人
            String user = (String) request.getSession().getAttribute("userName");
            shopOrderOperateLogsService.updateOperator(orderOperateLogs.getId(), user);
        } catch (Exception e) {
            logger.error("占用库存失败", e);
            result.setMessage("占用库存失败");
        }
        return result;
    }

    /**
     * 网单详情 先修改库位,然后调用重新占用库存
     * @param request
     * @param sCode
     * @param newsCode
     * @param cOrderSn
     * @return
     */
    @RequestMapping(value = { "/updatesCode" })
    @ResponseBody
    public String up(HttpServletRequest request,String newsCode, String cOrderSn){
        HttpSession session = request.getSession();
        String userName = Ustring.getString(session.getAttribute("userName"));
        //查找新库位是否存在
        InvSection invSection = stockInvSectionService.selectByPrimaryKey(newsCode);
        if (invSection != null){
            OrderProductsNew byCOrderSn = orderProductsNewService.getByCOrderSn(cOrderSn);
            //判断当前是否可以修改库位 LockedNumber 为0 或 123456789 时 可以修改
            if (byCOrderSn.getLockedNumber() == 0 || byCOrderSn.getLockedNumber() == 123456789 ) {
                int i = orderProductsNewService.updatesCodeBycOrderSn(newsCode, cOrderSn);
                if (i == 1){
                    //查询最近网单信息
                    OrderProductsNew byCOrderSn2 = orderProductsNewService.getByCOrderSn(cOrderSn);
                    //增加库位修改日志
                    OrderOperateLogs orderOperateLogs = new OrderOperateLogs();
                    orderOperateLogs.setSiteId(1);
                    orderOperateLogs.setOrderId(byCOrderSn.getOrderId());
                    orderOperateLogs.setOrderProductId(byCOrderSn.getId());
                    orderOperateLogs.setNetPointId(byCOrderSn.getNetPointId());
                    orderOperateLogs.setOperator(userName);
                    orderOperateLogs.setChangeLog("库位由:" + byCOrderSn.getSCode() + ",更改为:" + newsCode);
                    orderOperateLogs.setRemark("库位由:" + byCOrderSn.getSCode() + ",更改为:" + newsCode);
                    orderOperateLogs.setLogTime((int) (new Date().getTime() / 1000));
                    orderOperateLogs.setStatus(byCOrderSn.getStatus());
                    orderOperateLogs.setPaymentStatus(byCOrderSn.getCPaymentStatus());
                    shopOrderOperateLogsService.insert(orderOperateLogs);
                    List<OrderProductsNew> opn = new ArrayList<OrderProductsNew>();
                    opn.add(byCOrderSn2);
                    //重新占用库存
                    stockCenterOrderPubService.occupyStockAgainBysCode(opn);
                    return "修改库位成功";
                }else {
                    return "修改库位失败";
                }

            }else {
                return "当前不能修改库位";
            }
        }else {
            return "输入的库位不存在";
        }

    }


}
