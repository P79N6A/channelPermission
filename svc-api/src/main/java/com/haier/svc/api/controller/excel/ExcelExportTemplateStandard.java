package com.haier.svc.api.controller.excel;


import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 将数据导出到excel接口定义
 * @author WangXuzheng
 *
 */
public interface ExcelExportTemplateStandard<T> {
	/**
	 * 将数据导出为excel
	 * @param outputStream 文件输出流
	 * @param parameters 参数
	 */
	public void doExport(HttpServletResponse response, String fileName, T paramters)throws Exception;


	/**
	 * 要创建的excel文件的sheet名称
	 * @return
	 */
	public String[] getSheetNames();
	
	/**
	 * 要创建的excel表格中的表头内容.
	 * list中存放的是多个sheet的表头内容
	 * @return
	 */
	public String[][] getTitles();

	/**
	 * 表头对应字段
	 * @return
	 */
	public String[][] getColumns();
	
	/**
	 * 要创建的excel表格的每个sheet的表头
	 * @return
	 */
	public String[] getCaptions();
	
	/**
	 * 控制文件在内存中最多占用多少条
	 * @return
	 */
	public int getRowAccessWindowSize();
	
}