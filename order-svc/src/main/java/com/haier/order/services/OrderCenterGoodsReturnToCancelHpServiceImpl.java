package com.haier.order.services;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.order.model.InsertCancelDataFromVOMToHCSP_Type;
import com.haier.order.util.HelpUtils;
import com.haier.order.util.IExecute;
import com.haier.order.util.WriteLogProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
public class OrderCenterGoodsReturnToCancelHpServiceImpl {
    private Logger   logger = LoggerFactory.getLogger(OrderCenterGoodsReturnToCancelHpServiceImpl.class);
    private String   wsdlUrl;
    @Autowired
    private HelpUtils help;

    
    public ServiceResult<Map<String, String>> pushOrderCancelToHp(final String cOrderSn,
                                                                  final Map<String, String> pushData) {
        final ServiceResult<Map<String, String>> result = new ServiceResult<Map<String, String>>();
        WriteLogProxy.writeLog(cOrderSn, EisInterfaceDataLog.INTERFACE_CODE_GOODS_RETURN_TO_HP,
            JsonUtil.toJson(pushData), new IExecute() {
                @Override
                public String execute() throws Exception {
                    Map<String, String> returnData = new HashMap<String, String>();
                    try {

                        URL url = new URL(getWsdlPath(wsdlUrl));
//                        InsertCancelDataFromVOMToHCSP_Service clientHp = new InsertCancelDataFromVOMToHCSP_Service(
//                            url);
//                        InsertCancelDataFromVOMToHCSP ehaierToHp = clientHpååå
//                            .getInsertCancelDataFromVOMToHCSPSOAP();
                        List<InsertCancelDataFromVOMToHCSP_Type.Inputs> inputList = new ArrayList<InsertCancelDataFromVOMToHCSP_Type.Inputs>();
                        InsertCancelDataFromVOMToHCSP_Type.Inputs input = new InsertCancelDataFromVOMToHCSP_Type.Inputs();
                        String cOrderSn = pushData.get("cOrderSn");
                        input.setOrderNo(cOrderSn);
                        String cancelDate = pushData.get("cancelDate");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        input.setCancelDate(convertToXMLGregorianCalendar(format.parse(cancelDate)));
                        input.setCreatedDate(convertToXMLGregorianCalendar(new Date()));
//                        inputList.add(input);
                        Holder<String> flag = new Holder<String>();
                        Holder<String> msg = new Holder<String>();
//                        ehaierToHp.insertCancelDataFromVOMToHCSP(inputList, flag, msg);
                        returnData.put("FLAG", flag.value);
                        returnData.put("MSG", flag.value);
                        result.setResult(returnData);
                    } catch (Exception e) {
                        returnData.put("error", e.getMessage());
                        returnData.put("MSG", "作废工单异常");
                        result.setResult(returnData);
                        logger.error("退货：请求作废HP工单发生异常", e);
                    }
                    return JsonUtil.toJson(returnData);
                }
            });
        return result;
    }

    public XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return gc;
    }

    public String getWsdlUrl() {
        return wsdlUrl;
    }

    public void setWsdlUrl(String wsdlUrl) {
        this.wsdlUrl = wsdlUrl;
    }

    private String getWsdlPath(String wsdlFile) {
        return help.getWSDLPATH(wsdlFile);
    }

    public HelpUtils getHelp() {
        return help;
    }

    public void setHelp(HelpUtils help) {
        this.help = help;
    }

}
