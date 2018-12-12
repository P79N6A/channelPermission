package com.haier.stock.services;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.IExecute;
import com.haier.shop.model.InvoiceEntity;
import com.haier.shop.model.InvoiceOutType;
import com.haier.shop.model.QueryInvoiceInputType;
import com.haier.stock.service.StockCenterInvoiceToTaxService;
import com.haier.stock.util.HelpUtils;
@Service
public class StockCenterInvoiceToTaxServiceImpl implements StockCenterInvoiceToTaxService {
    private Logger             logger          = LoggerFactory
                                                   .getLogger(StockCenterInvoiceToTaxServiceImpl.class);

    public static final String YYYYMMDDTHHMMSS = "yyyy-MM-dd'T'HH:mm:ss";
    @Autowired
    private HelpUtils           help;

    public void setHelp(HelpUtils help) {
        this.help = help;
    }

    @Override
    public ServiceResult<InvoiceOutType> modifyInvoiceToTaxSys(final InvoiceEntity inputType) {
        final ServiceResult<InvoiceOutType> result = new ServiceResult<InvoiceOutType>();
        if (inputType == null) {
            result.setSuccess(false);
            result.setMessage("传参不能为空");
        }
        String inputTypeJson = JsonUtil.toJson(inputType);

        //================================================
        //测试设置，测试完删除
        //        logger.info("发送数据：" + inputTypeJson);
        //        InvoiceOutType invoiceOutType = new InvoiceOutType();
        //        invoiceOutType.setFlag("S");
        //        invoiceOutType.setMsg("");
        //        invoiceOutType.setWdh(invoiceOutType.getWdh());
        //        result.setResult(invoiceOutType);
        //        result.setSuccess(true);
        //================================================

        //测试时注释，测试完打开
        WriteLogProxy.writeLog(inputType.getWdh(),
            EisInterfaceDataLog.INTERFACE_CODE_MODIFY_INVOICE, inputTypeJson, new IExecute() {
                @Override
                public String execute() throws Exception {
                    try {
//                        URL url = new URL(getWsdlPath("ModifyInvoiceInfoToGoldenTax.wsdl"));
//                        ModifyinvoiceinfotogoldentaxClientEp taxClient = new ModifyinvoiceinfotogoldentaxClientEp(
//                            url);
//                        ModifyInvoiceInfoToGoldenTax goldenTax = taxClient
//                            .getModifyInvoiceInfoToGoldenTaxPt();
//                        Input payload = convertInputType(inputType);
//                        Output output = goldenTax.process(payload);
//                        //返回结果
//                        if (output == null || output.getResult() == null) {
//                            result.setSuccess(false);
//                            result.setMessage("响应数据为空");
//                            result.setResult(null);
//                            return "响应数据为空";
//                        }
//                        OutputType outPutType = output.getResult();
//                        InvoiceOutType invoiceOutType = new InvoiceOutType();
//                        invoiceOutType.setFlag(outPutType.getFLAG());
//                        invoiceOutType.setMsg(outPutType.getMSG());
//                        invoiceOutType.setWdh(outPutType.getWDH());
//                        result.setResult(invoiceOutType);
//                        result.setSuccess(true);
//                        return outPutType.getMSG();
                    } catch (Exception e) {
//                        logger.error("修改取消开票数据时,发生未知异常", e);
//                        result.setMessage("修改取消开票数据时,发生未知异常");
//                        result.setSuccess(false);
//                        result.setResult(null);
//                        throw e;
                    }
					return "做的时候再看卡老代码";
                }
            });

        return result;
    }

    @Override
    public ServiceResult<InvoiceEntity> queryInvoiceToTaxSys(final QueryInvoiceInputType inputType) {
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
        WriteLogProxy.writeLog(inputType.getWdh(),
            EisInterfaceDataLog.INTERFACE_CODE_QUERY_INVOICE, inputTypeJson, new IExecute() {
                @Override
                public String execute() throws Exception {
                    try {
//                        URL url = new URL(getWsdlPath("QueryInvoiceInfoFromTAXtoEHAIER.wsdl"));
//                        QueryInvoiceInfoFromTAXtoEHAIER_Service service = new QueryInvoiceInfoFromTAXtoEHAIER_Service(
//                            url);
//                        QueryInvoiceInfoFromTAXtoEHAIER soap = service
//                            .getQueryInvoiceInfoFromTAXtoEHAIERSOAP();
//                        In in = convertQueryInputType(inputType);
//                        Out out = soap.queryInvoiceInfoFromTAXtoEHAIER(in);
                        //返回结果
//                        if (out == null) {
//                            result.setSuccess(false);
//                            result.setMessage("响应数据为空");
//                            result.setResult(null);
//                            return "响应数据为空";
//                        }
//                        result.setResult(convertQueryOutType(out));
//                        result.setSuccess(true);
//                        return JsonUtil.toJson(out);
                        return "";
                    } catch (Exception e) {
                        logger.error("查询开票数据时,发生未知异常", e);
                        result.setMessage("查询开票数据时,发生未知异常");
                        result.setSuccess(false);
                        result.setResult(null);
                        throw e;
                    }
                }
            });

        return result;
    }

