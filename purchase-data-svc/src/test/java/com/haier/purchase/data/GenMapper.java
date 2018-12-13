package com.haier.purchase.data;

import java.lang.reflect.Field;

public class GenMapper {

	public static void main(String[] args) {
		Field[] fields = com.haier.purchase.data.model.CnReplenishEntryOrderItem.class.getDeclaredFields();
		for(Field f : fields){
			String columnName = f.getName();
			System.out.println("<result property=\"" + columnName + "\" column=\"" + columnName + "\"/>");
		}
	}

}
