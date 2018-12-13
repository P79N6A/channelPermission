package com.haier.svc.api.controller.pop;

import com.haier.common.util.ConvertUtil;
import com.haier.purchase.data.service.PurchaseItemService;
import com.haier.purchase.data.service.PurchaseT2OrderQueryService;
import com.haier.stock.service.InvTransferLineService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.shop.model.PropertiesConst;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.VomReceivedQueue;

import com.haier.svc.api.controller.util.WebUtil;
import com.haier.svc.api.service.pop.VOMInformationReceiverServiceImpl;



/**
 * 吴坤洋
 * 接受VOM的消息
 * Created by 钊 on 2017/12/22.
 */
@Controller
@RequestMapping("vom")
public class VOMInformationReceiverController {
    private static final Logger        LOGGER     = LoggerFactory
            .getLogger(VOMInformationReceiverController.class);
    @Autowired
    private VOMInformationReceiverServiceImpl vomInformationReceiverServiceImpl;
    @Autowired
    private InvTransferLineService invTransferLineService;
    @Autowired
    private PurchaseItemService purchaseItemService;
    @Autowired
    private PurchaseT2OrderQueryService purchaseT2OrderQueryService;
    @Value("${secretKey}")
    public  String secretKey;
    @Value("${ketValue}")
    public  String ketValue;
    @RequestMapping(value = {"/receive"}, method = { RequestMethod.POST })
    void receiveInformationFromVOM(HttpServletRequest request, HttpServletResponse response) {

        String receivedMessage = "";
        InputStream inputStream = null;
        BufferedReader br = null;
        try {
            inputStream = request.getInputStream();
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                receivedMessage += line;
            }
            receivedMessage = URLDecoder.decode(receivedMessage, "UTF-8");//解码(报文编码了必须进行解码)
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(receivedMessage);
            }
        } catch (Exception ex) {
            LOGGER.error("receiveInformationFromVOM error", ex);
        } finally {
            if (br != null)
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                    br = null;
                }
            if (inputStream != null)
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                    inputStream = null;
                }
        }
        response.setContentType("text/xml; charset=utf-8");//设置浏览器的读取方式
        //将参数变成Map
        Map<String, String> parseParams = parseParams(receivedMessage);
        if (parseParams.isEmpty()) {
            responseFailed(response, "解析参数失败");
            return;
        }
        String outCode = parseParams.get("outcode");
        if (StringUtil.isEmpty(outCode) || "null".equalsIgnoreCase(outCode)) {
            responseFailed(response, "outcode must not be null");
            return;
        }
        String notifyTime = parseParams.get("notifytime");
        Date notifyTimeDate = StringUtil.isEmpty(notifyTime) ? new Date()
            : com.haier.common.util.DateUtil.parse(notifyTime, "yyyy-MM-dd HH:mm:ss");

        String buType = parseParams.get("butype");
        if (StringUtil.isEmpty(buType) || "null".equalsIgnoreCase(buType)) {
            responseFailed(response, "buType must not be null");
            return;
        }
        String type = parseParams.get("type");
        if (StringUtil.isEmpty(type) || "null".equalsIgnoreCase(type)) {
            responseFailed(response, "type must not be null");
            return;
        }

        String source = parseParams.get("source");
        if (StringUtil.isEmpty(source)) {
            responseFailed(response, "source must not be null");
            return;
        }

        String sign = parseParams.get("sign");
        String content = parseParams.get("content");

        try {
            System.out.println(secretKey);
           content = decrypt(content,secretKey);
        } catch (Exception e) {
            responseFailed(response, "AES解密失败");
            return;
        }

        try {
            if (content == null) {
                content = "";
            }
            if ("<Return/>".equalsIgnoreCase(content)) {
                responseFailed(response, "content must not be <Return/>");
                return;
            }
            if (content.length() < 20) {
                responseFailed(response, "content information error");
                return;
            }
            //AES解密
            String signed = getMd5(content);
           if (!sign.equals(signed)) {
               WebUtil.saveAccessLog(request, "签名不一致：" + "sign=" + sign + "，encode=" + signed);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "SIGN NOT MATCHED");
               return;
            }
        } catch (Exception e) {
            WebUtil.saveAccessLog(request, "校验SIGN失败：" + e.getMessage());
            responseFailed(response, "校验SIGN失败：" + e.getMessage());
            return;
        }
        
        try{
           VomReceivedQueue vomReceivedQueue = new VomReceivedQueue();
        	 vomReceivedQueue.setBuType(buType);
        	 vomReceivedQueue.setNotifyTime(notifyTimeDate);
        	 vomReceivedQueue.setSign(sign);
        	 vomReceivedQueue.setSource(source);
        	 vomReceivedQueue.setContent(content);
          //2018-9-4校验订单是否是采购或者调拨
          //如果是采购和调拨则校验是否存在于3W-CBS系统 如果不存在则存入表中不处理
          Integer status = null;
          Document document = DocumentHelper.parseText(content);
          document.setXMLEncoding("utf-8");
          Element rootElement = document.getRootElement();
          String orderNo = rootElement.elementTextTrim("orderno");
          int orderType = ConvertUtil.toInt(rootElement.elementTextTrim("ordertype"),0);
          //如果是采购
          if (orderType == 1) {
            //普通采购 VOM回传单号为85开头D结尾
            //2018-09-10 按照旧系统 可能有SI单号或者PC单号
              //2.CBS采购，根据SI单或者85DN单号获取关联的采购PO单获取渠道
              //85D单号去掉D
              //PC6000190000008,统帅金立PC单
              Integer purchaseT2 = null;
              String pCOrderSn = orderNo.matches("8.+(D.*)?")
                      ? orderNo.substring(0, orderNo.length() - 1)
                      : orderNo.matches("(SI).+") ? orderNo
                      : orderNo.matches("(PC).+") ? orderNo : null;
              if (pCOrderSn != null) {
                  purchaseT2 = purchaseT2OrderQueryService.getByOrderId(pCOrderSn);
              }
            //3PL采购
            int purchaseItem = purchaseItemService.getByPoItemNo(orderNo);
            //如果存在
            if (purchaseT2 > 0 || purchaseItem > 0) {
              status = VomReceivedQueue.STATUS_NEW;
            //不存在
            } else {
              status = VomReceivedQueue.STATUS_NO;
            }
          //如果是调拨
          } else if (orderType == 6){
            //查询是否存在
            int invTransferLine = invTransferLineService.getByLineNum(orderNo);
            //如果存在
            if (invTransferLine > 0) {
              status = VomReceivedQueue.STATUS_NEW;
            //不存在
            } else {
              status = VomReceivedQueue.STATUS_NO;
            }
          //其他情况
          } else {
             status = VomReceivedQueue.STATUS_NEW;
          }
        	 vomReceivedQueue.setStatus(status);
        	 vomReceivedQueue.setType(type);
        	 vomReceivedQueue.setOutCode(outCode);
        	 
        	 ServiceResult<Boolean> result = vomInformationReceiverServiceImpl.addReceivedInformation(vomReceivedQueue);
        	 if (result == null) {
        		 insertDataLog(receivedMessage, outCode, "F[调用eisVOMService返回为null]");
        		 responseFailed(response, "调用eisVOMService返回为null");
        		 return;
        	 }
        	 if (!result.getSuccess() || !result.getResult()) {
        		 insertDataLog(receivedMessage, outCode, result.getMessage());
        		 responseFailed(response, result.getMessage());
        		 return;
        	 }
         }catch(Exception e){
        	 insertDataLog(receivedMessage, outCode, "调用eisVOMService失败" + e.getMessage());
        	 responseFailed(response, "调用eisVOMService失败" + e.getMessage());
             return;
         }
        insertDataLog(receivedMessage, outCode, "S");
        responseSuccess(response);
    }
    
    
    private Map<String, String> parseParams(String str) {
        Map<String, String> params = new HashMap<String, String>();
        if (str != null) {
            String key;
            String value;
            for (String s : str.split("&")) {
                if (!s.contains("=")) {
                    continue;
                }
                key = s.substring(0, s.indexOf("="));
                value = s.substring(s.indexOf("=") + 1);
                params.put(key, value);
            }
        }
        return params;
    }
	    private void responseFailed(HttpServletResponse response, String msg) {
	        StringBuilder stringBuilder = new StringBuilder();
	        stringBuilder.append("<request>");
	        stringBuilder.append("<flag>").append("F").append("</flag>");
	        stringBuilder.append("<msg>").append(msg).append("</msg>");
	        stringBuilder.append("</request>");
	        try {
	            response.getWriter().write(stringBuilder.toString());
	            response.getWriter().flush();
	            response.getWriter().close();
	        } catch (IOException e) {
	            LOGGER.error("回执VOM请求失败：", e);
	        }
	    }
	    private String getMd5(String content) {
	        System.out.println(ketValue);
	        return Base64.encodeBase64String(DigestUtils.md5Hex(content +ketValue).getBytes());
	    }
	    
	    private void insertDataLog(String message, String foreignKey, String returnMessage) { 
	        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
	        dataLog.setForeignKey(foreignKey);
	        dataLog.setInterfaceCode("receive_vom_message");
	        dataLog.setRequestData(message);
	        dataLog.setRequestTime(new Date());
	        dataLog.setErrorMessage("");
	        dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
	        dataLog.setResponseData(returnMessage);
	        dataLog.setResponseTime(new Date());
	        dataLog.setElapsedTime(0L);
	        vomInformationReceiverServiceImpl.record(dataLog);
	    }
	    
	    private void responseSuccess(HttpServletResponse response) {
	        StringBuilder stringBuilder = new StringBuilder();
	        stringBuilder.append("<request>");
	        stringBuilder.append("<flag>").append("T").append("</flag>");
	        stringBuilder.append("<msg>").append("成功").append("</msg>");
	        stringBuilder.append("</request>");
	        try {
	            response.getWriter().write(stringBuilder.toString());
	            response.getWriter().flush();
	            response.getWriter().close();
	        } catch (IOException e) {
	            LOGGER.error("回执VOM请求失败：", e);
	        }
	    }
	    private static void checkSKey(String sKey) {
	        if (sKey == null) {
	            throw new IllegalArgumentException("sKey不能为空");
	        }
	        if (sKey.length() != 16) {
	            throw new IllegalArgumentException("sKey必须为16位");
	        }
	    }
        public String  SecretKey(){
            return vomInformationReceiverServiceImpl.getProperties(PropertiesConst.PRO_VOM_KEY);
	    }
	    public String KetValue(){
	    	return vomInformationReceiverServiceImpl.getProperties(PropertiesConst.PRO_VOM_VALUE);
	    }

    public static String decrypt(String ssrc, String skey) throws Exception {
        try {
            // 判断Key是否正确
            if (skey == null) {
                LOGGER.info("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (skey.length() != 16) {
                LOGGER.info("Key长度不是16位");
                return null;
            }
            byte[] raw = skey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(ssrc);// 先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                LOGGER.info(e.toString());
                return null;
            }
        } catch (Exception ex) {
            LOGGER.info(ex.toString());
            return null;
        }
    }
}