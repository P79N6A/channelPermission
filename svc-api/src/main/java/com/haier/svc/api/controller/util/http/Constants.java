package com.haier.svc.api.controller.util.http;



import com.haier.svc.api.controller.util.StringUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Constants {

	/** UTF-8编码 **/
	public static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * 按比例分摊优惠
	 * 
	 * @param total
	 *            :总优惠金额
	 * @param eachTotal
	 *            :每一个网单的优惠总数
	 * @param map
	 *            : key:优惠券名称，value:使用的金额
	 * @return
	 */
	public static Map<String, BigDecimal> getCoupons(BigDecimal total,
			BigDecimal eachTotal, Map<String, BigDecimal> map) {
		Map<String, BigDecimal> m = new HashMap<String, BigDecimal>();
		int totalcount = m.size();
		int count = 0;
		BigDecimal tempTotal = new BigDecimal(0);
		for (Entry<String, BigDecimal> entry : map.entrySet()) {
			if (count != totalcount) {
				if (total.doubleValue() > 0) {
					m.put(entry.getKey(),
							(entry.getValue()
									.divide(total, 8, RoundingMode.HALF_UP)
									.multiply(eachTotal).setScale(2,
									RoundingMode.HALF_UP)));
					tempTotal.add((entry.getValue()
							.divide(total, 8, RoundingMode.HALF_UP)
							.multiply(eachTotal).setScale(2,
							RoundingMode.HALF_UP)));
				} else {
					m.put(entry.getKey(), new BigDecimal(0));
					tempTotal.add(new BigDecimal(0));
				}
			} else {
				m.put(entry.getKey(), eachTotal.subtract(tempTotal));
			}
			count++;
		}
		return m;
	}
	
	/**
	 * 物流信息获取
	 * @param expressName
	 * @return
	 */
	public static String getExpressCode(String expressName) {
	    if(StringUtil.isEmpty(expressName)){
	    	return "海尔物流";
	    } else if (expressName.equals("顺丰速运")) {
            return "SF";
        } else if (expressName.equals("EMS特快专递") || expressName.equals("神码发货（EMS）")
                   || expressName.equals("科捷上海（EMS）")) {
            return "EMS";
        } else if (expressName.equals("圆通速递") || expressName.equals("神码发货（圆通）")
                   || expressName.equals("科捷上海（圆通）")) {
            return "YTO";
        } else if (expressName.equals("中通速递")) {
            return "ZTO";
        } else if (expressName.equals("宅急送（兴长信达）")) {
            return "ZJS";
        } else {
            return "海尔物流";
        }
    }
	
	public static void main(String[] args) {
//		Map m = new HashMap();
//		 m.put("coupontotalMoney", 1.3);
//		System.out.println(getCoupons(new BigDecimal(1.3), new BigDecimal(1.3), m));\
		BigDecimal vouchertotalMoney = new BigDecimal(497.7);
		System.out.println(vouchertotalMoney.setScale(2, RoundingMode.HALF_UP));
	}
}
