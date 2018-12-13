package com.haier.invoice.services;

import com.haier.common.util.StringUtil;
import com.haier.invoice.service.InvoiceExceptionService;
import com.haier.order.model.ShopEnum;
import com.haier.shop.model.InvoiceExceptionDispItem;
import com.haier.shop.service.ShopMemberInvoicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 异常发票处理
 **/
@Service
public class InvoiceExceptionServiceImpl implements InvoiceExceptionService {

    @Autowired
    private ShopMemberInvoicesService shopMemberInvoicesService;

    @Override
    public Map<String, Object> getInvoiceExceptionListByPage(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = shopMemberInvoicesService.getInvoiceExceptionListByPage(paramMap);
        List<InvoiceExceptionDispItem> list = (List<InvoiceExceptionDispItem>) resultMap.get("rows");
        replaceData(list);
        resultMap.put("rows", list);
        return resultMap;
    }

    @Override
    public List<InvoiceExceptionDispItem> getInvoiceExceptionList(Map<String, Object> paramMap) {
        List<InvoiceExceptionDispItem> list = shopMemberInvoicesService.getInvoiceExceptionList(paramMap);
        replaceData(list);
        return list;
    }

    /**
     * 获得条数
     */
    public int getCount() {
        return shopMemberInvoicesService.getCount();
    }

    /**
     * 替换数据
     *
     * @param list
     */
    private void replaceData(List<InvoiceExceptionDispItem> list) {
        if (list != null && list.size() > 0) {
            for (InvoiceExceptionDispItem lt : list) {
                lt.setIsBackend(getIsBackend(lt.getIsBackend()));//是否后台下单
                lt.setStockType(getStockType(lt.getStockType()));//库存类型
                lt.setIsReceipt(getIsReceipt(lt.getIsReceipt()));//是否需要开具发票
                lt.setStatus(getStatus(lt.getStatus()));//网单状态
                lt.setcPaymentStatus(getcPaymentStatus(lt.getcPaymentStatus())); //网单付款状态
                lt.setPaymentStatus(getPaymentStatus(lt.getPaymentStatus()));//订单付款状态
                lt.setPaymentCode(getPaymentCode(lt.getPaymentCode()));//支付方式
                lt.setMakeReceiptType(getMakeReceiptType(lt.getMakeReceiptType())); //开票类型
                lt.setIsMakeReceipt(getIsMakeReceipt(lt.getIsMakeReceipt()));//开票状态
                lt.setAddTime(timeStamp2Date(lt.getAddTime(), null));//下单时间
                lt.setOrderStatus(getOrderStatus(lt.getOrderStatus()));//订单状态
//                lt.setSource(getSource(sourceMap, lt.getSource()));//订单来源
                dealRegionName(lt);//省、//市、//区县、//街道
                lt.setPayTime(timeStamp2Date(lt.getPayTime(), null));//付款时间
                lt.setFirstConfirmTime(timeStamp2Date(lt.getFirstConfirmTime(), null));//首次确认时间
                lt.setConfirmTime(timeStamp2Date(lt.getConfirmTime(), null));//确认时间
                lt.setHpFinishDate(timeStamp2Date(lt.getHpFinishDate(), null));//分配时间
                lt.setInvoiceType(getInvoiceType(lt.getInvoiceType()));//发票类型
                lt.setState(getState(lt.getState()));//发票审核状态
                lt.setSource(ShopEnum.getName(lt.getSource()));
            }
        }
        return;
    }

    /**
     * 是否后台下单
     */
    private String getIsBackend(String isBackend) {
        if (isBackend == null)
            return "";
        if ("0".equals(isBackend))
            return "否";
        if ("1".equals(isBackend))
            return "是";
        return isBackend;
    }

    /**
     * 库存类型
     */
    private String getStockType(String stockType) {
        if (stockType == null)
            return "";
        if ("WA".equalsIgnoreCase(stockType))
            return "自有库存网单";
        return stockType;
    }

    /**
     * 是否需要开具发票
     */
    private String getIsReceipt(String isReceipt) {
        if (isReceipt == null || "".equals(isReceipt) || "1".equals(isReceipt))
            return "是";
        return "否";
    }

    /**
     * 网单状态
     */
    private String getStatus(String status) {
        if (status == null)
            return "";
        if (status.equals("0"))
            return "处理中";
        if (status.equals("1"))
            return "已占用库存";
        if (status.equals("2"))
            return "同步到HP";
        if (status.equals("3"))
            return "同步到EC";
        if (status.equals("4"))
            return "分配网点";
        if (status.equals("8"))
            return "待出库";
        if (status.equals("10"))
            return "待审核";
        if (status.equals("11"))
            return "待转运入库";
        if (status.equals("12"))
            return "待转运出库";
        if (status.equals("40"))
            return "待发货";
        if (status.equals("150"))
            return "网点拒绝";
        if (status.equals("70"))
            return "待交付";
        if (status.equals("140"))
            return "用户签收";
        if (status.equals("130"))
            return "完成关闭";
        if (status.equals("160"))
            return "用户拒收";
        if (status.equals("110"))
            return "取消关闭";
        return status;
    }

