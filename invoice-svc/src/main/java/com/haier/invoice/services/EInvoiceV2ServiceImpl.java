package com.haier.invoice.services;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.service.EisWriteLogService;
import com.haier.invoice.model.invoiceapi.V20Facade;
import com.haier.invoice.model.invoiceapi.V20FacadeImplService;
import com.haier.invoice.service.EInvoiceV2Service;
import com.haier.invoice.util.XmlSignatureHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

/**
 * 电子发票系统处理类
 */
@Service
public class EInvoiceV2ServiceImpl implements EInvoiceV2Service {

    private Logger logger = LoggerFactory.getLogger(EInvoiceV2ServiceImpl.class);

    @Autowired
    private EisWriteLogService eisWriteLogService;

    /**
     * 证书路径
     */
    @Value("${einvoice.cerpath}")
    private String cerPath;
    /**
     * key路径
     */
    @Value("${einvoice.scriptpath}")
    private String scriptPath;
    /**
     * key用户id
     */
    @Value("${einvoice.keyStoreAbner}")
    private String keyStoreAbner;
    /**
     * key密码
     */
    @Value("${einvoice.keyStorePassWord}")
    private String keyStorePassWord;
    /**
     * wsdl文件路径
     */
    @Value("${invoice.wsdl.location}")
    private String wsdlLocation;

    /**
     * 发票操作类型，推送InvoiceApi时使用
     */
    private static final String OPERATETYPE_QUERY = "query";
    private static final String OPERATETYPE_INVALID = "invalid";
    private static final String OPERATETYPE_CREATE = "create";

    private byte[] cerData;
    private byte[] scriptData;

    /**
     * 查询发票
     *
     * @param foreignKey
     * @param pushData
     * @return
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
        String responseData = "";
        String errMessage = "";

        try {
            responseData = invoiceToInvoiceApi(result, pushData, OPERATETYPE_QUERY);
        } catch (Exception e) {
            logger.error(
                    "调用接口" + EisInterfaceDataLog.INTERFACE_CODE_EINVOICE_QRY + ",foreignKey(" + pushData + "),发生异常：" + e.getMessage(),
                    e);
            responseData = "";
            errMessage = e.getMessage();
        }
        if ("errorOperateType".equals(responseData)) {
            return result;
        }

        eisWriteLogService.writeLog(foreignKey, EisInterfaceDataLog.INTERFACE_CODE_EINVOICE_QRY,
                pushData, responseData, errMessage);

        return result;
    }

    /**
     * 作废发票
     *
     * @param foreignKey
     * @param pushData
     * @return
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
        String responseData = "";
        String errMessage = "";

        try {
            responseData = invoiceToInvoiceApi(result, pushData, OPERATETYPE_INVALID);
        } catch (Exception e) {
            logger.error(
                    "调用接口" + EisInterfaceDataLog.INTERFACE_CODE_IENVOICE_TO_BILL + ",foreignKey(" + pushData + "),发生异常：" + e.getMessage(),
                    e);
            responseData = "";
            errMessage = e.getMessage();
        }
        if ("errorOperateType".equals(responseData)) {
            return result;
        }

        eisWriteLogService.writeLog(foreignKey, EisInterfaceDataLog.INTERFACE_CODE_IENVOICE_TO_BILL,
                pushData, responseData, errMessage);
        return result;
    }

    /**
     * 创建发票
     *
     * @param foreignKey
     * @param pushData
     * @return
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
        String responseData = "";
        String errMessage = "";

        try {
            responseData = invoiceToInvoiceApi(result, pushData, OPERATETYPE_CREATE);
        } catch (Exception e) {
            logger.error(
                    "调用接口" + EisInterfaceDataLog.INTERFACE_CODE_IENVOICE_TO_BILL + ",foreignKey(" + pushData + "),发生异常：" + e.getMessage(),
                    e);
            responseData = "";
            errMessage = e.getMessage();
        }
        if ("errorOperateType".equals(responseData)) {
            return result;
        }

        eisWriteLogService.writeLog(foreignKey, EisInterfaceDataLog.INTERFACE_CODE_IENVOICE_TO_BILL,
                pushData, responseData, errMessage);

        return result;

    }

    /**
     * 推送发票信息（开票、作废、查询）
     *
     * @param result
     * @param pushData    推送的数据
     * @param operateType 操作类型 开票、作废、查询
     * @return
     * @throws Exception
     */
    private String invoiceToInvoiceApi(ServiceResult<String> result, String pushData, String operateType) throws Exception {
        String response = "";
        try {
            URL url = getWsdlUrl("InvoiceApi.wsdl");
            //组织调用传入的input

            V20FacadeImplService service = new V20FacadeImplService(url);
            V20Facade soap = service.getInboundWebServicesTicketSaServiceSoap();

            XmlSignatureHelper xh = new XmlSignatureHelper();
            String xml = constructXml(pushData, xh);

            if (OPERATETYPE_QUERY.equals(operateType)) {
                response = soap.cx(xml);  //查询
            } else if (OPERATETYPE_INVALID.equals(operateType)) {
                response = soap.ch(xml);  //冲红
            } else if (OPERATETYPE_CREATE.equals(operateType)) {
                response = soap.kp(xml);  //开票
            } else {
                logger.error("执行方法InvoiceToInvoiceApi向EAI发送操作类型不正确，操作类型为：" + operateType + "，推送数据：" + pushData);
                result.setMessage("执行方法InvoiceToInvoiceApi向EAI发送操作类型不正确");
                result.setSuccess(false);
                result.setResult(null);
                return "errorOperateType";
            }

            result.setResult(readResult(response, xh));
        } catch (Exception e) {
            String operateStr = "";
            switch (operateType) {
                case OPERATETYPE_QUERY:
                    operateStr = "查询";
                    break;
                case OPERATETYPE_INVALID:
                    operateStr = "作废";
                    break;
                case OPERATETYPE_CREATE:
                    operateStr = "开票";
                    break;
            }
            logger.error("电子发票" + operateStr + "，发生未知异常", e);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            result.setResult(null);
            throw e;
        }
        return response;
    }

