package com.haier.afterSale.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceQueueData;
import com.haier.eis.service.EisInterfaceDataLogService;
import com.haier.eis.service.EisInterfaceQueueDataService;
//import com.haier.shop.model.OrdersNew;
//import com.haier.shop.model.*;
//import com.haier.shop.service.*;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvWarehouse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.afterSale.service.ItemService;
import com.haier.afterSale.services.InvoiceNewServiceImpl;
import com.haier.afterSale.services.OrderServiceImpl;
import com.haier.afterSale.services.StockCommonServiceImpl;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.stock.model.InvSection;

/**
 * eis接口数据查询辅助工具类
 * @Filename: HelpUtils.java
 * @Version: 1.0
 * @Author: 施进成
 * @Email: shijincheng@ehaier.com
 *
 */
@Service
public class HelpUtils {

    private final static Logger      logger       = LogManager.getLogger(HelpUtils.class);
    @Autowired
    private OrderServiceImpl             orderService;

    @Autowired
    private ShopMemberInvoicesService shopMemberInvoicesService;

    @Autowired
    private CustomerCodesService customerCodesService;

    @Autowired
    private ItemService              itemService;
    @Autowired
    private ProductActivitiesService productActivitiesService;
    @Autowired
    private GroupOrdersService groupOrdersService;
    @Autowired
    private ShopTaoBaoGroupsService shopTaoBaoGroupsService;
    @Autowired
    private EisInterfaceDataLogService eisInterfaceDataLogService;

    private Map<String, String>      reasons      = new HashMap<String, String>();
    @Autowired
    private StockCommonServiceImpl       stockCommonService;
    private String                   wsdlLocation = "/wsdl";
    @Autowired
    private EisInterfaceQueueDataService eisInterfaceQueueDataService;
    @Autowired
    private InvoiceNewServiceImpl           invoiceService;

    public String getInvStockChannelCodeBySource(String source) {
        ServiceResult<String> rs3 = stockCommonService.getChannelCodeByOrderSource(source);
        if (!rs3.getSuccess())
            throw new RuntimeException("通过订单来源向库存服务请求渠道编码发生错误:" + rs3.getMessage());
        return rs3.getResult();
    }

    /**
     * 查询渠道
     * @param cOrderSn
     * @param source
     * @return
     */
    public String getChannel(String cOrderSn, String source) {
        if (cOrderSn.endsWith("J")) {//京东
            return "DS03";
        }
        if ("HPB2B".equalsIgnoreCase(source)) {//海朋归属大客户渠道
            return "DS03";
        }
        if ("TOPXB".equalsIgnoreCase(source) || "TOPFENXIAOXB".equalsIgnoreCase(source)
                || "TOPDHSC".equalsIgnoreCase(source)) {//新宝归属淘宝渠道
            return "DS02";
        }
        if ("RRS".equalsIgnoreCase(source) || "DXS".equalsIgnoreCase(source)) {//日日顺订单
            return "DS05";
        }
        if ("TONGSHUAI".equals(source) || "TONGSHUAIFX".equals(source)) {//统帅
            return "DS06";
        }
        if ("YIHAODIAN".equalsIgnoreCase(source) || "YHD".equalsIgnoreCase(source)) {//一号店订单
            return "DS11";
        }
        if ("CCBSC".equals(source) || "CCBSR".equals(source) || "95533".equals(source)) {//建行订单
            return "DS12";
        }
        if ("GMZX".equals(source)) {//国美在线订单
            return "DS10";
        }
        if ("YMX".equals(source)) {//亚马逊
            return "DS09";
        }
        if ("YHDZY".equals(source)) {//电商平台-1号店自营
            return "DS15";
        }
        if ("ICBC".equals(source)) {//平台大客户-工商银行
            return "DS13";
        }

        if ("SNYG".equals(source)) {//电商平台-苏宁易购旗舰店
            return "DS16";
        }
        //新加的sap渠道码走下面的流程    ---wyj-2015-04-10
        try {
            ServiceResult<InvChannel2OrderSource> result = stockCommonService
                    .getSapChannelCodeAndCustomerCode(source);
            if (result == null || !result.getSuccess()) {
                String message = result != null ? result.getMessage() : "result返回结果为null";
                throw new BusinessException("获取sap渠道码：调用sap渠道码接口时出错！错误信息：" + message);
            } else {
                InvChannel2OrderSource invc2os = result.getResult();
                if (invc2os != null && invc2os.getSapChannelCode() != null
                        && !invc2os.getSapChannelCode().equals("")) {
                    return invc2os.getSapChannelCode();
                }
            }
        } catch (Exception e) {
            logger.error("获取sap渠道码异常," + e.getMessage() + "\n错误信息:", e);
            throw new BusinessException(e.getMessage());
        }

        String channel = getInvStockChannelCodeBySource(source);
        if (StringUtil.isEmpty(channel)) {
            throw new BusinessException("订单来源对应的渠道不存在：" + source);
        }
        if (InvStockChannel.CHANNEL_DAKEHU.equals(channel)) {
            return "DS03";
        }
        if (InvStockChannel.CHANNEL_SHANGCHENG.equals(channel)) {
            return "DS01";
        }
        if (InvStockChannel.CHANNEL_TAOBAO.equals(channel)) {
            return "DS02";
        }
        if (InvStockChannel.JD.equals(channel)) {
            return "DS04";
        }
        if ("MK".equalsIgnoreCase(channel)) {
            return "DS07";
        }
        return "";
    }

