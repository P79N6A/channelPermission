package com.haier.svc.api.controller.order;

import com.haier.common.util.StringUtil;
import com.haier.invoice.util.CommUtil;
import com.haier.invoice.util.ExcelCallbackInterfaceUtil;
import com.haier.invoice.util.ExcelExportUtil;
import com.haier.shop.service.TBOrderSnExportService;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author:JinXueqian
 * @Date: 2018/8/6 17:54
 */
@Controller
@RequestMapping("order/")
public class TBOrderSnExportController {

    private static final Logger logger = LogManager.getLogger(TBOrderSnExportController.class);
    @Autowired
    TBOrderSnExportService tbOrderSnExportService;

    /**
     * 导出页面跳转
     * @return
     */
    @RequestMapping(value = {"loadtbOrderSnExportPage.html"}, method = {RequestMethod.GET})
    public String loadtbOrderSnExportPage() {
        return "order/tbOrderSnExportPage";
    }


    /**
     * 导出功能根据sourceOrderSn获取tbOrderSn
     * @param
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = { "exporttbOrderSnInfo" }, method = {RequestMethod.POST})
    public void exporttbOrderSnInfo(@RequestParam(required = true) String sourceOrderSns,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        if (StringUtil.isEmpty(sourceOrderSns.replace("\r\n", ""), true)) {
            response.reset();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("请提交有效的来源订单号！");
            response.getWriter().flush();
            return;
        }

        String[] strTemp = sourceOrderSns.split("\r\n");
        //        Set<String> set = new HashSet<String>(Arrays.asList(strTemp));
        //        String[] cOrderSnsArray = set.toArray(new String[0]);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sourceOrderSns", Arrays.asList(strTemp));
        //获取网单列表List
        final List<Map<String, Object>> result = tbOrderSnExportService.getTBOrderSnBySourceOrderSn(paramMap);
        //转订单来源到渠道
        if (result == null || result.size() == 0) {
            response.reset();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("没有查询到TB单号信息，请核对来源订单号是否正确！");
            response.getWriter().flush();
            return;
        }

        String fileName = "TB单号数据" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "TB单号数据导出";
        String[] sheetHead = new String[] { "序号", "来源订单号", "TB单号"};

        try {
            ExcelExportUtil.exportEntity(request, response, fileName, sheetName, sheetHead, new ExcelCallbackInterfaceUtil() {
                @Override
                public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp) throws Exception {
                    setExcelBodyTotalForTBOrderSnList(sheet, temp, result);
                }
            });
        } catch (Exception e) {
            logger.error("TB单号数据导出失败", e);
            e.printStackTrace();
        }
    }

    /**
     * 导出TB单号具体数据，实现回调函数
     *
     * @param sheet
     * @param temp
     * @param list
     * @throws Exception
     */
    private void setExcelBodyTotalForTBOrderSnList(WritableSheet sheet, int temp,List<Map<String, Object>> list) throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setAlignment(jxl.format.Alignment.RIGHT);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        int index = 0;

        for (Map<String, Object> map : list) {
            index++;
            //序号
            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, CommUtil.getStringValue(index), textFormat));
            //来源订单号
            sheet.setColumnView(1, 30);
            sheet.addCell(new Label(1, temp, CommUtil.getStringValue(map.get("sourceOderSnStr")), textFormat));
            //TB单号
            sheet.setColumnView(2, 30);
            sheet.addCell(new Label(2, temp, CommUtil.getStringValue(map.get("tbOrderSn")), textFormat));
            temp++;
        }

    }
}
