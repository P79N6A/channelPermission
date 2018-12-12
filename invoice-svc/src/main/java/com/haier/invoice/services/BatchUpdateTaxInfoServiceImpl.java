package com.haier.invoice.services;

import com.haier.common.ServiceResult;
import com.haier.invoice.model.SyncTaxInvoiceModel;
import com.haier.invoice.service.BatchUpdateTaxInfoService;
import com.haier.invoice.util.HttpJsonResult;
import com.haier.shop.model.Invoices;
import com.haier.shop.service.ShopInvoiceService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 批量更新金税发票
 **/
@Service
public class BatchUpdateTaxInfoServiceImpl implements BatchUpdateTaxInfoService {

    private static final Logger logger = LogManager.getLogger(BatchUpdateTaxInfoServiceImpl.class);

    @Autowired
    private ShopInvoiceService shopInvoiceService;
    @Autowired
    private SyncTaxInvoiceModel syncTaxInvoiceModel;

    @Override
    public HttpJsonResult<Map<String, Object>> doBatchUpdateTaxInfo(String cOrderSns, Map<String, Object> modelMap) {

        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
            String[] strTemp = cOrderSns.split("\r\n");
            Set<String> set = new HashSet<String>(Arrays.asList(strTemp));
            String[] cOrderSnsArray = set.toArray(new String[0]);

            StringBuffer sbUF = new StringBuffer();
            StringBuffer sbError = new StringBuffer();
            StringBuffer sbHidden = new StringBuffer();
            StringBuffer sb = new StringBuffer();
            List<Invoices> invoicesList = null;
            Integer total = 0;
            Integer unFindCount = 0;
            Integer errorCount = 0;
            for (String cOrderSn : cOrderSnsArray) {
                //获取开单列表List
                Invoices invoice = shopInvoiceService.getInvoicesByCOrderSn(cOrderSn.trim());
                if (invoice == null) {
                    unFindCount += 1;
                    sbUF.append(unFindCount).append("、网单号【").append(cOrderSn).append("】未查询到发票信息！")
                            .append("\r\n");
                    continue;
                }

                invoicesList = new ArrayList<Invoices>();
                invoicesList.add(invoice);
                try {
                    ServiceResult<Integer> rtn = syncTaxInvoiceModel.syncStatusInvoices(invoicesList);
                    if (rtn.getResult() == 0) {
                        errorCount += 1;
                        sbError.append(errorCount).append("、网单号【").append(cOrderSn)
                                .append("】同步失败，请联系管理员查看错误日志！").append("\r\n");
                        sbHidden.append(errorCount).append("、网单号【").append(cOrderSn)
                                .append("】同步失败，请看order服务错误信息").append("\r\n");
                    }
                    total += rtn.getResult();
                } catch (Exception e) {
                    errorCount += 1;
                    sbError.append(errorCount).append("、网单号【").append(cOrderSn).append("】连接超时")
                            .append("\r\n");
                    sbHidden.append(errorCount).append("、网单号【").append(cOrderSn).append("】错误信息：")
                            .append(e).append("\r\n");
                    continue;
                }

            }
            sb.append("总计").append(cOrderSnsArray.length).append("条数据！\r\n").append(total)
                    .append("条同步成功！\r\n");
            if (unFindCount > 0) {
                sb.append(unFindCount).append("条未查询到信息如下：\r\n").append(sbUF);
            }
            if (errorCount > 0) {
                sb.append(errorCount).append("条同步失败信息如下：\r\n").append(sbError);
            }
            result.setMessage(sb.toString());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", sbHidden.toString());
            result.setData(map);
        } catch (Exception e) {
            logger.error("[invoice][doBatchUpdateTaxInfo]批量同步金税发票状态时发生未知错误", e);
            result.setMessage("批量同步金税发票状态失败！");
        }
        return result;
    }
}
