package com.haier.svc.api.controller.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;

import org.apache.velocity.tools.config.DefaultKey;

import com.haier.purchase.data.model.PurchaseItemStatus;
import com.haier.purchase.data.model.PurchaseOrderQueueStatus;
import com.haier.purchase.data.model.PurchaseOrderStatus;
import com.haier.shop.model.OrderRepairsConst;
import com.haier.shop.model.OrderSource;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.model.InvTransferLog;

@DefaultKey("utils")
public class VelocityUtil {
    public boolean isNull(Object value) {
        return value == null;
    }

    public boolean notNull(Object value) {
        return !isNull(value);
    }

    @SuppressWarnings("rawtypes")
    public boolean isEmpty(Object value) {
        if (value == null)
            return true;
        if (value.getClass().equals(String.class))
            return String.valueOf(value).isEmpty();
        if (Collection.class.isAssignableFrom(value.getClass()))
            return ((Collection) value).isEmpty();
        return false;
    }

    public boolean notEmpty(Object value) {
        return !isEmpty(value);
    }

    /**
     * 判断两个对象是否相等。
     * @param v1
     * @param v2
     * @return
     */
    public boolean eq(String v1, String v2) {
        if (v1 == null && v2 == null)
            return true;
        if (v1 == null || v2 == null)
            return false;
        return v1.equals(v2);
    }

    /**
     * 判断两个对象是否不等。
     * @param v1
     * @param v2
     * @return
     */
    public boolean neq(String v1, String v2) {
        return !this.eq(v1, v2);
    }

    public String getPurchaseOrderStatus(int status) {
        return PurchaseOrderStatus.fromValue(status).getText();
    }

    public String getPurchaseItemStatus(int status) {
        return PurchaseItemStatus.fromValue(status).getText();
    }

    public String getPurchaseOrderQueueStatus(int status) {
        return PurchaseOrderQueueStatus.fromValue(status).getText();
    }

    public String getProduceDailyPurchaseStatus(int status) {
        PurchaseItemStatus purchaseItemStatus = PurchaseItemStatus.fromValue(status);
        if (purchaseItemStatus == null)
            return "未知：" + status;
        return purchaseItemStatus.getText();
    }

    public String getTransferLineStatus(int status) {
        String ret = null;
        if (status == InvTransferLine.LINE_STATUS_DEL)
            ret = "删除状态";
        else if (status == InvTransferLine.LINE_STATUS_INIT)
            ret = "初始状态";
        else if (status == InvTransferLine.LINE_STATUS_CONFIRM)
            ret = "确认状态";
        else if (status == InvTransferLine.LINE_STATUS_FEE_INPUT)
            ret = "待录费用";
        else if (status == InvTransferLine.LINE_STATUS_FEE_ADUIT)
            ret = "待审核费用";
        else if (status == InvTransferLine.LINE_STATUS_LES)
            ret = "待传LES";
        else if (status == InvTransferLine.LINE_STATUS_STORE_OUT)
            ret = "待出库";
        else if (status == InvTransferLine.LINE_STATUS_STORE_IN)
            ret = "待入库";
        else if (status == InvTransferLine.LINE_STATUS_COMPLETE)
            ret = "已完成";
        else if (status == InvTransferLine.LINE_STATUS_CANCEL)
            ret = "已取消";
        else if (status == InvTransferLine.LINE_STATUS_3W_CONFIRM)
            ret = "3W确认状态";
        else if (status == InvTransferLine.LINE_STATUS_3W_CANCEL)
            ret = "3W取消状态";
        else if (status == InvTransferLine.LINE_STATUS_3W_TO_SAP)
            ret = "3W入库推SAP成功";

        return ret;
    }

    public String bigDecimal2money(BigDecimal d) {
        if (d == null)
            return null;
        DecimalFormat fmt1 = new DecimalFormat("###############0.##");
        String str = fmt1.format(d);
        return str;
    }

