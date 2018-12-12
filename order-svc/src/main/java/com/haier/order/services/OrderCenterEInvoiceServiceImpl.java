package com.haier.order.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.haier.order.helper.XmlSignatureHelper;
import com.haier.order.util.HelpUtils;
import com.haier.order.util.HttpServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.shop.model.InvoiceData2HP;
import org.springframework.web.util.HtmlUtils;

@Service
public class OrderCenterEInvoiceServiceImpl {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(OrderCenterEInvoiceServiceImpl.class);

    /**
     * 证书路径
     */
    private String cerPath = "/einvoice/ChiEinv.cer";
    /**
     * key路径
     */
    private String scriptPath = "/einvoice/Ehaier.keystore";

    /**
     * 电子发票查询url
     */
    private String eInvoiceQryUrl = "http://www.chinaeinv.com/cx/GetInvoiceService";
    /**
     * 电子发票开票或作废url
     */
    private String eInvoiceBillUrl = "http://www.chinaeinv.com/kp/BillingService";

    /**
     * key用户id
     */
    private String keyStoreAbner = "q_ehaier_cde";
    /**
     * key密码
     */
    private String keyStorePassWord = "a4q_8uy";

    private byte[] cerData;

    private byte[] scriptData;
    @Autowired
    private HelpUtils help;

    public void setHelp(HelpUtils help) {
        this.help = help;
    }

    public String geteInvoiceQryUrl() {
        return eInvoiceQryUrl;
    }

    public String getCerPath() {
        return cerPath;
    }