    /**
     * 获取退货原因code
     * @param reason
     * @return
     */
    public String getReasonCode(String reason) {
        return reasons.get(reason);
    }

    /**
     * 记录eis接口队列数据
     * @param queueData
     * @return
     */
    public Integer recordEisInterfaceQueueData(EisInterfaceQueueData queueData) {
        Integer countRow = null;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("sourceId", queueData.getSourceId());
            params.put("source", queueData.getSource());
            params.put("billType", queueData.getBillType());
            List<EisInterfaceQueueData> queueDatas = eisInterfaceQueueDataService
                    .queryEisInterfaceQueueData(params);//只会有一条数据
            Date now = DateUtil.currentDateTime();
            queueData.setUpdateTime(now);
            if (queueDatas != null && queueDatas.size() > 0) {
                EisInterfaceQueueData data = queueDatas.get(0);//只会有一条数据
                queueData.setId(data.getId());
                queueData.setOldStatus(data.getStatus());
                countRow = eisInterfaceQueueDataService.update(queueData);
            } else {
                queueData.setAddTime(now);
                countRow = eisInterfaceQueueDataService.insert(queueData);
            }
        } catch (Exception e) {
            logger.error("[To Sap]写入EisInterfaceQueueData发生异常，" + e.getMessage());
            logger.error("EisInterfaceQueueData:" + JsonUtil.toJson(queueData));
        }
        return countRow;
    }

    /**
     * 记录eis接口日志
     * @param log
     * @return
     */
    public Integer recordEisInterfaceDataLog(EisInterfaceDataLog log) {
        try {
            eisInterfaceDataLogService.insert(log);
        } catch (Exception e) {
            logger.error("记录EisInterfaceDataLog发送异常，" + e);
            logger.error("EisInterfaceDataLog:" + JsonUtil.toJson(log));
        }

        return (log.getId() == null ? 0 : log.getId());
    }

    /**
     * 获取wsdl文件URL
     * @param wsdlFile
     * @return
     */
    public URL getWSDLURL(String wsdlFile) {
        try {
            String path = "file:" + this.getClass().getResource(wsdlLocation + "/" + wsdlFile).getPath();
            return new URL(path);
        } catch (Exception e) {
            logger.error("WSDL文件路径配置错误或WSDL文件不存在：" + wsdlFile);
            throw new BusinessException("解析WSDL文件失败，配置错误");
        }
    }

    /**
     * 获取wsdl文件路径
     * @param wsdlFile
     * @return
     */
    public String getWSDLPATH(String wsdlFile) {
        try {
            String path = "file:"
                    + this.getClass().getResource(wsdlLocation + "/" + wsdlFile).getPath();
            return path;
        } catch (Exception e) {
            logger.error("WSDL文件路径配置错误或WSDL文件不存在：" + wsdlFile);
            throw new BusinessException("解析WSDL文件失败，配置错误");
        }
    }

    public String getCustormCode(OrderProductsNew orderProducts, OrdersNew ordersNew, BigDecimal price) {
        //2016-02-22
        //一分钱订单优先级高于渠道，CBS推送SAP客户码前，判定订单金额，如果是0元订单，则按照一分钱订单逻辑，推送客户是A89；
        //如果订单金额就是一分钱，则客户码不推送A89，按照各个渠道对应的客户码规则推送。
        OrdersNew order = ordersNew;//不改变传入的订单信息
        price = computeInvoicePrice(order, orderProducts, false);
        if (new BigDecimal("0").compareTo(price) == 0) {
            return "A89";
        }
        //2016-02-22
        //当时对历史单的客户码要求：对历史数据的处理方式请按照如下规则执行：1月1号及以后新发生的订单，其推送SAP出库、红冲及二次开票按照新规则执行；
        //1月1号之前发生的订单，还按照原客户码推送规则执行。
        //目前出现一些关联订单，财务要求按照原关联网单的客户码规则上账，所以需要对其规则补充一下：对于网单有关联订单，
        //其客户码推送规则按照原关联网单的订单发生日期的客户码推送规则执行（或者系统加一个判定：网单如果有关联网单，
        //则推送客户码的订单日期判定并非为该网单的订单日期，而是原网单的订单日期）。例如，原网单在2016年12月5号发生，
        //客户码推送A69，新关联网单在2016年2月2号产生（目前按照新的一次性客户规则，有可能记到A1X等新一次性客户上），也要求按照原网单的客户吗A69上账
        if (!StringUtil.isEmpty(order.getRelationOrderSn(), true)
                && !"新单".equals(order.getRelationOrderSn())) {
            ServiceResult<OrdersNew> rs2 = orderService.getByOrderSn(order.getRelationOrderSn());
            if (!rs2.getSuccess())
                throw new BusinessException("向订单服务请求订单信息出现错误:" + rs2.getMessage());
            OrdersNew orderTemp = rs2.getResult();
            if (orderTemp == null) {
                throw new BusinessException("订单号：" + order.getOrderSn() + "的关联订单"
                        + order.getRelationOrderSn() + "查询不到！");
            }
            if (orderTemp.getAddTime().intValue() < 1451577600) {
                order = orderTemp;
            }
        }
        //2015年9月1号及以后新发生的订单，其推送出库、红冲及二次开票按照新规则执行：
        //OTO线上订单传送SAP出库、红冲及二次开票，其客户码推送至A0T，其优先级高于订单来源；后台录单和专项开票，推送SAP时，客户也推送A0T。
        //        if (orderProducts != null && orderProducts.getO2oType().equals(2)) {
        //            if (order.getAddTime().intValue() >= 1441036800) {
        //                if (new BigDecimal("0.01").compareTo(price) == 0) {
        //                    return "A89";
        //                }
        //                return "A0T";
        //            }
        //        }
        if (orderProducts != null && orderProducts.getO2oType().equals(2)) {
            if (order.getAddTime().intValue() >= 1441036800) {
                return "A0T";
            }
        }

        String source = order.getSource();

        //2015-11-18
        //对天猫洗衣机和冰冷渠道两个订单来源的一分钱订单推送SAP客户码进行如下调整 ：
        //1、订单来源“海尔冰冷官方旗舰店”，原推送渠道为DS22，客户码均推送为A0O；现调整为：该订单来源倘若出现一分钱订单，CBS和ehaier端推送SAP订单出库、红冲及二次开票接口时，推送渠道仍为DS22，客户码推送为A89；该订单来源的后台录单和专项开票的一分钱订单，推送SAP时，客户也推送A89；除一分钱订单外，其他金额的订单仍按照原渠道DS22及客户码A0O推送。
        //2、订单来源“海尔洗衣机官方旗舰店”，原推送渠道为DS21，客户码均推送为A0P；现调整为：该订单来源倘若出现一分钱订单，CBS和ehaier端推送SAP订单出库、红冲及二次开票接口时，推送渠道仍为DS21，客户码推送为A89；该订单来源的后台录单和专项开票的一分钱订单，推送SAP时，客户也推送A89；除一分钱订单外，其他金额的订单仍按照原渠道DS21及客户码A0P推送。
        //WASHER(海尔洗衣机官方旗舰店)      FRIDGE(海尔冰冷官方旗舰店)

        //2015-12-22
        //应财务利于清账的要求，对天猫摩卡渠道的订单来源的一分钱订单推送SAP客户码进行如下调整 ：
        //订单来源“mooka模卡官方旗舰店”，原推送渠道为DS07，客户码均推送为A0Q；现调整为：该订单来源倘若出现一分钱订单，CBS和ehaier端推送SAP订单出库、红冲及二次开票接口时，推送渠道仍为DS07，客户码推送为A89；该订单来源的后台录单和专项开票的一分钱订单，推送SAP时，客户也推送A89；除一分钱订单外，其他金额的订单仍按照原渠道DS07及客户码A0Q推送。
        if (("WASHER".equalsIgnoreCase(source) || "FRIDGE".equalsIgnoreCase(source) || "TMMK"
                .equalsIgnoreCase(source))
                //            && new BigDecimal("0.01").compareTo(price) == 0) {
                && new BigDecimal("0").compareTo(price) == 0) {
            return "A89";
        }

        //2015-12-25
        //订单来源（MSTORE）：微店，且支付方式：内部结算单，推送SAP客户时需要根据填写的开票户头匹配客户编码推送SAP，其他支付方式仍推送客户：A0R。
        MemberInvoices memberInvoices = shopMemberInvoicesService.getByOrderId(Integer.parseInt(order.getId()));
        if ("MSTORE".equalsIgnoreCase(source)
                && "insidestatement".equalsIgnoreCase(order.getPaymentCode()) && memberInvoices != null
                && !StringUtil.isEmpty(memberInvoices.getInvoiceTitle(), true)) {
            CustomerCodes customerCodes = customerCodesService.getByTitle(memberInvoices
                    .getInvoiceTitle());
            if (customerCodes == null) {
                logger.info("CustomerCode 不存在，title:" + memberInvoices.getInvoiceTitle());
            } else {
                return customerCodes.getCode();
            }
        }

        //2016-10-28
        //订单来源：GQGYS[天猫分销]2016-10-29之前产生的网单，其红冲及二次开票，客户码按照原网单客户码规则推送至A1W。
        if ("GQGYS".equalsIgnoreCase(source) && order.getAddTime().intValue() < 1477670400) {
            return "A1W";
        }

        //2016-03-07
        // 商城新增订单来源：YHDTS   一号店统帅官方旗舰店，需CBS和EHAIER推送SAP数据进行如下调整：CBS和ehaier端推送SAP订单出库、红冲、二次开票及销售退货接口时，推送渠道为DS11，客户码推送为8800046354；后台录单和专项开票，推送SAP时，客户也推送8800046354。
        if (order.getIsBackend().intValue() == 1 && "YHDTS".equalsIgnoreCase(source)) {
            return "8800046354";
        }
        //2016-03-10
        //2、（1）后台录单，且订单来源为：企业订单_大楼订单（DALOU），且支付方式是“内部结算”、“客户预付款”、“支付宝”，发票抬头与客户编码表名称匹配，匹配对应的客户编码按照客户码推送SAP;匹配不到客户码的，推送A83；支付方式非“内部结算”、“客户预付款”、“支付宝”的其他支付方式，推送固定客户码“A83”。
        //（2）后台录单，订单来源非企业订单_大楼订单（DALOU），首先按照支付方式是“内部结算”、“客户预付款”，发票抬头与客户编码表名称匹配，匹配对应的客户编码按照客户码推送SAP；匹配不到客户码的，按渠道规则对应的客户码推送；再匹配不到的，推送A1T；
        if ("DALOU".equalsIgnoreCase(source) && order.getIsBackend().intValue() == 1) {
            if ("insidestatement".equalsIgnoreCase(order.getPaymentCode())
                    || "prepaid".equalsIgnoreCase(order.getPaymentCode())
                    || "alipay".equalsIgnoreCase(order.getPaymentCode())) {
                if (memberInvoices != null
                        && !StringUtil.isEmpty(memberInvoices.getInvoiceTitle(), true)) {
                    CustomerCodes customerCodes = customerCodesService.getByTitle(memberInvoices
                            .getInvoiceTitle());
                    if (customerCodes == null) {
                        return "A83";
                    } else {
                        return customerCodes.getCode();
                    }
                } else {
                    return "A83";
                }
            } else {
                return "A83";
            }
        }
        //1、订单来源：各订单来源推送SAP出库、红冲及二次开票渠道代码保持原有不变，只更改客户编码。例如，订单来源：商城订单，推送SAP出库、红冲及二次开票渠道代码仍为DS01不变，推送客户由原来的A69变更为A1X（具体详见附件《A69客户推送规则》标识客户码为蓝色部分；备注中为“不调整”的部分，为财务要求取消的订单来源，在具体业务中会放弃该批订单来源，技术部门暂对CBS、EHAIER及SAP不予任何调整，以免对后续业务变更造成不便）。
        //2、后台录单：后台录单并且支付方式为“内部结算”或“客户预付款”：发票抬头与客户编码表名称匹配;配备到之后，填对应的编码；匹配不到的，如果订单来源为附件中备注为“客户码变更”部分，则按照以上第一条规则中“订单来源”推送客户规则执行；倘若订单来源仍旧匹配不到，则客户推送至A1T。
        //3、专项开票：专项开票且支付方式为“内部结算”或“客户预付款”, 发票抬头包含”中国建设银行”的，客户编码对应8800040597;发票抬头与客户编码表名称匹配;配备到之后，填对应的编码；匹配不到的，如果订单来源为附件中备注为“客户码变更”部分，则按照以上第一条规则中“订单来源”推送客户规则执行；倘若订单来源仍旧匹配不到，则客户推送至A1T。
        //      另，此需求请与2016年1月1号前上线，对历史数据的处理方式请按照如下规则执行：1月1号及以后新发生的订单，其推送SAP出库、红冲及二次开票按照新规则执行；1月1号之前发生的订单，还按照原客户码推送规则执行。例如A网单在12月20号发生，该网单客户码为A69，1月5号发生红冲及二次开票，那么该笔网单在1月5号推送红冲及二次开票仍按照原客户码规则即A69推送。
        if (order.getAddTime().intValue() >= 1451577600) {
            if (order.getIsBackend().intValue() == 1
                    && ("insidestatement".equalsIgnoreCase(order.getPaymentCode()) || "prepaid"
                    .equalsIgnoreCase(order.getPaymentCode()))) {
                if (memberInvoices != null
                        && !StringUtil.isEmpty(memberInvoices.getInvoiceTitle(), true)) {
                    CustomerCodes customerCodes = customerCodesService.getByTitle(memberInvoices
                            .getInvoiceTitle());
                    if (customerCodes == null) {
                        String customerSource = customerBySource(source);
                        if (!"".equals(customerSource)) {
                            return customerSource;
                        } else {
                            return "A1T";
                        }
                    } else {
                        return customerCodes.getCode();
                    }
                } else {
                    String customerSource = customerBySource(source);
                    if (!"".equals(customerSource)) {
                        return customerSource;
                    } else {
                        return "A1T";
                    }
                }
            } else {
                String customerSource = customerBySource(source);
                if (!"".equals(customerSource)) {
                    return customerSource;
                }
            }
        }

        //2014年3月12日1号店业务现将“上海传绩电子商务有限公司”所有的业务 切换至“纽海电子商务（上海）有限公司
        if ("YIHAODIAN".equalsIgnoreCase(source) || "YHD".equalsIgnoreCase(source)) {//一号店订单
            return "8800046354";
        }

        if ("YHDZY".equals(source)) {//电商平台-1号店自营
            return "8800049539";
        }

        if ("TONGSHUAI".equals(source) || "TONGSHUAIFX".equals(source)) {//统帅订单
            return "A92";
        }

        if ("GMZX".equals(source)) {//国美在线订单
            return "8800042421";
        }

        if ("CCBSC".equals(source) || "CCBSR".equals(source) || "95533".equals(source)) {//建行订单
            return "8800040597";
        }

        if ("YMX".equals(source)) {//亚马逊
            return "8800149760";
        }

        if ("ICBC".equals(source)) {//平台大客户-工商银行
            return "8800049167";
        }

        if ("SNYG".equals(source)) {//电商平台-苏宁易购旗舰店
            return "8800049541";
        }

        //2016-2-29
        //为确保数据的严谨性，请对2016年3月1号之前的订单，仍按照原客户码规则推送；
        //2016年3月1号之后的订单，按照新客户吗规则推送。
        //红票及二次开票，也根据订单日期做判定，如A订单2月19号创建，发票红冲再3月2号，那么红冲客户码仍按照原客户码规则执行。
        if (order.getAddTime().intValue() < 1456761600) {
            if (order.getSource().equalsIgnoreCase("CK")
                    || order.getSource().equalsIgnoreCase("CK_MOBILE")) {
                return "A69";
            }
        }

        //HP不良品换货的客户码获取方式，根据库位获得工贸代码作为客户码    ---wyj-2015-05-14
        if ("BLPHH".equals(source)) {//电商平台-HP不良品换货
            try {
                //哪买哪退订单年底电商和集团工贸核算不一致，需要调整对应的记账工贸信息
                //订单来源为BLPHH的订单：
                //1.1.系统自动分配的网单库位为 SDWA 或者ZBWA，推送sap出库接口信息时，售达方信息推送 9109000360
                //1.2.系统自动分配的网单库位为 JNWA 或者LYWA，推送sap出库接口信息时，售达方信息推送   9109000361
                //1.3.系统自动分配的网单库位为 JOWA 或者WFWA，推送sap出库接口信息时，售达方信息推送  9109000362
                //2.1如果有多层级场景，转运库位为SDWA 或者ZBWA，推送sap出库接口信息时，售达方信息也是推送 9109000360
                //                    转运库位为JNWA 或者LYWA，推送sap出库接口信息时，售达方信息也是推送 9109000361
                //                    转运库位为JOWA 或者WFWA，推送sap出库接口信息时，售达方信息也是推送 9109000362
                String sCode = orderProducts.getSCode();
                if (!StringUtil.isEmpty(orderProducts.getTsCode())) {
                    sCode = orderProducts.getTsCode();
                }
                //                if (sCode.equalsIgnoreCase("SDWA") || sCode.equalsIgnoreCase("ZBWA")) {
                //                    return "9109000360";
                //                }
                //                if (sCode.equalsIgnoreCase("JNWA") || sCode.equalsIgnoreCase("LYWA")) {
                //                    return "9109000361";
                //                }
                //                if (sCode.equalsIgnoreCase("JOWA") || sCode.equalsIgnoreCase("WFWA")) {
                //                    return "9109000362";
                //                }
                ServiceResult<InvSection> result = stockCommonService.getSectionByCode(sCode);
                if (result == null || !result.getSuccess()) {
                    String message = result != null ? result.getMessage()
                            : "获取section对象时，result返回结果为null";
                    throw new BusinessException("获取sap客户码：调用section服务接口时出错！错误信息：" + message);
                } else {
                    InvSection invsec = result.getResult();
                    if (invsec != null && invsec.getWhCode() != null
                            && !invsec.getWhCode().equals("")) {
                        try {
                            ServiceResult<InvWarehouse> result_wh = stockCommonService
                                    .getWarehouse(invsec.getWhCode());
                            if (result_wh == null || !result_wh.getSuccess()) {
                                String message = result_wh != null ? result_wh.getMessage()
                                        : "获取section对象时，result返回结果为null";
                                throw new BusinessException("获取sap客户码：获取section对象接口时出错！错误信息："
                                        + message);
                            } else {
                                InvWarehouse iwh = result_wh.getResult();
                                if (iwh != null && iwh.getSapCustomerCode() != null
                                        && !iwh.getSapCustomerCode().equals("")) {
                                    return iwh.getSapCustomerCode();
                                } else {
                                    String message = result_wh != null && !result_wh.getSuccess() ? result_wh
                                            .getMessage()
                                            : "获取InvWarehouse对象时，客户码（getSapCustomerCode）为空";
                                    throw new BusinessException(
                                            "获取sap客户码：获取InvWarehouse对象时接口时出错！错误信息：" + message);
                                }
                            }
                        } catch (Exception e) {
                            logger.error("获取sap客户码异常," + e.getMessage() + "\n错误信息:", e);
                            throw new BusinessException(e.getMessage());
                        }
                    } else {
                        String message = result != null && !result.getSuccess() ? result
                                .getMessage() : "获取section对象时，库位（sCode）为空";
                        throw new BusinessException("获取sap客户码：获取section对象时出错！错误信息：" + message);
                    }
                }
            } catch (Exception e) {
                logger.error("获取sap客户码异常," + e.getMessage() + "\n错误信息:", e);
                throw new BusinessException(e.getMessage());
            }
        }

        if ("SCFX".equals(source) && memberInvoices != null
                && "青岛信创信息科技有限公司".equals(memberInvoices.getInvoiceTitle())) {//商城订单-分销渠道,且发票抬头为：青岛信创信息科技有限公司
            return "8800048176";
        }

        //2016-11-4
        //商城订单来源：SCFX 商城订单-分销渠道，倘若发票抬头为：北国商城股份有限公司，则客户码推送为8700010322
        if ("SCFX".equals(source) && memberInvoices != null
                && "北国商城股份有限公司".equals(memberInvoices.getInvoiceTitle())) {
            return "8700010322";
        }

        //2016-01-05
        //商城订单-分销渠道，且发票抬头为：青岛信创信息科技有限公司，客户码均推送至8800048176；该订单来源发票抬头不是青岛信创信息科技有限公司，客户码推送A1X。
        if (order.getAddTime().intValue() >= 1451577600 && "SCFX".equalsIgnoreCase(source)) {
            return "A1X";
        }

        //新加的客户码通过这个服务获取，不在手动修改代码了    ---wyj-2015-04-10
        try {
            ServiceResult<InvChannel2OrderSource> result = stockCommonService
                    .getSapChannelCodeAndCustomerCode(order.getSource());
            if (result == null || !result.getSuccess()) {
                String message = result != null ? result.getMessage() : "result返回结果为null";
                throw new BusinessException("获取sap客户码：调用sap客户码接口时出错！错误信息：" + message);
            } else {
                InvChannel2OrderSource invc2os = result.getResult();
                if (invc2os != null && invc2os.getCustomerCode() != null
                        && !invc2os.getCustomerCode().equals("")) {
                    return invc2os.getCustomerCode();
                }
            }
        } catch (Exception e) {
            logger.error("获取sap客户码异常," + e.getMessage() + "\n错误信息:", e);
            throw new BusinessException(e.getMessage());
        }

        String paymentCode = order.getPaymentCode();

        if (("RRS".equals(source))
                || ("DXS".equalsIgnoreCase(source))
                || (order.getIsBackend().compareTo(1) == 0 && ("prepaid".equalsIgnoreCase(paymentCode) || "insidestatement"
                .equalsIgnoreCase(paymentCode)))) {
            if (memberInvoices == null) {
                logger.info("订单（" + order.getId() + ")没开发票，或发票信息不存在");
                return null;
            }
            if ("北京京东世纪信息技术有限公司".equals(memberInvoices.getInvoiceTitle()))
                return "C200065928";

            String title = memberInvoices.getInvoiceTitle();
            if (title == null)
                title = "";
            title = title.replaceAll("\\（", "(");
            title = title.replaceAll("\\）", ")");

            CustomerCodes customerCodes = customerCodesService.getByTitle(title);

            if (customerCodes == null) {
                logger.info("CustomerCode 不存在，title:" + memberInvoices.getInvoiceTitle());
                return "A69";
            }
            return customerCodes.getCode();
        } else {

            //            if (!"RRS".equals(source) && new BigDecimal("0.01").compareTo(price) == 0)
            if (!"RRS".equals(source) && new BigDecimal("0").compareTo(price) == 0)
                return "A89";
            else
                return "A69";
        }
    }

    private String customerBySource(String source) {
        if ("1".equalsIgnoreCase(source) || "C2BWASHER".equalsIgnoreCase(source)
                || "MOBILE".equalsIgnoreCase(source) || "COS".equalsIgnoreCase(source)
                || "DBJ".equalsIgnoreCase(source) || "SQXW".equalsIgnoreCase(source)
                || "XPZC".equalsIgnoreCase(source) || "PCNEW".equalsIgnoreCase(source)
                || "KDGW".equalsIgnoreCase(source) || "690CK".equalsIgnoreCase(source)
                || "ZSH".equalsIgnoreCase(source) || "DCYH".equalsIgnoreCase(source)
                || "CORPORATE".equalsIgnoreCase(source)) {
            return "A1X";
        }
        if ("TBSC".equalsIgnoreCase(source) || "TOPFENXIAO".equalsIgnoreCase(source)) {
            return "A1W";
        }
        if ("TOPBOILER".equalsIgnoreCase(source)) {
            return "A1V";
        }
        if ("TOPDHSC".equalsIgnoreCase(source) || "TOPFENXIAODHSC".equalsIgnoreCase(source)
                || "TOPSHJD".equalsIgnoreCase(source)) {
            return "A1U";
        }
        if ("DXS".equalsIgnoreCase(source)) {
            return "A0T";
        }
        return "";
    }

    /**
     *  计算开票单价(原有计算方式)
     * @param order
     * @param orderProduct
     */
    public BigDecimal computeInvoicePrice(OrdersNew order, OrderProductsNew orderProduct) {
        return computeInvoicePrice(order, orderProduct, true);
    }

    /**
     *  计算开票单价(新计算方式)
     * @param order
     * @param orderProduct
     * @param flag true代表最后如果为0元改为0.01元,false代表不更改,实际价
     */
    public BigDecimal computeInvoicePrice(OrdersNew order, OrderProductsNew orderProduct, boolean flag) {

        if (orderProduct == null || orderProduct.getCOrderSn() == null
                || orderProduct.getCOrderSn().equals("")) {
            throw new BusinessException("获取开票单价：网单为空，或网单号为空");
        }
        try {
            //获得开票含税总金额
            ServiceResult<BigDecimal> result = null;
            if (flag) {
                result = invoiceService.getInvoiceAmount(orderProduct.getCOrderSn());
            } else {
                result = invoiceService.getInvoiceAmount(orderProduct.getCOrderSn(), flag);
            }
            if (result == null || !result.getSuccess()) {
                String message = result != null ? result.getMessage() : "返回结果为null";
                throw new BusinessException("获取开票单价：调用发票价格接口时出错！错误信息：" + message);
            } else {
                //含税总金额
                BigDecimal amount = result.getResult();
                if (amount == null) {
                    throw new BusinessException("获取开票单价：调用发票价格接口返回金额为null");
                }
                if (orderProduct.getNumber() != null && orderProduct.getNumber().compareTo(0) > 0) {
                    //含税单价          =含税总金额除以网单数量
                    BigDecimal price = amount.divide(new BigDecimal(orderProduct.getNumber()), 2,
                            RoundingMode.HALF_UP);
                    return price;
                } else {
                    throw new BusinessException("获取开票单价：网单数量为0或为null");
                }
            }
        } catch (Exception e) {
            logger.error("获取开票单价异常," + e.getMessage() + "\n错误信息:", e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 查询退货信息
     * @param op
     * @return
     */
    public OrderRepairsNew getOrderRepairs(OrderProductsNew op) {
        ServiceResult<OrderRepairsNew> result = orderService.getValidByOrderProductId(op.getId());
        if (!result.getSuccess())
            throw new RuntimeException("orderService返回错误:" + result.getMessage());
        if (result.getResult() == null) {
            ServiceResult<List<OrderRepairsNew>> rs = orderService.getOrderRepairsByOrderProductId(op
                    .getId());
            if (rs==null || !rs.getSuccess())
                throw new RuntimeException("orderService-getOrderRepairsByOrderProductId 错误:"
                        + rs.getMessage());
            if (rs.getResult()!=null && rs.getResult().size() > 0)
                return rs.getResult().get(0);
        }
        return result.getResult();
    }

    /**
     * 查询订单信息
     * @param orderProducts
     * @return
     */
    public OrdersNew getOrderByWD(OrderProductsNew orderProducts) {
        if (orderProducts == null)
            return null;
        ServiceResult<OrdersNew> rs2 = orderService.getOrderById(orderProducts.getOrderId());
        if (!rs2.getSuccess())
            throw new BusinessException("向订单服务请求订单信息出现错误:" + rs2.getMessage());
        return rs2.getResult();
    }

    /**
     * 查询网单信息
     * @param cOrderSn
     * @return
     */
    public OrderProductsNew getOrderProducts(String cOrderSn) {
        ServiceResult<OrderProductsNew> rs = orderService.getOrderProductByCOrderSn(cOrderSn);
        if (!rs.getSuccess()) {
            throw new RuntimeException("向订单服务请求网单信息出现错误：" + rs.getMessage());
        }
        return rs.getResult();
    }

    public ProductsNew getProducts(Integer productId) {
        ServiceResult<ProductsNew> rs = itemService.getProductById(productId);
        if (!rs.getSuccess())
            throw new BusinessException("ItemService error:" + rs.getMessage());
        return rs.getResult();
    }

    public static final Map<String, String> KUNNR_RG_MAP = new HashMap<String, String>();

    static {
        KUNNR_RG_MAP.put("SNHEGQ", "C200076235");//苏宁海尔官方旗舰店
        KUNNR_RG_MAP.put("SNYG", "C200076234");//电商平台-苏宁易购旗舰店
    }

    public static String getKunnrRg(String source) {
        String kr = KUNNR_RG_MAP.get(source);
        if (kr != null) {
            return kr;
        }
        return "";
    }

    public static String getKunnrRgNull(String source) {
        return KUNNR_RG_MAP.get(source);
    }

    public TaoBaoGroups getTaoBaoGroups(Integer taobaoGroupId) {
        return shopTaoBaoGroupsService.get(taobaoGroupId);
    }

    public GroupOrders getGroupOrdersByDepositOrderId(Integer id) {
        return groupOrdersService.getByDepositOrderId(id);
    }

    public ProductActivities getProductActivities(Integer activityId) {
        return productActivitiesService.get(activityId);
    }

    public void setOrderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    public void setMemberInvoicesDao(ShopMemberInvoicesService shopMemberInvoicesService) {
        this.shopMemberInvoicesService = shopMemberInvoicesService;
    }

    public void setCustomerCodesDao(CustomerCodesService customerCodesService) {
        this.customerCodesService = customerCodesService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public void setProductActivitiesDao(ProductActivitiesService productActivitiesService) {
        this.productActivitiesService = productActivitiesService;
    }

    public void setGroupOrdersDao(GroupOrdersService groupOrdersService) {
        this.groupOrdersService = groupOrdersService;
    }

    public void setTaoBaoGroupsDao(ShopTaoBaoGroupsService shopTaoBaoGroupsService) {
        this.shopTaoBaoGroupsService = shopTaoBaoGroupsService;
    }

    public void setEisInterfaceDataLogDao(EisInterfaceDataLogService eisInterfaceDataLogService) {
        this.eisInterfaceDataLogService = eisInterfaceDataLogService;
    }

    public void setReasons(Map<String, String> reasons) {
        this.reasons = reasons;
    }

    public void setStockCommonService(StockCommonServiceImpl stockCommonService) {
        this.stockCommonService = stockCommonService;
    }

    public void setWsdlLocation(String wsdlLocation) {
        this.wsdlLocation = wsdlLocation;
    }

    public void setEisInterfaceQueueDataDao(EisInterfaceQueueDataService eisInterfaceQueueDataService) {
        this.eisInterfaceQueueDataService = eisInterfaceQueueDataService;
    }

    public void setInvoiceService(InvoiceNewServiceImpl invoiceService) {
        this.invoiceService = invoiceService;
    }
}
