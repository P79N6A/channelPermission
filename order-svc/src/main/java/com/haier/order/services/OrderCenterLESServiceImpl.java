package com.haier.order.services;

import java.net.URL;
import java.util.List;

import javax.xml.ws.Holder;

import com.haier.order.transforecastpracticalfromb2ctooms.OutType;
import com.haier.order.util.HelpUtils;
import com.haier.order.util.HttpServiceUtil;
import com.haier.order.util.IExecute;
import com.haier.order.util.WriteLogProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.shop.model.QueryLesStockOutType;
import com.haier.shop.model.QueryLesStockToCbs;
import com.haier.shop.model.QueryPayTimeToLes;

/**
 * LES开提单数据接口
 *
 * @Filename: LESServiceImpl.java
 * @Version: 1.0
 * @Author: weiyunjun
 * @Email: weiyunjun@ehaier.com
 */
@ConfigurationProperties(prefix = "url")
@Service
public class OrderCenterLESServiceImpl {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(OrderCenterLESServiceImpl.class);
    private String orderToLesUrl;
    @Autowired
    private HelpUtils help;

    public void setHelp(HelpUtils help) {
        this.help = help;
    }

    public String getOrderToLesUrl() {
        return orderToLesUrl;
    }

    public void setOrderToLesUrl(String orderToLesUrl) {
        this.orderToLesUrl = orderToLesUrl;
    }

    public ServiceResult<String> orderToLes(String foreignKey, final String content) {
        final ServiceResult<String> result = new ServiceResult<String>();
        try {
            return HttpServiceUtil.executeService(foreignKey,
                    EisInterfaceDataLog.INTERFACE_CODE_ORDER_TO_LES, content, getOrderToLesUrl());
        } catch (Exception e) {
            log.error("调用les开提单接口出错", e);
        }
        return result;
    }

    /**
     * 给物流提供付款时间和尾款时间
     * 传输单个订单的定金或尾款时间或者一个订单的定金和尾款记录时间
     *
     * @param foreignKey 订单号
     * @param param      订单参数
     * @return
     */
    public ServiceResult<Boolean> paytimeToLes(String foreignKey, final List<QueryPayTimeToLes> param) {
        final ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        if (param == null) {
            log.error("param为空");
            result.setSuccess(false);
            result.setMessage("传参不能为空");
            result.setResult(null);
            return result;
        }
        String inputTypeJson = JsonUtil.toJson(param);
        WriteLogProxy.writeLog(foreignKey, EisInterfaceDataLog.INTERFACE_CODE_DNF_TO_LES,
                inputTypeJson, new IExecute() {

                    public String execute() throws Exception {
                        try {
//                        URL url = new URL(getWsdlPath("QueryDNFromLEStoAPP.wsdl"));
//                        QueryDNFromLEStoAPP_Service service = new QueryDNFromLEStoAPP_Service(url);
//                        QueryDNFromLEStoAPP soap = service.getQueryDNFromLEStoAPPSOAP();
//
//                        List<ZINTWXWTLOG> input = new ArrayList<ZINTWXWTLOG>();
//                        if (param != null && param.size() > 0) {
//                            for (int i = 0; i < param.size(); i++) {
//                                ZINTWXWTLOG wxwtlog = new ZINTWXWTLOG();
//                                BeanUtils.copyProperties(param.get(i), wxwtlog);
//                                input.add(wxwtlog);
//                            }
//                        }

                            String callcode = "HAIERSC#321";
                            String source = "HAIERSC";
                            Holder<String> flag = new Holder<String>();
                            Holder<String> message = new Holder<String>();
//                        soap.queryDNFromLEStoAPP(callcode, source, input, flag, message);
                            //返回结果
                            if (flag == null || flag.value == null) {
                                result.setSuccess(false);
                                result.setMessage(message == null || message.value == null ? "响应数据为空"
                                        : message.value);
                                result.setResult(null);
                                return message == null || message.value == null ? "" : message.value;
                            } else if (flag.value.equals("S")) {
                                result.setSuccess(true);
                                result.setMessage(
                                        message == null || message.value == null ? "操作成功" : message.value);
                                result.setResult(true);
                                return flag.value + ":" + message == null || message.value == null ? ""
                                        : message.value;
                            } else {
                                result.setSuccess(false);
                                result.setMessage(
                                        message == null || message.value == null ? "操作失败" : message.value);
                                result.setResult(false);
                                return flag.value + ":" + message == null || message.value == null ? ""
                                        : message.value;
                            }
                        } catch (Exception e) {
                            log.error("发送给物流付款时间时,发生未知异常", e);
                            result.setMessage("发送给物流付款时间时,发生未知异常:" + e.getMessage());
                            result.setSuccess(false);
                            result.setResult(null);
                            throw e;
                        }
                    }
                });
        return result;
    }

    private String getWsdlPath(String wsdlFile) {
        //        String path = "file:" + this.getClass().getResource("/wsdl/" + wsdlFile).getPath();
        //        if (log.isDebugEnabled())
        //            log.debug("wsdl path:" + path);
        //        return path;
//        return help.getWsdlPath(wsdlFile);、
        return "";
    }

