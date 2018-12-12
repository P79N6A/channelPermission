package com.haier.stock.util;

/**
 * order-service 发送短信模板
 *                       
 * @Filename: SmsTemplateConst.java
 * @Version: 1.0
 * @Author: xinmeng
 * @Email: xinmeng@ehaier.com
 *
 */
public class SmsTemplateConst {

    /**
     * 【确认订单发送短信】统帅商城_B2B2C[物流模式]_纸质发票
     */
    public static String ConfirmOrder_TSSC_B2B2C_0 = "【顺逛·商城】#consignee#您好，您购买的#typeName#已下单成功，您可在用户中心查看，如有需要请联系客服！统帅商城";

    /**
     * 【确认订单发送短信】统帅商城_B2B2C[物流模式]_电子发票
     */
    public static String ConfirmOrder_TSSC_B2B2C_1 = "【顺逛·商城】#consignee#您好，您购买的#typeName#已下单成功，发票将以电子发.票（短信和邮件）的形式发送，请您注意查收！电子发.票同样保障您的法律权益和售后维修权益，关于电子发.票具体详情可到青岛税务局官网和统帅商城官网查询，也可联系在线客服进行咨询！统帅商城";

    /**
     * 【确认订单发送短信】天猫商城
     */
    public static String ConfirmOrder_TBSC = "【顺逛·商城】#consignee#您好，您定制的#typeName#已下单成功，我们会尽快安排工厂生产和发货，到货后将会及时通知您，如有需要请联系天猫客服。";

    /**
     * 【确认订单发送短信】海尔商城_纸质发票
     */
    public static String ConfirmOrder_HESC_0 = "【顺逛·商城】#consignee#您好，您购买的#typeName#已下单成功，您可在用户中心查看，如有需要请联系在线客服！海尔商城";

    /**
     * 【确认订单发送短信】海尔商城_电子发票
     */
    public static String ConfirmOrder_HESC_1 = "【顺逛·商城】#consignee#您好，您购买的#typeName#已下单成功，发票将以电子发.票（短信和邮件）的形式发送，请您注意查收！电子发.票同样保障您的法律权益和售后维修权益，关于电子发.票具体详情可到青岛税务局官网和海尔商城官网查询，也可联系在线客服进行咨询！海尔商城";

    /**
     * 【确认订单发送短信】顺逛微店_纸质发票
     */
    public static String ConfirmOrder_MSTORE_0 = "【顺逛·商城】#consignee#您好，您购买的#typeName#已下单成功，您可在订单管理或顺逛微店公众号查看，如有需要请联系在线客服！";

    /**
     * 【确认订单发送短信】顺逛微店_电子发票
     */
    public static String ConfirmOrder_MSTORE_1 = "【顺逛·商城】#consignee#您好，您购买的#typeName#已下单成功，发票将以电子发.票（短信和邮件）的形式发送，请注意查收！电子发.票同样保障您的法律权益和售后维修权益，详情可到青岛税务局官网和顺逛微店公众号查询，也可联系在线客服！";

    /**
     * 【电子发票发送短息】海尔商城_开票_电子发票_通知
     */
    public static String Invoice_HESC_Generate_1_Notice = "【顺逛·商城】尊敬的海尔商城用户,您购买的海尔产品电子发.票已开出，发.票抬头：#invoiceTitle#，详询网站客服或登录chinaeinv.com查询打印。电子发.票是国家税务机关认可的可作为保修和报销的凭证，请勿泄露发.票信息，以免权益受到损害。【海尔电子发.票平台】";

    /**
     * 【电子发票发送短息】海尔商城_开票_电子发票_发票详情
     */
    public static String Invoice_HESC_Generate_1_Detail = "【顺逛·商城】发.票详情:#eInvViewUrl#，发.票代码：#invoiceNumberF12#，发.票号码#invoiceNumberB8#，校验码：#checkCode#";

    /**
     * 【电子发票发送短息】海尔商城_作废_电子发票_通知
     */
    public static String Invoice_HESC_Invalid_1_Notice = "【顺逛·商城】尊敬的海尔商城用户,您购买的#productCateName#的发.票已作废,原发.票代码：#invoiceNumberF12#，发.票号码#invoiceNumberB8#，校验码：#checkCode#";

