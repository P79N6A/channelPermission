package com.haier.svc.api.controller.excel;

import com.haier.shop.model.WOUser;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 人员导出
 * @author zhangtianshuai
 *
 */
public class WebReviewExportPeople extends WebReviewExcelExportTemplate
		implements Serializable {
	private static final long serialVersionUID = -8495723071031999434L;
	private static Logger log = LogManager
			.getLogger(WebReviewExportPeople.class);

	public void toExcel(List<WOUser> list,
			HttpServletRequest request, int length, String f, OutputStream out)
			throws IOException {

		Long info = System.currentTimeMillis();
		String path = request.getSession().getServletContext().getRealPath("/");
		int pathIndex = path.lastIndexOf(File.separatorChar);
		path = path.substring(0, pathIndex + 1);
		// File zip = new File(path + f + ".zip");// 压缩文件
		// 生成excel
		int a = list.size() / length + 1;
		if (list.size() % length == 0) {
			a = list.size() / length;
		}
		for (int j = 0; j < a; j++) {
			HSSFWorkbook book = new HSSFWorkbook();
			HSSFSheet sheet = book.createSheet();
			sheet.createFreezePane(0, 1);// 冻结
			HSSFCellStyle columnHeadStyle = excelHeader(book);
			HSSFCellStyle style = excelContent(book);
			try {
				String[][] str = new String[][] { { "人员", "电话", "邮件",
						"员工类型", "发送统计邮件" } };
				HSSFRow row = sheet.createRow(0);
				for (int i = 0; i < str.length; i++) {
					String[] src = str[i];
					for (int k = 0; k < src.length; k++) {
						// row.createCell(k).setCellValue(src[k]);
						row.setHeight((short) 300);// 设置行高
						HSSFCell cell0 = row.createCell(k);
						cell0.setCellValue(new HSSFRichTextString(src[k]));
						// 设置头样式
						cell0.setCellStyle(columnHeadStyle);
						// 设置列宽
						sheet.setColumnWidth(k, 5000);
					}
				}
				for (int i = 1, min = (list.size() - j * length + 1) > (length + 1) ? (length + 1)
						: (list.size() - j * length + 1); i < min; i++) {
					WOUser people = list.get(length * (j) + i - 1);
					int index = 0;
					row = sheet.createRow(i);
					HSSFCell cell = row.createCell(index++);
					row.setHeight((short) 300);// 设置行高
					cell.setCellValue(new HSSFRichTextString(people.getUserName()));// 人员
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people.getMobile()));// 电话
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people
							.getEmail()));// 邮件
					cell.setCellStyle(style);
					
					Integer Type = people.getUserType();
                    String yglx = "";
                    if ("0".equals(Type.toString())) {
                    	yglx = "普通员工";
                    } else if ("1".equals(Type)) {
                    	yglx = "一级领导";
                    } else if ("2".equals(Type)) {
                    	yglx = "二级领导";
                    }
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(yglx));// 员工类型
					cell.setCellStyle(style);
					
					Integer Issend = people.getSendEmail();
                    String fsyj = "";
                    if ("0".equals(Issend.toString())) {
                    	fsyj = "不发送";
                    } else if ("1".equals(Issend)) {
                    	fsyj = "发送";
                    } 
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(fsyj));// 发送统计邮件
					cell.setCellStyle(style);
					
				}
				Long info2 = System.currentTimeMillis();
				log.info("for循环时间：" + (info2 - info));
			} catch (Exception e) {
				log.error("Exception:", e);
			}
			try {
				book.write(out);
			} catch (Exception ex) {
				log.error("Exception:", ex);
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

}