    public ServiceResult<QueryLesStockOutType> queryLesStock(final QueryLesStockToCbs queryLesStockToCbs) {
        final ServiceResult<QueryLesStockOutType> result = new ServiceResult<QueryLesStockOutType>();
        if (queryLesStockToCbs == null) {
            result.setSuccess(false);
            result.setMessage("传参不能为空");
            return result;
        }
        if (StringUtils.isEmpty(queryLesStockToCbs.getSecCode())) {
            result.setSuccess(false);
            result.setMessage("les库位编码不能为空");
            return result;
        }

        if (StringUtils.isEmpty(queryLesStockToCbs.getSku())) {
            result.setSuccess(false);
            result.setMessage("物料编码不能为空");
            return result;
        }

        if (StringUtils.isEmpty(queryLesStockToCbs.getFlag())) {
            result.setSuccess(false);
            result.setMessage("查询标识不能为空");
            return result;
        }

        String queryLesStockJson = JsonUtil.toJson(queryLesStockToCbs);

        WriteLogProxy.writeLog(queryLesStockToCbs.getSku(),
                EisInterfaceDataLog.INTERFACE_CODE_QUERY_LES_STOCK_TO_CBS, queryLesStockJson, new IExecute() {

                    public String execute() throws Exception {

                        try {
                            URL url = new URL(getWsdlPath("TransAccountCheckingFromCBSToLES.wsdl"));
//	                		TransAccountCheckingFromCBSToLES_Service lesService=new TransAccountCheckingFromCBSToLES_Service(url);
//	                		TransAccountCheckingFromCBSToLES soap=lesService.getTransAccountCheckingFromCBSToLESSOAP();
//	                		
//	                		//输入参数实体转换
//	                		InType inType=new InType();
//	                		inType.setFLAG(queryLesStockToCbs.getFlag());
//	                		inType.setLGORT(queryLesStockToCbs.getSecCode());
//	                		inType.setMATNR(queryLesStockToCbs.getSku());
//	                		OutType outType=soap.transAccountCheckingFromCBSToLES(inType);
//	                		
//	                		if(outType == null){
//	                			result.setSuccess(false);
//	                            result.setMessage("响应数据为空");
//	                            result.setResult(null);
//	                            return "响应数据为空";
//	                		}else{
//	                			QueryLesStockOutType lesOut=new QueryLesStockOutType();
////	                			lesOut=handleData(outType,lesOut);
//	                			result.setSuccess(true);
//                                result.setMessage("响应成功");
//                                result.setResult(lesOut);
//                                return JsonUtil.toJson(lesOut);
//	                		}
                            return "";
                        } catch (Exception e) {
                            log.error("les库存查询时,发生未知异常", e);
                            result.setMessage("les库存查询时,发生未知异常");
                            result.setSuccess(false);
                            result.setResult(null);
                            throw e;
                        }
                    }

                });
        return result;
    }

    private QueryLesStockOutType handleData(OutType outType, QueryLesStockOutType lesOut) {

        //LES库存数---CBS使用
//		if(outType.getCBSKC()!=null && !outType.getCBSKC().isEmpty()){
//			List<CBSKCType> CBSKCTypes=new ArrayList<CBSKCType>();
//			for( com.haier.cbs.base.eai.transaccountcheckingfromcbstoles.CBSKCType kc:outType.getCBSKC()){
//				CBSKCType type=new CBSKCType();
//				BeanUtils.copyProperties(kc, type);
//				CBSKCTypes.add(type);
//			}
//			lesOut.setCbskc(CBSKCTypes);
//		}
//		
        //CBS_MX表结构  LES出入库流水---CBS使用
//		if(outType.getCBSMX()!=null && !outType.getCBSMX().isEmpty()){
//			List<com.haier.cbs.base.entity.eai.CBSMXType> CBSMXTypes=new ArrayList<com.haier.cbs.base.entity.eai.CBSMXType>();
//			for(CBSMXType mx:outType.getCBSMX()){
//				com.haier.cbs.base.entity.eai.CBSMXType type=new com.haier.cbs.base.entity.eai.CBSMXType();
//				BeanUtils.copyProperties(mx, type);
//				CBSMXTypes.add(type);
//			}
//			lesOut.setCbsmx(CBSMXTypes);
//		}

        //COLLECTOR表结构    LES库存明细---财务SAP使用
//		if(outType.getCOLLECTOR()!=null && !outType.getCOLLECTOR().isEmpty()){
//			List<com.haier.cbs.base.entity.eai.COLLECTORType> COLLECTORTypes=new ArrayList<com.haier.cbs.base.entity.eai.COLLECTORType>();
//			for(COLLECTORType clllector:outType.getCOLLECTOR()){
//				com.haier.cbs.base.entity.eai.COLLECTORType type=new com.haier.cbs.base.entity.eai.COLLECTORType();
//				BeanUtils.copyProperties(clllector, type);
//				COLLECTORTypes.add(type);
//			}
//			lesOut.setCollector(COLLECTORTypes);
//		}
//		
//		//LTMX表结构  LES出入库流水---财务SAP使用
//		if(outType.getLTMX()!=null && !outType.getLTMX().isEmpty()){
//			List<com.haier.cbs.base.entity.eai.LTMXType> LTMXTypes=new ArrayList<com.haier.cbs.base.entity.eai.LTMXType>();
//			for(LTMXType limx:outType.getLTMX()){
//				com.haier.cbs.base.entity.eai.LTMXType type=new com.haier.cbs.base.entity.eai.LTMXType();
//				BeanUtils.copyProperties(limx, type);
//				LTMXTypes.add(type);
//			}
//			lesOut.setLtmx(LTMXTypes);
//		}
        return lesOut;
    }

}
