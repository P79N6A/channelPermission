package com.haier.shop.util;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.*;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发票业务操作使用工具类
 **/
public class InvoiceServiceUtil {

    /**
     * 组装订单操作日志
     * @param order
     * @param op
     * @param remark
     * @param changeLog
     * @return
     */
    public static OrderOperateLogs assemblyOrderOperateLog(Orders order, OrderProducts op, String remark,
                                                     String changeLog, String operator) {
        OrderOperateLogs log = new OrderOperateLogs();
        log.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
        log.setLogTime((int) (new Date().getTime() / 1000));
        log.setNetPointId(op.getNetPointId());
        log.setOperator(StringUtil.isEmpty(operator) ? "系统" : operator);
        log.setOrderId(op.getOrderId());
        log.setOrderProductId(op.getId());
        log.setPaymentStatus(order.getPaymentStatus());
        log.setRemark(StringUtil.isEmpty(remark) ? "" : remark);
        log.setSiteId(1);
        log.setStatus(op.getStatus());
        return log;
    }

    /**
     * 组装 电子发票同步日志
     * @param order
     * @param op
     * @param invoice
     * @param attMap
     * @return
     */
    public static InvoiceElectricSyncLogs assemblyInvoiceElectricSyncLogs(Orders order, OrderProducts op,
                                                                    Invoices invoice,
                                                                    Map<String, String> attMap) {
        InvoiceElectricSyncLogs log = new InvoiceElectricSyncLogs();
        log.setSiteId(1);
        log.setAddTime((int) (new Date().getTime() / 1000));
        log.setOrderId(op.getOrderId());
        log.setOrderProductId(op.getId());
        log.setCOrderSn(op.getCOrderSn());
        log.setOrderSource(order.getSource());
        log.setPushData("");
        log.setReturnData("");
        log.setCount(0);
        log.setSuccess(0);
        log.setSuccessTime(0);
        log.setLastMessage("");

        String invoiceNumber = "";
        String fiscalCode = "";
        String viewUrl = "";
        Integer statusType = 1;
        String pdfUrl = "";
        String downloadUrl = "";
        Long operateTime = 0l;
        BigDecimal amount = BigDecimal.ZERO;

        if (attMap.get("code") != null && !attMap.get("code").equals("")) {
            invoiceNumber = attMap.get("code");
        } else {
            invoiceNumber = "";
        }
        if (attMap.get("fiscalCode") != null && !attMap.get("fiscalCode").equals("")) {
            fiscalCode = attMap.get("fiscalCode");
        } else {
            fiscalCode = "";
        }
        if (attMap.get("viewUrl") != null && !attMap.get("viewUrl").equals("")) {
            viewUrl = attMap.get("viewUrl");
        } else {
            viewUrl = "";
        }
        if (attMap.get("status").equals("1")) {
            statusType = 1;
        } else {
            statusType = 2;
        }
        if (attMap.get("pdfUnsignedUrl") != null && !attMap.get("pdfUnsignedUrl").equals("")) {
            pdfUrl = attMap.get("pdfUnsignedUrl");
        } else {
            pdfUrl = "";
        }
        if (attMap.get("downloadUrl") != null && !attMap.get("downloadUrl").equals("")) {
            downloadUrl = attMap.get("downloadUrl");
        } else {
            downloadUrl = "";
        }
        if (statusType.equals(1)) {
            if (attMap.get("generateTime") != null && !attMap.get("generateTime").equals("")) {
                operateTime = Long.parseLong(attMap.get("generateTime"));
            } else {
                operateTime = 0l;
            }
        } else {
            if (attMap.get("validTime") != null && !attMap.get("validTime").equals("")) {
                operateTime = Long.parseLong(attMap.get("validTime"));
            } else {
                operateTime = 0l;
            }
        }
        if (attMap.get("totalAmount") != null && !attMap.get("totalAmount").equals("")) {
            amount = BigDecimal.valueOf(Double.parseDouble(attMap.get("totalAmount")));
        } else {
            amount = BigDecimal.ZERO;
        }
        log.setInvoiceNumber(invoiceNumber);
        log.setFiscalCode(fiscalCode);
        log.setViewUrl(viewUrl);
        log.setStatusType(statusType);
        log.setPdfUrl(pdfUrl);
        log.setDownloadUrl(downloadUrl);
        log.setOperateTime(operateTime);
        log.setAmount(amount);
        return log;
    }

