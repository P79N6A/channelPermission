package com.haier.svc.api.controller.util;

import java.io.OutputStream;

import jxl.write.WritableSheet;

public interface ExcelCallbackInterfaceUtil {

    /**
     * 待调用者实现的数据导出回调接口
     * @param os 输出流
     * @param sheet 设置完毕的sheet对象
     * @param temp 起始行号
     * @throws Exception
     */
    public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp) throws Exception;
}
