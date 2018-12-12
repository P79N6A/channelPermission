package com.haier.stock.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.IExecute;
import com.haier.stock.service.StockCenterEInvoiceV2Service;
import com.haier.stock.services.Helper.XmlSignatureHelper;
import com.haier.stock.util.HelpUtils;
@Service
public class StockCenterEInvoiceV2ServiceImpl implements StockCenterEInvoiceV2Service {

    private Logger   logger           = LoggerFactory.getLogger(StockCenterEInvoiceV2ServiceImpl.class);

    /**
     * 证书路径
     */
    private String   cerPath          = "/einvoice/abnerca_signed_test.cer";
    /**
     * key路径
     */
    private String   scriptPath       = "/einvoice/ScriptX_test.keystore";

    /**
     * key用户id
     */
    private String   keyStoreAbner    = "192.168.70.212";
    /**
     * key密码
     */
    private String   keyStorePassWord = "123456";

    private byte[]   cerData;
    private byte[]   scriptData;
    @Autowired
    private HelpUtils help;

    public void setHelp(HelpUtils help) {
        this.help = help;
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

//    private String getWsdlPath(String wsdlFile) {
    	private void getWsdlPath(String wsdlFile) {
        //        String path = "file:" + this.getClass().getResource("/wsdl/" + wsdlFile).getPath();
        //        if (logger.isDebugEnabled())
        //            logger.debug("wsdl path:" + path);
        //        return path;
//        return help.getWsdlPath(wsdlFile);
    }

    /**
     * 查询发票
     * @param foreignKey
     * @param pushData
     * @return
     * @see com.haier.StockCenterEInvoiceV2Service.base.service.EInvoiceV2Service#queryInvoice(java.lang.String, com.haier.cbs.base.entity.eai.InvoiceToSAPzsds0005)
     */
    @Override
    public ServiceResult<String> queryInvoice(String foreignKey, final String pushData) {
        final ServiceResult<String> result = new ServiceResult<String>();

        if (pushData == null) {
            logger.error("pushData为空");
            result.setMessage("pushData为空");
            result.setSuccess(false);
            result.setResult(null);
            return result;
        }
        WriteLogProxy.writeLog(foreignKey, EisInterfaceDataLog.INTERFACE_CODE_EINVOICE_QRY,
            pushData, new IExecute() {
                @Override
                public String execute() throws Exception {
                    String response = "";
                    try {
//                        URL url = new URL(getWsdlPath("InvoiceApi.wsdl"));
//                        //组织调用传入的input
//
//                        V20FacadeImplService service = new V20FacadeImplService(url);
//                        V20Facade soap = service.getInboundWebServicesTicketSaServiceSoap();
//
//                        XmlSignatureHelper xh = new XmlSignatureHelper();
//                        String xml = constructXml(pushData, xh);
//
//                        response = soap.cx(xml);
//
//                        //html转义解析
//                        //                        response = HtmlUtils.htmlUnescape(response);
//                        //读取响应xml
//                        result.setResult(readResult(response, xh));

                    } catch (Exception e) {
                        logger.error("电子发票查询，发生未知异常", e);
                        result.setMessage(e.getMessage());
                        result.setSuccess(false);
                        result.setResult(null);
                    }
                    return response;
                }
            });
        return result;
    }

    /**
     * 创建发票
     * @param foreignKey
     * @param pushData
     * @return
     * @see com.haier.StockCenterEInvoiceV2Service.base.service.EInvoiceV2Service#createInvoice(java.lang.String, com.haier.cbs.base.entity.eai.InvoiceToSAPzsds0005)
     */
    @Override
    public ServiceResult<String> createInvoice(String foreignKey, final String pushData) {
        final ServiceResult<String> result = new ServiceResult<String>();

        if (pushData == null) {
            logger.error("pushData为空");
            result.setMessage("pushData为空");
            result.setSuccess(false);
            result.setResult(null);
            return result;
        }
        WriteLogProxy.writeLog(foreignKey, EisInterfaceDataLog.INTERFACE_CODE_IENVOICE_TO_BILL,
            pushData, new IExecute() {
                @Override
                public String execute() throws Exception {
                    String response = "";
                    try {
//                        URL url = new URL(getWsdlPath("InvoiceApi.wsdl"));
//                        //组织调用传入的input
//
//                        V20FacadeImplService service = new V20FacadeImplService(url);
//                        V20Facade soap = service.getInboundWebServicesTicketSaServiceSoap();
//
//                        XmlSignatureHelper xh = new XmlSignatureHelper();
//                        String xml = constructXml(pushData, xh);
//
//                        response = soap.kp(xml);
//
//                        //html转义解析
//                        //                        response = HtmlUtils.htmlUnescape(response);
//                        //读取响应xml
//                        result.setResult(readResult(response, xh));

                    } catch (Exception e) {
                        logger.error("电子发票开票，发生未知异常", e);
                        result.setMessage(e.getMessage());
                        result.setSuccess(false);
                        result.setResult(null);
                    }
                    return response;
                }
            });
        return result;

    }

    /**
     * 作废发票
     * @param foreignKey
     * @param pushData
     * @return
     * @see com.haier.StockCenterEInvoiceV2Service.base.service.EInvoiceV2Service#invalidInvoice(java.lang.String, com.haier.cbs.base.entity.eai.InvoiceToSAPzsds0005)
     */
    @Override
    public ServiceResult<String> invalidInvoice(String foreignKey, final String pushData) {
        final ServiceResult<String> result = new ServiceResult<String>();

        if (pushData == null) {
            logger.error("pushData为空");
            result.setMessage("pushData为空");
            result.setSuccess(false);
            result.setResult(null);
            return result;
        }
        WriteLogProxy.writeLog(foreignKey, EisInterfaceDataLog.INTERFACE_CODE_IENVOICE_TO_BILL,
            pushData, new IExecute() {
                @Override
                public String execute() throws Exception {
                    String response = "";
                    try {
//                        URL url = new URL(getWsdlPath("InvoiceApi.wsdl"));
//                        //组织调用传入的input
//
//                        V20FacadeImplService service = new V20FacadeImplService(url);
//                        V20Facade soap = service.getInboundWebServicesTicketSaServiceSoap();
//
//                        XmlSignatureHelper xh = new XmlSignatureHelper();
//                        String xml = constructXml(pushData, xh);
//
//                        response = soap.ch(xml);
//
//                        //html转义解析
//                        //                        response = HtmlUtils.htmlUnescape(response);
//                        //读取响应xml
//                        result.setResult(readResult(response, xh));

                    } catch (Exception e) {
                        logger.error("电子发票作废，发生未知异常", e);
                        result.setMessage(e.getMessage());
                        result.setSuccess(false);
                        result.setResult(null);
                    }
                    return response;
                }
            });
        return result;
    }

    private String constructXml(String xml, XmlSignatureHelper xh) throws Exception {
        return xh.signature(new ByteArrayInputStream(xml.getBytes("UTF-8")), getScriptDataByPath(),
            keyStoreAbner, keyStorePassWord);
    }

    private byte[] getScriptDataByPath() {
        if (null == scriptData) {
            FileInputStream fis = null;
            ByteArrayOutputStream bais = null;
            try {
                File file = new File(scriptPath);
                logger.info("电子发票script文件 path = :" + scriptPath);
                logger.error("电子发票script文件 path = :" + scriptPath);
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
                logger.error("读取电子发票script文件出错:" + e);
            } finally {
                try {
                    if (null != fis) {
                        fis.close();
                    }
                    if (null != bais) {
                        bais.close();
                    }
                } catch (IOException e) {
                    logger.error("关闭读取电子发票script文件流出错:" + e);
                }
            }
        }
        return scriptData;
    }

    private String getPathByName(String name) {
        return this.getClass().getResource(name).getPath();
    }

    private String readResult(String response, XmlSignatureHelper xh) throws Exception {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(response.getBytes("utf-8"));

            //仅测试环境使用
            if (keyStoreAbner != null && keyStoreAbner.equals("192.168.70.212")) {
                return response;
            }
            //测试环境soapui模拟先不验证密钥
            if (xh.validate(bais, getCerDataByPath())) {
                return response;
            } else {
                return null;
            }
        } finally {
            if (null != bais) {
                bais.close();
            }
        }
    }

    private byte[] getCerDataByPath() {
        if (null == cerData) {
            FileInputStream fis = null;
            ByteArrayOutputStream bais = null;
            try {
                logger.info("cer证书path = ：" + cerPath);
                logger.error("cer证书path = ：" + cerPath);
                fis = new FileInputStream(getPathByName(cerPath));
                bais = new ByteArrayOutputStream();
                int b;
                while ((b = fis.read()) > -1) {
                    bais.write(b);
                }
                cerData = bais.toByteArray();
            } catch (Exception e) {
                logger.error("读取电子发票cer证书文件出错:" + e);
            } finally {
                try {
                    if (null != fis) {
                        fis.close();
                    }
                    if (null != bais) {
                        bais.close();
                    }
                } catch (IOException e) {
                    logger.error("关闭读取电子发票cer证书文件流出错:" + e);
                }
            }
        }
        return cerData;
    }
}
