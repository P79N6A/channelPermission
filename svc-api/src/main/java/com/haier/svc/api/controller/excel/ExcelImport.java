package com.haier.svc.api.controller.excel;

import com.google.common.base.Strings;
import com.haier.common.ServiceResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jtbshan on 2018/5/24.
 */
public class ExcelImport {
    public static ServiceResult transferStreamToExcel (InputStream inputStream) {
        ServiceResult serviceResult = new ServiceResult();
        List<String> titleInfo = new ArrayList<String>();
        List<List<String>> body = new ArrayList<List<String>>();
        XSSFWorkbook workbook = null;
        XSSFSheet sheet;
        XSSFRow row;

        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return serviceResult;
        }
        //读取表头 begin
        sheet = workbook.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        for (int i = 0; i < colNum; i++) {
            Cell cell=row.getCell(i);
            if (cell.getCellType() != Cell.CELL_TYPE_STRING) {
                serviceResult.setError("error","导入出错，请不要修改单元格格式("+i+")");
                return serviceResult;
            }
            titleInfo.add(cell.getStringCellValue());
        }
        //读取表头 end
        //读取内容 begin
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            List<String> rowResult = new ArrayList<String>();
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                Cell cell = row.getCell(j);
                String cellValue = getCellValue(i, j, cell, serviceResult);
                if (cellValue == null) {
                    return serviceResult;
                }
                rowResult.add(cellValue);
                j++;
            }
            body.add(rowResult);
        }
        //读取内容 end
        ExcelInfo excelinfo = new ExcelInfo();
        excelinfo.setTitleInfo(titleInfo);
        excelinfo.setBobyInfo(body);
        serviceResult.setResult(excelinfo);

        return serviceResult;
    }


    private static String getCellValue(int line,int col, Cell cell, ServiceResult serviceResult) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = null;
        if (cell==null){
            result="";
        }else{
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        result = sdf.format(cell.getDateCellValue());
                        //result = TimeUtilis.getDateStringByForm(cell.getDateCellValue(), "yyyy-MM-dd");
                    } else {
                        BigDecimal big=new BigDecimal(cell.getNumericCellValue());
                        result = big.toString();
                        //解决1234.0  去掉后面的.0
                        if(null!=result&&!"".equals(result.trim())){
                            String[] item = result.split("[.]");
                            if(1<item.length&&"0".equals(item[1])){
                                result=item[0];
                            }
                        }
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    //result=cell.getCellFormula();
                    try {
                        result = String.valueOf(cell.getStringCellValue());
                    } catch (IllegalStateException e) {
                        try {
                            result = String.valueOf(cell.getNumericCellValue());
                        }catch (Exception ex){
                            result="";
                        }
                    }
                    break;
                case Cell.CELL_TYPE_BLANK:
                    result = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = "";
                    break;
                default:
                    serviceResult.setSuccess(false);
                    serviceResult.setMessage("获取内容发生错误！行号：" + line+" 列号:"+col);
                    serviceResult.setError("error","获取内容发生错误！行号：" + line+" 列号:"+col);
                    break;
            }
        }

        return result;
    }

    public static void isNullCheck(int index, String col, String colname, StringBuffer result) {
        if (Strings.isNullOrEmpty(col)) {
            result.append("第" + (index + 1) + "行:" + colname + "为空;");
        }
    }
}
