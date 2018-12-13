package com.haier.svc.api.controller.util.excel;

import com.haier.shop.util.excel.Excel;
import com.haier.shop.util.excel.ExcelTitle;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.svc.api.controller.util.excel
 * @Date: Created in 2018/5/23 17:16
 * @Modified By:
 */
public class ExcelHandler {

  private List data;
  private List<Field> fieldsExp;
  private List<Field> fieldsImp;
  private Class _class;
  private Excel excel;
  private List<ExcelTitle> titles;
  private HSSFSheet sheet;
  private HSSFCellStyle hssfCellStyle;
  private HSSFWorkbook hssf;
  private XSSFWorkbook xssf;

  private ExcelHandler() {

  }

  public ExcelHandler(Class _class) {
    this._class = _class;
    this.excel = (Excel) _class.getAnnotation(Excel.class);
    this.init();
  }

  public HSSFCellStyle getHSSFCellStyle() {
    if (this.hssfCellStyle == null) {
      this.hssfCellStyle = hssf.createCellStyle();
      this.hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      this.hssfCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
      this.hssfCellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
      this.hssfCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
      this.hssfCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
      this.hssfCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
      this.hssfCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边
    }
    return this.hssfCellStyle;
  }

  private void init() {
    this.titles = new LinkedList<>();
    this.fieldsExp = new LinkedList<>();
    this.fieldsImp = new LinkedList<>();
    Field[] fs = this._class.getDeclaredFields();
    for (Field f : fs) {
      f.setAccessible(true);
      ExcelTitle et = f.getAnnotation(ExcelTitle.class);
      if (null == et) {
        continue;
      }
      if (!StringUtils.isEmpty(et.titleName())) {
        this.fieldsExp.add(f);
        this.titles.add(et);
      }
      if (et.importIndex() >= 0) {
        this.fieldsImp.add(f);
      }
    }

  }

  private boolean emptyData() {
    return null == data || data.size() == 0;
  }

  public void setData(List data) {
    this.data = data;
  }

  private void setData() {
    if (emptyData()) {
      return;
    }
    int rowNo = 1;
    for (Object o : this.data) {
      HSSFRow row = this.sheet.createRow(rowNo++);
      int index = 0;
      for (Field f : this.fieldsExp) {
        try {
          Object value = f.get(o);
          HSSFCell cell = row.createCell(index++);
          if (null == value) {
            continue;
          }
          if (f.getType().equals(Date.class)) {
            value = DateFormatUtils.format((Date) value, "yyyy/MM/dd HH:mm:ss");
          }
          cell.setCellValue(value.toString());
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }

  }

  private void setExcelTitle() {
    HSSFRow row = this.sheet.createRow(0);
    int index = 0;
    for (ExcelTitle et : this.titles) {
      HSSFCell cell = row.createCell(index++);
      cell.setCellValue(et.titleName());
      cell.setCellStyle(this.hssfCellStyle);
    }
  }

  private void setResponseHeader(HttpServletResponse response) {
    try {
      response.setContentType("application/octet-stream;charset=UTF-8");
      response.setHeader("Content-Disposition",
          "attachment;filename=" + java.net.URLEncoder
              .encode(this.excel.filename() + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"),
                  "UTF-8")
              + ".xls");
      response.addHeader("Pargam", "no-cache");
      response.addHeader("Cache-Control", "no-cache");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void exportxls(HttpServletResponse response) {
    this.hssf = new HSSFWorkbook();
    this.sheet = this.hssf.createSheet(this.excel.filename());
    getHSSFCellStyle();
    setExcelTitle();
    setData();
    OutputStream out = null;
    setResponseHeader(response);
    try {
      out = response.getOutputStream();
      this.hssf.write(out);
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

  public XSSFWorkbook exportxlsx() {
    return null;
  }

  public <T> void convertToEntity(List<List<String>> source, List<T> target) {
    try {
      for (List<String> l : source) {
        T o = (T) this._class.newInstance();
        for (Field f : this.fieldsImp) {
          String s = l.get(f.getAnnotation(ExcelTitle.class).importIndex());
          Class c = f.getType();
          if (StringUtils.isEmpty(s)) {
            continue;
          }
          if (BigDecimal.class.equals(c)&&s!=null) {
            f.set(o, new BigDecimal(s));
          }else if(Date.class.equals(c)&&s!=null){
            Date date = null;
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
            date=format1.parse(s);
            f.set(o,date);
          } else {
            f.set(o, s);
          }
        }
        target.add(o);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
