package com.haier.order.util;

import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.OmsOrderVO;
import com.haier.purchase.data.service.PurchaseOmsSyncService;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 向T2订单表中更新获取的OMS订单状态(从消息队列中获取数据)
 * <p>
 * <i>每个线程负责一部分订单的状态更新，提高更新的速度</i>
 * </p>
 * 
 * @Version: 1.0
 * @Author: zhanghan 张晗
 * @Email:
 * 
 */
public class T2DataQueueWritter extends Thread {
    private static org.apache.log4j.Logger log                  = org.apache.log4j.LogManager
                                                                    .getLogger(T2DataQueueWritter.class);
    private static final int               QUEUE_LENGTH         = 100;
    private static final int               RETRY_INTERVAL       = 100;                                   // 重试时间间隔

    private BlockingQueue<OmsOrderVO>      q                    = null;
    private PurchaseOmsSyncService purchaseOmsSyncService           = null;
    private CountDownLatch                 countDownLatch       = null;
    private int                            isCompareData        = 0;                                     // 比较变化的方式(此选项在数据变动量较小的情况下使用),0:不使用 1:MD5
                                                                                                          // 2:逐字段比较
    private boolean                        isFinished           = false;
    private boolean                        isPrepareFinish      = false;                                 // 当线程队列取数超过最大次数时允许结束线程

    // 性能统计参数
    private long                           dbWriteTotalTime     = 0;                                     // 数据库更新时间(总时间)
    private long                           queueWaitTotalTime   = 0;                                     // 队列数据等待时间(总时间)
    private long                           dataCompareTotalTime = 0;                                     // 数据比较时间(总时间)
    private int                            passOrderTotal       = 0;                                     // 未修改的订单数

    public T2DataQueueWritter(PurchaseOmsSyncService purchaseOmsSyncService, CountDownLatch countDownLatch,
                              int isCompareData) {
        this.q = new ArrayBlockingQueue<OmsOrderVO>(QUEUE_LENGTH);
        this.purchaseOmsSyncService = purchaseOmsSyncService;
        this.countDownLatch = countDownLatch;
        this.isCompareData = isCompareData;
    }

    private String getStatInfo() {
        return "dbWriteTotalTime:" + dbWriteTotalTime + "\tqueueWaitTotalTime:"
               + queueWaitTotalTime + "\tdataCompareTotalTime:" + dataCompareTotalTime
               + "\tpassOrderTotal:" + passOrderTotal;
    }

    public boolean addTaskItem(OmsOrderVO vo) throws InterruptedException {
        //System.out.println("Add Thread id=" + this.getId() + " orderId=" + vo.getOrderSoCode());
        return q.offer(vo);
    }

    public boolean addTaskItem(OmsOrderVO vo, long timeout, TimeUnit unit)
                                                                          throws InterruptedException {
        return q.offer(vo, timeout, unit);
    }

    public void prepareFinished() {
        this.isPrepareFinish = true;
    }

    public void finished() {
        this.isFinished = true;
    }

    /**
     * 将OMS订单状态对应到CBS中
     * 
     * @param code
     * @return
     */
    private int transStatus(String code) {
        // -50：OMS已取消
        // -40：直发库存作废
        // -30：已撤销
        // -20：OMS拒单
        // -10：作废
        // 0：未提交
        // 5:待内部审核
        // 10：内部审核通过
        // 15：OMS已审核
        // 20：待OMS评审
        // 25: OMS冻结
        // 30：等待发货
        // 35: 事业部已扫描，未出园
        // 40：事业部已发货
        // 50：等待入日日顺库
        // 60：已入日日顺库
        // 70：已开入WA提单
        // 80：已入WA库
        try {
            if (code.equalsIgnoreCase("创建中")) {
                return 15;
            } else if (code.equalsIgnoreCase("草稿")) {
                return 15;
            } else if (code.equalsIgnoreCase("待审核")) {
                return 15;
            } else if (code.equalsIgnoreCase("已审核")) {
                return 15;
            } else if (code.equalsIgnoreCase("已冻结")) {
                return 25;
            } else if (code.equalsIgnoreCase("待评审")) {
                return 20;
            } else if (code.equalsIgnoreCase("待确认")) {
                return 20;
            } else if (code.equalsIgnoreCase("等待发货")) {
                return 30;
            } else if (code.equalsIgnoreCase("事业部已扫描，未出园")) {
                return 35;
            } else if (code.equalsIgnoreCase("工厂已装车")) {
                return 35;
            } else if (code.equalsIgnoreCase("车辆已离园")) {
                return 40;
            } else if (code.equalsIgnoreCase("事业部已发货")) {
                return 40;
            } else if (code.equalsIgnoreCase("等待工贸发货")) {
                return 50;
            } else if (code.equalsIgnoreCase("工贸已发货")) {
                return 50;
            } else if (code.equalsIgnoreCase("到达客户")) {
                return 50;
            } else if (code.equalsIgnoreCase("已签收")) {
                return 50;
            } else if (code.equalsIgnoreCase("已返单")) {
                return 60;
            } else if (code.equalsIgnoreCase("已开发票")) {
                return 60;
            } else if (code.equalsIgnoreCase("发票已打印")) {
                return 60;
            } else if (code.equalsIgnoreCase("票据已签收")) {
                return 60;
            } else if (code.equalsIgnoreCase("已撤销待确认")) {
                return Integer.MIN_VALUE;
            } else if (code.equalsIgnoreCase("已关闭")) {
                return Integer.MIN_VALUE;
            } else if (code.equalsIgnoreCase("已取消")) {
                return -50;
            } else if (code.equalsIgnoreCase("订单未满足")) {
                return -20;
            } else if (code.equalsIgnoreCase("已撤消")) {
                return -30;
            } else if (code.equalsIgnoreCase("工贸库存满足")) {
                return -20;
            } else if (code.equalsIgnoreCase("直发库存作废")) {
                return -40;
            } else if (code.equalsIgnoreCase("已提交")) {
                return Integer.MIN_VALUE;
            } else if (code.equalsIgnoreCase("24小时承诺")) {
                return Integer.MIN_VALUE;
            } else if (code.equalsIgnoreCase("基地整车直发到客户在途")) {
                return Integer.MIN_VALUE;
            } else if (code.equalsIgnoreCase("产品总监待审核")) {
                return Integer.MIN_VALUE;
            } else if (code.equalsIgnoreCase("供应链待审核")) {
                return Integer.MIN_VALUE;
            }
        } catch (Exception ex) {
            log.error("订单状态:" + code);
            log.error(ex);
        }
        return Integer.MIN_VALUE;
    }

