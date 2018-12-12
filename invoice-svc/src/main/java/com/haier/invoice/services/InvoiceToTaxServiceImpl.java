package com.haier.invoice.services;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.service.EisWriteLogService;
import com.haier.invoice.model.eai.InvoiceEntity;
import com.haier.invoice.model.eai.InvoiceOutType;
import com.haier.invoice.model.eai.QueryInvoiceInputType;
import com.haier.invoice.model.queryinvoiceinfofromtaxtoehaier.QueryInvoiceInfoFromTAXtoEHAIER;
import com.haier.invoice.model.queryinvoiceinfofromtaxtoehaier.QueryInvoiceInfoFromTAXtoEHAIERResponse;
import com.haier.invoice.model.queryinvoiceinfofromtaxtoehaier.QueryInvoiceInfoFromTAXtoEHAIERResponse.Out;
import com.haier.invoice.model.queryinvoiceinfofromtaxtoehaier.QueryInvoiceInfoFromTAXtoEHAIER_Service;
import com.haier.invoice.model.queryinvoiceinfofromtaxtoehaier.QueryInvoiceInfoFromTAXtoEHAIER_Type.In;
import com.haier.invoice.service.InvoiceToTaxService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import java.net.URL;
import java.util.Date;

/**
 * 与金税系统交互实现类
 **/
@Component
public class InvoiceToTaxServiceImpl implements InvoiceToTaxService {

    private static final Logger logger = LogManager.getLogger(InvoiceToTaxServiceImpl.class);

    @Autowired
    private EisWriteLogService eisWriteLogService;
    @Value("${invoice.wsdl.location}")
    private String wsdlLocatin;

    @Override
    public ServiceResult<InvoiceOutType> modifyInvoiceToTaxSys(InvoiceEntity inputType) {
        //TODO
        return null;
    }

    @Override
    public ServiceResult<InvoiceEntity> queryInvoiceToTaxSys(QueryInvoiceInputType inputType) {
        final ServiceResult<InvoiceEntity> result = new ServiceResult<InvoiceEntity>();
        if (inputType == null) {
            result.setSuccess(false);
            result.setMessage("传参不能为空");
        }
        String inputTypeJson = JsonUtil.toJson(inputType);

        //================================================
        //测试设置，测试完删除
        //        logger.info("发送数据：" + inputTypeJson);
        //        InvoiceEntity invoiceOutType = new InvoiceEntity();
        //        result.setResult(invoiceOutType);
        //        result.setSuccess(true);
        //================================================

        //测试时注释，测试完打开
        String responseStr = "";
        String errMessage = "";
        try {
            //测试用数据 start
//            InvoiceEntity invoiceOutType = new InvoiceEntity();
//            invoiceOutType.setKpzt(0);
//            invoiceOutType.setKpzt(3);
//            invoiceOutType.setKpzt(4);
//            result.setResult(invoiceOutType);
//            result.setSuccess(true);
            //测试用数据 end


            //生产数据
            responseStr = queryInvoiceFromTaxSys(result, inputType);

        } catch (Exception e) {
            logger.error(
                    "调用接口" + EisInterfaceDataLog.INTERFACE_CODE_QUERY_INVOICE + ",foreignKey(" + inputType.getWdh() + "),发生异常：" + e.getMessage(),
                    e);
            responseStr = "";
            errMessage = e.getMessage();
        }

        eisWriteLogService.writeLog(inputType.getWdh(),
                EisInterfaceDataLog.INTERFACE_CODE_QUERY_INVOICE, inputTypeJson, responseStr, errMessage);

        return result;
    }