    @Override
    public ServiceResult<InvoiceOutType> createInvoiceToTaxSys(final InvoiceEntity invoiceEntity) {
        final ServiceResult<InvoiceOutType> result = new ServiceResult<InvoiceOutType>();
        if (invoiceEntity == null) {
            result.setSuccess(false);
            result.setMessage("传参不能为空");
        }
        String inputTypeJson = JsonUtil.toJson(invoiceEntity);

        //================================================
        //测试设置，测试完删除
        //        logger.info("发送数据：" + inputTypeJson);
        //        logger.info("发送数据Input：" + JsonUtil.toJson(convertInvoiceEntityToInput(invoiceEntity)));
        //        InvoiceOutType invoiceOutType = new InvoiceOutType();
        //        invoiceOutType.setFlag("S");
        //        invoiceOutType.setMsg("");
        //        invoiceOutType.setWdh(invoiceOutType.getWdh());
        //        result.setResult(invoiceOutType);
        //        result.setSuccess(true);
        //================================================

        //测试时注释，测试完打开
        WriteLogProxy.writeLog(invoiceEntity.getWdh(),
            EisInterfaceDataLog.INTERFACE_CODE_CREATE_INVOICE, inputTypeJson, new IExecute() {
                @Override
                public String execute() throws Exception {
                    try {
                        //组织调用传入的input
//                        Input input = convertInvoiceEntityToInput(invoiceEntity);
//                        //调用
//                        URL url = new URL(getWsdlPath("TransInvoiceInfoFromEHaier.wsdl"));
//                        TransinvoiceinfofromehaierClientEp service = new TransinvoiceinfofromehaierClientEp(
//                            url);
//                        TransInvoiceInfoFromEHaier soap = service.getTransInvoiceInfoFromEHaierPt();
//                        Output output = soap
//                            .process(input);
//                        //返回结果
//                        if (output == null || output.getResult() == null) {
//                            result.setSuccess(false);
//                            result.setMessage("响应数据为空");
//                            result.setResult(null);
//                            return "响应数据为空";
//                        }
//
//                       OutputType outPutType = output
//                            .getResult();
//                        InvoiceOutType invoiceOutType = new InvoiceOutType();
//                        invoiceOutType.setFlag(outPutType.getFLAG());
//                        invoiceOutType.setMsg(outPutType.getMSG());
//                        result.setSuccess(true);
//                        result.setResult(invoiceOutType);
//                        return JsonUtil.toJson(invoiceOutType);
                    	return "";
                    } catch (Exception e) {
                        logger.error("发送开票数据到EAI时,发生未知异常", e);
                        result.setMessage("发送开票数据到EAI时,发生未知异常");
                        result.setSuccess(false);
                        result.setResult(null);
                        throw e;
                    }
                }
            });
        return result;
    }