    /**
     * 网单付款状态
     */
    private String getcPaymentStatus(String cPaymentStatus) {
        if (cPaymentStatus == null)
            return "";
        if (cPaymentStatus.equals("200"))
            return "未付款";
        if (cPaymentStatus.equals("201"))
            return "已付款";
        if (cPaymentStatus.equals("206"))
            return "待退款";
        if (cPaymentStatus.equals("207"))
            return "已退款";
        return cPaymentStatus;
    }

    /**
     * 订单付款状态
     */
    private String getPaymentStatus(String paymentStatus) {
        if (paymentStatus == null)
            return "";
        if (paymentStatus.equals("100"))
            return "未付款";

        if (paymentStatus.equals("101"))
            return "已付款";

        if (paymentStatus.equals("102"))
            return "待退款";
        if (paymentStatus.equals("103"))
            return "已退款";
        return paymentStatus;
    }

    /**
     * 订单付款方式
     */
    private String getPaymentCode(String paymentCode) {
        if (paymentCode == null)
            return "";
        if (paymentCode.equals("chinapay"))
            return "银联支付";
        if (paymentCode.equals("alipay"))
            return "支付宝";
        if (paymentCode.equals("wxpay"))
            return "微信支付";
        if (paymentCode.equals("tenpay"))
            return "财付通";
        if (paymentCode.equals("cod"))
            return "货到付款";
        if (paymentCode.equals("insidestatement"))
            return "内部结算单";
        if (paymentCode.equals("lejia"))
            return "电子钱包";
        if (paymentCode.equals("giftCard"))
            return "礼品卡支付";
        if (paymentCode.equals("offline"))
            return "线下付款";
        if (paymentCode.equals("prepaid"))
            return "客户预付货款";
        if (paymentCode.equals("ccb"))
            return "建行网银支付";
        if (paymentCode.equals("balance"))
            return "余额支付";
        if (paymentCode.equals("alipaymobile"))
            return "支付宝移动商城";
        if (paymentCode.equals("chinaecpay"))
            return "联行支付";
        if (paymentCode.equals("kjtpay"))
            return "快捷通";
        if (paymentCode.equals("ccbfenqi"))
            return "建行信用卡分期";
        if (paymentCode.equals("weixinpc"))
            return "商城微信";

        return paymentCode;
    }

    /**
     * 开票类型
     */
    private String getMakeReceiptType(String makeReceiptType) {
        if (makeReceiptType == null)
            return "";
        if (makeReceiptType.equals("1"))
            return "库房开票";
        if (makeReceiptType.equals("2"))
            return "共享开票";
        return makeReceiptType;
    }

    /**
     * 开票状态
     */
    private String getIsMakeReceipt(String isMakeReceipt) {
        if (isMakeReceipt == null)
            return "";
        if (isMakeReceipt.equals("1"))
            return "未开票";
        if (isMakeReceipt.equals("9"))
            return "待开票";
        if (isMakeReceipt.equals("5"))
            return "开票中";
        if (isMakeReceipt.equals("6"))
            return "开票失败";
        if (isMakeReceipt.equals("2"))
            return "已开票";
        if (isMakeReceipt.equals("3"))
            return "作废发票";
        if (isMakeReceipt.equals("4"))
            return "冲红发票";
        if (isMakeReceipt.equals("10"))
            return "取消开票";
        if (isMakeReceipt.equals("20"))
            return "期初数据封存";
        return isMakeReceipt;
    }

    /**
     * 订单状态
     */
    private String getOrderStatus(String orderStatus) {
        if (orderStatus == null)
            return "";
        if (orderStatus.equals("200"))
            return "未确认";
        if (orderStatus.equals("201"))
            return "已确认";
        if (orderStatus.equals("202"))
            return "已取消";
        if (orderStatus.equals("203"))
            return "已完成";
        if (orderStatus.equals("204"))
            return "部分缺货";
        if (orderStatus.equals("-100"))
            return "未定义";
        return orderStatus;
    }

    /**
     * 订单来源
     */
    private String getSource(Map<String, String> sourceMap, String source) {
        if (sourceMap == null || source == null)
            return source;
        return sourceMap.get(source) == null ? source : sourceMap.get(source);
    }

    /**
     * 处理省、市、区县、街道
     * 地区名称（如：北京 北京 昌平区 兴寿镇）
     */
    private void dealRegionName(InvoiceExceptionDispItem lt) {
        if (lt.getRegionName() == null || "".equals(lt.getRegionName()))
            return;
        String[] regionName = lt.getRegionName().split(" ");
        for (int i = 0; i < regionName.length; i++) {
            if (i == 0) {
                lt.setProvince(regionName[i]);
            } else if (i == 1) {
                lt.setCity(regionName[i]);
            } else if (i == 2) {
                lt.setRegion(regionName[i]);
            } else if (i == 3) {
                lt.setStreet(regionName[i]);
            }
        }

    }

    /**
     * 发票类型
     */
    private String getInvoiceType(String invoiceType) {
        if (invoiceType == null)
            return "";
        if (invoiceType.equals("1"))
            return "增值税发票";
        if (invoiceType.equals("2"))
            return "普通发票";
        return invoiceType;
    }

    /**
     * 发票审核状态
     */
    private String getState(String state) {
        if (state == null)
            return "";
        if (state.equals("0"))
            return "待审核";
        if (state.equals("1"))
            return "审核通过";
        if (state.equals("2"))
            return "拒绝";
        return state;
    }

    /**
     * 处理时间变成时间戳
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date timeStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtil.isEmpty(time)) {
            return null;
        }
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }
}