    /**
     * 从金税系统查询发票信息
     *
     * @param result
     * @param inputType
     * @return
     * @throws Exception
     */
    private String queryInvoiceFromTaxSys(ServiceResult<InvoiceEntity> result, QueryInvoiceInputType inputType) throws Exception {

        try {
            URL url = getWsdlUrl("QueryInvoiceInfoFromTAXtoEHAIER.wsdl");
            QueryInvoiceInfoFromTAXtoEHAIER_Service service = new QueryInvoiceInfoFromTAXtoEHAIER_Service(
                    url);
            QueryInvoiceInfoFromTAXtoEHAIER soap = service
                    .getQueryInvoiceInfoFromTAXtoEHAIERSOAP();
            In in = convertQueryInputType(inputType);
            QueryInvoiceInfoFromTAXtoEHAIERResponse.Out out = soap.queryInvoiceInfoFromTAXtoEHAIER(in);
            //返回结果
            if (out == null) {
                result.setSuccess(false);
                result.setMessage("响应数据为空");
                result.setResult(null);
                return "响应数据为空";
            }
            result.setResult(convertQueryOutType(out));
            result.setSuccess(true);
            return JsonUtil.toJson(out);
        } catch (Exception e) {
            logger.error("查询开票数据时,发生未知异常", e);
            result.setMessage("查询开票数据时,发生未知异常");
            result.setSuccess(false);
            result.setResult(null);
            throw e;
        }
    }

    @Override
    public ServiceResult<InvoiceOutType> createInvoiceToTaxSys(InvoiceEntity invoiceEntity) {
        //TODO
        return null;
    }

    @Override
    public ServiceResult<InvoiceOutType> invalidInvoiceToTaxSys(InvoiceEntity invoiceEntity) {
        //TODO
        return null;
    }

    private In convertQueryInputType(QueryInvoiceInputType inputType) {
        //TODO
        In in = new In();
        in.setWDH(inputType.getWdh());
        return in;
    }

    private InvoiceEntity convertQueryOutType(Out out) {
        InvoiceEntity inType = new InvoiceEntity();
        inType.setWdh(out.getWDH());
        inType.setKhbm(out.getKHBM());
        inType.setKhmc(out.getKHMC());
        inType.setKhsh(out.getKHSH());
        inType.setKhdz(out.getKHDZ());
        inType.setKhkhyhzh(out.getKHKHYHZH());
        inType.setBz(out.getBZ());
        inType.setWdrq(isOutDateNull(out.getWDRQ()));
        inType.setCpdm(out.getCPDM());
        inType.setCpmc(out.getCPMC());
        inType.setCpxh(out.getCPXH());
        inType.setCpdw(out.getCPDW());
        inType.setCpsl(out.getCPSL());
        inType.setHsdj(out.getHSDJ());
        inType.setHsje(out.getHSJE());
        inType.setBhsdj(out.getBHSDJ());
        inType.setBhsje(out.getBHSJE());
        inType.setJsje(out.getJSJE());
        inType.setJssl(out.getJSSL());
        inType.setJfje(out.getJFJE());
        inType.setJlje(out.getJLJE());
        inType.setJlbz(out.getJLBZ());
        inType.setFplx(out.getFPLX());
        inType.setFpzt(out.getFPZT());
        inType.setSkfs(out.getSKFS());
        inType.setLbjsdh(out.getLBJSDH());
        inType.setKwbm(out.getKWBM());
        inType.setHptx(out.getHPTX());
        inType.setDdlx(out.getDDLX());
        inType.setFgsno(out.getFGSNO());
        inType.setDdhno(out.getDDHNO());
        inType.setWlno(out.getWLNO());
        inType.setAdd1(out.getADD1());
        inType.setAdd2(out.getADD2());
        inType.setRrrq(isOutDateNull(out.getRRRQ()));
        inType.setGxrq(isOutDateNull(out.getGXRQ()));
        inType.setFphm(out.getFPHM());
        inType.setKprq(isOutDateNull(out.getKPRQ()));
        inType.setSkrq(isOutDateNull(out.getSKRQ()));
        inType.setKpman(out.getKPMAN());
        inType.setKpzt(out.getKPZT());
        inType.setZfrq(isOutDateNull(out.getZFRQ()));
        return inType;
    }

    private Date isOutDateNull(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return null;
        } else {
            return xmlGregorianCalendar.toGregorianCalendar().getTime();
        }
    }

    /**
     * 获取wsdl文件url
     *
     * @param wsdlFile
     * @return
     */
    public URL getWsdlUrl(String wsdlFile) {
        try {
            URL url = this.getClass().getResource(wsdlLocatin + "/" + wsdlFile);
            return url;
        } catch (Exception e) {
            logger.error("WSDL文件路径配置错误或WSDL文件不存在：" + wsdlFile);
            throw new BusinessException("解析WSDL文件失败，配置错误");
        }
    }
}
