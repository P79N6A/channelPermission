package com.haier.svc.api.controller.util.http.dangdang;

import com.haier.eop.data.model.OrdersQueue;
import com.haier.svc.api.controller.util.DateUtil;
import com.haier.svc.api.controller.util.StringUtil;
import com.haier.svc.api.controller.util.http.AbstractHaierHttpClient;
import com.haier.svc.api.controller.util.http.HttpUtils;
import com.haier.svc.api.controller.util.http.JobException;
import com.haier.svc.api.controller.util.http.ShopStockEnum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Component
public class DangdangClient extends AbstractHaierHttpClient {
    private static final Logger log = LogManager.getLogger(DangdangClient.class);
    public final static String METHOD_ORDERS_GET = "dangdang.orders.list.get";
    public final static String METHOD_ORDER_DETAIL_GET = "dangdang.order.details.get";
    public final static String METHOD_ORDERDELIVERY_GET = "dangdang.order.goods.send";  //发货状态回传
    public final static String METHOD_INVOICE_GET = "dangdang.orders.invoice.site.update"; //电子发票地址

    //	public TreeMap<String, String> formparams = new TreeMap<String, String>();
    @Value("${dangdang.url}")
    private String serverUrl;
    @Value("${dangdang.appKey}")
    private String appKey;
    @Value("${dangdang.appSecret}")
    private String appSecret;
    @Value("${dangdang.session}")
    private String session;


    @Override
    public String getOrderByOrderId(String orderId) {
        TreeMap<String, String> formparams = new TreeMap<String, String>();
        formparams.put("method", METHOD_ORDER_DETAIL_GET);
        formparams.put("o", orderId);
        return excuteMehtod(formparams);
    }

    public String getOrderById(String orderId) {
        TreeMap<String, String> formparams = new TreeMap<String, String>();
        formparams.put("method", METHOD_ORDERS_GET);
        formparams.put("o", orderId);
        return excuteMehtod(formparams);
    }

