package com.haier.svc.api.controller.pop;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.haier.logistics.service.OrderInterfaceDataLogService;
import com.haier.logistics.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.OrderInterfaceDataLog;
import com.haier.shop.model.HpSignTimeInterface;



/*
* 作者:张波
* 2017/12/26
*/
@Controller
@RequestMapping("hp")
public class AcceptTimeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptTimeController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderInterfaceDataLogService orderInterfaceDataLogService;
    /**
     * HP回传网点接单，网点出库，用户签收3个时间
     *
     * @Filename: AcceptTimeController.java
     * @Version: 1.0
     *
     */
    @RequestMapping(value = { "/accept_time" }, method = { RequestMethod.POST })
    public String acceptHpTime(Map<String, Object> modelMap, HttpServletRequest request,
                               HttpServletResponse response){
        Document document;
        List<HpSignTimeInterface> entityList = new ArrayList<HpSignTimeInterface>();
        StringBuffer messageBuffer = new StringBuffer();
        String message;
        try {
            long startTime = System.currentTimeMillis();
            InputStream is = request.getInputStream();

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            document = builder.parse(is);
            entityList = parseDoc(document);
            if (entityList == null || entityList.size() == 0) {
                LOGGER.error("HP回传网点接单，网点出库，用户签收数据parseDoc后为空");
                message = this.apiResponse("F", "数据为空", "");
                this.insertDataLog(JsonUtil.toJson(entityList), message,
                        "HP回传网点接单，网点出库，用户签收数据parseDoc后为空");
            } else {
                for (HpSignTimeInterface entity : entityList) {
                    ServiceResult<String> result = orderService.acceptTimeFromHp(entity);
                    String apiResponse = this.apiResponse(result.getSuccess() ? "S" : "F",
                            result.getResult(), String.valueOf(entity.getRowId()));
                    messageBuffer.append(apiResponse);
                    //【接受HP回传接口】每一个网单记录一次日志 xinm 2016-6-14
                    this.insertDataLogSingle(JsonUtil.toJson(entity), apiResponse, entity.getOrderSn());
                }
                this.insertDataLog(JsonUtil.toJson(entityList), messageBuffer.toString(), "");
                message = messageBuffer.toString();
                long endTime = System.currentTimeMillis();
                long time = endTime - startTime;
                LOGGER.info("HP回传网点接单，网点出库，用户签收数据条数:" + entityList.size() + ",总共执行时间:" + time
                        + "毫秒,平均每条" + time / entityList.size() + "毫秒");
            }
        } catch (SAXParseException e) {
            message = this.apiResponse("F", "解析xml发生异常", "");
            LOGGER.error("accept hp time error, Parse xml Exception", e);
            this.insertDataLog(JsonUtil.toJson(entityList), message,
                    "accept hp time error, Parse xml Exception" + e.getMessage());
        } catch (Exception ex) {
            message = this.apiResponse("F", "接收时间发生异常", "");
            LOGGER.error("accept hp time error", ex);
            this.insertDataLog(JsonUtil.toJson(entityList), message,
                    "accept hp time error" + ex.getMessage());
        }

        modelMap.put("message", message);
        return "/hop/base/acceptTimeRes";
    }
    private List<HpSignTimeInterface> parseDoc(Document document){
        if (document == null) {
            return null;
        }
        NodeList nodeList = document.getElementsByTagName("ITEM");
        if (nodeList == null) {
            return null;
        }
        List<HpSignTimeInterface> paramList = new ArrayList<HpSignTimeInterface>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            HpSignTimeInterface hpSignTimeInterface = new HpSignTimeInterface();
            Node nNode = nodeList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String orderSn = this.getTagValue(element, "ORDER_NO");//网单号
                String statusTime = this.getTagValue(element, "STATUS_TIME");
                String status = this.getTagValue(element, "STATUS");//分辨是用户签收还是网店接单 网点出库状态
                String rowId = this.getTagValue(element, "ROWID");

                String tbNo = this.getTagValue(element, "TBNO");
                String wwwMark = this.getTagValue(element, "WwwMark");
                String lbxNo = this.getTagValue(element, "LBXNO");
                String sku = this.getTagValue(element, "SKU");
                String oprName = this.getTagValue(element, "OPR_NAME");
                String oprType = this.getTagValue(element, "OPR_TYPE");

                hpSignTimeInterface.setOrderSn(orderSn);
                hpSignTimeInterface.setStatusTime(statusTime);
                hpSignTimeInterface.setStatus(Integer.parseInt(status.trim()));
                hpSignTimeInterface.setRowId( StringUtil.isEmpty(rowId) ? null : rowId.trim());
                hpSignTimeInterface.setTbNo(tbNo);
                hpSignTimeInterface.setWwwMark(wwwMark);
                hpSignTimeInterface.setLbxNo(lbxNo);
                hpSignTimeInterface.setSku(sku);
                hpSignTimeInterface.setOprName(oprName);
                hpSignTimeInterface.setOprType(oprType);

                paramList.add(hpSignTimeInterface);
            }

        }
        return paramList;
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
    public String apiResponse(String errorCode, String msg, String row) {
        return "<ITEM><FLAG>" + errorCode + "</FLAG><MSG>" + msg + "</MSG><ROWID>" + row
                + "</ROWID></ITEM>";
    }
    private void insertDataLogSingle(String requestData, String responseDate, String foreignKey) {
        OrderInterfaceDataLog dataLog = new OrderInterfaceDataLog();
        dataLog.setForeignKey(foreignKey);
        dataLog.setInterfaceCode("receive_hpTime_sync");
        dataLog.setRequestData(requestData);
        dataLog.setRequestTime(new Date());
        dataLog.setErrorMessage("");
        dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
        dataLog.setResponseData(responseDate);
        dataLog.setResponseTime(new Date());
        dataLog.setElapsedTime(0L);
        orderInterfaceDataLogService.record(dataLog);
    }
    private void insertDataLog(String requestData, String responseDate, String errMessage) {
        OrderInterfaceDataLog dataLog = new OrderInterfaceDataLog();
        dataLog.setForeignKey("");
        dataLog.setInterfaceCode("receive_hpTime_sync");
        dataLog.setRequestData(requestData);
        dataLog.setRequestTime(new Date());
        dataLog.setErrorMessage(errMessage);
        dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
        dataLog.setResponseData(responseDate);
        dataLog.setResponseTime(new Date());
        dataLog.setElapsedTime(0L);
        orderInterfaceDataLogService.record(dataLog);
    }
}
