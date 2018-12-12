package com.haier.svc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.OmsOrderVO;
import com.haier.purchase.data.service.PurchaseOmsSyncService;
import com.haier.svc.util.IFetchCallback;
import com.haier.svc.util.OmsDataFetcher;
import com.haier.svc.util.T2DataQueueWritter;

@Service("omsSyncModel")
public class OmsSyncModel implements IFetchCallback  {
    private static org.apache.log4j.Logger log                    = org.apache.log4j.LogManager
                                                                      .getLogger(OmsSyncModel.class);
    @Autowired
    private PurchaseOmsSyncService purchaseOmsSyncService;
    private List<T2DataQueueWritter>       l_writter              = new ArrayList<T2DataQueueWritter>();

    private CountDownLatch                 countDownLatch         = null;
    private CountDownLatch                 sync_countDownLatch    = null;
    private BlockingQueue<OmsOrderVO>      q                      = null;

    private static final int               OMS_MONITOR_START_FLAG = 15;
    private static final int               OMS_MONITOR_END_FLAG   = 40;

    private static final int               MAX_QUEUE_LENGTH       = 1000;
    @Value("${t2OrderResponse.location}")
	private String						   wsdlLocation;


    private void fetch(List<String> orderIds, int threadCount, boolean waitForFinished) {

        q = new ArrayBlockingQueue(MAX_QUEUE_LENGTH);
        ExecutorService eService = Executors.newFixedThreadPool(threadCount);

        sync_countDownLatch = new CountDownLatch(threadCount);
        int perCount = orderIds.size() / threadCount + 1;
        boolean isEnd = false;
        for (int i = 0; i < threadCount; i++) {
            int toIndex = (i + 1) * perCount;
            int fromIndex = i * perCount;
            if (toIndex > orderIds.size()) {
                toIndex = orderIds.size();
            }
            if (fromIndex >= orderIds.size()) {
                isEnd = true;
            }

            if (!isEnd) {
                //System.out.println("fromIndex:" + fromIndex + " toIndex:" + toIndex + " data:"
                //                  + orderIds.get(fromIndex));
                eService.submit(new Thread(new OmsDataFetcher(l_writter, purchaseOmsSyncService, orderIds
                    .subList(fromIndex, toIndex), false, threadCount, sync_countDownLatch, this,wsdlLocation)));
            } else {
                eService.submit(new Thread(new OmsDataFetcher(l_writter, purchaseOmsSyncService, null, false,
                    threadCount, sync_countDownLatch, this,wsdlLocation)));
            }
        }

        eService.shutdown();

        if (waitForFinished) {
            // 等待所有线程运行结束
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error("", e);
            }

        }
    }

    /**
     * 调用OMS接口获得订单状态数据,数据存入消息队列中,供更新线程使用
     * 
     * @param orderIds
     */
    public void getOmsOrders(List<String> orderIds) {
        getOmsOrders(orderIds, 1);
    }

    /**
     * 调用OMS接口获得订单状态数据,数据存入消息队列中,供更新线程使用
     * 
     * @param orderIds
     *            订单列表
     * @param threadCount
     *            线程数量,按照线程数量分配每个线程抓取的订单列表
     */
    public void getOmsOrders(List<String> orderIds, int threadCount) {
        fetch(orderIds, threadCount, false);
    }

    /**
     * 获得需要同步状态的订单列表
     * <p>
     * <i>查询状态在OMS_MONITOR_START_FLAG和OMS_MONITOR_END_FLAG之间的订单</i>
     * </p>
     * 
     * @return
     */
    public List<String> getWaitForSyncOrderIds() {
        OmsOrderVO.QueryCondition query = new OmsOrderVO.QueryCondition();
        query.setMin_flow_flag(OMS_MONITOR_START_FLAG);
        query.setMax_flow_flag(OMS_MONITOR_END_FLAG);
        query
            .setAddition_contidition(" or ((factory_name is null or latest_leave_base_date is null or sign_num is null or custom_sign_date is null or actual_deliver_date is null or return_order_date is null or status not in ('订单未满足','已撤销','已取消','已开发票','发票已打印','直发库存作废','工贸库存满足')) and flow_flag >= 15 and oms_order_id is not null) or (flow_flag = -30 and status not in ('已撤销','已取消') and oms_order_id is not null)");//如果工厂名称、最晚离基地日期、实际发货日期、工贸签收日期、工贸反单日期或者工贸签收数量为空,那么还需要继续获取状态
        return purchaseOmsSyncService.getOrderIds(query);
    }

    /**
     * 更新订单状态
     * <p>
     * <i>分割数据,多线程同时入库</i>
     * </p>
     * 
     * @param threadCount
     */
    public void updateOrderStatusModel(int threadCount) {
        //System.out.println("Begin update orders data.");

        ExecutorService eService = Executors.newFixedThreadPool(threadCount);
        countDownLatch = new CountDownLatch(threadCount);

        long beginTime = System.currentTimeMillis();
        try {
            synchronized (l_writter) {
                l_writter.clear();
                for (int i = 0; i < threadCount; i++) {
                    T2DataQueueWritter tqw = new T2DataQueueWritter(purchaseOmsSyncService, countDownLatch, 2);
                    l_writter.add(tqw);
                    eService.submit(new Thread(tqw));
                }
            }
            // 等待所有线程运行结束
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error("", e);
            }
            eService.shutdown();

        } catch (Exception ex) {
            log.error("", ex);
            eService.shutdown();
        }

        long endTime = System.currentTimeMillis();

        log.info("Update orders data finished. Elapse Time:" + (endTime - beginTime));
    }

    @Override
    public void threadFinish(int id) {
        for (T2DataQueueWritter writter : l_writter) {
            writter.finished();
        }
        log.info("All OmsDataFetcher Threads work has finished.");
    }

    @Override
    public void dataFetchFinish() {
        for (T2DataQueueWritter writter : l_writter) {
            writter.prepareFinished();
        }
        log.info("All OmsDataFetcher Threads work prepare finish.");
    }
}
