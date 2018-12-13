package com.haier.svc.api.controller.excel;

import com.haier.shop.model.PersonnelStatistic;
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

public class WebReviewExportPersonnelStatistic extends WebReviewExcelExportTemplate implements
                                                                                   Serializable {
    private static Logger log              = LogManager
                                                   .getLogger(WebReviewExportPersonnelStatistic.class);
    private static final long serialVersionUID = -2451093586990768406L;

    public void toExcel(List<PersonnelStatistic> list, HttpServletRequest request, int length,
                        String f, OutputStream out) throws IOException {

        Long info = System.currentTimeMillis();
        String path = request.getSession().getServletContext().getRealPath("/");
        int pathIndex = path.lastIndexOf(File.separatorChar);
        path = path.substring(0, pathIndex + 1);
        //        File zip = new File(path + f + ".zip");// 压缩文件
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
                String[][] str = new String[][] { { "人员", "工贸", "中间结果上诉次数", "未处理条数", "中间结果条数",
                        "最终结果条数", "工单处理完成率" } };
                HSSFRow row = sheet.createRow(0);
                for (int i = 0; i < str.length; i++) {
                    String[] src = str[i];
                    for (int k = 0; k < src.length; k++) {
                        //                      row.createCell(k).setCellValue(src[k]);
                        row.setHeight((short) 300);//设置行高
                        HSSFCell cell0 = row.createCell(k);
                        cell0.setCellValue(new HSSFRichTextString(src[k]));
                        //设置头样式
                        cell0.setCellStyle(columnHeadStyle);
                        //设置列宽
                        sheet.setColumnWidth(k, 5000);
                    }
                }
                for (int i = 1, min = (list.size() - j * length + 1) > (length + 1) ? (length + 1)
                    : (list.size() - j * length + 1); i < min; i++) {
                    PersonnelStatistic duty = list.get(length * (j) + i - 1);
                    int index = 0;
                    row = sheet.createRow(i);
                    HSSFCell cell = row.createCell(index++);
                    row.setHeight((short) 300);//设置行高
                    cell.setCellValue(new HSSFRichTextString(duty.getWangId()));//人员
                    cell.setCellStyle(style);
                    cell = row.createCell(index++);
                    cell.setCellValue(new HSSFRichTextString(duty.getCompany()));//工贸
                    cell.setCellStyle(style);
                    cell = row.createCell(index++);
                    cell.setCellValue(new HSSFRichTextString(duty.getAppealCount2()));//中间结果上诉次数
                    cell.setCellStyle(style);
                    cell = row.createCell(index++);
                    cell.setCellValue(new HSSFRichTextString(duty.getUnsettledNum()));//未处理
                    cell.setCellStyle(style);
                    cell = row.createCell(index++);
                    cell.setCellValue(new HSSFRichTextString(duty.getMiddleNum()));//中间结果
                    cell.setCellStyle(style);
                    cell = row.createCell(index++);
                    cell.setCellValue(new HSSFRichTextString(duty.getFinallyNum()));//最终结果
                    cell.setCellStyle(style);
                    cell = row.createCell(index++);
                    cell.setCellValue(new HSSFRichTextString(duty.getFinishPercent()));//完成率
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
