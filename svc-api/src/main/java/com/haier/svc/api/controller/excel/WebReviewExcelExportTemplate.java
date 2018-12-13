package com.haier.svc.api.controller.excel;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @author WangXuzheng
 *
 */
public abstract class WebReviewExcelExportTemplate {
    private static Logger log = LogManager.getLogger(WebReviewExcelExportTemplate.class);

    /* (non-Javadoc)
     * @see com.haier.openplatform.excel.ExcelExportService#doExport(java.io.OutputStream)
     */
    public void doExport(HttpServletResponse response, String fileName) throws IOException {
    }

    public HSSFCellStyle excelContentRED(HSSFWorkbook book) {
        // 内容字体
        HSSFFont headfont2 = book.createFont();
        //        headfont2.setFontName("宋体");
        //        headfont2.setFontHeightInPoints((short) 11);// 字体大小  
        headfont2.setColor(IndexedColors.RED.getIndex());
        // 内容样式
        HSSFCellStyle headstyle2 = book.createCellStyle();
        headstyle2.setFont(headfont2);
        //      headstyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  
        headstyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中  
        headstyle2.setLocked(true);
        headstyle2.setWrapText(true);// 自动换行  
        // 普通单元格样式  
        HSSFCellStyle style = book.createCellStyle();
        style.setFont(headfont2);
        //      style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 上下居中  
        style.setWrapText(true);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft((short) 1);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight((short) 1);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体  
        style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．  
        style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．  
        return style;
    }

    public HSSFCellStyle excelContent(HSSFWorkbook book) {
        // 内容字体
        HSSFFont headfont2 = book.createFont();
        //        headfont2.setFontName("宋体");
        //        headfont2.setFontHeightInPoints((short) 11);// 字体大小  
        // 内容样式
        HSSFCellStyle headstyle2 = book.createCellStyle();
        headstyle2.setFont(headfont2);
        //		headstyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  
        headstyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中  
        headstyle2.setLocked(true);
        headstyle2.setWrapText(true);// 自动换行  
        // 普通单元格样式  
        HSSFCellStyle style = book.createCellStyle();
        style.setFont(headfont2);
        //		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 上下居中  
        style.setWrapText(true);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft((short) 1);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight((short) 1);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体  
        style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．  
        style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．  
        return style;
    }

    // 头字体
    public HSSFCellStyle excelHeader(HSSFWorkbook book) {
        HSSFFont headfont = book.createFont();
        headfont.setFontName("宋体");
        headfont.setFontHeightInPoints((short) 12.5);// 字体大小  
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗  
        // 头样式
        HSSFCellStyle headstyle = book.createCellStyle();
        headstyle.setFont(headfont);
        //		headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  
        headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中  
        headstyle.setLocked(true);
        headstyle.setWrapText(true);// 自动换行  
        // 头单元格样式  
        HSSFCellStyle columnHeadStyle = book.createCellStyle();
        columnHeadStyle.setFont(headfont);
        //		columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中  
        columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中  
        columnHeadStyle.setLocked(true);
        columnHeadStyle.setWrapText(true);
        columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色  
        columnHeadStyle.setBorderLeft((short) 1);// 边框的大小  
        columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色  
        columnHeadStyle.setBorderRight((short) 1);// 边框的大小  
        columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体  
        columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色  
        // 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）  
        columnHeadStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        columnHeadStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return columnHeadStyle;
    }
}
