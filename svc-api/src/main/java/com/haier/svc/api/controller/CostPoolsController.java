package com.haier.svc.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.invoice.util.CommUtil;
import com.haier.invoice.util.ExcelCallbackInterfaceUtil;
import com.haier.invoice.util.ExcelExportUtil;
import com.haier.order.service.OrderCostPoolsService;
import com.haier.shop.model.CostPools;
import com.haier.shop.model.Ustring;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.Boolean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 营销费用池
 */
@Controller
@RequestMapping("costPool")
public class CostPoolsController {

    private final static Logger logger = LoggerFactory.getLogger(CostPoolsController.class);

    @Autowired
    private OrderCostPoolsService orderCostPoolsService;

    /**
     * 跳转到营销费用池页面
     * @param request
     * @return
     */
    @RequestMapping(value = "toCostPool" ,method = {RequestMethod.GET})
    public String toCostPool (HttpServletRequest request){ return "order/costPoolsList"; }

    /**
     * 跳转到费用池使用日志页面
     * @param request
     * @return
     */
    @RequestMapping(value = "loadCostPoolsUsedLogs" ,method = {RequestMethod.GET})
    public String loadCostPoolsUsedLogs (HttpServletRequest request){ return "order/costPoolsUsedLogsList"; }

    /**
     * 查询营销费用池日志
     * @param request
     * @param response
     */
    @RequestMapping(value = "searchCPSUsedLogsList")
    @ResponseBody
    public void search(@RequestParam(required = false) String orderSn,
                       @RequestParam(required = false) String cOrderSn,
                       @RequestParam(required = false) String addTimeMin,
                       @RequestParam(required = false) String addTimeMax,
                       @RequestParam(required = false) String channel,
                       @RequestParam(required = false) String type,
                       @RequestParam(required = false) String chanYe,
                       @RequestParam(required = false) String usedType,
                       @RequestParam(required = false) String source,
                       @RequestParam(required = false) Integer rows,
                       @RequestParam(required = false) Integer page,
                       HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        try{
            if (rows == null)
                rows = 50;
            if (page == null)
                page = 1;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            params.put("orderSn",orderSn);
            params.put("cOrderSn",cOrderSn);
            params.put("addTimeMin",addTimeMin);
            params.put("addTimeMax",addTimeMax);
            params.put("channel",channel);
            params.put("type",type);
            params.put("chanYe",chanYe);
            params.put("usedType",usedType);
            params.put("source",source);

            Map<String,Object> retMap = orderCostPoolsService.findCostPoolsUsedLogsByPage(params);

            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();

        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 查询营销费用池
     * @param request
     * @param response
     */
    @RequestMapping(value = "search")
    @ResponseBody
    public void search(@RequestParam(required = false) String yearMonth,
                       @RequestParam(required = false) String batch,
                       @RequestParam(required = false) String type,
                       @RequestParam(required = false) String channel,
                       @RequestParam(required = false) String chanYe,
                       @RequestParam(required = false) Integer rows,
                       @RequestParam(required = false) Integer page,
                       HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        try{
            if (rows == null)
                rows = 50;
            if (page == null)
                page = 1;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            params.put("yearMonth",yearMonth);
            params.put("batch",batch);
            params.put("type",type);
            params.put("channel",channel);
            params.put("chanYe",chanYe);

            Map<String,Object> retMap = orderCostPoolsService.findCostPoolsByPage(params);

            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 跳转到费用池新增页面
     * @param request
     * @return
     */
    @RequestMapping(value = "toAddCostPool" ,method = {RequestMethod.GET})
    public String toAddCostPool (HttpServletRequest request){ return "order/costPoolsAdd"; }

    /**
     * 新增费用池逻辑
     * @param request
     * @return
     */
    @RequestMapping(value = "addCostPool" ,method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String addCostPool (
            Integer channel, Integer type, Integer yearMonth,
            String remark, BigDecimal C200076226, BigDecimal C200076227,
            BigDecimal C200076228, BigDecimal C200076229, BigDecimal C200076230,
            BigDecimal C200076231, BigDecimal C200076232, BigDecimal C200076233,
            BigDecimal C20170825, HttpServletRequest request){
        JSONObject json = new JSONObject();
        StringBuffer sb = new StringBuffer();
        json.put("flag",1);
        try {
            if(channel==null||type==null||yearMonth==null){
                logger.error("[CostPoolsController][addCostPool]重要参数为空");
                json.put("flag",0);
                return json.toString();
            }
            if(remark==null){
                remark="";
            }
            BigDecimal dec = new BigDecimal(0);

            CostPools costPools = new CostPools();
            costPools.setChannel(channel);
            costPools.setType(type);
            costPools.setYearMonth(yearMonth);
            costPools.setRemark(remark);
            //设置默认值
            costPools.setSiteId(0);
            //TODO: 取当前操作用户ID
            HttpSession session = request.getSession();
            String userName  = Ustring.getString(session.getAttribute("userName"));
            costPools.setMasterName(userName);
            costPools.setBalanceAmount(dec);
            costPools.setEditTime(0);


            if(C200076226!=null&&C200076226.compareTo(dec)>0){
                costPools.setChanYe("C200076226");
                costPools.setAmount(C200076226);
                int row = orderCostPoolsService.addCostPool(costPools);
                if(row<1){
                    sb.append("冰箱,");
                }
            }
            if(C200076227!=null&&C200076227.compareTo(dec)>0){
                costPools.setChanYe("C200076227");
                costPools.setAmount(C200076227);
                int row = orderCostPoolsService.addCostPool(costPools);
                if(row<1){
                    sb.append("冰柜,");
                }
            }
            if(C200076228!=null&&C200076228.compareTo(dec)>0){
                costPools.setChanYe("C200076228");
                costPools.setAmount(C200076228);
                int row = orderCostPoolsService.addCostPool(costPools);
                if(row<1){
                    sb.append("洗衣机,");
                }
            }
            if(C200076229!=null&&C200076229.compareTo(dec)>0){
                costPools.setChanYe("C200076229");
                costPools.setAmount(C200076229);
                int row = orderCostPoolsService.addCostPool(costPools);
                if(row<1){
                    sb.append("家用空调,");
                }
            }
            if(C200076230!=null&&C200076230.compareTo(dec)>0){
                costPools.setChanYe("C200076230");
                costPools.setAmount(C200076230);
                int row = orderCostPoolsService.addCostPool(costPools);
                if(row<1){
                    sb.append("热水器,");
                }
            }
            if(C200076231!=null&&C200076231.compareTo(dec)>0){
                costPools.setChanYe("C200076231");
                costPools.setAmount(C200076231);
                int row = orderCostPoolsService.addCostPool(costPools);
                if(row<1){
                    sb.append("厨电,");
                }
            }
            if(C200076232!=null&&C200076232.compareTo(dec)>0){
                costPools.setChanYe("C200076232");
                costPools.setAmount(C200076232);
                int row = orderCostPoolsService.addCostPool(costPools);
                if(row<1){
                    sb.append("彩电,");
                }
            }
            if(C200076233!=null&&C200076233.compareTo(dec)>0){
                costPools.setChanYe("C200076233");
                costPools.setAmount(C200076233);
                int row = orderCostPoolsService.addCostPool(costPools);
                if(row<1){
                    sb.append("商用空调及其他,");
                }
            }
            if(C20170825!=null&&C20170825.compareTo(dec)>0){
                costPools.setChanYe("C20170825");
                costPools.setAmount(C20170825);
                int row = orderCostPoolsService.addCostPool(costPools);
                if(row<1){
                    sb.append("海尔集团电子商务有限公司(赠品),");
                }
            }

            String text = sb.toString();
            if(text!=null&&text.length()>1){
                json.put("flag",3);
                text = text.substring(0,text.length()-1);
            }

            json.put("text",text+"产业新增费用失败");

        }catch (Exception e){
            logger.error("[CostPoolsController][addCostPool]异常，e:"+e.getMessage());
            json.put("flag",2);
            return json.toString();
        }

        return json.toString();
    }

    /**
     * 跳转到修改费用池页面
     *
     * @return
     */
    @RequestMapping(value = "toUpdateCostPool" ,method = {RequestMethod.GET})
    public String toUpdateCostPool (@RequestParam(required = false)String id, Model modal){

        ServiceResult<CostPools> findCostPoolsById = orderCostPoolsService.findCostPoolsById(id);
        if(findCostPoolsById.getSuccess()){
            CostPools result = findCostPoolsById.getResult();
            modal.addAttribute("CostPools", result);
        }
        return "order/costPoolsEdit";
    }

    /**
     * 修改费用池逻辑
     * @param request
     * @return
     */
    @RequestMapping(value = "updateCostPool" ,method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Integer updateCostPool (CostPools costPools ,HttpServletRequest request){
        try {
            if(costPools.getId()!=null){
                //新增判断批次是否重复逻辑
                String chanYe = costPools.getChanYe();
                if(chanYe==null){
                    return 3;
                }
                System.out.println(chanYe);
                    if("冰箱".equals(chanYe)){
                        chanYe="C200076226";
                    }else if("冷柜".equals(chanYe)){
                        chanYe="C200076227";
                    }else if("洗衣机".equals(chanYe)){
                        chanYe="C200076228";
                    }else if("家用空调".equals(chanYe)){
                        chanYe="C200076229";
                    }else if("热水器".equals(chanYe)){
                        chanYe="C200076230";
                    }else if("厨电".equals(chanYe)){
                        chanYe="C200076231";
                    }else if("彩电".equals(chanYe)){
                        chanYe="C200076232";
                    }else if("商用空调及其他".equals(chanYe)){
                        chanYe="C200076233";
                    }else if("海尔集团电子商务有限公司(赠品)".equals(chanYe)){
                        chanYe="C20170825";
                    }else{
                    return 3;
                    }
                    costPools.setChanYe(chanYe);
                CostPools rtnCostPools = orderCostPoolsService.findcostPoolsByTYBC(costPools);
                if(rtnCostPools!=null&&!rtnCostPools.getId().equals(costPools.getId())){
                    return 4;
                }
                //结束
                HttpSession session = request.getSession();
                String userName  = Ustring.getString(session.getAttribute("userName"));
                costPools.setMasterName(userName);
                Boolean flag = orderCostPoolsService.updateCostPools(costPools);
                if(flag){
                    return 1;
                }
            }
        return 0;
        } catch (Exception e) {
            logger.error("[CostPoolsController][CostPoolsEditOrAdd]异常，e:"+e.getMessage());
            return 2;
        }
    }

    /**
     * 删除
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteCostPool" ,method = {RequestMethod.GET})
    @ResponseBody
    public Integer deleteCostPool (@RequestParam(required = false)String id,HttpServletRequest request){
        try {
            if(id!=null&&id.trim()!=""){
                Boolean flag = orderCostPoolsService.deleteCostPools(id);
                if(flag){
                    return 1;
                }
            }
            return 0;
        } catch (Exception e) {
            logger.error("[CostPoolsController][CostPoolsEditOrAdd]异常，e:"+e.getMessage());
            return 2;
        }
    }



    /**
     * 导出费用池
     * @param request
     * @return
     */
    @RequestMapping(value = "exportCostPools" ,method = {RequestMethod.GET})
    public void exportCostPools (
            @RequestParam(required = false) String yearMonth,
            @RequestParam(required = false) String batch,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String channel,
            @RequestParam(required = false) String chanYe,
            HttpServletRequest request,HttpServletResponse response){

        Map<String, Object> paramMap = new HashMap<String, Object>();
        response.setCharacterEncoding("UTF-8");
        //参数加入params里
        paramMap.put("yearMonth", yearMonth);
        paramMap.put("batch", batch);
        paramMap.put("type", type);
        paramMap.put("channel", channel);
        paramMap.put("chanYe", chanYe);

        //获取开单列表List
        final List<Map<String, Object>> result = orderCostPoolsService.getExportCostPoolsList(paramMap);
        String fileName = "渠道费用列表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";
        String[] sheetHead = new String[] { "序号", "渠道", "产业", "费用类型", "年月", "批次",
                "总费用", "已使用"};
        try {
            ExcelExportUtil.exportEntity(request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {

                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp)
                                throws Exception {
                            setExcelBodyTotalForCostPools(sheet, temp, result);
                        }

                    });
        } catch (Exception e) {
            logger.error("渠道费用导出失败", e);
            e.printStackTrace();
        }
    }


    /**
     * 导出费用池日志
     * @param request
     * @return
     */
    @RequestMapping(value = "exportCostPoolsUsedLogs" ,method = {RequestMethod.GET})
    public void exportCostPoolsUsedLogs (
            @RequestParam(required = false) String orderSn,
            @RequestParam(required = false) String cOrderSn,
            @RequestParam(required = false) String addTimeMin,
            @RequestParam(required = false) String addTimeMax,
            @RequestParam(required = false) String channel,
            @RequestParam(required = false) String chanYe,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String usedType,
            @RequestParam(required = false) String source,
            HttpServletRequest request, HttpServletResponse response){

        Map<String, Object> paramMap = new HashMap<String, Object>();
        response.setCharacterEncoding("UTF-8");
        paramMap.put("m", 0);
        paramMap.put("n", 5000);

        //参数加入params里
        paramMap.put("orderSn", orderSn);
        paramMap.put("cOrderSn", cOrderSn);
        paramMap.put("addTimeMin", addTimeMin);
        paramMap.put("addTimeMax", addTimeMax);
        paramMap.put("channel", channel);
        paramMap.put("type", type);
        paramMap.put("chanYe", chanYe);
        paramMap.put("usedType", usedType);
        paramMap.put("source", source);

        //获取开单列表List
        final List<Map<String, Object>> result = orderCostPoolsService.getExportCostPoolsUsedLogsList(paramMap);
        String fileName = "费用池使用日志列表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";
        String[] sheetHead =  new String[] { "序号", "订单号", "关联订单号", "网单号", "订单来源",
                "渠道", "产业", "费用类型", "费用使用", "发生金额", "年月",
                "发生时间" };


        try {
            ExcelExportUtil.exportEntity(request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {

                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp)
                                throws Exception {
                            setExcelBodyTotalForUsedLogs(sheet, temp, result);
                        }

                    });
        } catch (Exception e) {
            logger.error("费用操作日志导出失败", e);
            e.printStackTrace();
        }

    }







    /**
     * 导出费用池具体数据，实现回调函数
     * @param sheet
     * @param temp 行号
     * @param list 传入需要导出的 list
     */
    private void setExcelBodyTotalForCostPools(WritableSheet sheet, int temp,
                                                      List<Map<String, Object>> list)
            throws Exception {

        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setAlignment(jxl.format.Alignment.RIGHT);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int index = 0;
        for (Map<String, Object> map : list) {
            index++;
            //jxl.write.Number(列号,行号 ,内容 )

            //序号
            sheet.setColumnView(0, 5);
            sheet.addCell(new Label(0, temp, CommUtil.getStringValue(index), textFormat));
            //渠道
            String channel = CommUtil.getStringValue(map.get("channel"));
            if(channel.equals("1")){
                channel="商城";
            }else if(channel.equals("2")){
                channel="天猫";
            }else if(channel.equals("3")){
                channel="电商平台";
            }else if(channel.equals("4")){
                channel="微店";
            }else{
                channel="";
            }
            sheet.setColumnView(1, 15);
            sheet.addCell(new Label(1, temp, channel, textFormat));

            //产业
            String chanYe = CommUtil.getStringValue(map.get("chanYe"));
            if(chanYe.equals("C200076226")){
                chanYe="冰箱";
            }else if(chanYe.equals("C200076227")){
                chanYe="冷柜";
            }else if(chanYe.equals("C200076228")){
                chanYe="洗衣机";
            }else if(chanYe.equals("C200076229")){
                chanYe="家用空调";
            }else if(chanYe.equals("C200076230")){
                chanYe="热水器";
            }else if(chanYe.equals("C200076231")){
                chanYe="厨电";
            }else if(chanYe.equals("C200076232")){
                chanYe="彩电";
            }else if(chanYe.equals("C200076233")){
                chanYe="商用空调及其他";
            }else if(chanYe.equals("C20170825")){
                chanYe="海尔集团电子商务有限公司(赠品)";
            }else{
                chanYe="";
            }
            sheet.setColumnView(2, 15);
            sheet.addCell(new Label(2, temp, chanYe, textFormat));

            //费用类型
            String type = CommUtil.getStringValue(map.get("type"));
            if(type.equals("0")){
                type="赠品机";
            }else if(type.equals("1")){
                type="优惠券";
            }else {
                type="";
            }
            sheet.setColumnView(3, 15);
            sheet.addCell(new Label(3, temp, type, textFormat));

            //年月
            sheet.setColumnView(4, 15);
            sheet.addCell(new Label(4, temp, CommUtil.getStringValue(map.get("yearMonth")), textFormat));

            //批次
            sheet.setColumnView(5, 15);
            sheet.addCell(new Label(5, temp, CommUtil.getStringValue(map.get("batch")), textFormat));

            //总费用
            sheet.setColumnView(6, 15);
            sheet.addCell(new Label(6, temp, CommUtil.getStringValue(map.get("amount")), textFormat));

            //已使用
            sheet.setColumnView(7, 15);
            sheet.addCell(new Label(7, temp, CommUtil.getStringValue(map.get("balanceAmount")), textFormat));

            temp++;
        }
    }

    /**
     * 导出日志具体数据，实现回调函数
     * @param sheet
     * @param temp 行号
     * @param list 传入需要导出的 list
     */
    private void setExcelBodyTotalForUsedLogs(WritableSheet sheet, int temp,
                                               List<Map<String, Object>> list)
            throws Exception {

        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setAlignment(jxl.format.Alignment.RIGHT);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int index = 0;
        for (Map<String, Object> map : list) {
            index++;
            String[] sheetHead = new String[] { "序号", "订单号", "关联订单号", "网单号", "订单来源",
                    "渠道", "产业", "费用类型", "费用使用", "发生金额", "年月",
                    "发生时间" };
            //序号
            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, CommUtil.getStringValue(index), textFormat));
            //订单号
            sheet.setColumnView(1, 15);
            sheet.addCell(new Label(1, temp, CommUtil.getStringValue(map.get("orderSn")), textFormat));
            //关联订单号
            String relationOrderSn = CommUtil.getStringValue(map.get("relationOrderSn"));
            if("".equalsIgnoreCase(relationOrderSn)||"新单".equals(relationOrderSn)){
                relationOrderSn="";
            }
            sheet.setColumnView(2, 15);
            sheet.addCell(new Label(2, temp, relationOrderSn, textFormat));
            //网单号
            sheet.setColumnView(3, 15);
            sheet.addCell(new Label(3, temp, CommUtil.getStringValue(map.get("cOrderSn")), textFormat));
            //订单来源
            String source = CommUtil.getStringValue(map.get("source"));
            //国美
            if(source.equals("GMZX")){
                source="国美海尔官方旗舰店";
            }else if(source.equals("GMZXTS")){
                source="国美统帅官方旗舰店";
                //苏宁
            }else if(source.equals("SNYG")){
                source="苏宁统帅官方旗舰店";
            }else if(source.equals("SNHEGQ")){
                source="苏宁海尔官方旗舰店";
            }else if(source.equals("SNQDZX")){
                source="苏宁渠道中心";
                //当当
            }else if(source.equals("DDW")){
                source="海尔当当旗舰店";
                //京东
            }else if(source.equals("JDHEGQ")){
                source="京东海尔官方旗舰店";
            }else if(source.equals("JDHEBXGQ")){
                source="京东海尔集团冰箱官方旗舰店";
                //天猫
            }else if(source.equals("TBSC")){
                source="海尔官方淘宝旗舰店";
            }else if(source.equals("TBXCR")){
                source="天猫小超人旗舰店";
            }else if(source.equals("TBCFDD")){
                source="海尔厨房大电旗舰店";
            }else if(source.equals("TBZYKT")){
                source="淘宝中央空调";
            }else if(source.equals("TOPBOILER")){
                source="海尔热水器专卖店";
            }else if(source.equals("TONGSHUAI")){
                source="淘宝网统帅日日顺乐家专卖店";
            }else if(source.equals("TOPFENXIAO")){
                source="海尔官方旗舰店(分销平台)";
            }else if(source.equals("TMMK")){
                source="mooka模卡官方旗舰店";
            }else if(source.equals("TMTV")){
                source="海尔电视旗舰店";
            }else if(source.equals("TMKSD")){
                source="天猫卡萨帝旗舰店";
            }else if(source.equals("FRIDGE")){
                source="海尔冰冷旗舰店";
            }else if(source.equals("AIR")){
                source="海尔空调旗舰店";
            }else if(source.equals("GQGYS")){
                source="海尔官方旗舰店供应商";
            }else if(source.equals("XSST")){
                source="淘宝WA线上生态授权店";
            }else if(source.equals("WASHER")){
                source="海尔洗衣机旗舰店";
            }else{
                source=source;
            }
            sheet.setColumnView(4, 15);
            sheet.addCell(new Label(4, temp, source, textFormat));
            //渠道
            String channel = CommUtil.getStringValue(map.get("channel"));
            if(channel.equals("1")){
                channel="商城";
            }else if(channel.equals("2")){
                channel="天猫";
            }else if(channel.equals("3")){
                channel="电商平台";
            }else if(channel.equals("4")){
                channel="微店";
            }else{
                channel="";
            }
            sheet.setColumnView(5, 15);
            sheet.addCell(new Label(5, temp, channel, textFormat));
            //产业
            String chanYe = CommUtil.getStringValue(map.get("chanYe"));
            if(chanYe.equals("C200076226")){
                chanYe="冰箱";
            }else if(chanYe.equals("C200076227")){
                chanYe="冷柜";
            }else if(chanYe.equals("C200076228")){
                chanYe="洗衣机";
            }else if(chanYe.equals("C200076229")){
                chanYe="家用空调";
            }else if(chanYe.equals("C200076230")){
                chanYe="热水器";
            }else if(chanYe.equals("C200076231")){
                chanYe="厨电";
            }else if(chanYe.equals("C200076232")){
                chanYe="彩电";
            }else if(chanYe.equals("C200076233")){
                chanYe="商用空调及其他";
            }else if(chanYe.equals("C20170825")){
                chanYe="海尔集团电子商务有限公司(赠品)";
            }else{
                chanYe="";
            }
            sheet.setColumnView(6, 15);
            sheet.addCell(new Label(6, temp, chanYe, textFormat));
            //费用类型
            String type = CommUtil.getStringValue(map.get("type"));
            if(type.equals("0")){
                type="赠品机";
            }else if(type.equals("1")){
                type="优惠券";
            }else {
                type="";
            }
            sheet.setColumnView(7, 30);
            sheet.addCell(new Label(7, temp, type, textFormat));
            //费用使用
            String usedType = CommUtil.getStringValue(map.get("usedType"));
            if(usedType.equals("1")){
                usedType="占用费用";
            }else if(usedType.equals("2")){
                usedType="释放费用";
            }else if(usedType.equals("3")){
                usedType="借调费用";
            }else {
                usedType="";
            }
            sheet.setColumnView(8, 15);
            sheet.addCell(new Label(8, temp,usedType, textFormat));
            //发生金额
            sheet.setColumnView(9, 15);
            sheet.addCell(new Label(9, temp, CommUtil.getStringValue(map.get("amount")), textFormat));
            //年月
            sheet.setColumnView(10, 15);
            sheet.addCell(new Label(10, temp, CommUtil.getStringValue(map.get("yearMonth")), textFormat));
            //发生时间
            sheet.setColumnView(11, 15);
            sheet.addCell(new Label(11, temp, CommUtil.getStringValue(map.get("addTime")), textFormat));

            temp++;
        }
    }



}
