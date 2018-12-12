package com.haier.svc.model;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.stock.model.InvWarehouse;
import com.haier.system.model.BaseErr;
import com.haier.system.service.BaseErrService;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.haier.common.util.JsonUtil;

import com.haier.svc.bean.OMST2OrderCreateRequire;
import com.haier.svc.bean.OMST2OrderCreateResponse;
import com.haier.svc.bean.PredictingStockItem;
import com.haier.svc.bean.queryfrostorderfromehaiertooms.InType;
import com.haier.svc.bean.queryfrostorderfromehaiertooms.OutType;
import com.haier.svc.purchase.omst3.TransForecastInfoFromOMS;
import com.haier.svc.purchase.omst3.TransForecastInfoFromOMS_Service;
import com.haier.svc.purchase.queryfrostorderfromehaiertooms.QueryFrostOrderFromEHAIERToOMS;
import com.haier.svc.purchase.queryfrostorderfromehaiertooms.QueryFrostOrderFromEHAIERToOMS_Service;
import com.haier.svc.purchase.transforecastpracticalfromb2ctooms.TransForecastPracticalFromB2CToOMS;
import com.haier.svc.purchase.transforecastpracticalfromb2ctooms.TransForecastPracticalFromB2CToOMS_Service;
import com.haier.svc.service.PurchaseBaseCommonService;
import com.haier.svc.util.WebCommonUtil;

import org.springframework.beans.factory.annotation.Value;
@Service("omsOrderModel")
public class OMSOrderModel {
    private static org.apache.log4j.Logger log                   = org.apache.log4j.LogManager
                                                                     .getLogger(OMSOrderModel.class);
   // protected static final String          T2CREATE_FROM_OMS_URL = "/EAI/RoutingProxyService/EAI_REST_POST_ServiceRoot?INT_CODE=CRM_INT_OMS_4";
    //zzb  2017-06-29 切换新地址
    protected static final String          T2CREATE_FROM_OMS_URL = "/SaleOrders/IHS/InsertOrderBill_OMS2/InsertOrderBill_OMS2?INT_CODE=CRM_INT_OMS_4";
    protected static final String          T3CREATE_FROM_OMS_URL = "";
    @Value("${t2Order.wsdl.url}")
    private String wsdlUrl;

    private String                         eaiUrl                = null;
    @Autowired
    private PurchaseBaseCommonService      purchaseBaseCommonService;
    @Value("${t2OrderResponse.location}")
	private String						   wsdlLocation;
//    private String                         wsdlLocation          = "/wsdl";
    public static String                   rWsdlLocation         = null;
    
    @Autowired
    private BaseErrService  baseErrService;

    public void setWsdlLocation(String wsdlLocation) {
        this.wsdlLocation = wsdlLocation;
        rWsdlLocation = wsdlLocation;
    }

    public void setEaiUrl(String eaiUrl) {
        this.eaiUrl = eaiUrl;
    }

    public PurchaseBaseCommonService getPurchaseBaseCommonService() {
        return purchaseBaseCommonService;
    }

    public void setPurchaseBaseCommonService(PurchaseBaseCommonService purchaseBaseCommonService) {
        this.purchaseBaseCommonService = purchaseBaseCommonService;
    }