    @Override
    public ServiceResult<InvoiceOutType> invalidInvoiceToTaxSys(final InvoiceEntity invoiceEntity) {
        final ServiceResult<InvoiceOutType> result = new ServiceResult<InvoiceOutType>();
        if (invoiceEntity == null) {
            result.setSuccess(false);
            result.setMessage("传参不能为空");
        }
        String inputTypeJson = JsonUtil.toJson(invoiceEntity);

        //================================================
        //测试设置，测试完删除
        //        logger.info("发送数据：" + inputTypeJson);
        //        InvoiceOutType invoiceOutType = new InvoiceOutType();
        //        invoiceOutType.setFlag("S");
        //        invoiceOutType.setMsg("");
        //        invoiceOutType.setWdh(invoiceOutType.getWdh());
        //        result.setResult(invoiceOutType);
        //        result.setSuccess(true);
        //================================================

        //测试时注释，测试完打开
        WriteLogProxy.writeLog(invoiceEntity.getWdh(),
            EisInterfaceDataLog.INTERFACE_CODE_INVALID_INVOICE, inputTypeJson, new IExecute() {
                @Override
                public String execute() throws Exception {
                    try {
                        //组织调用传入的input
//                        List<Inputs> listInput = new ArrayList<Inputs>();
//                        Inputs inputs = new Inputs();
//                        inputs.setWDH(invoiceEntity.getWdh());
//                        Date rrrq = invoiceEntity.getRrrq();
//                        SimpleDateFormat format = new SimpleDateFormat(YYYYMMDDTHHMMSS);
//                        inputs.setRRRQ(rrrq == null ? null : format.format(rrrq));
//                        listInput.add(inputs);
//                        //调用
//                        URL url = new URL(getWsdlPath("BillInfoCancel.wsdl"));
//                        BillinfocancelClientEp service = new BillinfocancelClientEp(url);
//                        BillInfoCancel soap = service.getBillInfoCancelPt();
//                        Outputs outputs = soap.process(listInput);
                        //返回结果
//                        if (outputs == null) {
//                            result.setSuccess(false);
//                            result.setMessage("响应数据为空");
//                            result.setResult(null);
//                            return "响应数据为空";
//                        }
//
                        InvoiceOutType invoiceOutType = new InvoiceOutType();
//                        invoiceOutType.setFlag(outputs.getFLAG());
//                        invoiceOutType.setMsg(outputs.getMSG());
                        result.setSuccess(true);
                        result.setResult(invoiceOutType);
                        return JsonUtil.toJson(invoiceOutType);
                    } catch (Exception e) {
                        logger.error("发票强制作废时,发生异常", e);
                        result.setMessage("发票强制作废时,发生异常");
                        result.setSuccess(false);
                        result.setResult(null);
                        throw e;
                    }
                }
            });
        return result;
    }
// private InvoiceEntity convertQueryOutType(Out out) {
    private InvoiceEntity convertQueryOutType() {
        InvoiceEntity inType = new InvoiceEntity();
//        inType.setWdh(out.getWDH());
//        inType.setKhbm(out.getKHBM());
//        inType.setKhmc(out.getKHMC());
//        inType.setKhsh(out.getKHSH());
//        inType.setKhdz(out.getKHDZ());
//        inType.setKhkhyhzh(out.getKHKHYHZH());
//        inType.setBz(out.getBZ());
//        inType.setWdrq(isOutDateNull(out.getWDRQ()));
//        inType.setCpdm(out.getCPDM());
//        inType.setCpmc(out.getCPMC());
//        inType.setCpxh(out.getCPXH());
//        inType.setCpdw(out.getCPDW());
//        inType.setCpsl(out.getCPSL());
//        inType.setHsdj(out.getHSDJ());
//        inType.setHsje(out.getHSJE());
//        inType.setBhsdj(out.getBHSDJ());
//        inType.setBhsje(out.getBHSJE());
//        inType.setJsje(out.getJSJE());
//        inType.setJssl(out.getJSSL());
//        inType.setJfje(out.getJFJE());
//        inType.setJlje(out.getJLJE());
//        inType.setJlbz(out.getJLBZ());
//        inType.setFplx(out.getFPLX());
//        inType.setFpzt(out.getFPZT());
//        inType.setSkfs(out.getSKFS());
//        inType.setLbjsdh(out.getLBJSDH());
//        inType.setKwbm(out.getKWBM());
//        inType.setHptx(out.getHPTX());
//        inType.setDdlx(out.getDDLX());
//        inType.setFgsno(out.getFGSNO());
//        inType.setDdhno(out.getDDHNO());
//        inType.setWlno(out.getWLNO());
//        inType.setAdd1(out.getADD1());
//        inType.setAdd2(out.getADD2());
//        inType.setRrrq(isOutDateNull(out.getRRRQ()));
//        inType.setGxrq(isOutDateNull(out.getGXRQ()));
//        inType.setFphm(out.getFPHM());
//        inType.setKprq(isOutDateNull(out.getKPRQ()));
//        inType.setSkrq(isOutDateNull(out.getSKRQ()));
//        inType.setKpman(out.getKPMAN());
//        inType.setKpzt(out.getKPZT());
//        inType.setZfrq(isOutDateNull(out.getZFRQ()));
        return inType;
    }

