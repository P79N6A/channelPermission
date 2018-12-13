package com.haier.svc.api.controller.eop;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.eop.data.model.OrdersQueue;
import com.haier.eop.service.EopCenterAgainSyncService;
import com.haier.svc.api.controller.util.StringUtil;
import com.haier.svc.api.controller.util.http.AbstractHaierHttpClient;
import com.haier.svc.api.controller.util.http.dangdang.DangdangClient;
import com.haier.svc.api.controller.util.http.gome.*;
import com.haier.svc.api.controller.util.http.jingdong.JingdongBxClient;
import com.haier.svc.api.controller.util.http.jingdong.JingdongClient;
import com.haier.svc.api.controller.util.http.suning.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("eop/againSync")
public class AgainSyncController {

    @Autowired
    private DangdangClient dangdangClient;
    @Autowired
    private JingdongBxClient jingdongBxClient;
    @Autowired
    private JingdongClient jingdongClient;
    // 国美 一共6个店
    @Autowired
    private GomeClient gomeClient;
    @Autowired
    private GomeTSClient gomeTSClient;
    // 苏宁一共有5个店
    @Autowired
    private SuningClient suningClient;
    @Autowired
    private SuningGqClient suningGqClient;
    @Autowired
    private SuningDepositClient suningDepositClient;
    @Autowired
    private SuningGQDepositClient suningGQDepositClient;
    @Autowired
    private SuningQDZXClient suningQDZXClient;

    @Autowired
    private EopCenterAgainSyncService eopCenterAgainSyncService;

    @RequestMapping("/againSyncList")
    public String showAgainSyncList() {

        return "eop/monitoring/againSyncOrdersById";
    }

    @RequestMapping("/dontSyncAgainList")
    public String dontSyncAgain() {
        return "order/dontSyncAgain";
    }

    @RequestMapping("/pointIdSyncOrders")
    @ResponseBody
    public String pointIdSyncOrders(String orderId, String reqType) {
        if (StringUtils.isBlank(orderId)) {
            return "订单ID不能为空";
        }

        orderId = orderId.trim();
        if (reqType.equals("DDW")) {
            return excute(orderId, dangdangClient, reqType);
        }else if(reqType.equals("JDHEGQ")){
            return excute(orderId, jingdongClient, reqType);
        }else if(reqType.equals("JDHEBXGQ")) {
            return excute(orderId, jingdongBxClient, reqType);
        }else if (reqType.equals("SNYG")) {
            return excute(orderId, suningClient, reqType);
        }else if(reqType.equals("SNHEGQ")){
            return excute(orderId, suningGqClient, reqType);
        }else if(reqType.equals("SNQDZX")) {
            return excute(orderId, suningQDZXClient, reqType);
        }else if (reqType.equals("SNYGDJUNP")) {
            return excute(orderId, suningDepositClient, reqType);
        } else if (reqType.equals("SNYGDJ")) {
            return excute(orderId, suningDepositClient, reqType);
        }else if(reqType.equals("SNHEGQDJUNP")){
            return excute(orderId, suningGQDepositClient, reqType);
        }else if(reqType.equals("SNHEGQDJ")){
            return excute(orderId, suningGQDepositClient, reqType);
        }else if (reqType.equals("GMZX")) {
            return excute(orderId, gomeClient, reqType);
        }else if (reqType.equals("GMZXTS")) {
            return excute(orderId, gomeTSClient, reqType);
        }
        return "没有查询到可执行的方法，请检查请求的渠道是否正确";
    }

