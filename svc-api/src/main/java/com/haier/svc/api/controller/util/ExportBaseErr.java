package com.haier.svc.api.controller.util;

public final class ExportBaseErr {
	
	
	public static final String ORDER_ID="订单ID";
	public static final String MENU_PATH="业务模块";
	public static final String INTERFACE_PATH="接口地址";
	public static final String MESSAGE="错误信息";
	public static final String LOG_TIME="日志时间";
	public static final String USER_ID="用户名";
	public static final String ROLE_ID="角色";
	
	
	public static String[] errListQuery = {ORDER_ID,MENU_PATH,INTERFACE_PATH,MESSAGE,LOG_TIME,USER_ID,ROLE_ID};
	

	/**	 * 错误信息导出的表头	 */
	public static String[] errListTitle = {ORDER_ID,MENU_PATH,INTERFACE_PATH,MESSAGE,LOG_TIME,USER_ID,ROLE_ID};
}