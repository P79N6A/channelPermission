package com.haier.svc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.T2OrderItem;
import com.haier.svc.bean.OMST2OrderCreateRequire;
import com.haier.svc.bean.OMST2OrderCreateResponse;
import com.haier.svc.model.OMSOrderModel;
import com.haier.svc.model.T2OrderModel;
import com.haier.svc.util.AsynchronousJobRunner.Configuration.IMonitorCallback;

public class T2ThreadJob implements IThreadJob {
    private static final org.apache.log4j.Logger log           = org.apache.log4j.LogManager
        .getLogger(T2ThreadJob.class);

    private BlockingQueue<T2OrderItem>           queue         = new ArrayBlockingQueue<T2OrderItem>(
        100);
    private boolean                              isFinished    = false;

    private boolean                              returnValue   = true;
    private List<T2OrderItem>                    successedItem = new ArrayList<T2OrderItem>();
    private IMonitorCallback                     monitorCallback;

    @Override
    public void setMonitorCallback(IMonitorCallback monitorCallback) {
        this.monitorCallback = monitorCallback;
    }

    @Override
    public void run() {
        T2OrderItem reviewedOrder = null;
        try {
            reviewedOrder = queue.poll(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (reviewedOrder == null) {
            //System.out.println("[T+2异步传输]超时未获取到数据");
            returnValue = false;
            return;
        }
        OMSOrderModel omsOrderModel = (OMSOrderModel) SpringContextUtil.getBean("omsOrderModel");
        T2OrderModel t2OrderModel = (T2OrderModel) SpringContextUtil.getBean("t2OrderModel");
        while (!isFinished || reviewedOrder != null) {
            try {
                List<T2OrderItem> t_l = new ArrayList<T2OrderItem>();

                Map params = new HashMap();

                //备料准确率闸口判定
                //Boolean stockPrecisionCheck = checkStockPrecision(params, reviewedOrder);
                //if (!stockPrecisionCheck) {
                //    continue;
                //}
                List<String> reviewList = new ArrayList<String>();
                reviewList.add(reviewedOrder.getOrder_id());
                params.put("reviewList", reviewList);
                // 审核订单
                //reviewResult = t2OrderModel.reviewT2OrderList(params);

                reviewedOrder.setFlow_flag(15);
                OMST2OrderCreateRequire order = new OMST2OrderCreateRequire();
                // 订单号
                order.setBillCode(reviewedOrder.getOrder_id());
                // 订单类型  固定值J001
                order.setBillType("J001");
                // 提货方式  固定值6（基地直发）
                order.setPickType("6");
                order.setPlanInDate("2014-08-28 14:40:29");
                // 日日顺库位码
                order.setWhCode(reviewedOrder.getArrival_storage_id());
                // 库位码
                order.setEStorgeId(reviewedOrder.getStorage_id());
                // 管理客户
                order.setMGCustCode(reviewedOrder.getCustom_id());
                // LockDay
                order.setLockDay("0");
                // 物料编码
                order.setInvCode(reviewedOrder.getMaterials_id());
                // 产品组ID
                order.setInvSort(reviewedOrder.getProduct_group_id());
                // T+2订单数量
                order.setQty(String.valueOf(reviewedOrder.getT2_delivery_prediction()));
                //参考单号
                order.setAdd4(org.apache.commons.lang.StringUtils.defaultIfEmpty(reviewedOrder.getOrder_num_73(), ""));
                //更改发货方向标记
                order.setAdd5(org.apache.commons.lang.StringUtils.defaultIfEmpty(reviewedOrder.getSend_flag(), ""));
                //渠道
                order.setAdd6(reviewedOrder.getEd_channel_id());
                // Flag 固定值2
                //海华要求如果渠道是商城和顺逛 flag赋值11 custCode设置为C200130028
                if (StringUtils.isNotEmpty(reviewedOrder.getEd_channel_id())
                    && (reviewedOrder.getEd_channel_id().equalsIgnoreCase("SC") || reviewedOrder.getEd_channel_id().equalsIgnoreCase("RS"))) {
                    order.setFlag("11");
                    order.setCustCode("C200130028");
                } else {
                    order.setFlag("10");
                }
                // 付款方
                order.setCustCode(reviewedOrder.getPayment_id());
                //审批人是系统自动则标记为系统自动执行
                order.setAdd18("系统自动".equals(reviewedOrder.getAudit_user()) ? "Y" : "N");

                OMST2OrderCreateResponse response = null;

                try {
                    //拆单导致数量有可能为零  zzb  2017-05-04
                    //数量是零的不传接口
                    if(Integer.parseInt(order.getQty())>0){
                        response = omsOrderModel.CreateT2Order(order);
                        if ("Y".equalsIgnoreCase(response.getFlag())) {
                            reviewedOrder.setFlow_flag(15);
                            reviewedOrder.setSync_status(20);//传输成功
                            reviewedOrder.setOms_order_id(response.getVbeln());
                            reviewedOrder.setError_msg(response.getReturnMsg());
                        } else {
                            reviewedOrder.setFlow_flag(5);
                            reviewedOrder.setSync_status(-20);//待重传
                            reviewedOrder.setError_msg(response.getReturnMsg());
                        }
                    }else{
                        reviewedOrder.setFlow_flag(80);
                        reviewedOrder.setSync_status(20);
                        reviewedOrder.setError_msg("自动拆单，走CRM订单流程，系统默认此单关闭！");
                    }

                    if (1 == reviewedOrder.getOrder_category()) {
                        reviewedOrder
                            .setOrder_id(reviewedOrder.getOrder_id().replace("KWN", "WP10"));
                    }
                    successedItem.add(reviewedOrder);
                    log.info("oms数据传输成功");
                    t2OrderModel.updateByOms(reviewedOrder);
                } catch (Exception e) {
                    if (reviewedOrder != null) {
                        reviewedOrder.setSync_status(-10);//传输失败
                        reviewedOrder.setFlow_flag(5);
                        reviewedOrder.setError_msg(e.getMessage());
                        if (1 == reviewedOrder.getOrder_category()) {
                            reviewedOrder
                                .setOrder_id(reviewedOrder.getOrder_id().replace("KWN", "WP10"));
                        }
                        log.error("oms数据传输失败:" + JsonUtil.toJson(reviewedOrder), e);
                        successedItem.add(reviewedOrder);
                        t2OrderModel.updateByOms(reviewedOrder);
                    } else {
                        log.error(e);
                    }
                }

                reviewedOrder = queue.poll(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
        finishedData();
    }

    @Override
    public void addData(List data) {
        for (int i = 0; i < data.size(); i++) {
            try {
                queue.put((T2OrderItem) data.get(i));
            } catch (InterruptedException e) {
                log.error(e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addData(final Object data) {

        try {
            queue.put((T2OrderItem) data);
        } catch (InterruptedException e) {
            log.error(e);
            e.printStackTrace();
        }

    }

    @Override
    public void dataFinish() {
        isFinished = true;
    }

    @Override
    public void finishedData() {
        Map returnMap = new LinkedHashMap();
        returnMap.put("returnValue", returnValue);
        returnMap.put("successedItem", successedItem);
        if (monitorCallback != null)
            monitorCallback.finishJob(returnMap);
    }

}
