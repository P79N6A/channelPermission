package com.haier.invoice.services;

import com.haier.invoice.service.BatchBillEInvoiceService;
import com.haier.invoice.util.ArraySplitUtil;
import com.haier.shop.service.InvoiceQueueService;
import com.haier.shop.service.ShopMemberInvoicesService;
import com.haier.shop.service.ShopOrderProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