    public String excute(String orderId, AbstractHaierHttpClient haierHttpClient, String source) {
        String jsonStr = "";
        OrdersQueue queue ;
        try {
            if("YMX".equals(source)){
                List<OrdersQueue> orderList = new ArrayList<OrdersQueue>();
                OrdersQueue q=new OrdersQueue();
                q.setSourceOrderSn(orderId);
                orderList.add(q);
                jsonStr = haierHttpClient.getOrderByOrderIds(orderList);
            } else if("SNYGDJ".equals(source) || "SNHEGQDJ".equals(source)){
                //苏宁订金尾款订单已付尾款,先到对列表中查到数据,如果状态是1006改成1007
                OrdersQueue orderQueue;
                if("SNYGDJ".equals(source)){
                    orderQueue = eopCenterAgainSyncService.selectBysourceOrderSnAndsource(orderId,"SNYG");
                }else{
                    orderQueue = eopCenterAgainSyncService.selectBysourceOrderSnAndsource(orderId,"SNHEGQ");
                }
                if(orderQueue!=null){
                    if("1006".equals(orderQueue.getOrdersState()) || "110".equals(orderQueue.getOrdersState())){
                        orderQueue.setOrdersState("1007");
                        orderQueue.setSyncCount(1);
                        boolean flag = eopCenterAgainSyncService.update(orderQueue);
                        if (!flag) {
                            return "手动同步订单失败";
                        }
                        return "成功";
                    }else{return "同步中";}
                }else{return "没有订单信息";}
            } else{
                jsonStr = haierHttpClient.getOrderByOrderId(orderId);
            }
            if (StringUtils.isEmpty(jsonStr)){
                return "没有获取到数据";
            }
            if ("error".equals(jsonStr)) {
                return "获取数据失败";
            }
            queue = haierHttpClient.conventOrdersQueue(jsonStr);
            if(queue==null){
                return "解析数据异常";
            }
            //判断该订单是否已经存在，如果已经存在根据情况返回错误信息或者正在同步中提示，不存在的同步到ordersQueue表中
            OrdersQueue oldQueue = eopCenterAgainSyncService.selectBysourceOrderSnAndsource(queue.getSourceOrderSn(),queue.getSource());
            if(oldQueue!=null){
                String ordersState = oldQueue.getOrdersState();
                if(!"1002".equals(ordersState)){
                    //如果该订单未同步则先修改同步次数,有失败原因再显示失败原因
                    String errorLog = oldQueue.getErrorLog();
                    oldQueue.setSyncCount(1);
                    oldQueue.setModifyTime(new Date());
                    eopCenterAgainSyncService.update(oldQueue);
                    if(!"".equals(errorLog)){
                        return errorLog;
                    }else{
                        return "同步中";
                    }
                }else{
                    return "成功";
                }
            }

            boolean flag = eopCenterAgainSyncService.insert(queue);
            if (!flag) {
                return "手动同步订单失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "获取数据失败，请检查接口是否异常";
        }
        return "成功";

    }


    /**
     * 已关闭的订单修改状态使不再预警
     * @param orderId
     * @param source
     * @return
     */
    @RequestMapping("/dontSyncAgain")
    @ResponseBody
    public String dontSyncAgain(String orderId, String source){
        if(StringUtil.isEmpty(orderId)){
            return "请输入来源订单号";
        }
        if(StringUtil.isEmpty(source)){
            return "请输入渠道名称";
        }
        //判断该订单是否已经存在，如果不存在则不做操作，存在的验证状态，只有1006和110的可以修改状态
        OrdersQueue oldQueue = eopCenterAgainSyncService.selectBysourceOrderSnAndsource(orderId,source);
        if(oldQueue == null){
            return "该订单不存在";
        }
        String state = oldQueue.getOrdersState();
        try {
            if("1006".equals(state)){
                //1006订单需要释放库存
                String jsonStr = "";
                if("SNYG".equals(source)){
                    jsonStr = suningClient.getOrderByOrderId(orderId);
                    suningClient.doCloseOrders(jsonStr);
                }else{
                    jsonStr = suningGqClient.getOrderByOrderId(orderId);
                    suningGqClient.doCloseOrders(jsonStr);
                }
                oldQueue.setOrdersState("1005");
                oldQueue.setModifyTime(new Date());
                eopCenterAgainSyncService.update(oldQueue);
                return "成功";
            }else if("110".equals(state) || "1001".equals(state)){
                oldQueue.setOrdersState("1005");
                oldQueue.setModifyTime(new Date());
                eopCenterAgainSyncService.update(oldQueue);
                return "成功";
            }else{
                return "该订单状态不能修改";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "操作异常";
        }
    }


}
