    package com.haier.order.services;


    import com.haier.common.ServiceResult;
    import com.haier.common.util.JsonUtil;
    import com.haier.eis.model.EisInterfaceDataLog;
    import com.haier.order.model.NoticeToHpInputType;
    import com.haier.order.service.HpToNoticeService;
    import com.haier.order.util.IExecute;
    import com.haier.order.util.WriteLogProxy;
    import com.haier.order.wsdl.transordernoticefromehaiertohp.InputType;
    import com.haier.order.wsdl.transordernoticefromehaiertohp.TransSCTailOrder;
    import com.haier.order.wsdl.transordernoticefromehaiertohp.TranssctailorderClientEp;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.GregorianCalendar;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import javax.xml.datatype.DatatypeFactory;
    import javax.xml.datatype.XMLGregorianCalendar;
    import javax.xml.ws.Holder;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;

    /**
     * 合并尾款，通知hp发货接口
     *
     * @Filename: HpToNoticeServiceImpl.java
     * @Version: 1.0
     * @Author: yaoyu
     * @Email: yaoyu@ehaier.com
     *
     */
    @Service
    public class HpToNoticeServiceImpl implements HpToNoticeService {

        @Value("${wsdlPath}")
        private String wsdllocation;
        private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                       .getLogger(HpToNoticeServiceImpl.class);

        @Override
        public ServiceResult<String> noticeHpToSend(String foreignKey,
                                                    final NoticeToHpInputType pushData) {
            final ServiceResult<String> result = new ServiceResult<String>();
            if (pushData == null) {
                log.error("[noticeHpToSend]content为空");
                result.setMessage("content内容为空");
                result.setSuccess(false);
                result.setResult(null);
                return result;
            }
            WriteLogProxy.writeLog(foreignKey, EisInterfaceDataLog.INTERFACE_NOTICE_TO_HP,
                JsonUtil.toJson(pushData), new IExecute() {
                    @Override
                    public String execute() throws Exception {
                        Map<String, Object> returnDataMap = new HashMap<String, Object>();
                        try {
                            TranssctailorderClientEp service =
                                new TranssctailorderClientEp(this.getClass().getResource(wsdllocation + "/TransSCTailOrder.wsdl"));
                            TransSCTailOrder soap = service.getTransSCTailOrderPt();
                            Holder<String> mscode = new Holder<String>();
                            Holder<String> message = new Holder<String>();
                            soap.process(getInputType(pushData), mscode, message);
                            if (mscode != null && mscode.value != null && message != null) {
                                if (mscode.value.equals("F")) { //成功标识
                                    result.setSuccess(false);
                                    result.setMessage(message.value);
                                } else {
                                    result.setSuccess(true);
                                    result.setMessage(message.value);
                                }
                                returnDataMap.put("MSCODE", mscode.value);
                                returnDataMap.put("MESSAGE", message.value);
                                result.setResult(JsonUtil.toJson(returnDataMap));
                            } else {
                                result.setSuccess(false);
                                result.setResult(null);
                                result.setMessage("向HP推送发货通知信息，HP接口未返回结果信息");
                            }

                        } catch (Exception e) {
                            log.error("向HP推送发货通知信息，发生未知异常", e);
                            result.setMessage("向HP推送发货通知信息，发生未知异常:" + e.getMessage());
                            result.setSuccess(false);
                            result.setResult(null);
                        }
                        return JsonUtil.toJson(returnDataMap);
                    }
                });
            return result;
        }

        private List<InputType> getInputType(NoticeToHpInputType noticeInputType) {
            List<InputType> inputTypeList = new ArrayList<InputType>();
            InputType inputType = new InputType();
            inputType.setCREATEDDATE(noticeInputType.getCreatedDate());
            inputType.setORDERNO(noticeInputType.getOrderNo());
            inputType.setReserverDate(noticeInputType.getReserverDate());
            inputType.setTAILSECTIONDATE(noticeInputType.getTailSectionDate());
            inputType.setTAILSECTIONNO(noticeInputType.getTailSectionNo());
            inputTypeList.add(inputType);
            return inputTypeList;

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
    }