    @Override
    public OrdersQueue conventOrdersQueue(String respStr) {
        OrdersQueue queue = null;
        try {
            Document doc = Jsoup.parse(respStr, CHARSET_UTF8);
            Element el = doc.select("response").get(0);
            Element buyerInfo = doc.select("buyerInfo").get(0);
            Element sendGoodsInfo = doc.select("sendGoodsInfo").get(0);
            queue = new OrdersQueue();
            queue.setSourceOrderSn(getElementValue(el, "orderID"));
            StringBuffer sb = new StringBuffer();

            //下单时间 用付款时间代替
            String purchaseDate = getElementValue(el, "paymentDate");
            //最后修改时间
            String lastUpdateDate = getElementValue(el, "lastModifyTime");

            queue.setOrderTime(DateUtil.toParse(purchaseDate, DateUtil.DATE_FORMAT_YYYY_MM_DD_HMS));
            queue.setModifyTime(DateUtil.toParse(lastUpdateDate, DateUtil.DATE_FORMAT_YYYY_MM_DD_HMS));
            String amount = getElementValue(el, "realPaidAmount");
            if (StringUtil.isEmpty(amount)) {
                queue.setOrderTotalPrice(null);
            } else {
                queue.setOrderTotalPrice(new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP));
            }
            String orderstatus = getElementValue(el, "orderState");
            queue.setOrderType(orderstatus);
            //此处只接受101  等待发货的订单，目前只有此状态为待同步
            if (orderstatus.equals("101")) {
                queue.setOrdersState(ORDER_QUEUE_STATA);
            } else {
                queue.setOrdersState(ORDER_FINAL_QUEUE_STATA);
            }
            queue.setSyncTime((System.currentTimeMillis() / 1000));
            queue.setAddTime((System.currentTimeMillis() / 1000));
            queue.setCreator("系统");
            queue.setSyncCount(0);
            queue.setErrorLog("");
            queue.setSource(ShopStockEnum.DDW.getSource());
            sb.append("<orderinfo><orderid>" + getElementValue(el, "orderID") + "</orderid>")
                    .append("<consigneename>" + getElementValue(sendGoodsInfo, "consigneeName") + "</consigneename>")
                    .append("<consigneetel>" + getElementValue(sendGoodsInfo, "consigneeTel") + "</consigneetel>")
                    .append("<consigneemobiletel>" + getElementValue(sendGoodsInfo, "consigneeMobileTel") + "<consigneemobiletel>")
                    .append("<consigneeaddr>" + getElementValue(sendGoodsInfo, "consigneeAddr") + " </consigneeaddr>")
                    .append("<dangdangwarehouseaddr>" + getElementValue(sendGoodsInfo, "DangdangWarehouseAddr") + "</dangdangwarehouseaddr>")
                    .append("<sendgoodsmode>" + getElementValue(sendGoodsInfo, "sendGoodsMode") + "</sendgoodsmode>")
                    .append("<ordermoney>" + getElementValue(buyerInfo, "realPaidAmount") + "</ordermoney>")
                    .append("<ordertimestart>" + getElementValue(el, "paymentDate") + "</ordertimestart>")
                    .append("<lastmodifytime>" + getElementValue(el, "lastModifyTime") + "</lastmodifytime>")
                    .append("<orderstate>" + getElementValue(el, "orderState") + "</orderstate>")
                    .append("<ordestatus>" + getElementValue(el, "orderStatus") + "</ordestatus>")
                    .append("<iscourierreceiptdetail>" + getElementValue(el, "isCourierReceiptDetail") + "</iscourierreceiptdetail>")
                    .append("<outerorderid>" + getElementValue(el, "outerOrderID") + "</outerorderid>")
                    .append("<remark>" + getElementValue(el, "remark") + "</remark>")
                    .append("<label>" + getElementValue(el, "label") + "</label>")
                    .append("<paymentdate>" + getElementValue(el, "paymentDate") + "</paymentdate>")
                    .append("<ordermode>" + getElementValue(el, "orderMode") + "</ordermode>")
                    .append("<ispresale>" + getElementValue(el, "isPresale") + "</ispresale>")
                    .append("<senddate>" + getElementValue(el, "sendDate") + "</senddate>")
                    .append("<originalorderId>" + getElementValue(el, "originalOrderId ") + "</originalorderId>")
                    .append("</orderinfo>");

            queue.setOrderInfo(sb.toString());
        } catch (Exception e) {
            throw new JobException("DangDang解析XML异常");
        }
        return queue;
    }


    /***
     * 执行远程操作
     * @param formparams
     * @return
     */
    private String excuteMehtod(TreeMap<String, String> formparams) {
        String reqData = "";
        try {
            String method = (String) formparams.get("method");
            TreeMap<String, String> params = getSign(method);
            reqData = iteratorMapToString(params);
            formparams.remove("method");
            reqData = reqData + "&" + iteratorMapToString(formparams);
            reqData = reqData.replaceAll(" ", "%20");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String req = this.serverUrl + "?" + reqData;
        String content = super.excuteGet(req);
        log.info("当当网请求报文：" + req);
        log.info("当当网返回报文：" + content);
        return content;
    }

    private TreeMap<String, String> getSign(String method)
            throws IOException {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("app_key", this.appKey);
        params.put("format", "xml");
        params.put("method", method);
        params.put("session", this.session);
        params.put("sign_method", "md5");
        params.put("timestamp", DateUtil.format(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD_HMS));
        params.put("v", "1.0");
        String signature = HttpUtils.getDangDangMSign(params, this.appSecret);
        params.put("sign", signature);
        return params;
    }

    public String iteratorMapToString(TreeMap<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entity : params.entrySet()) {
            sb.append(entity.getKey()).append("=").append(entity.getValue()).append("&");
        }
        String reqStr = sb.toString();
        reqStr = reqStr.substring(0, reqStr.length() - 1);
        return reqStr;
    }

    public String getElementValue(Element el, String cssQuery) {
        return el.select(cssQuery).text();
    }

}
