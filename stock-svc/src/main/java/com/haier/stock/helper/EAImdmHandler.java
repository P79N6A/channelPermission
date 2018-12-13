package com.haier.stock.helper;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.APIResult;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.service.EisInterfaceDataLogService;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.stock.model.itemattribute.Output;
import com.haier.stock.model.selectinfofrommdm.InType;
import com.haier.stock.model.selectinfofrommdm.SelectInfoFromMDM;
import com.haier.stock.model.selectinfofrommdm.SelectInfoFromMDM_Service;
import com.haier.stock.model.selectmdminfofrommdmbyconditon.InputsType;
import com.haier.stock.model.selectmdminfofrommdmbyconditon.OutputsType;
import com.haier.stock.model.selectmdminfofrommdmbyconditon.SelectMDMInfoFromMDMByConditon;
import com.haier.stock.model.selectmdminfofrommdmbyconditon.SelectMDMInfoFromMDMByConditon_Service;
import com.haier.stock.util.Constants;
import com.haier.stock.util.HelpUtils;
import com.haier.stock.util.ICStreamReaderDelegate;

@Component
public class EAImdmHandler {
	
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(EAImdmHandler.class);
	@Autowired
	private HelpUtils help;
	@Autowired
    private EisInterfaceDataLogService eisInterfaceDataLogService;
	
	/**
     * 从MDM获取所有符合条件的产品属性/物料基本信息
     * @param beginTime
     * @param endTime
     * @return list of HmValueSet
     */
    public <E> List<E> getTargetListFromMdm(Date beginTime, Date endTime, Class<E> clazz) {
        List<E> ret = new ArrayList<E>();
        log.info("从MDM获取" + clazz.getName() + "（beginTime:" + beginTime.toString() + ",endTime:"
                 + endTime.toString() + "）");
        this.getTargetListFromMdm(1, beginTime, endTime, ret, clazz);
        return ret;
    }
    
    /**
     * 从MDM获取产品属性/物料基本信息
     * @param pageIndex
     * @param beginTime
     * @param endTime
     * @param ret
     */
    private <E> void getTargetListFromMdm(Integer pageIndex, Date beginTime, Date endTime,
                                          List<E> ret, Class<E> clazz) {

        InType intype = this.getSelectInfoFromMDMIntype(pageIndex, beginTime, endTime, clazz);

        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(pageIndex.toString());
        dataLog.setInterfaceCode(EisInterfaceDataLog.INTERFACE_CODE_GET_SKU_INFO_FROM_MDM);
        dataLog.setRequestData(new Gson().toJson(intype));
        dataLog.setRequestTime(new Date());
        Long startTime = System.currentTimeMillis();

        
        com.haier.stock.model.selectinfofrommdm.SelectInfoFromMDMOPResponse.Output output = null;
        try {
            output = this.getMdmOutput(intype);
        } catch (Exception e) {
            log.error("从MDM获取" + clazz.getSimpleName() + "信息时发生未知异常", e);
            dataLog.setErrorMessage("未知异常：" + e.getMessage());
        }

        try {
            Gson gson = new Gson();
            String outputStr = gson.toJson(output);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseData(outputStr);
            dataLog.setResponseTime(new Date());
            if (output == null) {
                dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
                throw new RuntimeException("从MDM获取" + clazz.getSimpleName() + "信息时发生未知异常");
            } else {
                dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            }

            try {
            	eisInterfaceDataLogService.insert(dataLog);
            } catch (Exception ex) {
                log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex);
            }

            APIResult apiRet = gson.fromJson(outputStr, APIResult.class);
            List<E> targetList = this.getTargetList(apiRet.getOut(), clazz);
            if (targetList != null && targetList.size() > 0) {
                ret.addAll(targetList);
            }

            if (apiRet.getCurrentPage().intValue() < apiRet.getPageCount()) {
                this.getTargetListFromMdm(apiRet.getCurrentPage() + 1, beginTime, endTime, ret,
                    clazz);
            }
        } catch (Exception e) {
            log.error("从MDM获取" + clazz.getSimpleName() + "信息时发生未知异常", e);
            throw new RuntimeException("从MDM获取" + clazz.getSimpleName() + "信息时发生未知异常");
        }
    }
    
    private <E> InType getSelectInfoFromMDMIntype(Integer pageIndex, Date beginTime, Date endTime,Class<E> clazz) {
			
    		String department = null;
			String tableName = null;
			
			if (ItemAttribute.class.equals(clazz)) {
				department = Constants.EAI_PARAM_DEP_FOR_ITEM_ATTRIBUTE;
				tableName = Constants.EAI_PARAM_TABLE_FOR_ITEM_ATTRIBUTE;
			} else if (ItemBase.class.equals(clazz)) {
				department = Constants.EAI_PARAM_DEP_FOR_ITEM_BASE;
				tableName = Constants.EAI_PARAM_TABLE_FOR_ITEM_BASE;
			}
			
			InType input = new InType();
			input.setCurrentPage(pageIndex.toString());
			input.setDepartment(department);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			input.setEndTime(sdf.format(endTime));
			input.setStartTime(sdf.format(beginTime));
			input.setTableName(tableName);
			return input;
			
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
                jc = JAXBContext.newInstance(com.haier.stock.model.itembase.Output.class);
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
            	com.haier.stock.model.itembase.Output out = (com.haier.stock.model.itembase.Output) unmarshaller.unmarshal(xsr);
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
     * 访问接口
     * @param input
     * @return
     * @throws MalformedURLException
     */
    private <E> com.haier.stock.model.selectinfofrommdm.SelectInfoFromMDMOPResponse.Output getMdmOutput(InType input) throws MalformedURLException {
        URL url = this.getWsdlPath("SelectInfoFromMDM.wsdl");
        SelectInfoFromMDM_Service service = new SelectInfoFromMDM_Service(url);
        SelectInfoFromMDM soap = service.getSelectInfoFromMDMSOAP();
        return soap.selectInfoFromMDMOP(input);
    }
    
    /**
     * 获取wsdl file路径
     * @param wsdlFile
     * @return
     */
    private URL getWsdlPath(String wsdlFile) {
        return help.getWSDLURL(wsdlFile);
    }
    
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
                } finally {
                    try {
                    	eisInterfaceDataLogService.insert(dataLog);
                    } catch (Exception ex) {
                        log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex);
                    }
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
    
    private com.haier.stock.model.selectmdminfofrommdmbyconditon.OutputsType selectMdmInfoFromMdmByConditonOutput(InputsType input)
            throws MalformedURLException {

		URL url = this.getWsdlPath("SelectMDMInfoFromMDMByConditon.wsdl");
		SelectMDMInfoFromMDMByConditon_Service service = new SelectMDMInfoFromMDMByConditon_Service(
		url);
		
		SelectMDMInfoFromMDMByConditon soap = service.getSelectMDMInfoFromMDMByConditonSOAP();
		return soap.selectMDMInfoFromMDMByConditonOP(input);
	}

}
