package com.haier.invoice.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import com.haier.invoice.util.DateFormatUtil;
import com.haier.shop.model.InvoiceConst;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrderProducts;
import org.springframework.stereotype.Service;

/**
 * 发票操作辅助类
 */
@Service
public class InvoiceBizModel {

    private static Logger logger = LogManager.getLogger(InvoiceBizModel.class);

    /**
     * 更新发票信息
     */
    public static boolean eInvoiceEntityToInvoices(Map<String, String> src, Invoices target) {
        //验证发票数据
        if (src == null || target == null) {
            return false;
        }
        if ((src.get("validTime") == null || src.get("validTime").equals(""))
                && (src.get("generateTime") == null || src.get("generateTime").equals(""))) {
            return false;
        }
        //status=3冲红     status=2作废   status=1开票   status=4被红冲
        if (src.get("status").equals("1")) {
            Long generate = Long.parseLong(src.get("generateTime"));
            target.setBillingTime(generate);
            target.setState(InvoiceConst.COMPLETE_STATE);
            if (target.getStatusType().equals(1) && src.get("generateTime") != null
                    && !src.get("generateTime").equals("") && !target.getSuccess().equals(1)) {
                target.setSuccess(1);
            }
            target.setEaiWriteState(InvoiceConst.NORMAL_KP_STATE);
            target.setDrawer("CBS"); //TODO 系统名称
            target.setInvoiceNumber(src.get("code"));
            target.setEInvViewUrl(src.get("viewUrl"));
            target.setFiscalCode(src.get("fiscalCode"));
            target.setCheckCode(src.get("checkCode"));
            target.setTryNum((target.getTryNum() == null ? 1 : target.getTryNum()) + 1);
        } else if (src.get("status").equals("3")) {//作废红冲  被红冲
            Long valid = Long.parseLong(src.get("validTime"));
            target.setInvalidTime(valid);
            target.setInvalidReason(src.get("validReason"));
            target.setEaiWriteState(InvoiceConst.MONTH_INVALID_KP_STATE);
            if (target.getStatusType().equals(4) && src.get("validTime") != null
                    && !src.get("validTime").equals("") && !target.getSuccess().equals(1)) {
                target.setSuccess(1);
            }
            target.setTryNum((target.getTryNum() == null ? 1 : target.getTryNum()) + 1);
        }

        //作废发票,保留原发票信息
        //target.setInvoiceNumber(src.get("code") != null ? src.get("code") : "");
        //target.setEInvViewUrl(src.get("viewUrl") != null ? src.get("viewUrl") : "");
        //target.setFiscalCode(src.get("fiscalCode") != null ? src.get("fiscalCode") : "");
        //target.setCheckCode(src.get("checkCode") != null ? src.get("checkCode") : "");//V.20版本新增校验码
        return true;
    }

    /**
     * 根据网单号生成查询电子发票xml
     *
     * @param corderSn
     * @return
     */
    public static String getQueryInvoiceXml(String corderSn, String platformCode, String version) {
        Element root = new Element("request");
        Document doc = new Document(root);
        Element header = new Element("header");
        header.setAttribute("platformCode", platformCode);
        header.setAttribute("postTime",
                DateFormatUtil.formatByType("yyyy-MM-dd HH:mm:ss", new Date()));
        header.setAttribute("sessionID", corderSn + "_CX");
        header.setAttribute("version", version);

        root.addContent(header);

        Element criteria = new Element("criteria");
        Element criterion = new Element("criterion");
        criterion.setAttribute("name", "orderNo");
        criterion.setAttribute("value", corderSn);
        criteria.addContent(criterion);

        root.addContent(criteria);

        XMLOutputter XMLOut = new XMLOutputter();
        return XMLOut.outputString(doc);
    }

    /**
     * 计算发票金额
     *
     * @param op
     * @param flag true代表最后如果为0元改为0.01元,false代表不更改,实际价
     * @return
     */
    public static BigDecimal calInvoiceAmount(OrderProducts op, boolean flag) {
        if (op == null) {
            logger.error("计算发票金额：订单、网单对象都不能为null，网单号：" + op.getCOrderSn());
            return null;
        }
        //计算开票金额小计
        BigDecimal amount = op.getProductAmount();
        if (flag) {
            //2014-6-4 金额为零的所有网单，改为开1分钱发票
            if (amount.compareTo(BigDecimal.ZERO) == 0
                    || amount.divide(new BigDecimal(op.getNumber()), 2, RoundingMode.HALF_UP)
                    .compareTo(BigDecimal.ZERO) == 0) {
                BigDecimal price = new BigDecimal("0.01");
                amount = price.multiply(new BigDecimal(op.getNumber()));
            }
        }
        return amount;
    }
}
