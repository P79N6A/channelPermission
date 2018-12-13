package com.haier.invoice.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.SettlementInvoiceData;
import com.haier.shop.service.SettlementInvoiceDataDataService;
import com.haier.invoice.service.SettlementInvoiceService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jtbshan on 2018/5/22.
 */
@Component
public  class SettlementInvoiceServiceImpl implements SettlementInvoiceService {
    private static final Logger log = LogManager.getLogger(SettlementInvoiceServiceImpl.class);

    @Autowired
    private SettlementInvoiceDataDataService settlementInvoiceDataDataService;


    public JSONObject paging (Map<String, String> param, PagerInfo pagerInfo){
        return settlementInvoiceDataDataService.paging(param,pagerInfo);
    }
    public JSONObject export (Map<String, String> param){
        return settlementInvoiceDataDataService.export(param);
    }
    public ServiceResult<JSONObject> insert (SettlementInvoiceData param){
        ServiceResult<JSONObject> serviceResult = new ServiceResult<>();
        try {
            settlementInvoiceDataDataService.create(param);

            serviceResult.setSuccess(true);
            serviceResult.setMessage("创建成功");

        }catch (Exception e){
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("创建异常",e);
        }
        return serviceResult;
    }

    public ServiceResult<JSONObject> update (SettlementInvoiceData param){
        ServiceResult<JSONObject> serviceResult = new ServiceResult<>();
        try {
            settlementInvoiceDataDataService.updateSettlementInvoiceData(param);
            serviceResult.setSuccess(true);
            serviceResult.setMessage("更新成功");

        }catch (Exception e){
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("更新异常",e);
        }
        return serviceResult;
    }



