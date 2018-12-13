package com.haier.invoice.services;

import com.haier.invoice.service.BatchBillEInvoiceService;
import com.haier.invoice.util.ArraySplitUtil;
import com.haier.shop.model.InvoiceQueue;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.service.InvoiceQueueService;
import com.haier.shop.service.ShopMemberInvoicesService;
import com.haier.shop.service.ShopOrderProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 批量开电子发票
 *
 * @create 2018-01-10
 **/
@Service
public class BatchBillEInvoiceServiceImpl implements BatchBillEInvoiceService {

    @Autowired
    private ShopOrderProductsService shopOrderProductsService;
    @Autowired
    private InvoiceQueueService invoiceQueueService;
    @Autowired
    private ShopMemberInvoicesService shopMemberInvoicesService;

    @Override
    public String batchBillEInvoice(String[] totalArray) {
        StringBuilder content = new StringBuilder();
        List<String[]> list = new ArraySplitUtil<String>().splistArray(totalArray, 50);
        int totalCount = 0;
        Map<String, Object> tempMap = null;
        int existResult = 0;
        int orderId = 0;
        int electricFlag = 0;
        int insertCount = 0;
        for (String[] subArray : list) {
            tempMap = new HashMap<String, Object>();
            tempMap.put("cOrderSns", subArray);
            List<Integer> opIdArray = shopOrderProductsService.getOrderProductIdsInfo(tempMap);
            for (Integer opId : opIdArray) {
                existResult = invoiceQueueService.getInvoiceQueueExistFlag(opId);
                if (existResult != 0) {//数据已存在
                    content.append("网单ID【" + opId + "】已存在队列表中;");
                } else {
                    orderId = shopOrderProductsService.getOrderIdById(opId);
                    electricFlag = shopMemberInvoicesService.getElectricFlag(orderId);//是否是电子发票开票
                    if (electricFlag == 0) {
                        content.append("网单ID【" + opId + "】不是电子发票开票;");
                    } else {
                        tempMap.put("orderProductId", opId);
                        insertCount = invoiceQueueService.insertInvoiceQueue(opId);
                        totalCount += insertCount;
                    }
                }
            }
        }

        if (totalCount == 0) {
            return ("插入0条记录！请检查网单号是否正确！失败记录:" + content.toString());
        } else {
            if (!content.toString().equals("")) {
                return ("共插入" + totalCount + "条记录！失败记录:" + content.toString());
            } else {
                return ("插入成功！共插入" + totalCount + "条记录！");
            }
        }
    }

    /**
     * 根据网单id开票
     * @param orderProductId
     * @return
     */
    @Override
    public String eInvoiceBatchBillingByOrderProductId(String orderProductId) {

        int opId = Integer.parseInt(orderProductId);
        StringBuilder content = new StringBuilder();
        List<InvoiceQueue> invoiceQueuesList = null;
        int orderId = 0;
        int electricFlag = 0;
        int insertCount = 0;
        //先查询网单是否存在
        OrderProducts orderProduct = shopOrderProductsService.get(opId);
        if(orderProduct != null){
            //isMakeReceipt : "9">待开票
            Integer isMakeReceipt = orderProduct.getIsMakeReceipt();
            // '开票类型 0 初始值 1 库房开票  2 共享开票',
            Integer makeReceiptType = orderProduct.getMakeReceiptType();
            //状态 "130">完成关闭
            Integer status = orderProduct.getStatus();
            //判断是否需要开票
            if(isMakeReceipt == 9 && makeReceiptType == 2 && status > 8){
                 invoiceQueuesList = invoiceQueueService.getByOrderProductId(opId);
                if (invoiceQueuesList != null && invoiceQueuesList.size()>0) {//数据已存在
                    InvoiceQueue invoiceQueue = invoiceQueuesList.get(0);
                    if(invoiceQueue.getSuccess() == 1){
                        //跟新invoiceQueue状态
                        //1：是,0：否'
                        invoiceQueue.setSuccess(0);
                        invoiceQueue.setProcessTime(new Date());
                        invoiceQueue.setLastMessage("");
                        insertCount = invoiceQueueService.updateInvoiceQueueSuccessByOrderProductId(invoiceQueue);
                    }
                } else {
                    orderId = shopOrderProductsService.getOrderIdById(opId);
                    electricFlag = shopMemberInvoicesService.getElectricFlag(orderId);//是否是电子发票开票
                    if (electricFlag == 0) {

                        content.append("网单ID【" + opId + "】不是电子发票开票;");
                    } else {
                        insertCount = invoiceQueueService.insertInvoiceQueue(opId);
                    }
                }
            }else{
                content.append("网单ID"+opId+"不符合开票条件");
            }

        }else {
            content.append("网单ID"+opId+"不存在");
        }
        if (insertCount == 0) {
            return ("插入0条记录！请检查网单号是否正确！失败记录:" + content.toString());
        } else {
            if (!content.toString().equals("")) {
                return ("共插入" + insertCount + "条记录！失败记录:" + content.toString());
            } else {
                return ("插入成功,请等待开票！共插入" + insertCount + "条记录！");
            }
        }
    }
    /**
     * 根据网单id重新开票
     * @param orderProductId
     * @return
     */
    @Override
    public String eInvoiceBatchReBillingByOrderProductId(String orderProductId) {
        int opId = Integer.parseInt(orderProductId);
        int rowCount =0;
        StringBuilder content = new StringBuilder();
        //查询网单
        OrderProducts orderProduct = shopOrderProductsService.get(opId);
       if(orderProduct != null){
            /*isMakeReceipt :3"作废发票 ,"4"冲红发票*/
           Integer isMakeReceipt = orderProduct.getIsMakeReceipt();
           Integer status = orderProduct.getStatus();
           //判断是否符合重新开票条件
           if(isMakeReceipt == 3 || isMakeReceipt == 4 && status > 8){
               //根据orderProductId查询是否存在发票队列表中
               List<InvoiceQueue> invoiceQueueList = invoiceQueueService.getByOrderProductId(opId);
               if(invoiceQueueList != null && invoiceQueueList.size()>0){
                   InvoiceQueue invoiceQueue = invoiceQueueList.get(0);
                   if(invoiceQueue.getSuccess() == 1){
                       //跟新invoiceQueue状态
                       //1：是,0：否'
                       invoiceQueue.setSuccess(0);
                       invoiceQueue.setProcessTime(new Date());
                       invoiceQueue.setLastMessage("");
                       rowCount = invoiceQueueService.updateInvoiceQueueSuccessByOrderProductId(invoiceQueue);
                   }else{
                       content.append("网单ID【" + opId + "】不需要重新开票;");
                   }
               }else{
                   content.append("网单ID【" + opId + "】不存在队列表中;");
               }

           }else {
               content.append("网单ID【" + opId +"不符合重新开票条件");
           }
       }else {
           content.append("网单ID【" + opId + "网单不存在");
       }
        if (rowCount == 0) {
            return ("更新0条记录！重新开票失败"+ content.toString());
        }else {
            return ("重新开票成功," + rowCount + "条记录！");
        }
    }
}