    public OMST2OrderCreateResponse CreateT2Order(OMST2OrderCreateRequire order) throws Exception {

        // 库位关联情报取得
        ServiceResult<InvWarehouse> invWarehouse = purchaseBaseCommonService
            .getAllWhByEstorgeId(order.getEStorgeId());
        // 销售组织
        order.setCorpCode(invWarehouse.getResult().getSale_org_id());
        // 地区编码
        order.setRegionID(invWarehouse.getResult().getArea_id());
        // 库位C码
        order.setCorpDest(invWarehouse.getResult().getCenterCode());
        // 付款方
        order.setCustDest(invWarehouse.getResult().getPayment_id());
        // 客户送达方
        order.setCustDest(invWarehouse.getResult().getTransmit_id());
        //TODO测试用代码begin
//        order.setCorpCode("2130");
//        order.setRegionID("QD001");
//        order.setBillType("J001");
//        order.setCorpDest("C12809");
//        order.setWhCode("JOR2");
        //TODO用户登录完善后此处代码去掉
        order.setCustMgr("01381518");
        order.setProMgr("01381518");
        order.setProDeputy("01381518");
//        order.setCustCode("C200076228");
//        order.setCustDest("8800050625");
//        order.setMaker("A0021587");
//        order.setInvSort("DA");
//        order.setInvCode("CA0JN0B06");
//        order.setAdd6("TM");
      //TODO测试用代码end

//        String url = eaiUrl + T2CREATE_FROM_OMS_URL;
//        String url = "http://10.135.1.185:7001/EAI/RoutingProxyService/EAI_REST_POST_ServiceRoot?INT_CODE=CRM_INT_OMS_4";
        String url = wsdlUrl;
        //新的测试地址  zzb
//        String url = "http://10.138.40.168:8001/EAI/RoutingProxyService/EAI_REST_POST_ServiceRoot?INT_CODE=CRM_INT_OMS_4";
        String resultMsg = WebCommonUtil.PostMessage(url, order.toXMLMessage());

        OMST2OrderCreateResponse result = new OMST2OrderCreateResponse();
        Document doc = DocumentHelper.parseText(resultMsg);
        Element element = (Element) doc.getRootElement().elements().get(0);
        List<Element> list = element.elements();
        for (Element el : list) {
            if (el.getName().equals("Flag") || el.getName().equals("CODE")) {
                result.setFlag(el.getText());
            } else if (el.getName().equals("BillCode")) {
                result.setBillCode(el.getText());
            } else if (el.getName().equals("Vbeln")) {
                result.setVbeln(el.getText());
            } else if (el.getName().equals("ReturnMsg") || el.getName().equals("MSG")) {
                result.setReturnMsg(el.getText());
            }
        }
        BaseErr base = new BaseErr();
        base.setInterface_path(url);
        
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = sdf.format(date);
		Date da = sdf.parse(s);
		Timestamp ts = new Timestamp(da.getTime());
		base.setLog_time(ts);

        base.setOrder_id(order.getBillCode());
        base.setMenu_path("reviewT2OrderReviewDetailList");
        base.setMessage(result.getReturnMsg());

        baseErrService.adderr(base);
        
        return result;
    }


    public OutType ReleaseFrostT2Order(String omsOrderId, String reason) throws Exception {
        try {
//            String path = "file:"
//                          + this.getClass()
//                              .getResource(wsdlLocation + "/QueryFrostOrderFromEHAIERToOMS.wsdl")
//                              .getPath();
//            URL url = new URL(path);
        	URL url = this.getClass().getResource(
        			wsdlLocation + "/QueryFrostOrderFromEHAIERToOMS.wsdl");
            QueryFrostOrderFromEHAIERToOMS_Service service = new QueryFrostOrderFromEHAIERToOMS_Service(
                url);

            QueryFrostOrderFromEHAIERToOMS soap = service.getQueryFrostOrderFromEHAIERToOMSSOAP();
            InType in = new InType();
            log.info("T+2订单解除冻结, " + " id : " + omsOrderId);
            in.setOrderCode(omsOrderId);
            in.setDetailCode("10");
            in.setFromSystem("EHAIER");
            in.setOperationFlage("S");
            if (!"".equals(reason)) {
                in.setCancelReason(reason);
            }
            OutType output = soap.queryFrostOrderFromEHAIERToOMS(in);
            //System.out.println("T+2订单撤销" + " id : " + order.getOms_id() + " ResponseData:"
            //                   + JsonUtil.toJson(output));
            log.info("T+2订单解除冻结, " + " oms_order_id: " + omsOrderId + " output:" + JsonUtil.toJson(output));
            return output;
        } catch (Exception e) {
            log.error("T+2订单解除冻结时,发生未知异常,oms_order_id:" + omsOrderId + ",reason:" + reason, e);
            return null;
        }
    }

