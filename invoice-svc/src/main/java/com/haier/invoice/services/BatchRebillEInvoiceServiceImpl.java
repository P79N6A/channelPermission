package com.haier.invoice.services;

import com.haier.invoice.service.BatchRebillEInvoiceService;
import com.haier.invoice.util.ArraySplitUtil;
import com.haier.shop.service.ShopInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 批量重开电子发票
 **/
@Service
public class BatchRebillEInvoiceServiceImpl implements BatchRebillEInvoiceService {

    @Autowired
    private ShopInvoiceService shopInvoiceService;

    @Override
    public String batchRebillEInvoice(String[] totalArray) {
        List<String[]> list = new ArraySplitUtil<String>().splistArray(totalArray, 50);
        int totalCount = 0;
        for (String[] subArray : list) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("cOrderSns", subArray);
            int updateCount = shopInvoiceService.eInvoiceBatchReBilling(paramMap);
            totalCount += updateCount;
        }
        if (totalCount == 0) {
            return ("更新0条记录！请检查网单号是否正确！");
        } else {
            return ("更新成功！共更新" + totalCount + "条记录！");
        }
    }
}
