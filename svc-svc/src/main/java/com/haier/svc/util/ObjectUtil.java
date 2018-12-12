package com.haier.svc.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 对象类型转换工具类
 * 
 * @Filename ObjectUtil.java
 * 
 * @version 1.0
 * @author tie.liu
 */
public class ObjectUtil {

	private static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getObjectToURLString(@SuppressWarnings("rawtypes") List objlist) {
		StringBuffer sb = new StringBuffer();
		if (objlist==null||objlist.size()==0) {
			return "";
		}
		Object obj = objlist.get(0);
		Field[] fields = obj.getClass().getDeclaredFields();
		String name;
		Object o;
		for (int i = 0; i < fields.length; i++) {
			if (sb.toString().length() > 0) {
				sb.append("&");
			}
			name = fields[i].getName();
			sb.append(name).append("=");
			for (int j = 0; j < objlist.size(); j++) {
				o = getFieldValueByName(name, objlist.get(j));
				if (j > 0) {
					sb.append(",");
				}
				if (o == null) {
					sb.append("null");//替换所有半角逗号为全角逗号
				} else {
					o=o.toString()
					.replaceAll(",", "，").replaceAll("&", "");//替换所有半角逗号为全角逗号
					sb.append(o);
				}
			}
		}
		return sb.toString();
	}
}