    @Override
    public ServiceResult<String> checkAndImport(List<List<String>> list,String user,List<SettlementInvoiceData> newList) {
        ServiceResult<String> result = new ServiceResult<String>();
        StringBuffer info = new StringBuffer();
        int totalCount = 0;
        StringBuilder content = new StringBuilder();
        if (list!=null){
            for (int i=0;i<list.size();i++){
                List<String> row = list.get(i);
                int j = 0;
                List<String> excel = list.get(i);
                String cOrderSn = getRowCell(row, j++);
                if (StringUtil.isEmpty(cOrderSn, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[网单号]不能为空! 请核查后重新导入！");
                    return result;
                }

                String oldcOrderSn = getRowCell(row, j++);
                if (StringUtil.isEmpty(oldcOrderSn, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[原网单号]不能为空! 请核查后重新导入！");
                    return result;
                }

                String sourceOrderSn = getRowCell(row, j++);
                if (StringUtil.isEmpty(sourceOrderSn, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[来源订单号]不能为空! 请核查后重新导入！");
                    return result;
                }

                String source = getRowCell(row, j++);
                if (StringUtil.isEmpty(source, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[订单来源]不能为空! 请核查后重新导入！");
                    return result;
                }

                String sellPeople = getRowCell(row, j++);

                String productCateName = getRowCell(row, j++);
                if (StringUtil.isEmpty(productCateName, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[品类]不能为空! 请核查后重新导入！");
                    return result;
                }

                String brandName = getRowCell(row, j++);
                if (StringUtil.isEmpty(brandName, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[品牌]不能为空! 请核查后重新导入！");
                    return result;
                }

                String sku = getRowCell(row, j++);
                if (StringUtil.isEmpty(sku, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[SKU]不能为空! 请核查后重新导入！");
                    return result;
                }

                String productName = getRowCell(row, j++);
                if (StringUtil.isEmpty(productName, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[宝贝型号]不能为空! 请核查后重新导入！");
                    return result;
                }

                String consignee = getRowCell(row, j++);

                String payTime = getRowCell(row, j++);
                if (StringUtil.isEmpty(payTime, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[订单付款时间]不能为空! 请核查后重新导入！");
                    return result;
                } else {
                    try {
                        payTime = (timeStringToDate(payTime).getTime() / 1000) + "";
                    } catch (Exception e) {
                        result.setMessage("数据有误：Excel第" + (i + 1) + "行[订单付款时间]数据有误! 请核查后重新导入！");
                        return result;
                    }

                }

                String number = getRowCell(row, j++);
                if (StringUtil.isEmpty(number, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[销售数量]不能为空! 请核查后重新导入！");
                    return result;
                } else {
                    try {
                        Integer.parseInt(number);
                    } catch (Exception e) {
                        result.setMessage("数据有误：Excel第" + (i + 1) + "行[销售数量]必须是整数! 请核查后重新导入！");
                        return result;
                    }
                }

                String amount = getRowCell(row, j++);
                if (StringUtil.isEmpty(amount, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[总价(发票金额)]不能为空! 请核查后重新导入！");
                    return result;
                } else {
                    try {
                        new BigDecimal(amount);
                    } catch (Exception e) {
                        result.setMessage("数据有误：Excel第" + (i + 1) + "行[总价(发票金额)]数据有误! 请核查后重新导入！");
                        return result;
                    }
                }

                String year = getRowCell(row, j++);
                if (StringUtil.isEmpty(year, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[年度]不能为空! 请核查后重新导入！");
                    return result;
                } else {
                    try {
                        new SimpleDateFormat("yyyy").parse(year);
                    } catch (ParseException e) {
                        result.setMessage("数据有误：Excel第" + (i + 1) + "行[年度]数据有误! 请核查后重新导入！");
                        return result;
                    }
                }

                String month = getRowCell(row, j++);
                if (StringUtil.isEmpty(month, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[期间]不能为空! 请核查后重新导入！");
                    return result;
                } else {
                    try {
                        int m = Integer.parseInt(month);
                        if (m < 1 || m > 12) {
                            result.setMessage("数据有误：Excel第" + (i + 1) + "行[期间]数据有误! 请核查后重新导入！");
                            return result;
                        }
                    } catch (Exception e) {
                        result.setMessage("数据有误：Excel第" + (i + 1) + "行[期间]数据有误! 请核查后重新导入！");
                        return result;
                    }

                }

                String invoiceTime = getRowCell(row, j++);
                if (StringUtil.isEmpty(invoiceTime, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[发票时间]不能为空! 请核查后重新导入！");
                    return result;
                } else {
                    try {
                        invoiceTime = (timeStringToDate(invoiceTime).getTime() / 1000) + "";
                    } catch (Exception e) {
                        result.setMessage("数据有误：Excel第" + (i + 1) + "行[发票时间]数据有误! 请核查后重新导入！");
                        return result;
                    }
                }

                String isMakeReceipt = getRowCell(row, j++);
                if (StringUtil.isEmpty(isMakeReceipt, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[开票状态]不能为空! 请核查后重新导入！");
                    return result;
                } else if (!isMakeReceipt.equals("已开票") && !isMakeReceipt.equals("当月作废")
                        && !isMakeReceipt.equals("跨月冲红")) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[开票状态]数据有误! 请核查后重新导入！");
                    return result;
                }

                String statusType = getRowCell(row, j++);
                if (StringUtil.isEmpty(statusType, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[发票状态]不能为空! 请核查后重新导入！");
                    return result;
                } else if (statusType.equals("已开票")) {
                    statusType = "1";
                } else if (statusType.equals("作废/冲红")) {
                    statusType = "4";
                } else {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[发票状态]数据有误! 请核查后重新导入！");
                    return result;
                }

                SettlementInvoiceData settlementInvoiceData = settlementInvoiceDataDataService
                        .getByCOrderSnAndStatusType(cOrderSn, Integer.parseInt(statusType));
                if (settlementInvoiceData != null) {
                    result.setMessage("数据已存在，不能重复导入：Excel第" + (i + 1) + "行！");
                    return result;
                } else {
                    settlementInvoiceData = new SettlementInvoiceData();

                    settlementInvoiceData.setSettlementinvoicequeueid(0);//佣金核算发票队列ID
                    settlementInvoiceData.setCordersn(cOrderSn);// '网单号[发票表]
                    settlementInvoiceData.setOldcordersn(oldcOrderSn);//原网单号[网单表]
                    settlementInvoiceData.setSourceordersn(sourceOrderSn);//来源订单号[订单表]
                    settlementInvoiceData.setSource(source);//订单来源[订单表]已转名称
                    settlementInvoiceData.setSellpeople(sellPeople);//销售代表[订单表]
                    settlementInvoiceData.setProductcatename(productCateName);//品类[发票表]
                    settlementInvoiceData.setBrandname(brandName);//品牌[Brands表]
                    settlementInvoiceData.setSku(sku);//SKU[网单表]
                    settlementInvoiceData.setProductname(productName);//宝贝型号[网单表]
                    settlementInvoiceData.setConsignee(consignee);//收货人姓名[订单表]
                    settlementInvoiceData.setPaytime(Integer.valueOf(payTime));//订单付款时间
                    settlementInvoiceData.setNumber(Integer.parseInt(number));//销售数量[发票表]
                    settlementInvoiceData.setAmount(new BigDecimal(amount));//总价(发票金额)[发票表]
                    settlementInvoiceData.setMonth(month);//期间
                    settlementInvoiceData.setYear(year);//年度
                    settlementInvoiceData.setIsmakereceipt(isMakeReceipt);//开票状态[网单表]已转名称
                    settlementInvoiceData.setSettlementtype(0);//结算方式,0:人工,1:系统
                    settlementInvoiceData.setSuccess(0);//是否成功,0:否,1:是,2:不再处理
                    settlementInvoiceData.setCount(0);//推送次数, 超过20就不在处理
                    settlementInvoiceData.setStatustype(Integer.parseInt(statusType));//数据标识,1:已开票,4:冲红/作废
                    settlementInvoiceData.setInvoicetime(Long.valueOf(invoiceTime));//发票时间(开票/作废时间)
                    settlementInvoiceData.setState(1);//审核状态,0:无需审核,1:待业务审核,-1:业务审核拒绝,2:待财务审核,-2:财务审核拒绝,3:审核通过
                    settlementInvoiceData.setBusauditorpeople("");//业务审核人
                    settlementInvoiceData.setBusauditortime(null);//业务审核时间
                    settlementInvoiceData.setFinauditorpeople("");//财务审核人
                    settlementInvoiceData.setFinauditortime(null);//财务审核时间
                    settlementInvoiceData.setPushdata("");//推送数据
                    settlementInvoiceData.setReturndata("");//反馈数据
                    settlementInvoiceData.setLastmessage("");//信息
                    settlementInvoiceData.setAddpeople(user);//数据添加人
                    settlementInvoiceData.setAddtime(new Date());


                }

                newList.add(settlementInvoiceData);
            }

            for (SettlementInvoiceData settlementInvoiceData : newList) {
                try {
                    settlementInvoiceDataDataService.create(settlementInvoiceData);

                    totalCount++;

                } catch (Exception e) {
                    log.error("网单号【" + settlementInvoiceData.getCordersn() + "】导入异常:", e);
                    content.append("网单号【" + settlementInvoiceData.getCordersn() + "】导入异常;</br>");

                }

            }


            result.setSuccess(true);
            result.setMessage("导入成功" + totalCount + "条！"
                    + (!content.toString().equals("") ? "</br>" + content.toString() : ""));

        }else{
            result.setSuccess(false);
            result.setMessage("导入数据为空");
            result.setError("error","导入数据为空");
        }
        return result;
    }




    public void isNullCheck(int index, String col, String colname, StringBuffer result) {
        if (StringUtils.isEmpty(col)) {
            result.append("第" + (index + 1) + "行:" + colname + "为空;");
        }
    }
    private String getRowCell(List<String> row,int col){
       return row.get(col);
    }
    /**
     * 处理时间变成时间戳
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date timeStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtil.isEmpty(time)) {
            return null;
        }
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            return null;
        }
    }
	@Override
	public List<String> getSelections() {
		return settlementInvoiceDataDataService.getSelections();
	}
}
