package com.haier.svc.api.controller.pop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import com.haier.logistics.service.OrderRebackService;
import com.haier.logistics.service.EisInterfaceDataLogApiService;
import com.haier.shop.service.InvoicesService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.shop.model.DateFormatUtilNew;
import com.haier.shop.model.OrderProductsNew;

/*
* 作者:张波
* 2017/12/25
* */

@Controller
@RequestMapping("hopReturnController")
public class HopReturnController {
    private static Logger log = LogManager.getLogger(HopReturnController.class);
   private static String INTERFACE_HP_HOP_SHOP_API = "hp_hop_shop_api";
    @Autowired
    private EisInterfaceDataLogApiService eisInterfaceDataLogApiService;
    @Autowired
    private OrderRebackService orderRebackService;
    @Autowired
    private InvoicesService invoicesService;
    /**
     * 接收HP系统回传预约送货时间
     * @param modelMap
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value = { "/reservationGoodsDate"}, method = { RequestMethod.POST })
    public void reservationGoodsDate(Map<String, Object> modelMap, HttpServletRequest request,
                                     HttpServletResponse response){
        response.setContentType("text/xml");//返回的xml文本
        Long startTime = System.currentTimeMillis();
        InputStream inputStream = null;//字节流
       BufferedReader br = null;//缓冲流
        //从流中获取传输数据
        StringBuffer receivedMessage = new StringBuffer();
       String xml = "";
       Document document = null;

        String message = "<receiveFlag>" + EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS
                + "</receiveFlag>";
        List<OrderProductsNew> reservationDates = new ArrayList<OrderProductsNew>();

        try {
            inputStream = request.getInputStream();
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
           while ((line = br.readLine()) != null) {
               receivedMessage.append(line);
           }
            if (StringUtil.isEmpty(receivedMessage.toString())) {
                log.error("HopReturnController.reservationGoodsDate 传输数据为空！");
                message = "<receiveFlag>" + EisInterfaceDataLog.RESPONSE_STATUS_ERROR
                       + "</receiveFlag>";
               this.recordLog(xml,
                        "<message>HopReturnController.reservationGoodsDate 传输数据为空！</message>",
                        System.currentTimeMillis() - startTime, INTERFACE_HP_HOP_SHOP_API);
                responseReturn(receivedMessage.toString(), response);
               return;
            }

            xml = receivedMessage.toString();
           document = this.xmlToDocument(xml);

           if (document == null) {                log.error("HopReturnController.reservationGoodsDate xml转换document对象为空！");
                message = "<receiveFlag>" + EisInterfaceDataLog.RESPONSE_STATUS_ERROR
                        + "</receiveFlag>";
                this.recordLog(
                        xml,
                        "<message>HopReturnController.reservationGoodsDate xml转换document对象为空！</message>",
                        System.currentTimeMillis() - startTime, INTERFACE_HP_HOP_SHOP_API);
                responseReturn(receivedMessage.toString(), response);
                return;
            }

            reservationDates = this.getReservationDateInEntries(document);
            if (reservationDates != null && reservationDates.size() > 0) {
                for (OrderProductsNew orderProducts : reservationDates) {
                    OrderProductsNew orderProduct = orderRebackService
                           .getOrderProductsByCOrderSn(orderProducts.getCOrderSn());
                    if (orderProduct == null) {
                               continue;
                   }
                    //保存预约送货时间
                    orderProduct.setHpReservationDate(orderProducts.getHpReservationDate());
                    boolean flag =orderRebackService.saveHpReservationDateRelation(
                           orderProduct,
                           "HP系统回传预约送货时间：" + DateFormatUtilNew.formatTime(orderProduct.getHpReservationDate()),
                           "HP回传网单数据");//保存订单日志
                   //发送短信
                   if (flag) {
                       orderRebackService.sendSms(orderProduct);
                   }
                   //【接受HP回传接口】每一个网单记录一次日志 xinm 2016-6-14
                    this.recordLog(JsonUtil.toJson(orderProducts), message,
                           System.currentTimeMillis() - startTime, INTERFACE_HP_HOP_SHOP_API,
                           orderProducts.getCOrderSn());
               }
               message = "<receiveFlag>" + EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS
                       + "</receiveFlag>";
            } else {
               message = "<receiveFlag>" + EisInterfaceDataLog.RESPONSE_STATUS_ERROR
                       + "</receiveFlag>";
            }

           this.recordLog(xml, message, System.currentTimeMillis() - startTime,
                    INTERFACE_HP_HOP_SHOP_API);
        } catch (SAXParseException e) {
           this.recordLog(xml, "<message>输入参数解析失败，无法获取传预约送货时间</message>",
                    System.currentTimeMillis() - startTime, INTERFACE_HP_HOP_SHOP_API);
            log.error("reservationGoodsDate:接口发生异常，XML解析失败:", e);
            message = "<receiveFlag>" + EisInterfaceDataLog.RESPONSE_STATUS_ERROR
                    + "</receiveFlag>";
        } catch (Exception e) {
           this.recordLog(xml, "<message>封装数据发生异常，无法预约送货时间</message>", System.currentTimeMillis()
                           - startTime,
                    INTERFACE_HP_HOP_SHOP_API);
           log.error("reservationGoodsDate:封装数据发生异常，无法预约送货时间数据:", e);
            message = "<receiveFlag>" + EisInterfaceDataLog.RESPONSE_STATUS_ERROR
                    + "</receiveFlag>";
       } finally {
            closeIOinput(inputStream, br);
        }

        responseReturn(message, response);
   }
   /**
     * 记录接口日志
    * @param requestData
     * @param responseData
    * @param elapsedTime
     */
   private void recordLog(String requestData, String responseData, Long elapsedTime,
                          String interfaceCode) {
       //记录接口日志
        try {
            EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
            dataLog.setForeignKey("");
           dataLog.setInterfaceCode(interfaceCode);
           dataLog.setRequestData(requestData);
            dataLog.setRequestTime(new Date());
           dataLog.setErrorMessage("");
           dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);

            dataLog.setResponseData(responseData);
           dataLog.setResponseTime(new Date());
           dataLog.setElapsedTime(elapsedTime);
            eisInterfaceDataLogApiService.record(dataLog);
        } catch (Exception e) {
            log.error("recordLog:接口发生异常，接口名称是:" + interfaceCode + "记录接口日志失败:" + e);
       }
   }
    /**
    * 结果返回
     * @param
     * @param
     * @param
     * @param
     */
    private void responseReturn(String message, HttpServletResponse response) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("<processResponse>" + message + "</processResponse>");
            log.error("HopReturnController.responseReturn 返回数据：" + stringBuilder.toString());

            response.getWriter().write(stringBuilder.toString());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            log.error("HopReturnController.responseReturn 返回数据写入失败：", e);
        }
    }
    /**
     * XML字符串转换为document对象
     * @param xml
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private Document xmlToDocument(String xml) throws ParserConfigurationException, SAXException,
            IOException {
        Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(new InputSource(new StringReader(xml)));
        return doc;
    }
    /**
     * 解析XML数据
     * @param document
     * @param
     * @return
     */
    private List<OrderProductsNew> getReservationDateInEntries(Document document){
        if (document == null) {
            return null;
        }
        List<OrderProductsNew> list = new ArrayList<OrderProductsNew>();

        NodeList rootnodeList = document.getElementsByTagName("ns1:reservationDate");
        for (int j = 0; j < rootnodeList.getLength(); j++) {

            Node itemNode = rootnodeList.item(j);
            NodeList nodeList = itemNode.getChildNodes();
            if (nodeList == null) {
                return null;
            }

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    OrderProductsNew orderProducts = new OrderProductsNew();
                    String cOrderSn = this.getTagValue(element, "ns1:cOrderSn");
                    String hpReservationDate = this.getTagValue(element, "ns1:hpReservationDate");
                    Date date;
                    try {
                        if (!StringUtil.isEmpty(hpReservationDate)) {
                            if (hpReservationDate.indexOf("T") != -1) {
                                SimpleDateFormat format = new SimpleDateFormat(
                                        "yyyy-MM-dd'T'HH:mm:ss");
                                date = format.parse(hpReservationDate);
                            } else {
                                SimpleDateFormat format = new SimpleDateFormat(
                                        "yyyy-MM-dd HH:mm:ss");
                                date = format.parse(hpReservationDate);
                            }
                            orderProducts.setHpReservationDate(Integer.parseInt(String.valueOf(date
                                    .getTime() / 1000)));
                        }
                    } catch (ParseException e) {
                        log.error("HopReturnController利用getReservationDateInEntries方法处理预约送货时间异常"
                                + e.getMessage());
                    }
                    orderProducts.setCOrderSn(cOrderSn);
                    list.add(orderProducts);
                }
            }
        }

        return list;
    }
    /**
     * 读取XML节点内容
     * @param element
     * @param tagName
     * @return
     */
    private String getTagValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList == null) {
            return null;
        }
        Element subElement = (Element) nodeList.item(0);
        return subElement == null ? null : subElement.getTextContent();
    }
    /**
     * 记录接口日志
     * @param requestData
     * @param responseData
     * @param elapsedTime
     */
    private void recordLog(String requestData, String responseData, Long elapsedTime,
                           String interfaceCode, String cOrderSn) {
        //记录接口日志
        try {
            EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
            dataLog.setForeignKey(cOrderSn);
            dataLog.setInterfaceCode(interfaceCode);
            dataLog.setRequestData(requestData);
            dataLog.setRequestTime(new Date());
            dataLog.setErrorMessage("");
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setResponseData(responseData);
            dataLog.setResponseTime(new Date());
            dataLog.setElapsedTime(elapsedTime);
            eisInterfaceDataLogApiService.record(dataLog);
        } catch (Exception e) {
            log.error("recordLog:接口发生异常，接口名称是:" + interfaceCode + "记录接口日志失败:" + e);
        }
    }
    /**
     * 关闭流处理
     * @param inputStream
     * @param br
     */
    private void closeIOinput(InputStream inputStream, BufferedReader br) {
        try {
            if (br != null) {
                br.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            log.error("HopReturnController.closeIOinput 流关闭异常：", e);
        }
    }
}