    public void setCerPath(String cerPath) {
        this.cerPath = cerPath;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    public void seteInvoiceQryUrl(String eInvoiceQryUrl) {
        this.eInvoiceQryUrl = eInvoiceQryUrl;
    }

    public String geteInvoiceBillUrl() {
        return eInvoiceBillUrl;
    }

    public void seteInvoiceBillUrl(String eInvoiceBillUrl) {
        this.eInvoiceBillUrl = eInvoiceBillUrl;
    }

    public String getKeyStoreAbner() {
        return keyStoreAbner;
    }

    public void setKeyStoreAbner(String keyStoreAbner) {
        this.keyStoreAbner = keyStoreAbner;
    }

    public String getKeyStorePassWord() {
        return keyStorePassWord;
    }

    public void setKeyStorePassWord(String keyStorePassWord) {
        this.keyStorePassWord = keyStorePassWord;
    }

    //升级之前的1.1版本的[查询发票]，不再使用
//    @Deprecated
//    @Override
    public ServiceResult<String> queryInvoice(String queryXml, String orderProductId) {
//        ServiceResult<String> result = new ServiceResult<String>();
//        try {
//            XmlSignatureHelper xh = new XmlSignatureHelper();
//            //查询电子发票
//            String xml = constructXml(queryXml, xh, 1);
//            result = HttpServiceUtil.executeService(orderProductId,
//                EisInterfaceDataLog.INTERFACE_CODE_EINVOICE_QRY, xml, eInvoiceQryUrl);
//            String response = result.getResult();
//            if (StringUtils.isBlank(response)) {
//                return result;
//            }
//            //html转义解析
//            response = HtmlUtils.htmlUnescape(response);
//            //读取响应xml
//            result.setResult(readResult(response, xh));
//        } catch (Exception e) {
//            result.setSuccess(false);
//            result.setMessage("查询电子发票出错:" + e.getMessage());
//            log.error("查询电子发票出错:" + e);
//        }
//        return result;
        return null;
    }

    //升级之前的1.1版本的[创建发票]，不再使用
    @Deprecated
    
    public ServiceResult<String> createInvoice(String invoiceXml, String orderProductId) {
        ServiceResult<String> result = new ServiceResult<String>();
        try {
            XmlSignatureHelper xh = new XmlSignatureHelper();
            //推送电子发票
            String xml = constructXml(invoiceXml, xh, 2);
            result = HttpServiceUtil.executeService(orderProductId,
                    EisInterfaceDataLog.INTERFACE_CODE_IENVOICE_TO_BILL, xml, eInvoiceBillUrl);
            String response = result.getResult();
            if (StringUtils.isBlank(response)) {
                return result;
            }
            //html转义解析
            response = HtmlUtils.htmlUnescape(response);
            //读取响应xml
            result.setResult(readResult(response, xh));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("电子发票开票出错:" + e.getMessage());
            log.error("电子发票开票出错:", e);
        }
        return result;
    }

    //升级之前的1.1版本的[作废发票]，不再使用
    @Deprecated
    
    public ServiceResult<String> invalidInvoice(String invoiceXml, String orderProductId) {
        ServiceResult<String> result = new ServiceResult<String>();
        try {
            XmlSignatureHelper xh = new XmlSignatureHelper();
            //作废电子发票
            String xml = constructXml(invoiceXml, xh, 3);
            result = HttpServiceUtil.executeService(orderProductId,
                    EisInterfaceDataLog.INTERFACE_CODE_IENVOICE_TO_BILL, xml, eInvoiceBillUrl);
            String response = result.getResult();
            if (StringUtils.isBlank(response)) {
                return result;
            }
            //html转义解析
            response = HtmlUtils.htmlUnescape(response);
            //读取响应xml
            result.setResult(readResult(response, xh));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("电子发票作废出错:" + e.getMessage());
            log.error("电子发票作废出错:" + e);
        }
        return result;
    }

    private byte[] getCerDataByPath() {
        if (null == cerData) {
            FileInputStream fis = null;
            ByteArrayOutputStream bais = null;
            try {
                fis = new FileInputStream(getPathByName(cerPath));
                bais = new ByteArrayOutputStream();
                int b;
                while ((b = fis.read()) > -1) {
                    bais.write(b);
                }
                cerData = bais.toByteArray();
            } catch (Exception e) {
                log.error("读取电子发票cer证书文件出错:" + e);
            } finally {
                try {
                    if (null != fis) {
                        fis.close();
                    }
                    if (null != bais) {
                        bais.close();
                    }
                } catch (IOException e) {
                    log.error("关闭读取电子发票cer证书文件流出错:" + e);
                }
            }
        }
        return cerData;
    }

    private byte[] getScriptDataByPath() {
        if (null == scriptData) {
            FileInputStream fis = null;
            ByteArrayOutputStream bais = null;
            try {
                File file = new File(scriptPath);
                if (!file.exists()) {//兼容本地测试
                    fis = new FileInputStream(getPathByName(scriptPath));
                } else {
                    fis = new FileInputStream(file);
                }
                bais = new ByteArrayOutputStream();
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = fis.read(b)) > -1) {
                    bais.write(b, 0, len);
                }
                scriptData = bais.toByteArray();
            } catch (Exception e) {
                log.error("读取电子发票script文件出错:" + e);
            } finally {
                try {
                    if (null != fis) {
                        fis.close();
                    }
                    if (null != bais) {
                        bais.close();
                    }
                } catch (IOException e) {
                    log.error("关闭读取电子发票script文件流出错:" + e);
                }
            }
        }
        return scriptData;
    }

    private String constructXml(String xml, XmlSignatureHelper xh, int type) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:fac=\"http://facade.invoice.einv.mpc.com/\">");
        sb.append("<soapenv:Header/><soapenv:Body>");
        //1查询，2开票，3作废
        switch (type) {
            case 1:
                sb.append("<fac:queryInvoiceByOrderNo>");
                break;
            case 2:
                sb.append("<fac:generateInvoice>");
                break;
            case 3:
                sb.append("<fac:invalidateInvoice>");
                break;
        }
        sb.append("<!--Optional:--><arg0><![CDATA[");
        sb.append(xh.signature(new ByteArrayInputStream(xml.getBytes("UTF-8")),
                getScriptDataByPath(), keyStoreAbner, keyStorePassWord));
        sb.append("]]></arg0>");
        //1查询，2开票，3作废
        switch (type) {
            case 1:
                sb.append("</fac:queryInvoiceByOrderNo>");
                break;
            case 2:
                sb.append("</fac:generateInvoice>");
                break;
            case 3:
                sb.append("</fac:invalidateInvoice>");
                break;
        }
        sb.append("</soapenv:Body></soapenv:Envelope>");
        return sb.toString();
    }

    private String readResult(String response, XmlSignatureHelper xh) throws Exception {
        ByteArrayInputStream bais = null;
        String resultXml = null;
        try {
            int start = response.indexOf("<return>") + 8;
            int end = response.indexOf("</return>");
            //取出soap信息里return里的xml内容
            resultXml = response.substring(start, end);

            bais = new ByteArrayInputStream(resultXml.getBytes("utf-8"));

            //测试环境soapui模拟先不验证密钥
            if (xh.validate(bais, getCerDataByPath())) {
                return resultXml;
            } else {
                return null;
            }
        } finally {
            if (null != bais) {
                bais.close();
            }
        }
    }

    private String getPathByName(String name) {
        return this.getClass().getResource(name).getPath();
    }

    /**
     * 电子发票开票成功后发HP
     *
     * @param invoiceData2HP
     * @return
     */
