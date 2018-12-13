package com.haier.svc.api.controller.stock;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.PopInvWarehouse;
import com.haier.stock.service.StockCenterInvWareHouseService;
import com.haier.svc.api.controller.util.ExcelExportUtil;
import com.haier.svc.api.controller.util.ExcelReader;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.service.stock.WareHouseModel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.jsondoc.core.annotation.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 胡万来 on 2018/1/19 0019.
 */

@Controller
@Api(name = "基本库存信息", description = "invWareHouseController")
@RequestMapping(value = "invwarehouse/")
public class InvWareHouseController {

    @Autowired
    StockCenterInvWareHouseService stockCenterInvWareHouseService;
    @Autowired
    private WareHouseModel wareHouseModel;

    private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager
            .getLogger(InvWareHouseController.class);

    // t+2填报导入模板表头信息
    private static final String CHECKSTR = "库位编码,LES库位,库位名称,状态,仓库TC代码,库位属性,渠道编码,批次,销售组织编码,付款方编码,地方编码,分渠道编码,物流OE码";

    private static final String CHECKSTR1 = "仓库TC代码,仓库名称,状态,网单中心代码,是否支持货到付款,该TC支持的物流模式,LES送达方代码,LES库位编码,LES接收人," +
            "日日顺配送中心编码,CRM地区编码,电商付款方,工贸代码,销售组织编码,电商库位码,电商库位名称,送达方代码,送达方名称,0E码LES,管理客户编码,管理客户名称," +
            "产业工贸代码,产业工贸描述,颗粒度编码,网单经营体编码,电商售达方编码,电商售达方名称,销售组织编码,分支,电商付款方编码,电商付款方名称,地区编码CRM专用," +
            "日日顺配送编码,日日顺配送名称,日日顺售达方,日日顺售达方名称,OMS重庆新日日顺付款方,OMS重庆新日日顺付款方名称,客户编码,客户编码名称";



