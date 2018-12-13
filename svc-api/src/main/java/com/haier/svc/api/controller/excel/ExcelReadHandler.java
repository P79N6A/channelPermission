package com.haier.svc.api.controller.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.svc.api.controller.excel
 * @Date: Created in 2018/5/25 10:16
 * @Modified By:
 */
public class ExcelReadHandler {

  public int totalRows; //sheet中总行数
  public static int totalCells; //每一行总单元格数

  /**
   * read the Excel .xlsx,.xls
   *
   * @param file jsp中的上传文件
   */
  public List<List<String>>   readExcel(MultipartFile file) throws IOException {
    if (file == null || ExcelUtils.EMPTY.equals(file.getOriginalFilename().trim())) {
      return null;
    } else {
      String postfix = ExcelUtils.getPostfix(file.getOriginalFilename());
      if (!ExcelUtils.EMPTY.equals(postfix)) {
        if (ExcelUtils.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
          return readXls(file);
        } else if (ExcelUtils.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
          return readXlsx(file);
        } else {
          return null;
        }
      }
    }
    return null;
  }

  /**
   * read the Excel 2010 .xlsx
   */
  @SuppressWarnings("deprecation")
  public List<List<String>> readXlsx(MultipartFile file) {
    List<List<String>> list = new LinkedList<>();
    // IO流读取文件
    InputStream input = null;
    XSSFWorkbook wb = null;
    List<String> rowList = null;
    try {
      input = file.getInputStream();
      // 创建文档
      wb = new XSSFWorkbook(input);
      //读取sheet(页)
      for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
        XSSFSheet xssfSheet = wb.getSheetAt(numSheet);
        if (xssfSheet == null) {
          continue;
        }
        totalRows = xssfSheet.getLastRowNum();
        //读取Row,从第二行开始
        for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
          XSSFRow xssfRow = xssfSheet.getRow(rowNum);
          if (xssfRow != null) {
            rowList = new ArrayList<>();
            totalCells = xssfRow.getLastCellNum();
            //读取列，从第一列开始
            for (int c = 0; c <= totalCells; c++) {
              XSSFCell cell = xssfRow.getCell(c);
              if (cell == null) {
                rowList.add(ExcelUtils.EMPTY);
                continue;
              }
              rowList.add(ExcelUtils.getXValue(cell).trim());
            }
            list.add(rowList);
          }
        }
      }
      return list;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        input.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;

  }

  /**
   * read the Excel 2003-2007 .xls
   */
  public List<List<String>> readXls(MultipartFile file) {
    List<List<String>> list = new LinkedList<>();
    // IO流读取文件
    InputStream input = null;
    HSSFWorkbook wb;
    List<String> rowList;
    try {
      input = file.getInputStream();
      // 创建文档
      wb = new HSSFWorkbook(input);
      //读取sheet(页)
      HSSFSheet hssfSheet = wb.getSheetAt(0);
      if (hssfSheet == null) {
        return new LinkedList<>();
      }
      totalRows = hssfSheet.getLastRowNum();
      //读取Row,从第二行开始
      for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
        HSSFRow hssfRow = hssfSheet.getRow(rowNum);
        if (hssfRow != null) {
          rowList = new ArrayList<>();
          totalCells = hssfRow.getLastCellNum();
          //读取列，从第一列开始
          for (int c = 0; c < totalCells; c++) {
            HSSFCell cell = hssfRow.getCell(c);
            if (cell == null) {
              rowList.add("");
              continue;
            }
            rowList.add(ExcelUtils.getHValue(cell).trim());
          }
          list.add(rowList);
        }
      }
      return list;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        input.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}