//    @Override
    public ServiceResult<Boolean> invoiceInfoToHpSys(final InvoiceData2HP invoiceData2HP) {
        return null;
//        final ServiceResult<Boolean> result = new ServiceResult<Boolean>();
//        if (invoiceData2HP == null) {
//            result.setSuccess(false);
//            result.setMessage("传参不能为空");
//        }
//        String inputTypeJson = JsonUtil.toJson(invoiceData2HP);
//        WriteLogProxy.writeLog(invoiceData2HP.getOrderNo(),
//            EisInterfaceDataLog.INTERFACE_CODE_INVOICE_TO_HP, inputTypeJson, new IExecute() {
//                @Override
////                public String execute() throws Exception {
//                    try {
//                        URL url = new URL(getWsdlPath("TransInvoiceInfoFromEhaierToHP.wsdl"));
////                        TransInvoiceInfoFromEhaierToHP_Service service = new TransInvoiceInfoFromEhaierToHP_Service(
////                            url);
////                        TransInvoiceInfoFromEhaierToHP soap = service
////                            .getTransInvoiceInfoFromEhaierToHPSOAP();
////
////                        WoWdOrderFp woWdOrderFp = convertInvoiceData2HPToWoWdOrderFp(invoiceData2HP);
////                        List<WoWdOrderFp> woWdOrderFpList = new ArrayList<WoWdOrderFp>();
////                        woWdOrderFpList.add(woWdOrderFp);
////
////                        Holder<String> flag = new Holder<String>();
////                        Holder<String> message = new Holder<String>();
////                        soap.transInvoiceInfoFromEhaierToHP(woWdOrderFpList, flag, message);
//                        //返回结果
////                        if (flag == null || flag.value == null) {
////                            result.setSuccess(false);
////                            result.setMessage(message == null || message.value == null ? "响应数据为空"
////                                : message.value);
////                            result.setResult(null);
////                            return message == null || message.value == null ? "" : message.value;
////                        } else if (flag.value.equals("S") || flag.value.equals("1")
////                                   || (message != null && "插入成功".equals(message.value))) {
////                            result.setSuccess(true);
////                            result.setMessage(message == null || message.value == null ? "操作成功"
////                                : message.value);
////                            result.setResult(true);
////                            return flag.value + ":" + message == null || message.value == null ? ""
////                                : message.value;
////                        } else {
////                            result.setSuccess(false);
////                            result.setMessage(message == null || message.value == null ? "响应失败"
////                                : message.value);
////                            result.setResult(false);
////                            return flag.value + ":" + message == null || message.value == null ? ""
////                                : message.value;
////                        }
//                    } catch (Exception e) {
//                        log.error("开票发送HP数据时,发生未知异常", e);
//                        result.setMessage("开票发送HP数据时,发生未知异常");
//                        result.setSuccess(false);
//                        result.setResult(null);
//                        throw e;
//                    }
//                }
//            });
//        return result;
    }

    /**
     * 将发票实体转化为调用创建发票wsdl时的WoWdOrderFp对象
     *
     * @param invoiceData2HP
     * @return
     */
//    private WoWdOrderFp convertInvoiceData2HPToWoWdOrderFp(InvoiceData2HP invoiceData2HP) {\
    private String convertInvoiceData2HPToWoWdOrderFp(InvoiceData2HP invoiceData2HP) {
//        WoWdOrderFp inputType = new WoWdOrderFp();
//        inputType.setOrderNo(invoiceData2HP.getOrderNo());
//        inputType.setFpSkh(invoiceData2HP.getFpSkh());
//        inputType.setFpNo(invoiceData2HP.getFpNo());
//        inputType.setFpPrice(invoiceData2HP.getFpPrice());
//        inputType.setFpDate(getDateToXmlCalendar(invoiceData2HP.getFpDate()));
//        inputType.setFpCount(invoiceData2HP.getFpCount());
//        inputType.setFpType(invoiceData2HP.getFpType());
//        inputType.setFpStatus(invoiceData2HP.getFpStatus());
//        inputType.setProcFlag(invoiceData2HP.getProcFlag());
//        inputType.setProcRemark(invoiceData2HP.getProcRemark());
//        inputType.setCreatedDate(getDateToXmlCalendar(invoiceData2HP.getCreatedDate()));
//        return inputType;
        return "注视吊因为报错";
    }

    private XMLGregorianCalendar getDateToXmlCalendar(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar xgcDate = null;
        try {
            xgcDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return xgcDate;
    }

    private String getWsdlPath(String wsdlFile) {
        //        String path = "file:" + this.getClass().getResource("/wsdl/" + wsdlFile).getPath();
        //        if (log.isDebugEnabled())
        //            log.debug("wsdl path:" + path);
        //        return path;
        return "";//help.getWsdlPath(wsdlFile);
    }

}
