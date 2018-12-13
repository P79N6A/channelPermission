package com.haier.svc.api.controller.settleCenter.excel;


import com.haier.common.PagerInfo;
import com.haier.invoice.service.OdsTMFXPointMaintainService;
import com.haier.shop.model.OdsTMFXPointMaintain;
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
public class ExportExcelPointMaintainTemplate extends AbstractExcelExportTemplateStandard<OdsTMFXPointMaintain> implements ApplicationContextAware{

	@Autowired
	OdsTMFXPointMaintainService odsTMFXPointMaintainService;
	static ApplicationContext context;

    private JSONArray columnInfo = JSONArray.fromObject("            [\n" +
            "            {\n" +
            "                field: \"ecologyShop\",\n" +
            "                title: \"生态店\"\n" +
            "            }, {\n" +
            "                field: \"year\",\n" +
            "                title: \"年度\"\n" +
            "            }, {\n" +
            "                field: \"beginMonth\",\n" +
            "                title: \"期间起\"\n" +
            "            }, {\n" +
            "                field: \"endMonth\",\n" +
            "                title: \"期间止\"\n" +
            "            }, {\n" +
            "                field: \"industry\",\n" +
            "                title: \"产业  \"\n" +
            "            }, {\n" +
            "                field: \"sku\",\n" +
            "                title: \"SKU  \"\n" +
            "            }, {\n" +
            "                field: \"modelDes\",\n" +
            "                title: \"型号描述\"\n" +
            "            }, {\n" +
            "                field: \"brand\",\n" +
            "                title: \"品牌\"\n" +
            "            },{\n" +
            "                field: 'rewardType',\n" +
            "                title: '奖励类型'\n" +
            "            }, {\n" +
            "                field: \"type\",\n" +
            "                title: \"类型\"\n" +
            "            },  {\n" +
            "                field: \"beginTarget\",\n" +
            "                title: \"目标起\"\n" +
            "            }, {\n" +
            "                field: \"endTarget\",\n" +
            "                title: \"目标止\"\n" +
            "            }, {\n" +
            "                field: \"basePoint\",\n" +
            "                title: \"基础点位\"\n" +
            "            }, {\n" +
            "                field: \"extraRebate\",\n" +
            "                title: \"额外返点\"\n" +
            "            }, {\n" +
            "                field: \"stepPoint\",\n" +
            "                title: \"台阶点位\"\n" +
            "            }, {\n" +
            "                field: \"markPoint\",\n" +
            "                title: \"达标点位\"\n" +
            "            }, {\n" +
            "                field: \"fixedAmount\",\n" +
            "                title: \"固定金额\"\n" +
            "            }, {\n" +
            "                field: \"skuStepPoint\",\n" +
            "                title: \"SKU销额台阶点位\"\n" +
            "            }, {\n" +
            "                field: \"amountPoint\",\n" +
            "                title: \"销量台阶点位\"\n" +
            "            }, {\n" +
            "                field: \"intervalRule\",\n" +
            "                title: \"对赌计算规则\"\n" +
            "            }, {\n" +
            "                field: \"bettingTarget\",\n" +
            "                title: \"对赌目标\"\n" +
            "            }, {\n" +
            "                field: \"calculationType\",\n" +
            "                title: \"计算类型\"\n" +
            "            }, {\n" +
            "                field: \"bettingCoefficient\",\n" +
            "                title: \"对赌系数\"\n" +
            "            }, {\n" +
            "                field: \"deleteTab\",\n" +
            "                title: \"标记删除\"\n" +
            "            }, {\n" +
            "                field: \"createTime\",\n" +
            "                title: \"创建时间\"\n" +
            "            }, {\n" +
            "                field: \"createBy\",\n" +
            "                title: \"创建人\"\n" +
            "            }, {\n" +
            "                field: \"auditState\",\n" +
            "                title: \"审核状态\"\n" +
            "            }, {\n" +
            "                field: \"auditBy\",\n" +
            "                title: \"审核人\"\n" +
            "            }, {\n" +
            "                field: \"auditTime\",\n" +
            "                title: \"审核时间\"\n" +
            "            }\n" +
            "            ]");
	@Override
	public String[] getSheetNames() {
		// TODO Auto-generated method stub
		return new String[]{"返利政策点位"};
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

		odsTMFXPointMaintainService = context.getBean(OdsTMFXPointMaintainService.class);

		Map<String, Object> map=odsTMFXPointMaintainService.export(parameters);
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