    public String getTransferReason(int transferReason) {
        String ret = null;
        if (transferReason == InvTransferLine.TRANSFER_REASON_PP) {
            ret = "平铺";
        } else if (transferReason == InvTransferLine.TRANSFER_REASON_QH) {
            ret = "缺货";
        } else if (transferReason == InvTransferLine.TRANSFER_REASON_XN) {
            ret = "虚拟";
        } else if (transferReason == InvTransferLine.TRANSFER_REASON_3W) {
            ret = "3W调拨";
        }else if (transferReason == InvTransferLine.TRANSFER_REASON_YP) {
            ret = "优品调拨";
        }
        return ret;
    }

    public String getTransferLogType(int logType) {
        String ret = null;
        if (logType == InvTransferLog.LOG_TYPE_DEL)
            ret = "删除调拨网单";
        else if (logType == InvTransferLog.LOG_TYPE_NEW)
            ret = "创建调拨网单";
        else if (logType == InvTransferLog.LOG_TYPE_MODIFY)
            ret = "修改调拨网单";
        else if (logType == InvTransferLog.LOG_TYPE_CONFIRM)
            ret = "确认调拨单";
        else if (logType == InvTransferLog.LOG_TYPE_LOGISTICS_CENTER_SUBMIT)
            ret = "提交物流中心录入费用";
        else if (logType == InvTransferLog.LOG_TYPE_FEE_INPUT)
            ret = "录入费用";
        else if (logType == InvTransferLog.LOG_TYPE_FEE_SUBMIT)
            ret = "提交费用审核";
        else if (logType == InvTransferLog.LOG_TYPE_FEE_AUDIT)
            ret = "费用审核";
        else if (logType == InvTransferLog.LOG_TYPE_LES_TRANSFER)
            ret = "传LES";
        else if (logType == InvTransferLog.LOG_TYPE_LES_OUT)
            ret = "LES出库";
        else if (logType == InvTransferLog.LOG_TYPE_LES_IN)
            ret = "LES入库";
        else if (logType == InvTransferLog.LOG_TYPE_CANCEL)
            ret = "取消";
        else if (logType == InvTransferLog.LOG_TYPE_3W_CBSTOVOM)
            ret = "3W转库-传VOM出库";
        else if (logType == InvTransferLog.LOG_TYPE_3W_CANCEL_QUEUE)
            ret = "加入3W取消物流队列，自动调用物流接口";
        return ret;
    }

    public String getDepName(String code) {
        String ret = null;
        if (InvTransferLine.REQ_DEP_SC.equalsIgnoreCase(code))
            ret = "商城";
        else if (InvTransferLine.REQ_DEP_TB.equalsIgnoreCase(code))
            ret = "淘宝";
        else if (InvTransferLine.REQ_DEP_DK.equalsIgnoreCase(code))
            ret = "大客户";
        else if (InvTransferLine.REQ_DEP_YY.equalsIgnoreCase(code))
            ret = "运营";
        else if (InvTransferLine.REQ_DEP_CX.equalsIgnoreCase(code))
            ret = "采销";
        return ret;
    }

    public String getChannelName(String code) {
        String ret = null;
        if (InvTransferLine.CHANNEL_SC.equalsIgnoreCase(code)) {
            ret = "商城渠道";
        } else if (InvTransferLine.CHANNEL_TB.equalsIgnoreCase(code)) {
            ret = "天猫渠道";
        } else if (InvTransferLine.CHANNEL_DKH.equalsIgnoreCase(code)) {
            ret = "大客户渠道";
        } else if (InvTransferLine.CHANNEL_JD.equalsIgnoreCase(code)) {
            ret = "京东渠道";
        } else if (InvTransferLine.CHANNEL_YX.equalsIgnoreCase(code)) {
            ret = "易迅渠道";
        } else if (InvTransferLine.CHANNEL_SHH.equalsIgnoreCase(code)) {
            ret = "社会化渠道";
        } else {
            ret = "WA共享";
        }
        return ret;
    }

    public String getPaymentStatus(Integer paymentStatus) {
        return OrderRepairsConst.PSMAP.get(paymentStatus);
    }

    public String getReceiptStatus(Integer receiptStatus) {
        return OrderRepairsConst.RSMAP.get(receiptStatus);
    }