    private void convertQueryInputType(QueryInvoiceInputType inputType) {
//        In in = new In();
//        in.setWDH(inputType.getWdh());
//        return in;
    }

//    private Input convertInputType(InvoiceEntity input) {
    private void convertInputType(InvoiceEntity input) {
//        Input payload = new Input();
//        InputType inputType = new InputType();
//        inputType.setWDH(input.getWdh());
//        inputType.setKHBM(input.getKhbm());
//        inputType.setKHMC(input.getKhmc());
//        inputType.setKHSH(input.getKhsh());
//        inputType.setKHDZ(input.getKhdz());
//        inputType.setKHKHYHZH(input.getKhkhyhzh());
//        inputType.setBZ(input.getBz());
//        SimpleDateFormat format = new SimpleDateFormat(YYYYMMDDTHHMMSS);
//        inputType.setWDRQ(input.getWdrq() == null ? null : format.format(input.getWdrq()));
//        inputType.setCPDM(input.getCpdm());
//        inputType.setCPMC(input.getCpmc());
//        inputType.setCPXH(input.getCpxh());
//        inputType.setCPDW(input.getCpdw());
//        inputType.setCPSL(input.getCpsl() == null ? null : input.getCpsl().toString());
//        inputType.setHSDJ(input.getHsdj() == null ? null : input.getHsdj().toString());
//        inputType.setHSJE(input.getHsje() == null ? null : input.getHsje().toString());
//        inputType.setBHSDJ(input.getBhsdj() == null ? null : input.getBhsdj().toString());
//        inputType.setBHSJE(input.getBhsje() == null ? null : input.getBhsje().toString());
//        inputType.setJSJE(input.getJsje() == null ? null : input.getJsje().toString());
//        inputType.setJSSL(input.getJssl() == null ? null : input.getJssl().toString());
//        inputType.setJFJE(input.getJfje() == null ? null : input.getJfje().toString());
//        inputType.setJLJE(input.getJlje() == null ? null : input.getJlje().toString());
//        inputType.setJLBZ(input.getJlbz());
//        inputType.setFPLX(input.getFplx() == null ? null : input.getFplx().toString());
//        inputType.setFPZT(input.getFpzt() == null ? null : input.getFpzt().toString());
//        inputType.setSKFS(input.getSkfs());
//        inputType.setLBJSDH(input.getLbjsdh());
//        inputType.setKWBM(input.getKwbm());
//        inputType.setHPTX(input.getHptx() == null ? null : input.getHptx().toString());
//        inputType.setDDLX(input.getDdlx());
//        inputType.setFGSNO(input.getFgsno());
//        inputType.setDDHNO(input.getDdhno());
//        inputType.setWLNO(input.getWlno());
//        inputType.setADD1(input.getAdd1());
//        inputType.setADD2(input.getAdd2());
//        inputType.setRRRQ(input.getRrrq() == null ? null : format.format(input.getRrrq()));
//        inputType.setGXRQ(input.getGxrq() == null ? null : format.format(input.getGxrq()));
//        inputType.setFPHM(input.getFphm());
//        inputType.setKPRQ(input.getKprq() == null ? null : format.format(input.getKprq()));
//        inputType.setSKRQ(input.getSkrq() == null ? null : format.format(input.getSkrq()));
//        inputType.setKPMAN(input.getKpman());
//        inputType.setKPZT(input.getKpzt() == null ? null : input.getKpzt().toString());
//        inputType.setZFRQ(input.getZfrq() == null ? null : format.format(input.getZfrq()));
//        payload.setITEM(inputType);
//        return payload;
    }

