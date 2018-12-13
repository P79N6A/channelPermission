package com.haier.svc.api.controller.excel;


import com.haier.shop.model.Reviewpoolfordhzx;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * 人员导出
 * @author zhangtianshuai
 *
 */
public class WebReviewExportdhzxle extends WebReviewExcelExportTemplate
		implements Serializable {
	private static final long serialVersionUID = -8495723071031999434L;
	private static Logger log = LogManager
			.getLogger(WebReviewExportdhzxle.class);

	public void toExcel(List<Reviewpoolfordhzx> list,
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
				String[][] str = new String[][] { { "信息编号","工单类型","人员","问题描述", "电话1", "电话2",
						"电话3","地址","渠道类型","店铺类型","来电时间","回复时间","中间结果","工单创建时间","工单状态","跳闸结果","最终结果"} };
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
					Reviewpoolfordhzx people = list.get(length * (j) + i - 1);
					int index = 0;
					row = sheet.createRow(i);
					HSSFCell cell = row.createCell(index++);
					row.setHeight((short) 300);// 设置行高
					cell.setCellValue(new HSSFRichTextString(people.getMessageNum()));// 信息编号
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					String type="";
					if(people.getType().equals("0")){
						type="物流类工单";
					}
					if(people.getType().equals("1")){
						type="店铺类工单";
					}
					if(people.getType().equals("2")){
						type="售后类工单";
					}
					cell.setCellValue(new HSSFRichTextString(type));// 工单类型
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people.getUserName()));// 人员
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people.getProblemMessage()));// 问题描述
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people.getPhone1()));// 电话
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people
							.getPhone2()));// 电话2
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people
							.getPhone3()));// 电话3
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people
							.getAddress()));// 地址
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people
							.getStore()));// 渠道
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people
							.getStoreType()));// 店铺
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people
							.getCallTime()));// 来电
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people
							.getRecoveryTime()));// 回复
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people
							.getProblem()));// 中间
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people
							.getCreateTime()));// 创建
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					String  workStatus="";
					if(people.getWorkStatus().equals("0")){
						workStatus="未处理";
					}
					if(people.getWorkStatus().equals("2")){
						workStatus="中间结果";
					}
					if(people.getWorkStatus().equals("3")){
						workStatus="最终结果";
					}
					cell.setCellValue(new HSSFRichTextString(workStatus));// 状态
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people.getTrip()));// 状态
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(people
							.getBackContext3()));// 最终
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
