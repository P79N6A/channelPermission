package com.haier.svc.model;

import com.google.gson.Gson;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.APIResult;
import com.haier.eis.model.BusinessException;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.stock.model.InvStockAge;
import com.haier.stock.service.InvStockAgeService;
import com.haier.svc.purchase.itemattribute.Output;
import com.haier.svc.purchase.selectmdminfofrommdmbyconditon.InputsType;
import com.haier.svc.purchase.selectmdminfofrommdmbyconditon.OutputsType;
import com.haier.svc.purchase.selectmdminfofrommdmbyconditon.SelectMDMInfoFromMDMByConditon;
import com.haier.svc.purchase.selectmdminfofrommdmbyconditon.SelectMDMInfoFromMDMByConditon_Service;
import com.haier.svc.service.ItemService;
import com.haier.svc.util.Constants;
import com.haier.svc.util.ICStreamReaderDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("eaiHandlerModel")
public class EaiHandlerModel {
    private static org.apache.log4j.Logger log                   = org.apache.log4j.LogManager
            .getLogger(EaiHandlerModel.class);
    @Value("${t2OrderResponse.location}")
    private String						   wsdlLocation;

    @Autowired
    private ItemService itemService;


    @Autowired
    private ItemModel itemModel;

    /**
     * 根据SKU从MDM获取物料基本信息
     * @param skus
     * @return
     */
    public List<ItemBase> getMtlsBySkus(List<String> skus) {
        try {
            if (skus == null || skus.size() == 0)
                return null;
            List<ItemBase> ret = new ArrayList<ItemBase>();
            for (String sku : skus) {
                EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
                try {
                    InputsType input = this.constructMdmCondition(
                            Constants.EAI_PARAM_DEP_FOR_ITEM_BASE,
                            Constants.EAI_PARAM_TABLE_FOR_ITEM_BASE, sku);
                    dataLog.setForeignKey(sku);
                    dataLog
                            .setInterfaceCode(EisInterfaceDataLog.INTERFACE_CODE_GET_SKU_INFO_FROM_MDM);
                    dataLog.setRequestData(new Gson().toJson(input));
                    dataLog.setRequestTime(new Date());
                    Long startTime = System.currentTimeMillis();

                    OutputsType output = null;
                    try {
                        output = this.selectMdmInfoFromMdmByConditonOutput(input);
                    } catch (Exception e) {
                        log.error("根据SKU从MDM获取物料基本信息时发生未知异常", e);
                        dataLog.setErrorMessage("未知异常：" + e.getMessage());
                    }

                    Gson gson = new Gson();
                    String outputStr = gson.toJson(output);
                    dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
                    dataLog.setResponseData(outputStr);
                    dataLog.setResponseTime(new Date());

                    if (output == null) {
                        dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
                        throw new RuntimeException("根据SKU从MDM获取物料基本信息时发生未知异常");
                    } else {
                        dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
                    }

                    APIResult apiRet = gson.fromJson(outputStr, APIResult.class);
                    List<ItemBase> targetList = this.getTargetList(apiRet.getOut(), ItemBase.class);
                    if (targetList != null && targetList.size() > 0) {
                        ret.add(targetList.get(0));
                    }
                }  catch (Exception ex) {
                        log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex);

                }
            }
            return ret;
        } catch (Exception e) {
            log.error("根据SKU从MDM获取物料基本信息时发生未知异常", e);
            throw new RuntimeException("根据SKU从MDM获取物料基本信息时发生未知异常");
        }
    }

    private InputsType constructMdmCondition(String department, String tableName, String condition) {
        InputsType input = new InputsType();
        input.setDepartment(department);
        input.setTableName(tableName);
        input.setCondition("MATERIAL_CODE = '" + condition + "'");
        return input;
    }

    private com.haier.svc.purchase.selectmdminfofrommdmbyconditon.OutputsType selectMdmInfoFromMdmByConditonOutput(InputsType input)
            throws MalformedURLException {

        URL url = this.getClass().getResource(
                wsdlLocation + "/SelectMDMInfoFromMDMByConditon.wsdl");
        SelectMDMInfoFromMDMByConditon_Service service = new SelectMDMInfoFromMDMByConditon_Service(
                url);

        SelectMDMInfoFromMDMByConditon soap = service.getSelectMDMInfoFromMDMByConditonSOAP();
        return soap.selectMDMInfoFromMDMByConditonOP(input);
    }

    @SuppressWarnings("unchecked")
    private <E> List<E> getTargetList(String xml, Class<E> clazz) throws Exception {
        StringReader sr = null;
        try {
            if (StringUtil.isEmpty(xml, true))
                return null;
            JAXBContext jc = null;
            if (ItemAttribute.class.equals(clazz)) {
                jc = JAXBContext.newInstance(Output.class);
            } else if (ItemBase.class.equals(clazz)) {
                jc = JAXBContext.newInstance(com.haier.svc.purchase.itembase.Output.class);
            }
            if (jc==null){
                return null;
            }
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            xml = xml.replaceAll("\u003c", "<").replaceAll("\u003e", ">");
            sr = new StringReader(xml);

            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLStreamReader xsr = xif.createXMLStreamReader(sr);
            xsr = new ICStreamReaderDelegate(xsr);

            if (ItemAttribute.class.equals(clazz)) {
                Output out = (Output) unmarshaller.unmarshal(xsr);
                if (out != null && out.getRowset() != null) {
                    return (List<E>) out.getRowset().getItemAttributes();
                }
            } else if (ItemBase.class.equals(clazz)) {
                com.haier.svc.purchase.itembase.Output out = (com.haier.svc.purchase.itembase.Output) unmarshaller
                        .unmarshal(xsr);
                if (out != null && out.getRowset() != null) {
                    return (List<E>) out.getRowset().getItemBases();
                }
            }
        } catch (Exception e) {
            log.error("将从MDM获取的" + clazz.getSimpleName() + "信息结构化时发生未知异常", e);
            throw e;
        }finally {
            if(sr!=null){
                sr.close();
            }
        }
        return null;
    }


    /**
     * 更新信息不全的物料基本信息和库龄表的物料信息
     */
    public void updateMtlInfoBySku() {
        List<ItemBase> mtls = itemService.getIncompleteItemBaseList();
        if (mtls.size()==0) {
            throw new RuntimeException("通过itemService取得信息不完整的物料基本信息时发生异常：" );
        }
        List<String> skus = this.getIncompleteMtlSkus(mtls);
        mtls = this.getMtlsBySkus(skus);
        if (mtls == null || mtls.size() == 0) {
            return;
        }
        for (ItemBase mtl : mtls) {
            Integer stockRet = this.updateMtlInfoForStockAge(mtl);
            if (stockRet==0) {
                log.error("更新SkU为" + mtl.getMaterialCode()
                        + "的库龄表的物料信息(商品名称，品类，产品组，品牌)时发生异常,下次执行Job时会重新执行该操作");
                continue;
            }
            try{
                itemModel.saveItemBase(mtl);
            }catch (Exception e){
                log.error("更新SkU为" + mtl.getMaterialCode() + "的物料基本信息时发生异常,下次执行Job时会重新执行该操作");

            }

        }
    }

    /**
     * 更新库齡表的物料基本信息(商品名称，品类，产品组，品牌)
     *
     * @param base 产品信息
     */
    public Integer updateMtlInfoForStockAge(ItemBase base) {

        try {
            InvStockAge stockAge = new InvStockAge();
            stockAge.setSku(base.getMaterialCode());
            this.setDefaultMtlInfoForStockAge(stockAge);
            this.setMtlInfoForStockAge(stockAge, base);
            int cnt = itemService.updateMtlInfoForStockAge(stockAge);
            return cnt;
        } catch (Exception e) {
            log.error("更新库齡表的物料基本信息(商品名称，品类，产品组，品牌)时出现未知异常：", e);
            throw new RuntimeException("更新库齡表的物料基本信息(商品名称，品类，产品组，品牌)时出现未知异常");
        }
    }

    private void setMtlInfoForStockAge(InvStockAge stockAge, ItemBase mtl) {
        stockAge.setProductName(mtl.getMaterialDescription());
        if (!StringUtil.isEmpty(mtl.getDepartment(), true)) {
            ItemAttribute department = this.getHmValueSetByValueSetIdAndValue("ProductGroup",
                    mtl.getDepartment());
            if (department != null) {
                stockAge.setProductTypeName(department.getCbsCategory());
                stockAge.setProductGroupName(department.getValueMeaning());
            }
        }
        if (!StringUtil.isEmpty(mtl.getProBand(), true)) {
            ItemAttribute brand = this.getHmValueSetByValueSetIdAndValue("Brand", mtl.getProBand());
            if (brand != null) {
                stockAge.setBrand(brand.getValueMeaning());
            }
        }
    }
    private ItemAttribute getHmValueSetByValueSetIdAndValue(String valueSetId, String value) {
        ServiceResult<ItemAttribute> rs = itemService
                .getItemAttributeByValueSetIdAndValue(valueSetId, value);
        if (!rs.getSuccess())
            throw new BusinessException("向ItemService请求HmValueSet数据时发生错误：" + rs.getMessage());
        return rs.getResult();
    }
    private void setDefaultMtlInfoForStockAge(InvStockAge stockAge) {
        stockAge.setProductName("");
        stockAge.setProductTypeName("");
        stockAge.setBrand("");
        stockAge.setProductGroupName("");
    }

    private List<String> getIncompleteMtlSkus(List<ItemBase> mtls) {
        List<String> skus = null;
        if (mtls != null && mtls.size() > 0) {
            skus = new ArrayList<String>();
            for (ItemBase mtl : mtls) {
                skus.add(mtl.getMaterialCode());
            }
        }
        return skus;
    }
}
