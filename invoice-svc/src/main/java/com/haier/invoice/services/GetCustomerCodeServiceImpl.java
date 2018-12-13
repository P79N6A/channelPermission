package com.haier.invoice.services;

import com.haier.common.BusinessException;
import com.haier.common.util.StringUtil;
import com.haier.invoice.model.InvoiceBizModel;
import com.haier.invoice.service.GetCustomerCodeService;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.Orders;
import com.haier.shop.service.CustomerCodesService;
import com.haier.shop.service.ShopMemberInvoicesService;
import com.haier.shop.service.ShopOrderProductsService;
import com.haier.shop.service.ShopOrdersService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取客户码
 **/
@Service
public class GetCustomerCodeServiceImpl implements GetCustomerCodeService {

    private static final Logger logger = LogManager.getLogger(GetCustomerCodeServiceImpl.class);

    @Autowired
    private ShopOrderProductsService shopOrderProductsService;
    @Autowired
    private ShopOrdersService shopOrdersService;
    @Autowired
    private ShopMemberInvoicesService shopMemberInvoicesService;
    @Autowired
    private CustomerCodesService CustomerCodesService;

    @Override
    public Map<String, String> getCustomerCode(String cOrderSn) {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            String code = "";
            OrderProducts orderProduct = shopOrderProductsService.getByCOrderSn(cOrderSn);
            if (orderProduct == null) {
                logger.error("获取客户码时根据网单号没有找到网单信息！网单号：" + cOrderSn);
                resultMap.put("Message", "获取客户码时根据网单号没有找到网单信息！");
                return resultMap;
            }
            Orders order = shopOrdersService.get(orderProduct.getOrderId());
            if (order == null) {
                logger.error("获取客户码时根据订单号没有找到订单信息！");
                resultMap.put("Message", "获取客户码时根据订单号没有找到订单信息！");
                return resultMap;
            }
            String invoiceTitle = shopMemberInvoicesService.getInvoiceTitleByOrderId(order.getId());
            if (invoiceTitle == null) {
                logger.error("获取客户码时根据订单id没有找到memberInvoices信息！");
                resultMap.put("Message", "获取客户码时没有找到订单发票信息！");
                return resultMap;
            }

//            code = getCustomerCode(invoiceTitle, order, orderProduct);
            code =  getCode(orderProduct, order, false,invoiceTitle);
            if (null != code || !"".equals(code)) {
                resultMap.put("code", code);
                resultMap.put("source", order.getSource());
                resultMap.put("sourceOrderSn", order.getSourceOrderSn());
                resultMap.put("cOrderSn", cOrderSn);
            } else {
                resultMap.put("Message", "获取客户码为空!");
            }
        } catch (Exception e) {
            resultMap.put("Message", "查询客户码时, 发生未知异常!");
            logger.error("查询客户码时, 发生未知异常：", e);
        }
        return resultMap;
    }

    /**
     * 普通订单 取得客户编码
     *
     * @param invoiceTitle
     * @param orderNew
     * @param ops
     * @return
     */
    private String getCustomerCode(String invoiceTitle, Orders orderNew, OrderProducts ops) {
        String title = "";
        String customerCode = "";
        Orders order = orderNew;
        //2016-02-22
        //一分钱订单优先级高于渠道，CBS推送SAP客户码前，判定订单金额，如果是0元订单，则按照一分钱订单逻辑，推送客户是A89；
        //如果订单金额就是一分钱，则客户码不推送A89，按照各个渠道对应的客户码规则推送。
        BigDecimal amount = InvoiceBizModel.calInvoiceAmount(ops, false);
        //节能补贴逻辑的金额计算，暂时保留
//        if (op.getEsAmount().compareTo(BigDecimal.ZERO) > 0) {
//            amount = amount.add(op.getEsAmount());
//        }

        BigDecimal price = amount.divide(new BigDecimal(ops.getNumber()), 2, RoundingMode.HALF_UP);
        if (new BigDecimal("0").compareTo(price) == 0) {
            customerCode = "A89";
            return customerCode;
        }
        if (!StringUtil.isEmpty(order.getRelationOrderSn(), true)
                && !"新单".equals(order.getRelationOrderSn())) {
            Orders orderTemp = shopOrdersService.getByOrderSn(order.getRelationOrderSn());
            if (orderTemp == null) {
                throw new BusinessException("订单号：" + order.getOrderSn() + "的关联订单"
                        + order.getRelationOrderSn() + "查询不到！");
            }
            if (orderTemp.getAddTime().intValue() < 1451577600) {
                order = orderTemp;
            }
        }
        String source = order.getSource();
        String customerSource = customerBySource(source);
        if (!"".equals(customerSource)) {
            return customerSource;
        }

        // 订单来源为：统帅日日顺乐家专卖店、统帅分销以及未来产生的统帅相关渠道 无论发票抬头是什么，均按A92编码推送
        if (Orders.ORIGIN_TONGSHUAI.equals(order.getSource())
                || Orders.ORIGIN_TSFX.equals(order.getSource())) {
            customerCode = "A92";
            return customerCode;
        }
//        // 商城新增订单来源：统帅商城移动、统帅商城PC，A90编码推送
        if (orderSource.get("ORIGIN_TSMOBILE").equals(order.getSource())
                || orderSource.get("ORIGIN_TSPC").equals(order.getSource())) {
            customerCode = "A90";
            return customerCode;
        }
//        // 商订单来源：电商平台-1号店自营
        if (orderSource.get("ORIGIN_YHDZY").equals(order.getSource())) {
            customerCode = "8800049539";
            return customerCode;
        }
//        // 商订单来源：平台大客户-工商银行订单
        if (orderSource.get("ORIGIN_ICBC").equals(order.getSource())) {
            customerCode = "8800049167";
            return customerCode;
        }
//        //  商城新增订单来源：电商平台-苏宁易购旗舰店  渠道为DS16  客户码推送为8800049541  订单source为SNYG
        if (orderSource.get("ORIGIN_SNYG").equals(order.getSource())) {
            customerCode = "8800049541";
            return customerCode;
        }
//        //  商城新增订单来源：商城订单-分销渠道,且发票抬头为：青岛信创信息科技有限公司   客户码推送为8800048176  订单source为SCFX
        if (orderSource.get("ORIGIN_SCFX").equals(order.getSource())
                && invoiceTitle != null
                && invoiceTitle.equals("青岛信创信息科技有限公司")) {
            customerCode = "8800048176";
            return customerCode;
        }
//        //2016-11-4
//        //商城订单来源：SCFX 商城订单-分销渠道，倘若发票抬头为：北国商城股份有限公司，则客户码推送为8700010322
        if (orderSource.get("ORIGIN_SCFX").equals(order.getSource())
                && invoiceTitle != null
                && invoiceTitle.equals("北国商城股份有限公司")) {
            customerCode = "8700010322";
            return customerCode;
        }
//        //2016-01-05
//        //商城订单-分销渠道，且发票抬头为：青岛信创信息科技有限公司，客户码均推送至8800048176；该订单来源发票抬头不是青岛信创信息科技有限公司，客户码推送A1X。
        if (order.getAddTime().intValue() >= 1451577600
                && orderSource.get("ORIGIN_SCFX").equalsIgnoreCase(order.getSource())) {
            return "A1X";
        }

        if (Orders.ORIGIN_YIHAODIAN.equals(order.getSource())
                || Orders.ORIGIN_YHD.equals(order.getSource())) {
            title = "纽海电子商务（上海）有限公司";
        } else if ("北京京东世纪信息技术有限公司".equals(invoiceTitle)) {
            title = "(小家电)北京京东世纪信息技术有限公司";
        } else if (order.getIsBackend().equals(1)
                && ("insidestatement".equals(order.getPaymentCode()) || "prepaid".equals(order
                .getPaymentCode()))) {
            title = invoiceTitle;
        } else if (orderSource.get("ORIGIN_JDMK").equals(order.getSource())
                || orderSource.get("ORIGIN_TMMK").equals(order.getSource())) {
            title = "青岛信创信息科技有限公司";
        } else if (orderSource.get("ORIGIN_GMZX").equals(order.getSource())) {
            title = "国美在线电子商务有限公司";
        } else if (orderSource.get("ORIGIN_FRONTDXS").equals(order.getSource())
                || orderSource.get("ORIGIN_RRS").equals(order.getSource())) {
            title = invoiceTitle;
        } else if (orderSource.get("ORIGIN_CCBSC").equals(order.getSource())
                || orderSource.get("ORIGIN_CCBSR").equals(order.getSource())
                || orderSource.get("ORIGIN_CCB").equals(order.getSource())) {
            title = "中国建设银行青岛海尔路支行";
        } else if (orderSource.get("ORIGIN_YMX").equals(order.getSource())) {
            title = "亚马逊卓越有限公司";
        }

        if ((!"".equals(title)) || (null != invoiceTitle && !"".equals(invoiceTitle.trim()))) {
            List<String> customerCodes = CustomerCodesService.getCustomerCode(title);
            if (customerCodes == null || customerCodes.isEmpty()) {
                logger.info("CustomerCode 不存在，title:" + title);
                customerCode = "A69";
            } else {
                customerCode = customerCodes.get(0);
            }
        } else {
            customerCode = "A69";
        }
        return customerCode;
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


    public String getCode(OrderProducts orderProducts, Orders order, boolean flag,String invoiceTitle) {

        if (null == orderProducts || null == order) {
            logger.error("获取客户编码时，订单、网单信息不能为null");
            return null;
        }

        //2016-02-22
        //一分钱订单优先级高于渠道，CBS推送SAP客户码前，判定订单金额，如果是0元订单，则按照一分钱订单逻辑，推送客户是A89；
        //如果订单金额就是一分钱，则客户码不推送A89，按照各个渠道对应的客户码规则推送。
        BigDecimal price = computeInvoicePrice(orderProducts, flag);
        if (new BigDecimal("0").compareTo(price) == 0) {
            return "A89";
        }

        if (orderProducts != null && orderProducts.getO2oType().equals(2)) {
            return "A0T";
        }

        //按照渠道查询客户编码
        String source = order.getSource();
        String customerCode = sourceCustomerCodeMap.get(source);
        if (null != customerCode) {
            return customerCode;
        }

        //商城新增订单来源：商城订单-分销渠道,且发票抬头为：青岛信创信息科技有限公司   客户码推送为8800048176  订单source为SCFX
        if (orderSource.get("ORIGIN_SCFX").equals(source)
                && invoiceTitle != null
                && invoiceTitle.equals("青岛信创信息科技有限公司")) {
            return "8800048176";
        }

        //商城订单来源：SCFX 商城订单-分销渠道，倘若发票抬头为：北国商城股份有限公司，则客户码推送为8700010322
        if (orderSource.get("ORIGIN_SCFX").equals(source)
                && invoiceTitle != null
                && invoiceTitle.equals("北国商城股份有限公司")) {
            return "8700010322";
        }
        //商城订单来源：SCFX 商城订单-分销渠道，倘若发票抬头为：青岛宇恒电器有限公司，则客户码推送为8700000475
        if (orderSource.get("ORIGIN_SCFX").equals(source)
                && invoiceTitle != null
                && invoiceTitle.equals("青岛宇恒电器有限公司")) {
            return "8700000475";
        }

        //商城订单-分销渠道，且发票抬头为：青岛信创信息科技有限公司，客户码均推送至8800048176；该订单来源发票抬头不是青岛信创信息科技有限公司，客户码推送A1X。
        if (orderSource.get("ORIGIN_SCFX").equalsIgnoreCase(source)) {
            return "A1X";
        }

        //根据title查询
        String title = ""; //查询使用发票抬头
        if ("北京京东世纪信息技术有限公司".equals(invoiceTitle)) {
            title = "(小家电)北京京东世纪信息技术有限公司";
        } else if (order.getIsBackend().equals(1)
                && ("insidestatement".equals(order.getPaymentCode()) || "prepaid".equals(order
                .getPaymentCode()))) {
            title = invoiceTitle;
        } else if (orderSource.get("ORIGIN_RRS").equals(source)) {
            title = invoiceTitle;
        }

        if ((!"".equals(title)) || (null != invoiceTitle && !"".equals(invoiceTitle.trim()))) {

            title = title.replaceAll("\\（", "(");
            title = title.replaceAll("\\）", ")");

            List<String> customerCodes = CustomerCodesService.getCustomerCode(title);
            if (customerCodes == null || customerCodes.isEmpty()) {
                logger.info("CustomerCode 不存在，title:" + title);
                return "A69";
            } else {
                return customerCodes.get(0);
            }
        } else {
            return "A69";
        }
    }

    /**
     * 计算单价
     *
     * @param orderProduct
     * @return
     */
    public BigDecimal computeInvoicePrice(OrderProducts orderProduct, boolean flag) {

        if (orderProduct == null) {
            logger.error("计算单价：网单对象不能为空");
            return null;
        }
        try {
            //获取网单金额
            BigDecimal amount = orderProduct.getProductAmount();
            //节能补贴逻辑的金额计算
            if (orderProduct.getEsAmount().compareTo(BigDecimal.ZERO) > 0) {
                amount = amount.add(orderProduct.getEsAmount());
            }
            //金额为零的所有网单，改为1分钱
            if (flag) {
                if (amount.compareTo(BigDecimal.ZERO) == 0
                        || amount.divide(new BigDecimal(orderProduct.getNumber()), 2, RoundingMode.HALF_UP)
                        .compareTo(BigDecimal.ZERO) == 0) {
                    BigDecimal price = new BigDecimal("0.01");
                    amount = price.multiply(new BigDecimal(orderProduct.getNumber()));
                }
            }
            return amount;
        } catch (Exception e) {
            logger.error("获取单价异常," + e.getMessage() + "\n错误信息:", e);
            throw new BusinessException(e.getMessage());
        }
    }

    //渠道-客户编码对照关系
    private static final Map<String, String> sourceCustomerCodeMap = new HashMap<>();

    static {
        sourceCustomerCodeMap.put("1", "A1X");
        sourceCustomerCodeMap.put("C2BWASHER", "A1X");
        sourceCustomerCodeMap.put("MOBILE", "A1X");
        sourceCustomerCodeMap.put("COS", "A1X");
//        sourceCustomerCodeMap.put("SCFX","A1X"); //特殊处理
        sourceCustomerCodeMap.put("CK", "A0R");
        sourceCustomerCodeMap.put("CK_MOBILE", "A0R");
        sourceCustomerCodeMap.put("DBJ", "A1X");
        sourceCustomerCodeMap.put("SQXW", "A1X");
        sourceCustomerCodeMap.put("XPZC", "A1X");
        sourceCustomerCodeMap.put("PCNEW", "A1X");
        sourceCustomerCodeMap.put("KDGW", "A1X");
        sourceCustomerCodeMap.put("HBSC", "9109000197");
        sourceCustomerCodeMap.put("CHINAUNICOM", "A69");
        sourceCustomerCodeMap.put("TBSC", "A1W");
        sourceCustomerCodeMap.put("TOPDHSC", "A1U");
        sourceCustomerCodeMap.put("TOPFENXIAO", "A1W");
        sourceCustomerCodeMap.put("TOPFENXIAODHSC", "A1U");
        sourceCustomerCodeMap.put("TOPBUYBANG", "A69");
        sourceCustomerCodeMap.put("TOPBOILER", "A1V");
        sourceCustomerCodeMap.put("TOPSHJD", "A1U");
        sourceCustomerCodeMap.put("TOPMOBILE", "A69");
        sourceCustomerCodeMap.put("TOPXB", "A69");
        sourceCustomerCodeMap.put("TOPFENXIAOXB", "A69");
        sourceCustomerCodeMap.put("TOPPAD", "A69");
        sourceCustomerCodeMap.put("ZPTH", "A69");
        sourceCustomerCodeMap.put("BLPMS", "A69");
        sourceCustomerCodeMap.put("112", "A69");
        sourceCustomerCodeMap.put("CORPORATE", "A1X");
        sourceCustomerCodeMap.put("CORPORATE_SJMG", "A69");
        sourceCustomerCodeMap.put("ZSH", "A1X");
        sourceCustomerCodeMap.put("DCYH", "A1X");
        sourceCustomerCodeMap.put("HPB2B", "A69");
        sourceCustomerCodeMap.put("JDMK", "8800048176");
        sourceCustomerCodeMap.put("DXS", "A0T");
//        sourceCustomerCodeMap.put("RRS", "A69");  //根据titile获取客户编码
        sourceCustomerCodeMap.put("TONGSHUAI", "A92");
        sourceCustomerCodeMap.put("TONGSHUAIFX", "A92");
        sourceCustomerCodeMap.put("TMMK", "A0Q");
        sourceCustomerCodeMap.put("YMX", "8800149760");
        sourceCustomerCodeMap.put("GMZX", "8800042421");
        sourceCustomerCodeMap.put("YIHAODIAN", "8800046354");
        sourceCustomerCodeMap.put("YHD", "8800046354");
        sourceCustomerCodeMap.put("CCBSR", "8800040597");
        sourceCustomerCodeMap.put("CCBSC", "8800040597");
        sourceCustomerCodeMap.put("95533", "8800040597");
        sourceCustomerCodeMap.put("ICBC", "8800049167");
        sourceCustomerCodeMap.put("TSMOBILE", "A90");
        sourceCustomerCodeMap.put("TSPC", "A90");
        sourceCustomerCodeMap.put("YHDZY", "8800049539");
        sourceCustomerCodeMap.put("YHDQWZY", "8800227497");
        sourceCustomerCodeMap.put("SNYG", "8800049541");
        sourceCustomerCodeMap.put("SNHEGQ", "8800049541");
        sourceCustomerCodeMap.put("DDW", "8800049543");
        sourceCustomerCodeMap.put("YLW", "8800049544");
        sourceCustomerCodeMap.put("YDYZ", "8800049877");
        sourceCustomerCodeMap.put("WASHER", "A0P");
        sourceCustomerCodeMap.put("FRIDGE", "A0O");
        sourceCustomerCodeMap.put("MSTORE", "A0R");
        sourceCustomerCodeMap.put("690CK", "A0R");
        sourceCustomerCodeMap.put("YJYD", "A1X");
        sourceCustomerCodeMap.put("VDCN", "A1Z");
        sourceCustomerCodeMap.put("AIR", "A2A");
        sourceCustomerCodeMap.put("TMMKFX", "A2U");
        sourceCustomerCodeMap.put("SCHD", "8800050360");
        sourceCustomerCodeMap.put("GMTSZYBX", "8800181420");
        sourceCustomerCodeMap.put("GMTSZYCW", "8800181420");
        sourceCustomerCodeMap.put("GMTSZYYY", "8800181420");
        sourceCustomerCodeMap.put("GMTSZYKT", "8800181420");
        sourceCustomerCodeMap.put("GMTSZYXYJ", "8800181420");
        sourceCustomerCodeMap.put("S_MOBILE", "A1X");
        sourceCustomerCodeMap.put("GQGYS", "A3H");
        sourceCustomerCodeMap.put("TBCT", "8800183222");
        sourceCustomerCodeMap.put("TBQYG", "8800044313");
        sourceCustomerCodeMap.put("TMST", "8800162828");
        sourceCustomerCodeMap.put("FLW", "A1X");
        sourceCustomerCodeMap.put("TMKSD", "A3Q");
        sourceCustomerCodeMap.put("TMTV", "A3R");
        sourceCustomerCodeMap.put("TBCFDD", "A3V");
        sourceCustomerCodeMap.put("TBXCR", "A4A");
        sourceCustomerCodeMap.put("GMZXTS", "A4B");
        sourceCustomerCodeMap.put("TBZYKT", "A4I");
        sourceCustomerCodeMap.put("JDHEGQ", "A4J");
        sourceCustomerCodeMap.put("JDHEBXGQ", "A4K");
        sourceCustomerCodeMap.put("SNQDZX", "A4L");
        sourceCustomerCodeMap.put("XSST", "A3H");
        sourceCustomerCodeMap.put("DSPTJDST", "A5O");
    }

    private static final Map<String, String> orderSource = new HashMap<String, String>();

    static {
        orderSource.put("ORIGIN_FRONT", "1");//商城订单
        orderSource.put("ORIGIN_FRONTDXS", "DXS");//商城订单-大学生项目订单
        orderSource.put("ORIGIN_TBSC", "TBSC");//淘宝海尔官方旗舰店
        orderSource.put("ORIGIN_TOPDHSC", "TOPDHSC");//海尔生活家电旗舰店
        orderSource.put("ORIGIN_FENXIAO", "TOPFENXIAO");//淘宝海尔官方旗舰店分销平台
        orderSource.put("ORIGIN_FENXIAODHSC", "TOPFENXIAODHSC");//淘宝海尔生活家电旗舰店分销平台
        orderSource.put("ORIGIN_TOPBUYBANG", "TOPBUYBANG");//淘宝海尔买帮专卖店
        orderSource.put("ORIGIN_TOPBOILER", "TOPBOILER");//淘宝海尔热水器专卖店
        orderSource.put("ORIGIN_TOPSHJD", "TOPSHJD");//淘宝海尔生活电器专卖店
        orderSource.put("ORIGIN_TOPMOBILE", "TOPMOBILE");//淘宝海尔手机专卖店
        orderSource.put("ORIGIN_TONGSHUAI", "TONGSHUAI");//统帅传 DS06 2014-08-06  统帅日日顺乐家专卖店
        orderSource.put("ORIGIN_TSFX", "TONGSHUAIFX");//统帅传 DS06 2014-08-06   统帅分销
        orderSource.put("ORIGIN_CCBSC", "CCBSC");//企业订单_建行龙卡商城订单
        orderSource.put("ORIGIN_CCBSR", "CCBSR");//企业订单_建行善融商城订单
        orderSource.put("ORIGIN_CCB", "95533");//企业内购-建行
        orderSource.put("ORIGIN_CORPORATE", "CORPORATE");//企业订单
        orderSource.put("ORIGIN_CORPORATE_SJMG", "CORPORATE_SJMG");//企业订单_四季沐歌
        orderSource.put("ORIGIN_CORPORATE_DC", "112");//商城订单_海尔地产
        orderSource.put("ORIGIN_YIHAODIAN", "YIHAODIAN");//企业订单_1号店订单
        orderSource.put("ORIGIN_DALOU", "DALOU");//企业订单_大楼订单
        orderSource.put("ORIGIN_CORPORATE_NG", "113");//企业订单_内购
        orderSource.put("ORIGIN_CORPORATE_JC", "114");//企业订单_集采
        orderSource.put("ORIGIN_CORPORATE_B2B", "115");//企业订单_B2B
        orderSource.put("ORIGIN_ZPTH", "ZPTH");//正品退货
        orderSource.put("ORIGIN_CHINAUNICOM", "CHINAUNICOM");//中国联通_广电
        orderSource.put("ORIGIN_TOPPAD", "TOPPAD");//淘宝海尔PAD专卖店
        orderSource.put("ORIGIN_HPB2B", "HPB2B");//海朋转TC订单
        orderSource.put("ORIGIN_TOPXB", "TOPXB");//海尔新宝旗舰店
        orderSource.put("ORIGIN_FENXIAOXB", "TOPFENXIAOXB");//淘宝海尔新宝旗舰店分销平台
        orderSource.put("ORIGIN_C2BWASHER", "C2BWASHER");//C2B滚筒洗衣机订单
        orderSource.put("ORIGIN_MOBILE", "MOBILE");//移动商城
        orderSource.put("ORIGIN_ICBC", "ICBC");//平台大客户-工商银行
        orderSource.put("ORIGIN_YHD", "YHD");//平台大客户-1号店
        orderSource.put("ORIGIN_COS", "COS");//样品机
        orderSource.put("ORIGIN_RRS", "RRS");//日日顺
        orderSource.put("ORIGIN_CK", "CK");//海尔创客_PC端
        orderSource.put("ORIGIN_CK_MOBILE", "CK_MOBILE");//海尔创客_移动端
        orderSource.put("ORIGIN_SCFX", "SCFX");//商城订单-分销渠道
        orderSource.put("ORIGIN_GMZX", "GMZX");//国美在线
        orderSource.put("ORIGIN_DBJ", "DBJ");//夺宝机
        orderSource.put("ORIGIN_BLPMS", "BLPMS");//不良品买损
        orderSource.put("ORIGIN_TMMK", "TMMK");//天猫模卡
        orderSource.put("ORIGIN_JDMK", "JDMK");//京东模卡
        orderSource.put("ORIGIN_SQXW", "SQXW");//商圈小微
        orderSource.put("ORIGIN_YMX", "YMX");//亚马逊
        orderSource.put("ORIGIN_TSMOBILE", "TSMOBILE");//统帅商城移动
        orderSource.put("ORIGIN_TSPC", "TSPC");//统帅商城PC
        orderSource.put("ORIGIN_YHDZY", "YHDZY");//电商平台-1号店自营
        orderSource.put("ORIGIN_ZSH", "ZSH");//平台大客户-中石化
        orderSource.put("ORIGIN_SNYG", "SNYG");//电商平台-苏宁易购旗舰店   DS16 8800049541  --魏云军2015-03-31
        orderSource.put("ORIGIN_TBZYKT", "TBZYKT");//淘宝中央空调店
    }
}
