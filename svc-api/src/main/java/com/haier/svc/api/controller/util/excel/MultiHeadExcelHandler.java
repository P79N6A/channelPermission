package com.haier.svc.api.controller.util.excel;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/6/15 9:12
 * @modificed by:
 */
public class MultiHeadExcelHandler {

  private HSSFWorkbook workbook;
  private HSSFSheet sheet;
  //标题样式
  private HSSFCellStyle titleCellStyle;
  //内容样式
  private HSSFCellStyle contentCellStyle;

  //导出类
  private Class _class;
  //导出表格名称
  private ExcelName excelName;

  //对应数值的标题，所对应的字段属性
  private TreeMap<Integer,Field> realFields;
  //  private List<Field> titleFields;
  //表格所有的标题
  private List<ExcelTitle> titles;

  //标题行数
  private int titleRows;
  //标题列数
  private int titleColumns;
  //需要合并的单元格
  List<CellRangeAddress> cellRangeAddresses;

  //导出的数据
  private List data;

  public HSSFCellStyle getTitleCellStyle() {
    if (null == this.titleCellStyle) {
      titleCellStyle = workbook.createCellStyle();
      setCellStyle(titleCellStyle);
      setTitleFontStyle(titleCellStyle);

    }
    return titleCellStyle;
  }

  public HSSFCellStyle getContentCellStyle() {
    if (null == this.contentCellStyle) {
      contentCellStyle = workbook.createCellStyle();
      setCellStyle(contentCellStyle);
      setContentFontStyle(contentCellStyle);
    }
    return contentCellStyle;
  }


  public void setCellStyle(HSSFCellStyle cellStyle) {
    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
  }

  public void setTitleFontStyle(HSSFCellStyle cellStyle) {
    HSSFFont font = workbook.createFont();
    font.setFontName("宋体");
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
    font.setFontHeightInPoints((short) 11);
    cellStyle.setFont(font);//选择需要用到的字体格式
  }

  public void setContentFontStyle(HSSFCellStyle cellStyle) {
    HSSFFont font = workbook.createFont();
    font.setFontName("宋体");
    font.setFontHeightInPoints((short) 10);
    cellStyle.setFont(font);//选择需要用到的字体格式
  }


  public MultiHeadExcelHandler(Class _class) {
    this._class = _class;
    excelName = (ExcelName) _class.getAnnotation(ExcelName.class);
    this.init();
  }

  private void init() {
    Field[] fields = _class.getDeclaredFields();
    titles = new LinkedList<ExcelTitle>();
    realFields = new TreeMap<Integer,Field>();
//    titleFields = new LinkedList<Field>();
    for (Field field : fields) {
      field.setAccessible(true);
      ExcelTitle excelTitle = field.getAnnotation(ExcelTitle.class);
      if (null == excelTitle) {
        continue;
      }
      if (null != excelTitle.cellValue()) {
        titles.add(excelTitle);
//        titleFields.add(field);
        if (excelTitle.realTitle()) {
          realFields.put(excelTitle.columnStartIndex(),field);
        }
      }
    }
    if (null == cellRangeAddresses) {
      cellRangeAddresses = new ArrayList<CellRangeAddress>();
    }
  }

  private void calculate() {
    for (ExcelTitle title : titles) {
      titleRows =
          titleRows < (title.rowStartIndex() + title.rowNum() - 1) ? (title.rowStartIndex()
              + title.rowNum() - 1) : titleRows;
      titleColumns = titleColumns < (title.columnStartIndex() + title.columnNum() - 1) ? (
          title.columnStartIndex() + title.columnNum() - 1) : titleColumns;
      //计算合并那些单元格
      if (title.rowNum() > 1) {
        CellRangeAddress cellRangeAddress = new CellRangeAddress(title.rowStartIndex() - 1,
            title.rowNum() + title.rowStartIndex() - 2, title.columnStartIndex() - 1,
            title.columnStartIndex() - 1);
        cellRangeAddresses.add(cellRangeAddress);
      }
      if (title.columnNum() > 1) {
        CellRangeAddress cellRangeAddress = new CellRangeAddress(title.rowStartIndex() - 1,
            title.rowStartIndex() - 1, title.columnStartIndex() - 1,
            title.columnStartIndex() + title.columnNum() - 2);
        cellRangeAddresses.add(cellRangeAddress);
      }
    }
  }

  private void setExcelTitle() {
    for (int i = 0; i < titleRows; i++) {
      HSSFRow row = sheet.createRow(i);
      //每行输出标题
      for (int j = 0; j < titleColumns; j++) {
        HSSFCell hssfCell = row.createCell(j);
        hssfCell.setCellStyle(titleCellStyle);
        for (ExcelTitle title : titles) {
          //根据位置等位标题位置
          if ((title.rowStartIndex() - 1 == i) && (title.columnStartIndex() - 1 == j)) {
            hssfCell.setCellValue(title.cellValue());
          }
        }

      }
    }
    //合并标题的单元格
    for (CellRangeAddress cellRangeAddress : cellRangeAddresses) {
      sheet.addMergedRegion(cellRangeAddress);
    }

  }

  public void setData(List data) {
    this.data = data;
  }

  private void setData() {
    if (CollectionUtils.isEmpty(data)) {
      return;
    }
    int rowNo = titleRows;
    for (Object object : this.data) {
      HSSFRow row = this.sheet.createRow(rowNo++);
      int index = 0;
      for (Map.Entry<Integer, Field> entry : realFields.entrySet()) {
        try {
          //获取对象对应字段的值
          Object value = entry.getValue().get(object);
          HSSFCell cell = row.createCell(index++);
          cell.setCellStyle(contentCellStyle);
          if (null == value) {
            continue;
          }
          if (entry.getValue().getType().equals(Date.class)) {
            value = DateFormatUtils.format((Date) value, "yyyy/MM/dd HH:mm:ss");
          }
          if (entry.getValue().getType().equals(Long.class) || entry.getValue().getType().equals(Integer.class) || entry.getValue()
              .getType().equals(Short.class)) {
            cell.setCellValue(Double.valueOf(value.toString()));
          }else {

            cell.setCellValue(value.toString());
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }

  }

  public void exportExcel(HttpServletResponse response) {
    workbook = new HSSFWorkbook();
    if (null==this.excelName){
      sheet = workbook.createSheet("表格");
    }else {
      sheet = workbook.createSheet(this.excelName.fileName());
    }
    getTitleCellStyle();
    getContentCellStyle();
    init();
    calculate();
    setExcelTitle();
    setData();

    setResponseHeader(response);
    OutputStream out = null;
    try {
      out = response.getOutputStream();
      this.workbook.write(out);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void setResponseHeader(HttpServletResponse response) {
    try {
      response.setContentType("application/octet-stream;charset=UTF-8");
      response.setHeader("Content-Disposition",
          "attachment;filename=" + java.net.URLEncoder
              .encode(
                  this.excelName.fileName() + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"),
                  "UTF-8")
              + ".xls");
      response.addHeader("Pargam", "no-cache");
      response.addHeader("Cache-Control", "no-cache");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


}