    @Override
    public void run() {
        OmsOrderVO vo = null;
        try {
            log.debug("T2DataQueueWritter Thread Id = " + this.getId() + " start.");
            long beginTime = System.currentTimeMillis();
            OmsOrderVO.QueryCondition query = new OmsOrderVO.QueryCondition();
            boolean isQueueEmpty = false;
            // 从队列中取得数据
            while (!isFinished || !isQueueEmpty) {
                try {
                    queueWaitTotalTime -= System.currentTimeMillis();
                    vo = q.poll(RETRY_INTERVAL, TimeUnit.MILLISECONDS);
                    queueWaitTotalTime += System.currentTimeMillis();
                    if (vo == null) {
                        isQueueEmpty = true;
                        continue;
                    } else {
                        isQueueEmpty = false;
                    }
                    int status = transStatus(vo.getOrderState());
                    vo.setFlow_flag(status);
                    if (status != Integer.MIN_VALUE) {
                        vo.setFlow_flag(status);
                    } else {
                        vo.setFlow_flag(null);
                        log.warn("无法对应的状态:" + vo.getOrderState() + ",订单号:" + vo.getOrderSoCode());
                    }
                    log.debug("订单号:" + vo.getOrderSoCode() + "订单状态:" + vo.getFlow_flag());
                    //System.out.println("订单号:" + vo.getOrderSoCode() + "订单状态:" + vo.getFlow_flag());

                    boolean isUpdate = true;
                    dataCompareTotalTime -= System.currentTimeMillis();
                    if (isCompareData == 1) {
                        query.setMd5(vo.getMd5());
                        query.setOrderSoCode(vo.getOrderSoCode());
                        List<String> l = purchaseOmsSyncService.getOrderIds(query);
                        if (l.size() > 0) {
                            isUpdate = false;
                        }
                    } else if (isCompareData == 2) {
                        query.setOrderSoCode(vo.getOrderSoCode());
                        List<OmsOrderVO> l = purchaseOmsSyncService.getOmsOrder(query);
                        if (l.size() > 0) {
                            if (vo.equals(l.get(0))) {
                                isUpdate = false;
                            }
                        }
                    }
                    dataCompareTotalTime += System.currentTimeMillis();

                    if (vo.getFlow_flag() == -30) {
                        log.info("已找到状态为-30的订单：" + JsonUtil.toJson(vo));
                    }
                    if (isUpdate) {
                        // purchaseOmsSyncService.insertUpdateTable(vo);

                        dbWriteTotalTime -= System.currentTimeMillis();

                        purchaseOmsSyncService.updateOrderStatus(vo);
                        purchaseOmsSyncService.updateOrderFlowFlag(vo);
                        dbWriteTotalTime += System.currentTimeMillis();
                    } else {
                        passOrderTotal++;
                        log.debug("Not modified Data,ID:" + vo.getOrderSoCode());
                    }
                } catch (Exception ex) {
                    if (vo != null)
                        log.error("向OMS传输订单发生错误,OMS单号:" + vo.getOrderSoCode(), ex);
                    else {
                        log.error("向OMS传输订单发生错误", ex);
                    }
                }
            }

            long endTime = System.currentTimeMillis();
            log.debug("T2DataWritter Thread Id = " + this.getId() + " end:" + (endTime - beginTime)
                      + "\r\nStat:" + getStatInfo());

            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        } catch (Exception ex) {
            log.error("", ex);
            ex.printStackTrace();
            if (vo != null)
                log.error("Error Code id = " + vo.getOrderSoCode());
        }
    }
}