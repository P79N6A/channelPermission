package com.haier.invoice.services;

import com.haier.invoice.service.BatchInvalidEInvoiceService;
import com.haier.invoice.util.ArraySplitUtil;
import com.haier.shop.model.Invoices;
import com.haier.shop.service.ShopInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 批量作废电子发票
 **/
@Service
public class BatchInvalidEInvoiceServiceImpl implements BatchInvalidEInvoiceService {

    @Autowired
    private ShopInvoiceService shopInvoiceService;

    @Override
    public String batchInvalidEInvoice(String[] totalArray) {
        List<String[]> list = new ArraySplitUtil<String>().splistArray(totalArray, 50);
        int totalCount = 0;
        int failCount = 0;
        StringBuffer sb = new StringBuffer();
        for (String[] subArray : list) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            String[] strArray = new String[50];
            int num = 0;
            for (int i = 0; i < subArray.length; i++) {
                Invoices byCOrderSn = shopInvoiceService.getLatestInvoicesByCOrderSn(subArray[i]);
                if (byCOrderSn != null) {
                    boolean flag = (byCOrderSn.getElectricFlag() == 1)
                            && (byCOrderSn.getState() == 4)
                            && (byCOrderSn.getEaiWriteState().equals(""))
                            && (byCOrderSn.getStatusType() == 1 || byCOrderSn.getStatusType() == 3)
                            && (byCOrderSn.getSuccess() == 1);
                    if (flag) {
                        strArray[num++] = byCOrderSn.getCOrderSn();
                    } else {
                        sb.append("网单号:" + subArray[i] + "的最新发票网单号:" + byCOrderSn.getCOrderSn() + "不符合要求请检查<br>");
                        failCount++;
                    }
                } else {
                    sb.append("网单号:" + subArray[i] + "查询不到发票信息<br>");
                    failCount++;
                }

            }
            if (num != 0) {
                paramMap.put("cOrderSns", strArray);
                int updateCount = shopInvoiceService.eInvoiceBatchInvalid(paramMap);
                totalCount += updateCount;
            }

        }
        if (totalCount == 0) {
            return ("更新成功:0条记录！<br>" +
                    "更新失败:" + failCount + "条记录！请检查网单号是否符合作废条件(已开票且未推送作废、开票状态为正常)！<br>"
                    + sb.toString() + "<br>");
        } else {
            return ("更新成功:" + totalCount + "条记录！<br>" +
                    "更新失败:" + failCount + "条记录！请检查网单号是否符合作废条件(已开票且未推送作废、开票状态为正常)！<br>"
                    + sb.toString() + "<br>");
        }


//        for (String[] subArray : list) {
//            Map<String, Object> paramMap = new HashMap<String, Object>();
//            paramMap.put("cOrderSns", subArray);
//            int updateCount = shopInvoiceService.eInvoiceBatchInvalid(paramMap);
//            totalCount += updateCount;
//        }
//        if (totalCount == 0) {
//            return ("更新0条记录！请检查网单号是否符合作废条件(已开票且未推送作废、开票状态为正常)！");
//        } else {
//            return ("更新成功！共更新" + totalCount + "条记录！");
//        }
    }
}
