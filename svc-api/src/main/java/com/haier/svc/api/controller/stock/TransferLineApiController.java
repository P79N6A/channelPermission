package com.haier.svc.api.controller.stock;

import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.service.EisInterfaceDataLogService;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.service.TransferLineService;

/**
 * 向LES开放，用于LES向CBS传递信息
 * 接收LES传回的调拨单费用
 *                       
 * @Filename: TransferLineApiController.java
 * @Version: 1.0
 * @Author: maqiang 马强
 * @Email: mqianger@163.com
 *
 */
@Controller
@RequestMapping("/transfer")
public class TransferLineApiController {
    private static Logger              log = LogManager.getLogger(TransferLineApiController.class);

    @Resource
    private TransferLineService        transferLineService;
    @Resource
    private EisInterfaceDataLogService eisInterfaceDataLogService;

    /**
     * LES传CBS调货费用接口
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping(value = { "/on-audit" }, method = { RequestMethod.POST })
    @ResponseBody
    public String saveTransferFee(Map<String, Object> modelMap, HttpServletRequest request) {
        Long startTime = System.currentTimeMillis();

        Document doc = this.parseXML(request);
        String bstnks = this.getTransferLinNum(doc);

        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(bstnks == null ? "" : bstnks);
        dataLog.setInterfaceCode(EisInterfaceDataLog.INTERFACE_CODE_EXCHANGE_FEE_LES_TO_CBS);
        dataLog.setRequestData(this.getRequestXML(doc));
        dataLog.setRequestTime(new Date());

        List<InvTransferLine> transfers = new ArrayList<InvTransferLine>();
        Map<String, String> errMap = this.checkParam(transfers, doc);
        String flag = "F";
        String msg = null;
        String errMsg = null;
        String errBstnk = "";
        if (errMap.size() > 0) {//参数错误
            errBstnk = errMap.keySet().iterator().next();
            String errInfo = errMap.get(errBstnk);
            if (StringUtil.isEmpty(errBstnk)) {
                log.error("调拨单LES传CBS调货费用接口调用失败:" + errInfo);
            } else {
                log.error("提单号为" + errBstnk + "的调拨单LES传CBS调货费用接口调用失败:" + errInfo);
            }
            msg = errInfo;
            errMsg = errInfo;
        } else {
            ServiceResult<Boolean> result = transferLineService.saveTransferFee(transfers);
            if (!result.getSuccess()) {
                errBstnk = result.getCode();
                log.error("提单号为" + errBstnk + "的调拨单LES传CBS调货费用接口调用失败:" + result.getMessage());
                msg = result.getMessage();
                errMsg = result.getMessage();
            } else {
                flag = "S";
                msg = "商城保存成功！";
            }
        }

        if (errMsg != null) {
            dataLog.setErrorMessage(errMsg);
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
        } else {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
        }
        dataLog.setResponseData(this.getResponseData(errBstnk, flag, msg));
        dataLog.setResponseTime(new Date());
        dataLog.setElapsedTime(System.currentTimeMillis() - startTime);

        try {
            eisInterfaceDataLogService.insert(dataLog);
        } catch (Exception e) {
        }

        modelMap.put("bstnk", errBstnk);
        modelMap.put("flag", flag);
        modelMap.put("msg", msg);
        return dataLog.getResponseData().toString();
    }

    private String getResponseData(String bstnk, String flag, String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append("<RESPONSE><FLAG>").append(flag).append("</FLAG><MSG>").append(msg)
            .append("</MSG><BSTNK>").append(bstnk).append("</BSTNK></RESPONSE>");
        return sb.toString();
    }

    private Map<String, String> checkParam(List<InvTransferLine> transfers, Document document) {
        Map<String, String> map = new HashMap<String, String>();
        if (document == null) {
            map.put("", "XML解析失败");
            return map;
        }
        NodeList tags = document.getElementsByTagName("ITEM");
        if (tags == null || tags.getLength() == 0) {
            map.put("", "XML中没有ITEM节点");
            return map;
        }
        for (int i = 0; i < tags.getLength(); i++) {
            Node nNode = tags.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                String bstnk = this.getTagValue(element, "BSTNK");
                String price = this.getTagValue(element, "PRICE");
                String name = this.getTagValue(element, "NAME");
                String erdat = this.getTagValue(element, "ERDAT");
                String yszq = this.getTagValue(element, "YSZQ");
                String erzet = this.getTagValue(element, "ERZET");
                InvTransferLine transfer = new InvTransferLine();
                this.checkParam(map, transfer, bstnk, price, name, erdat, erzet, yszq);
                if (map.size() > 0) {
                    break;
                }
                transfers.add(transfer);
            }
        }
        return map;
    }

    private String getTransferLinNum(Document document) {
        StringBuffer ret = new StringBuffer();
        if (document != null) {
            NodeList tags = document.getElementsByTagName("ITEM");
            if (tags != null && tags.getLength() > 0) {
                for (int i = 0; i < tags.getLength(); i++) {
                    Node nNode = tags.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) nNode;
                        String bstnk = this.getTagValue(element, "BSTNK");
                        ret.append(bstnk).append(",");
                    }
                }
            }
        }
        if (ret.length() > 0) {
            return ret.substring(0, Math.min(60, ret.length() - 1));
        }
        return null;
    }

    private void checkParam(Map<String, String> map, InvTransferLine transfer, String bstnk,
                            String price, String name, String erdat, String erzet, String yszq) {
        //check bstnk
        if (StringUtil.isEmpty(bstnk, true)) {
            map.put(bstnk, "提单号不能为空");
            return;
        }
        transfer.setLineNum(bstnk.trim());

        //check price
        if (StringUtil.isEmpty(price, true)) {
            map.put(bstnk, "运费不能为空");
            return;
        }
        BigDecimal pri = null;
        try {
            pri = new BigDecimal(price.trim());
        } catch (Exception e) {
            map.put(bstnk, "运费必须为数字");
            return;
        }
        if (pri.compareTo(BigDecimal.ZERO) == -1) {
            map.put(bstnk, "运费不能为负数");
            return;
        }
        if (pri.compareTo(new BigDecimal("99999999.99")) == 1) {
            map.put(bstnk, "运费数额过大");
            return;
        }
        pri = pri.setScale(2, RoundingMode.HALF_UP);
        transfer.setTransferFee(pri);

        BigDecimal haulCycle = null;
        try {
            haulCycle = new BigDecimal(yszq.trim());
        } catch (Exception e) {

        }
        if (haulCycle == null)
            transfer.setHaulCycle(null);
        else
            transfer.setHaulCycle(haulCycle.intValue());

        //check name
        //        if (StringUtil.isEmpty(name, true)) {
        //            return "操作人姓名不能为空";
        //        }
        //        if (name.length() > 45) {
        //            return "操作人姓名过长";
        //        }
        //        transfer.setTransferFeeUser(name.trim());
        if (StringUtil.isEmpty(name, true)) {
            name = "物流";
        }
        name = name.trim();
        if (name.length() > 45) {
            map.put(bstnk, "操作人姓名过长");
            return;
        }
        transfer.setTransferFeeUser(name);

        //check 操作日期 yyyyMMdd
        if (StringUtil.isEmpty(erdat, true)) {
            map.put(bstnk, "操作日期不能为空");
            return;
        }
        erdat = erdat.trim();
        if (erdat.length() != 8) {
            map.put(bstnk, "操作日期格式不正确");
            return;
        }
        try {
            new SimpleDateFormat("yyyyMMdd").parse(erdat);
        } catch (Exception e) {
            map.put(bstnk, "操作日期格式不正确");
            return;
        }

        //check 操作时间 HHmmss
        if (StringUtil.isEmpty(erzet, true)) {
            map.put(bstnk, "操作时间不能为空");
            return;
        }
        erzet = erzet.trim();
        if (erzet.length() != 6) {
            map.put(bstnk, "操作时间格式不正确");
            return;
        }
        try {
            new SimpleDateFormat("HHmmss").parse(erzet);
        } catch (Exception e) {
            map.put(bstnk, "操作时间格式不正确");
            return;
        }
        try {
            transfer
                .setTransferFeeTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(erdat + erzet));
        } catch (Exception e) {
            map.put(bstnk, "操作日期或操作时间格式不正确");
            return;
        }
    }

    private Document parseXML(HttpServletRequest request) {
        Document document = null;
        try {
            InputStream is = request.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            document = builder.parse(is);
        } catch (Exception e) {
            log.error("XML解析失败:" + e);
        }
        return document;
    }

    private String getTagValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList == null) {
            return null;
        }
        Element subElement = (Element) nodeList.item(0);
        return subElement.getTextContent();
    }

    private String getRequestXML(Document doc) {
        String xml = "";
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            xml = writer.toString();
        } catch (Exception e) {
        }
        return xml;
    }

}