    /**
     * 获取渠道名称
     */
    public static String getSourceName(String sourceIds, List<Map<String, Object>> channels) {
        StringBuffer sbStr = new StringBuffer();
        Map<String, String> mapSource = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(channels)) {
            int length = channels.size();
            for (int k = 0; k < length; k++) {
                Map<String, Object> info = channels.get(k);
                mapSource.put(info.get("order_source").toString(), info.get("note").toString());
            }
        }

        if (StringUtils.isNotBlank(sourceIds)) {
            if (sourceIds.indexOf(",") != -1) { // 多个
                String[] strArr = sourceIds.split(",");
                for (int i = 0; i < strArr.length; i++) {
                    if (StringUtils.isBlank(sbStr.toString())) {
                        sbStr.append(mapSource.get(strArr[i]));
                    } else {
                        sbStr.append(",");
                        sbStr.append(mapSource.get(strArr[i]));
                    }

                }
            } else { // 单个
                if (mapSource.containsKey(sourceIds)) {
                    sbStr.append(mapSource.get(sourceIds));
                } else {
                    sbStr.append("无渠道名称");
                }

            }
        }
        return sbStr.toString();
    }

    /**
     * InvoiceWwwLogs.success转为汉字
     */
    public static String parseInvoiceWwwLogsSuccess(Integer success){
        String dispValue = "";
        if(success==0){
            dispValue="待处理";
        }
        if (success==1){
            dispValue="已处理";
        }
        if (success==2){
            dispValue="无需处理";
        }
        return dispValue ;
    }

    /**
     * InvoiceWwwLogs.source转汉字
     * @param source
     * @return
     */
    public static String parseInvoiceWwwLogsSource(String source){
        String dispValue="";
        switch (source){
            case "TBSC" :
                dispValue="淘宝海尔官方旗舰店";
                break;
            case "TOPDHSC" :
                dispValue="海尔生活家电旗舰店";
                break;
            case "TOPFENXIAO" :
                dispValue="淘宝海尔官方旗舰店分销平台";
                break;
            case "TOPFENXIAODHSC" :
                dispValue="淘宝海尔生活家电旗舰店分销平台";
                break;
            case "TOPBUYBANG" :
                dispValue="淘宝海尔买帮专卖店";
                break;
            case "TOPBOILER" :
                dispValue="淘宝海尔热水器专卖店";
                break;
            case "TOPSHJD" :
                dispValue="淘宝海尔生活电器专卖店";
                break;
            case "TOPMOBILE" :
                dispValue="淘宝海尔手机专卖店";
                break;
            case "TONGSHUAI" :
                dispValue="统帅日日顺乐家专卖店";
                break;
            case "TONGSHUAIFX" :
                dispValue="统帅日日顺分销平台";
                break;
            case "TOPXB" :
                dispValue="海尔新宝旗舰店";
                break;
            case "TOPFENXIAOXB" :
                dispValue="淘宝海尔新宝旗舰店分销平台";
                break;
            case "WASHER" :
                dispValue="海尔洗衣机官方旗舰店";
                break;
            case "FRIDGE" :
                dispValue="海尔冰冷官方旗舰店";
                break;
            case "AIR" :
                dispValue="淘宝空调旗舰店";
                break;
            case "TMMKFX" :
                dispValue="天猫模卡分销";
                break;
            case "GQGYS" :
                dispValue="天猫分销";
                break;
            case "TBCT" :
                dispValue="淘宝村淘";
                break;
            case "TBQYG" :
                dispValue="天猫企业购";
                break;
            case "TMST" :
                dispValue="天猫生态";
                break;
            case "FLW" :
                dispValue="商城PC-返利网";
                break;
            case "YHDQWZY" :
                dispValue="电商平台-1号店全网自营";
                break;
            case "TMKSD" :
                dispValue="卡萨帝官方旗舰店";
                break;
            case "TMTV" :
                dispValue="天猫海尔电视";
                break;
            case "TBCFDD" :
                dispValue="淘宝海尔厨房大电旗舰店";
                break;
            case "TBXCR" :
                dispValue="天猫小超人旗舰店";
                break;
            case "TMMK" :
                dispValue="mooka模卡官方旗舰店";
                break;
        }
        return dispValue ;
    }

}