    /**
     * 条件查询虚拟库位
     *
     * @param page
     * @param rows
     * @param condition
     * @return
     */
    @RequestMapping("/findInvSectionList")
    @ResponseBody
    public JSONObject findInvSectionList(int page, int rows, InvSection condition) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        JSONObject jsonObject=stockCenterInvWareHouseService.invSectionList(pager, condition);
        return jsonObject;
    }


    /**
     * 跳转页面
     *
     * @param modal
     * @return
     */
    @RequestMapping(value = {"/invSection"}, method = {RequestMethod.GET})
    public String invSectionIndex(Model modal) {
        List<InvStockChannel> tChannelsInfo = stockCenterInvWareHouseService.getChannelIdList();
        modal.addAttribute("cidList", tChannelsInfo);
        return "stock/invwarehouse/invSectionList";
    }



    /**
     * 添加虚拟库位
     *
     * @param condition
     * @return
     */
    @RequestMapping("/addInvSection")
    @ResponseBody
    public String addInvSection(InvSection condition, HttpServletRequest request) {
        condition.setUpdateTime(new Date());
        condition.setCreateUser(String.valueOf(request.getSession().getAttribute("loginId")));
        condition.setUpdateUser(String.valueOf(request.getSession().getAttribute("loginId")));
        condition.setCreateTime(new Date());

        return stockCenterInvWareHouseService.addInvSection(condition);
    }

    /**
     * 删除虚拟库位
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/removeInvSection", method = RequestMethod.POST)
    @ResponseBody
    public String removeInvSection(String id) {
        JSONObject json = new JSONObject();
        int flag = stockCenterInvWareHouseService.removeInvSection(id);
        if (1 == flag) {
            json.put("text", "success");
        } else {
            json.put("text", "fail");
        }
        return json.toString();
    }

    /**
     * 虚拟仓库跳转到导入页
     *
     * @param modal
     * @return
     */
    @RequestMapping(value = {"/importInvSection"}, method = {RequestMethod.GET})
    public String importInvSection(Model modal) {
        return "stock/invwarehouse/importInvSection";
    }

    /**
     * 库位基本跳转到导入页
     *
     * @param modal
     * @return
     */
    @RequestMapping(value = {"/importInvWarehouse"}, method = {RequestMethod.GET})
    public String importInvWarehouse(Model modal) {
        return "stock/invwarehouse/importInvWarehouse";
    }

    /**
     * 跳转仓库
     *
     * @param modal
     * @return
     */
    @RequestMapping(value = {"/invWarehouse"}, method = {RequestMethod.GET})
    public String invWarehouseIndex(Model modal) {
        return "stock/invwarehouse/invWarehouseList";
    }

    /**
     * 条件查询基本库位
     *
     * @param page
     * @param rows
     * @param condition
     * @return
     */
    @RequestMapping("/findInvWareHouseList")
    @ResponseBody
    public JSONObject findInvWareHouseList(int page, int rows, PopInvWarehouse condition) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        return stockCenterInvWareHouseService.invWarehouseList(pager, condition);
    }

    /**
     * 删除基本库位
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/removeInvWarehouse", method = RequestMethod.POST)
    @ResponseBody
    public String removeInvWarehouse(String id) {
        JSONObject json = new JSONObject();
        int flag = stockCenterInvWareHouseService.removeInvWarehouse(id);
        if (1 == flag) {
            json.put("text", "success");
        } else {
            json.put("text", "fail");
        }
        return json.toString();
    }


    /**
     * 添加基本库位
     *
     * @param condition
     * @return
     */
    @RequestMapping("/addinvWarehouse")
    @ResponseBody
    public String addinvWarehouse(PopInvWarehouse condition, HttpServletRequest request) {
        if ("insert".equals(condition.getId())) {
            condition.setCreateUser(String.valueOf(request.getSession().getAttribute("loginId")));
            condition.setUpdateUser(String.valueOf(request.getSession().getAttribute("loginId")));
        } else {
            condition.setUpdateUser(String.valueOf(request.getSession().getAttribute("loginId")));
        }
        return stockCenterInvWareHouseService.addInvWarehouse(condition);
    }

    /**
     * 导入虚拟库位
     *
     * @param request
     * @param response
     * @param report_year_week
     * @param modelMap
     * @return
     */
    @RequestMapping(value = {"/importInvSection"}, method = {RequestMethod.POST})
    public @ResponseBody
    HttpJsonResult<Map<String, Object>> importInvSectionData(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(required = false) String report_year_week,
            Map<String, Object> modelMap) {

        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        // 转型为MultipartHttpRequest
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        long totalStartTime = System.currentTimeMillis();
        List<InvSection> invSections = new ArrayList<InvSection>();

        MultipartFile file = multipartRequest.getFile("file");
        if (file == null) {
            result.setMessage("没有选择导入文件，请选择导入文件后再点击导入操作！");
            return result;
        }
        // 警告信息集合
        String MsgList = "";
        StringBuffer sb = new StringBuffer();

        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        String fileExtName = fileName.substring(index + 1);
        if (!fileExtName.equalsIgnoreCase("xls")) {
            result.setMessage("选择导入文件扩展名必须为xls!");
            return result;
        }
        int count = 0;
        int nullRow = 0;

        // 判断用 渠道code
        String channelCode = "";
        try {
            List<String[]> list = ExcelReader.readExcel(file.getInputStream(),
                    1);
            if (list.size() <= 1) {
                result.setMessage("很抱歉！你导入的Excel没有数据记录，请查看重新整理导入！");
                return result;
            }
            // 验证模板表头信息
            String[] firstLineData = list.get(0);

            boolean flag = this.checkDataTemplate(firstLineData, CHECKSTR);

            if (!flag) {
                result.setMessage("很抱歉！你导入的Excel数据格式与系统模板格式存在差异，请下载模板后重新导入！");
                return result;
            }

            // 渠道数据存入HashMap
            Map<String, String> invstockchannelmap = new HashMap<String, String>();
            // 调用Service，取得渠道数据
            invstockchannelmap = stockCenterInvWareHouseService.getChannelMap(invstockchannelmap);
            //判断Excel中主键是否重复
            String temp = "";
            for (int i = 0; i < list.size() - 1; i++) {
                String[] str = list.get(i);
                temp = StringUtil.nullSafeString(str[0]).trim();
                for (int j = i + 1; j < list.size(); j++) {
                    String[] str1 = list.get(j);
                    if (temp.equals(StringUtil.nullSafeString(str1[0]).trim())) {
                        result.setMessage("很抱歉！你导入的Excel数据中第" + (i + 1) + "行跟第" + (j + 1) + "行仓库(TC)编码重复，值是：" + temp);
                        return result;
                    }
                }
            }

            // 读取数据
            for (int i = 1; i < list.size(); i++) {

                String[] str = list.get(i);
                // 仓库(TC)编码
                String secCode = StringUtil.nullSafeString(str[0]).trim();
                // LES库位
                String lesSecCode = StringUtil.nullSafeString(str[1]).trim();
                // 库位名称
                String secName = StringUtil.nullSafeString(str[2]).trim();
                // 状态
                String status = StringUtil.nullSafeString(str[3]).trim();
                // 仓库（TC）代码
                String whCode = StringUtil.nullSafeString(str[4].trim());
                // 库位属性
                String sectionProperty = StringUtil.nullSafeString(str[5].trim());
                // 渠道编码
                String channel_Code = StringUtil.nullSafeString(str[6].trim());
                // 批次
                String itemProperty = StringUtil.nullSafeString(str[7].trim());
                // 销售组织编码
                String corpCode = StringUtil.nullSafeString(str[8].trim());
                // 付款方编码
                String custCode = StringUtil.nullSafeString(str[9].trim());
                // 地区编码
                String regionCode = StringUtil.nullSafeString(str[10].trim());
                // 分渠道送达方
                String ehaierDeliverCode = StringUtil.nullSafeString(str[11].trim());
                // 物流OE码
                String les0eCode = StringUtil.nullSafeString(str[12].trim());

                boolean isAllNull = StringUtil.isEmpty(secCode, true)
                        && StringUtil.isEmpty(lesSecCode, true)
                        && StringUtil.isEmpty(secName, true)
                        && StringUtil.isEmpty(status, true)
                        && StringUtil.isEmpty(whCode, true)
                        && StringUtil.isEmpty(sectionProperty, true)
                        && StringUtil.isEmpty(channel_Code, true)
                        && StringUtil.isEmpty(itemProperty, true)
                        && StringUtil.isEmpty(corpCode, true)
                        && StringUtil.isEmpty(custCode, true)
                        && StringUtil.isEmpty(regionCode, true)
                        && StringUtil.isEmpty(ehaierDeliverCode, true)
                        && StringUtil.isEmpty(les0eCode, true);
                if (isAllNull) {
                    nullRow++;
                    continue;
                }
                // 导入模板内容的非空判断****************START*******************************
                if (StringUtil.isEmpty(secCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【库位编码】不能为空! 请核查后重新导入！";

                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(lesSecCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【LES库位】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(secName, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【库位名称】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(status, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【状态】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(whCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【仓库（TC）代码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(sectionProperty, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【库位属性】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(channel_Code, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【渠道编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(corpCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【销售组织编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(custCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【付款方编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(regionCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【地区编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(ehaierDeliverCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【分渠道送达方】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                // 导入模板内容的非空判断****************END*********************************

                // 导入模板内容的字符长度校验**************START**********************
                if (secCode.length() > 10) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【库位编码】字符长度不应大于10! 请核查后重新导入！";

                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (lesSecCode.length() > 10) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【LES库位】字符长度不应大于10! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (secName.length() > 30) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【库位名称】字符长度不应大于30! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (whCode.length() > 2) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【仓库（TC）代码】字符长度不应大于2! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (sectionProperty.length() > 5) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【库位属性】字符长度不应大于5! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (channel_Code.length() > 10) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【渠道编码】字符长度不应大于10! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (corpCode.length() > 10) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【销售组织编码】字符长度不应大于10! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (custCode.length() > 16) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【付款方编码】字符长度不应大于16! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (regionCode.length() > 10) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【地区编码】字符长度不应大于10! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (ehaierDeliverCode.length() > 30) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【分渠道送达方】字符长度不应大于30! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (les0eCode.length() > 30) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【物流OE码】字符长度不应大于30! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (itemProperty.length() > 2) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【批次】字符长度不应大于2! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                // 导入模板内容的字符长度校验**************END**********************

                // 导入模板内容的合理性判断及权限判断**************START**********************
                // 用渠道名称取得渠道code及渠道的正确性判断
                channelCode = invstockchannelmap.get(channel_Code);
                // 渠道的正确性判断
                if ("".equals(channelCode) || channelCode == null) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的渠道【"
                            + channel_Code + "】无法识别！请检查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                //判断主键唯一
                long checkId = stockCenterInvWareHouseService.checkInvSectionByCode(secCode);
                if (checkId > 0) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的库位编码【"
                            + secCode + "】已存在！请检查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                InvSection invSection = new InvSection();

                invSection.setWhCode(whCode);
                //状态dataStatus
                if (  "已启用".equals(status)) {
                	
                	status = "1";
                } else if ( "未启用".equals(status)) {
                	 status = "0";
                }
                invSection.setStatus(Integer.parseInt(status));
                invSection.setSectionProperty(sectionProperty);
                invSection.setSecName(secName);
                invSection.setSecCode(secCode);
                invSection.setRegionCode(regionCode);
                invSection.setLesSecCode(lesSecCode);
                invSection.setLes0eCode(les0eCode);
                invSection.setItemProperty(itemProperty);
                invSection.setEhaierDeliverCode(ehaierDeliverCode);
                invSection.setCustCode(custCode);
                invSection.setCorpCode(corpCode);
                invSection.setChannelCode(channel_Code);
                invSection.setCreateTime(new Date());
                invSection.setCreateUser("系统");
                invSection.setUpdateUser("");
                invSections.add(invSection);
            }

            long startTime = System.currentTimeMillis();
            ServiceResult<Map<String, Integer>> insResult = wareHouseModel.insertInvSections(invSections);
            long endTime = System.currentTimeMillis();
            long totalEndTime = System.currentTimeMillis();

            int success = 0;
            if (insResult.getResult() != null) {
                success = insResult.getResult().get("success");
            }
            modelMap.put("total", list.size() - 1 - nullRow);
            modelMap.put("ignore", list.size() - success - 1 - nullRow);
            modelMap.put("success", success);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("文件导入数据库失败", e);
            result.setMessage("很抱歉！你导入的Excel数据导入失败，请联系技术负责人！");
            return result;
        }

        modelMap.put("warn", sb.toString());
        result.setData(modelMap);
        return result;
    }

    /**
     * 导入基本库位
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = {"/importInvWarehouse"}, method = {RequestMethod.POST})
    public @ResponseBody
    HttpJsonResult<Map<String, Object>> importInvWarehouse(
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> modelMap) {

        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        // 转型为MultipartHttpRequest
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        long totalStartTime = System.currentTimeMillis();
        List<PopInvWarehouse> invWarehouses = new ArrayList<PopInvWarehouse>();

        MultipartFile file = multipartRequest.getFile("file");
        if (file == null) {
            result.setMessage("没有选择导入文件，请选择导入文件后再点击导入操作！");
            return result;
        }
        // 警告信息集合
        String MsgList = "";
        StringBuffer sb = new StringBuffer();

        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        String fileExtName = fileName.substring(index + 1);
        if (!fileExtName.equalsIgnoreCase("xls")) {
            result.setMessage("选择导入文件扩展名必须为xls!");
            return result;
        }
        int count = 0;
        int nullRow = 0;

        try {
            List<String[]> list = ExcelReader.readExcel(file.getInputStream(),
                    1);
            if (list.size() <= 1) {
                result.setMessage("很抱歉！你导入的Excel没有数据记录，请查看重新整理导入！");
                return result;
            }
            // 验证模板表头信息
            String[] firstLineData = list.get(0);

            boolean flag = this.checkDataTemplate(firstLineData, CHECKSTR1);

            if (!flag) {
                result.setMessage("很抱歉！你导入的Excel数据格式与系统模板格式存在差异，请下载模板后重新导入！");
                return result;
            }

            //判断Excel中主键Code是否重复
            String temp = "";
            for (int i = 0; i < list.size() - 1; i++) {
                String[] str = list.get(i);
                temp = StringUtil.nullSafeString(str[0]).trim();
                for (int j = i + 1; j < list.size(); j++) {
                    String[] str1 = list.get(j);
                    if (temp.equals(StringUtil.nullSafeString(str1[0]).trim())) {
                        result.setMessage("很抱歉！你导入的Excel数据中第" + (i + 1) + "行跟第" + (j + 1) + "行仓库(TC)编码重复，值是：" + temp);
                        return result;
                    }
                }
            }
            //判断Excel中主键Name是否重复
            for (int i = 0; i < list.size() - 1; i++) {
                String[] str = list.get(i);
                temp = StringUtil.nullSafeString(str[1]).trim();
                for (int j = i + 1; j < list.size(); j++) {
                    String[] str1 = list.get(j);
                    if (temp.equals(StringUtil.nullSafeString(str1[1]).trim())) {
                        result.setMessage("很抱歉！你导入的Excel数据中第" + (i + 1) + "行跟第" + (j + 1) + "行仓库名称重复，值是：" + temp);
                        return result;
                    }
                }
            }

            // 读取数据
            for (int i = 1; i < list.size(); i++) {

                String[] str = list.get(i);
                // 仓库(TC)编码
                String whCode = StringUtil.nullSafeString(str[0]).trim();
                String whName = StringUtil.nullSafeString(str[1]).trim();
                String status = StringUtil.nullSafeString(str[2]).trim();
                String centerCode = StringUtil.nullSafeString(str[3]).trim();
                String supportCod = StringUtil.nullSafeString(str[4]).trim();
                String supportedDeliveryMode = StringUtil.nullSafeString(str[5]).trim();
                String lesFiveYard = StringUtil.nullSafeString(str[6]).trim();
                String lesWhCode = StringUtil.nullSafeString(str[7]).trim();
                String lesAccepter = StringUtil.nullSafeString(str[8]).trim();
                String rrsDeliverCode = StringUtil.nullSafeString(str[9]).trim();
                String crmAreaCode = StringUtil.nullSafeString(str[10]).trim();
                String ehaierPayer = StringUtil.nullSafeString(str[11]).trim();
                String itcCode = StringUtil.nullSafeString(str[12]).trim();
                String moCode = StringUtil.nullSafeString(str[13]).trim();
                String estorgeId = StringUtil.nullSafeString(str[14]).trim();
                String estorgeName = StringUtil.nullSafeString(str[15]).trim();
                String transmitId = StringUtil.nullSafeString(str[16]).trim();
                String transmitDesc = StringUtil.nullSafeString(str[17]).trim();
                String lesOeId = StringUtil.nullSafeString(str[18]).trim();
                String customId = StringUtil.nullSafeString(str[19]).trim();
                String customDesc = StringUtil.nullSafeString(str[20]).trim();
                String industryTradeId = StringUtil.nullSafeString(str[21]).trim();
                String industryTradeDesc = StringUtil.nullSafeString(str[22]).trim();
                String graininessId = StringUtil.nullSafeString(str[23]).trim();
                String netManagementId = StringUtil.nullSafeString(str[24]).trim();
                String esaleId = StringUtil.nullSafeString(str[25]).trim();
                String esaleName = StringUtil.nullSafeString(str[26]).trim();
                String saleOrgId = StringUtil.nullSafeString(str[27]).trim();
                String branch = StringUtil.nullSafeString(str[28]).trim();
                String paymentId = StringUtil.nullSafeString(str[29]).trim();
                String paymentName = StringUtil.nullSafeString(str[30]).trim();
                String areaId = StringUtil.nullSafeString(str[31]).trim();
                String rrsDistributionId = StringUtil.nullSafeString(str[32]).trim();
                String rrsDistributionName = StringUtil.nullSafeString(str[33]).trim();
                String rrsSaleId = StringUtil.nullSafeString(str[34]).trim();
                String rrsSaleName = StringUtil.nullSafeString(str[35]).trim();
                String omsRrsPaymentId = StringUtil.nullSafeString(str[36]).trim();
                String omsRrsPaymentName = StringUtil.nullSafeString(str[37]).trim();
                String sapCustomerCode = StringUtil.nullSafeString(str[38]).trim();
                String sapCustomerName = StringUtil.nullSafeString(str[39]).trim();

                boolean isAllNull = StringUtil.isEmpty(whCode, true)
                        && StringUtil.isEmpty(whName, true)
                        && StringUtil.isEmpty(status, true)
                        && StringUtil.isEmpty(centerCode, true)
                        && StringUtil.isEmpty(supportCod, true)
                        && StringUtil.isEmpty(supportedDeliveryMode, true)
                        && StringUtil.isEmpty(lesFiveYard, true)
                        && StringUtil.isEmpty(lesWhCode, true)
                        && StringUtil.isEmpty(lesAccepter, true)
                        && StringUtil.isEmpty(rrsDeliverCode, true)
                        && StringUtil.isEmpty(crmAreaCode, true)
                        && StringUtil.isEmpty(ehaierPayer, true)
                        && StringUtil.isEmpty(itcCode, true)
                        && StringUtil.isEmpty(moCode, true)
                        && StringUtil.isEmpty(estorgeId, true)
                        && StringUtil.isEmpty(estorgeName, true)
                        && StringUtil.isEmpty(transmitId, true)
                        && StringUtil.isEmpty(transmitDesc, true)
                        && StringUtil.isEmpty(lesOeId, true)
                        && StringUtil.isEmpty(customId, true)
                        && StringUtil.isEmpty(customDesc, true)
                        && StringUtil.isEmpty(industryTradeId, true)
                        && StringUtil.isEmpty(industryTradeDesc, true)
                        && StringUtil.isEmpty(graininessId, true)
                        && StringUtil.isEmpty(netManagementId, true)
                        && StringUtil.isEmpty(esaleId, true)
                        && StringUtil.isEmpty(esaleName, true)
                        && StringUtil.isEmpty(saleOrgId, true)
                        && StringUtil.isEmpty(branch, true)
                        && StringUtil.isEmpty(paymentId, true)
                        && StringUtil.isEmpty(paymentName, true)
                        && StringUtil.isEmpty(areaId, true)
                        && StringUtil.isEmpty(rrsDistributionId, true)
                        && StringUtil.isEmpty(rrsDistributionName, true)
                        && StringUtil.isEmpty(rrsSaleId, true)
                        && StringUtil.isEmpty(rrsSaleName, true)
                        && StringUtil.isEmpty(omsRrsPaymentId, true)
                        && StringUtil.isEmpty(omsRrsPaymentName, true)
                        && StringUtil.isEmpty(sapCustomerCode, true)
                        && StringUtil.isEmpty(sapCustomerName, true);

                if (isAllNull) {
                    nullRow++;
                    continue;
                }
                // 导入模板内容的非空判断****************START*******************************
                if (StringUtil.isEmpty(whCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【仓库（TC）代码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(whName, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【仓库名称】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(status, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【状态】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(centerCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【网单中心代码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(supportCod, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【是否支持货到付款】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(supportedDeliveryMode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【该TC支持的物流模式】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(lesFiveYard, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【LES送达方代码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(lesWhCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【LES库位编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(lesAccepter, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【LES接收方】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(rrsDeliverCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【日日顺配送中心编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(crmAreaCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【CRM地区编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(ehaierPayer, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商付款方】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (StringUtil.isEmpty(itcCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【工贸代码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(moCode, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【销售组织编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(estorgeId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商库位码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(estorgeName, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商库位名称】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(transmitId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【送达方代码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(transmitDesc, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【送达方名称】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(lesOeId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【0E码（LES）】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(customId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【管理客户编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(customDesc, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【管理客户名称】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(industryTradeId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【产业工贸代码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(industryTradeDesc, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【产业工贸描述】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(graininessId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【颗粒度编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(netManagementId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【网单经营体编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(esaleId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商售达方编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(esaleName, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商售达方名称】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(saleOrgId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【销售组织编码1】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(paymentId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商付款方编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(paymentName, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商付款方名称】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(areaId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【地区编码（CRM专用）】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(rrsDistributionId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【日日顺配送编码】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(rrsDistributionName, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【日日顺配送名称】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(rrsSaleId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【日日顺售达方】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(rrsSaleName, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【日日顺售达方名称】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(omsRrsPaymentId, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【OMS重庆新日日顺付款方】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (StringUtil.isEmpty(omsRrsPaymentName, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【OMS重庆新日日顺付款方名称】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                // 导入模板内容的非空判断****************END*********************************

                //导入模板内容的字符长度判断***************START****************************
                if (whCode.length() > 2) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【仓库（TC）代码】字符长度不应大于2! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (whName.length() > 10) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【仓库名称】字符长度不应大于10! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (status.length() > 11) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【状态】字符长度不应大于11! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (centerCode.length() > 10) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【网单中心代码】字符长度不应大于10! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (supportCod.length() > 4) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【是否支持货到付款】字符长度不应大于4! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (supportedDeliveryMode.length() > 10) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【该TC支持的物流模式】字符长度不应大于10! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (lesFiveYard.length() > 10) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【LES送达方代码】字符长度不应大于10! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (lesWhCode.length() > 10) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【LES库位编码】字符长度不应大于10! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (lesAccepter.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【LES接收方】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (rrsDeliverCode.length() > 12) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【日日顺配送中心编码】字符长度不应大于12! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (crmAreaCode.length() > 10) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【CRM地区编码】字符长度不应大于10! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (ehaierPayer.length() > 12) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商付款方】字符长度不应大于12! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (itcCode.length() > 8) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【工贸代码】字符长度不应大于8! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (moCode.length() > 6) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【销售组织编码】字符长度不应大于6! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (estorgeId.length() > 4) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商库位码】字符长度不应大于4! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (estorgeName.length() > 2000) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商库位名称】字符长度不应大于2000! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (transmitId.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【送达方代码】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (transmitDesc.length() > 2000) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【送达方名称】字符长度不应大于2000! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (lesOeId.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【0E码（LES）】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (customId.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【管理客户编码】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (customDesc.length() > 2000) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【管理客户名称】字符长度不应大于2000! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (industryTradeId.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【产业工贸代码】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (industryTradeDesc.length() > 2000) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【产业工贸描述】字符长度不应大于2000! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (graininessId.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【颗粒度编码】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (netManagementId.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【网单经营体编码】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (esaleId.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商售达方编码】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (esaleName.length() > 2000) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商售达方名称】字符长度不应大于2000! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (saleOrgId.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【销售组织编码1】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (paymentId.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商付款方编码】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (paymentName.length() > 2000) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【电商付款方名称】字符长度不应大于2000! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (areaId.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【地区编码（CRM专用）】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (rrsDistributionId.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【日日顺配送编码】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (rrsDistributionName.length() > 2000) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【日日顺配送名称】字符长度不应大于2000! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (rrsSaleId.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【日日顺售达方】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (rrsSaleName.length() > 2000) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【日日顺售达方名称】字符长度不应大于2000! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (omsRrsPaymentId.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【OMS重庆新日日顺付款方】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (omsRrsPaymentName.length() > 2000) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【OMS重庆新日日顺付款方名称】字符长度不应大于2000! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (sapCustomerCode.length() > 20) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【客户编码】字符长度不应大于20! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (sapCustomerName.length() > 1000) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【客户编码名称】字符长度不应大于1000! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if (branch.length() > 2000) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【分支】字符长度不应大于2000! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                // 导入模板内容的字符长度判断***************END*****************************

                // 导入模板内容的合理性判断及权限判断**************START**********************
                //判断主键唯一
                long checkCode = stockCenterInvWareHouseService.checkInvWarehouseByCode(whCode);
                long checkName = stockCenterInvWareHouseService.checkInvWarehouseByName(whName);
                if (checkCode > 0) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的仓库（TC）代码【"
                            + whCode + "】已存在！请检查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                if (checkName > 0) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的仓库（TC）代码【"
                            + whName + "】已存在！请检查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br></br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                PopInvWarehouse invWarehouse = new PopInvWarehouse();

                invWarehouse.setWhCode(whCode);
                invWarehouse.setWhName(whName);
                //状态dataStatus
                if (  "已启用".equals(status)) {
                	
                	status = "1";
                } else if ( "未启用".equals(status)) {
                	 status = "0";
                }
                invWarehouse.setStatus(Integer.parseInt(status));
                invWarehouse.setCenterCode(centerCode);
                //是否支持货到付款
                if ("否".equals(supportCod)) {
                    supportCod = "0";
                } else if ("是".equals(supportCod)) {
                    supportCod = "1";
                }
                invWarehouse.setSupportCod(Byte.parseByte(supportCod));
                invWarehouse.setSupportedDeliveryMode(supportedDeliveryMode);
                invWarehouse.setLesFiveYard(lesFiveYard);
                invWarehouse.setLesWhCode(lesWhCode);
                invWarehouse.setLesAccepter(lesAccepter);
                invWarehouse.setRrsDeliverCode(rrsDeliverCode);
                invWarehouse.setCrmAreaCode(crmAreaCode);
                invWarehouse.setEhaierPayer(ehaierPayer);
                invWarehouse.setItcCode(itcCode);
                invWarehouse.setMoCode(moCode);
                invWarehouse.setEstorgeId(estorgeId);
                invWarehouse.setEstorgeName(estorgeName);
                invWarehouse.setTransmitId(transmitId);
                invWarehouse.setTransmitDesc(transmitDesc);
                invWarehouse.setLesOeId(lesOeId);
                invWarehouse.setCustomId(customId);
                invWarehouse.setCustomDesc(customDesc);
                invWarehouse.setIndustryTradeId(industryTradeId);
                invWarehouse.setIndustryTradeDesc(industryTradeDesc);
                invWarehouse.setGraininessId(graininessId);
                invWarehouse.setNetManagementId(netManagementId);
                invWarehouse.setEsaleId(esaleId);
                invWarehouse.setEsaleName(esaleName);
                invWarehouse.setSaleOrgId(saleOrgId);
                invWarehouse.setBranch(branch);
                invWarehouse.setPaymentId(paymentId);
                invWarehouse.setPaymentName(paymentName);
                invWarehouse.setAreaId(areaId);
                invWarehouse.setRrsDistributionId(rrsDistributionId);
                invWarehouse.setRrsDistributionName(rrsDistributionName);
                invWarehouse.setRrsSaleId(rrsSaleId);
                invWarehouse.setRrsSaleName(rrsSaleName);
                invWarehouse.setOmsRrsPaymentId(omsRrsPaymentId);
                invWarehouse.setOmsRrsPaymentName(omsRrsPaymentName);
                invWarehouse.setSapCustomerCode(sapCustomerCode);
                invWarehouse.setSapCustomerName(sapCustomerName);
                invWarehouse.setCreateTime(new Date());
               
                invWarehouses.add(invWarehouse);
            }

            ServiceResult<Map<String, Integer>> insResult = wareHouseModel.insertInvWarehouses(invWarehouses);

            int success = 0;
            if (insResult.getResult() != null) {
                success = insResult.getResult().get("success");
            }
            modelMap.put("total", list.size() - 1 - nullRow);
            modelMap.put("ignore", list.size() - success - 1 - nullRow);
            modelMap.put("success", success);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("文件导入数据库失败", e);
            result.setMessage("很抱歉！你导入的Excel数据导入失败，请联系技术负责人！");
            return result;
        }

        modelMap.put("warn", sb.toString());
        result.setData(modelMap);
        return result;
    }

    /**
     * 判断导入文档表头是否正确
     *
     * @param firstLineData
     * @param checkStr
     * @return
     */
    public boolean checkDataTemplate(String[] firstLineData, String checkStr) {
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        for (String str : firstLineData) {
            if (sb.length() > 0)
                sb.append(",");
            sb.append(str.trim());
        }
        String nullStr = sb.toString().replace(checkStr, "").replace(",", "");
        if (nullStr == null || "".equals(nullStr)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 导出虚拟库位
     *
     * @param e_sec_code
     * @param e_les_sec_code
     * @param e_sec_name
     * @param res
     * @throws IOException
     */
    @RequestMapping(value = "/exportInvSectionList", method = RequestMethod.GET)
    public void exportInvSectionList(String e_sec_code, String e_les_sec_code, String e_sec_name, String code, HttpServletResponse res) throws IOException {
        InvSection condition = new InvSection();

        condition.setSecCode(e_sec_code);
        condition.setLesSecCode(e_les_sec_code);
        condition.setSecName(e_sec_name);
        List<InvSection> sectionList = null;
        if ("yes".equals(code)) {
            sectionList = stockCenterInvWareHouseService.exportSection(condition);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("虚拟库位表");
        sheet.setColumnWidth(0, (int) (10.32 * 256));
        sheet.setColumnWidth(1, (int) 15.57 * 256);
        sheet.setColumnWidth(2, (int) 21.57 * 256);
        sheet.setColumnWidth(3, (int) 21.57 * 256);
        sheet.setColumnWidth(4, (int) 10.71 * 256);
        sheet.setColumnWidth(5, (int) 15.14 * 256);
        sheet.setColumnWidth(6, (int) 10.57 * 256);
        sheet.setColumnWidth(7, (int) 27.57 * 256);
        sheet.setColumnWidth(8, (int) 12.29 * 256);
        sheet.setColumnWidth(9, (int) 11.43 * 256);
        sheet.setColumnWidth(10, (int) 12.12 * 256);
        sheet.setColumnWidth(11, (int) 16.57 * 256);
        sheet.setColumnWidth(12, (int) 12.4 * 256);
        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 4.创建单元格，设置值表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        String[] totalHeaders = CHECKSTR.split(",");
        // 设置表头
        for (int i = 0; i < totalHeaders.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(totalHeaders[i]);
            cell.setCellStyle(style);
        }

        //向单元格里添加数据
        String STATUS = "";
        if (sectionList != null) {
            for (short i = 0; i < sectionList.size(); i++) {
                //状态dataStatus
                if (0 == sectionList.get(i).getStatus()) {
                    STATUS = "未启用";
                } else if (1 == sectionList.get(i).getStatus()) {
                    STATUS = "已启用";
                }

                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(sectionList.get(i).getSecCode());
                row.createCell(1).setCellValue(sectionList.get(i).getLesSecCode());
                row.createCell(2).setCellValue(sectionList.get(i).getSecName());
                row.createCell(3).setCellValue(STATUS);
                row.createCell(4).setCellValue(sectionList.get(i).getWhCode());
                row.createCell(5).setCellValue(sectionList.get(i).getSectionProperty());
                row.createCell(6).setCellValue(sectionList.get(i).getChannelCode());
                row.createCell(7).setCellValue(sectionList.get(i).getItemProperty());
                row.createCell(8).setCellValue(sectionList.get(i).getCorpCode());
                row.createCell(9).setCellValue(sectionList.get(i).getCustCode());
                row.createCell(10).setCellValue(sectionList.get(i).getRegionCode());
                row.createCell(11).setCellValue(sectionList.get(i).getEhaierDeliverCode());
                row.createCell(12).setCellValue(sectionList.get(i).getLes0eCode());
            }
        }

        java.util.Date date = new java.util.Date();
        String str = sdf.format(date);
        String fileName = "虚拟库位表" + str;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        ExportExcelUtil.exportCommon(is, fileName, res);
    }

    /**
     * 导出库位基本信息
     *
     * @param e_wh_code
     * @param e_wh_name
     * @param e_center_code
     * @param res
     * @throws IOException
     */
    @RequestMapping(value = "/exportInvWarehouseList", method = RequestMethod.GET)
    public void exportInvWarehouseList(String e_wh_code, String e_wh_name, String e_center_code, String code, HttpServletResponse res) throws IOException {
        PopInvWarehouse condition = new PopInvWarehouse();

        condition.setWhCode(e_wh_code);
        condition.setWhName(e_wh_name);
        condition.setCenterCode(e_center_code);
        List<PopInvWarehouse> warehouseList = null;
        if ("yes".equals(code)) {
            warehouseList = stockCenterInvWareHouseService.exportInvWarehouse(condition);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("虚拟库位表");
        sheet.setColumnWidth(0, (int) (10.32 * 256));
        sheet.setColumnWidth(1, (int) 15.57 * 256);
        sheet.setColumnWidth(2, (int) 21.57 * 256);
        sheet.setColumnWidth(3, (int) 21.57 * 256);
        sheet.setColumnWidth(4, (int) 10.71 * 256);
        sheet.setColumnWidth(5, (int) 15.14 * 256);
        sheet.setColumnWidth(6, (int) 10.57 * 256);
        sheet.setColumnWidth(7, (int) 27.57 * 256);
        sheet.setColumnWidth(8, (int) 12.29 * 256);
        sheet.setColumnWidth(9, (int) 11.43 * 256);
        sheet.setColumnWidth(10, (int) 12.12 * 256);
        sheet.setColumnWidth(11, (int) 16.57 * 256);
        sheet.setColumnWidth(12, (int) 12.4 * 256);
        sheet.setColumnWidth(13, (int) 12.4 * 256);
        sheet.setColumnWidth(14, (int) 12.4 * 256);
        sheet.setColumnWidth(15, (int) 12.4 * 256);
        sheet.setColumnWidth(16, (int) 12.4 * 256);
        sheet.setColumnWidth(17, (int) 12.4 * 256);
        sheet.setColumnWidth(18, (int) 12.4 * 256);
        sheet.setColumnWidth(19, (int) 12.4 * 256);
        sheet.setColumnWidth(20, (int) 12.4 * 256);
        sheet.setColumnWidth(21, (int) 12.4 * 256);
        sheet.setColumnWidth(22, (int) 12.4 * 256);
        sheet.setColumnWidth(23, (int) 12.4 * 256);
        sheet.setColumnWidth(24, (int) 12.4 * 256);
        sheet.setColumnWidth(25, (int) 12.4 * 256);
        sheet.setColumnWidth(26, (int) 12.4 * 256);
        sheet.setColumnWidth(27, (int) 12.4 * 256);
        sheet.setColumnWidth(28, (int) 12.4 * 256);
        sheet.setColumnWidth(29, (int) 12.4 * 256);
        sheet.setColumnWidth(30, (int) 12.4 * 256);
        sheet.setColumnWidth(31, (int) 12.4 * 256);
        sheet.setColumnWidth(32, (int) 12.4 * 256);
        sheet.setColumnWidth(33, (int) 12.4 * 256);
        sheet.setColumnWidth(34, (int) 12.4 * 256);
        sheet.setColumnWidth(35, (int) 12.4 * 256);
        sheet.setColumnWidth(36, (int) 12.4 * 256);
        sheet.setColumnWidth(37, (int) 12.4 * 256);
        sheet.setColumnWidth(38, (int) 12.4 * 256);
        sheet.setColumnWidth(39, (int) 39.43 * 256);
        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 4.创建单元格，设置值表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        String[] totalHeaders = CHECKSTR1.split(",");
        // 设置表头
        for (int i = 0; i < totalHeaders.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(totalHeaders[i]);
            cell.setCellStyle(style);
        }
        //向单元格里添加数据
        String STATUS = "";
        String supportCod = "";
        if (warehouseList != null) {
            for (short i = 0; i < warehouseList.size(); i++) {
                //状态dataStatus
                if (0 == warehouseList.get(i).getStatus()) {
                    STATUS = "未启用";
                } else if (1 == warehouseList.get(i).getStatus()) {
                    STATUS = "已启用";
                }
                //是否支持货到付款
                if (0 == warehouseList.get(i).getSupportCod()) {
                    supportCod = "否";
                } else if (1 == warehouseList.get(i).getSupportCod()) {
                    supportCod = "是";
                }

                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(warehouseList.get(i).getWhCode());
                row.createCell(1).setCellValue(warehouseList.get(i).getWhName());
                row.createCell(2).setCellValue(STATUS);
                row.createCell(3).setCellValue(warehouseList.get(i).getCenterCode());
                row.createCell(4).setCellValue(supportCod);
                row.createCell(5).setCellValue(warehouseList.get(i).getSupportedDeliveryMode());
                row.createCell(6).setCellValue(warehouseList.get(i).getLesFiveYard());
                row.createCell(7).setCellValue(warehouseList.get(i).getLesWhCode());
                row.createCell(8).setCellValue(warehouseList.get(i).getLesAccepter());
                row.createCell(9).setCellValue(warehouseList.get(i).getRrsDeliverCode());
                row.createCell(10).setCellValue(warehouseList.get(i).getCrmAreaCode());
                row.createCell(11).setCellValue(warehouseList.get(i).getEhaierPayer());
                row.createCell(12).setCellValue(warehouseList.get(i).getItcCode());
                row.createCell(13).setCellValue(warehouseList.get(i).getMoCode());
                row.createCell(14).setCellValue(warehouseList.get(i).getEstorgeId());
                row.createCell(15).setCellValue(warehouseList.get(i).getEstorgeName());
                row.createCell(16).setCellValue(warehouseList.get(i).getTransmitId());
                row.createCell(17).setCellValue(warehouseList.get(i).getTransmitDesc());
                row.createCell(18).setCellValue(warehouseList.get(i).getLesOeId());
                row.createCell(19).setCellValue(warehouseList.get(i).getCustomId());
                row.createCell(20).setCellValue(warehouseList.get(i).getCustomDesc());
                row.createCell(21).setCellValue(warehouseList.get(i).getIndustryTradeId());
                row.createCell(22).setCellValue(warehouseList.get(i).getIndustryTradeDesc());
                row.createCell(23).setCellValue(warehouseList.get(i).getGraininessId());
                row.createCell(24).setCellValue(warehouseList.get(i).getNetManagementId());
                row.createCell(25).setCellValue(warehouseList.get(i).getEsaleId());
                row.createCell(26).setCellValue(warehouseList.get(i).getEsaleName());
                row.createCell(27).setCellValue(warehouseList.get(i).getSaleOrgId());
                row.createCell(28).setCellValue(warehouseList.get(i).getBranch());
                row.createCell(29).setCellValue(warehouseList.get(i).getPaymentId());
                row.createCell(30).setCellValue(warehouseList.get(i).getPaymentName());
                row.createCell(31).setCellValue(warehouseList.get(i).getAreaId());
                row.createCell(32).setCellValue(warehouseList.get(i).getRrsDistributionId());
                row.createCell(33).setCellValue(warehouseList.get(i).getRrsDistributionName());
                row.createCell(34).setCellValue(warehouseList.get(i).getRrsSaleId());
                row.createCell(35).setCellValue(warehouseList.get(i).getRrsSaleName());
                row.createCell(36).setCellValue(warehouseList.get(i).getOmsRrsPaymentId());
                row.createCell(37).setCellValue(warehouseList.get(i).getOmsRrsPaymentName());
                row.createCell(38).setCellValue(warehouseList.get(i).getSapCustomerCode());
                row.createCell(39).setCellValue(warehouseList.get(i).getSapCustomerName());
            }
        }

        java.util.Date date = new java.util.Date();
        String str = sdf.format(date);
        String fileName = "库位基本信息表" + str;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        ExportExcelUtil.exportCommon(is, fileName, res);
    }

    @RequestMapping(value = { "/downloadTemplate" })
    public void exportModel(HttpServletRequest request,
        HttpServletResponse response) {
        String fileName = "虚拟库位信息导入EXCEL模板";
        String sheetName = "导入模板";
        ExcelExportUtil.downloadDataTemplate(logger, request, response,
            fileName, sheetName, CHECKSTR);
    }

    @RequestMapping(value = { "/downloadTemplate1" })
    public void downloadTemplate(HttpServletRequest request,
        HttpServletResponse response) {
        String fileName = "基本库位信息导入EXCEL模板";
        String sheetName = "导入模板";
        ExcelExportUtil.downloadDataTemplate(logger, request, response,
            fileName, sheetName, CHECKSTR1);
    }
}
