package com.haier.svc.api.controller.excel;

import com.haier.shop.model.ReviewPool;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class WebReviewExportTemplate extends WebReviewExcelExportTemplate implements Serializable {
	private static Logger log = LogManager.getLogger(WebReviewExportTemplate.class);
	private static final long serialVersionUID = -2451093586990768406L;

	public void toExcel(List<ReviewPool> list, HttpServletRequest request, int length, String f, OutputStream out)
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
			HSSFCellStyle styleRED = excelContentRED(book);
			try {

				String[][] str = new String[][] { { "工单号", "订单号", "TB单号", "下单人账号", "宝贝名称", "数量", "网单号", "SKU编码", "发票金额",
						"收货联系人","联系人电话", "配送网点", "下单时间", "付款时间", "所属区域", "型号", "配送时效(天)", "所属工贸", "人员", "物流中心", "责任位一 ",
						"责任位二 ", "责任位三 ", "工单去向", "工单传送状态", "物流中心类别", "网单金额 ", "创建时间  ", "反馈内容 ", "操作人 ", "中间结果 ",
						"中间结果操作人 ", "中间结果操作时间 ", "最终结果 ", "最终结果操作人 ", "最终结果操作时间 ", "未处理上诉次数", "中间结果上诉次数", "一次处理时效 ",
						"工单处理时效 ", "用户咨询次数 ", "订单来源 ", "工单状态", "库存类型", "工单关闭原因", "是否投诉", "订单渠道" } };

				
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
					// if (i == 5) {
					// style = excelContentRED(book);
					// } else {
					// style = excelContent(book);
					// }
					ReviewPool pool = list.get(length * (j) + i - 1);
					int index = 0;
					row = sheet.createRow(i);

					HSSFCell cell = row.createCell(index++);
					row.setHeight((short) 300);// 设置行高
					cell.setCellValue(new HSSFRichTextString(pool.getId()));// 工单号
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getOrderId()));// 订单编号
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					// tb单号
					cell.setCellValue(new HSSFRichTextString(pool.getTbOrderSn()));
					cell.setCellStyle(style);
					cell = row.createCell(index++);

					cell.setCellValue(new HSSFRichTextString(pool.getNextUserName()));// 下单人-
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getProductName()));// 宝贝名称
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					String orderQty = (pool.getNumber() != null) ? pool.getNumber().toString() : "0";
					cell.setCellValue(new HSSFRichTextString(orderQty));// 数量
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getNetOrderId()));// 网单号
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getSku()));// SKU编码
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getProductAmount()));// 发票金额
					cell.setCellStyle(style);

					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getBuyer()));// 收获联系人
					cell.setCellStyle(style);
					
					cell = row.createCell(index++);
					// 新增
					if (("天猫渠道-发票类".equals(pool.getQuestion1Level1()) && "电子发票".equals(pool.getQuestion1Level2())) ||
							("顺逛商城-发票类".equals(pool.getQuestion1Level1()) && "发票问题".equals(pool.getQuestion1Level2())
									&& "电子发票".equals(pool.getQuestion1Level3()))) {
						cell.setCellValue(new HSSFRichTextString(pool.getBuyerMobile()));// 收货电话
					} else {
						cell.setCellValue(new HSSFRichTextString(""));
					}
					cell.setCellStyle(style);
					
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getNetPointId()));// 配送网点
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getCreateTime()));// 下单时间
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getPayTime()));// 付款时间
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					// 针对 天猫渠道-发票类 电子发票 修改导出文件的格式
					if (("天猫渠道-发票类".equals(pool.getQuestion1Level1()) && "电子发票".equals(pool.getQuestion1Level2())) ||
							("顺逛商城-发票类".equals(pool.getQuestion1Level1()) && "发票问题".equals(pool.getQuestion1Level2())
									 && "电子发票".equals(pool.getQuestion1Level3()))) {
						cell.setCellValue(new HSSFRichTextString(
								StringUtils.deleteWhitespace(pool.getRegionName()) + pool.getAddress()));
					} else {
						cell.setCellValue(new HSSFRichTextString(pool.getRegionName()));// 所属区域
					}

					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getProductType()));// 型号
					cell.setCellStyle(style);
					// cell = row.createCell(index++);
					// cell.setCellValue(new
					// HSSFRichTextString(pool.getAddress()));//详细地址
					// cell.setCellStyle(style);

					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getOrderCome()));// 工单来源
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getCompany()));// 所属工贸
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getWangId()));// 人员
																				// xxxxx
																				// 不确定
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getPhone()));// 中心
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getQuestion1Level1()));// 责任方一
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getQuestion1Level2()));// 责任方二
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getQuestion1Level3()));// 责任方三
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getWorkOrderTo()));// 工单去向
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getDesideText()));// 工单传送状态
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getCenterType()));// 物流中心类别
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getProductAmount()));// 网单金额
																						// XXX
																						// 发票金额？？
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getInsertTime()));// 创建时间
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getContext()));// 评论内容
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getRemark3()));// 操作人
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getBackContext2()));// 中间结果
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getRemark1()));// 中间结果操作人
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getWorkOrderTime()));// 中间结果操作时间
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getBackContext3()));// 最终结果
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getRemark2()));// 最终结果操作人
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getPosition3()));// 最终结果操作时间
					cell.setCellStyle(style);

					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(
							pool.getAppealCount1() != null ? pool.getAppealCount1().toString() : "0"));// 未处理上诉次数
					// 判断未处理上诉次数是否大于等于2
					if (pool.getAppealCount1() >= 2) {
						// 将字设置为红色
						cell.setCellStyle(styleRED);
					} else {
						// 将字设置为黑色
						cell.setCellStyle(style);
					}

					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(
							pool.getAppealCount2() != null ? pool.getAppealCount2().toString() : "0"));// 中间结果上诉次数
					// 判断中间结果上诉次数是否大于等于2
					if (pool.getAppealCount2() >= 2) {
						// 将字设置为红色
						cell.setCellStyle(styleRED);
					} else {

						cell.setCellStyle(style);
					}
					// 将字恢复为黑色
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getOneProcessTime()));// 一次处理时效
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getOrderProcessTime()));// 工单处理时效
					cell.setCellStyle(style);

					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(
							pool.getFeedBackCount() != null ? pool.getFeedBackCount().toString() : "0"));// 用户咨询次数
					// 判断用户咨询次数是否大于等于3
					if (pool.getFeedBackCount() >= 3) {
						// 将字设置为红色
						cell.setCellStyle(styleRED);
					} else {
						cell.setCellStyle(style);
					}

					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getRemark5()));// 订单来源
					cell.setCellStyle(style);

					String workSt = pool.getWorkStatus();
					String tmp = "";
					if ("0".equals(workSt)) {
						tmp = "未处理";
					} else if ("2".equals(workSt)) {
						tmp = "中间结果";
					} else if ("3".equals(workSt)) {
						tmp = "最终结果";
					}
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(tmp));// 工单状态
					cell.setCellStyle(style);

					cell = row.createCell(index++);
					cell.setCellValue(pool.getStoreType());// 库存类型
					cell.setCellStyle(style);

					// 工单关闭原因
					String closeType = pool.getCloseType();
					String closeTypeTmp = "";
					if ("1".equals(closeType)) {
						closeTypeTmp = "正常关闭";
					} else if ("2".equals(closeType)) {
						closeTypeTmp = "强制关闭";
					} else if ("3".equals(closeType)) {
						closeTypeTmp = "虚假封单";
					} else if ("4".equals(closeType)) {
						closeTypeTmp = "客服原因";
					}

					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(closeTypeTmp));// 工单关闭原因
					cell.setCellStyle(style);

					Integer complaintFlg = pool.getComplaintFlg();
					String complaintFlgTmp = "";
					if (1 == complaintFlg) {
						complaintFlgTmp = "是";
					} else if (0 == complaintFlg) {
						complaintFlgTmp = "否";
					}
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(complaintFlgTmp));// 是否投诉
					cell.setCellStyle(style);
					cell = row.createCell(index++);
					cell.setCellValue(new HSSFRichTextString(pool.getChannelName()));// 是否投诉
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