    /**
     * T+3预测备料与OMS同步接口
     * @param l_items
     * @return 0:成功执行 -1:部分数据同步错误
     */
    public int syncT3OrderToOMS(List<PredictingStockItem> l_items) {
        int returnCode = 0;
        try {
//            String path = "file:"
//                          + this.getClass()
//                              .getResource(wsdlLocation + "/TransForecastInfoFromOMS.wsdl")
//                              .getPath();
//            URL url = new URL(path);
        	URL url = this.getClass().getResource(
        			wsdlLocation + "/TransForecastInfoFromOMS.wsdl");
            TransForecastInfoFromOMS_Service service = new TransForecastInfoFromOMS_Service(url);
            TransForecastInfoFromOMS soap = service.getTransForecastInfoFromOMSSOAP();
            String sysName = "EHAIER";
            for (PredictingStockItem item : l_items) {
                try {
                	com.haier.svc.purchase.omst3.RequestData requestData = new com.haier.svc.purchase.omst3.RequestData();
                    //FIXME 此处需要完善对应关系
                    requestData.setSysName(sysName);
                    requestData.setFromSystem("CBS_WEEK");
                    requestData.setOmsRole("BDM");
                    requestData.setRoleChannel("1020");
                    requestData.setCustomerCode("");
                    requestData.setCustomerName("");
                    requestData.setProductCode(item.getMaterials_id());
                    requestData.setT3Num(item.getT3_require_prediction());
                    requestData.setT4Num(item.getT4_require_prediction());
                    requestData.setT5Num(item.getT5_require_prediction());
                    requestData.setT6Num(item.getT6_require_prediction());
                    requestData.setT7Num(item.getT7_require_prediction());
                    requestData.setT8Num(item.getT8_require_prediction());
                    requestData.setT9Num(item.getT9_require_prediction());
                    requestData.setT10Num(item.getT10_require_prediction());
                    requestData.setT11Num(item.getT11_require_prediction());
                    requestData.setT12Num(item.getT12_require_prediction());
                    requestData.setT13Num(item.getT13_require_prediction());

                    com.haier.svc.purchase.omst3.ResponseData responseData = soap
                        .transForecastInfoFromOMS(requestData);

                    if (responseData.getStatus().equalsIgnoreCase("F")) {
                        item.setFlow_flag(3);
                        item.setError_msg(responseData.getReason());
                        returnCode = -1;
                        log.error("OMS T+3提交失败,返回" + JsonUtil.toJson(responseData) + " 数据:"
                                  + JsonUtil.toJson(requestData));
                    } else if (responseData.getStatus().equalsIgnoreCase("S")) {
                        item.setFlow_flag(2);
                        //item.setError_msg(responseData.getReason());
                        log.info("OMS T+3提交成功,返回" + JsonUtil.toJson(responseData));
                    }
                } catch (Exception e) {
                    log.error("OMS T+3上报失败，发生未知异常", e);
                    returnCode = -1;
                    item.setFlow_flag(3);
                    item.setError_msg(e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("OMS T+3上报失败，发生未知异常", e);
            returnCode = -1;
        }

        return returnCode;
    }

    /**
     * 调用bdm接口
     * @param params
     * @return
     */
    public List<com.haier.svc.bean.transforecastpracticalfromb2ctooms.OutType> BdmOrder(Map<String, Object> params) {
        List<com.haier.svc.bean.transforecastpracticalfromb2ctooms.OutType> out = new ArrayList<com.haier.svc.bean.transforecastpracticalfromb2ctooms.OutType>();
        try {
            //角色
            String omsRole = ((String) params.get("omsRole"));
            //渠道id
            String roleChannel = ((String) params.get("roleChannel"));
            //工贸(可选)
            String tradeCode = (String) params.get("tradeCode");
            //客户（可选）
            String customerCode = (String) params.get("customerCode");
            //客户名称(可选)
            String customerName = (String) params.get("customerName");
            //系统
            String fromSystem = (String) params.get("fromSystem");
            //型号编码
            String itemcode = (String) params.get("itemcode");
            //型号名称
            String itemname = (String) params.get("itemname");

//            String path = "file:"
//                          + this
//                              .getClass()
//                              .getResource(
//                                  wsdlLocation + "/TransForecastPracticalFromB2CToOMS.wsdl")
//                              .getPath();
//            URL url = new URL(path);
            URL url = this.getClass().getResource(
        			wsdlLocation + "/TransForecastPracticalFromB2CToOMS.wsdl");
            TransForecastPracticalFromB2CToOMS_Service service = new TransForecastPracticalFromB2CToOMS_Service(
                url);

            TransForecastPracticalFromB2CToOMS soap = service
                .getTransForecastPracticalFromB2CToOMSSOAP();
            if ("CBS_DS".equals(fromSystem)) {
                out = soap.transForecastPracticalFromB2CToOMS(customerCode, customerName,
                    fromSystem, omsRole, roleChannel, tradeCode, itemcode, itemname);
                log.info("查询BDM样表");
            }
            //CBS_DSA是创建BDM样表
            if ("CBS_DSA".equals(fromSystem)) {
                out = soap.transForecastPracticalFromB2CToOMS(customerCode, customerName,
                    fromSystem, omsRole, roleChannel, tradeCode, itemcode, itemname);
                log.info("创建BDM样表,接口参数：" + JsonUtil.toJson(params) + ";返回参数："
                         + JsonUtil.toJson(out));
            }
            //CBS_DSD是删除BDM样表
            if ("CBS_DSD".equals(fromSystem)) {
                out = soap.transForecastPracticalFromB2CToOMS(customerCode, customerName,
                    fromSystem, omsRole, roleChannel, tradeCode, itemcode, itemname);
                log.info("删除BDM样表,接口参数 :" + JsonUtil.toJson(params) + ";返回参数："
                         + JsonUtil.toJson(out));
            }
        } catch (Exception ex) {
            log.error("调用BDM样表接口时，发生未知异常！", ex);
        }
        return out;
    }
}