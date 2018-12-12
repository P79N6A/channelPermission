package com.haier.order.services;

import com.haier.common.util.StringUtil;
import com.haier.order.service.MdmService;
import com.haier.order.wsdl.mdm.InType;
import com.haier.order.wsdl.mdm.SelectInfoFromMDM;
import com.haier.order.wsdl.mdm.SelectInfoFromMDMOPResponse;
import com.haier.order.wsdl.mdm.SelectInfoFromMDM_Service;
import com.haier.shop.service.MdmDataService;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class MdmServiceImpl implements MdmService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MdmServiceImpl.class);
    @Autowired
    private MdmDataService mdmDataService;
    @Value("${mdm.location}")
    private String wsdlLocation;
    //@Scheduled(cron = "0 0 0 0 1/1 ?")
    //@Scheduled(cron = "*/10 * * * * ?")
    public void  insert(){
        try{
            mdmDataService.delete();
        }catch (Exception e){
        LOGGER.error("删除数据失败,本次定时任务停止执行");
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String date=sdf.format(new Date());
        int year=Integer.parseInt(date.substring(0,4));
        String lastyear=year-1+"-01-01";
        String currentPage="1";
        System.out.println(this.getClass().getResource(
                  wsdlLocation+"/SelectInfoFromMDM.wsdl"));
        URL url = this.getClass().getResource(
                wsdlLocation + "/SelectInfoFromMDM.wsdl");
        SelectInfoFromMDM_Service selectInfoFromMDM_service=new SelectInfoFromMDM_Service(url);
        SelectInfoFromMDM selectInfoFromMDM=selectInfoFromMDM_service.getSelectInfoFromMDMSOAP();
        InType inType=new InType();
        inType.setCurrentPage(currentPage);
        inType.setStartTime(lastyear);
        inType.setEndTime(date);
        inType.setDepartment("table_hrbtb690_hm_mtl_general_all_B2C");
        inType.setTableName("haiermdm.hrbtb690_hm_mtl_general_all");
        SelectInfoFromMDMOPResponse.Output output=new SelectInfoFromMDMOPResponse.Output();
        output=selectInfoFromMDM.selectInfoFromMDMOP(inType);
        int count=Integer.parseInt(output.getPageCount());
        Document doc = null;
        for (int i=1;i<count+1;i++){
            System.out.println("当前执行的是第"+i+"页,一共"+count+"页;");
            currentPage=i+"";
            InType inType1=new InType();
            inType1.setCurrentPage(currentPage);
            inType1.setStartTime(lastyear);
            inType1.setEndTime(date);
            inType1.setDepartment("table_hrbtb690_hm_mtl_general_all_B2C");
            inType1.setTableName("haiermdm.hrbtb690_hm_mtl_general_all");
            SelectInfoFromMDMOPResponse.Output output1=new SelectInfoFromMDMOPResponse.Output();
            output1=selectInfoFromMDM.selectInfoFromMDMOP(inType1);
            try{
                doc = DocumentHelper.parseText(output1.getOut());
                System.out.println(output1.getOut());
                List<Map<String,Object>> maps=parseDoc(doc);
                int x=mdmDataService.insert(maps);
                System.out.println("本次要插入一共"+maps.size()+"条数据");
            }catch (Exception e){
                LOGGER.error("第"+i+"页数据插入失败");
            }
        }
    }
    public static List<Map<String,Object>> parseDoc(Document doc)throws Exception{
        List<Map<String,Object>> maps=new ArrayList<>();
        Element rootElt = doc.getRootElement();
        if (!(rootElt.getName().equals("OUTPUT"))){
            throw new Exception("XML解析失败,根节点不匹配");
        }
        List<Element>firstChild=rootElt.elements();
        if (!(firstChild.get(0).getName().equals("ROWSET"))||firstChild.size()!=1){
            throw new Exception("XML解析失败,一级子元素不匹配");
        }
        //通过一级子元素得到二级子元素
        List<Element> secondChild=firstChild.get(0).elements();
        if (!(secondChild.get(0).getName().equals("row"))){
            throw new Exception("XML解析失败,一级子元素不匹配");
        }
        for (Element element:secondChild){
            //得到<row></row>
           // Element itemElement=secondChild.get(0);
            //得到row的节点
            List<Element> rowChild=element.elements();
            Map<String,Object> xmlMap=new HashMap();
            for (int i=0;i<rowChild.size(); i++){
                Element row=rowChild.get(i);
                if (row.getName().equalsIgnoreCase("RN")){
                    //得到指定节点的值
                    String rn=row.getStringValue();
                    if (StringUtil.isEmpty(rn)){
                        xmlMap.put("rn","");
                    }else {
                        xmlMap.put("rn",rn);
                    }
                }
                if (row.getName().equalsIgnoreCase("row_id")){
                    //得到指定节点的值
                    String row_id=row.getStringValue();
                    if (StringUtil.isEmpty(row_id)){
                        xmlMap.put("row_id","");
                    }else {
                        xmlMap.put("row_id",row_id);
                    }

                }
                if (row.getName().equalsIgnoreCase("material_code")){
                    //得到指定节点的值
                    String material_code=row.getStringValue();
                    if (StringUtil.isEmpty(material_code)){
                        xmlMap.put("material_code","");
                    }else {
                        xmlMap.put("material_code",material_code);
                    }
                }
                if (row.getName().equalsIgnoreCase("material_descrition")){
                    //得到指定节点的值
                    String material_descrition=row.getStringValue();
                    if (StringUtil.isEmpty(material_descrition)){
                        xmlMap.put("material_descrition","");
                    }else {
                        xmlMap.put("material_descrition",material_descrition);
                    }
                }
                if (row.getName().equalsIgnoreCase("primary_uom")){
                    //得到指定节点的值
                    String primary_uom=row.getStringValue();
                    if (StringUtil.isEmpty(primary_uom)){
                        xmlMap.put("primary_uom","");
                    }else {
                        xmlMap.put("primary_uom",primary_uom);
                    }
                }
                if (row.getName().equalsIgnoreCase("product_attribute")){
                    //得到指定节点的值
                    String product_attribute=row.getStringValue();
                    if (StringUtil.isEmpty(product_attribute)){
                        xmlMap.put("product_attribute","");
                    }else {
                        xmlMap.put("product_attribute",product_attribute);
                    }
                }
                if (row.getName().equalsIgnoreCase("mtl_group_code")){
                    //得到指定节点的值
                    String mtl_group_code=row.getStringValue();
                    if (StringUtil.isEmpty(mtl_group_code)){
                        xmlMap.put("mtl_group_code","");
                    }else {
                        xmlMap.put("mtl_group_code",mtl_group_code);
                    }
                }
                if (row.getName().equalsIgnoreCase("department")){
                    //得到指定节点的值
                    String department=row.getStringValue();
                    if (StringUtil.isEmpty(department)){
                        xmlMap.put("department","");
                    }else {
                        xmlMap.put("department",department);
                    }
                }
                if (row.getName().equalsIgnoreCase("weight_uom_code")){
                    //得到指定节点的值
                    String weight_uom_code=row.getStringValue();
                    if (StringUtil.isEmpty(weight_uom_code)){
                        xmlMap.put("weight_uom_code","");
                    }else {
                        xmlMap.put("weight_uom_code",weight_uom_code);
                    }
                }
                if (row.getName().equalsIgnoreCase("weight")){
                    //得到指定节点的值
                    String weight=row.getStringValue();
                    if (StringUtil.isEmpty(weight)){
                        xmlMap.put("weight","");
                    }else {
                        xmlMap.put("weight",weight);
                    }
                }
                if (row.getName().equalsIgnoreCase("volume_uom_code")){
                    //得到指定节点的值
                    String volume_uom_code=row.getStringValue();
                    if (StringUtil.isEmpty(volume_uom_code)){
                        xmlMap.put("volume_uom_code","");
                    }else {
                        xmlMap.put("volume_uom_code",volume_uom_code);
                    }
                }
                if (row.getName().equalsIgnoreCase("volume")){
                    //得到指定节点的值
                    String volume=row.getStringValue();
                    if (StringUtil.isEmpty(volume)){
                        xmlMap.put("volume","");
                    }else {
                        xmlMap.put("volume",volume);
                    }
                }
                if (row.getName().equalsIgnoreCase("net_weight")){
                    //得到指定节点的值
                    String net_weight=row.getStringValue();
                    if (StringUtil.isEmpty(net_weight)){
                        xmlMap.put("net_weight","");
                    }else {
                        xmlMap.put("net_weight",net_weight);
                    }
                }
                if (row.getName().equalsIgnoreCase("mtl_class")){
                    //得到指定节点的值
                    String mtl_class=row.getStringValue();
                    if (StringUtil.isEmpty(mtl_class)){
                        xmlMap.put("primary_uom","");
                    }else {
                        xmlMap.put("mtl_class",mtl_class);
                    }
                }
                if (row.getName().equalsIgnoreCase("basic_mtl")){
                    //得到指定节点的值
                    String basic_mtl=row.getStringValue();
                    if (StringUtil.isEmpty(basic_mtl)){
                        xmlMap.put("basic_mtl","");
                    }else {
                        xmlMap.put("basic_mtl",basic_mtl);
                    }
                }
                if (row.getName().equalsIgnoreCase("delete_flag")){
                    //得到指定节点的值
                    String delete_flag=row.getStringValue();
                    if (StringUtil.isEmpty(delete_flag)){
                        xmlMap.put("primardelete_flagy_uom","");
                    }else {
                        xmlMap.put("delete_flag",delete_flag);
                    }
                }
                if (row.getName().equalsIgnoreCase("product_department")){
                    //得到指定节点的值
                    String product_department=row.getStringValue();
                    if (StringUtil.isEmpty(product_department)){
                        xmlMap.put("product_department","");
                    }else {
                        xmlMap.put("product_department",product_department);
                    }
                }
                if (row.getName().equalsIgnoreCase("is_village_product")){
                    //得到指定节点的值
                    String is_village_product=row.getStringValue();
                    if (StringUtil.isEmpty(is_village_product)){
                        xmlMap.put("is_village_product","");
                    }else {
                        xmlMap.put("is_village_product",is_village_product);
                    }
                }
                if (row.getName().equalsIgnoreCase("prod_life_state")){
                    //得到指定节点的值
                    String prod_life_state=row.getStringValue();
                    if (StringUtil.isEmpty(prod_life_state)){
                        xmlMap.put("prod_life_state","");
                    }else {
                        xmlMap.put("prod_life_state",prod_life_state);
                    }

                }
                if (row.getName().equalsIgnoreCase("kind_one")){
                    //得到指定节点的值
                    String kind_one=row.getStringValue();
                    if (StringUtil.isEmpty(kind_one)){
                        xmlMap.put("kind_one","");
                    }else {
                        xmlMap.put("kind_one",kind_one);
                    }
                }
                if (row.getName().equalsIgnoreCase("kind_add")){
                    //得到指定节点的值
                    String kind_add=row.getStringValue();
                    if (StringUtil.isEmpty(kind_add)){
                        xmlMap.put("kind_add","");
                    }else {
                        xmlMap.put("kind_add",kind_add);
                    }
                }
                if (row.getName().equalsIgnoreCase("pro_type")){
                    //得到指定节点的值
                    String pro_type=row.getStringValue();
                    if (StringUtil.isEmpty(pro_type)){
                        xmlMap.put("primary_uom","");
                    }else {
                        xmlMap.put("pro_type",pro_type);
                    }
                }
                if (row.getName().equalsIgnoreCase("pro_mid")){
                    //得到指定节点的值
                    String pro_mid=row.getStringValue();
                    if (StringUtil.isEmpty(pro_mid)){
                        xmlMap.put("pro_mid","");
                    }else {
                        xmlMap.put("pro_mid",pro_mid);
                    }
                }
                if (row.getName().equalsIgnoreCase("pro_flag")){
                    //得到指定节点的值
                    String pro_flag=row.getStringValue();
                    if (StringUtil.isEmpty(pro_flag)){
                        xmlMap.put("pro_flag","");
                    }else {
                        xmlMap.put("pro_flag",pro_flag);
                    }
                }
                if (row.getName().equalsIgnoreCase("pro_band")){
                    //得到指定节点的值
                    String pro_band=row.getStringValue();
                    if (StringUtil.isEmpty(pro_band)){
                        xmlMap.put("pro_band","");
                    }else {
                        xmlMap.put("pro_band",pro_band);
                    }
                }
                if (row.getName().equalsIgnoreCase("length_number")){
                    //得到指定节点的值
                    String length_number=row.getStringValue();
                    if (StringUtil.isEmpty(length_number)){
                        xmlMap.put("length_number","");
                    }else {
                        xmlMap.put("length_number",length_number);
                    }
                }
                if (row.getName().equalsIgnoreCase("width_number")){
                    //得到指定节点的值
                    String width_number=row.getStringValue();
                    if (StringUtil.isEmpty(width_number)){
                        xmlMap.put("width_number","");
                    }else {
                        xmlMap.put("width_number",width_number);
                    }
                }
                if (row.getName().equalsIgnoreCase("high_number")){
                    //得到指定节点的值
                    String high_number=row.getStringValue();
                    if (StringUtil.isEmpty(high_number)){
                        xmlMap.put("high_number","");
                    }else {
                        xmlMap.put("high_number",high_number);
                    }
                }
                if (row.getName().equalsIgnoreCase("gross_weight_number")){
                    //得到指定节点的值
                    String gross_weight_number=row.getStringValue();
                    if (StringUtil.isEmpty(gross_weight_number)){
                        xmlMap.put("primary_uom","");
                    }else {
                        xmlMap.put("gross_weight_number",gross_weight_number);
                    }
                }
                if (row.getName().equalsIgnoreCase("weight_unit")){
                    //得到指定节点的值
                    String weight_unit=row.getStringValue();
                    if (StringUtil.isEmpty(weight_unit)){
                        xmlMap.put("weight_unit","");
                    }else {
                        xmlMap.put("weight_unit",weight_unit);
                    }
                }
                if (row.getName().equalsIgnoreCase("measure_unit")){
                    //得到指定节点的值
                    String measure_unit=row.getStringValue();
                    if (StringUtil.isEmpty(measure_unit)){
                        xmlMap.put("measure_unit","");
                    }else {
                        xmlMap.put("measure_unit",measure_unit);
                    }
                }
                if (row.getName().equalsIgnoreCase("send_unit")){
                    //得到指定节点的值
                    String send_unit=row.getStringValue();
                    if (StringUtil.isEmpty(send_unit)){
                        xmlMap.put("send_unit","");
                    }else {
                        xmlMap.put("send_unit",send_unit);
                    }
                }
                if (row.getName().equalsIgnoreCase("spec")){
                    //得到指定节点的值
                    String spec=row.getStringValue();
                    if (StringUtil.isEmpty(spec)){
                        xmlMap.put("spec","");
                    }else {
                        xmlMap.put("spec",spec);
                    }
                }
                if (row.getName().equalsIgnoreCase("con_code")){
                    //得到指定节点的值
                    String con_code=row.getStringValue();
                    if (StringUtil.isEmpty(con_code)){
                        xmlMap.put("con_code","");
                    }else {
                        xmlMap.put("con_code",con_code);
                    }
                }
                if (row.getName().equalsIgnoreCase("type_set")){
                    //得到指定节点的值
                    String type_set=row.getStringValue();
                    if (StringUtil.isEmpty(type_set)){
                        xmlMap.put("type_set","");
                    }else {
                        xmlMap.put("type_set",type_set);
                    }
                }
                if (row.getName().equalsIgnoreCase("product_char1")){
                    //得到指定节点的值
                    String product_char1=row.getStringValue();
                    if (StringUtil.isEmpty(product_char1)){
                        xmlMap.put("product_char1","");
                    }else {
                        xmlMap.put("product_char1",product_char1);
                    }
                }
                if (row.getName().equalsIgnoreCase("product_char4")){
                    //得到指定节点的值
                    String product_char4=row.getStringValue();
                    if (StringUtil.isEmpty(product_char4)){
                        xmlMap.put("product_char4","");
                    }else {
                        xmlMap.put("product_char4",product_char4);
                    }
                }
                if (row.getName().equalsIgnoreCase("product_char5")){
                    //得到指定节点的值
                    String product_char5=row.getStringValue();
                    if (StringUtil.isEmpty(product_char5)){
                        xmlMap.put("product_char5","");
                    }else {
                        xmlMap.put("product_char5",product_char5);
                    }
                }
                if (row.getName().equalsIgnoreCase("sale_ser1")){
                    //得到指定节点的值
                    String sale_ser1=row.getStringValue();
                    if (StringUtil.isEmpty(sale_ser1)){
                        xmlMap.put("sale_ser1","");
                    }else {
                        xmlMap.put("sale_ser1",sale_ser1);
                    }
                }
                if (row.getName().equalsIgnoreCase("sale_ser2")){
                    //得到指定节点的值
                    String sale_ser2=row.getStringValue();
                    if (StringUtil.isEmpty(sale_ser2)){
                        xmlMap.put("sale_ser2","");
                    }else {
                        xmlMap.put("sale_ser2",sale_ser2);
                    }
                }
                if (row.getName().equalsIgnoreCase("sale_ser3")){
                    //得到指定节点的值
                    String sale_ser3=row.getStringValue();
                    if (StringUtil.isEmpty(sale_ser3)){
                        xmlMap.put("sale_ser3","");
                    }else {
                        xmlMap.put("sale_ser3",sale_ser3);
                    }
                }
                if (row.getName().equalsIgnoreCase("product_list")){
                    //得到指定节点的值
                    String product_list=row.getStringValue();
                    if (StringUtil.isEmpty(product_list)){
                        xmlMap.put("product_list","");
                    }else {
                        xmlMap.put("product_list",product_list);
                    }
                }
                if (row.getName().equalsIgnoreCase("sale_flag")){
                    //得到指定节点的值
                    String sale_flag=row.getStringValue();
                    if (StringUtil.isEmpty(sale_flag)){
                        xmlMap.put("sale_flag","");
                    }else {
                        xmlMap.put("sale_flag",sale_flag);
                    }
                }
                if (row.getName().equalsIgnoreCase("default_plant_code")){
                    //得到指定节点的值
                    String default_plant_code=row.getStringValue();
                    if (StringUtil.isEmpty(default_plant_code)){
                        xmlMap.put("default_plant_code","");
                    }else {
                        xmlMap.put("default_plant_code",default_plant_code);
                    }
                }
                if (row.getName().equalsIgnoreCase("last_upd")){
                    //得到指定节点的值
                    String last_upd=row.getStringValue();
                    if (StringUtil.isEmpty(last_upd)){
                        xmlMap.put("last_upd","");
                    }else {
                        xmlMap.put("last_upd",last_upd);
                    }
                }
                if (row.getName().equalsIgnoreCase("is_crust_flag")){
                    //得到指定节点的值
                    String is_crust_flag=row.getStringValue();
                    if (StringUtil.isEmpty(is_crust_flag)){
                        xmlMap.put("is_crust_flag","");
                    }else {
                        xmlMap.put("is_crust_flag",is_crust_flag);
                    }
                }
                if (row.getName().equalsIgnoreCase("product_land")){
                    //得到指定节点的值
                    String product_land=row.getStringValue();
                    if (StringUtil.isEmpty(product_land)){
                        xmlMap.put("product_land","");
                    }else {
                        xmlMap.put("product_land",product_land);
                    }
                }
                if (row.getName().equalsIgnoreCase("test_sales_flag")){
                    //得到指定节点的值
                    String test_sales_flag=row.getStringValue();
                    if (StringUtil.isEmpty(test_sales_flag)){
                        xmlMap.put("test_sales_flag","");
                    }else {
                        xmlMap.put("test_sales_flag",test_sales_flag);
                    }
                }
                if (row.getName().equalsIgnoreCase("test_sales_qty")){
                    //得到指定节点的值
                    String test_sales_qty=row.getStringValue();
                    if (StringUtil.isEmpty(test_sales_qty)){
                        xmlMap.put("test_sales_qty","");
                    }else {
                        xmlMap.put("test_sales_qty",test_sales_qty);
                    }
                }
                if (row.getName().equalsIgnoreCase("rebate_rate")){
                    //得到指定节点的值
                    String rebate_rate=row.getStringValue();
                    if (StringUtil.isEmpty(rebate_rate)){
                        xmlMap.put("rebate_rate","");
                    }else {
                        xmlMap.put("rebate_rate",rebate_rate);
                    }
                }
                if (row.getName().equalsIgnoreCase("categories_first")){
                    //得到指定节点的值
                    String categories_first=row.getStringValue();
                    if (StringUtil.isEmpty(categories_first)){
                        xmlMap.put("categories_first","");
                    }else {
                        xmlMap.put("categories_first",categories_first);
                    }
                }
                if (row.getName().equalsIgnoreCase("categories_mid")){
                    //得到指定节点的值
                    String categories_mid=row.getStringValue();
                    if (StringUtil.isEmpty(categories_mid)){
                        xmlMap.put("categories_mid","");
                    }else {
                        xmlMap.put("categories_mid",categories_mid);
                    }
                }
                if (row.getName().equalsIgnoreCase("platform")){
                    //得到指定节点的值
                    String platform=row.getStringValue();
                    if (StringUtil.isEmpty(platform)){
                        xmlMap.put("platform","");
                    }else {
                        xmlMap.put("platform",platform);
                    }
                }
                if (row.getName().equalsIgnoreCase("series")){
                    //得到指定节点的值
                    String series=row.getStringValue();
                    if (StringUtil.isEmpty(series)){
                        xmlMap.put("series","");
                    }else {
                        xmlMap.put("series",series);
                    }
                }
                if (row.getName().equalsIgnoreCase("item")){
                    //得到指定节点的值
                    String item=row.getStringValue();
                    if (StringUtil.isEmpty(item)){
                        xmlMap.put("item","");
                    }else {
                        xmlMap.put("item",item);
                    }
                }
                if (row.getName().equalsIgnoreCase("product_type")){
                    //得到指定节点的值
                    String product_type=row.getStringValue();
                    if (StringUtil.isEmpty(product_type)){
                        xmlMap.put("product_type","");
                    }else {
                        xmlMap.put("product_type",product_type);
                    }
                }
                if (row.getName().equalsIgnoreCase("demonstrator")){
                    //得到指定节点的值
                    String demonstrator=row.getStringValue();
                    if (StringUtil.isEmpty(demonstrator)){
                        xmlMap.put("demonstrator","");
                    }else {
                        xmlMap.put("demonstrator",demonstrator);
                    }
                }
                if (row.getName().equalsIgnoreCase("HIGH_END_PRODUCTS")){
                    //得到指定节点的值
                    String HIGH_END_PRODUCTS=row.getStringValue();
                    if (StringUtil.isEmpty(HIGH_END_PRODUCTS)){
                        xmlMap.put("HIGH_END_PRODUCTS","");
                    }else {
                        xmlMap.put("HIGH_END_PRODUCTS",HIGH_END_PRODUCTS);
                    }
                }
                if (row.getName().equalsIgnoreCase("dp_date_plan")){
                    //得到指定节点的值
                    String dp_date_plan=row.getStringValue();
                    if (StringUtil.isEmpty(dp_date_plan)){
                        xmlMap.put("dp_date_plan","");
                    }else {
                        xmlMap.put("dp_date_plan",dp_date_plan);
                    }
                }
                if (row.getName().equalsIgnoreCase("dp_date_actual")){
                    //得到指定节点的值
                    String dp_date_actual=row.getStringValue();
                    if (StringUtil.isEmpty(dp_date_actual)){
                        xmlMap.put("dp_date_actual","");
                    }else {
                        xmlMap.put("dp_date_actual",dp_date_actual);
                    }
                }
                if (row.getName().equalsIgnoreCase("pa_date_plan")){
                    //得到指定节点的值
                    String pa_date_plan=row.getStringValue();
                    if (StringUtil.isEmpty(pa_date_plan)){
                        xmlMap.put("pa_date_plan","");
                    }else {
                        xmlMap.put("pa_date_plan",pa_date_plan);
                    }
                }
                if (row.getName().equalsIgnoreCase("pa_date_actual")){
                    //得到指定节点的值
                    String pa_date_actual=row.getStringValue();
                    if (StringUtil.isEmpty(pa_date_actual)){
                        xmlMap.put("pa_date_actual","");
                    }else {
                        xmlMap.put("pa_date_actual",pa_date_actual);
                    }
                }
                if (row.getName().equalsIgnoreCase("pe_date_plan")){
                    //得到指定节点的值
                    String pe_date_plan=row.getStringValue();
                    if (StringUtil.isEmpty(pe_date_plan)){
                        xmlMap.put("pe_date_plan","");
                    }else {
                        xmlMap.put("pe_date_plan",pe_date_plan);
                    }
                }
                if (row.getName().equalsIgnoreCase("pe_date_actual")){
                    //得到指定节点的值
                    String pe_date_actual=row.getStringValue();
                    if (StringUtil.isEmpty(pe_date_actual)){
                        xmlMap.put("pe_date_actual","");
                    }else {
                        xmlMap.put("pe_date_actual",pe_date_actual);
                    }
                }
                if (row.getName().equalsIgnoreCase("em_date_plan")){
                    //得到指定节点的值
                    String em_date_plan=row.getStringValue();
                    if (StringUtil.isEmpty(em_date_plan)){
                        xmlMap.put("em_date_plan","");
                    }else {
                        xmlMap.put("em_date_plan",em_date_plan);
                    }
                }
                if (row.getName().equalsIgnoreCase("em_date_actual")){
                    //得到指定节点的值
                    String em_date_actual=row.getStringValue();
                    if (StringUtil.isEmpty(em_date_actual)){
                        xmlMap.put("em_date_actual","");
                    }else {
                        xmlMap.put("em_date_actual",em_date_actual);
                    }
                }
                if (row.getName().equalsIgnoreCase("es_date_plan")){
                    //得到指定节点的值
                    String es_date_plan=row.getStringValue();
                    if (StringUtil.isEmpty(es_date_plan)){
                        xmlMap.put("es_date_plan","");
                    }else {
                        xmlMap.put("es_date_plan",es_date_plan);
                    }
                }
                if (row.getName().equalsIgnoreCase("es_date_actual")){
                    //得到指定节点的值
                    String es_date_actual=row.getStringValue();
                    if (StringUtil.isEmpty(es_date_actual)){
                        xmlMap.put("es_date_actual","");
                    }else {
                        xmlMap.put("es_date_actual",es_date_actual);
                    }
                }
                if (row.getName().equalsIgnoreCase("hr_index")){
                    //得到指定节点的值
                    String hr_index=row.getStringValue();
                    if (StringUtil.isEmpty(hr_index)){
                        xmlMap.put("hr_index","");
                    }else {
                        xmlMap.put("hr_index",hr_index);
                    }
                }

            }
            maps.add(xmlMap);
        }
        return maps;
    }
}
