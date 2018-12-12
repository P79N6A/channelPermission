package com.haier.invoice.model;

import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.invoice.service.EInvoiceV2Service;
import com.haier.invoice.util.StrTools;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同步电子发票
 **/
@Service
public class SyncElecInvoiceModel {

    private static final Logger logger = LogManager.getLogger(SyncElecInvoiceModel.class);

    @Autowired
    private ShopInvoiceService shopInvoiceService;
    @Autowired
    private EInvoiceV2Service eInvoiceV2Service;

    @Value("${invoice.platformcode}")
    private String platformCode;
    @Value("${invoice.version}")
    private String version;


    public ServiceResult<Integer> syncInvoiceInfoFromEInvoiceSystem(List<Invoices> invoicesList) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            if (invoicesList == null || invoicesList.size() <= 0) {
                result.setMessage("发票列表为空!");
                result.setSuccess(false);
                result.setResult(0);
            } else {
                Integer count = syncInvoiceInfo(invoicesList);
                result.setMessage("");
                result.setSuccess(true);
                result.setResult(count);
            }
        } catch (Exception e) {
            logger.error("同步作废发票信息从电子发票系统时, 发生未知异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(0);
        }
        return result;
    }

    /**
     * 同步发票信息从电子发票系统(电子发票系统同步到Invoices表)
     * 如果开票成功，检查invoices表success字段是否为1，不为1要置为1
     * 检查sap队列是否存在，不存在，写入sap队列
     * 如果存在未成功要重置次数
     */
    private int syncInvoiceInfo(List<Invoices> list) {
        int count = 0;
        try {
            if (list == null || list.size() == 0) {
                logger.info("[syn_invoiceinfo_from_einvoicesys]获取待同步发票信息队列：没有需要处理的记录。");
                return 0;
            }
            if (list != null && list.size() > 0) {
                for (Invoices invoices : list) {
                    if (invoices == null) {
                        continue;
                    }
                    if (StringUtil.isEmpty(invoices.getCOrderSn(), true)) {
                        invoices
                                .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                        shopInvoiceService.updateInvoices(invoices);
                        continue;
                    }
                    try {
                        //同步发票信息
                        //                        ServiceResult<Response> electricResult = queryInvoice(invoices
                        //                            .getCOrderSn());
                        Map<String, String> paramMap = queryInvoiceResultMap(invoices.getCOrderSn());
                        if (paramMap != null) {
                            //                            Response response = electricResult.getResult();
                            //                            Invoice eInvoice = response.getInvoice();
                            if (!InvoiceBizModel.eInvoiceEntityToInvoices(paramMap, invoices)) {
                                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                        .getTryNum()) + 1);
                                shopInvoiceService.updateInvoices(invoices);
                                continue;
                            }
                            try {
                                boolean beSuccess = shopInvoiceService.updateElecInvoice4ElecSys(invoices, paramMap);
                                if (beSuccess) {
                                    count++;
                                }
                            } catch (Exception ex) {
                                logger.error("定时同步发票信息时异常", ex);
                                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                        .getTryNum()) + 1);
                                shopInvoiceService.updateInvoices(invoices);
                            }
                        } else {
                            logger.error("[syn_invoiceinfo_from_einvoicesys][invoiceId："
                                    + invoices.getId() + "]同步发票信息时异常");
                            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                    .getTryNum()) + 1);
                            shopInvoiceService.updateInvoices(invoices);
                            continue;
                        }

                    } catch (Exception e) {
                        logger.error("[syn_invoiceinfo_from_einvoicesys][网单id:"
                                + invoices.getOrderProductId().toString() + "]同步发票信息异常字符串:"
                                + StrTools.printExceptionStackInfo(e));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取同步发票信息队列出现异常：", e);
        }
        return count;
    }

    /**
     * 电子发票开票信息查询
     *
     * @param corderSn
     * @return
     */
    public Map<String, String> queryInvoiceResultMap(String corderSn) {
        String queryXml = InvoiceBizModel.getQueryInvoiceXml(corderSn, platformCode, version);
        //log.info("====================发送的xml：====================\n" + queryXml);
        //        ServiceResult<String> result = eInvoiceService.queryInvoice(queryXml, corderSn);
        ServiceResult<String> result = eInvoiceV2Service.queryInvoice(corderSn, queryXml);
        //log.info("====================返回的success：" + result.getSuccess());
        org.dom4j.Document doc = null;
        String[] strmes = new String[1];
        if (result.getSuccess()) {
            try {
                String responXML = result.getResult();
                //log.info("====================返回的xml：====================\n" + responXML);
                doc = parseXml(responXML, strmes);
                return invoiceResultDocToMap(doc);
            } catch (Exception e) {
                logger.error("解析响应xml异常", e);
                e.printStackTrace();
            }
        } else {
            logger.error("====================失败返回的mes：====================\n" + result.getMessage());
        }
        return null;
    }


    /**
     * 转换xml
     *
     * @param xml
     * @param message
     * @return
     */
    private org.dom4j.Document parseXml(String xml, String[] message) {
        org.dom4j.Document doc = null;
        if (xml != null && !xml.equals("")) {
            try {
                doc = DocumentHelper.parseText(xml);
            } catch (DocumentException e) {
                message[0] = "解析返回XML结果时异常:" + e.getMessage();
            }
        } else {
            message[0] = "返回XML结果为空";
            logger.error(message);
        }
        return doc;
    }

    /**
     * 发票返回结果数据转换到Map
     *
     * @param doc
     * @return
     * @throws ParseException
     */
    private Map<String, String> invoiceResultDocToMap(org.dom4j.Document doc) {
        if (doc == null) {
            return null;
        }
        org.dom4j.Element resultElement = doc.getRootElement().element("result");
        String code = resultElement.attribute("code").getValue();

        if (code != null && code.equals("0")) {//成功
            org.dom4j.Element invoiceElement = doc.getRootElement().element("invoices")
                    .element("invoice");

            @SuppressWarnings("unchecked")
            List<Attribute> listInvoice = invoiceElement.attributes();
            if (listInvoice != null && listInvoice.size() > 0) {
                Map<String, String> attMap = new HashMap<String, String>();
                for (int i = 0; i < listInvoice.size(); i++) {
                    if (listInvoice.get(i).getName().equalsIgnoreCase("generateTime")) {
                        attMap
                                .put(listInvoice.get(i).getName(),
                                        (timeStringToDate(listInvoice.get(i).getValue()).getTime() / 1000)
                                                + "");
                    } else if (listInvoice.get(i).getName().equalsIgnoreCase("validTime")) {
                        attMap
                                .put(listInvoice.get(i).getName(),
                                        (timeStringToDate(listInvoice.get(i).getValue()).getTime() / 1000)
                                                + "");
                    } else {
                        attMap.put(listInvoice.get(i).getName(), listInvoice.get(i).getValue());
                    }
                }
                return attMap;
            }
        }
        return null;
    }

    /**
     * 处理发票返回时间
     *
     * @param time
     * @return
     * @throws ParseException
     */
    private Date timeStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (StringUtil.isEmpty(time)) {
            return null;
        }
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
