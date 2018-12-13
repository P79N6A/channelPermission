package com.haier.svc.api.controller.settleCenter.excel;


import com.haier.invoice.service.SettlementInvoiceService;
import com.haier.shop.model.SettlementInvoiceData;
import com.haier.svc.api.controller.excel.AbstractExcelExportTemplateStandard;
import net.sf.json.JSONArray;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ExportExcelSettlementInvoiceTemplate extends AbstractExcelExportTemplateStandard<Map<String, String>> implements ApplicationContextAware{

	@Autowired
	SettlementInvoiceService settlementInvoiceService;
	static ApplicationContext context;

    private JSONArray columnInfo = JSONArray.fromObject("            [\n" +

			"                {\n" +
			"                    field: 'cordersn',\n" +
			"                    title: '网单号',\n" +
			"                    width: 135,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'oldcordersn',\n" +
			"                    title: '原网单号',\n" +
			"                    width: 110,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'sourceordersn',\n" +
			"                    title: '来源订单号',\n" +
			"                    width: 125,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'source',\n" +
			"                    title: '订单来源',\n" +
			"                    width: 70,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'sellpeople',\n" +
			"                    title: '销售代表',\n" +
			"                    width: 90,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'productcatename',\n" +
			"                    title: '品类',\n" +
			"                    width: 70,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'brandname',\n" +
			"                    title: '品牌',\n" +
			"                    width: 60,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'sku',\n" +
			"                    title: 'sku',\n" +
			"                    width: 90,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'productname',\n" +
			"                    title: '宝贝型号',\n" +
			"                    width: 110,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'consignee',\n" +
			"                    title: '收货人姓名',\n" +
			"                    width: 70,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'paytime',\n" +
			"                    title: '订单付款时间',\n" +
			"                    width: 140,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'number',\n" +
			"                    title: '销售数量',\n" +
			"                    width: 60,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'amount',\n" +
			"                    title: '总价(发票金额)',\n" +
			"                    width: 85,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'year',\n" +
			"                    title: '年度',\n" +
			"                    width: 40,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'month',\n" +
			"                    title: '期间',\n" +
			"                    width: 30,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'invoicetime',\n" +
			"                    title: '发票时间',\n" +
			"                    width: 140,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'ismakereceipt',\n" +
			"                    title: '开票状态',\n" +
			"                    width: 60,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'statustype',\n" +
			"                    title: '发票状态',\n" +
			"                    width: 60,\n" +
			"                    align: 'center'\n" +
			"                },\n" +
			"                {\n" +
			"                    field: 'state',\n" +
			"                    title: '审核状态',\n" +
			"                    width: 80,\n" +
			"                    align: 'center'\n" +
			"                }\n" +
			"            ]");
	@Override
	public String[] getSheetNames() {
		// TODO Auto-generated method stub
		return new String[]{"分销明细"};
	}

	@Override
	public String[][] getTitles() {
	    List<String> titleList = new ArrayList<>();
	    for(int i = 0;i< columnInfo.size();i++){
            titleList.add(columnInfo.getJSONObject(i).getString("title"));
        }
        String[] titleArray = titleList.toArray(new String[titleList.size()]);
        return new String[][]{titleArray};
		// TODO Auto-generated method stub
/*		return new String[][]{{"生态店","年度","期间起","期间止","产业","SKU","型号描述","品牌",
				"奖励类型","类型","目标起","目标止","基础点位","额外返点","台阶点位","达标点位",
				"固定金额","SKU销额台阶点位","销量台阶点位","创建时间","创建人","审核状态","删除标记",
				"审核人","审核时间"}};*/
	}
	@Override
    public String[][] getColumns() {
        // TODO Auto-generated method stub
        List<String> titleList = new ArrayList<>();
        for(int i = 0;i< columnInfo.size();i++){
            titleList.add(columnInfo.getJSONObject(i).getString("field"));
        }
        String[] titleArray = titleList.toArray(new String[titleList.size()]);
        return new String[][]{titleArray};
     /*   return new String[][]{{"ecologyShop","year","beginMonth","endMonth","industry","sku","modelDes","品牌",
                "奖励类型","类型","目标起","目标止","基础点位","额外返点","台阶点位","达标点位",
                "固定金额","SKU销额台阶点位","销量台阶点位","创建时间","创建人","审核状态","删除标记",
                "审核人","审核时间"}};*/
    }


	

	@Override
	protected void buildBody(int sheetIndex) {
	    String []columns = getColumns()[sheetIndex];
		// TODO Auto-generated method stub
		Sheet sheet = getSheet(sheetIndex);
		int startIndex = this.getBodyStartIndex(sheetIndex);

		settlementInvoiceService = context.getBean(SettlementInvoiceService.class);

		Map<String, Object> map=settlementInvoiceService.export(parameters);
		List<Map<String,Object>> list=(List<Map<String,Object>>) map.get("rows");

		 for(int i = 0; i < list.size(); i++){
			Row row = sheet.createRow(i+startIndex);
			row.setHeight((short)300);
			int index = 0;
			/**
			 * 此处从dubbo服务来接受数据
			 */
			for(int j = 0; j<columns.length;j++){
                sheet.setDefaultColumnStyle(j, getStringCellStyle());
			    Object cellObj = list.get(i).get(columns[j]);
			    String cellVal = "";
			    if(cellObj instanceof BigDecimal){
                    cellVal = ((BigDecimal) cellObj).toString();
                }else if(cellObj instanceof Integer){
			        cellVal = ((Integer)cellObj).toString();
                }else if(cellObj instanceof Long){
                    cellVal = ((Long)cellObj).toString();
                }else {
			        cellVal = (String)cellObj;
                }
                createStyledCell(row,index++,cellVal,getStringCellStyle());
            }

/*			createStyledCell(row,index++,list.get(i).getEcologyShop()+"",getStringCellStyle());
			createStyledCell(row,index++,list.get(i).getYear(),getStringCellStyle());
			createStyledCell(row,index++,list.get(i).getBeginMonth(),getStringCellStyle());
			createStyledCell(row,index++,list.get(i).getEndMonth(),getStringCellStyle());
			createStyledCell(row,index++,list.get(i).getIndustry(),getStringCellStyle());
			createStyledCell(row,index++,list.get(i).getIndustry(),getStringCellStyle());
			createStyledCell(row,index++,list.get(i).getSku(),getStringCellStyle());
			createStyledCell(row,index++,list.get(i).getModelDes(),getStringCellStyle());
			createStyledCell(row,index++,list.get(i).getBrand(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getRewardType(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getType(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getBeginTarget().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getEndTarget().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getBasePoint().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getExtraRebate().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getStepPoint().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getMarkPoint().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getFixedAmount().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getSkuStepPoint().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getAmountPoint().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getCreateTime().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getCreateBy().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getAuditState().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getDeleteTab().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getAuditBy().toString(),getStringCellStyle());
			 createStyledCell(row,index++,list.get(i).getAuditTime().toString(),getStringCellStyle());*/
		}

	}
	protected CellStyle getStringCellStyle(){
		Font font = workbook.createFont();
		//font.setColor(HSSFColor.BLUE_GREY.index);
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(false);
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setDataFormat((short) 49);
		short border = 1;
		setCellBorder(cellStyle,border,border,border,border);
		return cellStyle;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}
	
}