    /**
     * 将发票实体转化为调用创建发票wsdl时的input
     * @param invoiceEntity
     * @return
     */
//    private com.haier.cbs.base.eai.invoice.createinvoiceinfotogoldentax.Input convertInvoiceEntityToInput(InvoiceEntity invoiceEntity) {
    private void convertInvoiceEntityToInput(InvoiceEntity invoiceEntity) {

//        com.haier.cbs.base.eai.invoice.createinvoiceinfotogoldentax.Input input = new com.haier.cbs.base.eai.invoice.createinvoiceinfotogoldentax.Input();
//        List<com.haier.cbs.base.eai.invoice.createinvoiceinfotogoldentax.InputType> listInput = input
//            .getInput();
//        com.haier.cbs.base.eai.invoice.createinvoiceinfotogoldentax.InputType inputType = new com.haier.cbs.base.eai.invoice.createinvoiceinfotogoldentax.InputType();
//        inputType.setWDH(invoiceEntity.getWdh());
//        inputType.setKHBM(invoiceEntity.getKhbm());
//        inputType.setKHMC(invoiceEntity.getKhmc());
//        inputType.setKHSH(invoiceEntity.getKhsh());
//        inputType.setKHDZ(invoiceEntity.getKhdz());
//        inputType.setKHKHYHZH(invoiceEntity.getKhkhyhzh());
//        inputType.setBZ(invoiceEntity.getBz());
//        SimpleDateFormat format = new SimpleDateFormat(YYYYMMDDTHHMMSS);
//        inputType.setWDRQ(invoiceEntity.getWdrq() == null ? null : format.format(invoiceEntity
//            .getWdrq()));
//        inputType.setCPDM(invoiceEntity.getCpdm());
//        inputType.setCPMC(invoiceEntity.getCpmc());
//        inputType.setCPXH(invoiceEntity.getCpxh());
//        inputType.setCPDW(invoiceEntity.getCpdw());
//        inputType.setCPSL(invoiceEntity.getCpsl() == null ? null : invoiceEntity.getCpsl()
//            .toString());
//        inputType.setHSDJ(invoiceEntity.getHsdj() == null ? null : invoiceEntity.getHsdj()
//            .toString());
//        inputType.setHSJE(invoiceEntity.getHsje() == null ? null : invoiceEntity.getHsje()
//            .toString());
//        inputType.setBHSDJ(invoiceEntity.getBhsdj() == null ? null : invoiceEntity.getBhsdj()
//            .toString());
//        inputType.setBHSJE(invoiceEntity.getBhsje() == null ? null : invoiceEntity.getBhsje()
//            .toString());
//        inputType.setJSJE(invoiceEntity.getJsje() == null ? null : invoiceEntity.getJsje()
//            .toString());
//        inputType.setJSSL(invoiceEntity.getJssl() == null ? null : invoiceEntity.getJssl()
//            .toString());
//        inputType.setJFJE(invoiceEntity.getJfje() == null ? null : invoiceEntity.getJfje()
//            .toString());
//        inputType.setJLJE(invoiceEntity.getJlje() == null ? null : invoiceEntity.getJlje()
//            .toString());
//        inputType.setJLBZ(invoiceEntity.getJlbz());
//        inputType.setFPLX(invoiceEntity.getFplx() == null ? null : invoiceEntity.getFplx()
//            .toString());
//        inputType.setFPZT(invoiceEntity.getFpzt() == null ? null : invoiceEntity.getFpzt()
//            .toString());
//        inputType.setSKFS(invoiceEntity.getSkfs());
//        inputType.setLBJSDH(invoiceEntity.getLbjsdh());
//        inputType.setKWBM(invoiceEntity.getKwbm());
//        inputType.setHPTX(invoiceEntity.getHptx() == null ? null : invoiceEntity.getHptx()
//            .toString());
//        inputType.setDDLX(invoiceEntity.getDdlx());
//        inputType.setFGSNO(invoiceEntity.getFgsno());
//        inputType.setDDHNO(invoiceEntity.getDdhno());
//        inputType.setWLNO(invoiceEntity.getWlno());
//        inputType.setADD1(invoiceEntity.getAdd1());
//        inputType.setADD2(invoiceEntity.getAdd2());
//        inputType.setRRRQ(invoiceEntity.getRrrq() == null ? null : format.format(invoiceEntity
//            .getRrrq()));
//        inputType.setGXRQ(invoiceEntity.getGxrq() == null ? null : format.format(invoiceEntity
//            .getGxrq()));
//        inputType.setFPHM(invoiceEntity.getFphm());
//        inputType.setKPRQ(invoiceEntity.getKprq() == null ? null : format.format(invoiceEntity
//            .getKprq()));
//        inputType.setSKRQ(invoiceEntity.getSkrq() == null ? null : format.format(invoiceEntity
//            .getSkrq()));
//        inputType.setKPMAN(invoiceEntity.getKpman());
//        inputType.setKPZT(invoiceEntity.getKpzt() == null ? null : invoiceEntity.getKpzt()
//            .toString());
//        inputType.setZFRQ(invoiceEntity.getZfrq() == null ? null : format.format(invoiceEntity
//            .getZfrq()));
//        listInput.add(inputType);
//        return input;
    }

//    private String getWsdlPath(String wsdlFile) {
    private void getWsdlPath(String wsdlFile) {
        //        String path = "file:" + this.getClass().getResource("/wsdl/" + wsdlFile).getPath();
        //        if (logger.isDebugEnabled())
        //            logger.debug("wsdl path:" + path);
        //        return path;
//        return help.getWsdlPath(wsdlFile);
    }

    private Date isOutDateNull(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return null;
        } else {
            return xmlGregorianCalendar.toGregorianCalendar().getTime();
        }
    }

}
