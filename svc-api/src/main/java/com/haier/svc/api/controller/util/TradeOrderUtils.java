package com.haier.svc.api.controller.util;

import com.haier.shop.model.ExternalOrderState;
import com.haier.shop.model.ExternalOrders;
import com.haier.svc.bean.ShopEnum;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public abstract class TradeOrderUtils {

	/**
	 * 获取订单前缀，用于区分不同的来源订单号
	 * @param se
	 * @return
	 */
	public static String getOrderPrefix(ShopEnum se) {
		if (se == null) {
			return "top_"; // 海尔官方淘宝旗舰店
		} else {
			switch (se.getConfigId()) {
			case 1:
				return "top_"; // 海尔官方淘宝旗舰店
			case 3:
				return "shjd_"; // 海尔生活电器专卖店
			case 4:
				return "rsq_"; // 海尔热水器专卖店
			case 6:
				return "mobile_"; // 淘宝手机淘宝店
			case 7:
				return "ts_"; // 淘宝网统帅日日顺乐家专卖店
			case 8:
				return "fx_"; // 官方旗舰店分销平台
			case 10:
				return "shsc_"; // 海尔淘宝生活家电旗舰店
			case 11:
				return "fxshsc_"; // 海尔淘宝生活家电旗舰店-分销平台
			case 12:
				return "topxb_";
			case 13:
				return "fxtopxb_";
			case 15:
				return "tsfx_";
			case 16:
			    return "mooka_";
			case 17:
			    return "WASHER_";
			case 18:
			    return "FRIDGE_";
			case 21:
			    return "AIR_";
            case 22:
                return "TBCT_";
            case 23:
                return "GQGYS_";
            case 27:
                return "TMKSD_";
            case 28:
                return "TMTV_";
            case 29:
                return "TBCFDD_";
            case 26:
                return "TMMKFX_";
            case 31:
                return "TBXCR_";
            case 32:
				return "TBZYKT_";
			default:
				return "top_";
			}
		}
	}

	/**
	 * 判断字符串是否数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
			return false;
		}
		Pattern pattern = Pattern.compile("([0-9]|\\.|\\-)*");
        return pattern.matcher(str).matches();
	}

	/**
     * 将Trade 转换为ExternalOrders
     *
     * @param
     * @param se
     * @return
     */
    public static ExternalOrders convertTrade2ExtOrdersBySourceOrderSn_old(String sourceOrderSn,
																		   ShopEnum se, String type) {
        ExternalOrders myOrder = new ExternalOrders();
        myOrder.setAddTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new Date()).toString());
        myOrder.setConfigId(se.getConfigId());
        myOrder.setCreator("");
        myOrder.setErrorLog("");
        myOrder.setStepTradeStatus("");
       // myOrder.setOrderExtention("a:0:{}");
      //  myOrder.setOrderFrom("taobao");
        myOrder.setOrderId(0);
        myOrder.setOrderSn(TradeOrderUtils.getOrderPrefix(se)
                + sourceOrderSn);
        myOrder.setOrderState(ExternalOrderState.OS_UNSYNCED.value());
      //  myOrder.setSiteId(1);
        myOrder.setSourceOrderSn(sourceOrderSn);
        myOrder.setSyncCount(0);
        myOrder.setSyncTime(0L);
        myOrder.setTaobaoModifiedTime(new Date());
      //  myOrder.setTaskId(0L);
        myOrder.setType("");
        BigDecimal stepPaidFee = BigDecimal.ZERO;
        myOrder.setStepPaidFee(stepPaidFee);
//		myOrder.setStepTradeStatus(trade.getStatus() == null ? "" : trade.getStatus());
        myOrder.setStepTradeStatus("");
        myOrder.setTaobaoGroupId(0);
        myOrder.setShouldPaidFee(BigDecimal.ZERO);
       // myOrder.setIsFenXiaoOrder(0);
        myOrder.setHasShipped(0);
		myOrder.setOrderStatus(1);
		if(type!=null){
			myOrder.setType(type);
		}
        return myOrder;
    }

}