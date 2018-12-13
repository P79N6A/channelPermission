package com.haier.svc.api.controller.invoice;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.invoice.service.TradingFlowSerivce;
import com.haier.invoice.service.TransactionSapService;
import com.haier.invoice.service.ZfbOrderDetailsService;
import com.haier.shop.dto.ZfbOrderMatchingDto;
import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.TradingFlow;
import com.haier.shop.model.TransactionSap;
import com.haier.shop.model.ZfbOrdersDetails;
import com.haier.svc.api.controller.util.ExcelCallbackInterfaceUtil;
import com.haier.svc.api.controller.util.ExcelExportUtil;

import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
/**
 * 支付宝流水

 * @create 2018-01-10
 **/
@Controller
@RequestMapping(value = "zfbOrder/")
public class ZfbOrderController {

    private static final Logger logger = LogManager.getLogger(ZfbOrderController.class);
    SimpleDateFormat sd =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ZfbOrderDetailsService zfbOrderDetailsService;
    
    @Autowired
    private TradingFlowSerivce tradingFlowSerivce;
    
    @Autowired
    private TransactionSapService transactionSapService;
    /**
     *  支付宝流水
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "orderPage" }, method = { RequestMethod.GET })
    String zfbOrderPage(HttpServletRequest request, HttpServletResponse response,
                        Map<String, Object> modelMap) {
        return "invoice/zfbOrderPage";
    }
    /**
    //TODO 支付宝order后的数据
    * @param queryOrder
    * @param request
    * @param response
    */
   @RequestMapping(value = { "orderList" }, method = { RequestMethod.POST })
   @ResponseBody
   public void orderList(QueryZFBOrderParameter queryOrder,
           HttpServletRequest request, HttpServletResponse response) {
   	
   	try {
           if (queryOrder == null) {
               return;
           }
           //参数验证
           if (queryOrder.getPage() != null && queryOrder.getPage() != 0) {
               queryOrder.setPage((queryOrder.getPage() - 1) * queryOrder.getRows());
           }
           if (queryOrder.getRows() == null) {
               queryOrder.setRows(50);
           }

           if (queryOrder.getAddTimeEnd() != null && queryOrder.getAddTimeEnd().length() > 0) {
               queryOrder.setAddTimeEnd(queryOrder.getAddTimeEnd() + " 23:59:59");
           }
           if(queryOrder.getAddTimeBegine()!=null && queryOrder.getAddTimeBegine().length()>0) {
        	   queryOrder.setAddTimeBegine(queryOrder.getAddTimeBegine() + " 00:00:00");
           }
           ServiceResult<List<ZfbOrdersDetails>> serviceResult=zfbOrderDetailsService.selectZfbOrdersByParam(queryOrder);
           Map<String, Object> retMap = new HashMap<String, Object>();
           if (serviceResult != null && serviceResult.getSuccess() == true) {
               List<ZfbOrdersDetails> result = serviceResult.getResult();
               SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               for (ZfbOrdersDetails zfbOrdersDetails : result) {
            	   Date createTime = zfbOrdersDetails.getCreateTime();
            	   if(createTime!=null) {
            		   String format = formatter.format(createTime);
            		   zfbOrdersDetails.setTime(format);
            	   }
			}
               PagerInfo pager = serviceResult.getPager();
               retMap.put("total", pager.getRowsCount());
               retMap.put("rows", result);
           } else {
               retMap.put("total", 0);
               retMap.put("rows", new ArrayList<>());
           }
           Gson gson = new Gson();
           response.addHeader("Content-type", "text/html;charset=utf-8");
           response.getWriter().write(gson.toJson(retMap));
           response.getWriter().flush();
           response.getWriter().close();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   /**
    *  支付宝流水每日汇总
    * @param request
    * @param response
    * @param modelMap
    * @return
    */
   @RequestMapping(value = { "tradingFlow" }, method = { RequestMethod.GET })
   String tradingFlow(HttpServletRequest request, HttpServletResponse response,
                       Map<String, Object> modelMap) {
       return "invoice/tradingflow";
   }
   
   /**
   //TODO 支付宝流水每日汇总后的数据
   * @param queryOrder
   * @param request
   * @param response
   */
  @RequestMapping(value = { "tradingFlowList" }, method = { RequestMethod.POST })
  @ResponseBody
  public void tradingFlowList(QueryZFBOrderParameter queryOrder,
          HttpServletRequest request, HttpServletResponse response) {
  	
  	try {
          if (queryOrder == null) {
              return;
          }
          //参数验证
          if (queryOrder.getPage() != null && queryOrder.getPage() != 0) {
              queryOrder.setPage((queryOrder.getPage() - 1) * queryOrder.getRows());
          }
          if (queryOrder.getRows() == null) {
              queryOrder.setRows(50);
          }

          if (queryOrder.getAddTimeEnd() != null && queryOrder.getAddTimeEnd().length() > 0) {
              queryOrder.setAddTimeEnd(queryOrder.getAddTimeEnd() + " 23:59:59");
          }
          if(queryOrder.getAddTimeBegine()!=null && queryOrder.getAddTimeBegine().length()>0) {
       	   queryOrder.setAddTimeBegine(queryOrder.getAddTimeBegine() + " 00:00:00");
          }
          ServiceResult<List<TradingFlow>> serviceResult=tradingFlowSerivce.selectTradingFlowByParam(queryOrder);
          Map<String, Object> retMap = new HashMap<String, Object>();
          if (serviceResult != null && serviceResult.getSuccess() == true) {
              List<TradingFlow> result = serviceResult.getResult();
              SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
              for (TradingFlow zfbOrdersDetails : result) {
           	   Date createTime = zfbOrdersDetails.getCreateTime();
           	   if(createTime!=null) {
           		   String format = formatter.format(createTime);
           		   zfbOrdersDetails.setTime(format);
           	   }
			}
              PagerInfo pager = serviceResult.getPager();
              retMap.put("total", pager.getRowsCount());
              retMap.put("rows", result);
          } else {
              retMap.put("total", 0);
              retMap.put("rows", new ArrayList<>());
          }
          Gson gson = new Gson();
          response.addHeader("Content-type", "text/html;charset=utf-8");
          response.getWriter().write(gson.toJson(retMap));
          response.getWriter().flush();
          response.getWriter().close();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
    /**
     * 匹配shop.Orders  后的页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "matchingOrderPage" }, method = { RequestMethod.GET })
    String matchingOrderPage(HttpServletRequest request, HttpServletResponse response,
                        Map<String, Object> modelMap) {
        return "invoice/zfbMatchingOrderPage";
    }
    /**
     //TODO 支付宝匹配order后的数据
     * @param queryOrder
     * @param request
     * @param response
     */
    @RequestMapping(value = { "matchingOrderList" }, method = { RequestMethod.POST })
    @ResponseBody
    public void matchingOrderList(QueryZFBOrderParameter queryOrder,
            HttpServletRequest request, HttpServletResponse response) {
    	
    	try {
            if (queryOrder == null) {
                return;
            }
            //参数验证
            if (queryOrder.getPage() != null && queryOrder.getPage() != 0) {
                queryOrder.setPage((queryOrder.getPage() - 1) * queryOrder.getRows());
            }
            if (queryOrder.getRows() == null) {
                queryOrder.setRows(50);
            }

            if (queryOrder.getAddTimeEnd() != null && queryOrder.getAddTimeEnd().length() > 0) {
                queryOrder.setAddTimeEnd(queryOrder.getAddTimeEnd() + " 23:59:59");
            }
          
            ServiceResult<List<ZfbOrderMatchingDto>> serviceResult=zfbOrderDetailsService.selectAllByParam(queryOrder);
            Map<String, Object> retMap = new HashMap<String, Object>();
            if (serviceResult != null && serviceResult.getSuccess() == true) {
                List<ZfbOrderMatchingDto> result = serviceResult.getResult();
                PagerInfo pager = serviceResult.getPager();
                retMap.put("total", pager.getRowsCount());
                retMap.put("rows", result);
            } else {
                retMap.put("total", 0);
                retMap.put("rows", new ArrayList<>());
            }
            Gson gson = new Gson();
            response.addHeader("Content-type", "text/html;charset=utf-8");
            response.getWriter().write(gson.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     //TODO 导出订单列表
     *
     * @param queryOrder
     * @param request
     * @param response
     */
    @RequestMapping(value = {"exportMatchingOrderList"})
    void exportMatchingOrderList(QueryZFBOrderParameter queryOrder,
                         HttpServletRequest request, HttpServletResponse response) {

    	if (queryOrder == null) {
            return;
        }
        //参数验证
        if (queryOrder.getPage() != null && queryOrder.getPage() != 0) {
            queryOrder.setPage((queryOrder.getPage() - 1) * queryOrder.getRows());
        }
        if (queryOrder.getRows() == null) {
            queryOrder.setRows(50);
        }

        if (queryOrder.getAddTimeEnd() != null && queryOrder.getAddTimeEnd().length() > 0) {
            queryOrder.setAddTimeEnd(queryOrder.getAddTimeEnd() + " 23:59:59");
        }

        ServiceResult<List<ZfbOrderMatchingDto>> serviceResult=zfbOrderDetailsService.exportMatchingOrderList(queryOrder);
        List<ZfbOrderMatchingDto> list = serviceResult.getResult();

        String fileName = "订单查询列表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";
        String[] sheetHead = new String[]{"序号", "商户订单号", "业务类型", "差异", "创建时间", "支付宝流水收入金额", "支付宝流水支出金额", "订单总金额",
                "实付金额", "发票金额", "网单使用积分", "网单集分宝", "网单点券", "网单红包", "网单金额", "网单数量", "网单使用优惠券", "节能补贴金额",
                "订单优惠价格分摊",  "账务流水单号", "业务流水单号","对方账户", 
                "备注","订单来源"};
        try {
            ExcelExportUtil.exportEntity(logger, request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {

                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp) throws Exception {
                            setExportOrderList(sheet, temp, list);
                        }
                    });
        } catch (Exception e) {
        	logger.error("订单查询列表导出失败", e);
            e.printStackTrace();
        }
    }
    /**
     * 导出具体数据，实现回调函数
     *
     * @param sheet
     * @param temp  行号
     * @param list  传入需要导出的 list
     * @throws WriteException
     */
    private void setExportOrderList(WritableSheet sheet, int temp,
                                    List<ZfbOrderMatchingDto> list) throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        int index = 0;
        for (ZfbOrderMatchingDto lt : list) {

            index++;
            int i = 0;
            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, getStringValue(index), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getOrdersn()), textFormat));//商户订单号
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getBusinessTypeName()), textFormat));//业务类型
            String differenceStatus="";
            if(lt.getDifferenceStatus() !=null) {
            	if (lt.getDifferenceStatus() == 1) {
            		differenceStatus = "没有差异";
                }else if (lt.getDifferenceStatus() == 2) {
                	differenceStatus = "有差异";
                }
            }
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(differenceStatus),
                    textFormat));	//差异
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(getTimeFormat(sd.format(lt.getCreateTime()))),
                    textFormat));  // 创建时间
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getIncomeMoney().toString()),
                    textFormat));	//支付宝流水收入金额
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue((lt.getExpenditureMoney().toString())),
                    textFormat));	//支付宝流水支出金额
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getProductAmounto()),
                    textFormat));	//订单总金额
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getOrderamount().toString()),
                    textFormat));	//实付金额
            sheet.setColumnView(++i, 25);
            if(lt.getBillAmount()==null) {
            	sheet.addCell(new Label(i, temp, getStringValue("0.00"),
                        textFormat));	//发票金额
            }else {
            	sheet.addCell(new Label(i, temp, getStringValue(lt.getBillAmount().toString()),
                        textFormat));	//发票金额
            }
            
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getPoints()),
                    textFormat));	//网单使用积分
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getJfbAmount()),
                    textFormat));	//网单集分宝
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getDjAmount()),
                    textFormat));	//网单点券
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getHbAmount()),
                    textFormat));	//网单红包
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getProductAmount()),
                    textFormat));	//网单金额
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getNumber().toString()),
                    textFormat));	//网单数量
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getCouponAmount()),
                    textFormat));	//网单使用优惠券
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getEsAmount()),
                    textFormat));	//节能补贴金额
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getItemShareAmount()),
                    textFormat));	//订单优惠价格分摊
            /*sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getShippingAmount()),
                    textFormat));	//淘宝运费
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getTotalEsAmount()),
                    textFormat));	//总节能补贴
*/            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getAccountsNo()),
                    textFormat));	//账务流水单号
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getBusinessNo()),
                    textFormat));	//业务流水单号
           /* sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getAccountBalance().toString()),
                    textFormat));	//账户余额
            sheet.setColumnView(++i, 35);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getProductName()),
                    textFormat));	//商品名称
            
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getPaymentName()),
                    textFormat));	//支付方式
*/          
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getMemberLoginId()),
            		textFormat));	//对方账户 
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getRemake()),
                    textFormat));	//备注
            /*sheet.setColumnView(++i, 25);
            if(lt.getTaobaoBusinessType()==null) {
            	sheet.addCell(new Label(i, temp, getStringValue(""),
                        textFormat));	//淘宝业务类型
            }else {
            	sheet.addCell(new Label(i, temp, getStringValue(lt.getTaobaoBusinessType()),
                        textFormat));	//淘宝业务类型
            }*/
            
            String source="";
            if(lt.getSource()!=null) {
            	 if( "TBSC".equals(lt.getSource())){
                     source="海尔官方淘宝旗舰店";
                 }
                 else if("TOPBOILER".equals(lt.getSource())){
                     source="海尔热水器专卖店";
                 }
                 else if("TONGSHUAI".equals(lt.getSource())){
                     source="淘宝网统帅日日顺乐家专卖店";
                 }
                 else if("TONGSHUAIFX".equals(lt.getSource())){
                     source="统帅品牌商";
                 }
                 else if("TOPXB".equals(lt.getSource())){
                     source="海尔新宝旗舰店";
                 }
                 else if("MOOKA".equals(lt.getSource())){
                     source="mooka模卡官方旗舰店";
                 }
                 else if("WASHER".equals(lt.getSource())){
                     source="海尔洗衣机旗舰店";
                 }
                 else if("FRIDGE".equals(lt.getSource())){
                     source="海尔冰冷旗舰店";
                 }
                 else if("AIR".equals(lt.getSource())){
                     source="海尔空调旗舰店";
                 }
                 else if("TBCT".equals(lt.getSource())){
                     source="村淘海尔商家";
                 }
                 else if("GQGYS".equals(lt.getSource())){
                     source="生态授权店";
                 }
                 else if("TMKSD".equals(lt.getSource())){
                     source="天猫卡萨帝旗舰店";
                 }
                 else if("TMTV".equals(lt.getSource())){
                     source="海尔电视旗舰店";
                 }
                 else if("TBCFDD".equals(lt.getSource())){
                     source="海尔厨房大电旗舰店";
                 }
                 else if("TBXCR".equals(lt.getSource())){
                     source="天猫小超人旗舰店";
                 }
                 else if("TOPSHJD".equals(lt.getSource())){
                     source="海尔生活电器专卖店";
                 }
                 else if("TOPDHSC".equals(lt.getSource())){
                     source="海尔生活家电旗舰店";
                 }
                 else if("GMZX".equals(lt.getSource())){
                     source="国美海尔官方旗舰店";
                 }
                 else if("GMZXTS".equals(lt.getSource())){
                     source="国美统帅官方旗舰店";
                 }
                 else if("SNYG".equals(lt.getSource())){
                     source="苏宁统帅官方旗舰店";
                 }
                 else if("SNHEGQ".equals(lt.getSource())){
                     source="苏宁海尔官方旗舰店";
                 }
                 else if("SNQDZX".equals(lt.getSource())){
                     source="苏宁渠道中心";
                 }
                 else if("DDW".equals(lt.getSource())){
                     source="当当";
                 }
                 else if("JDHEGQ".equals(lt.getSource())){
                     source="京东海尔官方旗舰店";
                 }
                 else if("JDHEBXGQ".equals(lt.getSource())){
                     source="京东海尔集团冰箱官方旗舰店";
                 }
            }
            
            
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(source),
                    textFormat));	//订单来源
            temp++;
        }
    }
    /**
     * 把object转为String null时转为""
     *
     * @param obj
     * @return
     */
    public static String getStringValue(Object obj) {
        if (obj == null)
            return "";
        return String.valueOf(obj);
    }
    /**
     * 空时间转换
     *
     * @param time
     * @return
     */
    public static String getTimeFormat(String time) {
        if (time == null || time.equals("1970-01-01 08:00:00")
                || time.equals("0000-00-00 00:00:00"))
            return "";
        return String.valueOf(time);
    }
    
    /**
     *  支付宝流水报表页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "zfbOrderReportForm" }, method = { RequestMethod.GET })
    String zfbOrderReportForm(HttpServletRequest request, HttpServletResponse response,
                        Map<String, Object> modelMap) {
        return "invoice/zfbOrderReportForm";
    }
    
    /**
    //TODO 支付宝报表order后的数据
    * @param queryOrder
    * @param request
    * @param response
    */
   @RequestMapping(value = { "orderReportFormList" }, method = { RequestMethod.POST })
   @ResponseBody
   public void orderReportFormList(QueryZFBOrderParameter queryOrder,
           HttpServletRequest request, HttpServletResponse response) {
   	
   	try {
           if (queryOrder == null) {
               return;
           }
           if(queryOrder.getAddTimeEnd() == null) {
        	   return;
           }
           //参数验证
           if (queryOrder.getPage() != null && queryOrder.getPage() != 0) {
               queryOrder.setPage((queryOrder.getPage() - 1) * queryOrder.getRows());
           }
           if (queryOrder.getRows() == null) {
               queryOrder.setRows(50);
           }

           if (queryOrder.getAddTimeEnd() != null && queryOrder.getAddTimeEnd().length() > 0) {
               queryOrder.setAddTimeEnd(queryOrder.getAddTimeEnd() + " 23:59:59");
           }
           if(queryOrder.getAddTimeBegine()!=null && queryOrder.getAddTimeBegine().length()>0) {
        	   queryOrder.setAddTimeBegine(queryOrder.getAddTimeBegine() + " 00:00:00");
           }
           /*if(StringUtil.isEmpty(queryOrder.getMemberLoginId())) {
        	   queryOrder.setMemberLoginId("'淘宝（中国）软件有限公司(r-qudao@taobao.com)','浙江天猫技术有限公司(tmall-weibo@service.taobao.com)',"+
				       					   "'阿里巴巴(中国)软件有限公司(cuntao-3c@alibaba-inc.com)','中国扶贫基金会(weibo@fupin.org.cn)',"+
				       					   "'深圳壹基金公益基金会(donation00@one-foundation.com)','重庆市蚂蚁小微小额贷款有限公司(f53_hbfq@service.aliyun.com)',"+
			       						   "'浙江天猫技术有限公司(jifenb2c@taobao.com)','浙江天猫技术有限公司(pinpaiticheng@taobao.com)',"+
			       						   "'浙江天猫技术有限公司(p-o2oisv@service.taobao.com)','浙江天猫技术有限公司(ju.tuangou@service.taobao.com)',"+
			       						   "'浙江天猫技术有限公司(p-tmall-kefujifen@service.taobao.com)','杭州阿里妈妈软件服务有限公司(n-htcs@service.taobao.com)',"+
			       						   "'杭州阿里妈妈软件服务有限公司(n-p-choujian@service.taobao.com)','杭州阿里妈妈软件服务有限公司(n-p-ztc@service.taobao.com)',"+
			       						   "'杭州阿里妈妈软件服务有限公司(n-r-tbke@service.taobao.com)','淘宝（中国）软件有限公司(p-tanxdsp@service.taobao.com)',"+
			       						   "'浙江天猫技术有限公司(pinpaiticheng@taobao.com)','浙商财产保险股份有限公司(xingzhengfeiyong@zsins.com)',"+
			       						   "'支付宝(中国)网络技术有限公司(systemrevenue@alipay.com)','众安在线财产保险股份有限公司(za_zfbf3_fjr@zhongan.com)',"+
			       						   "'众安在线财产保险股份有限公司(za_zfbs3_fjr@zhongan.com)'");
           }*/
           ServiceResult<List<ZfbOrdersDetails>> serviceResult=zfbOrderDetailsService.selectReportFormByParam(queryOrder);
           Map<String, Object> retMap = new HashMap<String, Object>();
           if (serviceResult != null && serviceResult.getSuccess() == true) {
               List<ZfbOrdersDetails> result = serviceResult.getResult();
               PagerInfo pager = serviceResult.getPager();
               retMap.put("total", pager.getRowsCount());
               retMap.put("rows", result);
           } else {
               retMap.put("total", 0);
               retMap.put("rows", new ArrayList<>());
           }
           Gson gson = new Gson();
           response.addHeader("Content-type", "text/html;charset=utf-8");
           response.getWriter().write(gson.toJson(retMap));
           response.getWriter().flush();
           response.getWriter().close();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   
	  /**
	     *  支付宝流水报表页面
	     * @param request
	     * @param response
	     * @param modelMap
	     * @return
	     */
	    @RequestMapping(value = { "transactionCostsPushSap" }, method = { RequestMethod.GET })
	    String transactionCostsPushSap(HttpServletRequest request, HttpServletResponse response,
	                        Map<String, Object> modelMap) {
	        return "invoice/transactionCostsPushSapList";
	    }
	  /**
	    //TODO 支付宝order后的数据
	    * @param queryOrder
	    * @param request
	    * @param response
	    */
	   @RequestMapping(value = { "transactionCostsPushSapPage" }, method = { RequestMethod.POST })
	   @ResponseBody
	   public void transactionCostsPushSapPage(QueryZFBOrderParameter queryOrder,
	           HttpServletRequest request, HttpServletResponse response) {
	   	
	   	try {
	           if (queryOrder == null) {
	               return;
	           }
	           //参数验证
	           if (queryOrder.getPage() != null && queryOrder.getPage() != 0) {
	               queryOrder.setPage((queryOrder.getPage() - 1) * queryOrder.getRows());
	           }
	           if (queryOrder.getRows() == null) {
	               queryOrder.setRows(50);
	           }

	           if (queryOrder.getAddTimeEnd() != null && queryOrder.getAddTimeEnd().length() > 0) {
	               queryOrder.setAddTimeEnd(queryOrder.getAddTimeEnd() + " 23:59:59");
	           }
	           if(queryOrder.getAddTimeBegine()!=null && queryOrder.getAddTimeBegine().length()>0) {
	        	   queryOrder.setAddTimeBegine(queryOrder.getAddTimeBegine() + " 00:00:00");
	           }
	           ServiceResult<List<TransactionSap>> serviceResult=transactionSapService.selectZfbOrdersByParam(queryOrder);
	           Map<String, Object> retMap = new HashMap<String, Object>();
	           if (serviceResult != null && serviceResult.getSuccess() == true) {
	               List<TransactionSap> result = serviceResult.getResult();
	               SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	               for (TransactionSap zfbOrdersDetails : result) {
	            	   Date createTime = zfbOrdersDetails.getCreateTime();
	            	   if(createTime!=null) {
	            		   String format = formatter.format(createTime);
	               		   zfbOrdersDetails.setTime(format);
	            	   }
				}
	               PagerInfo pager = serviceResult.getPager();
	               retMap.put("total", pager.getRowsCount());
	               retMap.put("rows", result);
	           } else {
	               retMap.put("total", 0);
	               retMap.put("rows", new ArrayList<>());
	           }
	           Gson gson = new Gson();
	           response.addHeader("Content-type", "text/html;charset=utf-8");
	           response.getWriter().write(gson.toJson(retMap));
	           response.getWriter().flush();
	           response.getWriter().close();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	   }
	   
	   
	   
	   /**
	     *  支付宝流水报表页面
	     * @param request
	     * @param response
	     * @param modelMap
	     * @return
	     */
	    @RequestMapping(value = { "transactionSummarySap" }, method = { RequestMethod.GET })
	    String transactionSummarySap(HttpServletRequest request, HttpServletResponse response,
	                        Map<String, Object> modelMap) {
	        return "invoice/transactionSummarySap";
	    }
	  /**
	    //TODO 支付宝order后的数据
	    * @param queryOrder
	    * @param request
	    * @param response
	    */
	   @RequestMapping(value = { "transactionSummarySapPage" }, method = { RequestMethod.POST })
	   @ResponseBody
	   public void transactionSummarySapPage(QueryZFBOrderParameter queryOrder,
	           HttpServletRequest request, HttpServletResponse response) {
	   	
	   	try {
	           if (queryOrder == null) {
	               return;
	           }
	           //参数验证
	           if (queryOrder.getPage() != null && queryOrder.getPage() != 0) {
	               queryOrder.setPage((queryOrder.getPage() - 1) * queryOrder.getRows());
	           }
	           if (queryOrder.getRows() == null) {
	               queryOrder.setRows(50);
	           }

	           if (queryOrder.getAddTimeEnd() != null && queryOrder.getAddTimeEnd().length() > 0) {
	               queryOrder.setAddTimeEnd(queryOrder.getAddTimeEnd() + " 23:59:59");
	           }
	           if(queryOrder.getAddTimeBegine()!=null && queryOrder.getAddTimeBegine().length()>0) {
	        	   queryOrder.setAddTimeBegine(queryOrder.getAddTimeBegine() + " 00:00:00");
	           }
	           ServiceResult<List<TransactionSap>> serviceResult=transactionSapService.transactionSummarySapPage(queryOrder);
	           Map<String, Object> retMap = new HashMap<String, Object>();
	           if (serviceResult != null && serviceResult.getSuccess() == true) {
	               List<TransactionSap> result = serviceResult.getResult();
	               SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	               for (TransactionSap zfbOrdersDetails : result) {
	            	   Date createTime = zfbOrdersDetails.getCreateTime();
	            	   if(createTime!=null) {
	            		   String format = formatter.format(createTime);
	               		   zfbOrdersDetails.setTime(format);
	            	   }
				}
	               PagerInfo pager = serviceResult.getPager();
	               retMap.put("total", pager.getRowsCount());
	               retMap.put("rows", result);
	           } else {
	               retMap.put("total", 0);
	               retMap.put("rows", new ArrayList<>());
	           }
	           Gson gson = new Gson();
	           response.addHeader("Content-type", "text/html;charset=utf-8");
	           response.getWriter().write(gson.toJson(retMap));
	           response.getWriter().flush();
	           response.getWriter().close();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	   }
}