    /**
     * 组装XML
     *
     * @param xml
     * @param xh
     * @return
     * @throws Exception
     */
    private String constructXml(String xml, XmlSignatureHelper xh) throws Exception {
        return xh.signature(new ByteArrayInputStream(xml.getBytes("UTF-8")), getScriptDataByPath(),
                keyStoreAbner, keyStorePassWord);
    }

    /**
     * 读取key信息
     *
     * @return
     */
    private byte[] getScriptDataByPath() {
        if (null == scriptData) {
            InputStream fis = null;
            ByteArrayOutputStream bais = null;
            try {
                File file = new File(scriptPath);
//                logger.info("电子发票script文件 path = :" + scriptPath);
//                logger.error("电子发票script文件 path = :" + scriptPath);
                if (!file.exists()) {//兼容本地测试
                    //fis = new FileInputStream(getPathByName(scriptPath));
                    URL url = this.getClass().getResource(scriptPath);
                    fis =url.openStream();
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
            if (keyStoreAbner != null && keyStoreAbner.equals("PTTEST11")) {
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
            InputStream fis = null;
            ByteArrayOutputStream bais = null;
            try {
                logger.info("cer证书path = ：" + cerPath);
                logger.error("cer证书path = ：" + cerPath);
                //fis = new FileInputStream(getPathByName(cerPath));

                URL url = this.getClass().getResource(cerPath);
                fis =url.openStream();

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

    /**
     * 获取wsdl文件url
     *
     * @param wsdlFile
     * @return
     */
    public URL getWsdlUrl(String wsdlFile) {
        try {
            URL url = this.getClass().getResource(wsdlLocation + "/" + wsdlFile);
            return url;
        } catch (Exception e) {
            logger.error("WSDL文件路径配置错误或WSDL文件不存在：" + wsdlFile);
            throw new BusinessException("解析WSDL文件失败，配置错误");
        }
    }
}
