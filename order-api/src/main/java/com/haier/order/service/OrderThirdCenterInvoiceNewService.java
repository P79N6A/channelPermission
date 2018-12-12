//package com.haier.orderthird.service;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Map;
//
//import com.haier.common.ServiceResult;
//import com.haier.shop.model.Invoices;
//
//public interface OrderThirdCenterInvoiceNewService {
//    /**
//     * 根据队列，自动开票(invoice_queue到Invoices表)
//     * @return
//     */
//    ServiceResult<Boolean> createInvoice();
//
//    /**
//     * @param productID
//     * @return
//     */
//    String getCustomerCode(int productID);
//
//    /**
//     * 发票金额计算(原有计算方式)
//     * @param cOrderSn 网单号
//     * @return
//     */
//    ServiceResult<BigDecimal> getInvoiceAmount(String cOrderSn);
//
//    /**
//     * 发票金额计算(新计算方式)
//     * @param cOrderSn 网单号
//     * @param flag true代表最后如果为0元改为0.01元,false代表不更改,实际价
//     * @return
//     */
//    ServiceResult<BigDecimal> getInvoiceAmount(String cOrderSn, boolean flag);
//
//    /**
//     * 同步发票信息到电子发票系统或金穗系统(Invoices表同步开票系统)
//     * @return
//     */
//    ServiceResult<Boolean> syncToInvoiceSystem();
//
//    /**
//     * 同步作废发票信息从电子发票系统(电子发票系统同步到Invoices表)
//     * @return
//     */
//    ServiceResult<Boolean> syncInvoiceInvalidInfoFromEInvoiceSystem();
//
//    /**
//     * 同步发票信息从电子发票系统(电子发票系统同步到Invoices表)
//     * @return
//     */
//    ServiceResult<Integer> syncInvoiceInfoFromEInvoiceSystem(List<Invoices> list);
//
//    /**
//     * 保存发票信息
//     * @param id ：发票ID(MemberInvoice id)
//     * @param invoiceType
//     * @param invoiceTitle
//     * @param taxPayerNumber
//     * @param registerAddress
//     * @param registerPhone
//     * @param bankName
//     * @param bankCardNumber
//     * @param state
//     * @param remark
//     * @param auditor
//     * @return
//     */
//    String saveMemberInvoices(Integer id, Integer invoiceType, String invoiceTitle,
//                              String taxPayerNumber, String registerAddress, String registerPhone,
//                              String bankName, String bankCardNumber, Integer state, String remark,
//                              String auditor);
//
//    /**
//     * 解锁发票信息
//     * @param id
//     * @param userName
//     * @return
//     */
//    String unlockMemberInvoices(Integer id, String userName);
//
//    /**
//     * 电子发票实时开票信息查询
//     * @param orderProductId 网单id
//     * @return
//     */
//    ServiceResult<Map<String, String>> queryInvoice(String corderSn);
//
//    /**
//     * 更新发票(发票重推、取消、作废)
//     * @param invoicesList
//     * @return
//     */
//    public ServiceResult<Integer> updateInvoice(List<Invoices> invoicesList, boolean reSend);
//
//    /**
//     * 电子发票开票成功后发送HP
//     * @return
//     */
//    public ServiceResult<Boolean> syncElectricInvoiceToHp();
//
//    /**
//     * 电子发票开票成功后发送差异订单到SAP
//     * @return
//     */
//    public ServiceResult<Boolean> syncElectricInvoiceOrder2thsToSap();
//
//    /**
//     * 同步金税发票
//     * @param invoicesList
//     * @return
//     */
//    public ServiceResult<Integer> syncStatusInvoices(List<Invoices> invoicesList);
//
//    /**
//     * 金税同步状态
//     * @param invoicesList
//     * @return
//     */
//    public ServiceResult<Integer> syncTaxStatus(List<Invoices> invoicesList);
//
//    /**
//     * 根据网单号查询发票部分信息
//     * @param cOrderSn
//     * @return
//     */
//    public ServiceResult<Map<String, Object>> searchInvoicesInfoByCOrderSn(String cOrderSn);
//}