    /**
     * 【电子发票发送短息】海尔商城_作废_电子发票_发票详情
     */
    public static String Invoice_HESC_Invalid_1_Detail = "【顺逛·商城】发.票详情:#eInvViewUrl#";

    /**
     * 【电子发票发送短息】顺逛微店_开票_电子发票_通知
     */
    public static String Invoice_MSTORE_Generate_1_Notice = "【顺逛·商城】尊敬的用户,您购买的海尔产品电子发.票已开出，发.票抬头：#invoiceTitle#，详询网站客服或登录chinaeinv.com查询打印。电子发.票是国家税务机关认可的可作为保修和报销的凭证，请勿泄露发.票信息，以免权益受到损害。【海尔电子发.票平台】";

    /**
     * 【电子发票发送短息】顺逛微店_开票_电子发票_发票详情
     */
    public static String Invoice_MSTORE_Generate_1_Detail = "【顺逛·商城】发.票详情:#eInvViewUrl#，发.票代码：#invoiceNumberF12#，发.票号码#invoiceNumberB8#，校验码：#checkCode#";

    /**
     * 【电子发票发送短息】顺逛微店_作废_电子发票_通知
     */
    public static String Invoice_MSTORE_Invalid_1_Notice = "【顺逛·商城】尊敬的用户,您购买的#productCateName#的发.票已作废,原发.票代码：#invoiceNumberF12#，发.票号码#invoiceNumberB8#，校验码：#checkCode#";

    /**
     * 【电子发票发送短息】顺逛微店_作废_电子发票_发票详情
     */
    public static String Invoice_MSTORE_Invalid_1_Detail = "【顺逛·商城】发.票详情:#eInvViewUrl#";

    /**
     * 【保本价闸口发送信息】
     */
    public static String GUARANTEE_PRICE_NOTICE = "#person#您好，#channel#渠道，订单号#orderSn#，网单号#orderProductSn#，被闸原因：#lockReason#";

    /**
     * 【完成关闭超时免单发送信息】
     */
    public static String CLOSE_TIMEOUT_NOTICE = "【顺逛·商城】亲,您的订单#orderSn#【#productName#】可以联系客服申请超时免单,如符合条件,免单金额将会在4个工作日内发放到您的海尔商城账户余额中,可抵现金使用.";

    /**
     * 【确认订单发送短信】新的顺逛销售订单
     */
    public static String NEW_SALES_ORDER = "【顺逛·商城】您有新的顺逛销售订单，订单信息:#orderSn#,#productName#*#number#台，收货信息：#customerName#,#customerPhoneNum#，请尽快联系用户配送安装。";

    /**
     * 【众筹成功发送短信】
     */
    public static String CROWDFUNDING_SUCCESS = "尊敬的顺逛众筹用户，您支持的《#activityName#》项目众筹成功啦，项目发起人正在马不停蹄的给您准备回报呢，敬请期待吧！如果您参与的是抽奖档位，请及时关注抽奖公告，幸运用户可能就是您！";

    /**
     * 【众筹失败发送短信】
     */
    public static String CROWDFUNDING_FAIL = "尊敬的顺逛众筹用户，您支持的《#activityName#》项目众筹失败。我们会在10个工作日内将您支持的金额退还到相关账户，请注意查看，有问题请及时及时咨询在线客服，给您带来的不便敬请谅解！";

    /**
     * 【接收HP系统回传预约送货时间】海尔商城
     */
    public static String HopReturn_ReservationDate_HESC   = "【海尔商城】#consignee#您好，您购买的\"#typeName#\"预约送货时间修改为#hpReservationDate#。如有需要请联系在线客服！";

    /**
     * 【接收HP系统回传预约送货时间】顺逛微店
     */
    public static String HopReturn_ReservationDate_MSTORE = "【顺逛微店】#consignee#您好，您购买的\"#typeName#\"预约送货时间修改为#hpReservationDate#。如有需要请联系在线客服！";

}
