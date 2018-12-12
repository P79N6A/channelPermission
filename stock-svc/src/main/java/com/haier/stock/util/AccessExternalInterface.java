package com.haier.stock.util;

import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.haier.shop.model.CancelInputType;
import com.haier.shop.model.InputTypes;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.VomInterData;

/**
 * 访问外部接口类
 *                       
 * @Filename: AccessExternalInterface.java
 * @Version: 1.0
 * @Author: weiyunjun
 * @Email: weiyunjun@ehaier.com
 *
 */
@Service
public class AccessExternalInterface {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
        .getLogger(AccessExternalInterface.class);

    private String keyvalue;
    private String aes;
    private String o2oKey   = "dfwobswqdvds"; //O2O传结算中心，密钥串，上线会修改
    private String o2oAppId = "CBS";          //O2O传结算中心，调用方名称

    /**
     * 生成开提单参数，les要求的参数加密格式
     * @param content 数据内容
     * @throws Exception
     */
    public String orderToLesParam(String content, VomInterData vomInterData) {
        //外网测试地址：http://58.56.128.84:9001/EAI/service/VOM/CommonGetWayToVOM/CommonGetWayToVOM
        //内网测试地址：http://10.135.17.72:10101/EAI/service/VOM/CommonGetWayToVOM/CommonGetWayToVOM
        //线上地址：http://58.56.128.10:19001/EAI/RoutingProxyService/EAI_REST_POST_ServiceRoot?INT_CODE=EAI_INT_1353
        if (content == null || content.trim().equals("")) {
            log.error("content参数为空");
            return null;
        }
        if (vomInterData == null || vomInterData.getNotifyid().equals("")) {
            log.error("VomInterData参数为空");
            return null;
        }

        keyvalue = keyvalue == null || keyvalue.equals("") ? "RRS,123" : keyvalue;
        aes = aes == null || aes.equals("") ? "KeLy8g7qjmnbgWP1" : aes;
        try {
            VomInterData stgVomData = new VomInterData();
            stgVomData.setNotifyid(vomInterData.getNotifyid());//网单id作为消息id
            stgVomData.setNotifytime(vomInterData.getNotifytime());
            String s1 = AESUtil.Encrypt(content, aes);
            //        System.out.println(s1);
            //        System.out.println(AESUtil.Decrypt(s1, sKey));
            s1 = URLEncoder.encode(s1, "utf-8");
            //        System.out.println(s1);
            stgVomData.setContent(s1);
            stgVomData.setSign(Md5.getMd5(content + keyvalue));
            stgVomData.setButype("rrs_order");
            stgVomData.setSource("HAIERSC");
            stgVomData.setType("xml");
            List<VomInterData> list = new ArrayList<VomInterData>();
            list.add(stgVomData);
            return ObjectUtil.getObjectToURLString(list);

        } catch (Exception e) {
            log.error("调用les接口异常:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成centent的xml格式数据
     * @return
     */
    public String getContentXml(InputTypes input) {
        StringBuffer sb = new StringBuffer();
        sb.append("<Order>");

        if (input == null) {
            sb.append("</Order>");
            return sb.toString();
        }
        List<?> imsList = input.getIMSLIST();

        sb.append("<orderno>" + input.getBSTKD() + "</orderno>");//网单号
        sb.append("<sourcesn>" + input.getSOURCESN() + "</sourcesn>");//订单号
        sb.append("<ordertype>2</ordertype>");//订单类型：1.采购入库 2.销售出库 3.退货入库 4.取件 5.普通出库（自提）6.调拨 7.第三方运输订单  8客户调货 9.客户调货入库 10.网点取货 11.拒收入库
        sb.append("<bustype>" + input.getSDABW() + "</bustype>");//业务类型：入库订单：1 送货到仓库 70 提货 ;出库订单：1 自提2 网点 70 直配 调货：1.送货到HUB库 2.HUB库自提
        sb.append("<expno></expno>");//快递单号：自动分配的快递单号或客户生成的快递单号    --不填
        String orderdate = "";
        if (input.getAUDAT() != null && !input.getAUDAT().equals("")
            && !input.getAUDAT().equals("null")) {
            orderdate = input.getAUDAT() + " ";
        }
        if (input.getAUTIM() != null && !input.getAUTIM().equals("")
            && !input.getAUTIM().equals("null")) {//如果日期为空，只传时间
            orderdate = orderdate + input.getAUTIM();
        }
        if (orderdate == null || orderdate.trim().equals("")) {
            orderdate = "";
        }
        sb.append("<orderdate>" + orderdate + "</orderdate>");//订单时间（格式：YYYY-MM-DD HH:MM:SS）
        sb.append("<storecode><![CDATA[" + input.getCENTERCODE() + "]]></storecode>");//scode转  中心代码     仓库编码：按日日顺C码
        sb.append("<province><![CDATA[" + input.getPROV() + "]]></province>");//收货人所在省
        sb.append("<city><![CDATA[" + input.getCITY() + "]]></city>");//收货人所在市
        sb.append("<county><![CDATA[" + input.getCOUNTY() + "]]></county>");//收货人所在县/区
        sb.append("<addr><![CDATA[" + input.getADDRESS() + "]]></addr>");//详细地址
        sb.append("<gbcode>" + input.getGBCODE() + "</gbcode>");
        sb.append("<name><![CDATA[" + input.getSHRXM() + "]]></name>");//送达方姓名：收货人姓名
        sb.append("<mobile>" + input.getSHRMOB() + "</mobile>");//联系电话
        sb.append("<tel><![CDATA[" + input.getSHRTEL() + "]]></tel>");//固定电话
        sb.append("<postcode>" + input.getPSTLZ() + "</postcode>");//邮政编码
        String deldate = "";
        if (input.getYDDAT() != null && !input.getYDDAT().equals("")
            && !input.getYDDAT().equals("null")) {
            deldate = input.getYDDAT() + " ";
        }
        if (input.getYDTIME() != null && !input.getYDTIME().equals("")
            && !input.getYDTIME().equals("null")) {//如果日期为空，只传时间
            deldate = deldate + input.getYDTIME();
        }
        if (deldate == null || deldate.trim().equals("")) {
            deldate = "";
        }
        sb.append("<deldate>" + deldate.trim() + "</deldate>");//预约时间：预约送货时间
        sb.append("<insdate></insdate>");//预约时间：预约安装时间    --不填
        sb.append("<reorder></reorder>");//前续订单号：关联单号    --不填
        sb.append("<freight>" + input.getSHIPCO() + "</freight>");//运费
        sb.append("<billsum>" + input.getKWERZ() + "</billsum>");//订单总金额
        if (input.getPAYSTE() != null && input.getPAYSTE().equals("P2")) {//如果是货到付款，有代收欠款
            sb.append("<billowe>" + input.getKWERZ() + "</billowe>");
        } else {
            sb.append("<billowe>0</billowe>");//代收金额：应收欠款
        }
        sb.append("<paystate>" + input.getPAYSTE() + "</paystate>");//付款状态：P1-已付款，P2-代收货款
        String paytime = "";
        //        if (input.getAUDAT() != null && !input.getAUDAT().equals("")
        //            && !input.getAUDAT().equals("null")) {
        //            paytime = input.getAUDAT() + " ";
        //        }
        //        if (input.getAUTIM() != null && !input.getAUTIM().equals("")
        //            && !input.getAUTIM().equals("null")) {//如果日期为空，只传时间
        //            paytime = paytime + input.getAUTIM();
        //        }
        if (input.getADD1() != null && !input.getADD1().equals("")
            && !input.getADD1().equals("null")) {
            paytime = input.getADD1();
        }
        sb.append("<paytime>" + paytime + "</paytime>");//付款时间   ---订单为cod时付款时间为空
        sb.append("<paytype>" + input.getPAYTYP() + "</paytype>");//支付类别（如支付宝、现金、银联等) 
        sb.append("<isinv>" + input.getINVO() + "</isinv>");//是否需要开具发票：1.是 2.否
        sb.append("<invtype>" + input.getINVTYP() + "</invtype>");//发票类型
        sb.append("<invrise><![CDATA[" + input.getINVACC() + "]]></invrise>");//发票抬头
        sb.append("<taxbearer>" + input.getINVNUM() + "</taxbearer>");//纳税人登记号
        sb.append("<recaddr><![CDATA[" + input.getINVADD() + "]]></recaddr>");//发票地址
        sb.append("<recacc>" + input.getINVCARDNUMBER() + "</recacc>");//发票开户行帐号
        sb.append("<recbank><![CDATA[" + input.getINVBANK() + "]]></recbank>");//发票开户行
        sb.append("<sname>海尔电商</sname>");//发货人姓名
        sb.append("<sprovince>山东省</sprovince>");//发货人所在省
        sb.append("<scity>青岛市</scity>");//发货人所在市
        sb.append("<scounty>崂山区</scounty>");//发货人所在县/区
        sb.append("<saddr>山东省青岛市崂山区海尔路1号海尔工业园</saddr>");//发货人详细地址
        sb.append("<smobile></smobile>");//发货人联系电话    --不填
        sb.append("<stel></stel>");//发货人固定电话    --不填
        sb.append("<busflag>1</busflag>");//订单标记 1送装一体 2.只配送 3.开箱验货
        sb.append("<remark><![CDATA[" + input.getSDMEMO() + "]]></remark>");//备注
        sb.append("<attributes><shcode><![CDATA[" + input.getKUNWE() + "]]></shcode></attributes>");//属性备注    <shcode>86码或5码<shcode>  --70直配，需要传5码
        String urlab = input.getURLAB();
        if (urlab != null && !urlab.trim().equals("")) {
            urlab = "," + urlab;
        } else {
            urlab = "";
        }
        sb.append("<remark1><![CDATA[" + input.getMATNR() + urlab + "]]></remark1>");//备用字段    --F1全款订单，F2尾款订单，F3超时免单订单，F4开箱验货（多个逗号隔开）
        sb.append("<remark2><![CDATA[" + input.getCOMTYP() + "]]></remark2>");//备用字段    --品牌
        sb.append("<remark3><![CDATA[" + input.getSOURCEEXT() + "]]></remark3>");//备用字段    --订单来源
        sb.append("<remark4></remark4>");//备用字段    --不填
        //        String tailPayTime = "";//尾款时间
        //        if (input.getADD2() != null && !input.getADD2().equals("")
        //            && !input.getADD2().equals("null")) {
        //            tailPayTime = input.getADD2();
        //        }
        sb.append("<remark5></remark5>");//备用字段    --不填
        sb.append("<remark6></remark6>");//备用字段    --不填
        sb.append("<remark7></remark7>");//备用字段    --不填
        sb.append("<remark8></remark8>");//备用字段    --不填
        sb.append("<items>");
        if (imsList != null && imsList.size() > 0) {
            for (int i = 0; i < imsList.size(); i++) {
                InvMachineSet ims = (InvMachineSet) imsList.get(i);
                sb.append("<Item>");//stock服务，main_sku=sku and status=0   stock.inv_machine_set表查询到多条记录    
                //                sb.append("<itemno>" + ims.getPosnr() + "</itemno>");//行号：订单中有多行物料时，物料所在的行数     -- 默认为10    套机查询表的BOM项目号字段
                sb.append("<itemno>" + (i + 1) + "</itemno>");//行号传1,2,3.。。   不用BOM项目号字段了
                sb.append("<storagetype>" + input.getCHARG() + "</storagetype>");//批次 产品状态:10 正品 21 不良 22 破箱 40 样品 L0礼品    --10
                sb.append("<productcode><![CDATA[" + ims.getSubSku() + "]]></productcode>");//客户产品编码      -- sku   套机填sub_sku
                sb.append("<hrcode><![CDATA[" + ims.getSubSku() + "]]></hrcode>");//海尔产品编码 日日顺物流生成
                sb.append("<prodes><![CDATA[" + input.getPNAME() + "]]></prodes>");//产品描述      ---型号productname 都填统一的  
                sb.append("<volume></volume>");//体积
                sb.append("<weight></weight>");//重量
                sb.append("<number>" + ims.getMenge().multiply(input.getKWMENG()).intValue()
                          + "</number>");//网单数量乘以组件数量     不是套机填网单数量
                sb.append("<unprice>" + input.getKBETR() + "</unprice>");//单价
                sb.append("<reitem></reitem>");//前续订单行号(关联行号)
                sb.append("<remark1></remark1>");//备用
                sb.append("<remark2></remark2>");//备用
                sb.append("</Item>");
            }
        }
        sb.append("</items>");
        sb.append("</Order>");

        return sb.toString();
    }
    
    /**
     * 生成开提单参数，les要求的参数加密格式，用户取消回传LES开提单
     * @param content 数据内容
     * @throws Exception
     */
    public String cancelOrderToLesParam(String content, VomInterData vomInterData) {
        //外网测试地址：http://58.56.128.84:9001/EAI/service/VOM/CommonGetWayToVOM/CommonGetWayToVOM
        //内网测试地址：http://10.135.17.72:10101/EAI/service/VOM/CommonGetWayToVOM/CommonGetWayToVOM
        //线上地址：http://58.56.128.10:19001/EAI/RoutingProxyService/EAI_REST_POST_ServiceRoot?INT_CODE=EAI_INT_1353
        if (content == null || content.trim().equals("")) {
            log.error("content参数为空");
            return null;
        }
        if (vomInterData == null || vomInterData.getNotifyid().equals("")) {
            log.error("VomInterData参数为空");
            return null;
        }

        keyvalue = keyvalue == null || keyvalue.equals("") ? "RRS,123" : keyvalue;
        aes = aes == null || aes.equals("") ? "KeLy8g7qjmnbgWP1" : aes;
        try {
            VomInterData stgVomData = new VomInterData();
            stgVomData.setNotifyid(vomInterData.getNotifyid());//网单id作为消息id
            stgVomData.setNotifytime(vomInterData.getNotifytime());
            String s1 = AESUtil.Encrypt(content, aes);
            //        System.out.println(s1);
            //        System.out.println(AESUtil.Decrypt(s1, sKey));
            s1 = URLEncoder.encode(s1, "utf-8");
            //        System.out.println(s1);
            stgVomData.setContent(s1);
            stgVomData.setSign(Md5.getMd5(content + keyvalue));
            stgVomData.setButype("rrs_cancel");
            stgVomData.setSource("HAIERSC");
            stgVomData.setType("xml");
            List<VomInterData> list = new ArrayList<VomInterData>();
            list.add(stgVomData);
            return ObjectUtil.getObjectToURLString(list);

        } catch (Exception e) {
            log.error("调用les接口异常:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 用户取消网单生成centent的xml格式数据
     * @param cancelInput
     * @return
     */
    public String getCancelContentXml(CancelInputType cancelInput) {
    	StringBuffer sb = new StringBuffer();
        sb.append("<CancelCode>");

        if (cancelInput == null) {
            sb.append("</CancelCode>");
            return sb.toString();
        }
        sb.append("<orderno>" + cancelInput.getOrderno() + "</orderno>");//网单号
        sb.append("<canceltype>" + cancelInput.getCanceltype() + "</canceltype>");//取消类型：1.出库前取消 2.拦截订单  必填
        sb.append("</CancelCode>");
        
		return sb.toString();
    	
    }

    /**
     * 解析调用les开提单返回的xml数据
     * @param resultXml xml数据
     * @return
     */
    public HttpResult<String> getLesToOrderResult(String resultXml) {
        /**
         * 示例XML
         * <request>
         * <flag>F</flag>
         * <msg>HAIERSC系统部不识别</msg>
         * </request>
         */
        if (resultXml == null || resultXml.trim().equals("")) {
            log.error("resultXml返回为空，resultXml: " + resultXml);
            return null;
        }
        HttpResult<String> hr = new HttpResult<String>();
        //创建一个新的字符串
        StringReader read = new StringReader(resultXml);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            //System.out.println(root.getName());//输出根元素的名称（测试）
            //得到根元素所有子元素的集合
            List jiedian = root.getChildren();

            Element et = null;
            for (int i = 0; i < jiedian.size(); i++) {
                et = (Element) jiedian.get(i);//循环依次得到子元素
                if (et != null && et.getName() != null) {
                    if (et.getName().equals("flag")) {
                        String flag = et.getText();
                        hr.setSuccess(flag == null || !flag.equalsIgnoreCase("T") ? false : true);
                    } else if (et.getName().equals("msg")) {
                        hr.setMessage(et.getText());//返回结果信息
                    }
                }
            }
        } catch (JDOMException e) {
            log.error("解析les回传结果xml错误，xml:" + resultXml);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("读取les回传结果xml错误，xml:" + resultXml);
            e.printStackTrace();
        } finally {
        	if(read!=null){
        		read.close();
        	}
		}
        return hr;
    }

    /**
     * 编码并加密参数
     * @param interfaceName 接口名称
     * @param jsonContent 参数json串
     * @throws Exception
     */
    public String encodeParam(String interfaceName, String jsonContent) throws Exception {
        return DESPlus.encodeParam(interfaceName, jsonContent, o2oAppId, o2oKey);
    }

    /**
     * 解码并解密参数
     * @param encodeContent 解码解密前的参数
     * @throws Exception
     */
    public HttpResult<HttpApi> decodeParam(String encodeContent) throws Exception {
        return DESPlus.decodeAndDecryptParam(encodeContent, o2oKey);
    }

    public void setKeyvalue(String keyvalue) {
        this.keyvalue = keyvalue;
    }

    public void setAes(String aes) {
        this.aes = aes;
    }

    public void setO2oKey(String o2oKey) {
        this.o2oKey = o2oKey;
    }

    public void setO2oAppId(String o2oAppId) {
        this.o2oAppId = o2oAppId;
    }

}