    public String getStorageStatus(Integer storageStatus) {
        return OrderRepairsConst.SSMAP.get(storageStatus);
    }

    public String getCheckType(Integer checkType) {
        return OrderRepairsConst.CHECKTYPEMAP.get(checkType);
    }

    public String getCheckResult(Integer checkResult) {
        return OrderRepairsConst.CHECKRESULTMAP.get(checkResult);
    }

    public String getPackResult(Integer packResult, Integer checkType) {
        if (packResult == null || checkType == null) {
            return null;
        }
        if (OrderRepairsConst.CHECKTYPE_FIRST == checkType) {
            return OrderRepairsConst.PACKRESULTMAP.get(packResult);
        } else if (OrderRepairsConst.CHECKTYPE_SECOND == checkType) {
            return OrderRepairsConst.PACKRESULTMAP_2MAP.get(packResult);
        } else if (OrderRepairsConst.CHECKTYPE_THIRD == checkType) {
            return OrderRepairsConst.PACKRESULT_3MAP.get(packResult);
        }
        return null;
    }

    public String getQuality(Integer quality) {
        return OrderRepairsConst.QUALITYMAP.get(quality);
    }

    public String getOperate(Integer operate) {
        return OrderRepairsConst.OPERATEMAP.get(operate);
    }

    /**
     * hp生成工单结果
     * @param hpOrderStatus
     * @return
     */
    public String getHpOrderStatus(Integer hpOrderStatus) {
        return OrderRepairsConst.HPORDERMAP.get(hpOrderStatus);
    }

    /**
     * 退货退款状态
     * @param typeActual
     * @return
     */
    public String getTypeActual(Integer typeActual) {
        return OrderRepairsConst.TYPEACTUALMAP.get(typeActual);
    }

    /**
     * 查询处理方式
     * @param handlerStatus
     * @return
     */
    public String getHandlerStatus(Integer handlerStatus) {
        return OrderRepairsConst.HANDLESTATUSMAP.get(handlerStatus);
    }

    /**
     * 线上线下退款查询
     * @param code
     * @return
     */
    public String getOfflineFlag(Integer code) {
        return OrderRepairsConst.OFFLINEFLAGMAP.get(code);
    }

    /**
     * 退换货操作日志更改内容转化
     * @param code
     * @return
     */
    public String getChangeContent(String code) {
        if ("hs_begin".equalsIgnoreCase(code)) {
            code = "审核通过";
        } else if ("ss_directin10".equalsIgnoreCase(code)) {
            code = "未发网点入10库";
        } else if ("les_return".equalsIgnoreCase(code)) {
            code = "LES回传检验结果";
        } else if ("edit".equalsIgnoreCase(code)) {
            code = "修改信息";
        } else if ("hp_send".equalsIgnoreCase(code)) {
            code = "提交HP工单";
        } else if ("ss_wait".equalsIgnoreCase(code)) {
            code = "等待货物召回";
        } else if ("hs_cancel".equalsIgnoreCase(code)) {
            code = "审核不通过";
        } else if ("hs_stop".equalsIgnoreCase(code)) {
            code = "终止申请";
        } else if ("rs_recall".equalsIgnoreCase(code)) {
            code = "发票召回完成";
        } else if ("ss_recall".equalsIgnoreCase(code)) {
            code = "货物召回完成";
        } else if ("ps_refund".equalsIgnoreCase(code)) {
            code = "退款完成";
        }
        return code;
    }

    /**
     * 转化时间
     * 时间为0时,格式化后为1970-01-01 08:00:00,需要转为""
     * @param date
     * @return
     */
    public String dateTransfer(String date) {
        if ("1970-01-01 08:00:00".equals(date)) {
            date = "";
        }
        return date;
    }

    public String getOrderSourceName(String source) {
        Map<String, String> externalSourceMap = OrderSource.getExternalSource();
        String sourceName = externalSourceMap.get(source);
        if (sourceName == null || sourceName == "") {
            return "未知";
        }
        return sourceName;
    }

}