package com.haier.svc.api.controller.order;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.order.model.Ustring;
import com.haier.order.service.AddRessService;
import com.haier.shop.dto.Merchandise;
import com.haier.shop.dto.RegionsDTO;
import com.haier.shop.model.OrderType;
import com.haier.shop.model.Orders;

import com.haier.shop.model.Products;
import com.haier.svc.api.controller.util.ExcelReader;
import com.haier.svc.api.controller.util.HttpJsonResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping(value = "order/")
public class AddOrderController {
    private final static Logger logger = LoggerFactory
            .getLogger(AddOrderController.class);


    // 导入模板表头信息
    private static final String CHECKSTR = "物料编码,物流模式,是否开票,发票类型,货票同行,纳税人识别号,发票抬头," +
                                                "注册地址,注册电话,开户银行,开户账号,省,市,区县,商品价格,商品数量,总优惠金额，"+
                                                "节能补贴金额,详细地址,收货人,收货人手机号码,收货人固定电话,订单备注,邮政编码," +
                                                 "来源订单号,关联订单号,天猫日日单";
    @Autowired
    private AddRessService addRessService;



    @RequestMapping(value = {"addOrderList"})
    public String addOrderList(HttpServletRequest request, HttpServletResponse response){
         return "order/additionOrder";
    }
    @RequestMapping(value = {"addOrder"})
    public void addOrder(HttpServletRequest request, HttpServletResponse response){

    }
    @RequestMapping(value = {"getRegions"}, method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getRegions(HttpServletRequest request, HttpServletResponse response){
        List<RegionsDTO> list = addRessService.getRegionsAll();
        JSONObject json=new JSONObject();
        json.put("rows", list);
        return json;
    }

    @RequestMapping(value = {"getRegions2"}, method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getRegions2(String parentId ,HttpServletRequest request, HttpServletResponse response){
        List<RegionsDTO> list;
        if(StringUtils.isEmpty(parentId)){
            parentId = "";
            list = addRessService.getRegionsParentId(parentId);
        }else{
            list = addRessService.getRegionsParentId(parentId);
        }

        JSONObject json=new JSONObject();
        json.put("rows", list);
        return json;
    }

    //加载弹出层的商品分类下拉列表框
    @RequestMapping(value = {"getProductCates"})
    @ResponseBody
    public JSONArray getProductCates(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Object>> list=addRessService.getProductCates();
        JSONArray jsonArray = new JSONArray();
        for (Map map:list){
            JSONObject jsonObject =new JSONObject();
            String id=map.get("id").toString();
            String cateName=map.get("cateName").toString();
            jsonObject.put("id",id);
            jsonObject.put("text",cateName);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    //加载弹出层的品牌下拉列表框
    @RequestMapping(value = {"getBrands"})
    @ResponseBody
    public JSONArray getBrands(HttpServletRequest request, HttpServletResponse response){
        List<Map<String,Object>> list=addRessService.getBrands();
        JSONArray jsonArray = new JSONArray();
        for (Map map:list){
            JSONObject jsonObject =new JSONObject();
            String id=map.get("id").toString();
            String brandName=map.get("brandName").toString();
            jsonObject.put("id",id);
            jsonObject.put("text",brandName);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    //加载商品表格
    @RequestMapping(value = {"getProducts"})
    @ResponseBody
    public JSONArray getProducts(HttpServletRequest request, HttpServletResponse response){
        JSONArray jsonArray = new JSONArray();
        List<Map<String,Object>> list=addRessService.getProducts();
        for (Map map:list){
            JSONObject jsonObject =new JSONObject();
            String id=map.get("id").toString();
            String productName=map.get("productName").toString();
            jsonObject.put("id",id);
            jsonObject.put("productName",productName);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    //点击弹出层的搜索按钮执行的函数
    @RequestMapping(value = {"getProductsByJson"})
    @ResponseBody
    public JSONArray getProductsByJson(HttpServletRequest request, HttpServletResponse response,
                                String sku,String productName,String ProductCates,String Brands){
        if (ProductCates.equals("选择商品分类")|| StringUtil.isEmpty(ProductCates)){
            ProductCates="";
        }
        if (Brands.equals("选择品牌")|| StringUtil.isEmpty(Brands)){
            Brands="";
        }
        Map<String,Object> map=new HashMap<>();
        map.put("sku",sku);
        map.put("productName",productName);
        map.put("ProductCates",ProductCates);
        map.put("Brands",Brands);
        List<Map<String,Object>> list=addRessService.getProductBy(map);
        JSONArray jsonArray = new JSONArray();
        for (Map m:list){
            JSONObject jsonObject =new JSONObject();
            String id=m.get("id").toString();
            String productName1=m.get("productName").toString();
            jsonObject.put("id",id);
            jsonObject.put("productName",productName1);
            String remove="<a >删除</a>";
            jsonObject.put("operation",remove);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    //点击弹出层的确定按钮后在表格中追加商品
    @RequestMapping(value = {"appendProduct"})
    @ResponseBody
    public JSONArray appendProduct(HttpServletRequest request, HttpServletResponse response,String data){
        JSONArray jsonArray = new JSONArray();
        List<String> list=new ArrayList<>();
        //把String转换为json
        net.sf.json.JSONArray jsonArray1 =  net.sf.json.JSONArray.fromObject(data);
        //这里的t是Class<T>
       if (jsonArray1.size()!=0){
           for (int i=0;i<jsonArray1.size();i++){
               String str=jsonArray1.get(i).toString();
               list.add(str);
           }
       }
        List<Map<String,Object>> productList=addRessService.getProductInfo(list);
        for (Map m:productList){
            JSONObject jsonObject =new JSONObject();
            String id=m.get("id").toString();
            String productName1=m.get("productName").toString();
            String sku=m.get("sku").toString();
            String limitedPrice=m.get("limitedPrice").toString();//限价
            String saleGuidePrice=m.get("saleGuidePrice").toString();//价格
            String energySubsidyAmount=m.get("energySubsidyAmount").toString();//节能补贴金额
            jsonObject.put("id",id);
            jsonObject.put("productName",productName1);
            jsonObject.put("sku",sku);
            jsonObject.put("limitedPrice",limitedPrice);
            jsonObject.put("saleGuidePrice",saleGuidePrice);
            jsonObject.put("unitPrice",Double.parseDouble(saleGuidePrice)-Double.parseDouble(energySubsidyAmount));//节能后补贴金额
            jsonObject.put("number",1);
            jsonObject.put("freight",0);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    //保存
    @RequestMapping(value = {"onSaveOrderInfo"})
    @ResponseBody
    public String onSaveOrderInfo(HttpServletRequest request, HttpServletResponse response,
                                    String idGift,String industrys,String isProduceDaily,String orderUserName,String orderValidPeriod,
                                     String source,String sourceOrderSn,String userEmail,String address,String consignee,
                                     String province,String citys,String county,String zipcode,String mobile,String deliverTime,
                                     String phone,String data ,String invoiceTitle,String isInvoice,String isElectronicInvoice,
                                     String InvoiceContent,String productInvoiceTogether,String connectOrderNum,String remark,
                                     String companyName, String taxPayerNumber, String registerAddress, String registerPhone,
                                     String bankName,  String bankCardNumber     ){

        Date date = new Date();
        long dat = date.getTime()/1000;
        String ordersn = getOrderNo();
        Map<String,Object> map=new HashMap<>();
        net.sf.json.JSONArray jsonArray1 =  net.sf.json.JSONArray.fromObject(data);
        net.sf.json.JSONObject object =null;
        Merchandise mer =null;
        List<Merchandise> list1 = new ArrayList<>();
        JSONObject json = new JSONObject();
        //判断来源订单是否存在
        if(connectOrderNum==null){
            connectOrderNum="";
        }
        if(StringUtil.isEmpty(sourceOrderSn,true)){
            sourceOrderSn = "";
        }
        map.put("sourceOrderSn",sourceOrderSn.trim());
        connectOrderNum = connectOrderNum.trim();
        if(StringUtils.isNotEmpty(connectOrderNum)){
            Orders orders = addRessService.getOrderByRelationOrderSn(connectOrderNum);
            if(orders==null){
                json.put("text", "关联订单不存在");
                return json.toString();
            }else{
                map.put("originRegionName",orders.getOriginRegionName());
                map.put("originAddress",orders.getOriginAddress());
            }
        }else{
            map.put("originRegionName","");
            map.put("originAddress","");
        }
        mobile = StringUtil.nullSafeString(mobile).trim();
        phone = StringUtil.nullSafeString(phone).trim();
        if(StringUtil.isEmpty(mobile,true)&&StringUtil.isEmpty(phone,true)){
            json.put("text", "至少需要一个手机号或者固定电话号");
            return json.toString();
        }


        BigDecimal productAmount = new BigDecimal(0);   //商品金额
        BigDecimal orderAmount = new BigDecimal(0);     // 订单金额
        BigDecimal shippingAmount = new BigDecimal(0);  // 淘宝运费
        BigDecimal totalEsAmount = new BigDecimal(0);   // 网单中总的节能补贴金额之和
        for (int i = 0; i < jsonArray1.size(); i++) {
            object = object.fromObject(jsonArray1.get(i));
            mer = (Merchandise) object.toBean(object, Merchandise.class);
            Products pro = addRessService.getProductIsBySku(mer.getSku());
            //赠品逻辑
            if("1".equals(idGift)){
                //根据SKU判断商品是否支持赠品
                if (pro == null || !pro.getIsGift()) {
                    json.put("text", "此sku" + mer.getSku() + "未维护赠品机标识，不允许录入");
                    return json.toString();
                }
                mer.setUnitPrice(new BigDecimal(0.01));
                mer.setSaleGuidePrice(new BigDecimal(0.01));
            }else{
                mer.setUnitPrice(mer.getUnitPrice());
                mer.setSaleGuidePrice(mer.getUnitPrice());
            }

            //结束
            shippingAmount = mer.getFreight();
            totalEsAmount = pro.getEnergySubsidyAmount();
            productAmount = mer.getUnitPrice().multiply(new BigDecimal(mer.getNumber()));
            orderAmount =productAmount.add(shippingAmount);
            mer.setProductName(pro.getProductName());
            mer.setAddTime(dat);
            mer.setIsTest(0);//是否是测试网点
            mer.setEsAmount(pro.getEnergySubsidyAmount());
            mer.setOrderStatus(200);
            mer.setSupportOneDayLimit(0);//是否支持24小时时限达
            mer.setcOrderSn(ordersn);//child order sn 子订单编码 C0919293(网单号)
            mer.setcPaymentStatus(200);//子订单付款状态
            mer.setcPayTime(0);//子订单付款时间
            mer.setProductType(pro.getProductTypeId());//商品类型
            mer.setLockedNumber(0);//曾经锁定的库存数量
            mer.setUnlockedNumber(0);//曾经解锁的库存数量
            mer.setProductAmount(productAmount);//此字段专为同步外部订单而加
            mer.setCouponAmount(new BigDecimal(0));//优惠券抵扣金额
            mer.setCateId(pro.getProductCateId());//分类ID
            mer.setBrandId(pro.getBrandId());//品牌id
            mer.setNetPointId(0);//网点id
            mer.setSettlementStatus(0);//结算状态0 未结算 1已结算 、
            mer.setReceiptOrRejectTime(0);//确认收货时间或拒绝收货时间、
            mer.setIsWmsSku(0);//是否淘宝小家电、
            mer.setsCode("");//库位编码、
            mer.setStatus(0);//状态、
            mer.setInvoiceNumber("");//运单号、
            mer.setExpressName("");//快递公司、
            mer.setInvoiceExpressNumber("");//发票快递单号、
            mer.setShippingTime(0);//发货时间、
            mer.setLessOrderSn("");//less 订单号、
            mer.setWaitGetLesShippingInfo(0);//是否等待获取LES物流配送节点信息
            mer.setGetLesShippingCount(0);//已获取LES配送节点信息的次数
            mer.setOutping("");//出库凭证
            mer.setLessShipTime(0);//less出库时间
            mer.setCloseTime(0);//网单完成关闭或取消关闭时间
            mer.setReceiptNum("");//发票号
            mer.setReceiptAddTime("");//开票时间
            mer.setMakeReceiptType(0);//开票类型
            mer.setShippingMode(pro.getShippingMode());//物流模式值为B2B2C或B2C
            mer.setLastTimeForShippingMode(0);//最后修改物流模式的时间
            mer.setLastEditorForShippingMode("");//最后修改物流模式的管理员
            mer.setSystemRemark("");//系统备注
            mer.setExternalSaleSettingId(0);//淘宝套装id
            mer.setIsNoLimitStockProduct(0);//是否是无限制库存量的商品
            mer.setSplitFlag(0);//拆单标志
            mer.setSplitRelateCOrderSn("");//拆单关联单号
            list1.add(mer);
        }
        if("普通发票".equals(InvoiceContent)){
            InvoiceContent = "2";
        }else{
            InvoiceContent = "1";
        }


        map.put("idGift",idGift);
        map.put("industrys",industrys);
        map.put("isProduceDaily",isProduceDaily);//isProduceDaily 是否日日单
        map.put("orderUserName",orderUserName);//
        map.put("orderValidPeriod",orderValidPeriod);

        map.put("userEmail",userEmail);
        map.put("address",address);  //address收货地址中用户输入的地址
        if(StringUtil.isEmpty(String.valueOf(map.get("originAddress")),true)){
            map.put("originAddress",address);
        }
        map.put("consignee",consignee);// consignee收货人
        map.put("source",source);//source 订单来源
        map.put("province",Integer.parseInt(province));//省
        map.put("citys",Integer.parseInt(citys));//市
        map.put("county",Integer.parseInt(county));//区
        map.put("zipcode",zipcode);//zipcode 收货地址中的邮编
        map.put("mobile",phone);// mobile 收货人固定电话号
        map.put("deliverTime",deliverTime);
        map.put("phone",mobile);//`phone` '收货人手机号'
        if(invoiceTitle !=null){
            map.put("invoiceTitle",invoiceTitle);//发票抬头
        }else{
            map.put("invoiceTitle",companyName);//发票抬头
        }

        if(isInvoice==null){
            isInvoice="1";
        }
        map.put("isInvoice",isInvoice);//
        map.put("isElectronicInvoice",isElectronicInvoice);
        map.put("InvoiceContent",InvoiceContent);
        map.put("productInvoiceTogether",productInvoiceTogether);//isTogether货票通行

        if(StringUtils.isEmpty(connectOrderNum)) {
            map.put("connectOrderNum", "新单");
        }else{
            map.put("connectOrderNum", connectOrderNum);
        }
        if(remark==null){
            remark = "";
        }
        map.put("remark",remark);//备注
        map.put("addTime",dat);//addTime 下单时间

        /*orders默认值*/
        map.put("isTest",0);//是否是测试订单
        map.put("isCod",0);//是否是货到付款订单
        map.put("notAutoConfirm",0);//是否是非自动确认订单
        map.put("orderSn",ordersn);//是否是非自动确认订单
        map.put("memberId",0);//会员id
        map.put("memberEmail","");//会员邮件
        map.put("syncTime",(new Date().getTime() / 1000));// 同步时间
        map.put("orderStatus",200);// 默认为未确认状态
        map.put("payTime",0);// 在线付款时间
        map.put("paymentStatus",100);// 付款状态
        map.put("receiptConsignee","");// 发票收件人
        map.put("receiptAddress","");// 发票地址
        map.put("receiptZipcode","");// 发票邮编
        map.put("receiptMobile","");// 发票联系电话

        //商品金额
        if("1".equals(idGift)){
            map.put("productAmount",new BigDecimal(0.01));// 商品金额
            map.put("orderAmount",new BigDecimal(0.01));// 订单金额
            map.put("paidAmount",new BigDecimal(0.01));// 已支付金额
            map.put("shippingAmount",new BigDecimal(0));// 淘宝运费
            map.put("totalEsAmount",new BigDecimal(0));// 网单中总的节能补贴金额之和
        }else {
            map.put("productAmount", productAmount);// 商品金额
            map.put("orderAmount", orderAmount);// 订单金额
            map.put("paidAmount", new BigDecimal(0));// 已支付金额
            map.put("shippingAmount", shippingAmount);// 淘宝运费
            map.put("totalEsAmount", totalEsAmount);// 网单中总的节能补贴金额之和
        }

        map.put("paymentCode","alipay");// 支付方式
        map.put("payBankCode","");// 网银代码
        map.put("paymentName","PC端支付宝");// 支付方式名称
        map.put("street",0);// 街道信息
        map.put("markBuilding",0);// 标建标志
        map.put("poiId","");// 标建id
        map.put("poiName","");// 标建名称
        map.put("regionName","");// 地区名称
        map.put("receiptInfo","a:0:{}");// "a:0:{}"
        map.put("delayShipTime",0);// 延缓发货日期
        map.put("firstConfirmTime",0);// 首次确定时间
        map.put("firstConfirmPerson","");// 首次确定人
        map.put("signCode","");// 收货确定码
        map.put("orderType",0);// 订单类型 默认0 团购预付款 团购正式单 2
        map.put("memberInvoiceId",0);//订单发票ID,MemberInvoices表的主键
        map.put("taobaoGroupId",0);//淘宝万人团活动ID
        map.put("tradeType","fixed");//交易类型,值参考淘宝
        map.put("stepTradeStatus","");//分阶段付款的订单状态,值参考淘宝
        map.put("stepPaidFee",new BigDecimal(0));// 分阶段付款的已付金额
        map.put("depositAmount",new BigDecimal(0));// 定金应付金额
        map.put("balanceAmount",new BigDecimal(0));// 尾款应付金额
        map.put("autoCancelDays",orderValidPeriod);//未付款过期的天数
        map.put("isNoLimitStockOrder",0);//不是无库存限制订单
        map.put("autoConfirmNum",0);//自动确认次数
        map.put("smConfirmTime",0);//请求发送HP时间，格式为时间戳


        /*发票默认值*/
        map.put("memberName","");//会员名称
        if(taxPayerNumber != null){
            map.put("taxPayerNumber",taxPayerNumber);//纳税人识别号
        }else{
            map.put("taxPayerNumber","");//纳税人识别号
        }
        if(registerAddress != null){
            map.put("registerAddress",registerAddress);//注册地址
        }else{
            map.put("registerAddress","");//注册地址
        }
        if(registerPhone != null){
            map.put("registerPhone",registerPhone);//注册电话
        }else{
            map.put("registerPhone","");//注册电话
        }
        if(bankName != null){
            map.put("bankName",bankName);//开户银行
        }else{
            map.put("bankName","");//开户银行
        }
        if(bankCardNumber != null){
            map.put("bankCardNumber",bankCardNumber);//银行账户
        }else{
            map.put("bankCardNumber","");//银行账户
        }
        HttpSession session = request.getSession();
        String userName  = Ustring.getString(session.getAttribute("userName"));
        map.put("userName",userName);
        map.put("state",0);//审核状态0-待审核,1-审核通过,2-拒绝
        map.put("auditor","系统");//审核人
        map.put("remarks","");//审核人备注
        map.put("isLock",0);//是否锁定
        map.put("parentId",0);//已审核通过的增票记录
        String flas = addRessService.addProduct(map,list1);
        return flas;

    }
    public static String getOrderNo() {
        //序列号,不足6位补0,超过6位取末尾6位\
        StringBuffer flag = new StringBuffer();
        for (int i = 0; i <= 100; i++)
        {
            String sources = Ustring.getString(Calendar.getInstance().getTimeInMillis()); // 加上一些字母，就可以生成pc站的验证码了
            Random rand = new Random();
            for (int j = 0; j < 8; j++)
            {
                flag.append(sources.charAt(rand.nextInt(9)) + "");
            }
        }
        String seq = flag.toString();
        int len = seq.length();
        if (len > 8) {
            seq = seq.substring(len - 8);
        }
        //日期
//        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
//        String sDate = sdf.format(new Date());
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR)).substring(2, 4);
        String month = String .valueOf(date.get(Calendar.MONTH) + 1 + 24);
        String day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        if(day.length() == 1){
            day = "0" + day;
        }
        return  year + month + day + seq;
    }
    public static Orders getDefaultOrder() {
        Orders order = new Orders();
        order.setSyncTime((new Date().getTime() / 1000));// 同步时间
        order.setReceiptInfo("a:0:{}");// 发票信息
        order.setSmConfirmStatus(1);// 确认标建的初始状态
        order.setAgent(""); // 处理人
        order.setAutoCancelDays(0); // 未付款过期的天数
        order.setAutoConfirmNum(0);// 自动确认次数
        order.setBankCode("");// 银行代码
        order.setCodConfirmPerson("");// 货到付款确认人
        order.setCodConfirmRemark("");// 货到付款确认备注
        order.setCodConfirmState("");// 货到付款确认状态
        order.setCodConfirmTime("");// 货到付款确认时间
        order.setConfirmTime(0);// 确认时间
        order.setDelayShipTime(0);// 延迟发货日期
        order.setFinishTime(0);// 订单完成时间
        order.setFirstConfirmTime(0);// 首次确认时间
        order.setFirstConfirmPerson("");// 首次确认用户
        order.setIsBackend(0);// 不是后台添加的订单
        order.setIsBook(0);// 不是预定订单
        order.setIsCod(0);// 不是货到付款订单
        order.setIsNoLimitStockOrder(0);// 不是无库存限制订单
        order.setIsTest(0);// 不是测试订单
        order.setMarkBuilding(0);// 标建，淘宝订单需要客服手工处理
        order.setMemberId(0);// 会员Id，淘宝订单不对应客户
        order.setMemberInvoiceId(0);// 订单发票id，外面会重新赋值
        order.setNotAutoConfirm(0);// 是否不自动确认订单，1：是，0：否
        order.setOnedayLimit(0);// 是否24小时限时到达，目前这个字段没有用，网单上会分别限制
        order.setOrderStatus(200);// 默认为未确认状态
        order.setPayBankCode("");// 网银代码
        order.setPaymentNoticeUrl("");// ?
        order.setReceiptAddress("");
        order.setReceiptConsignee("");
        order.setReceiptMobile("");
        order.setReceiptZipcode("");
        order.setRelationOrderSn("");// 关联订单，主要用于万人团订单
        order.setStreet(0);// 街道id
        order.setTotalEsAmount(BigDecimal.ZERO);// 节能补贴总计，在网单中会计算
        order.setIsTogether(0);// 是否货票同行
        order.setTailPayTime(0L);
        order.setTaobaoGroupId(0);
        order.setDepositAmount(BigDecimal.ZERO);
        order.setBalanceAmount(BigDecimal.ZERO);
        order.setOrderType(OrderType.TYPE_NORMAL.getValue());
        order.setStepTradeStatus("");// 分阶段付款的交易状态，值参考淘宝
        order.setStepPaidFee(new BigDecimal(0));// 分阶段付款的已付金额
        order.setTradeType("");
        order.setPoints(0L);
        order.setIsNotConfirm(0);// 默认是可以确认
        order.setShippingAmount(new BigDecimal(0));// 淘宝运费
        order.setCkCode("");
        return order;
    }

    //跳转导入页面
    @RequestMapping(value = {"/importInvWarehouse"}, method = {RequestMethod.GET})
    public String importInvWarehouse(Model modal) {
        return "order/ImportJump";
    }

    /**
     * 导入
     */
    @RequestMapping(value = {"/importInvWarehouses"}, method = {RequestMethod.POST})
    @ResponseBody
    HttpJsonResult<Map<String, Object>> importInvWarehouse(
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> modelMap,
            String source,
            String industrys,
            String orderValidPeriod,
            String paymentName,
            String idGift
            ) {

        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        // 转型为MultipartHttpRequest
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        long totalStartTime = System.currentTimeMillis();
        List<Merchandise> invWarehouses = new ArrayList<Merchandise>();

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

            boolean flag = this.checkDataTemplate(firstLineData, CHECKSTR);

            if (flag) {
                result.setMessage("很抱歉！你导入的Excel数据格式与系统模板格式存在差异，请下载模板后重新导入！");
                return result;
            }

            //判断Excel中主键物料编码是否重复
          /*  String temp = "";
            for (int i = 0; i < list.size() - 1; i++) {
                String[] str = list.get(i);
                temp = StringUtil.nullSafeString(str[0]).trim();
                for (int j = i + 1; j < list.size(); j++) {
                    String[] str1 = list.get(j);
                    if (temp.equals(StringUtil.nullSafeString(str1[0]).trim())) {
                        result.setMessage("很抱歉！你导入的Excel数据中第" + (i + 1) + "行跟第" + (j + 1) + "物料编码重复，值是：" + temp);
                        return result;
                    }
                }
            }*/

            if(list.size()>2000){
                result.setMessage("很抱歉！你导入的Excel数据大于2000条！请分割数据再录入");
                return result;
            }

            // 读取数据
            for (int i = 1; i < list.size(); i++) {

                String[] str = list.get(i);
                // 仓库(TC)编码
                String sku = StringUtil.nullSafeString(str[1]).trim();                                // 物料编码
                String shippingMode = StringUtil.nullSafeString(str[2]).trim();                       // 物流模式
                String invoicing = StringUtil.nullSafeString(str[3]).trim();//是否开具发票
                String invoiceType = StringUtil.nullSafeString(str[4]).trim();                         //发票类型
                String isTogether = StringUtil.nullSafeString(str[5]).trim();                          //      货票同行
                String taxPayerNumber = StringUtil.nullSafeString(str[6]).trim();                      //纳税人识别
                String invoiceTitle = StringUtil.nullSafeString(str[7]).trim();                         //发票抬头
                String registerAddress = StringUtil.nullSafeString(str[8]).trim();                     //注册地址
                String registerPhone = StringUtil.nullSafeString(str[9]).trim();                       //注册电话
                String bankName = StringUtil.nullSafeString(str[10]).trim();                            //开户银行
                String bankCardNumber = StringUtil.nullSafeString(str[11]).trim();                      //银行账户
                String province = StringUtil.nullSafeString(str[12]).trim();                           //  省
                String city = StringUtil.nullSafeString(str[13]).trim();                               //  市
                String region = StringUtil.nullSafeString(str[14]).trim();                             //  区
                String unitPrice = StringUtil.nullSafeString(str[15]).trim();                          //  商品价格
                String number = StringUtil.nullSafeString(str[16]).trim();                             //  数量
                String couponCodeValue = StringUtil.nullSafeString(str[17]).trim();                    //  优惠价
                String esAmount = StringUtil.nullSafeString(str[18]).trim();                           //  节能补贴金额
                String address = StringUtil.nullSafeString(str[19]).trim();                            //  详细地址
                String consignee = StringUtil.nullSafeString(str[20]).trim();                          //  收货人
                String mobile = StringUtil.nullSafeString(str[21]).trim();                             //  收货人手机
                String phone = StringUtil.nullSafeString(str[22]).trim();                              //  收货人固话
                String remark1 = StringUtil.nullSafeString(str[23]).trim();                            //  订单备注
                String zipcode = StringUtil.nullSafeString(str[24]).trim();                            //  邮政编码
                String sourceOrderSn = StringUtil.nullSafeString(str[25]).trim();                      //来源订单号
                String relationOrderSn = StringUtil.nullSafeString(str[26]).trim();                    //  关联订单号
                String isProduceDaily = StringUtil.nullSafeString(str[27]).trim();                     //  是否是日日单
              //  String source = StringUtil.nullSafeString(str[28]).trim();                     //  订单来源
                boolean isAllNull = StringUtil.isEmpty(sku, true)
                        && StringUtil.isEmpty(shippingMode, true)
                        && StringUtil.isEmpty(invoicing, true)
                        && StringUtil.isEmpty(invoiceType, true)
                        && StringUtil.isEmpty(isTogether, true)
                        && StringUtil.isEmpty(taxPayerNumber, true)
                        && StringUtil.isEmpty(invoiceTitle, true)
                        && StringUtil.isEmpty(registerAddress, true)
                        && StringUtil.isEmpty(registerPhone, true)
                        && StringUtil.isEmpty(bankName, true)
                        && StringUtil.isEmpty(bankCardNumber, true)
                        && StringUtil.isEmpty(province, true)
                        && StringUtil.isEmpty(city, true)
                        && StringUtil.isEmpty(region, true)
                        && StringUtil.isEmpty(unitPrice, true)
                        && StringUtil.isEmpty(number, true)
                        && StringUtil.isEmpty(couponCodeValue, true)
                        && StringUtil.isEmpty(esAmount, true)
                        && StringUtil.isEmpty(address, true)
                        && StringUtil.isEmpty(consignee, true)
                        && StringUtil.isEmpty(mobile, true)
                        && StringUtil.isEmpty(phone, true)
                        && StringUtil.isEmpty(remark1, true)
                        && StringUtil.isEmpty(zipcode, true)
                        && StringUtil.isEmpty(sourceOrderSn, true)
                        && StringUtil.isEmpty(relationOrderSn, true)
                        && StringUtil.isEmpty(isProduceDaily, true);
                        //&& StringUtil.isEmpty(source, true);

                if (isAllNull) {
                    nullRow++;
                    continue;
                }
                // 导入模板内容的非空判断****************START*******************************
                if (StringUtil.isEmpty(sku, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【物料编码】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(shippingMode, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【物流模式】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(invoicing, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【是否开票】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(invoiceType, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【发票类型】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(invoiceTitle, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【发票抬头】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(province, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【省】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(city, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【市】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(region, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【区】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(unitPrice, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【商品价格】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(number, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【商品数量】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(couponCodeValue, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【总优惠金额】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(esAmount, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【节能补贴金额】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(address, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【详细地址】不能为空! 请核查后重新导入！");
                    return result;
                }
                address = address.replaceAll("\\p{P}"," ");
                if (StringUtil.isEmpty(consignee, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【收货人】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(mobile, true)&& StringUtil.isEmpty(phone,true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的没有手机号和座机号! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(remark1, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【订单备注】不能为空! 请核查后重新导入！");
                    return result;
                }
//                if (StringUtil.isEmpty(sourceOrderSn, true)) {
//                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
//                            + "行数据的【来源订单号】不能为空! 请核查后重新导入！");
//                    return result;
//                }
                if(sourceOrderSn.length()>1&&sourceOrderSn.substring(0,1).equals("=")){
                    sourceOrderSn = sourceOrderSn.substring(1,sourceOrderSn.length());
                }
                if(sourceOrderSn.length()>1&&sourceOrderSn.substring(0,1).equals("#")){
                    sourceOrderSn = sourceOrderSn.substring(1,sourceOrderSn.length());
                }

                if (StringUtil.isEmpty(relationOrderSn, true)) {
                    relationOrderSn="新单";
//                    MsgList = "很抱歉！你导入的Excel数据,第" + i
//                            + "行数据的【关联订单号】不能为空! 请核查后重新导入！";
//                    if (StringUtil.isEmpty(MsgList, true)) {
//                        sb.append(MsgList);
//                    } else {
//                        MsgList = MsgList + "<br></br>";
//                        sb.append(MsgList);
//                    }
//                    continue;
                }
                if (StringUtil.isEmpty(isProduceDaily, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【是否是日日单】不能为空! 请核查后重新导入！");
                    return result;
                }

                if (StringUtil.isEmpty(source, true)) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【订单来源】不能为空! 请核查后重新导入！");
                    return result;
                }

                Merchandise invWarehouse = new Merchandise();

                invWarehouse.setSku(sku);//物料编码
                invWarehouse.setShippingMode(shippingMode);//物流模式
                //状态dataStatus
                if (  "是".equals(invoicing)) {

                    invoicing = "1";
                } else if ( "否".equals(invoicing)) {
                    invoicing = "0";
                }
                invWarehouse.setInvoicing(Integer.parseInt(invoicing));
                if (  "普通发票".equals(invoiceType)) {

                    invoiceType = "2";
                } else  {
                    invoiceType = "1";
                }
                invWarehouse.setInvoiceType(Integer.parseInt(invoiceType));
                if (  "是".equals(isTogether)) {

                    isTogether = "1";
                } else  {
                    isTogether = "0";
                }
                invWarehouse.setIsTogether(Integer.parseInt(isTogether));

                invWarehouse.setTaxPayerNumber(taxPayerNumber);
                invWarehouse.setInvoiceTitle(invoiceTitle);

                invWarehouse.setRegisterAddress(registerAddress);
                invWarehouse.setRegisterPhone(registerPhone);
                invWarehouse.setBankName(bankName);
                invWarehouse.setBankCardNumber(bankCardNumber);
                invWarehouse.setProvince(province);
                invWarehouse.setCity(city);
                invWarehouse.setRegion(region);
                Integer numbers = Integer.parseInt(number);
                if (numbers<1) {
                    result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                            + "行数据的【商品数量】不能小于0! 请核查后重新导入！");
                    return result;
                }
                if("1".equals(idGift)){
                    invWarehouse.setSaleGuidePrice(new BigDecimal(0.01));
                    invWarehouse.setNumber(numbers);
                    invWarehouse.setEsAmount(new BigDecimal(0));
                }else{
                    BigDecimal unitP = new BigDecimal(unitPrice);
                    if(unitP.compareTo(new BigDecimal("0"))<1){
                        result.setMessage( "很抱歉！你导入的Excel数据,第" + i
                                + "行数据的【商品价格】不能小于0! 请核查后重新导入！");
                        return result;
                    }
                    invWarehouse.setSaleGuidePrice(unitP);
                    invWarehouse.setNumber(numbers);
                    invWarehouse.setEsAmount(new BigDecimal(esAmount));
                }
                invWarehouse.setCouponCodeValue(new BigDecimal(couponCodeValue));
                invWarehouse.setAddress(address);
                invWarehouse.setConsignee(consignee);
                if(mobile.length()>1&&mobile.substring(0,1).equals("#")){
                    mobile = mobile.substring(1,mobile.length());
                    invWarehouse.setMobile(mobile);
                }else{
                    invWarehouse.setMobile(mobile);
                }
                if (phone.length()>1&&phone.substring(0, 1).equals("#")) {
                    phone = phone.substring(1,phone.length());
                    invWarehouse.setPhone(phone);
                }else{
                    invWarehouse.setPhone(phone);
                }

                invWarehouse.setRemark1(remark1);
                invWarehouse.setZipcode(zipcode);
                invWarehouse.setSourceOrderSn(sourceOrderSn);
                if(relationOrderSn.substring(0,1).equals("#")){
                    invWarehouse.setRelationOrderSn(relationOrderSn.substring(1,relationOrderSn.length()));
                }
                invWarehouse.setRelationOrderSn(relationOrderSn);

                if("是".equals(isProduceDaily)){
                    isProduceDaily = "1";
                }else if("否".equals(isProduceDaily)){
                    isProduceDaily = "0";
                }
                invWarehouse.setIsProduceDaily(Integer.parseInt(isProduceDaily));

             //   invWarehouse.setSource(Integer.parseInt(source));

                invWarehouse.setSource(source);
                invWarehouse.setAutoCancelDays(Integer.parseInt(orderValidPeriod));
                invWarehouse.setPaymentName(paymentName);

                invWarehouses.add(invWarehouse);
            }

            HashMap<String,String> param = new HashMap<String,String>();
            HttpSession session = request.getSession();
            String userName  = Ustring.getString(session.getAttribute("userName"));
            param.put("source",source);
            param.put("industrys",industrys);
            param.put("idGift",idGift);
            param.put("userName",userName);
            ServiceResult<Map<String, Integer>> insResult = addRessService.insertInvWarehouses(invWarehouses,param);

            int success = 0;
            int failure = 0;
            if (insResult.getResult() != null) {
                success = insResult.getResult().get("success");
                failure = insResult.getResult().get("failure");
            }
            if(StringUtils.isNotEmpty(insResult.getMessage())){
                sb.append(insResult.getMessage());
            }

            logger.info("批量录入订单与赠品");
            modelMap.put("total", list.size() - 1 - nullRow);
            logger.info("======录入========"+(list.size() - 1 - nullRow));
            modelMap.put("ignore", list.size() - success - 1 - nullRow);
            logger.info("======失败========"+(list.size() - success - 1 - nullRow));
            modelMap.put("success", success);
            logger.info("======success========"+success);
            modelMap.put("failure", failure);
            logger.info("======failure========"+failure);
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
}
